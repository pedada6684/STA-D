import { useNavigate, useParams } from "react-router-dom";
import { SeriesDetailProps } from "./VideoDetail";
import styles from "./VideoEpisode.module.css";
interface VideoEpisodeProps extends SeriesDetailProps {
  thumbnailUrl: string;
}
export default function VideoEpisode(props: VideoEpisodeProps) {
  const navigate = useNavigate();

  return (
    <>
      <hr className={`${styles.title}`} />
      <br />
      <div
        className={`${styles.episodeContainer}`}
        onClick={() => navigate(`/tv/stream/${props.detailId}`)}
      >
        <div className={`${styles.thumbnail}`}>
          <img src={props.thumbnailUrl} />
        </div>
        <div className={`${styles.metadata}`}>
          <div className={`${styles.episode}`}>{props.episode}화</div>
          <div className={`${styles.summary}`}>{props.summary}</div>
        </div>
      </div>
      <br />
    </>
  );
}
