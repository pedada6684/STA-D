import React, { useEffect, useRef, useState } from "react";
import ReactPlayer from "react-player";
import { useNavigate, useParams } from "react-router-dom";
import { useMutation } from "react-query";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import styles from "./Streaming.module.css";
import backIcon from "../../assets/material-symbols_arrow-back.png";
import pauseIcon from "../../assets/material-symbols_pause.png";
import playIcon from "../../assets/mdi_play.png";
import { getStreaming, postWatchAdd, getAdvertUrlList } from "./StreamingAPI";

export default function Streaming() {
  const [isHovered, setIsHovered] = useState(false);
  const [playedSeconds, setPlayedSeconds] = useState(0);
  const [isModalOpen, setIsModalOpen] = useState(true);
  const [advertUrls, setAdvertUrls] = useState<string[]>([]);
  const [currentVideoIndex, setCurrentVideoIndex] = useState(0);

  const videoRef = useRef<ReactPlayer | null>(null);
  const { videoId } = useParams<{ videoId: string }>();
  const navigate = useNavigate();
  const token = useSelector((state: RootState) => state.token.accessToken);
  const userId = 1;
  const detailId = Number(videoId);

  // 광고 URL 리스트를 가져와 상태에 저장
  const fetchAdvertList = async () => {
    try {
      const response = await getAdvertUrlList(token, userId, detailId);
      console.log("광고 URL 리스트 조회 완료");
      setAdvertUrls(Object.values(response.data));
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
      console.error("시청 영상 생성 실패", error);
    }
  };

  // 동영상이 끝났을 때 다음 동영상으로 이동
  const handleEnded = () => {
    if (currentVideoIndex < advertUrls.length - 1) {
      setCurrentVideoIndex(currentVideoIndex + 1);
    } else {
      setIsModalOpen(false); // 모든 동영상이 끝난 후 모달 닫기
    }
  };

  const handleProgress = (state: any) => {
    setPlayedSeconds(state.playedSeconds);
    console.log("진행된 초: ", Math.floor(playedSeconds));
  };

  const handleMouseEnter = () => setIsHovered(true);
  const handleMouseLeave = () => setIsHovered(false);

  const handleCloseModal = () => setIsModalOpen(false);

  // 컴포넌트 마운트 시 실행
  useEffect(() => {
    fetchAdvertList();
    addWatchVideo();
  }, [token, userId, detailId]);

  // 현재 동영상 인덱스에 해당하는 URL 가져오기
  const currentVideoUrl = advertUrls[currentVideoIndex];

  return (
    <div className={styles.videoContainer} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
      {isHovered && (
        <>
          <div className={styles.videoControls}>
            <button onClick={() => navigate(-1)} className={styles.back}>
              <img src={backIcon} alt="뒤로가기" />
            </button>
          </div>
          <div className={styles.centerControls}>
            <button onClick={() => setIsModalOpen(false)}>
              <img src={playIcon} alt="닫기" />
            </button>
          </div>
        </>
      )}
      {/* ReactPlayer에서 현재 동영상 URL로 재생 */}
      {isModalOpen && currentVideoUrl && (
        <ReactPlayer
          className="player"
          url={currentVideoUrl}
          controls={true}
          playing={true} // 자동 재생 설정
          width="100%"
          height="100%"
          onProgress={handleProgress}
          onEnded={handleEnded} // 동영상이 끝났을 때 다음 동영상으로 이동
          ref={videoRef}
        />
      )}
    </div>
  );
}
