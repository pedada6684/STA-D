import React from "react";
import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import WebLogin from "./pages/WebLogin/WebLogin";
import LandingPage from "./pages/Landing/LandingPage";
import WebMain from "./pages/Main/WebMain";
import MyPage from "./pages/MyPage/MyPage";
import EnterprisePage from "./pages/MyPage/Enterprise/EnterprisePage";
import EnrolledGoodsList from "./pages/MyPage/SalesManagement/EnrolledGoodsList";
import AdManagement from "./pages/AdManagement/AdManagement";
import SignUp from "./pages/WebLogin/SignUp";
import Review from "./pages/Review/Review";
import EnrolledAdList from "./pages/MyPage/SalesManagement/EnrolledAdList";
import AdEnroll from "./pages/AdEnroll/AdEnroll";
import TvLogin from "./pages/TVLogin/TVLogin";
import TVLanding from "./pages/Landing/TVLanding";
import ProfilePick from "./pages/TVLogin/ProfilePick";
import TVMain from "./pages/Main/TVMain";
import TVSeries from "./pages/Category/TVSeries";
import TVSearch from "./pages/Search/TVSearch";
import VideoDetail from "./pages/Streaming/VideoDetail";
import Streaming from "./pages/Streaming/Streaming";

function App() {
  return (
    <>
      <BrowserRouter>
        <div className="App">
          <Routes>
            <Route path="/web-login" element={<WebLogin />} />
            <Route path="/web-signUp" element={<SignUp />} />
            <Route path="/loading" element={<LandingPage />} />
            <Route path="/web-main" element={<WebMain />} />
            <Route path="/my-page" element={<MyPage />}>
              <Route path="enterprise" element={<EnterprisePage />} />
              <Route path="enroll-list" element={<EnrolledGoodsList />} />
              <Route path="enroll-adList" element={<EnrolledAdList />} />
              <Route path="review" element={<Review />} />
            </Route>
            <Route path="/ad-management" element={<AdManagement />} />
            <Route path="/ad-enroll/*" element={<AdEnroll />} />
            {/* ---------------- 스트리밍 서비스 관련 router ------------------ */}
            <Route path="/tv-login" element={<TvLogin />} />
            <Route path="/tv-landing" element={<TVLanding />} />
            <Route path="/tv-profile" element={<ProfilePick />} />
            <Route path="/tv-main" element={<TVMain />} />
            <Route path="/tv-series" element={<TVSeries />} />
            <Route path="/tv-search" element={<TVSearch />}>
              <Route path=":videoTitle" element={<TVSearch />} />
            </Route>
            <Route path="/tv/:videoId" element={<VideoDetail />} />
            <Route path="/tv/stream/:videoId" element={<Streaming />} />
          </Routes>
        </div>
      </BrowserRouter>
    </>
  );
}

export default App;
