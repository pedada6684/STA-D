import { useNavigate } from "react-router-dom";
import styles from "./VideoList.module.css";
import { Video } from "../Category/TVDetail";
import { searchProps } from "../Search/TVSearch";
interface VideoListProps {
  items: searchProps[];
}
export default function VideoList({ items = [] }: VideoListProps) {
  const navigate = useNavigate();
  return (
    <div className={`${styles.gridContainer}`}>
      {items.map((data, index) => (
        <div
          className={`${styles.thumbnail}`}
          key={index}
          onClick={() => navigate(`/tv/${data.conceptId}`)}
        >
          <img src={data.thumbnailUrl} alt="영상 썸네일" />
          <div className={`${styles.title}`}>{data.title}</div>
        </div>
      ))}
    </div>
  );
}
