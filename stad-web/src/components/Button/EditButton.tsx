import { modifyAdvert } from "../../pages/AdEdit/AdDetailAPI";
import styles from "./Button.module.css";
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
  const handleEditClick = async () => {
    console.log(formData);
    const result = await modifyAdvert(formData);
    console.log(result);
    window.alert("광고 수정이 완료되었습니다.");
  };

  return (
    <div className={`${styles.enCon}`}>
      <button className={`${styles.enroll}`} onClick={handleEditClick}>
        광고 수정완료
      </button>
    </div>
  );
}

export function EditGoodsButton(formData: any, advertType: string) {
  // 여기서 advertType 받아서 "NOTPRODUCT"면 비상품 수정 / 아니면 상품 수정으로 이동되게
  return (
    <div className={`${styles.enCon}`}>
      <button className={`${styles.navigate}`}>상품 수정으로 이동</button>
    </div>
  );
}
