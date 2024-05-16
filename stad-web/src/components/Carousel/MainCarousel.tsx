import React, { useEffect, useState } from "react";
import Slider from "react-slick";
import Thumbnail from "../../pages/Main/ThumbnailDummy";
import Content from "../Container/Content";
import "./MainCarousel.css";
import { MainNextArrow, MainPrevArrow } from "../Arrow/Arrow";
import { useQuery } from "react-query";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { GetMainCarousel } from "./CarouselApI";
import { useNavigate } from "react-router-dom";

export type CarouselVideoProps = {
  title: string;
  thumbnailUrl: string;
  conceptId: number;
};

export type CarouselWatchedProps = {
  title: string;
  thumbnailUrl: string;
  conceptId: number;
  episode: number;
  detailId: number;
};
export default function MainCarousel() {
  const navigate = useNavigate();
  const {
    data: CarouselData,
    isLoading,
    error,
  } = useQuery<CarouselVideoProps[]>(["mainCarousel"], () => GetMainCarousel());
  console.log(CarouselData);
  if (isLoading)
    return (
      <div>
        <Content>Loading...</Content>
      </div>
    );
  let sliderSettings = {
    // className: "center",
    dots: true,
    centerMode: CarouselData && CarouselData.length > 1,
    infinite: CarouselData && CarouselData.length > 1,
    centerPadding: "60px",
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    speed: 500,
    nextArrow: <MainNextArrow />,
    prevArrow: <MainPrevArrow />,
  };

  return (
    <div>
      <Content>
        <div className="slider-container">
          <Slider {...sliderSettings}>
            {CarouselData?.map((data: CarouselVideoProps, index: number) => (
              <div
                className="videoContainer"
                key={`${data.title}-${index}`}
                style={{ position: "relative", transition: "all 0.3s" }}
                onClick={() => navigate(`/tv/${data.conceptId}`)}
              >
                <img src={data.thumbnailUrl}></img>
              </div>
            ))}
          </Slider>
        </div>
      </Content>
    </div>
  );
}
