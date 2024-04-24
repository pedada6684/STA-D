import styles from "./Button.module.css";
export default function EnrollButton() {
  return (
    <div className={`${styles.enCon}`}>
      <button className={`${styles.enroll}`}>등록</button>
    </div>
  );
}
