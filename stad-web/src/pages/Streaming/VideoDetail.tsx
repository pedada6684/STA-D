import { useNavigate, useParams } from "react-router-dom";
import Content from "../../components/Container/Content";
import TVContainer from "../../components/Container/TVContainer";
import TVNav from "../../components/Nav/TVNav";
import BillboardContainer from "../../components/Container/BillboardContainer";
import styles from "./VideoDetail.module.css";
import PlayButton from "../../components/Button/PlayButton";
import AddButton from "../../components/Button/AddButton";
import CheckButton from "../../components/Button/CheckButton";
import { useQuery } from "react-query";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { getVideoConcept, getIsBookmarked } from "./StreamingAPI";
import Loading from "../../components/Loading";
import VideoEpisode from "./VideoEpisode";
import { useEffect, useState } from "react";

export interface SeriesDetailProps {
  detailId: number;
  episode: number;
  summary: string;
  videoUrl: string;
}

export default function VideoDetail() {
  const navigate = useNavigate();
  const [thumbnailUrl, setThumbnailUrl] = useState("");
  const [title, setTitle] = useState("");
  const [releaseYear, setReleaseYear] = useState("");
  const [playtime, setPlaytime] = useState("");
  const [audienceAge, setAudienceAge] = useState("");
  const [isMovie, setIsMovie] = useState(false);
  const [description, setDescription] = useState("");
  const [creator, setCreator] = useState("");
  const [cast, setCast] = useState("");
  const [videoConceptData, setVideoConceptData] = useState<SeriesDetailProps[]>([]);
  const [isBookmarked, setIsBookmarked] = useState(false);

  // URL 에서 videoId 가져오기
  // URL에서 videoId 가져오기 및 변환
  const { videoId } = useParams<{ videoId: string }>();
  const conceptId = Number(videoId);

  const token = useSelector((state: RootState) => state.token.accessToken);
  // const userId = useSelector((state: RootState) => state.tvUser.userId);
  const userId = 1;

  const fetchVideoDetail = async () => {
    try {
      const response = await getVideoConcept(token, conceptId);
      console.log("response : ", response);
      setThumbnailUrl(response.thumbnailUrl);
      setTitle(response.title);
      setReleaseYear(response.releaseYear);
      setPlaytime(response.playtime);
      setAudienceAge(response.audienceAge);
      setIsMovie(response.movie);
      setDescription(response.description);
      setCreator(response.creator);
      setCast(response.cast);
      setVideoConceptData(response.data);
      console.log("영상 상세 정보 조회 완료");
    } catch (error) {
      console.error("영상 상세 정보 조회 실패", error);
    }
  };

  const fetchIsBookmarked = async () => {
    try {
      const response = await getIsBookmarked(token, userId, conceptId);
      console.log("response : ", response);
      setIsBookmarked(response.bookmarked);
      console.log("북마크 유무 조회 성공");
    } catch (error) {
      console.error("북마크 유무 조회 실패", error);
    }
  };

  useEffect(() => {
    fetchVideoDetail();
    fetchIsBookmarked();
  }, [token, userId, conceptId]);

  const handlePlayClick = () => {
    navigate(`/tv/stream/${conceptId}`); // 스트리밍 페이지로 이동
  };

  const backgroundStyle = {
    backgroundImage: `linear-gradient(
      to top, rgba(0, 0, 0, 0.8) 2%, rgba(0, 0, 0, 0) 50%),
      linear-gradient(
      to right, rgba(0, 0, 0, 0.8) 20%, rgba(0, 0, 0, 0) 50%, rgba(0, 0, 0, 0.1) 100%),
      url(${thumbnailUrl})`,
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
                  <div style={backgroundStyle} className={`${styles.coverImage}`}>
                    <img
                      src={thumbnailUrl}
                      alt={title}
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
                    <div className={`${styles.vidTitle}`}>{title}</div>
                    <div className={`${styles.timeAge}`}>
                      <span>{releaseYear}</span>
                      <span>•</span>
                      <span>{playtime}</span>
                      <span>•</span>
                      <span>{audienceAge}</span>
                    </div>
                    <div className={`${styles.bar}`}></div>
                    <div className={`${styles.buttonWrapper}`}>
                      {isMovie && <PlayButton onClick={handlePlayClick} />}
                      {/* 찜 여부로 바꾸기 */}
                      {isBookmarked ? (
                        <>
                          <CheckButton />
                        </>
                      ) : (
                        <>
                          <AddButton />
                        </>
                      )}
                    </div>
                    <div className={`${styles.description}`}>{description}</div>
                    <div className={`${styles.staff}`}>
                      <span>크리에이터</span>
                      <span>{creator}</span>
                    </div>
                    <div className={`${styles.staff}`}>
                      <span>출연</span>
                      <span>{cast}</span>
                    </div>
                  </div>
                </div>
              </div>
            </BillboardContainer>
            {videoConceptData && videoConceptData.length > 0 && (
              <>
                {videoConceptData.map((data: SeriesDetailProps, index: number) => (
                  <>
                    {/* 객체 속성 직접 전달 */}
                    {!isMovie && <VideoEpisode key={data.episode} {...data} thumbnailUrl={thumbnailUrl} />}
                  </>
                ))}
              </>
            )}
          </Content>
        </div>
      </TVContainer>
    </div>
  );
}
