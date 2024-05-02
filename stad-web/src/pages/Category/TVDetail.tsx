import { useLocation, useNavigate, useParams } from "react-router-dom";
import TVContainer from "../../components/Container/TVContainer";
import TVNav from "../../components/Nav/TVNav";
import Content from "../../components/Container/Content";
import styles from "./TVDetail.module.css";
import { MouseEvent, useEffect, useState } from "react";
import VideoList from "../Streaming/VideoList";
import { useQuery } from "react-query";
import {
  getMovieVideoList,
  getSeriesVideoList,
} from "../Streaming/StreamingAPI";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import Loading from "../../components/Loading";
interface TVDetailProps {
  type: "series" | "movie";
}
export interface Video {
  id: number;
  thumbnailUrl: string;
  title: string;
  playtime?: string;
  releaseYear?: string;
  audienceAge?: string;
  creator?: string;
  cast?: string;
  description?: string;
}

export default function TVSeriesDetail({ type }: TVDetailProps) {
  const { categoryId } = useParams<{ categoryId: string }>();
  const navigate = useNavigate();
  const [video, setVideos] = useState<Video[]>([]);
  const title = type === "series" ? "시리즈" : "영화";
  const [fade, setFade] = useState(false);
  const handleNavigate = (e: MouseEvent<HTMLSpanElement>) => {
    if (title == "시리즈") {
      navigate(`/tv-series`);
    } else {
      navigate(`/tv-movie`);
    }
  };
  const token = useSelector((state: RootState) => state.token.accessToken);
  const { data: videos, isLoading } = useQuery(
    ["videoList", token, categoryId],
    () => {
      if (categoryId) {
        if (title === "시리즈") {
          return getSeriesVideoList(token, categoryId);
        } else {
          return getMovieVideoList(token, categoryId);
        }
      }
    }
  );

  if (isLoading) {
    <Loading />;
  }
  useEffect(() => {
    setFade(true); // 컴포넌트 마운트 시 fade 상태를 true로 설정하여 애니메이션 트리거
    return () => setFade(false);
  }, []);
  return (
    <div>
      <TVContainer>
        <TVNav />
        <Content>
          <div className={`${styles.top}`}>
            <div className={`${styles.title}`}>
              <span onClick={handleNavigate} style={{ cursor: "pointer" }}>
                {title}
              </span>{" "}
              <span>{">"}</span>
            </div>
            <div className={`${styles.genre} ${fade ? styles.fadeIn : ""}`}>
              {categoryId}
            </div>
          </div>
          {videos && <VideoList items={videos} />}
        </Content>
      </TVContainer>
    </div>
  );
}
