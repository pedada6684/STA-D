import styles from "./Button.module.css";
import { useNavigate } from "react-router-dom";
interface GoEnrollButtonProps {
  children: React.ReactNode;
  to: string;
}
export default function GoEnrollButton({ children, to }: GoEnrollButtonProps) {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(to); // to 프로퍼티로 받은 경로로 이동
  };
  return (
    <div>
      <button className={styles.goButton} onClick={handleClick}>
        {children}
      </button>
    </div>
  );
}
