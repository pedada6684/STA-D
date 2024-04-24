import { Route, Routes, useLocation } from "react-router-dom";
import Container from "../../components/Container/Container";
import Content from "../../components/Container/Content";
import Advertisement from "../../components/Enroll/Advertisement";
import WebNav from "../../components/Nav/WebNav";
import styles from "./AdEnroll.module.css";
import Merchandise from "../../components/Enroll/Merchandise";
import Digital from "../../components/Enroll/Digital";
export default function AdEnroll() {
  const location = useLocation();
  let title = "광고등록"; // 제일 먼저
  if (location.pathname.includes("merchandise")) {
    title = "상품등록";
  } else if (location.pathname.includes("digital")) {
    title = "디지털 광고등록";
  }
  return (
    <div>
      <Container>
        <WebNav />
        <Content>
          <div className={`${styles.title}`}>{title}</div>
          <Routes>
            <Route path="/" element={<Advertisement />} />
            <Route path="merchandise" element={<Merchandise />} />
            <Route path="digital" element={<Digital />} />
          </Routes>
        </Content>
      </Container>
    </div>
  );
}
