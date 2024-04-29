import styles from "./Loading.module.css";
import { BeatLoader } from "react-spinners";

export default function Loading() {
  return (
    <div className={`${styles.container}`}>
      <div className={`${styles.loader}`}>
        <BeatLoader />
      </div>
    </div>
  );
}
