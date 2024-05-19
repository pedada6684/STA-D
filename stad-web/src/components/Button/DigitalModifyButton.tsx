import styles from "./Button.module.css";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { useNavigate } from "react-router-dom";
import {modifyAdvert} from "../../pages/AdEdit/AdDetailAPI";

export default function DigitalModifyButton(formData : any){
  const navigate = useNavigate();
  const handleEditClick = async () => {
    console.log(formData.formData)
    if (formData.formData && formData.formData.directVideoUrl !== "" && formData.formData.directVideoUrl !== null && formData.formData.directVideoUrl !== undefined) {
      const result = await modifyAdvert(formData);
      console.log(result);
      window.alert("광고 수정이 완료되었습니다.");
      navigate("/my-page/enroll-adList")
    }
    else{
      window.alert("필수 값을 입력하여 주십시오.")
    }
  };

  return (
    <div className={`${styles.enCon}`}>
      <button className={`${styles.enroll}`} onClick={handleEditClick}>
        등록
      </button>
    </div>
  );
}
