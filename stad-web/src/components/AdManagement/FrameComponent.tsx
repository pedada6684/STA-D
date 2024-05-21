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

  // 숫자 포맷 함수
  const formatNumber = (num?: number) => {
    if (num === undefined) return "";
    return new Intl.NumberFormat().format(num);
  };
  // unit 값에 따라 다르게 렌더링
  const valueDisplay =
    unit === "건" ? (
      <>
        {formatNumber(value)}
        <span>{unit}</span>
      </>
    ) : (
      <>
        <span>{unit}</span>
        {formatNumber(value)}
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
