import { useRef, useEffect } from 'react';
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css';
import { isLangRTL, handleMouseClickForDropdown } from '../../utils/AppUtils';
import { getUserProfile } from '../../services/UserProfileService';
import Information from './fields/Information';
import { format } from 'date-fns';
import PropTypes from 'prop-types';

function CalendarInput({ isUsedAsFilter, showCalendar, addInfoIcon, infoKey, infoKey1, setShowCalender, placeholderText, label, onChange, styleSet, selectedDateStr, containsAsterisk, id, disabled}) {
  const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

  const calendarRef = useRef(null);

  useEffect(() => {
    handleMouseClickForDropdown(calendarRef, () =>
      setShowCalender(false))
  }, [calendarRef]);

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
      <label id={id + '_label'} className={`flex items-center text-dark-blue text-sm mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
        <p className={`mb-0.5 font-semibold`}>{label}{containsAsterisk && <span className={`text-crimson-red mx-1`}>*</span>}</p>
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
          className={`${styleSet?.datePicker || ''} w-full px-2 py-3 border border-[#707070] rounded-[4px] text-base text-dark-blue ${disabled ? 'bg-platinum-gray cursor-not-allowed' : 'bg-white'} leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar`}
          wrapperClassName="w-full"
          isClearable={isUsedAsFilter? true : false}
          disabled={disabled}
        />
      </div>
    </div>
  )
}

CalendarInput.propTypes = {
  isUsedAsFilter: PropTypes.bool,
  showCalendar: PropTypes.bool.isRequired,
  addInfoIcon: PropTypes.bool,
  infoKey: PropTypes.string,
  infoKey1: PropTypes.string,
  setShowCalender: PropTypes.func.isRequired,
  placeholderText: PropTypes.string,
  label: PropTypes.string.isRequired,
  onChange: PropTypes.func.isRequired,
  styleSet: PropTypes.object.isRequired,
  selectedDateStr: PropTypes.string.isRequired,
  containsAsterisk: PropTypes.bool,
  id: PropTypes.string.isRequired,
  disabled: PropTypes.bool,
};

export default CalendarInput;