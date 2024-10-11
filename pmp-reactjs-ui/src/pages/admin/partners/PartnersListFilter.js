import { useState, useEffect } from "react";
import DropdownComponent from "../../common/fields/DropdownComponent.js";
import { useTranslation } from "react-i18next";
import { createDropdownData } from "../../../utils/AppUtils.js";

function PartnerListFilter({ filteredPartnersData, onFilterChange }) {
  console.log(filteredPartnersData)
  const { t } = useTranslation();
  const [partnerIdData, setPartnerIdData] = useState([]);
  const [partnerTypeData, setPartnerTypeData] = useState([]);
  const [organisationNameData, setOrganisationNameData] = useState([]);
  const [emailIdData, setEmailIdData] = useState([]);
  const [policyGroupData, setPolicyGroupData] = useState([]);
  const [statusData, setStatusData] = useState([]);

  console.log(partnerIdData)
  console.log(partnerTypeData)
  useEffect(() => {
    const fetchData = async () => {
      setPartnerIdData(
        createDropdownData(
          "partnerID",
          "",
          true,
          filteredPartnersData,
          t,
          t("partnerList.selectPartnerId")
        )
      );

      setPartnerTypeData(
        createDropdownData(
          "partnerType",
          "",
          true,
          filteredPartnersData,
          t,
          t("partnerList.selectPartnerType")
        )
      );

      setOrganisationNameData(
        createDropdownData(
          "orgName",
          "",
          true,
          filteredPartnersData,
          t,
          t("partnerList.selectOrganisation")
        )
      );

      setEmailIdData(
        createDropdownData(
          "emailID",
          "",
          true,
          filteredPartnersData,
          t,
          t("partnerList.selectEmailAddress")
        )
      );

      setPolicyGroupData(
        createDropdownData(
          "policyGroup",
          "",
          true,
          filteredPartnersData,
          t,
          t("partnerList.selectPolicyGroup")
        )
      );

      setStatusData(
        createDropdownData(
          "status",
          "",
          true,
          filteredPartnersData,
          t,
          t("rootTrustCertificate.selectStatus")
        )
      );
    };
    fetchData();
  }, [t]);

  const onFilterChangeEvent = (fieldName, selectedFilter) => {
    onFilterChange(fieldName, selectedFilter);
  };

  const styles = {
    dropdownButton: "!text-[#343434] min-w-72",
  };

  return (
    <>
      <div className="flex w-full p-2 justify-start bg-[#F7F7F7] flex-wrap">
        <DropdownComponent
          fieldName="partnerID"
          dropdownDataList={partnerIdData}
          onDropDownChangeEvent={onFilterChangeEvent}
          fieldNameKey="partnerList.partnerId"
          placeHolderKey="partnerList.selectPartnerId"
          styleSet={styles}
          isPlaceHolderPresent={true}
          id="partner_id_filter"
        ></DropdownComponent>
        <DropdownComponent
          fieldName="partnerType"
          dropdownDataList={partnerTypeData}
          onDropDownChangeEvent={onFilterChangeEvent}
          fieldNameKey="partnerList.partnerType"
          placeHolderKey="partnerList.selectPartnerType"
          styleSet={styles}
          isPlaceHolderPresent={true}
          id="partner_type_filter"
        ></DropdownComponent>
        <DropdownComponent
          fieldName="orgName"
          dropdownDataList={organisationNameData}
          onDropDownChangeEvent={onFilterChangeEvent}
          fieldNameKey="partnerList.organisation"
          placeHolderKey="partnerList.selectOrganisation"
          styleSet={styles}
          isPlaceHolderPresent={true}
          id="partner_organisation_filter"
        ></DropdownComponent>
        <DropdownComponent
          fieldName="emailID"
          dropdownDataList={emailIdData}
          onDropDownChangeEvent={onFilterChangeEvent}
          fieldNameKey="partnerList.email"
          placeHolderKey="partnerList.selectEmailAddress"
          styleSet={styles}
          isPlaceHolderPresent={true}
          id="email_address_filter"
        ></DropdownComponent>
        <DropdownComponent
          fieldName="policyGroup"
          dropdownDataList={policyGroupData}
          onDropDownChangeEvent={onFilterChangeEvent}
          fieldNameKey="partnerList.policyGroup"
          placeHolderKey="partnerList.selectPolicyGroup"
          styleSet={styles}
          isPlaceHolderPresent={true}
          id="policy_group_filter"
        ></DropdownComponent>
        <DropdownComponent
          fieldName="status"
          dropdownDataList={statusData}
          onDropDownChangeEvent={onFilterChangeEvent}
          fieldNameKey="partnerList.status"
          placeHolderKey="partnerList.selectStatus"
          styleSet={styles}
          isPlaceHolderPresent={true}
          id="partner_status_filter"
        ></DropdownComponent>
      </div>
    </>
  );
}

export default PartnerListFilter;
