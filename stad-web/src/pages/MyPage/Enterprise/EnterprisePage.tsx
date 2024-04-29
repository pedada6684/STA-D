import { MouseEvent, useCallback, useState } from "react";
import EnterpriseInfo from "./EnterpriseInfo";
import styles from "./EnterprisePage.module.css";
import Profile from "./Profile";
import EnterpriseEdit, { EnterpriseData } from "./EnterprisesEdit";
import { useSelector } from "react-redux";
import { RootState } from "../../../store";
import { useMutation } from "react-query";
import { postProfileEdit } from "./EnterpriseApi";
export default function EnterprisePage() {
  const [editMode, setEditMode] = useState(false);
  const [userData, setUserData] = useState<EnterpriseData>();
  const accessToken = useSelector(
    (state: RootState) => state.token.accessToken
  );
  const updateUserData = (updatedData: EnterpriseData) => {
    setUserData(updatedData); // 변경된 데이터를 저장
  };
  // useMutation을 이용하여 프로필 수정
  const mutation = useMutation(
    () => {
      if (!userData || !accessToken) {
        throw new Error("User data is undefined");
      }
      return postProfileEdit(userData, accessToken);
    },
    {
      onSuccess: (data) => {
        console.log("Update Success:", data);
        setEditMode(false);
      },
      onError: (error) => {
        console.error("Update Error:", error);
      },
    }
  );
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
    if (!accessToken) {
      console.error("Access token is null");
      return;
    }

    console.log("Saving data:", userData);
    mutation.mutate();
  }, [userData, accessToken]);

  return (
    <div className={styles.container}>
      <Profile
        data={userData}
        onEditClick={() => setEditMode(true)}
        onSaveClick={handleSaveClick}
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
