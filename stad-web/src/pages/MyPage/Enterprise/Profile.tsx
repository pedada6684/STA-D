import styles from "./Profile.module.css";
import company from "../../../assets/profile_company.png";
import { MouseEvent, useState } from "react";

interface ProfileProps {
  onEditClick: (e: MouseEvent<HTMLDivElement>) => void;
}

export default function Profile({ onEditClick }: ProfileProps) {
  const [editing, setEditing] = useState(false); // 수정 or 저장 상태관리
  const [image, setImage] = useState(company); // 이미지 업로드 관리

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      let img = e.target.files[0];
      setImage(URL.createObjectURL(img));
    }
  };

  const toggleEditing = (e: React.MouseEvent<HTMLDivElement>) => {
    setEditing(!editing);
    if (!editing) onEditClick(e); // 수정 모드 아닐 때는 onEditClick() 호출 x
  };
  return (
    <div className={`${styles.profile}`}>
      <div className={`${styles.name}`}>
        <div className={styles.profileSection}>
          <div className={styles.circle}>
            {editing ? (
              <input
                type="file"
                name="file"
                id="file"
                onChange={handleImageChange}
                className={styles.imageInput}
              />
            ) : (
              <img src={image} alt="회사 로고" className={styles.icon} />
            )}
          </div>
          {editing && (
            <label htmlFor="file" className={styles.btnUpload}>
              업로드
            </label>
          )}
        </div>
        <div className={`${styles.title}`}>회사명</div>
      </div>
      <div className={`${styles.edit}`} onClick={toggleEditing}>
        {editing ? "저장" : "수정"}
      </div>
    </div>
  );
}
