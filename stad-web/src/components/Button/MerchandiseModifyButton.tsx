import styles from "./Button.module.css";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { useNavigate } from "react-router-dom";
import {modifyMerchandiseDetail} from "../../pages/AdEdit/MerchandiseDetailAPI";

interface MerchandiseModifyButtonProps {
    goodsFormData:
        | {
        productId?: number;
        name?: string;
        imgs?: string[];
        thumbnail?: String;
        cityDeliveryFee?: number;
        mtDeliveryFee?: number;
        expStart?: string;
        expEnd?: string;
        productTypeList?: ProductType[];
    }
        | undefined;
}

// 상품 interface
interface ProductType {
    id: number;
    productTypeId: number;
    productTypeName: string; // 상품명
    productTypePrice: number; // 상품 가격
    productTypeQuantity: number; // 재고 수량
    options: ProductOption[]; // 옵션 목록
}

// 옵션 interface
interface ProductOption {
    id: number;
    optionId: number;
    optionName: string; // 옵션명
    optionValue: number; // 옵션값
}
export default function MerchandiseModifyButton({
                                         goodsFormData,
                                     }: MerchandiseModifyButtonProps) {
    const navigate = useNavigate();
    const handleClick = () => {
        if(goodsFormData && goodsFormData.productId != null && goodsFormData.productId != undefined &&
            goodsFormData.name!=null && goodsFormData.name!=undefined && goodsFormData.name!="" &&
            goodsFormData.expStart!=null && goodsFormData.expStart!=undefined && goodsFormData.expStart!="" &&
            goodsFormData.expEnd!=null && goodsFormData.expEnd!=undefined && goodsFormData.expEnd!="" &&
            goodsFormData.imgs!=null && goodsFormData.imgs!=undefined && goodsFormData.imgs.length!=0 &&
            goodsFormData.cityDeliveryFee!=null && goodsFormData.cityDeliveryFee!=undefined &&
            goodsFormData.mtDeliveryFee!=null && goodsFormData.mtDeliveryFee!=undefined &&
            goodsFormData.thumbnail!=null && goodsFormData.thumbnail!=undefined && goodsFormData.thumbnail!="" &&
            goodsFormData.productTypeList!=null && goodsFormData.productTypeList!=undefined && goodsFormData.productTypeList.length!=0) {
            console.log("상품 수정");
            modifyMerchandiseDetail(goodsFormData);
            window.alert("상품 수정이 정상적으로 등록되었습니다.");
            navigate("/my-page/enroll-adList");
        }
        else{
            window.alert("필수 값을 입력하여 주십시오.")
        }
    };

    return (
        <div className={`${styles.enCon}`}>
            <button className={`${styles.enroll}`} onClick={handleClick}>
                등록
            </button>
        </div>
    );
}
