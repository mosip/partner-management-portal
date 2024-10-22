import { useState, useEffect } from "react";
import DropdownComponent from "../../common/fields/DropdownComponent.js";
import TextInputComponent from "../../common/fields/TextInputComponent.js";
import { useTranslation } from "react-i18next";
import { createDropdownData } from "../../../utils/AppUtils.js";

function PartnerListFilter({ filteredPartnersData, onFilterChange }) {
  const { t } = useTranslation();

  const [partnerType, setPartnerType] = useState([]);
  const [status, setStatus] = useState([]);
  const [certUploadStatus, setCertUploadStatus] = useState([]);
  const [filters, setFilters] = useState({
    partnerType: "",
    status: "",
    orgName: "",
    emailID: "",
    certUploadStatus: "",
    policyGroup: ""
  });

  useEffect(() => {
    setPartnerType(
      createDropdownData("partnerType", "", true, filteredPartnersData, t, t("partnerList.selectPartnerType"))
    );
    setStatus(
      createDropdownData("status", "", true, filteredPartnersData, t, t("partnerList.selectStatus"))
    );
    setCertUploadStatus(
      createDropdownData("certificateUploadStatus", "", true, filteredPartnersData, t, t("partnerList.selectCertUploadStatus"))
    );
  }, [filteredPartnersData, t]);

  const onFilterChangeEvent = (fieldName, selectedFilter) => {
    setFilters((prevFilters) => ({
      ...prevFilters,
      [fieldName]: selectedFilter
    }));
  };

  const styles = {
    dropdownButton: "!text-[#343434] min-w-72",
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
        placeHolderKey="partnerList.searchPartnerId"
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
      <div className="ml-auto mt-6 mr-6">
        <button
          id="apply_filter__btn"
          onClick={() => onFilterChange(filters)}
          type="button"
          className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md ml-6"
        >
          {t("partnerList.applyFilter")}
        </button>
      </div>
    </div>
  );
}

export default PartnerListFilter;
