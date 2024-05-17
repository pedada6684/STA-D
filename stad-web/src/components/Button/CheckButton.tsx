import { useSelector } from "react-redux";
import { MouseEvent } from "react";

import { RootState } from "../../store";
import styles from "./Button.module.css";
import check from "../../assets/checkedmark.png";
import { deleteBookmark } from "../../pages/Streaming/StreamingAPI";

export interface CheckButtonProps {
  conceptId: number;
  onClick?: () => void;
}

const CheckButton: React.FC<CheckButtonProps> = ({ conceptId, onClick }) => {
  const token = useSelector((state: RootState) => state.token.accessToken);
  const userId = useSelector(
    (state: RootState) => state.tvUser.selectedProfile?.userId
  );

  const handleDeleteBookmark = (e: MouseEvent<HTMLButtonElement>) => {
    if (userId) {
      deleteBookmark(token, userId, conceptId);
    }
    if (onClick) onClick(); // onClick 함수가 존재하면 호출
  };

  return (
    <div className={`${styles.buttonWrapper}`}>
      <button
        className={`${styles.checkButton}`}
        onClick={handleDeleteBookmark}
      >
        <img src={check} alt="찜한 콘텐츠" />
      </button>
      <p>찜한 콘텐츠</p>
    </div>
  );
};

export default CheckButton;
