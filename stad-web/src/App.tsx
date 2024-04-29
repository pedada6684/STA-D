import React from "react";
import "./App.css";
import { BrowserRouter } from "react-router-dom";
import AnimatedRouter from "./AnimatedRouter";

function App() {
  return (
    <>
      <BrowserRouter>
        <div className="App">
          <AnimatedRouter />
        </div>
      </BrowserRouter>
    </>
  );
}

export default App;
