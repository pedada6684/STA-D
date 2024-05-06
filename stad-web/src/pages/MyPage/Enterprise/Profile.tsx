import styles from "./Profile.module.css";
import company from "../../../assets/profile_company.png";
import { MouseEvent, useEffect, useState } from "react";
import { EnterpriseData } from "./EnterprisesEdit";
import { profileImgUpload } from "./EnterpriseApi";
import { useSelector } from "react-redux";
import { RootState } from "../../../store";
import { useMutation } from "react-query";

interface ProfileProps {
  data?: EnterpriseData;
  onEditClick: (e: MouseEvent<HTMLDivElement>) => void;
  onSaveClick: (e: MouseEvent<HTMLDivElement>) => void;
  onFileSelect: (file: File) => void;
}

export default function Profile({
  data,
  onEditClick,
  onSaveClick,
  onFileSelect,
}: ProfileProps) {
  const [editing, setEditing] = useState(false); // 수정 or 저장 상태관리
  const [image, setImage] = useState(company); // 이미지 업로드 관리(URL 상태관리)
  const [previewUrl, setPreviewUrl] = useState("");
  const [fileKey, setFileKey] = useState(Date.now());
  const [selectedFile, setSelectedFile] = useState<File | null>(null);

  useEffect(() => {
    if (data?.profile instanceof File) {
      const url = URL.createObjectURL(data.profile);
      setImage(url);
    } else if (typeof data?.profile === "string") {
      setImage(data.profile);
    } else {
      setImage(company);
    }
  }, [data]);

  const handleImageChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      let profileImg = e.target.files[0]; // FileList에서 첫 번째 File 선택
      setSelectedFile(profileImg);
      const imgUrl = URL.createObjectURL(profileImg);
      setPreviewUrl(imgUrl);
      onFileSelect(profileImg);
    } else {
      // 파일 선택이 취소되었거나 파일이 선택되지 않았을 때 처리
      setSelectedFile(null);
      setPreviewUrl(""); // 미리보기 URL을 비워줌
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
                  key={fileKey}
                  type="file"
                  accept="image/*"
                  name="file"
                  id="file"
                  onChange={handleImageChange}
                  className={styles.imageInput}
                />
                <div className={`${styles.preview}`}>
                  {image && (
                    <img src={previewUrl} alt="preview-img" key={previewUrl} />
                  )}
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
