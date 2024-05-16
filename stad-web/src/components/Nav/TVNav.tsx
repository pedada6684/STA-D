import styles from "./TVNav.module.css";
import logo from "../../assets/tv_STA_D.png";
import search from "../../assets/ic_sharp-search.png";
import dummyProfile from "../../assets/Ellipse 5.png";
import { Link, useNavigate } from "react-router-dom";
import { MouseEvent } from "react";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../store";
import profile from "../../assets/iconamoon_profile-circle.png";
import logout from "../../assets/material-symbols_logout.png";
import { tvUserActions } from "../../store/tvUser";
export default function TVNav() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const selectedProfile = useSelector(
    (state: RootState) => state.tvUser.selectedProfile
  );

  const handleSearchIconClick = (e: MouseEvent<HTMLButtonElement>) => {
    navigate("/tv-search"); // 검색 입력창이 열릴 때 검색 페이지로 이동
  };

  const handleProfileTransfer = (e: MouseEvent<HTMLLIElement>) => {
    navigate(`/tv-profile`);
  };

  const handleLogout = (e: MouseEvent<HTMLLIElement>) => {
    dispatch(tvUserActions.logoutUser());
    navigate("/tv-profile");
  };
  return (
    <div className={`${styles.container}`}>
      <div className={`${styles.left}`}>
        <div className={`${styles.logo}`}>
          <Link to="/tv-main">
            <img src={logo} className={`${styles.logoIcon}`} alt="홈" />
          </Link>
        </div>
        <div className={`${styles.menus}`}>
          <div className={`${styles.category}`}>
            <Link to="/tv-series">시리즈</Link>
          </div>
          <div className={`${styles.category}`}>
            <Link to="/tv-movie">영화</Link>
          </div>
        </div>
      </div>
      <div className={`${styles.right}`}>
        <div className={`${styles.search}`}>
          <button onClick={handleSearchIconClick}>
            <img src={search} alt="검색" />
          </button>
        </div>

        <div className={`${styles.profileDropdownButton}`}>
          <img src={selectedProfile?.profile} />
          <div className={`${styles.dropDown}`}>
            <ul className={`${styles.menuList}`}>
              <li
                className={`${styles.menuItem}`}
                onClick={handleProfileTransfer}
              >
                <div>
                  <img
                    className={`${styles.subLogo}`}
                    src={profile}
                    alt="프로필 변경"
                  />
                </div>
                <div>프로필 변경</div>
              </li>
              <li className={`${styles.menuItem}`} onClick={handleLogout}>
                <div>
                  <img
                    className={`${styles.subLogo}`}
                    src={logout}
                    alt="로그아웃"
                  />
                </div>
                <div>로그아웃</div>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
}
