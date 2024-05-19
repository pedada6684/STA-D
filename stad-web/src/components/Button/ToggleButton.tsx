import top from "../../assets/flowbite_angle-top-solid.png";
import bottom from "../../assets/flowbite_angle-bottom-solid.png";
import styles from "./Button.module.css";

type ToggleButtonProps = {
  isExpanded: boolean;
  onToggle: () => void;
};
export default function ToggleButton() {
  // const iconSrc = isExpanded ? top : bottom;
  // const altText = isExpanded ? "접기" : "펼치기";
  return (
    <div>
      <button className={styles.toggleBtn}>
        <img src={top} alt={"오픈"} />
      </button>
    </div>
  );
}
