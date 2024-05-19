import { ChangeEvent, useEffect, useState } from "react";
import styles from "./Advertisement.module.css";
import ToggleButton from "../Button/ToggleButton";
import InputContainer from "../Container/InputContainer";
import EnrollButton from "../Button/EnrollButton";
import { useLocation } from "react-router-dom";
interface digitalForm {
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
}

interface goodsForm {
  advertId?: number;
  name?: string;
  imgs?: string[];
  thumbnail?: String;
  cityDeliveryFee?: number;
  mtDeliveryFee?: number;
  expStart?: string;
  expEnd?: string;
  productTypeList?: ProductType[];
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
export default function Digital() {
  const location = useLocation();
  const initialFormData: digitalForm = location.state;
  const [formData, setFormData] = useState<digitalForm>(initialFormData);
  const [goodsFormData, setGoofsFormData] = useState<goodsForm>();

  useEffect(() => {
    console.log("FormData on Digital page:", formData);
  }, [formData]);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: value,
    }));
  };

  const [isDigitalMainExpanded, setDigitalMainExpanded] = useState(false);

  const toggleDigitalMain = () =>
    setDigitalMainExpanded(!isDigitalMainExpanded);

  useEffect(() => {
    console.log("FormData on Digital page:", formData);
  }, []);

  return (
    <div className={`${styles.container}`}>
      <div className={`${styles.item}`}>
        <div className={`${styles.title}`}>
          <div className={`${styles.name}`}>
            메인링크<span>*</span>
          </div>
          <ToggleButton />
        </div>
        {!isDigitalMainExpanded && (
          <InputContainer>
            <div className={`${styles.linkUrl}`}>
              <div className={`${styles.subTitle}`}>연결 URL</div>
              <div>
                <input
                  type="text"
                  name="directVideoUrl"
                  onChange={handleChange}
                  className={`${styles.input}`}
                  required
                />
              </div>
            </div>
          </InputContainer>
        )}
      </div>
      <EnrollButton
        goodsFormData={goodsFormData}
        formData={formData}
        from="digital"
      />
    </div>
  );
}
