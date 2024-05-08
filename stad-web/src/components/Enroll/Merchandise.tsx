import { ChangeEvent, useEffect, useState } from "react";
import styles from "./Advertisement.module.css";
import ToggleButton from "../Button/ToggleButton";
import InputContainer from "../Container/InputContainer";
import plus from "../../assets/plus.png";
import whitep from "../../assets/ph_plus.png";
import EnrollButton from "../Button/EnrollButton";
import DateRange from "../Calendar/DateRange";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../store";
import close from "../../assets/material-symbols-light_close.png";
import { toggleActions } from "../../store/toggle";
import {
  ItemContainer,
  NameContainer,
  TitleContainer,
} from "../Container/EnrollContainer";
import {bannerImgUpload, productDetailImgUpload, productThumbnailUpload} from "../../pages/AdEnroll/AdEnrollApi";
import {useLocation} from "react-router-dom";

interface formData {
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
  expStart?: string
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

export default function Merchandise() {
  const location = useLocation();
  const advertId : number = location.state;
  const [formData, setFormData] = useState<formData>();
  const [goodsFormData, setGoodsFormData] = useState<goodsForm>();

  useEffect(() => {
    console.log(advertId);
    setGoodsFormData((prevState) => ({
      ...prevState,
      advertId: advertId,
    }));
  }, [advertId]);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setGoodsFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };
  // redux를 통한 토글 상태관리
  const dispatch = useDispatch();
  const toggles = useSelector((state: RootState) => state.toggle);
  const handleToggle = (toggleKey: string) => {
    dispatch(toggleActions.toggle(toggleKey));
  };
  // 토글 상태 갖고오기
  const isMerTitleExpanded = useSelector(
    (state: RootState) => state.toggle.isMerTitleExpanded
  );
  const isMerThumbnailExpanded = useSelector(
    (state: RootState) => state.toggle.isMerThumbnailExpanded
  );
  const isMerShipPriceExpanded = useSelector(
    (state: RootState) => state.toggle.isMerShipPriceExpanded
  );
  const isExpDateExpanded = useSelector(
    (state: RootState) => state.toggle.isExpDateExpanded
  );
  const isMerAddExpanded = useSelector(
    (state: RootState) => state.toggle.isMerAddExpanded
  );
  const [startDate, setStartDate] = useState<Date | null>(new Date());
  const [endDate, setEndDate] = useState<Date | null>(new Date("2024/12/31"));
  const [thumbnailImgUrl, setThumbnailImgUrl] = useState<string>("");
  const [thumbnailPreviewUrl, setThumbnailPreviewUrl] = useState<string | null>(
    null
  );
  const [detailImages, setDetailImages] = useState<File[]>([]);
  const [detailImagePreviews, setDetailImagePreviews] = useState<string[]>([]);
  useEffect(() => {
    console.log(goodsFormData);
  }, [goodsFormData]);

  useEffect(() => {
    if (startDate) {
      setGoodsFormData((prevState) => ({
        ...prevState,
        expStart: startDate.toISOString().split("T")[0],
      }));
    }
  }, [startDate]);
  useEffect(() => {
    if (endDate) {
      setGoodsFormData((prevState) => ({
        ...prevState,
        expEnd: endDate.toISOString().split("T")[0],
      }));
    }
  }, [endDate]);

  // 대표 이미지 추가
  const handleThumbnailImg = async (e: ChangeEvent<HTMLInputElement>) => {
    const thumbnailImgUrl = e.target.files;
    const data = await productThumbnailUpload(thumbnailImgUrl);
    console.log("대표 이미지:", data);
    setThumbnailImgUrl(data);
    setGoodsFormData((prevState) => ({
      ...prevState,
      thumbnail: data.data,
    }));
  };

  // 설명 이미지 추가
  const handleDetailImg = async (e: ChangeEvent<HTMLInputElement>) => {
    const imgList = e.target.files;
    const dataList = await productDetailImgUpload(imgList);
    console.log("상품 상세 이미지:", dataList);
    setDetailImages(dataList);
    setGoodsFormData((prevState) => ({
      ...prevState,
      imgs: dataList.data,
    }));
    console.log("이미지 리스트 추가", dataList);
  };

  const handleDetailImagesChange = (
    e: ChangeEvent<HTMLInputElement>,
    index: number
  ) => {
    if (e.target.files && e.target.files[0]) {
      const newFile = e.target.files[0];
      const updatedFiles = [...detailImages];
      updatedFiles[index] = newFile;
      setDetailImages(updatedFiles);

      // 미리보기 생성
      const reader = new FileReader();
      reader.onload = () => {
        const updatedPreviews = [...detailImagePreviews];
        updatedPreviews[index] = reader.result as string;
        setDetailImagePreviews(updatedPreviews);
      };
      reader.readAsDataURL(newFile);
    }
  };
  // 상품 상태관리
  const [products, setProducts] = useState<ProductType[]>([]);
  useEffect(() => {
    setGoodsFormData((prevState) => ({
      ...prevState,
      productTypeList: products,
    }));
  }, [products]);
  // 상품 추가
  const addProduct = () => {
    const newProduct = {
      id: Date.now(), // 고유 ID 생성
      productTypeName: "",
      productTypePrice: 0,
      productTypeQuantity: 0,
      options: [],
    };
    // 기존 상품 배열 + 스프레드 연산 사용해서 새 상품 추가해 새 배열 생성 후 setProducts에 담아줌
    setProducts([...products, newProduct]);
  };
  // 상품 삭제
  const removeProduct = (productId: number) => {
    setProducts(products.filter((product) => product.id !== productId)); // filter함수로 해당 ID랑 일치하지 않는 원소 추출해서 새로운 배열 만들기
  };

  // 옵션 추가
  const addOption = (productId: number) => {
    // 초기값 설정
    const newOption: ProductOption = { id: Date.now(), optionName: "", optionValue: 0 };
    setProducts(
      // 각 상품 확인해서 옵션 추가할 상품 Id랑 일치하는 경우 옵션 배열 추가
      products.map((product) =>
        product.id === productId
          ? // 스프레드 연산 사용해서 product 모든 요소 복사 해서 새 배열 생성
            // product 속성 중 하나인 options는 ...product.options + newOption로 새 값 설정
            { ...product, options: [...product.options, newOption] }
          : product
      )
    );
  };
  // 옵션 삭제
  const removeOption = (productId: number, optionId: number) => {
    setProducts(
      products.map((product) =>
        product.id === productId
          ? {
              ...product,
              options: product.options.filter(
                (option) => option.id !== optionId
              ),
            }
          : product
      )
    );
  };

  // 상품 정보 변경
  const handleProductChange = (
    e: ChangeEvent<HTMLInputElement>,
    productId: number
  ) => {
    const { name, value } = e.target;
    setProducts(
      products.map((product) =>
        product.id === productId ? { ...product, [name]: value } : product
      )
    );
  };

  // 옵션 정보 변경
  const handleOptionChange = (
    e: ChangeEvent<HTMLInputElement>,
    productId: number,
    optionId: number
  ) => {
    const { name, value } = e.target;
    setProducts(
      products.map((product) =>
        product.id === productId
          ? {
              ...product,
              options: product.options.map((option) =>
                option.id === optionId ? { ...option, [name]: value } : option
              ),
            }
          : product
      )
    );
  };
  return (
    <div className={`${styles.container}`}>
      <ItemContainer>
        <TitleContainer>
          <NameContainer>
            상품제목<span>*</span>
          </NameContainer>
          <ToggleButton
            isExpanded={toggles.isMerTitleExpanded}
            onToggle={() => handleToggle("isMerTitleExpanded")}
          />
        </TitleContainer>
        {isMerTitleExpanded && (
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
              * 가이드에 맞지않은 상품제목 입력 시 별도 고지없이 제재 될 수
              있습니다.
            </div>
          </InputContainer>
        )}
      </ItemContainer>
      <ItemContainer>
        <TitleContainer>
          <NameContainer>
            상품 이미지 등록<span>*</span>
          </NameContainer>
          <ToggleButton
            isExpanded={toggles.isMerThumbnailExpanded}
            onToggle={() => handleToggle("isMerThumbnailExpanded")}
          />
        </TitleContainer>
        {isMerThumbnailExpanded && (
          <InputContainer>
            <div className={`${styles.merImage}`}>
              <div className={`${styles.subTitle}`}>상품 대표 이미지</div>
              <div className={`${styles.imageContainer}`}>
                <div className={`${styles.image}`}>
                  {!thumbnailPreviewUrl ? (
                    <>
                      <input
                        type="file"
                        name="thumbnail"
                        id="thumbnail"
                        accept="image/gif, image/jpeg, image/jpg, image/png"
                        onChange={handleThumbnailImg}
                        className={`${styles.imageInput}`}
                      />
                      <label
                        htmlFor="thumbnail"
                        className={`${styles.btnUpload}`}
                      >
                        <img src={plus} alt="업로드" />
                      </label>
                    </>
                  ) : (
                    <div className={`${styles.image}`}>
                      <img
                        className={`${styles.preview}`}
                        src={thumbnailPreviewUrl}
                        alt="Thumbnail Preview"
                      />
                    </div>
                  )}
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
                {Array.from({ length: 1 }).map((_, index) => (
                  <div key={index} className={`${styles.imageUploadContainer}`}>
                    {detailImagePreviews[index] ? (
                      <div className={`${styles.imagePreviewContainer}`}>
                        <img
                          className={`${styles.preview}`}
                          src={detailImagePreviews[index]}
                          alt={`Detail ${index + 1}`}
                        />
                      </div>
                    ) : (
                      <div className={`${styles.image}`}>
                        <input
                          type="file"
                          name={`detailImage-${index}`}
                          id={`detailImage-${index}`}
                          multiple
                          required
                          accept="image/gif, image/jpeg, image/jpg, image/png"
                          onChange={(e) => handleDetailImg(e)}
                          className={`${styles.imageInput} ${styles.input}`}
                        />
                        <label
                          htmlFor={`detailImage-${index}`}
                          className={`${styles.btnUpload}`}
                        >
                          <img src={plus} alt="업로드" />
                        </label>
                      </div>
                    )}
                  </div>
                ))}
                <div className={`${styles.caution}`}>
                  * 가이드에 맞지않은 상품 이미지 등록 시 별도 고지없이 제재 될
                  수 있습니다.
                </div>
              </div>
            </div>
          </InputContainer>
        )}
      </ItemContainer>
      <ItemContainer>
        <TitleContainer>
          <NameContainer>
            배송비<span>*</span>
          </NameContainer>
          <ToggleButton
            isExpanded={toggles.isMerShipPriceExpanded}
            onToggle={() => handleToggle("isMerShipPriceExpanded")}
          />
        </TitleContainer>
        {isMerShipPriceExpanded && (
          <InputContainer>
            <div className={`${styles.city}`}>
              <div className={`${styles.subTitle}`}>
                도심지역<span>*</span>
              </div>
              <div className={`${styles.priceContainer}`}>
                <input
                  type="number"
                  name="cityDeliveryFee"
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
                  name="mtDeliveryFee"
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
      </ItemContainer>
      <ItemContainer>
        <TitleContainer>
          <NameContainer>
            유통기한<span>*</span>
          </NameContainer>
          <ToggleButton
            isExpanded={toggles.isExpDateExpanded}
            onToggle={() => handleToggle("isExpDateExpanded")}
          />
        </TitleContainer>
        {isExpDateExpanded && (
          <InputContainer>
            <div className={`${styles.calendar}`}>
              <DateRange
                startDate={startDate}
                endDate={endDate}
                setStartDate={setStartDate}
                setEndDate={setEndDate}
              />
            </div>
          </InputContainer>
        )}
      </ItemContainer>
      <ItemContainer>
        <TitleContainer>
          <NameContainer>
            상품 추가<span>*</span>
          </NameContainer>
          <ToggleButton
            isExpanded={toggles.isMerAddExpanded}
            onToggle={() => handleToggle("isMerAddExpanded")}
          />
        </TitleContainer>
        {!isMerAddExpanded && (
          <InputContainer>
            {products.map((product, index) => (
              <div key={product.id} className={`${styles.productContainer}`}>
                <div className={`${styles.productWrapper}`}>
                  <div className={`${styles.productInput}`}>
                    {/* 상품명 입력란 */}
                    <input
                      type="text"
                      name="productTypeName"
                      className={`${styles.input2}`}
                      value={product.productTypeName}
                      onChange={(e) => handleProductChange(e, product.id)}
                    />
                    <label>상품명</label>
                    <span></span>
                  </div>
                  <div className={`${styles.productInput}`}>
                    <input
                      type="number"
                      name="productTypePrice"
                      className={`${styles.input2}`}
                      value={product.productTypePrice}
                      onChange={(e) => handleProductChange(e, product.id)}
                    />
                    <label>판매가(KRW)</label>
                    <span></span>
                  </div>
                  {/* 재고 입력란 */}
                  <div className={`${styles.productInput}`}>
                    <input
                      type="number"
                      name="productTypeQuantity"
                      className={`${styles.input2}`}
                      value={product.productTypeQuantity}
                      onChange={(e) => handleProductChange(e, product.id)}
                    />
                    <label>재고</label>
                    <span></span>
                  </div>
                  {/* 상품 제거 버튼 */}
                  <div>
                    <button
                      className={`${styles.closeBtn}`}
                      onClick={() => removeProduct(product.id)}
                    >
                      <img
                        className={styles.icon}
                        src={close}
                        alt="상품 제거"
                      />
                    </button>
                  </div>
                </div>
                {/* 옵션 추가 버튼 */}
                <div className={`${styles.option}`}>
                  <div>
                    <button
                      className={`${styles.optionAdd}`}
                      onClick={() => addOption(product.id)}
                    >
                      <img src={whitep} alt="옵션 추가" /> 옵션 추가
                    </button>
                  </div>
                  {/* 옵션 목록 */}
                  <div className={`${styles.optionContainer}`}>
                    {product.options.map((option) => (
                      <div
                        key={option.id}
                        className={`${styles.optionWrapper}`}
                      >
                        <div className={`${styles.detailO} ${styles.borderR}`}>
                          <div className={`${styles.tt}`}>옵션명</div>
                          <div className={`${styles.optionInput}`}>
                            <input
                              type="text"
                              name="optionName"
                              className={`${styles.input3}`}
                              value={option.optionName}
                              onChange={(e) =>
                                handleOptionChange(e, product.id, option.id)
                              }
                              placeholder="Name"
                            />
                          </div>
                        </div>
                        <div className={`${styles.detailO} `}>
                          <div className={`${styles.tt} `}>옵션값</div>
                          <div className={`${styles.optionInput}`}>
                            <input
                              type="text"
                              name="optionValue"
                              value={option.optionValue}
                              className={`${styles.input3}`}
                              onChange={(e) =>
                                handleOptionChange(e, product.id, option.id)
                              }
                              placeholder="Value"
                            />
                          </div>
                        </div>
                        <div>
                          <button
                            className={`${styles.removeOptionBtn}`}
                            onClick={() => removeOption(product.id, option.id)}
                          >
                            <img src={close} alt="옵션 제거" />
                          </button>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            ))}
            {/* 상품 추가 버튼 */}
            <button className={`${styles.addProductBtn}`} onClick={addProduct}>
              상품 추가
            </button>
          </InputContainer>
        )}
      </ItemContainer>

      <EnrollButton goodsFormData={goodsFormData} formData={formData} from="merchandise" />
    </div>
  );
}
