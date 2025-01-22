import HeaderNav from './HeaderNav.js';
import SideNav from './SideNav.js';
import Footer from './Footer.js';
import '../index.css';
import { getUserProfile } from '../services/UserProfileService.js';
import { useTranslation } from 'react-i18next';
import { useState, useEffect, useRef } from 'react';
import { logout, getPartnerManagerUrl, createRequest } from "../utils/AppUtils";
import { getAppConfig } from '../services/ConfigService.js';
import { HttpService } from "../services/HttpService";

function MainLayout({ children }) {
    const { i18n } = useTranslation();
    const [open, setOpen] = useState(false);
    const [showPrompt, setShowPrompt] = useState(false);
    const { t } = useTranslation();
    const [policyRequiredPartnerTypes, setPolicyRequiredPartnerTypes] = useState([]);
    const [partnerType, setPartnerType] = useState("");
    const timer = useRef(null);
    const promptTimer = useRef(null);
    const inActivityTimer = useRef(null);
    const inActivityPromptTimer = useRef(null);

    const INACTIVITY_TIMER = 'inActivityTimer';
    const INACTIVITY_PROMPT_TIMER = 'inActivityPromptTimer';

    const events = ["load", "click", "scroll", "keypress"];

    const handleLogoutTimer = () => {
        clearTimeout(timer.current);
        clearTimeout(promptTimer.current);

        timer.current = setTimeout(() => {
            setShowPrompt(true);
            promptTimer.current = setTimeout(() => {
                resetTimer();
                logout();
            }, inActivityPromptTimer.current);
        }, inActivityTimer.current);
    };

    const resetTimer = () => {
        clearTimeout(timer.current);
        clearTimeout(promptTimer.current);
        setShowPrompt(false);
    };

    const getTimerValues = async () => {
        try {
            const configData = await getAppConfig();
            // Convert minutes to milliseconds
            inActivityTimer.current = Number(configData[INACTIVITY_TIMER]) * 60 * 1000;
            inActivityPromptTimer.current = Number(configData[INACTIVITY_PROMPT_TIMER]) * 60 * 1000;
        } catch (error) {
            console.error("Error fetching or parsing app config for user session idle timers :", error);
            // Set default values or handle the error as needed
            inActivityTimer.current = 25 * 60 * 1000;
            inActivityPromptTimer.current = 5 * 60 * 1000;
        }
    };

    const getItemPerPage = async () => {
        try {
            const configData = await getAppConfig();
            const itemsPerPage = Number(configData['itemsPerPage']);
            localStorage.setItem('itemsPerPage', itemsPerPage);
        } catch (error) {
            console.error("Error fetching item per page value:", error);
        }
    }

    useEffect(() => {
        const langCode = getUserProfile() ? getUserProfile().langCode: null;
        if (langCode != null) {
            if (langCode === "ara") {
                document.body.dir = 'rtl';
                i18n.changeLanguage(langCode);
            }
            else {
                document.body.dir = 'ltr';
                i18n.changeLanguage(langCode);
            }
            i18n.changeLanguage(langCode);
        }

        const initializeItemsPerPage = async () => {
            await getItemPerPage()
        };

        const initialize = async () => {
            await getTimerValues();
            events.forEach(event => {
                window.addEventListener(event, resetTimerAndStartTimer);
            });
            handleLogoutTimer();
        };

        const resetTimerAndStartTimer = () => {
            resetTimer();
            handleLogoutTimer();
        };

        initialize();
        initializeItemsPerPage();

        return () => {
            events.forEach(event => {
                window.removeEventListener(event, resetTimerAndStartTimer);
            });
            clearTimeout(timer.current);
            clearTimeout(promptTimer.current);
        };
    }, [i18n]);

    useEffect(() => {
        async function fetchData() {
            try {
                const userProfile = getUserProfile();
                const verifyEmailRequest = createRequest({
                    "emailId": userProfile.email
                });
                const response = await HttpService.put(
                    getPartnerManagerUrl('/partners/email/verify', process.env.NODE_ENV), verifyEmailRequest);
                if (response?.data?.response) {
                    const resData = response.data.response;
                    setPolicyRequiredPartnerTypes(resData.policyRequiredPartnerTypes);
                    setPartnerType(userProfile.partnerType);
                }

            } catch (err) {
                console.log('email verify error in MainLayout:', err);
            }
        }
        fetchData();
    }, []);

    return (
        <div className="flex flex-col justify-evenly bg-anti-flash-white font-inter">
            <HeaderNav open={open} setOpen={setOpen}></HeaderNav>
            <div className='flex flex-row justify-stretch h-full'>
                <SideNav open={open} policyRequiredPartnerTypes={policyRequiredPartnerTypes} partnerType={partnerType}></SideNav>
                {children}
            </div>
            <Footer></Footer>
            {showPrompt && (
                <div className="fixed inset-0 w-full flex items-center justify-center bg-black bg-opacity-50 z-50 font-inter">
                    <div className="bg-white w-1/3 break-words text-sm h-[15%] min-h-fit rounded-xl shadow-lg flex justify-center items-center p-5">
                        {t('commons.sessionIdleLogout')}
                    </div>
                </div>
            )}
        </div>
    );
}

export default MainLayout;