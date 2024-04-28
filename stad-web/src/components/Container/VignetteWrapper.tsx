import { HorizonVignetteProps } from "./HorizonVignette";
import styles from "./TVContainer.module.css";
interface VignetteWrapperProps {
  className?: string;
}
export default function VignetteWrapper({
  className = "",
}: VignetteWrapperProps) {
  return <div className={`${styles.vignetteWrapper} ${className}`}></div>;
}
