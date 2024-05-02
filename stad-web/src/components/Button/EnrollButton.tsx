import styles from "./Button.module.css";
import {useSelector} from "react-redux";
import {RootState} from "../../store";

interface EnrollButtonProps {
    from : string;
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
    } | undefined;
}
export default function EnrollButton({from, formData} : EnrollButtonProps) {
    const userId = useSelector((state: RootState)=> state.user.userId);
    const handleClick = () => {
        if (from === "digital") {
            console.log(formData?.directVideoUrl)
            if (formData && formData.directVideoUrl!=undefined) {
                    addAdvert(formData); // 광고 추가 요청
            } else {
                alert("필수 입력 값을 모두 입력해 주세요."); // 광고명이 비어있을 경우 경고창 표시
            }
        }
    };
    const addAdvert = async (data : any) => {
        if (!data || data.length === 0) return;
        const request= {
            'userId' :userId,
            'title' : data.title,
            'description' : "",
            'startDate' : data.startDate + "T00:00:00",
            'endDate' : data.endDate + "T00:00:00",
            'advertType' : "NOTPRODUCT",
            'advertCategory' : data.category,
            'directVideoUrl' : data.directVideoUrl,
            'bannerImgUrl' : data.bannerImgUrl,
            'selectedContentList' : data.selectedContentList,
            'advertVideoUrlList' : data.advertVideoUrlList
        }

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
    <div className={`${styles.enCon}`}>
      <button className={`${styles.enroll}`} onClick={handleClick}>등록</button>
    </div>
  );
}
