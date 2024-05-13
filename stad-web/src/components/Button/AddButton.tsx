import styles from "./Button.module.css";
import plus from "../../assets/ph_plus.png";
export default function AddButton() {
  return (
    <div className={`${styles.buttonWrapper}`}>
      <button className={`${styles.addButton}`}>
        <img src={plus} alt="찜하기" />
      </button>
      <p>찜하기</p>
    </div>
  );
}
