import { useRef, useEffect } from 'react';
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css';
import { isLangRTL, handleMouseClickForDropdown } from '../../utils/AppUtils';
import { getUserProfile } from '../../services/UserProfileService';
import { useTranslation } from 'react-i18next';
import calendar_icon from '../../svg/calendar_icon.svg'
import Information from './fields/Information';

function CalendarInput({ showCalendar, addInfoIcon, setShowCalender, label, onChange, selectedDateStr, styles }) {
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

  const onDateChange = (newdate) => {
    const newDateStr = newdate.toISOString();
    console.log(`onDateChange ${newDateStr}`);
    setShowCalender(!showCalendar);
    onChange(newDateStr);
  }
  
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
          {selectedDateStr !== "" ? new Date(selectedDateStr).toDateString()
           : <p className={`text-gray-400`}>{t('addSbis.selectDate')}</p>
          }
        </span>
        {showCalendar &&
          <DatePicker 
            selected={selectedDateStr == "" ? new Date() : new Date(selectedDateStr)}
            onChange={(date) => onDateChange(date)}
            className={styles}
             />
        }
        <img onClick={openCalendar} src={calendar_icon} className={`h-[48%] mb-1 ${isLoginLanguageRTL ? "mr-3" : "ml-3"} text-blue-500 font-bold text-sm`} />
      </div>
    </div>
  )
}

export default CalendarInput;