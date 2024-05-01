import styles from "./Button.module.css";
import { useNavigate } from "react-router-dom";
import {useSelector} from "react-redux";
import {RootState} from "../../store";
interface GoEnrollButtonProps {
  children: React.ReactNode;
  to: string;
  formData : {
  userId?: number;
  title?: string;
  description?: string;
  startDate?: string;
  endDate?: string;
  category?: string;
  directVideoUrl?: string;
  bannerImgUrl?: string;
  selectedContentList?: number[];
  advertVideoUrlList?: string[];
} | undefined
}
export default function GoEnrollButton({ children, to, formData }: GoEnrollButtonProps) {
  const navigate = useNavigate();
  const userId = useSelector((state: RootState)=> state.user.userId);
  const handleClick = () => {
    if(to==="/ad-enroll/merchandise"){
      addAdvert(formData);
    }
    navigate(to); // to 프로퍼티로 받은 경로로 이동
  };
  const addAdvert = async (data : any) => {
    if (!data || data.length === 0) return;
    const request= {
      'userId' :userId,
      'title' : data.title,
      'description' : "",
      'startDate' : data.startDate + "T00:00:00",
      'endDate' : data.endDate + "T00:00:00",
      'advertType' : "PRODUCT",
      'advertCategory' : data.category,
      'directVideoUrl' : "",
      'bannerImgUrl' : data.bannerImgUrl,
      'selectedContentList' : data.selectedContentList,
      'advertVideoUrlList' : data.advertVideoUrlList
    }

    console.log("###############################")
    console.log("formData ",request)

    try {
      const response = await fetch(`/api/advert`, {
        method: 'POST',
        body: JSON.stringify(request),
        headers: {
          'Content-Type': 'application/json' // JSON 데이터를 전송한다고 명시
        }
      });
      if (!response.ok) {
        throw new Error('광고 생성 요청 실패');
      }
      const data = await response.json(); // JSON 형태로 응답 받음
      const result = data;
      console.log(result);
      return result;
    } catch (error) {
      console.error('광고 생성 요청 실패 : ', error);
      return null;
    }
  };
  return (
    <div>
      <button className={styles.goButton} onClick={handleClick}>
        {children}
      </button>
    </div>
  );
}


