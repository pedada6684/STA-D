import styles from "./Container.module.css";
export default function Content(props: any) {
  return <div className={styles.content}>{props.children}</div>;
}
