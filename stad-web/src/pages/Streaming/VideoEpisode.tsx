import { useNavigate, useParams } from "react-router-dom";
import { SeriesDetailProps } from "./VideoDetail";
import styles from "./VideoEpisode.module.css";
interface VideoEpisodeProps extends SeriesDetailProps {
  thumbnailUrl: string;
}
export default function VideoEpisode(props: VideoEpisodeProps) {
  const navigate = useNavigate();
  const { videoId } = useParams<{ videoId: string }>();
  console.log(videoId);
  const detailId = Number(videoId);

  return (
    <div
      className={`${styles.episodeContainer}`}
      onClick={() => navigate(`/tv/stream/${detailId}`)}
    >
      <div className={`${styles.thumbnail}`}>
        <img src={props.thumbnailUrl} />
      </div>
      <div className={`${styles.metadata}`}>
        <div className={`${styles.episode}`}>{props.episode}</div>
        <div className={`${styles.summary}`}>{props.summary}</div>
      </div>
    </div>
  );
}
