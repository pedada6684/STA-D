import { MouseEvent, useState } from "react";
import EnterpriseInfo from "./EnterpriseInfo";
import styles from "./EnterprisePage.module.css";
import Profile from "./Profile";
import EnterpriseEdit from "./EnterprisesEdit";
export default function EnterprisePage() {
  const [editMode, setEditMode] = useState(false);
  const handleToggleEditMode = (e: MouseEvent<HTMLDivElement>) => {
    setEditMode(true);
  };

  // const handleSaveClick = (e: MouseEvent<HTMLDivElement>) => {
  //   setEditMode(false);
  // };
  return (
    <div className={styles.container}>
      <Profile onEditClick={handleToggleEditMode} />
      {editMode ? (
        <EnterpriseEdit onSaveClick={handleToggleEditMode} />
      ) : (
        <EnterpriseInfo />
      )}
    </div>
  );
}
