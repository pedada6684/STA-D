import InfoButton from "../../components/Button/InfoButton";
import PlayButton from "../../components/Button/PlayButton";
import CategoryCarousel, {
  getConceptResponseList,
} from "../../components/Carousel/CategoryCarousel";
import BillboardContainer from "../../components/Container/BillboardContainer";
import Content from "../../components/Container/Content";
import HorizonVignette from "../../components/Container/HorizonVignette";
import ImageWrapper from "../../components/Container/ImageWrapper";
import InfoContainer from "../../components/Container/InfoContainer";
import TVContainer from "../../components/Container/TVContainer";
import VignetteWrapper from "../../components/Container/VignetteWrapper";
import TVNav from "../../components/Nav/TVNav";
import TVCategorySelect, {
  OptionType,
} from "../../components/Select/TVCategorySelect";
import styles from "./TVCategory.module.css";
import { useNavigate } from "react-router-dom";
import { SingleValue } from "react-select";
import { useQuery } from "react-query";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { GetSeriesVideoList } from "./TVCategoryAPI";
import Loading from "../../components/Loading";

export type CategoryProps = {
  category: string;
  getConceptResponseList: getConceptResponseList[];
};
export default function TVSeries() {
  const navigate = useNavigate();
  const token = useSelector((state: RootState) => state.token.accessToken);
  // 시리즈 카테고리 별로 메인 영상 12개씩 조회
  const {
    data: videoList,
    isLoading,
    error,
  } = useQuery(["series", token], () => GetSeriesVideoList(token));

  // 로딩 상태 처리
  if (isLoading) {
    return <Loading />;
  }
  const handleCategoryChange = (selectedOption: SingleValue<OptionType>) => {
    if (selectedOption) {
      navigate(`/tv-series/${selectedOption.value}`);
    }
  };

  const firstCategory = videoList?.categoryAndConceptsList[0]; // 빌보드 컨테이너 안에 들어갈 첫번째 카테고리 캐러셀
  const othersCategories = videoList?.categoryAndConceptsList.slice(1); // 인덱스 1번부터 ~ 모두 복사
  console.log(firstCategory);
  return (
    <div>
      <TVContainer>
        <TVNav />
        <Content>
          <div className={`${styles.top}`}>
            <div className={`${styles.title}`}>시리즈</div>
            <div className={`${styles.category}`}>
              <TVCategorySelect onChange={handleCategoryChange} />
            </div>
          </div>
          <BillboardContainer>
            <ImageWrapper>
              <img
                src="https://news.coupang.com/wp-content/uploads/2023/08/01-2.jpg"
                alt="소년시대"
                className={`${styles.mainImage}`}
              />
              <VignetteWrapper />
              <HorizonVignette />
              <InfoContainer>
                <div className={`${styles.info}`}>
                  <div className={`${styles.vidTitle}`}>소년시대</div>
                  <div className={`${styles.btnWrapper}`}>
                    <PlayButton onClick={() => navigate("/tv/stream/123")} />
                    <InfoButton onClick={() => navigate(`/tv/${123}`)} />
                  </div>
                </div>
              </InfoContainer>
            </ImageWrapper>
            {firstCategory && (
              <CategoryCarousel
                key={firstCategory.category}
                title={firstCategory.category}
                items={firstCategory.getConceptResponseList}
                marginTop="30rem"
              />
            )}
          </BillboardContainer>
          {othersCategories &&
            othersCategories
              .filter(
                (category: CategoryProps) =>
                  category.getConceptResponseList.length >= 4
              )
              .map((category: CategoryProps) => (
                <CategoryCarousel
                  title={category.category}
                  items={category.getConceptResponseList}
                  key={category.category}
                  marginTop="2rem"
                  marginBottom="2rem"
                />
              ))}
        </Content>
      </TVContainer>
    </div>
  );
}
