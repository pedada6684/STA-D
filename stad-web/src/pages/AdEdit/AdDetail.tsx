import { ChangeEvent, useEffect, useRef, useState } from "react";
import { useParams } from "react-router";
import Container from "../../components/Container/Container";
import WebNav from "../../components/Nav/WebNav";
import Content from "../../components/Container/Content";
import { useQuery } from "react-query";
import styles from "../../components/Enroll/Advertisement.module.css";
import ToggleButton from "../../components/Button/ToggleButton";
import InputContainer from "../../components/Container/InputContainer";
import { deleteAdvertVideo, getAdvertDetail } from "./AdDetailAPI";
import DateRange from "../../components/Calendar/DateRange";
import {
  SelectAdCategory,
  SelectContentsBox,
} from "../../components/Select/SelectBox";
import Modal from "../../components/Modal/Modal";
import { advertVideoUpload, bannerImgUpload } from "../AdEnroll/AdEnrollApi";
import edit from "../../assets/lucide_edit.png";
import plus from "../../assets/plus.png";
import close from "../../assets/material-symbols-light_close.png";
import {
  EditButton,
  EditGoodsButton,
  advertVideoUrlList,
  dataProps,
} from "../../components/Button/EditButton";

export default function AdDetail() {
  const { id } = useParams(); // 광고 ID 파라미터 URL에서 가져오기
  const advertId = Number(id);
  const [formData, setFormData] = useState<dataProps>();

  const [modalIsOpen, setModalIsOpen] = useState(false);
  const openModal = () => setModalIsOpen(true);
  const closeModal = () => setModalIsOpen(false);

  const [videoUrlList, setVideoUrlList] = useState<string[]>([]);
  const [bannerImgUrl, setBannerImgUrl] = useState<string>("");
  const [bannerImgPreview, setBannerImgPreview] = useState<string>("");
  const [startDate, setStartDate] = useState<Date | null>(new Date());
  const [endDate, setEndDate] = useState<Date | null>(new Date("2024/12/31"));
  const [category, setCategory] = useState<string | null>("");
  const [contentId, setContentId] = useState<
    { value: number; label: string }[] | null
  >([]);
  const {
    data: ad,
    error,
    isLoading,
  } = useQuery(["adDetail", id], () => getAdvertDetail(advertId), {});

  useEffect(() => {
    if (ad) {
      // formData fetch한 데이터로 초기값 설정해주기
      const initialFormData = {
        productId: ad.productId,
        advertId: advertId,
        title: ad.title,
        description: ad.description,
        startDate: ad.startDate,
        endDate: ad.endDate,
        advertCategory: ad.category,
        advertType: ad.type,
        bannerImgUrl: ad.bannerImgUrl,
        directVideoUrl: ad.directVideoUrl,
        advertVideoUrlList: ad.advertVideoUrlList,
        selectedContentList: ad.selectedContentList,
      };
      console.log(ad.selectedContentList);
      setBannerImgPreview(ad.bannerImgUrl);
      setStartDate(new Date(ad.startDate));
      setEndDate(new Date(ad.endDate));
      setFormData(initialFormData);
    }
  }, [ad]);

  useEffect(() => {
    console.log("formData updated:", formData);
  }, [formData]);
  useEffect(() => {
    if (category) {
      setFormData((prevState) => ({
        ...prevState,
        advertCategory: category,
      }));
    }
  }, [category]);

  useEffect(() => {
    const updatedFormData = { ...formData };
    // 카테고리 업데이트
    if (contentId) {
      setFormData((prevState) => ({
        ...prevState,
        selectedContentList: [...contentId.map((option) => option.value)],
      }));
    }
  }, [contentId]);

  useEffect(() => {
    if (startDate || endDate) {
      setFormData((prevState) => ({
        ...prevState,
        startDate: startDate
          ? startDate.toISOString().split("T")[0]
          : prevState?.startDate,
        endDate: endDate
          ? endDate.toISOString().split("T")[0]
          : prevState?.endDate,
      }));
    }
  }, [startDate, endDate]);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };
  const handleAdvertVideoList = async (e: ChangeEvent<HTMLInputElement>) => {
    const videoList = e.target.files;
    const responseData = await advertVideoUpload(videoList);
    const videoUrls = responseData.map((video: any) => video.videoUrl);
    setFormData((prevState) => ({
      ...prevState,
      advertVideoUrlList: [
        ...(prevState?.advertVideoUrlList ?? []),
        ...videoUrls.map((url: string) => ({
          advertVideoId: -1,
          advertVideoUrl: url,
        })),
      ],
    }));
    setVideoUrlList(videoUrls);
  };

  // 비디오 파일 입력과 이미지 파일 입력에 대해 서로 다른 ref 사용
  const videoInputRef = useRef<HTMLInputElement>(null);
  const imageInputRef = useRef<HTMLInputElement>(null);

  const handleBannerImg = async (e: ChangeEvent<HTMLInputElement>) => {
    const bannerImgUrl = e.target.files;
    const responseData = await bannerImgUpload(bannerImgUrl);
    console.log(`배너이미지 : `, responseData);
    setBannerImgUrl(responseData);
    setFormData((prevState) => ({
      ...prevState,
      bannerImgUrl: responseData.bannerUrl,
    }));
    setBannerImgPreview(responseData.bannerUrl); // 미리보기 생성
  };

  const handleAdvertVideoDelete = async (advertVideoId: number) => {
    console.log(advertVideoId);
    try {
      const deleteVideo = await deleteAdvertVideo(advertVideoId);
      console.log(deleteVideo);
      // 삭제 성공 후 formData 상태 업데이트
      setFormData((prevState) => ({
        ...prevState,
        advertVideoUrlList: prevState?.advertVideoUrlList?.filter(
          (video) => video.advertVideoId !== advertVideoId
        ),
      }));
    } catch (error) {
      console.error("삭제실패", error);
    }
  };
  const handleBannerImgDelete = () => {
    setBannerImgUrl(""); // 이미지 URL 상태 초기화
    setBannerImgPreview(""); // 미리보기 상태 초기화
    setFormData((prevState) => ({
      ...prevState,
      bannerImgUrl: undefined,
    }));
  };
  const handleAddVideoDelete = (index: number) => {
    const filteredVideos = videoUrlList.filter((_, i) => i !== index);
    setVideoUrlList(filteredVideos);
    setFormData((prevState) => ({
      ...prevState,
      advertVideoUrlList: filteredVideos.map((url) => ({
        advertVideoId: -1,
        advertVideoUrl: url,
      })),
    }));
  };
  return (
    <div>
      <Container>
        <WebNav />
        <Content>
          <div className={`${styles.container}`}>
            <div className={`${styles.item}`}>
              <div className={`${styles.title}`}>
                <div className={`${styles.name}`}>
                  광고명<span>*</span>
                </div>
                <ToggleButton />
              </div>
              <InputContainer>
                <div>
                  <input
                    type="text"
                    name="title"
                    onChange={handleChange}
                    defaultValue={ad?.title}
                    className={`${styles.input}`}
                    required
                  />
                </div>
                <div className={`${styles.caution}`}>
                  *가이드에 맞지않은 광고명 입력 시 별도 고지없이 제재될 수
                  있습니다.
                </div>
              </InputContainer>
            </div>
            <div className={`${styles.item}`}>
              <div className={`${styles.title}`}>
                <div className={`${styles.name}`}>
                  광고영상<span>*</span>
                </div>
                <ToggleButton />
              </div>
              <InputContainer>
                <div className={`${styles.advideoList}`}>
                  <div className={`${styles.subTitle}`}>TV 광고 영상</div>
                  <div className={`${styles.videoContainer}`}>
                    <div className={`${styles.afterUpload}`}>
                      {videoUrlList.length > 0 ? (
                        <>
                          {formData?.advertVideoUrlList?.map((url, index) => (
                            <div
                              key={index}
                              className={`${styles.videoPreview}`}
                            >
                              <video
                                className={`${styles.videoPrev}`}
                                controls
                                src={url.advertVideoUrl}
                                style={{ width: "100%" }}
                              />
                              <button
                                onClick={() => handleAddVideoDelete(index)}
                                className={`${styles.deleteVidButton}`}
                              >
                                <img src={close} alt="Delete" />
                              </button>
                            </div>
                          ))}
                          <div className={`${styles.video}`}>
                            <input
                              ref={videoInputRef}
                              type="file"
                              name="videoList"
                              id="videoList"
                              accept="video/*"
                              multiple
                              required
                              onChange={handleAdvertVideoList}
                              className={`${styles.videoInput} ${styles.input}`}
                              style={{ display: "none" }}
                            />
                            <label
                              htmlFor="videoList"
                              className={styles.btnUpload}
                            >
                              <img src={plus} alt="Upload" />
                            </label>
                          </div>
                        </>
                      ) : (
                        <>
                          {/* 기존 advertVideoUrlList 조회해서 보여주기 */}
                          {formData?.advertVideoUrlList?.map(
                            (url: advertVideoUrlList, index: number) => (
                              <div
                                key={url.advertVideoId}
                                className={`${styles.videoPreview}`}
                              >
                                <video
                                  className={`${styles.videoPrev}`}
                                  controls
                                  src={url.advertVideoUrl}
                                  style={{ width: "100%" }}
                                />
                                <button
                                  onClick={() =>
                                    handleAdvertVideoDelete(url.advertVideoId)
                                  }
                                  className={`${styles.deleteVidButton}`}
                                >
                                  <img src={close} alt="Delete" />
                                </button>
                              </div>
                            )
                          )}
                          <div className={`${styles.video}`}>
                            <input
                              ref={videoInputRef}
                              type="file"
                              name="videoList"
                              id="videoList"
                              accept="video/*"
                              multiple
                              required
                              onChange={handleAdvertVideoList}
                              className={`${styles.videoInput} ${styles.input}`}
                              style={{ display: "none" }}
                            />
                            <label
                              htmlFor="videoList"
                              className={styles.btnUpload}
                            >
                              <img src={plus} alt="Upload" />
                            </label>
                          </div>
                        </>
                      )}
                    </div>
                    <div className={`${styles.caution}`}>
                      * 가이드에 맞지않은 영상 등록 시 별도 고지없이 제재 될 수
                      있습니다.
                    </div>
                    <div className={`${styles.caution2}`}>
                      TV 광고 영상은 15초, 30초, 45초, 60초 옵션으로 등록할 수
                      있으며, 각각 다른 길이를 가진 광고 영상 여러개를 업로드할
                      수 있습니다.
                    </div>
                  </div>
                </div>
                <div className={`${styles.adBanner}`}>
                  <div className={`${styles.subTitle}`}>
                    앱 광고 배너 이미지
                  </div>
                  <div
                    className={`${styles.imageContainer} ${styles.inputContainer}`}
                  >
                    <div className={`${styles.bImage} ${styles.prev}`}>
                      {!bannerImgPreview ? (
                        <>
                          <input
                            type="file"
                            name="file"
                            id="fileInput" // 동일한 input ID를 유지
                            accept="image/gif, image/jpeg, image/jpg, image/png"
                            ref={imageInputRef}
                            onChange={handleBannerImg}
                            style={{ display: "none" }}
                            required
                          />
                          <label
                            htmlFor="fileInput"
                            className={`${styles.btnUpload}`}
                          >
                            <img src={plus} alt="Upload" />
                          </label>
                        </>
                      ) : (
                        <>
                          {/* <img
                            src={bannerImgPreview}
                            alt="Preview"
                            className={styles.imgPrev}
                          />
                          <button
                            onClick={() => imageInputRef.current?.click()} // 버튼 클릭시 input 트리거
                            className={styles.overlayButton}
                          >
                            <img src={edit} alt="편집 버튼" />
                          </button> */}
                          <button
                            onClick={handleBannerImgDelete}
                            className={`${styles.deleteImgButton}`}
                          >
                            <img src={close} alt="Delete" />
                          </button>
                          <img
                            className={`${styles.bannerImg}`}
                            src={bannerImgPreview}
                            alt="기존이미지"
                          />
                          <button
                            onClick={() => imageInputRef.current?.click()} // 버튼 클릭시 input 트리거
                            className={styles.overlayButton}
                          >
                            <img src={edit} alt="편집 버튼" />
                          </button>
                          <input
                            type="file"
                            name="file"
                            id="fileInput" // 파일 input ID 설정
                            ref={imageInputRef}
                            accept="image/gif, image/jpeg, image/jpg, image/png"
                            onChange={handleBannerImg}
                            style={{ display: "none" }}
                            required
                          />
                        </>
                      )}
                    </div>
                    <div className={`${styles.caution}`}>
                      * 가이드에 맞지않은 배너 이미지 등록 시 별도 고지없이 제재
                      될 수 있습니다.
                    </div>
                    <div className={`${styles.caution2}`}>
                      앱 광고용 영상은 풀 버전으로 업로드해 주세요.
                    </div>
                  </div>
                </div>
              </InputContainer>
            </div>
            <div className={`${styles.item}`}>
              <div className={`${styles.title}`}>
                <div className={`${styles.name}`}>
                  광고기간<span>*</span>
                </div>
                <ToggleButton />
              </div>
              <InputContainer>
                <div className={`${styles.calendar}`}>
                  <DateRange
                    startDate={startDate}
                    endDate={endDate}
                    setStartDate={setStartDate}
                    setEndDate={setEndDate}
                  />
                </div>
                <div className={`${styles.caution}`}>
                  *계약 기간 만료시 광고는 자동으로 삭제됩니다.
                </div>
              </InputContainer>
            </div>
            <div className={`${styles.item}`}>
              <div className={`${styles.title}`}>
                <div className={`${styles.name}`}>
                  광고 카테고리<span>*</span>
                </div>
                <ToggleButton />
              </div>
              <InputContainer>
                <div className={`${styles.selectBox}`}>
                  <SelectAdCategory
                    initialCategory={ad?.category}
                    setCategory={setCategory}
                  />
                </div>
                <div className={`${styles.caution}`}>
                  *각 광고 카테고리에 맞는 영상을 매칭해드립니다.
                </div>
              </InputContainer>
            </div>
            <div className={`${styles.item}`}>
              <div className={`${styles.title}`}>
                <div className={`${styles.name}`}>노출 컨텐츠(선택)</div>
                <ToggleButton />
              </div>
              <InputContainer>
                <div>
                  <button className={`${styles.contents}`} onClick={openModal}>
                    컨텐츠 선택하기
                  </button>
                  <Modal isOpen={modalIsOpen} onRequestClose={closeModal}>
                    <div className={`${styles.modalContent}`}>
                      <div className={`${styles.modalTitle}`}>
                        컨텐츠 선택하기
                      </div>
                      <SelectContentsBox
                        contentId={contentId}
                        setContentId={setContentId}
                        existedData={ad?.selectedContentList}
                      />
                    </div>
                  </Modal>
                </div>
                <div className={`${styles.caution}`}>
                  광고를 삽입하고자 하는 컨텐츠를 선택할 수 있습니다. 회차
                  지정은 불가능하며, 선택한 컨텐츠에 자동으로 광고가 삽입됩니다.
                </div>
              </InputContainer>
            </div>
          </div>
          <div className={`${styles.buttons}`}>
            <EditButton formData={formData} />
            <EditGoodsButton formData={formData} advertType={ad?.advertType} productId={ad?.productId} />
          </div>
        </Content>
      </Container>
    </div>
  );
}
