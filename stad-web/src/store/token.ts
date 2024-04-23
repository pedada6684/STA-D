import { createSlice } from "@reduxjs/toolkit";

interface tokenProps {
  accessToken: string | null;
}

const initialState: tokenProps = {
  accessToken: null,
};

const tokenSlice = createSlice({
  name: "token",
  initialState,
  reducers: {
    getAccessToken: (state, action) => {
      state.accessToken = action.payload; // 문자열로 바로 저장
    },
    logoutUser: (state) => {
      state.accessToken = null;
    },
  },
});

export const tokenActions = tokenSlice.actions;
export default tokenSlice;
