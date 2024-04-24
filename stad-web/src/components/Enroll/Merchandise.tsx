import { ChangeEvent, useState } from "react";
import styles from "./Advertisement.module.css";
import ToggleButton from "../Button/ToggleButton";
import InputContainer from "../Container/InputContainer";
import plus from "../../assets/plus.png";
import EnrollButton from "../Button/EnrollButton";
interface merchanForm {
  name: string;
  price: number;
  quantity: number;
  introduction: string;
  thumbnail: string;
  category: string; // 이거 말해서 빼야함
  option?: string[];
  // 상품 이미지들/추가로 필요한 데이터 넣을것
}
export default function Merchandise() {
  const [formData, setFormData] = useState<merchanForm>();
  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
  };
  const [isMerNameExpanded, setMerNameExpanded] = useState(false);
  const [isMerIntroductionExpanded, setMerIntroductionExpanded] =
    useState(false);
  const [isMerOptionExpanded, setMerOptionExpanded] = useState(false);
  const [isMerPriceExpanded, setMerPriceExpanded] = useState(false);
  const [isMerQuantityExpanded, setMerQuantityExpanded] = useState(false);
  const [isMerThumbnailExpanded, setMerThumbnailExpanded] = useState(false);
  const toggleMerName = () => setMerNameExpanded(!isMerNameExpanded);
  const toggleMerIntroduction = () =>
    setMerIntroductionExpanded(!isMerIntroductionExpanded);
  const toggleMerOptions = () => setMerOptionExpanded(!isMerOptionExpanded);
  const toggleMerPrice = () => setMerPriceExpanded(!isMerPriceExpanded);
  const toggleMerQuantity = () =>
    setMerQuantityExpanded(!isMerQuantityExpanded);
  const toggleMerThumbnail = () =>
    setMerThumbnailExpanded(!isMerThumbnailExpanded);

  return (
    <div className={`${styles.container}`}>
      <div className={`${styles.item}`}>
        <div className={`${styles.title}`}>
          <div className={`${styles.name}`}>
            상품명<span>*</span>
          </div>
          <ToggleButton
            isExpanded={isMerNameExpanded}
            onToggle={toggleMerName}
          />
        </div>
        {isMerNameExpanded && (
          <InputContainer>
            <div>
              <input
                type="text"
                name="merchandise-name"
                onChange={handleChange}
                className={`${styles.input}`}
                required
              />
            </div>
            <div className={`${styles.caution}`}>
              * 가이드에 맞지않은 상품명 입력 시 별도 고지없이 제재 될 수
              있습니다.
            </div>
          </InputContainer>
        )}
      </div>
      <div className={`${styles.item}`}>
        <div className={`${styles.title}`}>
          <div className={`${styles.name}`}>
            상품설명<span>*</span>
          </div>
          <ToggleButton
            isExpanded={isMerIntroductionExpanded}
            onToggle={toggleMerIntroduction}
          />
        </div>
        {isMerIntroductionExpanded && (
          <InputContainer>
            <div>
              <input
                type="text"
                name="merchandise-introduction"
                onChange={handleChange}
                className={`${styles.input}`}
                required
              />
            </div>
            <div className={`${styles.caution}`}>
              * 가이드에 맞지않은 상품설명 입력 시 별도 고지없이 제재 될 수
              있습니다.
            </div>
          </InputContainer>
        )}
      </div>
      <div className={`${styles.item}`}>
        <div className={`${styles.title}`}>
          <div className={`${styles.name}`}>
            상품 이미지 등록<span>*</span>
          </div>
          <ToggleButton
            isExpanded={isMerThumbnailExpanded}
            onToggle={toggleMerThumbnail}
          />
        </div>
        {isMerThumbnailExpanded && (
          <InputContainer>
            <div className={`${styles.merImage}`}>
              <div className={`${styles.subTitle}`}>상품 대표 이미지</div>
              <div className={`${styles.imageContainer}`}>
                <div className={`${styles.image}`}>
                  <input
                    type="file"
                    name="file"
                    id="file"
                    onChange={handleChange}
                    className={`${styles.imageInput} ${styles.input}`}
                    required
                  />
                  <label htmlFor="file" className={`${styles.btnUpload}`}>
                    <img src={plus} alt="업로드" />
                  </label>
                </div>
                <div className={`${styles.caution}`}>
                  * 가이드에 맞지않은 상품 이미지 등록 시 별도 고지없이 제재 될
                  수 있습니다.
                </div>
              </div>
            </div>
          </InputContainer>
        )}
      </div>
      <div className={`${styles.item}`}>
        <div className={`${styles.title}`}>
          <div className={`${styles.name}`}>옵션목록</div>
          <ToggleButton
            isExpanded={isMerOptionExpanded}
            onToggle={toggleMerOptions}
          />
        </div>
        {isMerOptionExpanded && (
          <InputContainer>
            <div>이 상품에 옵션을 등록하시겠습니까?</div>
            <div></div>
          </InputContainer>
        )}
      </div>
      <div className={`${styles.item}`}>
        <div className={`${styles.title}`}>
          <div className={`${styles.name}`}>판매가</div>
          <ToggleButton
            isExpanded={isMerPriceExpanded}
            onToggle={toggleMerPrice}
          />
        </div>
        {isMerPriceExpanded && (
          <InputContainer>
            <div>
              <input
                type="number"
                name="merchandise-price"
                onChange={handleChange}
                className={`${styles.input}`}
                required
              />
              <span>원</span>
            </div>
          </InputContainer>
        )}
      </div>
      <div className={`${styles.item}`}>
        <div className={`${styles.title}`}>
          <div className={`${styles.name}`}>재고수량</div>
          <ToggleButton
            isExpanded={isMerQuantityExpanded}
            onToggle={toggleMerQuantity}
          />
        </div>
        {isMerQuantityExpanded && (
          <InputContainer>
            <div>
              <input
                type="number"
                name="merchandise-quantity"
                onChange={handleChange}
                className={`${styles.input}`}
                required
              />
              <span>개</span>
            </div>
          </InputContainer>
        )}
      </div>
      <EnrollButton />
    </div>
  );
}
