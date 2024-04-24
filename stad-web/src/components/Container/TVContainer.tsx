import styles from "./TVContainer.module.css";
export default function TVContainer(props: any) {
  return <div className={`${styles.container}`}>{props.children}</div>;
}
