import Container from "../../components/Container/Container";
import WebNav from "../../components/Nav/WebNav";
import styles from "./WebMain.module.css";
import { useNavigate } from "react-router-dom";
import { MouseEvent } from "react";
export default function WebMain() {
  const navigate = useNavigate();
  const handleAdEnroll = (e: MouseEvent<HTMLButtonElement>) => {
    navigate("/ad-enroll");
  };
  return (
    <div>
      <Container>
        <WebNav />
        <div className={styles.contentC}>
          <div className={styles.video}>
            <video
              muted
              autoPlay
              loop
              src="/Video/3007426-uhd_2560_1440_30fps.mp4"
            />
          </div>
          <div className={styles.introduce}>
            <div className={`${styles.introduceT}`}>
              FAST 티비가 만들어가는 신개념 광고 플랫폼
            </div>
            <div className={`${styles.introduceS}`}>
              시청과 쇼핑을 하나로 - FAST 티비, 광고의 미래
            </div>
            <div>
              <button onClick={handleAdEnroll} className={styles.btn}>
                광고 등록하러가기
              </button>
            </div>
          </div>
        </div>
      </Container>
    </div>
  );
}
