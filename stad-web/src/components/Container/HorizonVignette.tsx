import styles from "./TVContainer.module.css";
export interface HorizonVignetteProps {
  className?: string;
  children?: React.ReactNode;
}

export default function HorizonVignette({
  className = "",
  children,
}: HorizonVignetteProps) {
  return (
    <div className={`${styles.horizonVignette} ${className}`}>{children}</div>
  );
}
