import styles from "./TVLanding.module.css";
import TVContainer from "../../components/Container/TVContainer";
import logo from "../../assets/tv_STA_D.png";
import { useNavigate } from "react-router-dom";
export default function TVLanding() {
  const navigate = useNavigate();
  return (
    <div>
      <TVContainer>
        <div className={`${styles.content}`}>
          <div className={`${styles.slogan}`}>
            <div className={`${styles.logo}`}>
              <img src={logo} alt="로고" />
            </div>
            <div className={`${styles.text}`}>
              원하던 컨텐츠를 무료로 즐기세요
            </div>
          </div>
          <div className={`${styles.description}`}>
            명작드라마, 인기 예능, 추억의 영화까지! <br />
            100개 이상의 다양한 시리즈와 영화 VOD들을 모두 무료로 만날 수
            있습니다.
          </div>
          <div className={`${styles.loginBtn}`}>
            <button onClick={() => navigate("/tv-login")}>
              QR 코드로 로그인하기
            </button>
          </div>
        </div>
      </TVContainer>
    </div>
  );
}
