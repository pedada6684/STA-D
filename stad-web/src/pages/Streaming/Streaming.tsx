import React, { useEffect, useRef, useState } from "react";
import ReactPlayer from "react-player";
import { useNavigate, useParams } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import styles from "./Streaming.module.css";
import backIcon from "../../assets/material-symbols_arrow-back.png";
import { getStreaming, postWatchAdd, getAdvertUrlList } from "./StreamingAPI";

export default function Streaming() {
  const [isHovered, setIsHovered] = useState(false);
  const [isPlaying, setIsPlaying] = useState(false);
  const [playedSeconds, setPlayedSeconds] = useState(0);
  const [isModalOpen, setIsModalOpen] = useState(true);
  const [advertUrls, setAdvertUrls] = useState<string[]>([]);
  const [currentVideoIndex, setCurrentVideoIndex] = useState(0);

  const videoRef = useRef<ReactPlayer | null>(null);
  const { videoId } = useParams<{ videoId: string }>();
  const navigate = useNavigate();
  const token = useSelector((state: RootState) => state.token.accessToken);
  // const userId = useSelector((state: RootState) => state.tvUser.userId);
  const userId = 1;
  const detailId = Number(videoId);

  // const videoUrl = `http://localhost:8080/api/contents-detail/streaming/1/${detailId}`;
  // const videoUrl = `http://localhost:8083/stream/contents/1/${detailId}`;
  const videoUrl = `https://mystad.com/stream/contents/1/${detailId}`;
  // const videoUrl = `https://mystad.com/api/contents-detail/streaming/${detailId}`;

  // 광고 URL 리스트를 가져와 상태에 저장
  const fetchAdvertList = async () => {
    try {
      const response = await getAdvertUrlList(token, userId, detailId);
      console.log("response : " + typeof response.data);
      const a = response.data;
      const result = JSON.stringify(a);
      console.log("result: " + result);
      const advertUrlsArray = JSON.parse(result);
      console.log(advertUrlsArray);
      console.log(Object.values(advertUrlsArray));
      console.log(typeof Object.values(advertUrlsArray));

      setAdvertUrls(Object.values(advertUrlsArray));
      console.log("광고 URL 리스트 조회 완료");
    } catch (error) {
      console.error("광고 URL 리스트 조회 실패");
    }
  };

  // 시청 영상을 추가
  const addWatchVideo = async () => {
    try {
      await postWatchAdd(token, userId, detailId);
      console.log("시청 영상 생성 완료");
    } catch (error) {
      console.error("시청 영상 생성 실패");
    }
  };

  // 동영상이 끝났을 때 모달 닫기
  const handleEnded = () => {
    if (currentVideoIndex < advertUrls.length - 1) {
      // 다음 동영상 인덱스 증가
      setCurrentVideoIndex(currentVideoIndex + 1);
    } else {
      // 모든 동영상이 끝났을 때 모달을 닫습니다.
      setIsModalOpen(false);
    }
  };

  const handleProgress = (state: any) => {
    setPlayedSeconds(state.playedSeconds);
    console.log("진행된 초: ", Math.floor(playedSeconds));
  };

  const handleMouseEnter = () => setIsHovered(true);
  const handleMouseLeave = () => setIsHovered(false);
  const handleClick = () => setIsPlaying(true);

  // 컴포넌트 마운트 시 실행
  useEffect(() => {
    fetchAdvertList();
    addWatchVideo();
  }, [token, userId, detailId]);

  // 현재 동영상 인덱스에 해당하는 URL 가져오기
  const currentVideoUrl = advertUrls[currentVideoIndex];

  return (
    <div
      className={styles.videoContainer}
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}
      onClick={handleClick}
    >
      {!isModalOpen && (
        <>
          <ReactPlayer
            className="player"
            url={videoUrl}
            controls={true}
            playing={!isModalOpen}
            width="100%"
            height="100%"
            onProgress={handleProgress}
            onEnded={() => navigate(-1)} // 컨텐츠 종료되면 이전으로
            ref={videoRef}
          />
          {isHovered && (
            <>
              <div className={styles.videoControls}>
                <button onClick={() => navigate(-1)} className={styles.back}>
                  <img src={backIcon} alt="뒤로가기" />
                </button>
              </div>
            </>
          )}
        </>
      )}

      {/* ReactPlayer에서 현재 동영상 URL로 재생 */}
      {isModalOpen && (
        <ReactPlayer
          className="modalContent"
          url={currentVideoUrl}
          playing={isPlaying} // 클릭시 재생 설정
          width="100vw"
          height="100vh"
          onProgress={handleProgress}
          onEnded={handleEnded} // 동영상이 끝났을 때 handleEnded 함수 호출
          ref={videoRef}
          style={{ objectFit: "contain" }} // 동영상이 비율에 따라 전체 화면에 맞추도록 설정
        />
      )}
    </div>
  );
}
