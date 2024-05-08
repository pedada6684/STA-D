import TVContainer from "../../components/Container/TVContainer";
import styles from "./TVLogin.module.css";
import logo from "../../assets/tv_STA_D.png";
import { useEffect, useRef, useState } from "react";
import { v4 as uuidv4 } from "uuid";
import qrcode from "qrcode-generator";
import { postProps } from "./TestServer";
import axios from "axios";
export default function TVLogin() {
  const [sessionId, setSessionId] = useState("");
  const qrContainerRef = useRef<HTMLDivElement>(null); // QR코드 렌더링할 컨테이너 참조
  const URL = "http://localhost:8081"; // 로컬로 테스트 진행하고 성공 시 서버 URL로 바꿔서 올리기
  useEffect(() => {
    // 세션 ID 생성
    const newSessionId = uuidv4();
    setSessionId(newSessionId);
    console.log(newSessionId);
  }, []);

  useEffect(() => {
    // QR Code 생성
    if (sessionId && qrContainerRef.current) {
      const qr = qrcode(4, "L"); // 오류 수정 레벨 L(약 7%의 데이터 복구)
      qr.addData(sessionId);
      qr.make();
      qrContainerRef.current.innerHTML = qr.createSvgTag({}); // SVG 태그 생성하여 할당
    }
  }, [sessionId]); // sessionId 변경될 때마다 QR 코드 갱신

  const sendRequest = async ({ sessionId }: postProps) => {
    try {
      const response = await axios.post(`${URL}/alert/connect`, {
        tmpId: sessionId,
      });
      console.log("서버 응답:", response);
    } catch (error) {
      console.error("전달 실패", error);
    }
  };
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
          <div className={`${styles.qr}`} ref={qrContainerRef} />
        </div>
      </TVContainer>
    </div>
  );
}
