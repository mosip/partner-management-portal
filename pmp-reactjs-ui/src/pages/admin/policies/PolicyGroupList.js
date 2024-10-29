import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import {
    isLangRTL
} from '../../../utils/AppUtils';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from "../../common/LoadingIcon";
import rectangleGrid from '../../../svg/rectangle_grid.svg';
import Title from '../../common/Title.js';
import PoliciesTab from './PoliciesTab.js';

function PolicyGroupList() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [activePolicyGroup, setActivePolicyGroup] = useState(true);
    const [activeAuthPolicy, setActiveAuthPolicy] = useState(false);
    const [activeDataSharePolicy, setActiveDataSharePolicy] = useState(false);
    const [policyGroupList, setPolicyGroupList] = useState([]);

    const createPolicyGroup = () => {
        navigate('/partnermanagement/admin/policyGroup/createPolicyGroup');
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter overflow-x-scroll`}>
            { !dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            { dataLoaded && (
                <>
                    { errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}/>
                    )}
                    <div className="flex-col mt-7">
                        <div className="flex justify-between mb-5">
                            <Title title='policyGroupList.policies' backLink='/partnermanagement' ></Title>
                            {policyGroupList.length > 0 ?
                                <button id='create_policy_group_btn' type="button" className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md">
                                    {t('policyGroupList.createPolicyGroup')}
                                </button>
                                : null
                            }
                        </div>
                        <PoliciesTab
                            activePolicyGroup={activePolicyGroup}
                            setActivePolicyGroup={setActivePolicyGroup}
                            activeAuthPolicy={activeAuthPolicy}
                            setActiveAuthPolicy={setActiveAuthPolicy}
                            activeDataSharePolicy={activeDataSharePolicy}
                            setActiveDataSharePolicy={setActiveDataSharePolicy}>
                        </PoliciesTab>
                        { policyGroupList.length === 0 
                            ?
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                { activePolicyGroup && (
                                    <div className="flex justify-between py-2 pt-4 text-sm font-semibold text-[#6F6E6E]">
                                        <div className={`flex w-full justify-between`}>
                                            <h6 className="px-2 mx-2">{t('policyGroupList.policyGroupId')}</h6>
                                            <h6 className="px-2 mx-2">{t('policyGroupList.policyGroupName')}</h6>
                                            <h6 className="px-2 mx-2">{t('policyGroupList.policyGroupDescription')}</h6>
                                            <h6 className="px-2 mx-2">{t('policyGroupList.status')}</h6>
                                            <h6 className="px-2 mx-2">{t('policyGroupList.action')}</h6>
                                        </div>
                                    </div>
                                )}
                                <hr className="h-px mx-3 bg-gray-200 border-0" />
                                <div className="flex items-center justify-center p-24">
                                    <div className="flex flex-col justify-center">
                                        <img src={rectangleGrid} alt="" />
                                        {activePolicyGroup &&
                                            (<button id='create_policy_group_btn' type="button" onClick={createPolicyGroup}
                                                className={`text-white font-semibold mt-8 bg-tory-blue rounded-md text-sm mx-8 py-3`}>
                                                {t('policyGroupList.createPolicyGroup')}
                                            </button>)
                                        }
                                    </div>
                                </div>
                            </div>
                            : null
                        }
                    </div>
                </>
            )}
        </div>
    )

}
export default PolicyGroupList;