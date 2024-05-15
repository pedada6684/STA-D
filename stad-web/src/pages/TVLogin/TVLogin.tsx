import TVContainer from "../../components/Container/TVContainer";
import styles from "./TVLogin.module.css";
import logo from "../../assets/tv_STA_D.png";
import { useEffect, useRef, useState } from "react";
import { v4 as uuidv4 } from "uuid";
import qrcode from "qrcode-generator";
import { useNavigate } from "react-router";
import { useDispatch } from "react-redux";
import { tvUserActions } from "../../store/tvUser";
interface appUserType {
  userId: number;
  nickname: string;
  profile: string;
}
export default function TVLogin() {
  const [sessionId, setSessionId] = useState("");
  const qrContainerRef = useRef<HTMLDivElement>(null); // QR코드 렌더링할 컨테이너 참조
  // 로컬로 테스트 진행하고 성공 시 서버 URL로 바꿔서 올리기
  const URL = "https://www.mystad.com";
  // const URL = "http://localhost:8081";
  // const EventSource = NativeEventSource || EventSourcePolyfill;
  const [userProfile, setUserProfile] = useState<appUserType>();
  const navigate = useNavigate();
  const dispatch = useDispatch();

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

    // api 연결 부분 (header에 jwt 토큰 요청시 event-source-polyfill 필요)
    const eventSource = new EventSource(
      `${URL}/alert/connect/${"tmp"}/${sessionId}`
    );

    // 연결 성공
    eventSource.onopen = (event) => {
      console.log("연결 성공", event);
    };

    // 이벤트 리스너
    // connect 연결 시도 부분
    eventSource.addEventListener("connect", (e) => {
      const { data: receivedData } = e;
      console.log("connect", receivedData);
    });
    // qr 로그인으로 App유저 프로필 받아오기
    eventSource.addEventListener("Qr login", (e) => {
      const { data: receivedData } = e;
      const data = JSON.parse(e.data);
      console.log(data);
      setUserProfile(data);
      console.log(userProfile);
      console.log("QRLogin", receivedData);
    });

    // 연결 실패
    eventSource.onerror = (e) => {
      console.log("연결 실패", e);
      eventSource.close(); // 연결 끊기
    };
  }, [sessionId]); // sessionId 변경될 때마다 QR 코드 갱신

  useEffect(() => {
    console.log("Updated userProfile:", userProfile);
    if (userProfile) {
      dispatch(
        tvUserActions.addUser({
          userId: userProfile.userId,
          profile: {
            userNickname: userProfile.nickname,
            profile: userProfile.profile,
          },
        })
      );
      navigate("/tv-profile");
    }
  }, [userProfile, dispatch, navigate]); // userProfile 상태가 변경될 때마다 실행

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
