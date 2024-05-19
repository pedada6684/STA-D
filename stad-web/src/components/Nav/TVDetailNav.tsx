import { MouseEvent } from "react";
import { useNavigate } from "react-router-dom";
import back from "../../assets/system-uicons_wrap-back.png";
import styles from "./TVNav.module.css";
export default function TVDetailNav() {
  const navigate = useNavigate();
  const handleClick = (e: MouseEvent<HTMLButtonElement>) => {
    navigate(-1);
  };

  return (
    <div className={`${styles.container}`}>
      <div className={`${styles.goBack}`}>
        <button onClick={handleClick}>
          <img src={back} alt="뒤로 가기" />
        </button>
      </div>
    </div>
  );
}
