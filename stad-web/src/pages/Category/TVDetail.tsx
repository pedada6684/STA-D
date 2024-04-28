import { useLocation, useNavigate, useParams } from "react-router-dom";
import TVContainer from "../../components/Container/TVContainer";
import TVNav from "../../components/Nav/TVNav";
import Content from "../../components/Container/Content";
import styles from "./TVDetail.module.css";
import { MouseEvent, useEffect, useState } from "react";
import VideoList from "../Streaming/VideoList";
import {
  documentaryThumbnail,
  dramaThumbnail,
  foreignThumbnail,
  varietyThumbnail,
} from "./SeriesDummy";
import { actionDummy, comedyDummy } from "./MovieDummy";
interface TVDetailProps {
  type: "series" | "movie";
}
interface Video {
  id?: number;
  url: string;
  title: string;
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
  useEffect(() => {
    setFade(true); // 컴포넌트 마운트 시 fade 상태를 true로 설정하여 애니메이션 트리거
    switch (categoryId) {
      case "드라마":
        setVideos(dramaThumbnail);
        break;
      case "예능":
        setVideos(varietyThumbnail);
        break;
      case "교양/다큐멘터리":
        setVideos(documentaryThumbnail);
        break;
      case "해외":
        setVideos(foreignThumbnail);
        break;
      case "액션":
        setVideos(actionDummy);
        break;
      case "코미디":
        setVideos(comedyDummy);
        break;
      default:
        setVideos([]);
    }
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
          <VideoList items={video} />
        </Content>
      </TVContainer>
    </div>
  );
}
