import { Link, useNavigate } from "react-router-dom";
import styles from "./WebNav.module.css";
import webLogo from "../../assets/stad-weblogo.png";
import { MouseEvent, useEffect, useState } from "react";
import { AdDropDownMenu, MerchanDropDownMenu } from "./WebDropDownMenu";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../store";
import axios from "axios";
import { userActions } from "../../store/user";
import { jwtDecode } from "jwt-decode";
import { useQuery } from "react-query";
import { GetLogout, GetUser } from "../../pages/WebLogin/LoginApi";
import { tokenActions } from "../../store/token";
export default function WebNav() {
  const [company, setCompany] = useState("");
  const accessToken = useSelector(
    (state: RootState) => state.token.accessToken
  );
  const {
    data: userData,
    isLoading,
    error,
  } = useQuery(["user", accessToken], () => GetUser(accessToken), {
    enabled: !!accessToken, // accessToken이 있는 경우에만 실행
  });

  const dispatch = useDispatch();
  const navigate = useNavigate();
  useEffect(() => {
    if (userData) {
      dispatch(
        userActions.loginUser({
          userId: userData.userId,
          userCompany: userData.company,
          isLoggedIn: true,
        })
      ); // 사용자 정보를 Redux 스토어에 저장
      setCompany(userData.company);
    }
  }, [userData, dispatch]);

  const handleSignUp = (e: MouseEvent<HTMLButtonElement>) => {
    navigate("/web-signUp");
  };
  const handleLogin = (e: MouseEvent<HTMLButtonElement>) => {
    navigate("/web-login");
  };
  const handleMyPage = (e: MouseEvent<HTMLButtonElement>) => {
    if (accessToken) {
      navigate("/my-page");
    }
  };

  const handleLogout = async (e: MouseEvent<HTMLButtonElement>) => {
    const res = await GetLogout(accessToken);
    console.log(res);
    window.alert("로그아웃이 완료되었습니다");
    dispatch(userActions.logoutUser());
    dispatch(tokenActions.logoutUser());
    navigate("/web-main");
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
          {accessToken ? (
            <>
              <button
                className={`${styles.btn} ${styles.myPage}`}
                onClick={handleMyPage}
              >
                {company}
              </button>
              <button
                className={`${styles.btn} ${styles.signup}`}
                onClick={handleLogout}
              >
                LOGOUT
              </button>
            </>
          ) : (
            <>
              <button
                className={`${styles.btn} ${styles.login}`}
                onClick={handleLogin}
              >
                LOGIN
              </button>
              <button
                className={`${styles.btn} ${styles.signup}`}
                onClick={handleSignUp}
              >
                SIGN UP
              </button>
            </>
          )}
        </div>
      </div>
    </div>
  );
}
