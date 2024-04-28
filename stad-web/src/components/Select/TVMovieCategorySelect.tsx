import Select from "react-select";
import { TVCategorySelectProps } from "./TVCategorySelect";

export default function TVMovieCategorySelect({
  onChange,
}: TVCategorySelectProps) {
  const options = [
    { value: "액션", label: "액션" },
    { value: "코미디", label: "코미디" },
    { value: "드라마", label: "드라마" },
    { value: "멜로", label: "멜로" },
    { value: "공포 / 스릴러", label: "공포 / 스릴러" },
    { value: "SF / 판타지", label: "SF / 판타지" },
    { value: "애니메이션", label: "애니메이션" },
    { value: "다큐멘터리", label: "다큐멘터리" },
    { value: "독립영화", label: "독립영화" },
  ];

  const customStyles = {
    valueContainer: (provided: any) => ({
      ...provided,
      padding: "2px 2rem",
      backgroundColor: "#141414",
      color: "white",
    }),
    singleValue: (provided: any) => ({
      ...provided,
      color: "white",
      fontSize: "1.3rem",
    }),
    menu: (provided: any) => ({
      ...provided,
      border: "1px solid #565656",
    }),
    menuList: (provided: any) => ({
      ...provided,
      backgroundColor: "#212121",
    }),
    option: (provided: any) => ({
      ...provided,
      color: "white",
      backgroundColor: "#212121",
    }),
  };

  return (
    <Select
      options={options}
      placeholder="장르"
      styles={customStyles}
      onChange={onChange}
    />
  );
}
