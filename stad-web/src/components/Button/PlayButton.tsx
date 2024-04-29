import play from "../../assets/ion_play-sharp.png";
import styles from "./Button.module.css";
interface PlayButtonProps {
  onClick?: () => void; // onClick prop 추가
}

export default function PlayButton({ onClick }: PlayButtonProps) {
  return (
    <div className={`${styles.buttonWrapper}`}>
      <button
        className={`${styles.playButton} ${styles.mainButton}`}
        onClick={onClick}
      >
        <img src={play} alt="재생" />
        <div>재생</div>
      </button>
    </div>
  );
}
