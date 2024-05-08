// 클라이언트 측에서 특정할 만한 sessionID를 생성하여 저장 (이때는 게스트라고 생각)
// qr 생성 시 내 정보를 담고서 qr 생성
// qr 인식 후 유저 식별 후 데이터 저장 ?
// 로그인 한 후엔 유령계정 아니라고 또 서버에 요청보내야함

// eventSource 객체가 생성된다면,  네트워크 탭에서 event 스트림 관찰 가능
//
import { NativeEventSource, EventSourcePolyfill } from "event-source-polyfill";
import { useDispatch } from "react-redux";
export default function TestServer() {
  const dispatch = useDispatch();
  const EventSource = NativeEventSource || EventSourcePolyfill;
  const URL = "http://localhost:8080"; // 로컬로 테스트 진행하고 성공 시 서버 URL로 바꿔서 올리기
  const sessionId = ``;
  const handleConnect = () => {
    const eventSource = new EventSource(
      // api 연결 부분 (header에 jwt 토큰 요청시 event-source-polyfill)
      URL + `/api/`
    );

    // 연결 성공
    eventSource.onopen = (event) => {
      console.log("연결 성공", event);
    };

    // 연결 실패
    eventSource.onerror = (e) => {
      console.log("연결 실패", e);
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
