import InfoButton from "../../components/Button/InfoButton";
import PlayButton from "../../components/Button/PlayButton";
import CategoryCarousel from "../../components/Carousel/CategoryCarousel";
import { dramaThumbnail } from "../../components/Carousel/SeriesDummy";
import BillboardContainer from "../../components/Container/BillboardContainer";
import Content from "../../components/Container/Content";
import HorizonVignette from "../../components/Container/HorizonVignette";
import ImageWrapper from "../../components/Container/ImageWrapper";
import InfoContainer from "../../components/Container/InfoContainer";
import TVContainer from "../../components/Container/TVContainer";
import VignetteWrapper from "../../components/Container/VignetteWrapper";
import TVNav from "../../components/Nav/TVNav";
import TVCategorySelect from "../../components/Select/TVCategorySelect";
import styles from "./TVCategory.module.css";
export default function TVMovie() {
  return (
    <div>
      <TVContainer>
        <TVNav />
        <Content>
          <div className={`${styles.top}`}>
            <div className={`${styles.title}`}>영화</div>
            <div className={`${styles.category}`}>
              <TVCategorySelect />
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
              title="드라마"
              items={dramaThumbnail}
              marginTop="30rem"
            />
          </BillboardContainer>
        </Content>
      </TVContainer>
    </div>
  );
}
