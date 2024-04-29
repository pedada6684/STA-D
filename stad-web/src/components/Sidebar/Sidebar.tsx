import styles from "./Sidebar.module.css";
import DropDownMenu from "./DropDownMenu";
import { MouseEvent } from "react";
import { tab } from "../../pages/MyPage/MyPage";

export interface SideBarProps {
  activeTab: tab;
  onClickTab: (tab: tab) => (e: MouseEvent<HTMLElement>) => void;
}

export default function Sidebar({ activeTab, onClickTab }: SideBarProps) {
  return (
    <div className={`${styles.container}`}>
      <ul className={`${styles.content}`}>
        <li
          className={
            activeTab === "enterprise"
              ? `${styles.item} ${styles.active}`
              : styles.item
          }
          onClick={onClickTab("enterprise")}
        >
          <div>
            {/* <img src={company} className={`${styles.icon}`} alt="회사" /> */}
          </div>
          <div className={`${styles.text}`}>기업정보</div>
        </li>
        <li className={`${styles.item} ${styles.dropDown}`}>
          <div>
            {/* <img src={board} className={`${styles.icon}`} alt="관리" /> */}
          </div>
          <div className={`${styles.text}`}>판매관리</div>
          <div>
            <DropDownMenu activeTab={activeTab} onClickTab={onClickTab} />
          </div>
        </li>
      </ul>
    </div>
  );
}
