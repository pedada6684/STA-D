import styles from "./Sidebar.module.css";
import { SideBarProps } from "./Sidebar";

export default function DropDownMenu({ activeTab, onClickTab }: SideBarProps) {
  return (
    <>
      <ul className={`${styles.dropDownMenu}`}>
        <li
          className={
            activeTab === "enroll-adList"
              ? `${styles.tabButton} ${styles.tabButtonActive}`
              : styles.tabButton
          }
          onClick={onClickTab("enroll-adList")}
        >
          등록 광고 목록 조회 / 수정
        </li>
        <li
          className={
            activeTab === "enroll-list"
              ? `${styles.tabButton} ${styles.tabButtonActive}`
              : styles.tabButton
          }
          onClick={onClickTab("enroll-list")}
        >
          등록 상품 목록 조회
        </li>
        <li
          className={
            activeTab === "review"
              ? `${styles.tabButton} ${styles.tabButtonActive}`
              : styles.tabButton
          }
          onClick={onClickTab("review")}
        >
          리뷰 관리
        </li>
      </ul>
    </>
  );
}
