import Select from "react-select";
import { TVCategorySelectProps } from "./TVCategorySelect";
import { useQuery } from "react-query";
import { getMovieCategory, getSeriesCategory } from "./TVSelectAPI";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import Loading from "../Loading";

export default function TVMovieCategorySelect({
  onChange,
}: TVCategorySelectProps) {
  const token = useSelector((state: RootState) => state.token.accessToken);
  const {
    data: CategoryList,
    isLoading,
    error,
  } = useQuery(["movie-categoryList", token], () => getMovieCategory(token));
  if (isLoading) {
    return <Loading />;
  }
  const options = CategoryList.map((category: string, index: number) => ({
    value: category, // 'category'는 API에서 받은 실제 카테고리 이름
    label: category, // 동일하게 label도 카테고리 이름으로 설정
  }));

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
