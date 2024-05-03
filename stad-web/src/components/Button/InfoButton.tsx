import styles from "./Button.module.css";
import info from "../../assets/mdi_information-outline.png";
import { PlayButtonProps } from "./PlayButton";
export default function InfoButton({ onClick }: PlayButtonProps) {
  return (
    <div className={`${styles.buttonWrapper}`}>
      <button
        onClick={onClick}
        className={`${styles.mainButton} ${styles.infoButton}`}
      >
        <img src={info} alt="상세 정보" />
        <div>상세 정보</div>
      </button>
    </div>
  );
}
