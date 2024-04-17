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
import Advertisement from "./components/Enroll/Advertisement";
import { QueryClient, QueryClientProvider } from "react-query";
import Review from "./pages/Review/Review";
import Merchandise from "./components/Enroll/Merchandise";

function App() {
  const queryClient = new QueryClient();
  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <div className="App">
          <Routes>
            <Route path="/web-login" element={<WebLogin />} />
            <Route path="/web-signUp" element={<SignUp />} />
            <Route path="/loading" element={<LandingPage />} />
            <Route path="/web-main" element={<WebMain />} />
            <Route path="/my-page" element={<MyPage />}>
              <Route path="info" element={<EnterprisePage />} />
              <Route path="enroll-list" element={<EnrolledGoodsList />} />
            </Route>
            <Route path="/ad-management" element={<AdManagement />} />
            <Route path="/ad-enroll" element={<Advertisement />}>
              <Route path="mer-enroll" element={<Merchandise />} />
            </Route>
            <Route path="/review" element={<Review />} />
          </Routes>
        </div>
      </BrowserRouter>
    </QueryClientProvider>
  );
}

export default App;
