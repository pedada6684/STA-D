import { useEffect, useRef, useState } from "react";
import styles from "./Streaming.module.css";
import ReactPlayer from "react-player";
import { useNavigate, useParams } from "react-router-dom";
import back from "../../assets/material-symbols_arrow-back.png";
import pause from "../../assets/material-symbols_pause.png";
import play from "../../assets/mdi_play.png";
import { useMutation, useQuery } from "react-query";
import { getStreaming, postWatchAdd } from "./StreamingAPI";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
export default function Streaming() {
  const [isPlaying, setIsPlaying] = useState(false);
  const [isHovered, setIsHovered] = useState(false);
  const { videoId } = useParams<{ videoId: string }>();
  const token = useSelector((state: RootState) => state.token.accessToken);
  const userId = useSelector((state: RootState) => state.user.userId);
  const detailId = Number(videoId);
  const [playedSeconds, setPlayedSeconds] = useState(0);
  const videoLoader = () => {
    return `https://mystad.com/api/contents-detail/streaming/${detailId}`;
  };

  const videoUrl = videoLoader(); // videoLoader 함수를 호출하여 URL을 얻습니다.
  const navigate = useNavigate();

  const { mutate: addWatchMutation } = useMutation(
    () => postWatchAdd(token, userId, detailId),
    {
      onSuccess: (data) => {
        console.log("시청 영상 생성 완료", data);
      },
      onError: (error) => {
        console.log("시청 영상 생성 실패", error);
      },
    }
  );
  const handlePlayPause = () => {
    addWatchMutation();
    setIsPlaying(!isPlaying);
  };

  const handleProgress = (state: any) => {
    setPlayedSeconds(state.playedSeconds);
    console.log(Math.floor(playedSeconds));
  };

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
          onProgress={handleProgress}
          onClick={() => setIsPlaying(!isPlaying)}
        />
      </div>
    </>
  );
}
