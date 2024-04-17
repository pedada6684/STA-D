import styles from "./Sidebar.module.css";
import company from "../../assets/mdi_company.png";
import board from "../../assets/mage_dashboard-3.png";
import acompany from "../../assets/active-mdi_company.png";
import aboard from "../../assets/active-mage_dashboard-3.png";
import DropDownMenu from "./DropDownMenu";
import { MouseEvent } from "react";
import { tab } from "../../pages/MyPage/MyPage";

interface SideBarProps {
  activeTab: tab;
  onClickTab: (tab: tab) => (e: MouseEvent<HTMLDivElement>) => void;
}

export default function Sidebar({ activeTab, onClickTab }: SideBarProps) {
  return (
    <div className={`${styles.container}`}>
      <ul className={`${styles.content}`}>
        <li className={`${styles.item}`}>
          <div>
            <img src={company} className={`${styles.icon}`} alt="회사" />
          </div>
          <div className={`${styles.text}`}>기업정보</div>
        </li>
        <li className={`${styles.item} ${styles.dropDown}`}>
          <div>
            <img src={board} className={`${styles.icon}`} alt="관리" />
          </div>
          <div className={`${styles.text}`}>판매관리</div>
          <div>
            <DropDownMenu />
          </div>
        </li>
      </ul>
    </div>
  );
}
