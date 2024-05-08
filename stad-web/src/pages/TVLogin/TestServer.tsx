// 클라이언트 측에서 특정할 만한 sessionID를 생성하여 저장 (이때는 게스트라고 생각)
// 로그인 페이지에 qr 생성 시 저장한 세션 아이디를 value로 담아주기
// qr 인식 후 앱에서는 sse로 로그인 요청으로 서버에 api 요청
// 로그인 후 서버에 서버에 CONNECT 요청 보내기

// eventSource 객체가 생성된다면,  네트워크 탭에서 event 스트림 관찰 가능
import { NativeEventSource, EventSourcePolyfill } from "event-source-polyfill";
export interface postProps {
  userId?: number;
  sessionId?: string;
  type?: string;
}
export default function TestServer() {
  const EventSource = NativeEventSource || EventSourcePolyfill;
  const URL = "http://localhost:8081"; // 로컬로 테스트 진행하고 성공 시 서버 URL로 바꿔서 올리기
  // post 요청으로 사용자 정보 보내기 (연결을 위한 api post요청)
  // const sendLoginRequest = async ({ userId, sessionId, type }: postProps) => {
  //   try {
  //     const response = await fetch(`${URL}/alert/connect`, {
  //       method: "POST",
  //       headers: {
  //         "Content-Type": "application/json", // 요청 본문이 JSON임을 명시
  //       },
  //       body: JSON.stringify({
  //         userId: userId,
  //         tmpId: sessionId,
  //         type: type,
  //       }),
  //     });
  //     const data = await response.json(); // 응답을 JSON으로 변환
  //     console.log("서버 응답:", data);
  //   } catch (error) {
  //     console.error("연결 에러:", error);
  //   }
  // };

  // 연결 부분
  const handleConnect = () => {
    // api 연결 부분 (header에 jwt 토큰 요청시 event-source-polyfill 필요)
    const eventSource = new EventSource(`${URL}/alert/connect/`);

    // 연결 성공
    eventSource.onopen = (event) => {
      console.log("연결 성공", event);
    };

    // 연결 실패
    eventSource.onerror = (e) => {
      console.log("연결 실패", e);
      eventSource.close(); // 연결 끊기
    };

    // 이벤트 리스너
    eventSource.addEventListener("connect", (e) => {
      const { data: receivedData } = e;
      console.log("connect", receivedData);
    });
  };
  return (
    <div>
      <button onClick={handleConnect}>Connect SSE</button>
    </div>
  );
}
