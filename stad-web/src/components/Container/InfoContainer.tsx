import styles from "./TVContainer.module.css";
export default function InfoContainer(props: any) {
  return (
    <div className={`${styles.infoWrapper}`}>
      <div className={`${styles.infoLayer}`}>{props.children}</div>
    </div>
  );
}
