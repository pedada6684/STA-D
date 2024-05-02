import { useSelector } from "react-redux";
import Select, { SingleValue } from "react-select";
import { RootState } from "../../store";
import { useQuery } from "react-query";
import { getSeriesCategory } from "./TVSelectAPI";
import Loading from "../Loading";
export interface OptionType {
  value: string;
  label: string;
}
export interface TVCategorySelectProps {
  onChange: (option: SingleValue<OptionType>) => void;
}
export default function TVCategorySelect({ onChange }: TVCategorySelectProps) {
  const token = useSelector((state: RootState) => state.token.accessToken);
  const { data: CategoryList, isLoading } = useQuery(
    ["series-categoryList", token],
    () => getSeriesCategory(token)
  );
  if (isLoading) {
    return <Loading />;
  }
  console.log(CategoryList);
  const options = CategoryList.map((category: string, index: number) => ({
    value: category,
    label: category,
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
