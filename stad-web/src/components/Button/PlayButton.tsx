import play from "../../assets/ion_play-sharp.png";
import styles from "./Button.module.css";
export default function PlayButton() {
  return (
    <div className={`${styles.buttonWrapper}`}>
      <button className={`${styles.playButton} ${styles.mainButton}`}>
        <img src={play} alt="재생" />
        <div>재생</div>
      </button>
    </div>
  );
}
