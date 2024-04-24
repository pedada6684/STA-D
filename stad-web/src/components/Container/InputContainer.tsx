import styles from "./Container.module.css";
export default function InputContainer(props: any) {
  return <div className={`${styles.inputContainer}`}>{props.children}</div>;
}
