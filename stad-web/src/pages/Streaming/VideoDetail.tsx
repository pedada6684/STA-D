import { useNavigate, useParams } from "react-router-dom";
import { KeyboardEvent, WheelEvent, useEffect, useRef, useState } from "react";
import { useSelector } from "react-redux";

import Content from "../../components/Container/Content";
import TVContainer from "../../components/Container/TVContainer";
import TVNav from "../../components/Nav/TVNav";
import BillboardContainer from "../../components/Container/BillboardContainer";
import styles from "./VideoDetail.module.css";
import PlayButton from "../../components/Button/PlayButton";
import AddButton from "../../components/Button/AddButton";
import CheckButton from "../../components/Button/CheckButton";
import { RootState } from "../../store";
import { getVideoConcept, getIsBookmarked } from "./StreamingAPI";
import VideoEpisode from "./VideoEpisode";
import TVDetailNav from "../../components/Nav/TVDetailNav";

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
  const [videoConceptData, setVideoConceptData] = useState<SeriesDetailProps[]>(
    []
  );
  const [isBookmarked, setIsBookmarked] = useState(false);

  // URL 에서 videoId 가져오기
  // URL에서 videoId 가져오기 및 변환
  const { videoId } = useParams<{ videoId: string }>();
  const conceptId = Number(videoId);

  const token = useSelector((state: RootState) => state.token.accessToken);
  const userId = useSelector(
    (state: RootState) => state.tvUser.selectedProfile?.userId
  );

  //스크롤 위한 useRef
  const scrollRef = useRef<HTMLDivElement>(null);
  const sectionRefs = [
    useRef<HTMLDivElement>(null),
    useRef<HTMLDivElement>(null),
  ];
  const [currentSection, setCurrentSection] = useState(0);
  const fetchVideoDetail = async () => {
    try {
      const response = await getVideoConcept(token, conceptId);
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
    } catch (error) {
      console.error("영상 상세 정보 조회 실패", error);
    }
  };

  const fetchIsBookmarked = async () => {
    try {
      if (userId) {
        const response = await getIsBookmarked(token, userId, conceptId);
        setIsBookmarked(response.bookmarked);
      }
    } catch (error) {
      console.error("북마크 유무 조회 실패", error);
    }
  };

  useEffect(() => {
    fetchVideoDetail();
    fetchIsBookmarked();
  }, [token, userId, conceptId]);

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  useEffect(() => {
    const smoothScrollToSection = (index: number) => {
      if (index >= 0 && index < sectionRefs.length) {
        sectionRefs[index].current?.scrollIntoView({
          behavior: "smooth",
        });
        setCurrentSection(index);
      }
    };

    const handleWheel = (event: WheelEvent) => {
      event.preventDefault();
      if (event.deltaY > 0 && currentSection < sectionRefs.length - 1) {
        smoothScrollToSection(currentSection + 1);
      } else if (event.deltaY < 0 && currentSection > 0) {
        smoothScrollToSection(currentSection - 1);
      }
    };

    const handleKeyDown = (event: unknown) => {
      const keyEvent = event as KeyboardEvent;
      if (
        keyEvent.key === "ArrowDown" &&
        currentSection < sectionRefs.length - 1
      ) {
        keyEvent.preventDefault();
        smoothScrollToSection(currentSection + 1);
      } else if (keyEvent.key === "ArrowUp" && currentSection > 0) {
        keyEvent.preventDefault();
        smoothScrollToSection(currentSection - 1);
      }
    };

    window.addEventListener("wheel", handleWheel as unknown as EventListener);
    window.addEventListener("keydown", handleKeyDown as EventListener);

    return () => {
      window.removeEventListener(
        "wheel",
        handleWheel as unknown as EventListener
      );
      window.removeEventListener("keydown", handleKeyDown as EventListener);
    };
  }, [currentSection]);

  const handlePlayClick = () => {
    navigate(`/tv/stream/${conceptId}`);
  };

  const backgroundStyle = {
    backgroundImage: `linear-gradient(
      to top, rgba(0, 0, 0, 0.8) 2%, rgba(0, 0, 0, 0) 50%),
      linear-gradient(
      to right, rgba(0, 0, 0, 0.8) 20%, rgba(0, 0, 0, 0) 50%, rgba(0, 0, 0, 0.1) 100%),
      url(${thumbnailUrl})`,
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat",
    width: "70%",
  };

  return (
    <div ref={scrollRef}>
      <TVContainer>
        <TVDetailNav />
        <div ref={sectionRefs[0]} style={{ paddingTop: "5rem" }}>
          <Content>
            <BillboardContainer>
              <div>
                <div className={`${styles.imgWrapper}`}>
                  <div
                    style={backgroundStyle}
                    className={`${styles.coverImage}`}
                  >
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
                      {isBookmarked ? (
                        <CheckButton
                          conceptId={conceptId}
                          onClick={() => setIsBookmarked(!isBookmarked)}
                        />
                      ) : (
                        <AddButton
                          conceptId={conceptId}
                          onClick={() => setIsBookmarked(!isBookmarked)}
                        />
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
            {!isMovie && videoConceptData && videoConceptData.length > 0 && (
              <div
                className={`${styles.episodeContainer}`}
                ref={sectionRefs[1]}
              >
                <div>
                  <h3 className={`${styles.epiTitle}`}>에피소드</h3>
                  <hr className={`${styles.epiTitle}`} />
                </div>
                {videoConceptData.map(
                  (data: SeriesDetailProps, index: number) => (
                    <VideoEpisode
                      key={data.episode}
                      {...data}
                      thumbnailUrl={thumbnailUrl}
                    />
                  )
                )}
              </div>
            )}
          </Content>
        </div>
      </TVContainer>
    </div>
  );
}
