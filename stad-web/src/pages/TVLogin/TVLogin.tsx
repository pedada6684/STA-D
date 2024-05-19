import TVContainer from "../../components/Container/TVContainer";
import styles from "./TVLogin.module.css";
import logo from "../../assets/tv_STA_D.png";
import { useEffect, useRef, useState } from "react";
import { v4 as uuidv4 } from "uuid";
import qrcode from "qrcode-generator";
import { useNavigate } from "react-router";
import { useDispatch, useSelector } from "react-redux";
import { tvUserActions } from "../../store/tvUser";
import { RootState } from "../../store";
interface appUserType {
  userId: number;
  nickname: string;
  profile: string;
}
export default function TVLogin() {
  const [sessionId, setSessionId] = useState("");
  const qrContainerRef = useRef<HTMLDivElement>(null); // QR코드 렌더링할 컨테이너 참조
  const [eventSource, setEventSource] = useState<EventSource | null>(null);
  const URL = "https://www.mystad.com";
  const [switchConnection, setSwitchConnection] = useState(false); // tmp -> tv연결전환을 위한 트리거
  const [userProfile, setUserProfile] = useState<appUserType>();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const users = useSelector((state: RootState) => state.tvUser.users);
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
    const connectEventSource = (path: string) => {
      const source = new EventSource(
        `${URL}/alert/connect/${path}/${sessionId}`
      );

      // 연결 성공
      source.onopen = (event) => {
        console.log(`연결 성공 (${path})`, event);
      };

      // 이벤트 리스너
      // connect 연결 시도 부분
      source.addEventListener("connect", (e) => {
        const { data: receivedData } = e;
        console.log("connect", receivedData);
      });
      // qr 로그인으로 App유저 프로필 받아오기
      source.addEventListener("Qr login", (e) => {
        const { data: receivedData } = e;
        const data = JSON.parse(e.data);
        console.log(data);
        setUserProfile(data);
        console.log(userProfile);
        console.log("QRLogin", receivedData);
        setSwitchConnection(true); //연결 전환 트리거
      });
      // 연결 실패
      source.onerror = (e) => {
        console.log("연결 실패", e);
        source.close(); // 연결 끊기
      };

      return source;
    };

    const initialEventSource = connectEventSource("tmp");
    setEventSource(initialEventSource);

    return () => {
      if (initialEventSource) {
        initialEventSource.close(); // 컴포넌트 언마운트 시 이벤트 소스 정리
      }
    };
  }, [sessionId]);

  useEffect(() => {
    console.log("Updated userProfile:", userProfile);
    if (userProfile) {
      console.log("tmp -> tv");
      if (eventSource) {
        eventSource.close(); // 기존 연결 해제
        console.log("tmp 연결 닫기");
      }
      const connectTvEventSource = (userId: number) => {
        const source = new EventSource(`${URL}/alert/connect/tv/${userId}`);

        source.onopen = (event) => {
          console.log("연결 성공 (tv)", event);
        };

        source.addEventListener("connect", (e) => {
          const { data: receivedData } = e;
          console.log("SSE tv 연결", receivedData);
        });

        source.addEventListener("Content Play Request", (e) => {
          console.log("Content Play Request event received:", e);
          const data = JSON.parse(e.data);
          console.log("앱에서 넘어온 데이터", data);
          const { userId, contentDetailId } = data;
          console.log("컨텐츠 데이터", contentDetailId);
          navigate(`/tv/stream/${contentDetailId}`);
        });

        source.onerror = (e) => {
          console.log(`연결 실패 (tv)`, e);
          source.close();
          console.log("연결 종료(tv)");
          setTimeout(() => {
            const newSource = connectTvEventSource(userId);
            setEventSource(newSource);
          }, 5000);
        };

        return source;
      };

      const tvEventSource = connectTvEventSource(userProfile.userId);
      setEventSource(tvEventSource);

      dispatch(
        tvUserActions.addUser({
          userId: userProfile.userId,
          profile: {
            userNickname: userProfile.nickname,
            profile: userProfile.profile,
          },
        })
      );
      console.log("Dispatched addUser with: ", userProfile);
      navigate("/tv-profile");
    }
  }, [userProfile, dispatch, navigate]); // userProfile 상태가 변경될 때마다 실행

  useEffect(() => {
    console.log("Updated users in TVLogin:", users);
  }, [users]);

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
