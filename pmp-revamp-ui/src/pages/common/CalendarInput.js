import { useRef, useEffect } from 'react';
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css';
import { isLangRTL, handleMouseClickForDropdown } from '../../utils/AppUtils';
import { getUserProfile } from '../../services/UserProfileService';
import { useTranslation } from 'react-i18next';
import Information from './fields/Information';
import { format } from 'date-fns';

function CalendarInput({ isUsedAsFilter, showCalendar, addInfoIcon, infoKey, infoKey1, setShowCalender, placeholderText, label, onChange, styleSet, selectedDateStr, containsAsterisk, id}) {
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

  const onDateChange = (newDate) => {
    let formattedDate = "";
  
    if (newDate) {
      if (isUsedAsFilter) {
        formattedDate = format(newDate, 'yyyy-MM-dd');
      } else {
        formattedDate = newDate.toISOString();
      }
    }
  
    console.log(`onDateChange ${formattedDate}`);
    onChange(formattedDate);
    setShowCalender(false);
  };  
  
  return (
    <div className={`flex flex-col ${styleSet?.outerDiv || ''} overflow-x-auto`}>
      <label className={`flex items-center text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
        <p className={`mb-0.5`}>{label}{containsAsterisk && <span className={`text-crimson-red mx-1`}>*</span>}</p>
        {addInfoIcon && (
          <Information infoKey={infoKey} infoKey1={infoKey1} id={id + '_info'}/>
        )}
      </label>
      <div id="datePicker" className="w-full">
        <DatePicker
          id={id}
          selected={selectedDateStr === "" ? (isUsedAsFilter ? null : new Date()) : new Date(selectedDateStr)}
          onChange={(date) => onDateChange(date)}
          placeholderText={placeholderText}
          dateFormat="MM/dd/yyyy"
          className={`${styleSet?.datePicker || ''} w-full px-2 py-3 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar`}
          wrapperClassName="w-full"
          isClearable={isUsedAsFilter? true : false}
        />
      </div>
    </div>
  )
}

export default CalendarInput;