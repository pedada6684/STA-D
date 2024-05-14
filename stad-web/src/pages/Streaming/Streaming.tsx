import React, { useEffect, useRef, useState } from "react";
import ReactPlayer from "react-player";
import { useNavigate, useParams } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import Loading from "../../components/Loading";
import styles from "./Streaming.module.css";
import backIcon from "../../assets/material-symbols_arrow-back.png";
import { getStreaming, checkWatched, postWatchAdd, getAdvertUrlList, updateStopTime, putWatched } from "./StreamingAPI";

export default function Streaming() {
  const [isHovered, setIsHovered] = useState(false);
  const [isPlaying, setIsPlaying] = useState(false);
  const [playedSeconds, setPlayedSeconds] = useState(0);
  const [isModalOpen, setIsModalOpen] = useState(true);
  const [advertIds, setAdvertIds] = useState<number[]>([]);
  const [currentVideoIndex, setCurrentVideoIndex] = useState(0);
  const [isLoading, setIsLoading] = useState(true); // 추가: 로딩 상태 추가
  const [timer, setTimer] = useState(60); // 1분 타이머

  const videoRef = useRef<ReactPlayer | null>(null);
  const { videoId } = useParams<{ videoId: string }>();
  const navigate = useNavigate();
  const token = useSelector((state: RootState) => state.token.accessToken);
  // const userId = useSelector((state: RootState) => state.tvUser.userId);
  const userId = 1;
  const detailId = Number(videoId);

  useEffect(() => {
    const timerId = setInterval(() => {
      if (isPlaying && timer > 0) {
        setTimer((prevTimer) => prevTimer - 1);
      }
    }, 1000);

    return () => clearInterval(timerId); // 컴포넌트 언마운트 시 타이머 정리
  }, [isPlaying]);

  useEffect(() => {
    if (timer === 0) {
      setIsModalOpen(false); // 타이머가 0이 되면 모달을 닫음
    }
  }, [timer]);

  const videoUrl = `https://mystad.com/stream/contents/1/${detailId}`; // 1을 userId로 바꿔야 함

  const fetchInitialData = async () => {
    try {
      await Promise.all([fetchCheckWatched(), fetchAdvertList(), addWatchVideo()]);
      setIsLoading(false); // 모든 비동기 작업 완료 후 로딩 상태 변경
    } catch (error) {
      console.error("Error fetching initial data:", error);
    }
  };

  const fetchCheckWatched = async () => {
    const response = await checkWatched(token, userId, detailId);
    console.log("시청중인 영상일까요?", response.result);

    if (response.result && videoRef.current) {
      // ReactPlayer 참조를 사용하여 특정 시점부터 동영상을 재생
      videoRef.current.seekTo(response.stopTime);
    }
  };

  // 광고 URL 리스트를 가져와 상태에 저장
  const fetchAdvertList = async () => {
    try {
      const response = await getAdvertUrlList(token, userId, detailId);
      console.log("response : " + typeof response);
      console.log("result: " + response.advertIdList);
      setAdvertIds(response.advertIdList);
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

  // 광고가 끝났을 때 모달 닫기
  const handleAdvertEnded = () => {
    if (currentVideoIndex < advertIds.length - 1) {
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

  const handlePause = () => {
    navigate(-1);
    try {
      updateStopTime(token, userId, detailId, playedSeconds);
      console.log("시청 종료 시점 저장 완료");
    } catch (error) {
      console.error("시청 종료 시점 저장 실패", error);
    }
  };

  const handleContentEnded = () => {
    navigate(-1);
    try {
      putWatched(token, userId, detailId);
      console.log("시청 완료 영상 저장 완료");
    } catch (error) {
      console.error("시청 완료 영상 저장 실패", error);
    }
  };

  useEffect(() => {
    fetchInitialData(); // 비동기 함수 호출을 한번에 처리
  }, [token, userId, detailId]);

  useEffect(() => {
    if (!isModalOpen) {
      fetchCheckWatched();
    }
  }, [isModalOpen]);

  // 현재 동영상 인덱스에 해당하는 URL 가져오기
  const currentVideoUrl = `https://www.mystad.com/stream/advert-video/${advertIds[currentVideoIndex]}`;

  // 로딩 중이면 로딩 화면 표시
  if (isLoading) {
    return (
      <div>
        <Loading />
      </div>
    );
  }

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
            onEnded={handleContentEnded} // 컨텐츠 종료되면 이전으로
            ref={videoRef}
          />
          {isHovered && (
            <>
              <div className={styles.videoControls}>
                <button onClick={handlePause} className={styles.back}>
                  <img src={backIcon} alt="뒤로가기" />
                </button>
              </div>
            </>
          )}
        </>
      )}

      {/* ReactPlayer에서 현재 동영상 URL로 재생 */}
      {isModalOpen && (
        <div className={styles.modal}>
          <ReactPlayer
            className={styles.modalContent}
            url={currentVideoUrl}
            playing={isPlaying} // 클릭시 재생 설정
            width="100vw"
            height="100vh"
            onEnded={handleAdvertEnded} // 동영상이 끝났을 때 handleAdvertEnded 함수 호출
          />
          <div className={styles.timer}>{timer - 1}초 뒤에 콘텐츠가 재생됩니다.</div>
          <button className={styles.skip} onClick={() => setIsModalOpen(false)}>
            skip
          </button>
        </div>
      )}
    </div>
  );
}
