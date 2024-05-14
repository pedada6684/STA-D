import { createSlice } from "@reduxjs/toolkit";

interface tvUserType {
  isTvLoggedIn: boolean;
  userId: number;
  userNickname: string;
  profile: string;
}

const initialState: tvUserType = {
  isTvLoggedIn: false,
  userId: 0,
  userNickname: "",
  profile: "",
};

const tvUserSlice = createSlice({
  name: "tvUser",
  initialState,
  reducers: {
    loginUser: (state, action) => {
      state.isTvLoggedIn = true;
      state.userId = action.payload.userId;
      state.userNickname = action.payload.userNickname;
      state.profile = action.payload.profile;
    },
  },
});

export const tvUserActions = tvUserSlice.actions;
export default tvUserSlice;
