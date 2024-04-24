import Select from "react-select";
import styles from "./SelectBox.module.css";
import { useState } from "react";
import { SelectAdMainCategory, SelectAdSubCategory } from "./AdCategory";
export function SelectReviewGoodsBox() {
  const options = [
    { value: "상품 전체", label: "상품 전체" },
    { value: "딸기", label: "딸기" },
    { value: "망고", label: "망고" },
  ];
  return (
    <div className={`${styles.option}`}>
      <Select options={options} />
    </div>
  );
}

export function SelectReviewSortBox() {
  const options = [
    { value: "별점순", label: "별점순" },
    { value: "최신순", label: "최신순" },
  ];

  return <Select options={options} />;
}

export function SelectAdCategory() {
  type OptionType = { value: string; label: string };
  type SubCategoryOptions = {
    [key: string]: OptionType[];
  };

  const [selectMainCategory, setSelectMainCategory] =
    useState<OptionType | null>(null);
  const [selectSubCategory, setSelectSubCategory] = useState<OptionType | null>(
    null
  );

  // 대분류 변경 핸들러
  const handleMainCategoryChange = (selectedOption: OptionType | null) => {
    setSelectMainCategory(selectedOption);
    setSelectSubCategory(null); // 중분류 초기화
  };

  // 중분류 변경 핸들러
  const handleSubcategoryChange = (selectedOption: OptionType | null) => {
    setSelectSubCategory(selectedOption);
  };

  const customStyles = {
    valueContainer: (provided: any) => ({
      ...provided,
      padding: "2px 2rem",
    }),
  };

  return (
    <div className={`${styles.container}`}>
      <div className={`${styles.category}`}>
        <Select
          options={SelectAdMainCategory}
          value={selectMainCategory}
          onChange={handleMainCategoryChange}
          placeholder="대분류"
          styles={customStyles}
        />
      </div>
      <div>
        {selectMainCategory && (
          <Select
            options={
              (SelectAdSubCategory as SubCategoryOptions)[
                selectMainCategory.value
              ]
            }
            value={selectSubCategory}
            onChange={handleSubcategoryChange}
            placeholder="중분류"
            styles={customStyles}
          />
        )}
      </div>
    </div>
  );
}
