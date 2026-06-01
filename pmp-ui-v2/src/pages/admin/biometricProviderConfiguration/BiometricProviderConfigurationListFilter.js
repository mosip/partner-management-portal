import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import PropTypes from "prop-types";
import TextInputComponent from "../../common/fields/TextInputComponent";
import DropdownComponent from "../../common/fields/DropdownComponent";
import {
  createDropdownData,
  getFilterDropdownStyle,
  getFilterTextFieldStyle,
  isLangRTL,
  validateInputRegex,
} from "../../../utils/AppUtils";
import { getUserProfile } from "../../../services/UserProfileService";
import { getAppConfig } from "../../../services/ConfigService";

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

const parseModalitiesFromConfig = (configData) => {
  const map = parseModalityAttributeNameMapFromConfig(configData);
  const modalitiesFromMap = Object.keys(map)
    .map((k) => String(k || "").trim())
    .filter(Boolean)
    .map((m) => m.toLowerCase());

  if (modalitiesFromMap.length > 0) {
    return Array.from(new Set(modalitiesFromMap));
  }

  return Array.from(new Set(parseCsvOrArray(configData?.allowedBioextractorModalities).map((m) => String(m).toLowerCase())));
};

function BiometricProviderConfigurationListFilter({ onApplyFilter }) {
  const { t } = useTranslation();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
  const [modalityOptions, setModalityOptions] = useState([]);
  const [filters, setFilters] = useState({
    configName: "",
    bioextractorProviderName: "",
    bioextractorProviderVersion: "",
    bioModality: "",
  });
  const [invalidConfigName, setInvalidConfigName] = useState("");
  const [invalidProviderName, setInvalidProviderName] = useState("");
  const [invalidProviderVersion, setInvalidProviderVersion] = useState("");

  useEffect(() => {
    let cancelled = false;
    (async () => {
      try {
        const configData = await getAppConfig();
        if (cancelled) return;
        const modalities = parseModalitiesFromConfig(configData).map((m) => ({ modality: m }));
        setModalityOptions(
          createDropdownData(
            "modality",
            "",
            true,
            modalities,
            t,
            t("bioExtractorConfig.selectBiometricModality")
          )
        );
      } catch (error) {
        if (cancelled) return;
        console.error("Error fetching modalities from system-config:", error);
        setModalityOptions(
          createDropdownData("modality", "", true, [], t, t("bioExtractorConfig.selectBiometricModality"))
        );
      }
    })();

    return () => {
      cancelled = true;
    };
  }, [t]);

  const onFilterChangeEvent = (fieldName, selectedFilter) => {
    setFilters((prevFilters) => ({
      ...prevFilters,
      [fieldName]: selectedFilter,
    }));
    if (fieldName === "configName") {
      validateInputRegex(selectedFilter, setInvalidConfigName, t);
    }
    if (fieldName === "bioextractorProviderName") {
      validateInputRegex(selectedFilter, setInvalidProviderName, t);
    }
    if (fieldName === "bioextractorProviderVersion") {
      validateInputRegex(selectedFilter, setInvalidProviderVersion, t);
    }
  };

  const areFiltersEmpty = () => {
    return (
      Object.values(filters).every((value) => value === "") ||
      invalidConfigName ||
      invalidProviderName ||
      invalidProviderVersion
    );
  };

  return (
    <div className="flex w-full p-3 justify-start bg-[#F7F7F7] flex-wrap">
      <TextInputComponent
        fieldName="configName"
        onTextChange={onFilterChangeEvent}
        fieldNameKey="bioExtractorConfig.configurationName"
        placeHolderKey="bioExtractorConfig.enterConfigurationName"
        styleSet={getFilterTextFieldStyle()}
        id="bio_extractor_config_name_filter"
        maxLength={128}
        inputError={invalidConfigName}
      />
      <TextInputComponent
        fieldName="bioextractorProviderName"
        onTextChange={onFilterChangeEvent}
        fieldNameKey="bioExtractorConfig.providerName"
        placeHolderKey="bioExtractorConfig.enterProviderName"
        styleSet={getFilterTextFieldStyle()}
        id="bio_extractor_provider_name_filter"
        maxLength={128}
        inputError={invalidProviderName}
      />
      <TextInputComponent
        fieldName="bioextractorProviderVersion"
        onTextChange={onFilterChangeEvent}
        fieldNameKey="bioExtractorConfig.providerVersion"
        placeHolderKey="bioExtractorConfig.enterProviderVersion"
        styleSet={getFilterTextFieldStyle()}
        id="bio_extractor_provider_version_filter"
        maxLength={36}
        inputError={invalidProviderVersion}
      />
      <DropdownComponent
        fieldName="bioModality"
        dropdownDataList={modalityOptions}
        onDropDownChangeEvent={onFilterChangeEvent}
        fieldNameKey="bioExtractorConfig.biometricModality"
        placeHolderKey="bioExtractorConfig.selectBiometricModality"
        styleSet={getFilterDropdownStyle()}
        isPlaceHolderPresent={true}
        id="bio_modality_filter"
      />
      <div className={`mt-6 mr-6 ${isLoginLanguageRTL ? "mr-auto" : "ml-auto"}`}>
        <button
          id="apply_bio_extractor_config_filter_btn"
          onClick={() => onApplyFilter(filters)}
          type="button"
          disabled={areFiltersEmpty()}
          className={`h-10 text-sm font-semibold px-7 text-white rounded-md ml-6 ${areFiltersEmpty() ? "bg-[#A5A5A5] cursor-auto" : "bg-tory-blue"}`}
        >
          {t("partnerList.applyFilter")}
        </button>
      </div>
    </div>
  );
}

BiometricProviderConfigurationListFilter.propTypes = {
  onApplyFilter: PropTypes.func.isRequired,
};

export default BiometricProviderConfigurationListFilter;
