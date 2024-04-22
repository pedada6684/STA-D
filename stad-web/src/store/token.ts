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
      state.accessToken = action.payload;
    },
  },
});

export const tokenActions = tokenSlice.actions;
export default tokenSlice;
