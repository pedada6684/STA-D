import { createSlice } from "@reduxjs/toolkit";

interface tvUserType {
  userId: number;
  userNickname: string;
  profile: string;
}

const initialState: tvUserType = {
  userId: 0,
  userNickname: "",
  profile: "",
};

const tvUserSlice = createSlice({
  name: "tv-user",
  initialState,
  reducers: {
    loginUser: (state, action) => {
      state.userId = action.payload.userId;
      state.userNickname = action.payload.userNickname;
      state.profile = action.payload.profile;
    },
  },
});

export const tvUserActions = tvUserSlice.actions;
export default tvUserSlice;
