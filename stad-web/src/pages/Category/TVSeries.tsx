import InfoButton from "../../components/Button/InfoButton";
import PlayButton from "../../components/Button/PlayButton";
import {
  documentaryThumbnail,
  dramaThumbnail,
  foreignThumbnail,
  smallThumbnail,
  varietyThumbnail,
} from "../../components/Carousel/SeriesDummy";
import CategoryCarousel from "../../components/Carousel/CategoryCarousel";
import RecentWatching from "../../components/Carousel/RecentWatching";
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
export default function TVSeries() {
  return (
    <div>
      <TVContainer>
        <TVNav />
        <Content>
          <div className={`${styles.top}`}>
            <div className={`${styles.title}`}>방송</div>
            <div className={`${styles.category}`}>
              <TVCategorySelect />
            </div>
          </div>
          <BillboardContainer>
            <ImageWrapper>
              <img
                src="https://img2.sbs.co.kr/img/sbs_cms/WE/2022/02/07/NxE1644218336386.jpg"
                alt="그 해 우리는"
                className={`${styles.mainImage}`}
              />
              <VignetteWrapper />
              <HorizonVignette />
              <InfoContainer>
                <div className={`${styles.info}`}>
                  <div className={`${styles.vidTitle}`}>그 해 우리는</div>
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
          <CategoryCarousel
            title="예능"
            marginTop="3rem"
            items={varietyThumbnail}
          />
          <CategoryCarousel
            title="교양/다큐멘터리"
            marginTop="3rem"
            items={documentaryThumbnail}
          />
          <CategoryCarousel
            title="해외"
            marginTop="3rem"
            marginBottom="3rem"
            items={foreignThumbnail}
          />
        </Content>
      </TVContainer>
    </div>
  );
}
