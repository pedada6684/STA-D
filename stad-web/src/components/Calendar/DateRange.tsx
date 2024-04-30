import { useState } from "react";
import DatePicker from "react-datepicker";
import styles from "./Calendar.module.css";
import "react-datepicker/dist/react-datepicker.css";

interface DateRangeProps {
    startDate: Date | null;
    endDate: Date | null;
    setStartDate: (date: Date | null) => void;
    setEndDate: (date: Date | null) => void;
}

export default function DateRange({
  startDate,
  endDate,
  setStartDate,
  setEndDate,
}: DateRangeProps) {
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
      <div className={`${styles.letter}`}>~</div>
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
