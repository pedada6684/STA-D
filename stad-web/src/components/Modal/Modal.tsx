import styles from "./Modal.module.css";
import close from "../../assets/material-symbols-light_close.png";
type ModalProps = {
  isOpen: boolean;
  onRequestClose: () => void;
  children: React.ReactNode;
};
export default function Modal({
  isOpen,
  onRequestClose,
  children,
}: ModalProps) {
  if (!isOpen) return null;

  return (
    <div className={`${styles.overlay}`}>
      <div className={`${styles.content}`}>
        <button className={`${styles.closeBtn}`} onClick={onRequestClose}>
          <img className={styles.close} src={close} alt="닫기" />
        </button>
        {children}
        <button className={styles.applyBtn}>적용</button>
      </div>
    </div>
  );
}
