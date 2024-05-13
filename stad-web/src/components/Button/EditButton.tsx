import { modifyAdvert } from "../../pages/AdEdit/AdDetailAPI";
import styles from "./Button.module.css";
import {useNavigate} from "react-router-dom";
export interface advertVideoUrlList {
  advertVideoId: number;
  advertVideoUrl: string;
}
export interface dataProps {
  advertId?: number;
  title?: string;
  description?: string;
  startDate?: string;
  endDate?: string;
  advertType?: string;
  advertCategory?: string;
  bannerImgUrl?: string;
  selectedContentList?: number[];
  advertVideoUrlList?: advertVideoUrlList[];
}
export function EditButton(formData: any) {
  const navigate = useNavigate();
  const handleEditClick = async () => {
    console.log(formData.formData)
    if (formData.formData && formData.formData.title != undefined && formData.formData.title != "" && formData.formData.advertCategory != undefined && formData.formData.advertCategory != ""
        && formData.formData.startDate != undefined && formData.formData.startDate != "" && formData.formData.endDate != undefined && formData.formData.endDate != ""
        && formData.formData.bannerImgUrl != undefined && formData.formData.bannerImgUrl != "" && formData.formData.advertVideoUrlList != undefined && formData.formData.advertVideoUrlList != null && formData.formData.advertVideoUrlList.length != 0) {
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
        수정 완료
      </button>
    </div>
  );
}

export function EditGoodsButton(formData: any, advertType: string) {
  const navigate = useNavigate();
  const modifyClick = async () => {
    console.log(formData.formData,formData.formData.advertType,formData.formData.productId)
    if (formData.formData && formData.formData.title != undefined && formData.formData.title != "" && formData.formData.advertCategory != undefined && formData.formData.advertCategory != ""
        && formData.formData.startDate != undefined && formData.formData.startDate != "" && formData.formData.endDate != undefined && formData.formData.endDate != ""
        && formData.formData.bannerImgUrl != undefined && formData.formData.bannerImgUrl != "" && formData.formData.advertVideoUrlList != undefined && formData.formData.advertVideoUrlList != null && formData.formData.advertVideoUrlList.length != 0) {
      if(formData.formData.advertType==="PRODUCT"){
        navigate("/modify-merchandise",{state:formData.formData.productId});
      }
      else{
        navigate("/",{state:formData})
      }
    }
    else{
      window.alert("필수 값을 입력하여 주십시오.")
    }
  };

  // 여기서 advertType 받아서 "NOTPRODUCT"면 비상품 수정 / 아니면 상품 수정으로 이동되게
  return (
    <div className={`${styles.enCon}`}>
      <button className={`${styles.navigate}`} onClick={modifyClick}>저장 후 <br />상품 / 링크 수정</button>
    </div>
  );
}
