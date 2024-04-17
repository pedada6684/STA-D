import { MouseEvent, useState } from "react";
import Container from "../../components/Container/Container";
import WebNav from "../../components/Nav/WebNav";
import Sidebar from "../../components/Sidebar/Sidebar";
import styles from "./MyPage.module.css";
import { useNavigate } from "react-router";
import EnterprisePage from "./Enterprise/EnterprisePage";
import EnrolledAdList from "./SalesManagement/EnrolledAdList";
import EnrolledGoodsList from "./SalesManagement/EnrolledGoodsList";
import Review from "../Review/Review";

export type tab =
  | "enterprise"
  | "enrollAdList"
  | "enrollGoodsList"
  | "reviewList";

export default function MyPage() {
  const navigate = useNavigate();
  // 마이페이지 처음 들어갔을 때 디폴트는 기업정보 탭으로
  const [activeTab, setActiveTab] = useState<tab>("enterprise");
  const handleClickTab = (tab: tab) => (e: MouseEvent<HTMLDivElement>) => {
    e.preventDefault();
    setActiveTab(tab);
    navigate(`${tab}`);
  };

  function renderComponent() {
    switch (activeTab) {
      case "enterprise":
        return <EnterprisePage />;
      case "enrollAdList":
        return <EnrolledAdList />;
      case "enrollGoodsList":
        return <EnrolledGoodsList />;
      case "reviewList":
        return <Review />;
    }
  }
  return (
    <div>
      <Container>
        <WebNav />
        <div className={styles.content}>
          <div className={`${styles.title}`}>마이페이지</div>
          <Sidebar activeTab={activeTab} onClickTab={handleClickTab} />
        </div>
      </Container>
    </div>
  );
}
