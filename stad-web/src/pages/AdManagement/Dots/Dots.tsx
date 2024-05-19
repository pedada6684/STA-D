import styles from "./Dots.module.css";
interface DotProps {
  num: number;
  scrollIdx: number;
}

const Dot = ({ num, scrollIdx }: DotProps) => {
  return (
    <div
      className={scrollIdx === num ? `${styles.dot} ${styles.on}` : styles.dot}
    >
      <div className={scrollIdx === num ? styles.boundary : ""}></div>
    </div>
  );
};

interface DotsProps {
  scrollIdx: number;
}

export default function Dots({ scrollIdx }: DotsProps) {
  return (
    <div className={`${styles.dotContainer}`}>
      <div className={`${styles.dotList}`}>
        <Dot num={1} scrollIdx={scrollIdx} />
        <Dot num={2} scrollIdx={scrollIdx} />
        <Dot num={3} scrollIdx={scrollIdx} />
        <Dot num={4} scrollIdx={scrollIdx} />
        <div className={`${styles.dotLine}`} />
      </div>
    </div>
  );
}
