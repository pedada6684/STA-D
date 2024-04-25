import mainLeft from "../../assets/teenyicons_left-outline.png";
import mainRight from "../../assets/teenyicons_right-outline.png";
import smallRight from "../../assets/ic_round-navigate-next.png";
import smallLeft from "../../assets/ic_round-navigate-prev.png";
import "./Arrow.css";

interface ArrowProps {
  onClick?: React.MouseEventHandler<HTMLDivElement>;
}

export function MainNextArrow({ onClick }: ArrowProps) {
  return (
    <div className="slick-main-next" onClick={onClick}>
      <img src={mainRight} alt="다음" />
    </div>
  );
}
export function MainPrevArrow({ onClick }: ArrowProps) {
  return (
    <div className="slick-main-prev" onClick={onClick}>
      <img src={mainLeft} alt="이전" />
    </div>
  );
}
export function SmallNextArrow({ onClick }: ArrowProps) {
  return (
    <span className="slick-small-next" onClick={onClick}>
      <img src={smallRight} alt="다음" />
    </span>
  );
}
export function SmallPrevArrow({ onClick }: ArrowProps) {
  return (
    <span className="slick-small-prev" onClick={onClick}>
      <img src={smallLeft} alt="이전" />
    </span>
  );
}
