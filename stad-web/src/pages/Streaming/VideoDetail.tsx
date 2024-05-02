import { useNavigate, useParams } from "react-router-dom";
import Content from "../../components/Container/Content";
import TVContainer from "../../components/Container/TVContainer";
import TVNav from "../../components/Nav/TVNav";
import BillboardContainer from "../../components/Container/BillboardContainer";
import styles from "./VideoDetail.module.css";
import PlayButton from "../../components/Button/PlayButton";
import AddButton from "../../components/Button/AddButton";
import { useQuery } from "react-query";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { getVideoConcept, getVideoDetail } from "./StreamingAPI";
import Loading from "../../components/Loading";
import VideoEpisode from "./VideoEpisode";

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

export interface SeriesDetailProps {
  episode: number;
  summary: string;
  videoUrl: string;
}

export default function VideoDetail() {
  const navigate = useNavigate();

  // URL 에서 videoId 가져오기
  // URL에서 videoId 가져오기 및 변환
  const { videoId } = useParams<{ videoId: string }>();
  const detailId = Number(videoId);

  const token = useSelector((state: RootState) => state.token.accessToken);
  // 영화videoConceptData까지
  const { data: videoConceptData, isLoading } = useQuery(
    ["concept", token, detailId],
    () => getVideoConcept(token, detailId)
  );
  if (isLoading) {
    return <Loading />;
  }

  const handlePlayClick = () => {
    navigate(`/tv/stream/${detailId}`); // 스트리밍 페이지로 이동
  };

  const backgroundStyle = {
    backgroundImage: `linear-gradient(
      to top, rgba(0, 0, 0, 0.8) 2%, rgba(0, 0, 0, 0) 50%),
      linear-gradient(
      to right, rgba(0, 0, 0, 0.8) 20%, rgba(0, 0, 0, 0) 50%, rgba(0, 0, 0, 0.1) 100%),
      url(${videoConceptData.thumbnailUrl})`,
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
                      src={videoConceptData.thumbnailUrl}
                      alt={videoConceptData.title}
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
                    <div className={`${styles.vidTitle}`}>
                      {videoConceptData.title}
                    </div>
                    <div className={`${styles.timeAge}`}>
                      <span>{videoConceptData.playtime}</span>
                      <span>•</span>
                      <span>{videoConceptData.audienceAge}</span>
                    </div>
                    <div className={`${styles.bar}`}></div>
                    <div className={`${styles.buttonWrapper}`}>
                      <PlayButton onClick={handlePlayClick} />
                      <AddButton />
                    </div>
                    <div className={`${styles.description}`}>
                      {videoConceptData.description}
                    </div>
                    <div className={`${styles.staff}`}>
                      <span>크리에이터</span>
                      <span>{videoConceptData.creator}</span>
                    </div>
                    <div className={`${styles.staff}`}>
                      <span>출연</span>
                      <span>{videoConceptData.cast}</span>
                    </div>
                  </div>
                </div>
              </div>
            </BillboardContainer>
            {videoConceptData.data && videoConceptData.data.length > 0 && (
              <>
                {videoConceptData.data.map(
                  (data: SeriesDetailProps, index: number) => (
                    <>
                      {/* 객체 속성 직접 전달 */}
                      <VideoEpisode
                        key={data.episode}
                        {...data}
                        thumbnailUrl={videoConceptData.thumbnailUrl}
                      />
                    </>
                  )
                )}
              </>
            )}
          </Content>
        </div>
      </TVContainer>
    </div>
  );
}
