import React from "react";
import { getUserProfile } from "../../services/UserProfileService";
import { isLangRTL} from "../../utils/AppUtils";
import Title from "../common/Title";

function ManageFtmChipCertificate() {
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    return (
        <>
            <div className={`flex-col w-full p-5 bg-anti-flash-white h-full font-inter break-all break-normal max-[450px]:text-sm mb-[2%] ${isLoginLanguageRTL ? "mr-24 ml-1" : "ml-24 mr-1"} overflow-x-scroll`}>
                <div className="flex justify-between mb-3">
                    <Title title='manageFtmChipCertificate.manageFtmChipCertificate' subTitle='manageFtmChipCertificate.listOfFtmChipDetails' backLink='/partnermanagement/ftmChipProviderServices/ftmList' ></Title>
                </div>
            </div>
        </>
    )
}

export default ManageFtmChipCertificate;