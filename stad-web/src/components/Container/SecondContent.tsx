import styles from "./Container.module.css";
interface SecondContentProps {
  children: React.ReactNode;
}
export default function SecondContent({ children }: SecondContentProps) {
  return <div className={`${styles.content2}`}>{children}</div>;
}
