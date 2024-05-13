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
        console.log("상품 수정");
        modifyMerchandiseDetail(goodsFormData);
        window.alert("상품 수정이 정상적으로 등록되었습니다.");
        navigate("/my-page/enroll-adList");
    };

    return (
        <div className={`${styles.enCon}`}>
            <button className={`${styles.enroll}`} onClick={handleClick}>
                등록
            </button>
        </div>
    );
}
