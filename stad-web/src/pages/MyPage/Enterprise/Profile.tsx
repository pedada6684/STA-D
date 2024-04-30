import styles from "./Profile.module.css";
import company from "../../../assets/profile_company.png";
import { MouseEvent, useState } from "react";
import { EnterpriseData } from "./EnterprisesEdit";
import { profileImgUpload } from "./EnterpriseApi";
import { useSelector } from "react-redux";
import { RootState } from "../../../store";

interface ProfileProps {
  data?: EnterpriseData;
  onEditClick: (e: MouseEvent<HTMLDivElement>) => void;
  onSaveClick: (e: MouseEvent<HTMLDivElement>) => void;
}

export default function Profile({
  data,
  onEditClick,
  onSaveClick,
}: ProfileProps) {
  const [editing, setEditing] = useState(false); // 수정 or 저장 상태관리
  const [image, setImage] = useState(company); // 이미지 업로드 관리
  const userId = useSelector((state: RootState) => state.user.userId);
  const handleImageChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      let profileImg = e.target.files[0]; // FileList에서 첫 번째 File 선택
      const res = await profileImgUpload(profileImg, userId);
      console.log(res);
      if (res) {
        console.log("이미지 url", res.profileImgUrl);
        setImage(res.profileImgUrl);
      }
    }
  };

  const toggleEditing = (e: React.MouseEvent<HTMLDivElement>) => {
    if (editing) {
      onSaveClick(e); // "저장" 버튼 클릭 시 이벤트
      setEditing(false);
    } else {
      onEditClick(e); // "수정" 버튼 클릭 시 이벤트
      setEditing(true);
    }
  };
  return (
    <div className={`${styles.profile}`}>
      <div className={`${styles.name}`}>
        <div className={styles.profileSection}>
          <div className={styles.circle}>
            {editing ? (
              <>
                <input
                  type="file"
                  accept="image/*"
                  name="file"
                  id="file"
                  onChange={handleImageChange}
                  className={styles.imageInput}
                />
                <div className={`${styles.preview}`}>
                  {image && <img src={image} alt="preview-img" />}
                </div>
              </>
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
        <div className={`${styles.title}`}>{data?.company}</div>
      </div>
      <div className={`${styles.edit}`} onClick={toggleEditing}>
        {editing ? "저장" : "수정"}
      </div>
    </div>
  );
}
