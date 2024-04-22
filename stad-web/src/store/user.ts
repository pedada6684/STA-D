import { createSlice } from "@reduxjs/toolkit";

interface userReduxProps {
  isLoggedIn: boolean;
  userId: number;
  userCompany: string;
}

const initialState: userReduxProps = {
  isLoggedIn: false,
  userId: 0,
  userCompany: "",
};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    loginUser: (state, action) => {
      console.log(action.payload.userId);
      state.isLoggedIn = true;
      state.userId = action.payload.userId;
      state.userCompany = action.payload.userCompany;
    },
    logoutUser: (state) => {
      localStorage.clear();
      state.isLoggedIn = false;
      state.userId = 0;
      state.userCompany = "";
    },
  },
});

export const userActions = userSlice.actions;
export default userSlice;
