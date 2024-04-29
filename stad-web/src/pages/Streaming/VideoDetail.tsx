import { useNavigate, useParams } from "react-router-dom";
import Content from "../../components/Container/Content";
import TVContainer from "../../components/Container/TVContainer";
import TVNav from "../../components/Nav/TVNav";
import {
  documentaryThumbnail,
  dramaThumbnail,
  foreignThumbnail,
  smallThumbnail,
  varietyThumbnail,
} from "../Category/SeriesDummy";
import BillboardContainer from "../../components/Container/BillboardContainer";
import styles from "./VideoDetail.module.css";
import PlayButton from "../../components/Button/PlayButton";
import AddButton from "../../components/Button/AddButton";

interface VideoDetailProps {
  id: number;
  title: string;
  thumbnailUrl: string;
  playtime?: string;
  releaseYear?: string;
  audienceAge?: string;
  creator?: string;
  cast?: string;
  description?: string;
}

export default function VideoDetail() {
  const navigate = useNavigate();

  const allVideos: VideoDetailProps[] = [
    ...smallThumbnail,
    ...dramaThumbnail,
    ...varietyThumbnail,
    ...documentaryThumbnail,
    ...foreignThumbnail,
  ];
  // URL 에서 videoId 가져오기
  // URL에서 videoId 가져오기 및 변환
  const { videoId } = useParams<{ videoId: string }>();
  if (!videoId) {
    return <div>No video ID provided.</div>;
  }
  const videoIdNumber = parseInt(videoId);
  if (isNaN(videoIdNumber)) {
    return <div>Invalid video ID.</div>;
  }

  const video = allVideos.find((video) => video.id === videoIdNumber);
  if (!video) {
    return <div>Video not found.</div>;
  }
  const handlePlayClick = () => {
    navigate(`/tv/stream/${videoId}`); // 스트리밍 페이지로 이동
  };

  const backgroundStyle = {
    backgroundImage: `linear-gradient(
      to top, rgba(0, 0, 0, 0.8) 2%, rgba(0, 0, 0, 0) 50%),
      linear-gradient(
      to right, rgba(0, 0, 0, 0.8) 20%, rgba(0, 0, 0, 0) 50%, rgba(0, 0, 0, 0.1) 100%),
      url(${video.thumbnailUrl})`,
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat",
    // height: "30vh",
    width: "70%",
  };

  return (
    <div>
      <TVContainer>
        <TVNav />
        <div style={{ paddingTop: "5rem" }}>
          <Content>
            <BillboardContainer>
              <div>
                <div className={`${styles.imgWrapper}`}>
                  <div
                    style={backgroundStyle}
                    className={`${styles.coverImage}`}
                  >
                    <img
                      src={video.thumbnailUrl}
                      alt={video.title}
                      style={{
                        width: "100%",
                        height: "100%",
                        objectFit: "cover",
                      }}
                    />
                  </div>
                </div>
                <div className={`${styles.detailContainer}`}>
                  <div className={`${styles.detailWrapper}`}>
                    <div className={`${styles.vidTitle}`}>{video.title}</div>
                    <div className={`${styles.timeAge}`}>
                      <span>{video.playtime}</span>
                      <span>•</span>
                      <span>{video.audienceAge}+</span>
                    </div>
                    <div className={`${styles.bar}`}></div>
                    <div className={`${styles.buttonWrapper}`}>
                      <PlayButton onClick={handlePlayClick} />
                      <AddButton />
                    </div>
                    <div className={`${styles.description}`}>
                      {video.description}
                    </div>
                    <div className={`${styles.staff}`}>
                      <span>크리에이터</span>
                      <span>{video.creator}</span>
                    </div>
                    <div className={`${styles.staff}`}>
                      <span>출연</span>
                      <span>{video.cast}</span>
                    </div>
                  </div>
                </div>
              </div>
            </BillboardContainer>
          </Content>
        </div>
      </TVContainer>
    </div>
  );
}
