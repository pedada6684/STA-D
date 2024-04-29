import styles from "./TVContainer.module.css";
export default function ImageWrapper(props: any) {
  return <div className={`${styles.imageWrapper}`}>{props.children}</div>;
}
