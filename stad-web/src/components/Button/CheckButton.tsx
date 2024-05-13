import { useSelector } from "react-redux";
import { MouseEvent } from "react";

import { RootState } from "../../store";
import styles from "./Button.module.css";
import check from "../../assets/checkedmark.png";
import { deleteBookmark } from "../../pages/Streaming/StreamingAPI";

export default function CheckButton({ conceptId }: { conceptId: number }) {
  const token = useSelector((state: RootState) => state.token.accessToken);
  // const userId = useSelector((state: RootState) => state.tvUser.userId);
  const userId = 1;

  const handleDeleteBookmark = (e: MouseEvent<HTMLButtonElement>) => {
    deleteBookmark(token, userId, conceptId);
  };

  return (
    <div className={`${styles.buttonWrapper}`}>
      <button className={`${styles.checkButton}`} onClick={handleDeleteBookmark}>
        <img src={check} alt="찜한 콘텐츠" />
      </button>
      <p>찜한 콘텐츠</p>
    </div>
  );
}
