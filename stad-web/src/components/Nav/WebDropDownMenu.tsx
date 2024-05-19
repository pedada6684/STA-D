import { useNavigate } from "react-router-dom";
import styles from "./WebNav.module.css";
export function AdDropDownMenu() {
  const navigate = useNavigate();
  return (
    <>
      <ul className={styles.dropDownMenu}>
        <li onClick={() => navigate("/ad-enroll")}>광고등록</li>
        <li onClick={() => navigate("/ad-management")}>광고관리</li>
      </ul>
    </>
  );
}

export function MerchanDropDownMenu() {
  const navigate = useNavigate();
  return (
    <>
      <ul className={styles.dropDownMenu}>
        <li onClick={() => navigate("/pro-management")}>상품관리</li>
        {/* <li onClick={() => navigate("/review")}>리뷰관리</li> */}
      </ul>
    </>
  );
}
