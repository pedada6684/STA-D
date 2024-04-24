import { useState } from "react";
import DatePicker from "react-datepicker";
import styles from "./Calendar.module.css";
import "react-datepicker/dist/react-datepicker.css";

export default function DateRange() {
  const [startDate, setStartDate] = useState<Date | null>(new Date());
  const [endDate, setEndDate] = useState<Date | null>(new Date("2024/12/31"));

  return (
    <div className={`${styles.container}`}>
      <div className={`${styles.calendar}`}>
        <DatePicker
          dateFormat="yyyy-MM-dd" // 날짜 형태
          shouldCloseOnSelect // 날짜 선택시 자동 닫힘
          selected={startDate}
          onChange={(date) => setStartDate(date)}
          selectsStart
          startDate={startDate}
          endDate={endDate}
          showIcon
          toggleCalendarOnIconClick
        />
      </div>
      <div>~</div>
      <div className={`${styles.calendar}`}>
        <DatePicker
          dateFormat="yyyy-MM-dd"
          shouldCloseOnSelect
          selected={endDate}
          onChange={(date) => setEndDate(date)}
          selectsEnd
          startDate={startDate}
          endDate={endDate}
          minDate={startDate}
          showIcon
          toggleCalendarOnIconClick
        />
      </div>
    </div>
  );
}
