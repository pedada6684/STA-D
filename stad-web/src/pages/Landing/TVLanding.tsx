import styles from "./TVLanding.module.css";
import TVContainer from "../../components/Container/TVContainer";
import logo from "../../assets/tv_STA_D.png";
import { useNavigate } from "react-router-dom";
import { RootState } from "../../store";
import { useSelector } from "react-redux";
import { useEffect } from "react";
export default function TVLanding() {
  const navigate = useNavigate();
  const isUser = useSelector((state: RootState) => state.tvUser.users);
  console.log(isUser);
  useEffect(() => {
    if (isUser.length > 0) {
      navigate("/tv-profile");
    } else {
      navigate("/tv-login");
    }
  }, [isUser, navigate]);
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
            <button onClick={() => navigate("/tv-profile")}>로그인하기</button>
          </div>
        </div>
      </TVContainer>
    </div>
  );
}
