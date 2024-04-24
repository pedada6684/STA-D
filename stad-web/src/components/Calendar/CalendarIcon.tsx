import DatePicker from "react-datepicker";
import styles from "./Calendar.module.css";
import "react-datepicker/dist/react-datepicker.css";
import { useState } from "react";
export default function CalendarIcon() {
  const [startDate, setStartDate] = useState<Date | null>(new Date());

  return (
    <div className={`${styles.container}`}>
      <div className={`${styles.calendar}`}>
        <DatePicker
          dateFormat="yyyy-MM-dd"
          shouldCloseOnSelect
          selected={startDate}
          onChange={(date) => setStartDate}
          selectsStart
          startDate={startDate}
          showIcon
          toggleCalendarOnIconClick
        />
      </div>
    </div>
  );
}
