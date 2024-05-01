import styles from "./Advertisement.module.css";
import top from "../../assets/flowbite_angle-top-solid.png";
import plus from "../../assets/plus.png";
import {ChangeEvent, useEffect, useState} from "react";
import DateRange from "../Calendar/DateRange";
import { SelectAdCategory, SelectContentsBox } from "../Select/SelectBox";
import Modal from "../Modal/Modal";
import ToggleButton from "../Button/ToggleButton";
import InputContainer from "../Container/InputContainer";
import GoEnrollButton from "../Button/GoEnrollButton";
import {advertVideoUpload, bannerImgUpload} from "../../pages/AdEnroll/AdEnrollApi";

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

export default function Advertisement() {
  const [formData, setFormData] = useState<advertForm>();
  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
      setFormData(prevState => ({
        ...prevState,
        [name]: value
      }));
  };
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [selectedContents, setSelectedContents] = useState<string[]>([]);
  const openModal = () => setModalIsOpen(true);
  const closeModal = () => setModalIsOpen(false);
  // 각 항목들 토글을 위한 상태관리
  const [isAdNameExpanded, setAdNameExpanded] = useState(false);
  const [isAdVideoExpanded, setAdVideoExpanded] = useState(false);
  const [isAdPeriodExpanded, setAdPeriodExpanded] = useState(false);
  const [isAdCategoryExpanded, setAdCategoryExpanded] = useState(false);
  const [isAdContentExpanded, setAdContentExpanded] = useState(false);
  const [videoUrlList, setVideoUrlList] = useState<string[]>([])
  const [bannerImgUrl,setBannerImgUrl] = useState<String>("");
  const toggleAdName = () => setAdNameExpanded(!isAdNameExpanded); // 광고명
  const toggleAdVideo = () => setAdVideoExpanded(!isAdVideoExpanded); // 광고 영상
  const toggleAdPeriod = () => setAdPeriodExpanded(!isAdPeriodExpanded); // 광고 기간
  const toggleAdCategory = () => setAdCategoryExpanded(!isAdCategoryExpanded); // 광고 카테고리
  const toggleAdContent = () => setAdContentExpanded(!isAdContentExpanded); // 노출 컨텐츠
  const [startDate, setStartDate] = useState<Date | null>(new Date());
  const [endDate, setEndDate] = useState<Date | null>(new Date("2024/12/31"));
  const [category, setCategory] = useState<String | null>("");
  const [contentId, setContentId] = useState<{value : number, label : string}[] | null>(null);

  const handleDateRangeChange = (startDate: Date | null, endDate: Date | null) => {
    // startDate와 endDate를 이용한 작업 수행
    console.log("Start Date:", startDate);
    console.log("End Date:", endDate);
  };
  useEffect(() => {
    console.log(videoUrlList);
    console.log(bannerImgUrl);
    console.log(formData)
    console.log(startDate)
    console.log(endDate)
    console.log(category)
    console.log(contentId)
  }, [videoUrlList,bannerImgUrl,formData,startDate,endDate,category,contentId]);

  const handleAdvertVideoList = async (e: ChangeEvent<HTMLInputElement>) => {
    const videoList = e.target.files;
    const responseData = await advertVideoUpload(videoList);
    responseData.forEach((video: any, index: number) => {
      console.log(`영상 ${index + 1}:`, video.videoUrl);
      setVideoUrlList(prevVideoUrlList => [...prevVideoUrlList, video.videoUrl]);
    });
  };

  const handleBannerImg = async (e: ChangeEvent<HTMLInputElement>) => {
    const bannerImgUrl = e.target.files;
    const responseData = await bannerImgUpload(bannerImgUrl);
    console.log(`배너이미지 : `,responseData);
    setBannerImgUrl(responseData);
  };

  return (
    <div className={`${styles.container}`}>
      <div className={`${styles.item}`}>
        <div className={`${styles.title}`}>
          <div className={`${styles.name}`}>
            광고명<span>*</span>
          </div>
          <ToggleButton isExpanded={isAdNameExpanded} onToggle={toggleAdName} />
        </div>
        {isAdNameExpanded && (
          <InputContainer>
            <div>
              <input
                type="text"
                name="advertise-name"
                onChange={handleChange}
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
            isExpanded={isAdVideoExpanded}
            onToggle={toggleAdVideo}
          />
        </div>
        {isAdVideoExpanded && (
          <InputContainer>
            <div className={`${styles.advideoList}`}>
              <div className={`${styles.subTitle}`}>TV 광고 영상</div>
              <div className={`${styles.videoContainer}`}>
                <div className={`${styles.video}`}>
                  <input
                    type="file"
                    name="videoList"
                    id="videoList"
                    onChange={handleAdvertVideoList}
                    className={`${styles.videoInput} ${styles.input}`}
                    multiple
                    required
                  />
                  <label htmlFor="videoList" className={styles.btnUpload}>
                    <img src={plus} alt="업로드" />
                  </label>
                </div>
                <div className={`${styles.caution}`}>
                  * 가이드에 맞지않은 영상 등록 시 별도 고지없이 제재 될 수
                  있습니다.
                </div>
                <div className={`${styles.caution2}`}>
                  TV 광고 영상은 15초, 30초, 45초, 60초 옵션으로 등록할 수
                  있으며, 각각 다른 길이를 가진 광고 영상 여러개를 업로드할 수
                  있습니다.
                </div>
              </div>
            </div>
            <div className={`${styles.adBanner}`}>
              <div className={`${styles.subTitle}`}>앱 광고 배너 이미지</div>
              <div
                className={`${styles.imageContainer} ${styles.inputContainer}`}
              >
                <div className={`${styles.image}`}>
                  <input
                    type="file"
                    name="file"
                    id="file"
                    onChange={handleBannerImg}
                    className={`${styles.imageInput} ${styles.input}`}
                    required
                  />
                  <label htmlFor="file" className={`${styles.btnUpload}`}>
                    <img src={plus} alt="업로드" />
                  </label>
                </div>
                <div className={`${styles.caution}`}>
                  * 가이드에 맞지않은 배너 이미지 등록 시 별도 고지없이 제재 될
                  수 있습니다.
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
            isExpanded={isAdPeriodExpanded}
            onToggle={toggleAdPeriod}
          />
        </div>
        {isAdPeriodExpanded && (
          <InputContainer>
            <div className={`${styles.calendar}`}>
              <DateRange startDate={startDate}
                         endDate={endDate}
                         setStartDate={setStartDate}
                         setEndDate={setEndDate} />
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
            isExpanded={isAdCategoryExpanded}
            onToggle={toggleAdCategory}
          />
        </div>
        {isAdCategoryExpanded && (
          <InputContainer>
            <div className={`${styles.selectBox}`}>
              <SelectAdCategory setCategory={setCategory}/>
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
            isExpanded={isAdContentExpanded}
            onToggle={toggleAdContent}
          />
        </div>
        {isAdContentExpanded && (
          <InputContainer>
            <div>
              <button className={`${styles.contents}`} onClick={openModal}>
                컨텐츠 선택하기
              </button>
              <Modal isOpen={modalIsOpen} onRequestClose={closeModal}>
                <div className={`${styles.modalContent}`}>
                  <div className={`${styles.modalTitle}`}>컨텐츠 선택하기</div>
                  <SelectContentsBox contentId={contentId}
                                     setContentId={setContentId}/>
                </div>
              </Modal>
            </div>
            <div className={`${styles.caution}`}>
              광고를 삽입하고자 하는 컨텐츠를 선택할 수 있습니다. 회차 지정은
              불가능하며, 선택한 컨텐츠에 자동으로 광고가 삽입됩니다.
            </div>
          </InputContainer>
        )}
      </div>
      <div className={`${styles.buttonContainer}`}>
        <GoEnrollButton to="/ad-enroll/merchandise">
          상품 등록하러가기
        </GoEnrollButton>
        <GoEnrollButton to="/ad-enroll/digital">
          직접 광고 등록하러가기
        </GoEnrollButton>
      </div>
    </div>
  );
}
