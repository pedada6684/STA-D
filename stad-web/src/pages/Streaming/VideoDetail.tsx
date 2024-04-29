import { useParams } from "react-router-dom";
import Content from "../../components/Container/Content";
import ImageWrapper from "../../components/Container/ImageWrapper";
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
import VignetteWrapper from "../../components/Container/VignetteWrapper";
import HorizonVignette from "../../components/Container/HorizonVignette";
import InfoContainer from "../../components/Container/InfoContainer";
import styles from "./VideoDetail.module.css";

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

  return (
    <div>
      <TVContainer>
        <TVNav />
        <div style={{ paddingTop: "5rem" }}>
          <Content>
            <BillboardContainer>
              <ImageWrapper>
                <img
                  src="https://img2.sbs.co.kr/img/sbs_cms/WE/2022/02/07/NxE1644218336386.jpg"
                  alt="그 해 우리는"
                  className={`${styles.coverImage}`}
                />
                <VignetteWrapper className={styles.customVignetteWrapper} />
                <HorizonVignette className={styles.customHorizonVignette} />
                <InfoContainer>
                  <div className={`${styles.info}`}>
                    <div className={`${styles.vidTitle}`}>{video.title}</div>
                    {video.playtime && (
                      <div className={`${styles.videoDetail}`}>
                        재생 시간: {video.playtime}
                      </div>
                    )}
                    {video.audienceAge && (
                      <div className={`${styles.videoDetail}`}>
                        관람 등급: {video.audienceAge}+
                      </div>
                    )}
                  </div>
                </InfoContainer>
              </ImageWrapper>
            </BillboardContainer>
          </Content>
        </div>
      </TVContainer>
    </div>
  );
}
