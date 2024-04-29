import { SmallNextArrow, SmallPrevArrow } from "../Arrow/Arrow";
import { smallThumbnail } from "../../pages/Category/SeriesDummy";
import "./CategoryCarousel.css";
import Slider from "react-slick";
import { useNavigate } from "react-router-dom";
interface CategoryCarouselProps {
  title?: string;
  items?: {
    id: number;
    thumbnailUrl: string;
    title: string;
    playtime?: string;
    releaseYear?: string;
    audienceAge?: string;
    creator?: string;
    cast?: string;
    description?: string;
  }[];
  marginTop?: string;
  marginBottom?: string;
}

export default function CategoryCarousel({
  title,
  items = [],
  marginTop,
  marginBottom,
}: CategoryCarouselProps) {
  let setting = {
    dots: true,
    infinite: true,
    slidesToShow: 4,
    slidesToScroll: 4,
    speed: 500,
    appendDots: (dots: any) => (
      <div
        style={{
          position: "absolute",
          display: "flex",
          alignItems: "center",
        }}
      >
        <ul> {dots} </ul>
      </div>
    ),
    dotsClass: "dots_custom",
    prevArrow: <SmallPrevArrow />,
    nextArrow: <SmallNextArrow />,
  };
  const navigate = useNavigate();

  return (
    <div
      className="main-container"
      style={{ marginTop: marginTop, marginBottom: marginBottom }}
    >
      <div className="v-title">{title}</div>
      <div className="thumbnail-container">
        <Slider {...setting}>
          {items.map((data, index) => (
            <div
              className="xs-vid-container"
              key={index}
              style={{ position: "relative", transition: "all 0.3s" }}
              onClick={() => navigate(`/tv/${data.id}`)}
            >
              <img src={data.thumbnailUrl} alt="비디오 썸네일" />
              <div className="vidTitle">{data.title}</div>
            </div>
          ))}
        </Slider>
      </div>
    </div>
  );
}
