import TVContainer from "../../components/Container/TVContainer";
import styles from "./ProfilePick.module.css";
import logo from "../../assets/tv_STA_D.png";
import { motion } from "framer-motion";
import { useNavigate } from "react-router";
import { MouseEvent, useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
export default function ProfilePick() {
  const navigate = useNavigate();
  const userId = useSelector((state: RootState) => state.tvUser.userId);
  const userNickname = useSelector(
    (state: RootState) => state.tvUser.userNickname
  );
  const profile = useSelector((state: RootState) => state.tvUser.profile);
  const handleClickMain = (e: MouseEvent<HTMLLIElement>) => {
    navigate("/tv-main");
  };
  return (
    <div>
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
        transition={{ duration: 0.5 }}
      >
        <TVContainer>
          <div className={`${styles.listProfile}`}>
            <div className={`${styles.text}`}>
              <div>
                <span>
                  <img src={logo} alt="로고" className={`${styles.logo}`} />
                </span>
                를 시청할 프로필을 선택하세요.
              </div>
            </div>
            <ul className={`${styles.chooseProfile}`}>
              <li className={`${styles.profile}`} onClick={handleClickMain}>
                <div className={`${styles.imgWrapper}`}>
                  <img
                    className={`${styles.profileImg}`}
                    src={profile}
                    alt="게스트 프로필"
                  />
                </div>
                <div className={`${styles.name}`}>{userNickname}</div>
              </li>
            </ul>
          </div>
        </TVContainer>
      </motion.div>
    </div>
  );
}
