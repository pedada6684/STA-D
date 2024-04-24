import { useEffect, useRef, useState } from "react";
import styles from "./VideoDetail.module.css";
import ReactPlayer from "react-player";
import { useNavigate } from "react-router-dom";
import back from "../../assets/material-symbols_arrow-back.png";
import pause from "../../assets/material-symbols_pause.png";
import play from "../../assets/mdi_play.png";
export default function VideoDetail() {
  const [isPlaying, setIsPlaying] = useState(false);
  //   const [isFullscreen, setIsFullscreen] = useState(false);
  const [isHovered, setIsHovered] = useState(false);
  const videoLoader = () => {
    return "http://localhost:8080/api/contents/streaming/1"; // 1 대신 contentId 보내면 됨
  };

  const videoUrl = videoLoader(); // videoLoader 함수를 호출하여 URL을 얻습니다.
  const navigate = useNavigate();
  const handlePlayPause = () => {
    setIsPlaying(!isPlaying);
  };
  //   const playerContainerRef = useRef<HTMLDivElement>(null);
  //   // 전체화면 토글 함수
  //   const toggleFullscreen = () => {
  //     const player = playerContainerRef.current;
  //     if (player) {
  //       if (!document.fullscreenElement) {
  //         player.requestFullscreen().catch((e) => {
  //           console.error("전체화면 모드 진입 실패: ", e);
  //         });
  //       } else {
  //         document.exitFullscreen().catch((e) => {
  //           console.error("전체화면 모드 종료 실패: ", e);
  //         });
  //       }
  //     }
  //   };

  //   // 전체화면 변경 이벤트 리스너 설정
  //   useEffect(() => {
  //     function handleFullscreenChange() {
  //       setIsFullscreen(!!document.fullscreenElement);
  //     }

  //     document.addEventListener("fullscreenchange", handleFullscreenChange);

  //     // 컴포넌트 언마운트 시 이벤트 리스너 제거
  //     return () => {
  //       document.removeEventListener("fullscreenchange", handleFullscreenChange);
  //     };
  //   }, []); // 빈 의존성 배열을 추가하여 마운트 될 때만 실행되도록 함

  return (
    <>
      <div
        // ref={playerContainerRef}
        className={`${styles.videoContainer}`}
        onMouseEnter={() => setIsHovered(true)}
        onMouseLeave={() => setIsHovered(false)}
        onClick={handlePlayPause}
      >
        {isHovered && (
          <>
            <div className={`${styles.videoControls}`}>
              <button
                onClick={() => {
                  navigate(-1);
                }}
                className={`${styles.back}`}
              >
                <img src={back} alt="뒤로가기" />
              </button>
            </div>
            <div className={`${styles.centerControls}`}>
              <button onClick={handlePlayPause}>
                <img
                  src={isPlaying ? play : pause}
                  alt={isPlaying ? "play" : "pause"}
                  className={`${styles.playPause}`}
                />
              </button>
            </div>
          </>
        )}
        <ReactPlayer
          className="player"
          url={videoUrl} // videoUrl을 전달합니다.
          controls={true}
          playing={isPlaying}
          width="100%"
          height="100%"
          onClick={() => setIsPlaying(!isPlaying)}
        />
      </div>
    </>
  );
}
