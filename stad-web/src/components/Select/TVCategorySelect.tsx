import { useSelector } from "react-redux";
import Select, { SingleValue } from "react-select";
import { RootState } from "../../store";
import { useQuery } from "react-query";
import { getSeriesCategory } from "./TVSelectAPI";
import Loading from "../Loading";
import { useEffect, useRef, useState } from "react";
import styles from "./DropDown.module.css";
import down from "../../assets/octicon_triangle-right-24.png";
export interface OptionType {
  value: string;
  label: string;
}
export interface TVCategorySelectProps {
  onChange: (option: SingleValue<OptionType>) => void;
}
export default function TVCategorySelect({ onChange }: TVCategorySelectProps) {
  const token = useSelector((state: RootState) => state.token.accessToken);
  const [isCategoryOpen, setIsCategoryOpen] = useState<boolean>(false);
  const [maxHeight, setMaxHeight] = useState<number>(0);
  const dropdownRef = useRef<HTMLDivElement>(null);
  const { data: CategoryList, isLoading } = useQuery(
    ["series-categoryList", token],
    () => getSeriesCategory(token)
  );
  useEffect(() => {
    if (dropdownRef.current) {
      dropdownRef.current.style.maxHeight = isCategoryOpen
        ? `${dropdownRef.current.scrollHeight}px`
        : "0";
    }
  }, [isCategoryOpen, CategoryList]);

  if (isLoading) {
    return <Loading />;
  }
  const options = CategoryList.map((category: string, index: number) => ({
    value: category,
    label: category,
  }));
  const toggleCategory = () => {
    setIsCategoryOpen(!isCategoryOpen);
  };

  const handleCategorySelect = (category: OptionType) => {
    onChange(category);
    toggleCategory();
  };
  // const customStyles = {
  //   valueContainer: (provided: any) => ({
  //     ...provided,
  //     padding: "2px 2rem",
  //     backgroundColor: "#141414",
  //     color: "white",
  //   }),
  //   singleValue: (provided: any) => ({
  //     ...provided,
  //     color: "white",
  //     fontSize: "1.3rem",
  //   }),
  //   menu: (provided: any) => ({
  //     ...provided,
  //     border: "1px solid #565656",
  //   }),
  //   menuList: (provided: any) => ({
  //     ...provided,
  //     backgroundColor: "#212121",
  //   }),
  //   option: (provided: any) => ({
  //     ...provided,
  //     color: "white",
  //     backgroundColor: "#212121",
  //   }),
  // };

  return (
    // <Select
    //   options={options}
    //   placeholder="장르"
    //   styles={customStyles}
    //   onChange={onChange}
    // />
    <div className={`${styles.dropdownContainer}`}>
      <button className={`${styles.dropBtn}`} onClick={toggleCategory}>
        <div>장르</div>
        <div>
          <img src={down} />
        </div>
      </button>
      {isCategoryOpen && (
        <div
          className={`${styles.dropdownMenu} ${
            isCategoryOpen ? styles.open : ""
          }`}
          style={{ maxHeight: isCategoryOpen ? `${maxHeight}px` : "0" }}
          ref={dropdownRef}
        >
          <ul className={`${styles.dropdownContent} ${styles.seriesContent}`}>
            {options.map((option: OptionType, index: number) => (
              <li
                className={`${styles.dropdownLi} ${styles.seriesLi}`}
                key={index}
                onClick={() => handleCategorySelect(option)}
              >
                {option.label}
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
}
