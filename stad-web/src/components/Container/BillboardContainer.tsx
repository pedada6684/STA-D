import styles from "./TVContainer.module.css";
export default function BillboardContainer(props: any) {
  return <div className={`${styles.BillboardContainer}`}>{props.children}</div>;
}
