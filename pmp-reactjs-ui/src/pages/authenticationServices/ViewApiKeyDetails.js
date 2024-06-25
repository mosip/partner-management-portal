import { isLangRTL } from "../../utils/AppUtils";
import { getUserProfile } from "../../services/UserProfileService";

function ViewApiKeyDetails() {
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    
    return (
        <>
        <div className={`flex-col w-full p-4 bg-anti-flash-white h-full font-inter break-all max-[450px]:text-sm mb-[2%] ${isLoginLanguageRTL ? "mr-[7%]" : "ml-[7%]"} overflow-x-scroll`}>
            <p>View API Key Details...</p>
        </div>
        </>
    )
}

export default ViewApiKeyDetails;