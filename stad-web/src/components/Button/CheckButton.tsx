import styles from "./Button.module.css";
import check from "../../assets/checkedmark.png";
export default function AddButton() {
  return (
    <div className={`${styles.buttonWrapper}`}>
      <button className={`${styles.checkButton}`}>
        <img src={check} alt="찜한 콘텐츠" />
      </button>
      <p>찜한 콘텐츠</p>
    </div>
  );
}
