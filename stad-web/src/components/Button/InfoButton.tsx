import styles from "./Button.module.css";
import info from "../../assets/mdi_information-outline.png";
export default function InfoButton() {
  return (
    <div className={`${styles.buttonWrapper}`}>
      <button className={`${styles.mainButton} ${styles.infoButton}`}>
        <img src={info} alt="상세 정보" />
        <div>상세 정보</div>
      </button>
    </div>
  );
}
