import { useRef, useEffect } from 'react';
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css';
import { isLangRTL, handleMouseClickForDropdown } from '../../utils/AppUtils';
import { getUserProfile } from '../../services/UserProfileService';
import { useTranslation } from 'react-i18next';
import calendar_icon from '../../svg/calendar_icon.svg'
import Information from './fields/Information';

function CalendarInput({ showCalendar, addInfoIcon, infoKey, infoKey1, setShowCalender, label, onChange, selectedDateStr, containsAsterisk, id}) {
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
      <label className={`flex items-center text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
        <p className={`mb-0.5`}>{label}{containsAsterisk && <span className={`text-crimson-red mx-1`}>*</span>}</p>
        {addInfoIcon && (
          <Information infoKey={infoKey} infoKey1={infoKey1} id={id + '_info'}/>
        )}
      </label>
      <div id="datePicker" className="w-full">
        <DatePicker
          id={id}
          selected={selectedDateStr === "" ? new Date() : new Date(selectedDateStr)}
          onChange={(date) => onDateChange(date)}
          dateFormat="MM/dd/yyyy"
          className="h-10 w-full px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
          wrapperClassName="w-full" 
        />
      </div>
    </div>
  )
}

export default CalendarInput;