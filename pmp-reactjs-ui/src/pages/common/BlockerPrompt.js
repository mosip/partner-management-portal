import React from 'react';
import { useTranslation } from "react-i18next";

const BlockerPrompt = ({ blocker }) => {
  const { t } = useTranslation();

  return blocker.state === "blocked" ? (
    <div className="fixed min-w-36 h-full inset-0 w-full flex flex-col z-50 font-inter">
      <div className="bg-white w-fit mx-auto rounded-xl justify-center shadow-lg p-3 pt-4 text-sm">
        <p className="text-center">{t('blockerMessage.description')}</p>
        <div className="flex justify-center pt-2">
          <button className="w-24 h-9 mx-2 my-1 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold" onClick={() => blocker.proceed()}>
            {t('blockerMessage.proceed')}
          </button>
          <button className="w-24 h-9 mx-2 my-1 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold" onClick={() => blocker.reset()}>
            {t('blockerMessage.cancel')}
          </button>
        </div>
      </div>
    </div>
  ) : null;
};

export default BlockerPrompt;
