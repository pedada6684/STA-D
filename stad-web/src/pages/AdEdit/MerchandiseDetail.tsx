import { ChangeEvent, useEffect, useRef, useState } from "react";
import styles from "../../components/Enroll/Merchandise.module.css";
import ToggleButton from "../../components/Button/ToggleButton";
import InputContainer from "../../components/Container/InputContainer";
import plus from "../../assets/plus.png";
import whitep from "../../assets/ph_plus.png";
import EnrollButton from "../../components/Button/EnrollButton";
import DateRange from "../../components/Calendar/DateRange";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../store";
import close from "../../assets/material-symbols-light_close.png";
import { toggleActions } from "../../store/toggle";
import {
  ItemContainer,
  NameContainer,
  TitleContainer,
} from "../../components/Container/EnrollContainer";
import {
  bannerImgUpload,
  productDetailImgUpload,
  productThumbnailUpload,
} from "../AdEnroll/AdEnrollApi";
import { useLocation } from "react-router-dom";
import edit from "../../assets/lucide_edit.png";
import { getMerchandiseDetail } from "./MerchandiseDetailAPI";
import MerchandiseModifyButton from "../../components/Button/MerchandiseModifyButton";
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
  productId?: number;
  name?: string;
  imgs?: string[];
  thumbnail?: string;
  cityDeliveryFee?: number;
  mtDeliveryFee?: number;
  expStart?: string;
  expEnd?: string;
  productTypeList?: ProductType[];
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

export default function Merchandise() {
  const location = useLocation();
  const productId: number = location.state;
  const [formData, setFormData] = useState<formData>();
  const [goodsFormData, setGoodsFormData] = useState<goodsForm>();
  const thumbnailInputRef = useRef<HTMLInputElement>(null);
  const detailImgInputRef = useRef<HTMLInputElement>(null);
  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getMerchandiseDetail(productId);
        if (data != null) {
          setGoodsFormData({
            productId: data.productId,
            name: data.name,
            imgs: data.images,
            thumbnail: data.thumbnail,
            cityDeliveryFee: data.cityDeliveryFee,
            mtDeliveryFee: data.mtDeliveryFee,
            expStart: data.expStart,
            expEnd: data.expEnd,
            productTypeList: data.productTypeList,
          });
          setThumbnailImgUrl(data.thumbnail);
          setThumbnailPreviewUrl(data.thumbnail);
          setDetailImages(data.images);
          setDetailImagePreviews(data.images);
          setProducts(data.productTypeList);
          console.log(data.expStart);
          console.log(data.expEnd);
          setStartDate(new Date(data.expStart));
          setEndDate(new Date(data.expEnd));
        }
      } catch (error) {
        console.error("상품 조회 실패", error);
      }
    };

    fetchData(); // fetchData 함수 호출
  }, [productId]);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setGoodsFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const [startDate, setStartDate] = useState<Date | null>(new Date());
  const [endDate, setEndDate] = useState<Date | null>(new Date());
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
    setThumbnailImgUrl(data.data);
    setGoodsFormData((prevState) => ({
      ...prevState,
      thumbnail: data.data,
    }));
    setThumbnailPreviewUrl(data.data);
  };

  // 설명 이미지 추가
  const handleDetailImg = async (e: ChangeEvent<HTMLInputElement>) => {
    const imgList = e.target.files;
    const dataList = await productDetailImgUpload(imgList);
    setDetailImages((prevImages) => [...prevImages, ...dataList.data]);
    console.log(dataList.data);
    setDetailImagePreviews((prevImages) => [...prevImages, ...dataList.data]);
  };

  const handleDetailImgDelete = (index: number) => {
    const filteredDetailImages = detailImages.filter((_, i) => i !== index);
    setDetailImages(filteredDetailImages);
    const filteredDetailImagePreviews = detailImagePreviews.filter(
      (_, i) => i !== index
    );
    setDetailImagePreviews(filteredDetailImagePreviews);
  };

  useEffect(() => {
    setGoodsFormData((prevState) => ({
      ...prevState,
      imgs: detailImagePreviews,
    }));
  }, [detailImagePreviews]);

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
      productTypeId: -1,
      productTypeName: "",
      productTypePrice: 0,
      productTypeQuantity: 0,
      options: [],
    };
    // 기존 상품 배열 + 스프레드 연산 사용해서 새 상품 추가해 새 배열 생성 후 setProducts에 담아줌
    setProducts([...products, newProduct]);
  };
  // 상품 삭제
  const removeProduct = (ptId: number, productId: number) => {
    setProducts(
      products.filter(
        (product) =>
          (product.id !== ptId && ptId !== undefined) ||
          product.productTypeId !== productId
      )
    ); // filter함수로 해당 ID랑 일치하지 않는 원소 추출해서 새로운 배열 만들기
  };

  // 옵션 추가
  const addOption = (ptId: number, productId: number) => {
    console.log(ptId, productId);
    // 초기값 설정
    const newOption: ProductOption = {
      id: Date.now(),
      optionId: -1,
      optionName: "",
      optionValue: 0,
    };
    setProducts(
      // 각 상품 확인해서 옵션 추가할 상품 Id랑 일치하는 경우 옵션 배열 추가
      products.map((product) =>
        product.id === ptId && ptId !== undefined
          ? // 스프레드 연산 사용해서 product 모든 요소 복사 해서 새 배열 생성
            // product 속성 중 하나인 options는 ...product.options + newOption로 새 값 설정
            { ...product, options: [...product.options, newOption] }
          : product.productTypeId === productId && productId !== undefined
          ? { ...product, options: [...product.options, newOption] }
          : product
      )
    );
  };
  // 옵션 삭제
  const removeOption = (
    ptId: number,
    productTypeId: number,
    oId: number,
    optionId: number
  ) => {
    setProducts(
      products.map((product) =>
        product.id === ptId && product.id !== null
          ? {
              ...product,
              options: product.options.filter((option) => option.id !== oId),
            }
          : product.productTypeId === productTypeId
          ? {
              ...product,
              option: product.options.filter(
                (option) => option.optionId !== optionId
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
  const handleBannerImgDelete = () => {
    setThumbnailImgUrl(""); // 이미지 URL 상태 초기화
    setThumbnailPreviewUrl(""); // 미리보기 상태 초기화
    setGoodsFormData((prevState) => ({
      ...prevState,
      bannerImgUrl: undefined,
    }));
  };

  // const handleGoodsImagesDelete = (index: number) => {
  //   // 상세 이미지 파일 배열에서 삭제
  //   const newDetailImages = detailImagePreviews.filter((_, i: number) => i !== index);
  //   setDetailImages(newDetailImages);

  //   // URL 배열에서 삭제
  //   if (goodsFormData && goodsFormData.imgs) {
  //     const newImgs = goodsFormData.imgs.filter((_, i) => i !== index);
  //     setGoodsFormData((prevState) => ({
  //       ...prevState,
  //       imgs: newImgs,
  //     }));
  //   }
  // };
  return (
    <div className={`${styles.container}`}>
      <div className={`${styles.enroll}`}>
        <span className={`${styles.enrollText}`}>상품 수정</span>
        <div className={`${styles.enrollLine}`} />
        <div className={`${styles.item}`}>
          <span className={`${styles.title} ${styles.title1}`}>
            상품명<span>*</span>
          </span>
          <InputContainer>
            <input
              type="text"
              name="name"
              value={`${goodsFormData?.name}`}
              onChange={handleChange}
              className={`${styles.input}`}
              required
            />
            <div className={`${styles.caution}`}>
              * 가이드에 맞지않은 상품제목 입력 시 별도 고지없이 제재 될 수
              있습니다.
            </div>
          </InputContainer>
        </div>
        <div className={`${styles.item}`}>
          <span className={`${styles.title}`}>
            상품 대표 이미지 등록<span>*</span>
          </span>
          <InputContainer>
            <div className={`${styles.merImage}`}>
              <div
                className={`${styles.imageContainer} ${styles.inputContainer}`}
              >
                <div className={`${styles.image} ${styles.prev}`}>
                  {!thumbnailPreviewUrl ? (
                    <>
                      <input
                        type="file"
                        ref={thumbnailInputRef}
                        name="thumbnail"
                        id="thumbnail"
                        accept="image/gif, image/jpeg, image/jpg, image/png"
                        onChange={handleThumbnailImg}
                        className={`${styles.imageInput}`}
                        required
                      />
                      <label
                        htmlFor="thumbnail"
                        className={`${styles.btnUpload}`}
                      >
                        <img src={plus} alt="업로드" />
                      </label>
                    </>
                  ) : (
                    <>
                      <img
                        className={`${styles.preview}`}
                        src={thumbnailPreviewUrl}
                        alt="Thumbnail Preview"
                      />
                      <button
                        onClick={() => thumbnailInputRef.current?.click()} // 버튼 클릭시 input 트리거
                        className={styles.overlayButton}
                      >
                        <img src={edit} alt="편집 버튼" />
                      </button>
                      <button
                        onClick={handleBannerImgDelete}
                        className={styles.deleteButton}
                      >
                        <img src={close} alt="Delete" />
                      </button>
                      <input
                        type="file"
                        ref={thumbnailInputRef}
                        name="thumbnail"
                        id="thumbnail"
                        accept="image/gif, image/jpeg, image/jpg, image/png"
                        onChange={handleThumbnailImg}
                        className={`${styles.imageInput}`}
                        required
                      />
                    </>
                  )}
                </div>
                <div className={`${styles.caution}`}>
                  * 가이드에 맞지않은 상품 이미지 등록 시 별도 고지없이 제재 될
                  수 있습니다.
                </div>
              </div>
            </div>
          </InputContainer>
        </div>
        <div className={`${styles.item}`}>
          <span className={`${styles.title}`}>
            상품 설명 이미지 등록<span>*</span>
          </span>
          <div className={`${styles.imageContainer}`}>
            {/* {Array.from({ length: 1 }).map((_, index) => (
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
                ))} */}
            <div
              className={
                detailImagePreviews.length > 0
                  ? `${styles.afterUpload}`
                  : `${styles.image}`
              }
            >
              {detailImagePreviews.length > 0 ? (
                <>
                  {detailImagePreviews.map((url, index) => (
                    <div
                      key={index}
                      className={`${styles.imagePreviewWrapper}`}
                    >
                      <div className={styles.imagePreviewContainer}>
                        <div>
                          <button
                            onClick={() => handleDetailImgDelete(index)}
                            className={`${styles.deleteVidButton}`}
                          >
                            <img src={close} alt="Delete" />
                          </button>
                        </div>
                        <div>
                          <img
                            src={url}
                            alt={`Detail ${index + 1}`}
                            className={styles.preview}
                          />
                        </div>
                      </div>
                    </div>
                  ))}

                  <button
                    className={styles.videoOverlay}
                    onClick={() =>
                      detailImgInputRef.current &&
                      detailImgInputRef.current.click()
                    }
                  >
                    <img src={edit} alt="Edit image" />
                  </button>
                  <input
                    ref={detailImgInputRef}
                    type="file"
                    name={`detailImage`}
                    id={`detailImage`}
                    multiple
                    required
                    accept="image/gif, image/jpeg, image/jpg, image/png"
                    onChange={(e) => handleDetailImg(e)}
                    className={styles.imageInput}
                  />
                </>
              ) : (
                <>
                  <input
                    type="file"
                    name="detailImage-0"
                    id="detailImage-0"
                    multiple
                    required
                    accept="image/gif, image/jpeg, image/jpg, image/png"
                    onChange={(e) => handleDetailImg(e)}
                    className={styles.imageInput}
                  />
                  <label htmlFor="detailImage-0" className={styles.btnUpload}>
                    <img src={plus} alt="Upload" />
                  </label>
                </>
              )}
            </div>
            <div className={`${styles.caution}`}>
              * 가이드에 맞지않은 상품 이미지 등록 시 별도 고지없이 제재 될 수
              있습니다.
            </div>
          </div>
        </div>

        <div className={`${styles.item}`}>
          <span className={`${styles.title2}`}>
            배송비<span>*</span>
          </span>
          <InputContainer>
            <div className={`${styles.deliveryWrapper}`}>
              <div className={`${styles.city}`}>
                <div className={`${styles.subTitle}`}>도심지역</div>
                <div className={`${styles.priceContainer}`}>
                  <input
                    type="number"
                    name="cityDeliveryFee"
                    value={`${goodsFormData?.cityDeliveryFee}`}
                    onChange={handleChange}
                    className={`${styles.deliInput}`}
                    required
                    placeholder="숫자만 입력"
                  />
                  <div className={`${styles.letter}`}>원</div>
                </div>
              </div>
              <div className={`${styles.jejuMountain}`}>
                <div className={`${styles.subTitle}`}>제주, 산간지역</div>
                <div className={`${styles.priceContainer}`}>
                  <input
                    type="number"
                    name="mtDeliveryFee"
                    value={`${goodsFormData?.mtDeliveryFee}`}
                    onChange={handleChange}
                    className={`${styles.deliInput}`}
                    required
                    placeholder="숫자만 입력"
                  />
                  <div className={`${styles.letter}`}>원</div>
                </div>
              </div>
            </div>
          </InputContainer>
        </div>
        <div className={`${styles.item}`}>
          <span className={`${styles.title2}`}>
            소비기한<span>*</span>
          </span>
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
        </div>
        <div className={`${styles.item}`}>
          <span className={`${styles.title2}`}>
            상품 추가<span>*</span>
          </span>

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
                      onClick={() =>
                        removeProduct(product.id, product.productTypeId)
                      }
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
                      onClick={() =>
                        addOption(product.id, product.productTypeId)
                      }
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
                            onClick={() =>
                              removeOption(
                                product.id,
                                product.productTypeId,
                                option.id,
                                option.optionId
                              )
                            }
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
        </div>

        <MerchandiseModifyButton goodsFormData={goodsFormData} />
      </div>
    </div>
  );
}
