import styles from "./FrameComponent.module.css";
interface FrameProps {
  icon: string;
  title: string;
  value?: number;
  className?: string;
  iconContainer?: string;
  unit: string;
}
export default function FrameComponent({
  icon,
  title,
  value,
  className,
  iconContainer,
  unit,
}: FrameProps) {
  const stylesContainer = className
    ? `${styles.container} ${styles[className] || ""}`
    : styles.container;
  const iconWrapper = iconContainer
    ? `${styles.iconWrapper} ${styles[iconContainer]}`
    : styles.iconWrapper;
  // unit 값에 따라 다르게 렌더링
  const valueDisplay =
    unit === "건" ? (
      <>
        {value}
        <span>{unit}</span>
      </>
    ) : (
      <>
        <span>{unit}</span>
        {value}
      </>
    );
  return (
    <div className={stylesContainer}>
      {/* <div className={iconWrapper}>
        <img src={icon} alt="아이콘" className={`${styles.icon}`} />
      </div> */}
      <div className={`${styles.textWrapper}`}>
        <div className={`${styles.title}`}>{title}</div>
        <div className={`${styles.value}`}>{valueDisplay}</div>
      </div>
    </div>
  );
}
