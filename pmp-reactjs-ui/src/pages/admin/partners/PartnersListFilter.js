import { useState, useEffect } from "react";
import DropdownComponent from "../../common/fields/DropdownComponent.js";
import TextInputComponent from "../../common/fields/TextInputComponent.js";
import { useTranslation } from "react-i18next";
import { createDropdownData, createRequest, getPartnerManagerUrl, handleServiceErrors } from "../../../utils/AppUtils.js";
import { isLangRTL } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import { HttpService } from "../../../services/HttpService.js";

function PartnerListFilter({ onApplyFilter, setErrorCode, setErrorMsg }) {
  const { t } = useTranslation();

  const [partnerType, setPartnerType] = useState([]);
  const [status, setStatus] = useState([]);
  const [certUploadStatus, setCertUploadStatus] = useState([]);
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const [certUploadStatusDropdownData, setCertUploadStatusDropdownData] = useState([
    { certificateUploadStatus: 'uploaded' },
    { certificateUploadStatus: 'not_uploaded' }
  ]);
  const [statusDropdownData, setStatusDropdownData] = useState([
    { status: 'active' },
    { status: 'deactivated' }
  ]);
  const [filters, setFilters] = useState({
    partnerId: "",
    partnerType: "",
    status: "",
    orgName: "",
    emailID: "",
    certUploadStatus: "",
    policyGroup: ""
  });

  useEffect(() => {
    const fetchData = async () => {
      const partnerTypeDropdownData = await fetchPartnerTypeData();
      setPartnerType(
        createDropdownData("partnerType", "", true, partnerTypeDropdownData, t, t("partnerList.selectPartnerType"))
      );
      setStatus(
        createDropdownData("status", "", true, statusDropdownData, t, t("partnerList.selectStatus"))
      );
      setCertUploadStatus(
        createDropdownData("certificateUploadStatus", "", true, certUploadStatusDropdownData, t, t("partnerList.selectCertUploadStatus"))
      );
    };

    fetchData();
  }, [t]);

  async function fetchPartnerTypeData() {
    const request = createRequest({
      "filters": [],
      "pagination": { "pageFetch": 100, "pageStart": 0 },
      "sort": []
    });

    try {
      const response = await HttpService.post(
        getPartnerManagerUrl(`/partners/partnertype/search`, process.env.NODE_ENV),
        request
      );

      if (response && response.data) {
        const responseData = response.data;
        if (responseData.response && responseData.response.data) {
          const partnerTypeData = responseData.response.data.map(item => ({
            partnerType: item.code
          }));
          return partnerTypeData;
        } else {
          handleServiceErrors(responseData, setErrorCode, setErrorMsg);
          return [];
        }
      } else {
        setErrorMsg(t('partnerList.errorInPartnersList'));
        return [];
      }
    } catch (err) {
      setErrorMsg(err.message || t('partnerList.errorInPartnersList'));
      console.error("Error fetching partner type data: ", err);
      return [];
    }
  }


  const onFilterChangeEvent = (fieldName, selectedFilter) => {
    setFilters((prevFilters) => ({
      ...prevFilters,
      [fieldName]: selectedFilter
    }));
  };

  const areFiltersEmpty = () => {
    return Object.values(filters).every(value => value === "");
  };

  const styles = {
    dropdownButton: "min-w-72",
  };

  return (
    <div className="flex w-full p-2 justify-start bg-[#F7F7F7] flex-wrap">
      <TextInputComponent
        fieldName="partnerId"
        onTextChange={onFilterChangeEvent}
        fieldNameKey="partnerList.partnerId"
        placeHolderKey="partnerList.searchPartnerId"
        styleSet={styles}
        id="partner_id_filter"
      />
      <DropdownComponent
        fieldName="partnerType"
        dropdownDataList={partnerType}
        onDropDownChangeEvent={onFilterChangeEvent}
        fieldNameKey="partnerList.partnerType"
        placeHolderKey="partnerList.selectPartnerType"
        styleSet={styles}
        isPlaceHolderPresent={true}
        id="partner_type_filter"
      />
      <TextInputComponent
        fieldName="orgName"
        onTextChange={onFilterChangeEvent}
        fieldNameKey="partnerList.organisation"
        placeHolderKey="partnerList.searchOrganisation"
        styleSet={styles}
        id="partner_organisation_filter"
      />
      <TextInputComponent
        fieldName="policyGroup"
        onTextChange={onFilterChangeEvent}
        fieldNameKey="partnerList.policyGroup"
        placeHolderKey="partnerList.searchPolicyGroup"
        styleSet={styles}
        id="policy_group_filter"
      />
      <TextInputComponent
        fieldName="emailID"
        onTextChange={onFilterChangeEvent}
        fieldNameKey="partnerList.email"
        placeHolderKey="partnerList.searchEmailAddress"
        styleSet={styles}
        id="email_address_filter"
      />
      <DropdownComponent
        fieldName="certUploadStatus"
        dropdownDataList={certUploadStatus}
        onDropDownChangeEvent={onFilterChangeEvent}
        fieldNameKey="partnerList.certUploadStatus"
        placeHolderKey="partnerList.selectCertUploadStatus"
        styleSet={styles}
        isPlaceHolderPresent={true}
        id="cert_upload_status_filter"
      />
      <DropdownComponent
        fieldName="status"
        dropdownDataList={status}
        onDropDownChangeEvent={onFilterChangeEvent}
        fieldNameKey="partnerList.status"
        placeHolderKey="partnerList.selectStatus"
        styleSet={styles}
        isPlaceHolderPresent={true}
        id="status_filter"
      />
      <div className={`mt-6 mr-6 ${isLoginLanguageRTL ? "mr-auto" : "ml-auto"}`}>
        <button
          id="apply_filter__btn"
          onClick={() => onApplyFilter(filters)}
          type="button"
          disabled={areFiltersEmpty()}
          className={`h-10 text-sm font-semibold px-7 text-white rounded-md ml-6 
          ${areFiltersEmpty() ? 'bg-[#A5A5A5] cursor-not-allowed' : 'bg-tory-blue'}`}
        >
          {t("partnerList.applyFilter")}
        </button>
      </div>
    </div>
  );
}

export default PartnerListFilter;
