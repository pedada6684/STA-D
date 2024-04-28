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
export default function TVMovie() {
  const navigate = useNavigate();
  const handleCategoryChange = (selectedOption: SingleValue<OptionType>) => {
    if (selectedOption) {
      navigate(`/tv-movie/${selectedOption.value}`);
    }
  };
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
                    <PlayButton />
                    <InfoButton />
                  </div>
                </div>
              </InfoContainer>
            </ImageWrapper>
            <CategoryCarousel
              title="액션"
              items={actionDummy}
              marginTop="30rem"
            />
          </BillboardContainer>
          <CategoryCarousel
            title="코메디"
            items={comedyDummy}
            marginTop="3rem"
          />
        </Content>
      </TVContainer>
    </div>
  );
}
