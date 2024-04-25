import styles from "./TVNav.module.css";
import logo from "../../assets/tv_STA_D.png";
import search from "../../assets/ic_sharp-search.png";
import dummyProfile from "../../assets/Ellipse 5.png";
import { Link } from "react-router-dom";

export default function TVNav() {
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
          <button>
            <img src={search} alt="검색" />
          </button>
        </div>
        <div className={`${styles.profile}`}>
          <img src={dummyProfile} alt="더미 프로필사진" />
        </div>
      </div>
    </div>
  );
}
