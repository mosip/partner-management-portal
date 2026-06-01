import { useEffect, useMemo, useRef, useState } from "react";
import { useLocation, useNavigate, useBlocker } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { createRequest, getPartnerManagerUrl, handleEscapeKey, handleServiceErrors, isLangRTL } from "../../../utils/AppUtils";
import { HttpService } from "../../../services/HttpService";
import { getAppConfig } from "../../../services/ConfigService";
import Title from "../../common/Title";
import ErrorMessage from "../../common/ErrorMessage";
import LoadingIcon from "../../common/LoadingIcon";
import DropdownComponent from "../../common/fields/DropdownComponent";
import BlockerPrompt from "../../common/BlockerPrompt";
import DropdownWithSearchComponent from "../../common/fields/DropdownWithSearchComponent";

const EMPTY_MAPPING_ROW = {
  biometricModality: "",
  biometricProviderConfiguration: "",
};

const parseCsvOrArray = (raw) => {
  const list = Array.isArray(raw) ? raw : typeof raw === "string" ? raw.split(",") : [];
  return list.map((v) => String(v || "").trim()).filter(Boolean);
};

const parseModalityAttributeNameMapFromConfig = (configData) => {
  const raw =
    configData?.allowedBioextractorModalitiesAttributeNameMap ??
    configData?.allowedBioextractorModalitiesAttributeNameMapString ??
    configData?.allowedBioextractorModalityAttributeNameMap ??
    configData?.allowedBioextractorModalityAttributeNameMapString ??
    configData?.["mosip.pms.bioextractor.allowed.modalities.attribute.name.map"];

  const result = {};
  const rawStr = typeof raw === "string" ? raw.trim() : "";

  if (raw && typeof raw === "object" && !Array.isArray(raw) && !rawStr) {
    Object.entries(raw).forEach(([k, v]) => {
      const modality = String(k || "").trim();
      if (!modality) return;
      result[modality.toUpperCase()] = String(v || "").trim();
    });
    return result;
  }

  if (!rawStr) return result;

  rawStr
    .split(",")
    .map((t) => t.trim())
    .filter(Boolean)
    .forEach((segment) => {
      const parts = segment.split(":").map((p) => p.trim()).filter(Boolean);
      if (parts.length < 2) return;
      for (let i = 0; i + 1 < parts.length; i += 2) {
        result[String(parts[i]).toUpperCase()] = String(parts[i + 1] || "").trim();
      }
    });

  return result;
};

function MapBiometricExtractorProvider() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { state } = useLocation();
  // Required for submit + next step
  const hasRequiredState = Boolean(state?.partnerId && state?.policyId && state?.policyName);
  const userProfile = getUserProfile();
  const isLoginLanguageRTL = isLangRTL(userProfile?.locale || "en");

  const rowIdSeqRef = useRef(0);
  const nextRowId = () => `row_${Date.now()}_${rowIdSeqRef.current++}`;
  const initialRowIdRef = useRef(nextRowId());

  const [dataLoaded, setDataLoaded] = useState(true);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [rows, setRows] = useState([{ id: initialRowIdRef.current, ...EMPTY_MAPPING_ROW }]);
  const [isSubmitClicked, setIsSubmitClicked] = useState(false);
  const [activeConfigs, setActiveConfigs] = useState(() => ({
    [initialRowIdRef.current]: [],
  }));
  const [configFetchErrorByRowId, setConfigFetchErrorByRowId] = useState({});
  const configReqVersionRef = useRef({});
  const [showSaveConfirm, setShowSaveConfirm] = useState(false);

  const [allowedModalities, setAllowedModalities] = useState([]);
  const [attributeNameByModality, setAttributeNameByModality] = useState({});

  useEffect(() => {
    let cancelled = false;
    (async () => {
      try {
        const configData = await getAppConfig();
        if (cancelled) return;

        const modalityAttrMap = parseModalityAttributeNameMapFromConfig(configData);
        const modalitiesFromMap = Object.keys(modalityAttrMap)
          .map((m) => String(m || "").trim())
          .filter(Boolean)
          .map((m) => m.toLowerCase());

        if (modalitiesFromMap.length > 0) {
          setAllowedModalities(Array.from(new Set(modalitiesFromMap)));

          const map = {};
          Object.entries(modalityAttrMap).forEach(([modalityUpper, attr]) => {
            map[String(modalityUpper || "").toUpperCase()] = String(attr || "").trim();
          });
          setAttributeNameByModality(map);
        } else {
          // Backward-compatible fallback (older system-config fields)
          const modalities = parseCsvOrArray(configData?.allowedBioextractorModalities).map((m) => String(m).toLowerCase());
          const attributeNames = parseCsvOrArray(configData?.allowedBioextractorAttributeNames);

          setAllowedModalities(modalities);

          const map = {};
          modalities.forEach((modality, index) => {
            map[String(modality || "").toUpperCase()] = String(attributeNames[index] || "").trim();
          });
          setAttributeNameByModality(map);
        }
      } catch (error) {
        if (cancelled) return;
        console.error("Error fetching bio-extractor config from system-config:", error);
        setAllowedModalities([]);
        setAttributeNameByModality({});
      }
    })();

    return () => {
      cancelled = true;
    };
  }, []);

  const modalityDropdownData = useMemo(
    () =>
      allowedModalities.map((value) => ({
        fieldCode: value,
        fieldValue: value,
      })),
    [allowedModalities]
  );

  /** Options for one row: unselected modalities elsewhere, plus this row's current value (any order). */
  const getModalityDropdownDataForRow = (rowId) => {
    const usedElsewhere = new Set(
      rows
        .filter((r) => r.id !== rowId && r.biometricModality)
        .map((r) => String(r.biometricModality).toUpperCase())
    );
    const currentRow = rows.find((r) => r.id === rowId);
    const current = String(currentRow?.biometricModality || "").toUpperCase();

    return modalityDropdownData.filter((item) => {
      const val = String(item.fieldValue || "").toUpperCase();
      if (current && val === current) return true;
      return !usedElsewhere.has(val);
    });
  };

  const distinctSelectedModalitiesCount = useMemo(() => {
    return new Set(
      rows.map((r) => r.biometricModality).filter(Boolean).map((m) => String(m).toUpperCase())
    ).size;
  }, [rows]);

  const canAddMoreRow =
    rows.length < allowedModalities.length && distinctSelectedModalitiesCount < allowedModalities.length;

  const getAttributeName = (modality) => {
    return attributeNameByModality[String(modality || "").trim().toUpperCase()] || "";
  };

  const getConfigDropdownByModality = (rowId, selectedModality) => {
    const configs = activeConfigs[rowId] || [];

    return configs
      .filter(
        (cfg) =>
          !selectedModality ||
          (cfg.biometricModality || "").toUpperCase() === (selectedModality || "").toUpperCase()
      )
      .map((cfg) => ({
        fieldCode: cfg.configurationName,
        fieldValue: cfg.configurationName,
      }));
  };

  const shouldBlockNavigation = () => {
    return rows.some((row) => row.biometricModality || row.biometricProviderConfiguration);
  };

  const blocker = useBlocker(({ currentLocation, nextLocation }) => {
    if (isSubmitClicked) return false;
    return shouldBlockNavigation() && currentLocation.pathname !== nextLocation.pathname;
  });

  useEffect(() => {
    if (hasRequiredState) return;
    navigate("/partnermanagement/policies/request-policy");
  }, [hasRequiredState, navigate]);

  useEffect(() => {
    if (!showSaveConfirm) return;
    document.body.style.overflow = "hidden";
    return () => {
      document.body.style.overflow = "auto";
    };
  }, [showSaveConfirm]);

  useEffect(() => {
    if (!showSaveConfirm) return;
    return handleEscapeKey(() => setShowSaveConfirm(false));
  }, [showSaveConfirm]);

  const fetchBioExtractorConfigs = async (index, modality) => {
    const rowId = index;
    try {
      setDataLoaded(false);
      const nextVersion = (configReqVersionRef.current[rowId] || 0) + 1;
      configReqVersionRef.current[rowId] = nextVersion;

      setConfigFetchErrorByRowId((prev) => ({ ...prev, [rowId]: "" }));

      const response = await HttpService.get(
        getPartnerManagerUrl(`/bio-extractor-configurations?bioModality=${modality.toLowerCase()}`, process.env.NODE_ENV)
      );

      const isStale = configReqVersionRef.current[rowId] !== nextVersion;
      if (isStale) return;

      if (response?.data?.response) {
        const configs = response?.data?.response?.data || [];

        const formattedConfigs = configs.map((item) => ({
          configurationName: item.configName,
          bioextractorProviderName: item.bioextractorProviderName || item.extractorProviderName || item.provider || "",
          bioextractorProviderVersion: item.bioextractorProviderVersion || item.extractorProviderVersion || item.version || "",
          biometricModality: item.bioModality || "",
        }));

        setActiveConfigs((prev) => {
          if (!Object.prototype.hasOwnProperty.call(prev, rowId)) return prev;
          return { ...prev, [rowId]: formattedConfigs };
        });
      } else {
        setConfigFetchErrorByRowId((prev) => ({
          ...prev,
          [rowId]: t("mapBiometricExtractorProvider.configFetchError"),
        }));
        setActiveConfigs((prev) => {
          if (!Object.prototype.hasOwnProperty.call(prev, rowId)) return prev;
          return { ...prev, [rowId]: [] };
        });
      }
    } catch (err) {
      console.error("API ERROR:", err);
      setConfigFetchErrorByRowId((prev) => ({
        ...prev,
        [rowId]: t("mapBiometricExtractorProvider.configFetchError"),
      }));
      setActiveConfigs((prev) => {
        if (!Object.prototype.hasOwnProperty.call(prev, rowId)) return prev;
        return { ...prev, [rowId]: [] };
      });
    } finally {
      setDataLoaded(true);
    }
  };

  if (!hasRequiredState) return null;

  const policyDetails = {
    partnerId: state?.partnerId || "",
    partnerType: state?.partnerType || "",
    policyGroupName: state?.policyGroupName || "",
    policyName: state?.policyName || "",
    policyId: state?.policyId || "",
    mappingKey: state?.mappingKey || "",
    requestPayload: state?.requestPayload || null,
    partnerComment: state?.partnerComment || "",
  };

  const getSelectedConfig = (rowId, configurationName) => {
    const configs = activeConfigs[rowId] || [];
    return configs.find((config) => config.configurationName === configurationName);
  };

  const isRowEmpty = (row) => !row.biometricModality && !row.biometricProviderConfiguration;

  const isRowFullyMapped = (row) => {
    if (!row.biometricModality || !row.biometricProviderConfiguration) return false;
    const selected = getSelectedConfig(row.id, row.biometricProviderConfiguration) || {};
    return Boolean((selected.bioextractorProviderName || "").trim());
  };

  /** At least one complete mapping; empty extra rows are ignored; partially filled rows must be completed. */
  const getMappedRowsForSubmit = () => rows.filter(isRowFullyMapped);

  const updateRow = (rowId, field, value) => {
    setRows((prev) =>
      prev.map((row) => {
        if (row.id !== rowId) return row;

        const updatedRow = {
          ...row,
          [field]: value,
        };

        if (field === "biometricModality") {
          updatedRow.biometricProviderConfiguration = "";
          if (value) {
            fetchBioExtractorConfigs(rowId, value.toLowerCase());
          } else {
            setConfigFetchErrorByRowId((prev) => ({ ...prev, [rowId]: "" }));
            setActiveConfigs((prevConfigs) => ({ ...(prevConfigs || {}), [rowId]: [] }));
          }
        }

        if (field === "biometricProviderConfiguration") {
          const selectedConfig = getSelectedConfig(rowId, value);
          updatedRow.bioextractorProviderName = selectedConfig?.bioextractorProviderName || "";
          updatedRow.bioextractorProviderVersion = selectedConfig?.bioextractorProviderVersion || "";
        }

        return updatedRow;
      })
    );
  };

  const clearForm = () => {
    const id = nextRowId();
    setRows([{ id, ...EMPTY_MAPPING_ROW }]);
    setActiveConfigs({ [id]: [] });
    setConfigFetchErrorByRowId({});
    configReqVersionRef.current = {};
    setErrorCode("");
    setErrorMsg("");
  };

  const clickOnCancel = () => {
    navigate("/partnermanagement/policies/policies-list");
  };

  const isFormValid = () => {
    if (!policyDetails.partnerId || !policyDetails.policyId) return false;

    if (!policyDetails.mappingKey) return false;
    if (!rows.some(isRowFullyMapped)) return false;
    return rows.every((row) => isRowEmpty(row) || isRowFullyMapped(row));
  };

  const clickOnSaveAndProceed = () => {
    if (!isFormValid()) {
      setErrorMsg(t("mapBiometricExtractorProvider.validationMsg"));
      return;
    }
    setErrorCode("");
    setErrorMsg("");
    setShowSaveConfirm(true);
  };

  const executeSaveAndProceed = async () => {
    setShowSaveConfirm(false);

    if (!isFormValid()) {
      setErrorMsg(t("mapBiometricExtractorProvider.validationMsg"));
      return;
    }

    setIsSubmitClicked(true);

    setErrorCode("");
    setErrorMsg("");
    setDataLoaded(false);

    try {
      const mappedRows = getMappedRowsForSubmit();
      const request = createRequest({
        extractors: mappedRows.map((row) => {
          const selectedConfig = getSelectedConfig(row.id, row.biometricProviderConfiguration) || {};
          return {
            biometric: (row.biometricModality || "").toLowerCase(),
            attributeName: getAttributeName(row.biometricModality),
            extractorProvider: selectedConfig.bioextractorProviderName,
            extractorProviderVersion: selectedConfig.bioextractorProviderVersion,
          };
        }),
      },
        "mosip.pms.partners.bioextractors.request.post"
      );
      const timestamp = new Date().toISOString();
      request.requestTime = timestamp;

      const response = await HttpService.post(
        getPartnerManagerUrl(`/partner-policy-requests/${policyDetails.mappingKey}/bio-extractors-request`, process.env.NODE_ENV),
        request
      );

      if (response?.data?.response) {
        const selectedBioModalities = mappedRows.map((r) => r.biometricModality).filter(Boolean);
        const selectedBioProviderConfigurations = mappedRows
          .map((r) => r.biometricProviderConfiguration)
          .filter(Boolean);

        navigate("/partnermanagement/policies/map-credential-type", {
          state: {
            ...policyDetails,
            requestPayload: policyDetails.requestPayload,
            selectedBioModalities,
            selectedBioProviderConfigurations,
          },
        });
        return;
      }

      handleServiceErrors(response?.data, setErrorCode, setErrorMsg);
      setIsSubmitClicked(false);
    } catch (err) {
      if (err.response?.status && err.response.status !== 401) {
        setErrorMsg(err.toString());
      } else if (err.response?.status !== 401) {
        setErrorMsg(t("mapBiometricExtractorProvider.saveError"));
      }
      setIsSubmitClicked(false);
    } finally {
      setDataLoaded(true);
    }
  };

  return (
    <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll relative font-inter`}>
      {!dataLoaded && <LoadingIcon />}
      {dataLoaded && (
        <>
          {errorMsg && (
            <ErrorMessage
              id="map_bio_extractor_provider_error_msg"
              errorCode={errorCode}
              errorMessage={errorMsg}
              clickOnCancel={() => setErrorMsg("")}
            />
          )}
          <div className="flex-col mt-5">
            <Title title="mapBiometricExtractorProvider.title" subTitle="requestPolicy.requestPolicy" backLink="/partnermanagement/policies/policies-list" />
            <p
              id="map_bio_extractor_provider_mandatory_mapping_msg"
              className="mt-3 rounded-md border border-[#F7D18D] bg-[#FFF8EA] px-3 py-2 text-sm text-[#684B00]"
            >
              {t("requestPolicy.mandatoryMappingBanner")}
            </p>
            <div className="w-[100%] bg-snow-white mt-[1%] rounded-lg shadow-md">
              <div className="p-7">
                <p className="text-base text-[#3D4468]">
                  {t("requestPolicy.mandatoryFieldsMsg1")} <span className="text-crimson-red">*</span> {t("requestPolicy.mandatoryFieldsMsg2")}
                </p>

                <div className="grid grid-cols-2 gap-4 my-2 max-[450px]:grid-cols-1">
                  <div className="flex flex-col w-full max-[450px]:w-full">
                    <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                      {t("requestPolicy.partnerId")}
                      <span className="text-crimson-red mx-1">*</span>
                    </label>
                    <button
                      disabled
                      className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar"
                      type="button"
                    >
                      <span className="w-full break-words text-wrap text-start">
                        {policyDetails.partnerId || t("commons.partnersHelpText")}
                      </span>
                    </button>
                  </div>
                  <div className="flex flex-col w-full max-[450px]:w-full">
                    <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                      {t("requestPolicy.partnerType")}
                      <span className="text-crimson-red mx-1">*</span>
                    </label>
                    <button
                      disabled
                      className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar"
                      type="button"
                    >
                      <span className="w-full break-words text-wrap text-start">
                        {policyDetails.partnerType || t("commons.partnersHelpText")}
                      </span>
                    </button>
                  </div>
                </div>

                <div className="grid grid-cols-2 gap-4 my-2 max-[450px]:grid-cols-1">
                  <div className="flex flex-col w-full max-[450px]:w-full">
                    <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                      {t("requestPolicy.policyGroup")}
                      <span className="text-crimson-red mx-1">*</span>
                    </label>
                    <button
                      disabled
                      className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar"
                      type="button"
                    >
                      <span className="w-full break-words text-wrap text-start">
                        {policyDetails.policyGroupName || t("commons.partnersHelpText")}
                      </span>
                    </button>
                  </div>
                  <div className="flex flex-col w-full max-[450px]:w-full">
                    <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                      {t("requestPolicy.policyName")}
                      <span className="text-crimson-red mx-1">*</span>
                    </label>
                    <button
                      disabled
                      className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar"
                      type="button"
                    >
                      <span className="w-full break-words text-wrap text-start">
                        {policyDetails.policyName || t("commons.partnersHelpText")}
                      </span>
                    </button>
                  </div>
                </div>

                <div className="mt-5 border border-[#D5D8E3] rounded-md p-4">
                  <p className="text-sm font-semibold text-dark-blue mb-3">{t("mapBiometricExtractorProvider.mappingSectionTitle")}</p>

                  {rows.map((row, index) => {
                    const selectedConfig = getSelectedConfig(row.id, row.biometricProviderConfiguration) || {};
                    const styles = {
                      outerDiv: "!ml-0 !mb-0",
                      dropdownLabel: "!text-sm !mb-1",
                      dropdownButton: "!w-full min-h-10 !rounded-md !text-base !text-start",
                      selectionBox: "!top-10",
                    };

                    return (
                      <div key={`bio-mapping-row-${index}`} className="border border-[#E7E7E7] rounded-md p-4 mb-4">
                        <div className="grid grid-cols-2 gap-4 my-2 max-[450px]:grid-cols-1">
                          <div className="flex flex-col w-full">
                            <div className="w-full">
                              <DropdownComponent
                                fieldName="biometricModality"
                                dropdownDataList={getModalityDropdownDataForRow(row.id)}
                                onDropDownChangeEvent={(fieldName, selectedValue) => updateRow(row.id, fieldName, selectedValue)}
                                fieldNameKey="mapBiometricExtractorProvider.biometricModality*"
                                placeHolderKey="mapBiometricExtractorProvider.selectBiometricModality"
                                selectedDropdownValue={row.biometricModality}
                                styleSet={styles}
                                id={`map_bio_extractor_provider_modality_${index + 1}`}
                              />
                            </div>
                          </div>

                          <div className="flex flex-col w-full">
                            <div className="w-full">
                              <DropdownWithSearchComponent
                                fieldName="biometricProviderConfiguration"
                                dropdownDataList={getConfigDropdownByModality(row.id, row.biometricModality)}
                                onDropDownChangeEvent={(fieldName, selectedValue) => updateRow(row.id, fieldName, selectedValue)}
                                fieldNameKey="mapBiometricExtractorProvider.biometricProviderConfiguration*"
                                placeHolderKey="mapBiometricExtractorProvider.selectBiometricProviderConfiguration"
                                selectedDropdownValue={row.biometricProviderConfiguration}
                                searchKey="commons.search"
                                styleSet={styles}
                                id={`map_bio_extractor_provider_config_${index + 1}`}
                              />
                            </div>
                            {configFetchErrorByRowId[row.id] && (
                              <p className="mt-1 text-xs font-semibold text-crimson-red">
                                {configFetchErrorByRowId[row.id]}
                              </p>
                            )}
                          </div>
                        </div>

                        <div className="grid grid-cols-2 gap-4 my-2 max-[450px]:grid-cols-1">
                          <div className="flex flex-col w-full max-[450px]:w-full">
                            <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                              {t("bioExtractorConfig.providerName")}
                              <span className="text-crimson-red mx-1">*</span>
                            </label>
                            <button
                              disabled
                              className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar"
                              type="button"
                            >
                              <span className="w-full break-words text-wrap text-start">
                                {selectedConfig?.bioextractorProviderName || t("mapBiometricExtractorProvider.autoPopulatedValue")}
                              </span>
                            </button>
                          </div>
                          <div className="flex flex-col w-full max-[450px]:w-full">
                            <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                              {t("bioExtractorConfig.providerVersion")}
                              <span className="text-crimson-red mx-1">*</span>
                            </label>
                            <button
                              disabled
                              className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar"
                              type="button"
                            >
                              <span className="w-full break-words text-wrap text-start">
                                {selectedConfig?.bioextractorProviderVersion || t("mapBiometricExtractorProvider.autoPopulatedValue")}
                              </span>
                            </button>
                          </div>
                        </div>

                        <div className="flex w-full items-center mt-4">
                          <button
                            id={`map_bio_extractor_provider_add_more_btn_${index}`}
                            type="button"
                            className={`text-tory-blue text-sm font-semibold ${!canAddMoreRow ? "opacity-50 cursor-not-allowed" : ""}`}
                            disabled={!canAddMoreRow}
                            onClick={() => {
                              if (canAddMoreRow) {
                                const id = nextRowId();
                                setRows((prev) => [...prev, { id, ...EMPTY_MAPPING_ROW }]);
                                setActiveConfigs((prev) => ({ ...(prev || {}), [id]: [] }));
                              }
                            }}
                          >
                            + {t("mapBiometricExtractorProvider.addMore")}
                          </button>

                          {rows.length > 1 && (
                            <div className="ml-auto">
                              <button
                                type="button"
                                className="flex items-center gap-1 text-[#1447B2] text-sm font-medium"
                                onClick={() => {
                                  const rowIdToDelete = row.id;
                                  setRows((prev) => prev.filter((r) => r.id !== rowIdToDelete));
                                  setActiveConfigs((prev) => {
                                    const next = { ...(prev || {}) };
                                    delete next[rowIdToDelete];
                                    return next;
                                  });
                                  setConfigFetchErrorByRowId((prev) => {
                                    const next = { ...(prev || {}) };
                                    delete next[rowIdToDelete];
                                    return next;
                                  });
                                  delete configReqVersionRef.current[rowIdToDelete];
                                }}
                              >
                                <svg xmlns="http://www.w3.org/2000/svg" className="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                                  <path strokeLinecap="round" strokeLinejoin="round" d="M6 7h12M9 7V4h6v3M5 7h14l-1 14H6L5 7z" />
                                  <path strokeLinecap="round" strokeLinejoin="round" d="M10 11l4 4M14 11l-4 4" />
                                </svg>
                                {t("mapBiometricExtractorProvider.delete")}
                              </button>
                            </div>
                          )}
                        </div>
                      </div>
                    );
                  })}
                </div>
              </div>

              <div className="border bg-medium-gray" />
              <div className="flex flex-row px-[3%] py-5 justify-between">
                <button id="map_bio_extractor_provider_clear_btn" onClick={clearForm} className={`w-40 h-10 mr-3 border-[#1447B2] ${isLoginLanguageRTL ? "mr-2" : "ml-2"} border rounded-md bg-white text-tory-blue text-sm font-semibold`}>
                  {t("requestPolicy.clearForm")}
                </button>
                <div className="flex flex-row space-x-3 w-full md:w-auto justify-end">
                  <button id="map_bio_extractor_provider_cancel_btn" onClick={clickOnCancel} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>
                    {t("requestPolicy.cancel")}
                  </button>
                  <button
                    id="map_bio_extractor_provider_submit_btn"
                    disabled={!isFormValid()}
                    onClick={clickOnSaveAndProceed}
                    className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border rounded-md text-sm font-semibold ${
                      isFormValid()
                        ? "border-[#1447B2] bg-tory-blue text-white"
                        : "border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed"
                    }`}
                  >
                    {t("requestPolicy.saveAndProceed")}
                  </button>
                </div>
              </div>
            </div>
          </div>
        </>
      )}
      <BlockerPrompt blocker={blocker} message={t("unsavedChangesPopup.message")} />

      {showSaveConfirm && (
        <div
          className="fixed inset-0 z-[60] flex items-center justify-center bg-black bg-opacity-35 font-inter px-4"
          role="presentation"
          onClick={(e) => {
            if (e.target === e.currentTarget) {
              setShowSaveConfirm(false);
            }
          }}
        >
          <div
            role="dialog"
            aria-modal="true"
            aria-labelledby="map_bio_extractor_save_confirm_title"
            className="bg-white rounded-lg shadow-lg max-w-md w-full p-6"
          >
            <h2 id="map_bio_extractor_save_confirm_title" className="text-lg font-semibold text-dark-blue mb-3">
              {t("mapBiometricExtractorProvider.saveConfirmTitle")}
            </h2>
            <p className="text-sm text-[#3D4468] mb-6 leading-relaxed">{t("mapBiometricExtractorProvider.saveConfirmMessage")}</p>
            <div className="flex w-full flex-row flex-nowrap items-center justify-between gap-4">
              <button
                type="button"
                id="map_bio_extractor_save_confirm_cancel"
                onClick={() => setShowSaveConfirm(false)}
                className="shrink-0 min-w-[7rem] h-10 px-4 border border-[#1447B2] rounded-md bg-white text-tory-blue text-sm font-semibold"
              >
                {t("commons.cancel")}
              </button>
              <button
                type="button"
                id="map_bio_extractor_save_confirm_submit"
                onClick={executeSaveAndProceed}
                className="shrink-0 min-w-[7rem] h-10 px-4 border border-[#1447B2] rounded-md bg-tory-blue text-white text-sm font-semibold"
              >
                {t("requestPolicy.saveAndProceed")}
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default MapBiometricExtractorProvider;

