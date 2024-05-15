import styles from "./TVNav.module.css";
import logo from "../../assets/tv_STA_D.png";
import search from "../../assets/ic_sharp-search.png";
import dummyProfile from "../../assets/Ellipse 5.png";
import { Link, useNavigate } from "react-router-dom";
import { MouseEvent } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../store";

export default function TVNav() {
  const navigate = useNavigate();
  const selectedProfile = useSelector(
    (state: RootState) => state.tvUser.selectedProfile
  );

  const handleSearchIconClick = (e: MouseEvent<HTMLButtonElement>) => {
    navigate("/tv-search"); // 검색 입력창이 열릴 때 검색 페이지로 이동
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

        <div className={`${styles.profile}`}>
          <img src={selectedProfile?.profile} />
        </div>
      </div>
    </div>
  );
}
