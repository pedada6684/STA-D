import { MouseEvent, useCallback, useState } from "react";
import EnterpriseInfo from "./EnterpriseInfo";
import styles from "./EnterprisePage.module.css";
import Profile from "./Profile";
import EnterpriseEdit, { EnterpriseData } from "./EnterprisesEdit";
import { useSelector } from "react-redux";
import { RootState } from "../../../store";
import { useMutation } from "react-query";
import { postProfileEdit } from "./EnterpriseApi";
import axios from "axios";
export default function EnterprisePage() {
  const [editMode, setEditMode] = useState(false);
  const [userData, setUserData] = useState<EnterpriseData>();
  const [profileFile, setProfileFile] = useState<File | null>(null);
  const accessToken = useSelector(
    (state: RootState) => state.token.accessToken
  );

  const handleFileSelect = (file: File) => {
    setProfileFile(file); // 선택된 파일 상태 업데이트
  };
  const updateUserData = (updatedData: EnterpriseData) => {
    setUserData(updatedData); // 변경된 데이터를 저장
  };

  const handleToggleEditMode = useCallback(
    (e: MouseEvent<HTMLDivElement>) => {
      setEditMode(!editMode);
    },
    [editMode]
  );
  const [data, setData] = useState<EnterpriseData>();

  const handleDataLoaded = useCallback((loadedData: EnterpriseData) => {
    setUserData(loadedData);
  }, []);

  const handleSaveClick = useCallback(async () => {
    console.log(userData);
    if (!accessToken) {
      console.error("토큰 누락");
      return;
    }
    if (!userData || !(userData.profile instanceof File)) {
      console.error("파일 선택X or 사용자 데이터 불완전");
    }
    const formData = new FormData();
    if (userData) {
      Object.keys(userData).forEach((key) => {
        const value = userData[key];
        if (
          value !== undefined &&
          value != null &&
          key !== "profile" &&
          key !== "email"
        ) {
          formData.append(key, value.toString());
        } else if (key === "department" && value == null) {
          formData.append(key, "");
        }
      });

      if (profileFile instanceof File) {
        // 파일 있는 경우 추가
        formData.append("profile", profileFile);
      } else {
        console.log("Profile is not a file", profileFile);
      }
    }

    console.log("수정할 데이터:", userData);
    formData.forEach((value, key) => {
      console.log(key, value);
    });
    try {
      const response = await axios.post(`/api/user/update`, formData, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "multipart/form-data",
        },
      });
      console.log("프로필 수정 완료", response.data);
      updateUserData({ ...userData, ...response.data });
      setEditMode(false);
    } catch (error) {
      console.error("업데이트 실패", error);
    }
  }, [userData, accessToken, updateUserData]);

  return (
    <div className={styles.container}>
      <Profile
        data={userData}
        onEditClick={() => setEditMode(true)}
        onSaveClick={handleSaveClick}
        onFileSelect={handleFileSelect}
      />
      {editMode ? (
        <EnterpriseEdit
          data={userData}
          onSaveClick={handleSaveClick}
          updateData={updateUserData}
        />
      ) : (
        <EnterpriseInfo onLoaded={handleDataLoaded} />
      )}
    </div>
  );
}
