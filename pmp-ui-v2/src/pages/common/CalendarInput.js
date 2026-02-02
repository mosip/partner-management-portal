import { useRef, useEffect } from 'react';
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css';
import { isLangRTL, handleMouseClickForDropdown } from '../../utils/AppUtils';
import { getUserProfile } from '../../services/UserProfileService';
import Information from './fields/Information';
import { format } from 'date-fns';
import PropTypes from 'prop-types';

function CalendarInput({ isUsedAsFilter, showCalendar, addInfoIcon, infoKey, infoKey1, setShowCalender, placeholderText, label, onChange, styleSet, selectedDateStr, containsAsterisk, id, minDate, disabled}) {
  const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

  const calendarRef = useRef(null);

  useEffect(() => {
    handleMouseClickForDropdown(calendarRef, () =>
      setShowCalender(false))
  }, [calendarRef]);

  useEffect(() => {
    const clearBtn = document.querySelector('.react-datepicker__close-icon');
    
    if (clearBtn) {
      clearBtn.setAttribute("tabIndex", "0");

      // Add Tailwind classes for focus styling
      clearBtn.classList.add(
        "focus-visible:outline-none",
        "focus-visible:ring-2",
        "focus-visible:ring-black",
        "rounded-sm"
      );

      const handleKeydown = (e) => {
        if (e.key === "Enter") {
          // Trigger the same effect as clicking clear
          clearBtn.click();
        }
      };
      clearBtn.addEventListener("keydown", handleKeydown);

      return () => {
        clearBtn.removeEventListener("keydown", handleKeydown);
      };
    }
  }, [selectedDateStr]);


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
    <div className={`flex flex-col ${styleSet?.outerDiv || ''}`}>
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
          className={`${styleSet?.datePicker || ''} w-full px-2 py-3 border border-[#707070] rounded-[4px] text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar`}
          wrapperClassName="w-full"
          isClearable={isUsedAsFilter? true : false}
          minDate={minDate}
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
  minDate: PropTypes.instanceOf(Date),
  disabled: PropTypes.bool,
};

export default CalendarInput;