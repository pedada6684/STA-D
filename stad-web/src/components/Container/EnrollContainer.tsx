import styles from "./EnrollContainer.module.css";
export function ItemContainer(props: any) {
  return <div className={`${styles.item}`}>{props.children}</div>;
}
export function TitleContainer(props: any) {
  return <div className={`${styles.title}`}>{props.children}</div>;
}
export function NameContainer(props: any) {
  return <div className={`${styles.name}`}>{props.children}</div>;
}
