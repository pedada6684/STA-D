import Select from "react-select";
import styles from "../../pages/Review/Review.module.css";
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
