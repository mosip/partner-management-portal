import { isLangRTL } from "../../utils/AppUtils";
import { getUserProfile } from "../../services/UserProfileService";

function GenerateApiKey() {
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    
    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
            <p>Generate API Key....</p>
        </div>
    )
}

export default GenerateApiKey;