import React, { useEffect, useState } from "react";
import Slider from "react-slick";
import Thumbnail from "../../pages/Main/ThumbnailDummy";
import Content from "../Container/Content";
import "./MainCarousel.css";
import { MainNextArrow, MainPrevArrow } from "../Arrow/Arrow";

type ThumbnailProps = {
  id: number;
  url: string;
};
export default function MainCarousel() {
  let sliderSettings = {
    // className: "center",
    dots: true,
    centerMode: true,
    infinite: true,
    centerPadding: "60px",
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    speed: 500,
    nextArrow: <MainNextArrow />,
    prevArrow: <MainPrevArrow />,
    // appendDots: (dots: any) => (
    //   <div
    //     style={{
    //       position: "absolute",
    //       display: "flex",
    //       alignItems: "center",
    //     }}
    //   >
    //     <ul> {dots} </ul>
    //   </div>
    // ),
    // dotsClass: "dots_custom",
  };

  return (
    <div>
      <Content>
        <div className="slider-container">
          <Slider {...sliderSettings}>
            {Thumbnail.map((data, index) => (
              <div
                className="videoContainer"
                key={index}
                style={{ position: "relative", transition: "all 0.3s" }}
                // onClick={() => navigate(`/video/${video.id}`)}
              >
                <img src={data.url}></img>
              </div>
            ))}
          </Slider>
        </div>
      </Content>
    </div>
  );
}
