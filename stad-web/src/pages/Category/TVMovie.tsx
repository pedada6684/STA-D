import InfoButton from "../../components/Button/InfoButton";
import PlayButton from "../../components/Button/PlayButton";
import CategoryCarousel from "../../components/Carousel/CategoryCarousel";
import BillboardContainer from "../../components/Container/BillboardContainer";
import Content from "../../components/Container/Content";
import HorizonVignette from "../../components/Container/HorizonVignette";
import ImageWrapper from "../../components/Container/ImageWrapper";
import InfoContainer from "../../components/Container/InfoContainer";
import TVContainer from "../../components/Container/TVContainer";
import VignetteWrapper from "../../components/Container/VignetteWrapper";
import TVNav from "../../components/Nav/TVNav";
import TVMovieCategorySelect from "../../components/Select/TVMovieCategorySelect";
import styles from "./TVCategory.module.css";
import { actionDummy, comedyDummy } from "./MovieDummy";
import { OptionType } from "../../components/Select/TVCategorySelect";
import { SingleValue } from "react-select";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { useQuery } from "react-query";
import { GetMovieVideoList } from "./TVCategoryAPI";
import Loading from "../../components/Loading";
import { CategoryProps } from "./TVSeries";
export default function TVMovie() {
  const navigate = useNavigate();
  const token = useSelector((state: RootState) => state.token.accessToken);
  // 영화 리스트 조회
  const {
    data: videoList,
    isLoading,
    error,
  } = useQuery(["movie", token], () => GetMovieVideoList(token));
  if (isLoading) {
    return <Loading />;
  }
  const handleCategoryChange = (selectedOption: SingleValue<OptionType>) => {
    if (selectedOption) {
      navigate(`/tv-movie/${selectedOption.value}`);
    }
  };
  const firstCategory = videoList?.categoryAndConceptsList[0]; // 빌보드 컨테이너 안에 들어갈 첫번째 카테고리 캐러셀
  const othersCategories = videoList?.categoryAndConceptsList.slice(1); // 인덱스 1번부터 ~ 모두 복사
  return (
    <div>
      <TVContainer>
        <TVNav />
        <Content>
          <div className={`${styles.top}`}>
            <div className={`${styles.title}`}>영화</div>
            <div className={`${styles.category}`}>
              <TVMovieCategorySelect onChange={handleCategoryChange} />
            </div>
          </div>
          <BillboardContainer>
            <ImageWrapper>
              <img
                src="https://dunenewsnet.com/wp-content/uploads/2024/01/Dune-Part-Two-Movie-Posters-final-feature.jpg"
                alt="듄-파트2"
                className={`${styles.mainImage}`}
              />
              <VignetteWrapper />
              <HorizonVignette />
              <InfoContainer>
                <div className={`${styles.info}`}>
                  <div className={`${styles.vidTitle}`}>듄-파트2</div>
                  <div className={`${styles.btnWrapper}`}>
                    <PlayButton onClick={() => navigate("/tv/stream/6")} />
                    <InfoButton onClick={() => navigate(`/tv/${6}`)} />
                  </div>
                </div>
              </InfoContainer>
            </ImageWrapper>
            {firstCategory && (
              <CategoryCarousel
                title={firstCategory.category}
                items={firstCategory.getConceptResponseList}
                key={firstCategory.category}
                marginTop="30rem"
                marginBottom="4rem"
              />
            )}
          </BillboardContainer>
          {othersCategories &&
            othersCategories.map((category: CategoryProps) => (
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
