import MainCarousel from "../../components/Carousel/MainCarousel";
import RecentWatching from "../../components/Carousel/RecentWatching";
import TVContainer from "../../components/Container/TVContainer";
import TVNav from "../../components/Nav/TVNav";

export default function TVMain() {
  return (
    <div>
      <TVContainer>
        <TVNav />
        <MainCarousel />
        <RecentWatching />
      </TVContainer>
    </div>
  );
}
