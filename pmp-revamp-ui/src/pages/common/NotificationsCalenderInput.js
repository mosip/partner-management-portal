import { useRef, useEffect } from 'react';
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css';
import { isLangRTL, handleMouseClickForDropdown } from '../../utils/AppUtils';
import { getUserProfile } from '../../services/UserProfileService';
import { useTranslation } from 'react-i18next';
import { format } from 'date-fns';

function NotificationsCalendarInput({ fieldName, showCalendar, setShowCalender, label, placeholderText, onChange, selectedDateStr, containsAsterisk, id }) {
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
    if (newDate) {
      const formattedDate = format(newDate, 'yyyy-MM-dd');
      setShowCalender(false);
      onChange(fieldName, formattedDate);
    }
  };

  const handleDateChange = (e) => {
    if (e.target.value === '') {
      onChange(fieldName, "");
    }
  };

  return (
    <div className="flex flex-col ml-4 min-w-64 h-9 relative">
      <label className={`flex items-center text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
        <p className={`mb-0.5`}>{label}{containsAsterisk && <span className={`text-crimson-red mx-1`}>*</span>}</p>
      </label>

      <div id="datePicker" className="w-full relative">
        <DatePicker
          id={id}
          selected={selectedDateStr!=="" ? new Date(selectedDateStr) : null}
          onChange={(date) => onDateChange(date)}
          onChangeRaw={handleDateChange}
          dateFormat="MM/dd/yyyy"
          placeholderText={placeholderText}
          className="h-9 w-full px-2 border border-[#707070] rounded-md text-[15px] text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
          wrapperClassName="w-full"
        />
      </div>
    </div>
  )
}

export default NotificationsCalendarInput;
