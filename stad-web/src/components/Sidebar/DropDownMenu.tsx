import styles from "./Sidebar.module.css";
export default function DropDownMenu() {
  return (
    <>
      <ul className={`${styles.dropDownMenu}`}>
        <li>등록 광고 목록 조회 / 수정</li>
        <li>등록 상품 목록 조회 / 수정</li>
        <li>리뷰 관리</li>
      </ul>
    </>
  );
}
