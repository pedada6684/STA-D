import Select, { MultiValue } from "react-select";
import styles from "./SelectBox.module.css";
import { useEffect, useState } from "react";
import { SelectAdMainCategory, SelectAdSubCategory } from "./AdCategory";
import { getContentConcept } from "../../pages/AdEnroll/AdEnrollApi";

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

interface SelectContentBox {
  contentId: { value: number; label: string }[] | null;
  setContentId: (value: { value: number; label: string }[] | null) => void;
  existedData?: number[];
}
export function SelectContentsBox({
  contentId,
  setContentId,
  existedData,
}: SelectContentBox) {
  const [options, setOptions] = useState<{ value: number; label: string }[]>(
    []
  );

  useEffect(() => {
    console.log(contentId);
  }, [contentId]);

  useEffect(() => {
    const fetchOptions = async () => {
      try {
        const data = await getContentConcept();
        setOptions(data);
      } catch (error) {
        console.error("컨텐츠 가져오기 실패 : ", error);
      }
    };
    fetchOptions();
  }, []);

  useEffect(() => {
    if (options.length > 0 && existedData && existedData.length > 0) {
      // options 배열에서 existedData에 포함된 value값만 포함해서 selectedOptions만들기
      const selectedOptions = options.filter((option) =>
        existedData.includes(option.value)
      );
      setContentId(selectedOptions);
    }
  }, [existedData, options, setContentId]);

  const handleContentBox = (
    selectedOptions: MultiValue<{ value: number; label: string }> | null
  ) => {
    if (selectedOptions) {
      setContentId(selectedOptions.map((option) => option));
      console.log(contentId);
    } else {
      setContentId(null);
    }
  };

  // 데이터가 준비되지 않은 경우, 렌더링할 수 있는 방법을 사용합니다.
  // 예를 들어 로딩 중이라는 메시지를 표시할 수 있습니다.
  if (options.length === 0) {
    return <div>Loading...</div>;
  }

  // 데이터가 준비된 후에는 옵션을 렌더링합니다.
  return (
    <Select
      isMulti
      value={contentId}
      name="colors"
      options={options}
      onChange={handleContentBox}
      className="basic-multi-select"
      classNamePrefix="select"
    />
  );
}

interface CategoryProps {
  setCategory: (string: string | null) => void;
  initialCategory?: string;
}

export function SelectAdCategory({
  setCategory,
  initialCategory,
}: CategoryProps) {
  type OptionType = { value: string; label: string };
  type SubCategoryOptions = {
    [key: string]: OptionType[];
  };
  useEffect(() => {
    // 등록한 카테고리 값이 있는 경우
    if (initialCategory) {
      const mainCategoryValue = initialCategory.charAt(0); // 앞글자 추출해서 대분류 카테고리
      const mainCategoryOption = SelectAdMainCategory.find(
          (option) => option.value === mainCategoryValue
      );
      if (mainCategoryOption) {
        setSelectMainCategory(mainCategoryOption);
      }
      const subCategoryOptions = SelectAdSubCategory[mainCategoryValue];
      const subCategoryOption = subCategoryOptions.find(
          (option) => option.value === initialCategory
      );
      if (subCategoryOption) setSelectSubCategory(subCategoryOption);
    }
  },[initialCategory]);

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
    if (selectedOption) {
      setCategory(selectedOption.value);
      setSelectSubCategory(selectedOption);
     console.log(selectedOption.value);
     console.log(selectedOption);
     console.log(selectSubCategory);
    } else {
      setCategory(null);
    }
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
