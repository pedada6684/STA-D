import styles from "./TVContainer.module.css";
export default function HorizonVignette(props: any) {
  return <div className={`${styles.horizonVignette}`}>{props.children}</div>;
}
