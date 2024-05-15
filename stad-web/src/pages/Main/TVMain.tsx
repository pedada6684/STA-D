import MainCarousel from "../../components/Carousel/MainCarousel";
import RecentWatching from "../../components/Carousel/RecentWatching";
import SaveCarousel from "../../components/Carousel/SaveCarousel";
import TVContainer from "../../components/Container/TVContainer";
import TVNav from "../../components/Nav/TVNav";

export default function TVMain() {
  return (
    <div>
      <TVContainer>
        <TVNav />
        <MainCarousel />
        <RecentWatching />
        <SaveCarousel />
      </TVContainer>
    </div>
  );
}
