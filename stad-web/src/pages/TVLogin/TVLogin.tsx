import TVContainer from "../../components/Container/TVContainer";
import styles from "./TVLogin.module.css";
import logo from "../../assets/tv_STA_D.png";
import QRCode from "qrcode.react";
export default function TVLogin() {
  return (
    <div>
      <TVContainer>
        <div className={`${styles.container}`}>
          <div className={`${styles.description}`}>
            <div className={`${styles.mainText}`}>QR코드로 로그인</div>
            <div className={`${styles.subText}`}>
              <div>
                1. 핸드폰에서{" "}
                <span>
                  <img src={logo} alt="로고" className={`${styles.logo}`} />
                </span>
                앱을 엽니다.
              </div>
              <div>2. 프로필 섹션에서 새 기기 연결을 선택합니다.</div>
              <div>3. QR코드를 스캔하여 로그인합니다.</div>
            </div>
          </div>
          <div>
            <QRCode value="" />
          </div>
        </div>
      </TVContainer>
    </div>
  );
}
