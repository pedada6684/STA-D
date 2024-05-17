import TVContainer from "../../components/Container/TVContainer";
import styles from "./ProfilePick.module.css";
import logo from "../../assets/tv_STA_D.png";
import { motion } from "framer-motion";
import { useNavigate } from "react-router";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../store";
import { tvUserActions } from "../../store/tvUser";
import plus from "../../assets/ph_plus.png";
import { useEffect } from "react";

interface Profile {
  userNickname: string;
  profile: string;
}

export default function ProfilePick() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const handleProfileClick = (userId: number, profile: Profile) => {
    dispatch(tvUserActions.setSelectedProfile({ userId, profile })); // 선택된 프로필을 Redux에 저장
    navigate("/tv-main");
  };
  const handleAddUser = () => {
    navigate("/tv-login"); // QR 로그인 페이지로 이동하여 새로운 유저 등록
  };
  const users = useSelector((state: RootState) => state.tvUser.users);
  useEffect(() => {}, [users]);
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
            <ul className={styles.chooseProfile}>
              {users &&
                users.length > 0 &&
                users.map((user) =>
                  user.profiles.map((profile) => (
                    <li
                      key={`${user.userId}-${profile.userNickname}`}
                      className={styles.profile}
                      onClick={() => handleProfileClick(user.userId, profile)}
                    >
                      <div className={styles.imgWrapper}>
                        <img
                          className={styles.profileImg}
                          src={profile.profile}
                          alt={`${profile.userNickname} 프로필`}
                        />
                      </div>
                      <div className={styles.name}>{profile.userNickname}</div>
                    </li>
                  ))
                )}
              <li className={styles.profile} onClick={handleAddUser}>
                <div className={styles.imgWrapper}>
                  <img
                    className={styles.addProfileImg}
                    src={plus} // 추가 프로필 아이콘 이미지 URL
                    alt="유저 추가"
                  />
                </div>
                <div className={styles.name}>유저 추가</div>
              </li>
            </ul>
          </div>
        </TVContainer>
      </motion.div>
    </div>
  );
}
