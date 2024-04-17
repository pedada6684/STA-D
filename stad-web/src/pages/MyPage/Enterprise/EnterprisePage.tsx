import styles from "./EnterprisePage.module.css";
import company from "../../../assets/mdi_company.png";
export default function EnterprisePage() {
  return (
    <div className={styles.container}>
      <div className={`${styles.profile}`}>
        <div>
          <div>
            <img src={company} alt="회사" className={styles.icon} />
          </div>
          <div className={`${styles.title}`}>회사명</div>
        </div>
        <div>수정</div>
      </div>
    </div>
  );
}
