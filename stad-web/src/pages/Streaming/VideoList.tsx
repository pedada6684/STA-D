import styles from "./VideoList.module.css";
interface VideoListProps {
  items?: { url: string; title: string }[];
}
export default function VideoList({ items = [] }: VideoListProps) {
  return (
    <div className={`${styles.gridContainer}`}>
      {items.map((data, index) => (
        <div className={`${styles.thumbnail}`} key={index}>
          <img src={data.url} alt="영상 썸네일" />
          <div className={`${styles.title}`}>{data.title}</div>
        </div>
      ))}
    </div>
  );
}
