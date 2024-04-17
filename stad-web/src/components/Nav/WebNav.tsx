import { Link, useNavigate } from "react-router-dom";
import styles from "./WebNav.module.css";
import webLogo from "../../assets/stad-weblogo.png";
import { MouseEvent, useState } from "react";
import { AdDropDownMenu, MerchanDropDownMenu } from "./WebDropDownMenu";
export default function WebNav() {
  const [isAdOpen, setIsAdOpen] = useState(false);
  const [isMerOpen, setIsMerOpen] = useState(false);
  const navigate = useNavigate();
  const handleSignUp = (e: MouseEvent<HTMLButtonElement>) => {
    navigate("/web-signUp");
  };
  const handleLogin = (e: MouseEvent<HTMLButtonElement>) => {
    navigate("/web-login");
  };
  return (
    <div className={`${styles.container}`}>
      <div className={styles.logo}>
        <Link to="/web-main">
          <img src={webLogo} className={styles.logoIcon} alt="홈" />
        </Link>
      </div>
      <div className={styles.menus}>
        <ul className={styles.navMenu}>
          <li className={styles.dropDown}>
            <div className={styles.dropDownTitle}>광고</div>
            <div>
              <AdDropDownMenu />
            </div>
          </li>
          <li className={styles.dropDown}>
            <div className={styles.dropDownTitle}>상품</div>
            <div>
              <MerchanDropDownMenu />
            </div>
          </li>
        </ul>
        <div className={styles.btns}>
          <button
            className={`${styles.btn} ${styles.login}`}
            onClick={handleLogin}
          >
            LOG IN
          </button>
          <button
            className={`${styles.btn} ${styles.signup}`}
            onClick={handleSignUp}
          >
            SIGN UP
          </button>
        </div>
      </div>
    </div>
  );
}
