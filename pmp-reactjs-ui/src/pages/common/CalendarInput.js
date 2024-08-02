import { useRef, useEffect } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import { isLangRTL, handleMouseClickForDropdown } from '../../utils/AppUtils';
import { getUserProfile } from '../../services/UserProfileService';
import { useTranslation } from 'react-i18next';
import calendar_icon from '../../svg/calendar_icon.svg'
import Information from './fields/Information';

function CalendarInput({ showCalendar, addInfoIcon, setShowCalender, label, onChange, value, styles }) {

  const { t } = useTranslation();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

  const calendarRef = useRef(null);

  useEffect(() => {
    handleMouseClickForDropdown(calendarRef, () =>
      setShowCalender(false))
  }, [calendarRef]);

  const openCalendar = () => {
    setShowCalender(!showCalendar);
  };

  return (
    <div className="flex flex-col w-[48%] overflow-x-auto">
      <label className={`flex items-center gap-x-1 text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
        <p className={`mb-0.5`}>{label}</p>
        {addInfoIcon && (
          <Information infoKey={t('addSbis.addSbiInfoKey')} />
        )}
      </label>
      <div className={`flex items-center justify-between h-10 px-2 border border-[#707070] rounded-md text-md text-dark-blue bg-white text-start cursor-pointer`}>
        <span className="text-start ">
          {value.toLocaleDateString() ? value.toLocaleDateString()
            : <p className={`text-gray-400`}>{t('addSbis.selectDateAndTime')}</p>
          }
        </span>
        {showCalendar &&
          <Calendar inputRef={calendarRef}
            onClickDay={openCalendar}
            onChange={onChange}
            value={value}
            defaultView="month"
            calendarType={`${isLoginLanguageRTL ? "islamic" : "iso8601"}`}
            locale={`${isLoginLanguageRTL ? "ar-AR" : "en-EN"}`}
            className={styles}
          />
        }
        <img onClick={openCalendar} src={calendar_icon} className={`h-[48%] mb-1 ${isLoginLanguageRTL ? "mr-3" : "ml-3"} text-blue-500 font-bold text-sm`} />
      </div>
    </div>
  )
}

export default CalendarInput;