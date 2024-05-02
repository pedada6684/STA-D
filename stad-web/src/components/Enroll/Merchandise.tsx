import {ChangeEvent, useEffect, useState} from "react";
import styles from "./Advertisement.module.css";
import ToggleButton from "../Button/ToggleButton";
import InputContainer from "../Container/InputContainer";
import plus from "../../assets/plus.png";
import EnrollButton from "../Button/EnrollButton";
import DateRange from "../Calendar/DateRange";
import CalendarIcon from "../Calendar/CalendarIcon";
interface merchanForm {
  name?: string;
  price?: number;
  quantity?: number;
  introduction?: string;
  thumbnail?: string;
  category?: string; // 이거 말해서 빼야함
  option?: string[];
  // 상품 이미지들/추가로 필요한 데이터 넣을것
}
export default function Merchandise() {
  const [formData, setFormData] = useState<merchanForm>();
  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  };
  const [isMerNameExpanded, setMerNameExpanded] = useState(false);
  const [isMerOptionExpanded, setMerOptionExpanded] = useState(false);
  const [isMerPriceExpanded, setMerPriceExpanded] = useState(false);
  const [isMerQuantityExpanded, setMerQuantityExpanded] = useState(false);
  const [isMerThumbnailExpanded, setMerThumbnailExpanded] = useState(false);
  const [isMerShipPriceExpanded, setMerShipPriceExpanded] = useState(false);
  const [isExpDateExpanded, setExpDateExpanded] = useState(false);
  const [isDeliveryDateExpanded, setDeliveryDateExpanded] = useState(false);
  const toggleMerName = () => setMerNameExpanded(!isMerNameExpanded);
  const toggleMerOptions = () => setMerOptionExpanded(!isMerOptionExpanded);
  const toggleMerPrice = () => setMerPriceExpanded(!isMerPriceExpanded);
  const toggleMerQuantity = () =>
    setMerQuantityExpanded(!isMerQuantityExpanded);
  const toggleMerThumbnail = () =>
    setMerThumbnailExpanded(!isMerThumbnailExpanded);
  const toggleMerShipPrice = () =>
    setMerShipPriceExpanded(!isMerShipPriceExpanded);
  const toggleExpDate = () => setExpDateExpanded(!isExpDateExpanded);
  const toggleDeliveryDate = () =>
    setDeliveryDateExpanded(!isDeliveryDateExpanded);
  const [startDate, setStartDate] = useState<Date | null>(new Date());
  const [endDate, setEndDate] = useState<Date | null>(new Date("2024/12/31"));

  useEffect(() => {
    console.log(formData)
  }, [formData]);
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
                name="name"
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
            <div className={`${styles.merDesImages}`}>
              <div className={`${styles.subTitle}`}>상품 설명 내용 이미지</div>
              <div className={`${styles.imageContainer}`}>
                <div className={`${styles.image}`}>
                  <input
                    type="file"
                    name="file"
                    id="file"
                    onChange={handleChange}
                    className={`${styles.imageInput} ${styles.input}`}
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
          <div className={`${styles.name}`}>
            판매가<span>*</span>
          </div>
          <ToggleButton
            isExpanded={isMerPriceExpanded}
            onToggle={toggleMerPrice}
          />
        </div>
        {isMerPriceExpanded && (
          <InputContainer>
            <div className={`${styles.sell}`}>
              <div className={`${styles.subTitle}`}>
                판매가<span>*</span>
              </div>
              <div className={`${styles.priceContainer}`}>
                <input
                  type="number"
                  name="merchandise-price"
                  onChange={handleChange}
                  className={`${styles.input}`}
                  required
                  placeholder="숫자만 입력"
                />
                <div className={`${styles.letter}`}>원</div>
              </div>
            </div>
            <div className={`${styles.sellPeriod}`}>
              <div className={`${styles.subTitle}`}>
                판매기간<span>*</span>
              </div>
              <div className={`${styles.sellContainer}`}>
                <div className={`${styles.calendar}`}>
                  <DateRange startDate={startDate}
                             endDate={endDate}
                             setStartDate={setStartDate}
                             setEndDate={setEndDate}/>
                </div>
              </div>
            </div>
          </InputContainer>
        )}
      </div>
      <div className={`${styles.item}`}>
        <div className={`${styles.title}`}>
          <div className={`${styles.name}`}>
            재고수량<span>*</span>
          </div>
          <ToggleButton
            isExpanded={isMerQuantityExpanded}
            onToggle={toggleMerQuantity}
          />
        </div>
        {isMerQuantityExpanded && (
          <InputContainer>
            <div className={`${styles.bond}`}>
              <input
                type="number"
                name="merchandise-quantity"
                onChange={handleChange}
                className={`${styles.input}`}
                required
                placeholder="숫자만 입력"
              />
              <div className={`${styles.letter}`}>개</div>
            </div>
          </InputContainer>
        )}
      </div>
      <div className={`${styles.item}`}>
        <div className={`${styles.title}`}>
          <div className={`${styles.name}`}>
            배송비<span>*</span>
          </div>
          <ToggleButton
            isExpanded={isMerShipPriceExpanded}
            onToggle={toggleMerShipPrice}
          />
        </div>
        {isMerShipPriceExpanded && (
          <InputContainer>
            <div className={`${styles.city}`}>
              <div className={`${styles.subTitle}`}>
                도심지역<span>*</span>
              </div>
              <div className={`${styles.priceContainer}`}>
                <input
                  type="number"
                  name="merchandise-shipping-price-city"
                  onChange={handleChange}
                  className={`${styles.input}`}
                  required
                  placeholder="숫자만 입력"
                />
                <div className={`${styles.letter}`}>원</div>
              </div>
            </div>
            <div className={`${styles.jejuMountain}`}>
              <div className={`${styles.subTitle}`}>
                제주, 산간지역<span>*</span>
              </div>
              <div className={`${styles.priceContainer}`}>
                <input
                  type="number"
                  name="merchandise-shipping-price-jeju"
                  onChange={handleChange}
                  className={`${styles.input}`}
                  required
                  placeholder="숫자만 입력"
                />
                <div className={`${styles.letter}`}>원</div>
              </div>
            </div>
          </InputContainer>
        )}
      </div>
      <div className={`${styles.item}`}>
        <div className={`${styles.title}`}>
          <div className={`${styles.name}`}>
            유통기한<span>*</span>
          </div>
          <ToggleButton
            isExpanded={isExpDateExpanded}
            onToggle={toggleExpDate}
          />
        </div>
        {isExpDateExpanded && (
          <InputContainer>
            <div className={`${styles.calendar}`}>
              <DateRange startDate={startDate}
                         endDate={endDate}
                         setStartDate={setStartDate}
                         setEndDate={setEndDate}/>
            </div>
          </InputContainer>
        )}
      </div>
      <div className={`${styles.item}`}>
        <div className={`${styles.title}`}>
          <div className={`${styles.name}`}>
            배송예정일 등록<span>*</span>
          </div>
          <ToggleButton
            isExpanded={isDeliveryDateExpanded}
            onToggle={toggleDeliveryDate}
          />
        </div>
        {isDeliveryDateExpanded && (
          <InputContainer>
            <div className={`${styles.calendar}`}>
              <CalendarIcon />
            </div>
          </InputContainer>
        )}
      </div>

      <EnrollButton formData={formData} from="merchandise"/>
    </div>
  );
}
