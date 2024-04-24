import top from "../../assets/flowbite_angle-top-solid.png";
import bottom from "../../assets/flowbite_angle-bottom-solid.png";
import styles from "./Button.module.css";

type ToggleButtonProps = {
  isExpanded: boolean;
  onToggle: () => void;
};
export default function ToggleButton({
  isExpanded,
  onToggle,
}: ToggleButtonProps) {
  const iconSrc = isExpanded ? bottom : top;
  const altText = isExpanded ? "접기" : "펼치기";
  return (
    <div>
      <button className={styles.toggleBtn} onClick={onToggle}>
        <img src={iconSrc} alt={altText} />
      </button>
    </div>
  );
}
