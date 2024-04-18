import styles from "./EnterpriseInfo.module.css";
export default function EnterpriseInfo() {
  return (
    <div className={`${styles.container}`}>
      <div className={`${styles.item} ${styles.id}`}>
        <div className={`${styles.title}`}>아이디</div>
        <div className={`${styles.space}`}>ssafy@gmail.com</div>
      </div>

      <div className={` ${styles.item}  ${styles.phone}`}>
        <div className={`${styles.title}`}>회사전화번호</div>
        <div className={`${styles.space}`}>010-2342-2342</div>
      </div>
      <div className={`${styles.item} ${styles.regNum}`}>
        <div className={`${styles.title}`}>사업자 등록번호</div>
        <div className={`${styles.space}`}>000-00-0000</div>
      </div>
      <div className={`${styles.item} ${styles.name}`}>
        <div className={`${styles.title}`}>등록 사원 이름</div>
        <div className={`${styles.space}`}>홍길동</div>
      </div>
    </div>
  );
}
