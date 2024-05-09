import { ChangeEvent, useEffect, useRef, useState } from "react";
import { useParams } from "react-router";
import Container from "../../components/Container/Container";
import WebNav from "../../components/Nav/WebNav";
import Content from "../../components/Container/Content";
import { useQuery } from "react-query";
import axios from "axios";
import styles from "../../components/Enroll/Advertisement.module.css";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../store";
import { toggleActions } from "../../store/toggle";
import ToggleButton from "../../components/Button/ToggleButton";
import InputContainer from "../../components/Container/InputContainer";
import { getAdvertDetail } from "./AdDetailAPI";
import DateRange from "../../components/Calendar/DateRange";
import {
  SelectAdCategory,
  SelectContentsBox,
} from "../../components/Select/SelectBox";
import Modal from "../../components/Modal/Modal";
import { advertVideoUpload, bannerImgUpload } from "../AdEnroll/AdEnrollApi";
import plus from "../../assets/plus.png";
import edit from "../../assets/lucide_edit.png";
interface advertForm {
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

export default function AdDetail() {
  const { id } = useParams(); // 광고 ID 파라미터 URL에서 가져오기
  const advertId = Number(id);
  const [formData, setFormData] = useState<advertForm>();
  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const openModal = () => setModalIsOpen(true);
  const closeModal = () => setModalIsOpen(false);
  // 각 항목들 토글을 위한 상태관리
  const dispatch = useDispatch();
  const toggles = useSelector((state: RootState) => state.toggle);
  const handleToggle = (toggleKey: string) => {
    dispatch(toggleActions.toggle(toggleKey));
  };

  const isAdNameExpanded = useSelector(
    (state: RootState) => state.toggle.isAdNameExpanded
  );
  const isAdVideoExpanded = useSelector(
    (state: RootState) => state.toggle.isAdVideoExpanded
  );
  const isAdPeriodExpanded = useSelector(
    (state: RootState) => state.toggle.isAdPeriodExpanded
  );
  const isAdCategoryExpanded = useSelector(
    (state: RootState) => state.toggle.isAdCategoryExpanded
  );
  const isAdContentExpanded = useSelector(
    (state: RootState) => state.toggle.isAdContentExpanded
  );

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
  } = useQuery(["adDetail", id], () => getAdvertDetail(advertId), {
    onSuccess: (data) => {
      // 데이터가 성공적으로 로드되면 폼 데이터 기본값으로 덮어씌우기
      setFormData({
        title: data.title,
        description: data.description,
        startDate: data.startDate,
        endDate: data.endDate,
        category: data.category,
        bannerImgUrl: data.bannerImgUrl,
        advertVideoUrlList: data.advertVideoUrlList,
        selectedContentList: data.selectedContentList.map(
          (content: any) => content.value
        ), // 컨텐츠 ID 배열 변환
      });
      if (data.startDate && data.endDate) {
        setStartDate(new Date(data.startDate));
        setEndDate(new Date(data.endDate));
      }
    },
  });

  const handleAdvertVideoList = async (e: ChangeEvent<HTMLInputElement>) => {
    const videoList = e.target.files;
    const responseData = await advertVideoUpload(videoList);
    const videoUrls = responseData.map((video: any) => video.videoUrl);
    setFormData((prevState) => ({
      ...prevState,
      advertVideoUrlList: [
        ...(prevState?.advertVideoUrlList ?? []),
        ...videoUrls,
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
                <ToggleButton
                  isExpanded={toggles.isAdNameExpanded}
                  onToggle={() => handleToggle("isAdNameExpanded")}
                />
              </div>
              {isAdNameExpanded && (
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
              )}
            </div>
            <div className={`${styles.item}`}>
              <div className={`${styles.title}`}>
                <div className={`${styles.name}`}>
                  광고영상<span>*</span>
                </div>
                <ToggleButton
                  isExpanded={toggles.isAdVideoExpanded}
                  onToggle={() => handleToggle("isAdVideoExpanded")}
                />
              </div>
              {isAdVideoExpanded && (
                <InputContainer>
                  <div className={`${styles.advideoList}`}>
                    <div className={`${styles.subTitle}`}>TV 광고 영상</div>
                    <div className={`${styles.videoContainer}`}>
                      <div
                        className={
                          ad?.advertVideoUrlList?.length > 0
                            ? `${styles.afterUpload}`
                            : `${styles.video}`
                        }
                      >
                        {videoUrlList.length > 0 ? (
                          <>
                            {videoUrlList.map((url, index) => (
                              <div
                                key={index}
                                className={`${styles.videoPreview}`}
                              >
                                <video
                                  className={`${styles.videoPrev}`}
                                  controls
                                  src={url}
                                  style={{ width: "100%" }}
                                />
                              </div>
                            ))}
                            <button
                              className={styles.videoOverlay}
                              onClick={() =>
                                videoInputRef.current &&
                                videoInputRef.current.click()
                              }
                            >
                              <img src={edit} alt="Edit videos" />
                            </button>
                            <input
                              type="file"
                              name="videoList"
                              id="videoList"
                              accept="video/*"
                              ref={videoInputRef}
                              multiple
                              onChange={handleAdvertVideoList}
                              className={`${styles.videoInput} ${styles.input}`}
                              style={{ display: "none" }}
                            />
                          </>
                        ) : (
                          <>
                            {/* 기존 advertVideoUrlList 조회해서 보여주기 */}
                            {ad?.advertVideoUrlList?.map(
                              (url: string, index: number) => (
                                <div
                                  key={index}
                                  className={`${styles.videoPreview}`}
                                >
                                  <video
                                    className={`${styles.videoPrev}`}
                                    controls
                                    src={url}
                                    style={{ width: "100%" }}
                                  />
                                </div>
                              )
                            )}
                            <button
                              className={styles.videoOverlay}
                              onClick={() =>
                                videoInputRef.current &&
                                videoInputRef.current.click()
                              }
                            >
                              <img src={edit} alt="Edit videos" />
                            </button>
                            <input
                              ref={videoInputRef}
                              type="file"
                              name="videoList"
                              id="videoList"
                              accept="video/*"
                              multiple
                              onChange={handleAdvertVideoList}
                              className={`${styles.videoInput} ${styles.input}`}
                              style={{ display: "none" }}
                            />
                          </>
                        )}
                      </div>
                      <div className={`${styles.caution}`}>
                        * 가이드에 맞지않은 영상 등록 시 별도 고지없이 제재 될
                        수 있습니다.
                      </div>
                      <div className={`${styles.caution2}`}>
                        TV 광고 영상은 15초, 30초, 45초, 60초 옵션으로 등록할 수
                        있으며, 각각 다른 길이를 가진 광고 영상 여러개를
                        업로드할 수 있습니다.
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
                            <img
                              className={`${styles.bannerImg}`}
                              src={ad?.bannerImgUrl}
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
                        ) : (
                          <>
                            <img
                              src={bannerImgPreview}
                              alt="Preview"
                              className={styles.imgPrev}
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
                              id="fileInput" // 동일한 input ID를 유지
                              accept="image/gif, image/jpeg, image/jpg, image/png"
                              ref={imageInputRef}
                              onChange={handleBannerImg}
                              style={{ display: "none" }}
                              required
                            />
                          </>
                        )}
                      </div>
                      <div className={`${styles.caution}`}>
                        * 가이드에 맞지않은 배너 이미지 등록 시 별도 고지없이
                        제재 될 수 있습니다.
                      </div>
                      <div className={`${styles.caution2}`}>
                        앱 광고용 영상은 풀 버전으로 업로드해 주세요.
                      </div>
                    </div>
                  </div>
                </InputContainer>
              )}
            </div>
            <div className={`${styles.item}`}>
              <div className={`${styles.title}`}>
                <div className={`${styles.name}`}>
                  광고기간<span>*</span>
                </div>
                <ToggleButton
                  isExpanded={toggles.isAdPeriodExpanded}
                  onToggle={() => handleToggle("isAdPeriodExpanded")}
                />
              </div>
              {isAdPeriodExpanded && (
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
              )}
            </div>
            <div className={`${styles.item}`}>
              <div className={`${styles.title}`}>
                <div className={`${styles.name}`}>
                  광고 카테고리<span>*</span>
                </div>
                <ToggleButton
                  isExpanded={toggles.isAdCategoryExpanded}
                  onToggle={() => handleToggle("isAdCategoryExpanded")}
                />
              </div>
              {isAdCategoryExpanded && (
                <InputContainer>
                  <div className={`${styles.selectBox}`}>
                    <SelectAdCategory setCategory={setCategory} />
                  </div>
                  <div className={`${styles.caution}`}>
                    *각 광고 카테고리에 맞는 영상을 매칭해드립니다.
                  </div>
                </InputContainer>
              )}
            </div>
            <div className={`${styles.item}`}>
              <div className={`${styles.title}`}>
                <div className={`${styles.name}`}>노출 컨텐츠(선택)</div>
                <ToggleButton
                  isExpanded={toggles.isAdContentExpanded}
                  onToggle={() => handleToggle("isAdContentExpanded")}
                />
              </div>
              {isAdContentExpanded && (
                <InputContainer>
                  <div>
                    <button
                      className={`${styles.contents}`}
                      onClick={openModal}
                    >
                      컨텐츠 선택하기
                    </button>
                    <Modal isOpen={modalIsOpen} onRequestClose={closeModal}>
                      <div className={`${styles.modalContent}`}>
                        <div className={`${styles.modalTitle}`}>
                          컨텐츠 선택하기
                        </div>
                        <SelectContentsBox
                          contentId={ad?.selectedContentList}
                          setContentId={setContentId}
                        />
                      </div>
                    </Modal>
                  </div>
                  <div className={`${styles.caution}`}>
                    광고를 삽입하고자 하는 컨텐츠를 선택할 수 있습니다. 회차
                    지정은 불가능하며, 선택한 컨텐츠에 자동으로 광고가
                    삽입됩니다.
                  </div>
                </InputContainer>
              )}
            </div>
          </div>
        </Content>
      </Container>
    </div>
  );
}