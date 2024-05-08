import styles from "./Button.module.css";
import {useSelector} from "react-redux";
import {RootState} from "../../store";
import {useNavigate} from "react-router-dom";

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
    goodsFormData : {
    advertId?: number;
    name?: string;
    imgs?: string[];
    thumbnail?: String;
    cityDeliveryFee?: number;
    mtDeliveryFee?: number;
    expStart?: string
    expEnd?: string;
    productTypeList?: ProductType[];
    } | undefined

}

// 상품 interface
interface ProductType {
    id: number;
    productTypeName: string; // 상품명
    productTypePrice: number; // 상품 가격
    productTypeQuantity: number; // 재고 수량
    options: ProductOption[]; // 옵션 목록
}

// 옵션 interface
interface ProductOption {
    id: number;
    optionName: string; // 옵션명
    optionValue: number; // 옵션값
}
export default function EnrollButton({from, formData, goodsFormData} : EnrollButtonProps) {
    const userId = useSelector((state: RootState)=> state.user.userId);
    const navigate = useNavigate();
    const handleClick = () => {
        if (from === "digital") {
            console.log(formData?.directVideoUrl)
            if (formData && formData.directVideoUrl!=undefined) {
                addAdvert(formData); // 광고 추가 요청
                navigate("/my-page/enroll-adList")
            } else {
                alert("필수 입력 값을 모두 입력해 주세요."); // 광고명이 비어있을 경우 경고창 표시
            }
        }
        else{
            console.log("상품 추가")
            addProduct(goodsFormData);
            navigate("/my-page/enroll-adList")
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

    const addProduct = async (data : any) => {
        if (!data || data.length === 0) return;
        const request= {
            'advertId' :data.advertId,
            'name' : data.name,
            'imgs' : data.imgs,
            'thumbnail' : data.thumbnail,
            'cityDeliveryFee' : data.cityDeliveryFee,
            'mtDeliveryFee' : data.mtDeliveryFee,
            'expStart' : data.expStart+"T00:00:00",
            'expEnd' : data.expEnd+"T00:00:00",
            'productTypeList' : data.productTypeList
        }

        try {
            const response = await fetch(`/api/product/regist`, {
                method: 'POST',
                body: JSON.stringify(request),
                headers: {
                    'Content-Type': 'application/json' // JSON 데이터를 전송한다고 명시
                }
            });
            if (!response.ok) {
                throw new Error('상품 생성 요청 실패');
            }
            const data = await response.json(); // JSON 형태로 응답 받음
            const result = data;
            console.log(result);
            return result;
        } catch (error) {
            console.error('상품 생성 요청 실패 : ', error);
            return null;
        }
    };

  return (
    <div className={`${styles.enCon}`}>
      <button className={`${styles.enroll}`} onClick={handleClick}>등록</button>
    </div>
  );
}
