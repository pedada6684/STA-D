import { createSlice, PayloadAction } from "@reduxjs/toolkit";

// 액션 여러개 관리하기 위한 인터페이스
interface LoadingInitialState {
  [actionType: string]: boolean;
}

const initialState: LoadingInitialState = {};

const loadingSlice = createSlice({
  name: "loading",
  initialState,
  reducers: {
    startLoading: (state, action: PayloadAction<string>) => {
      state[action.payload] = true; // 특정 액션에 대한 로딩 상태를 true로 설정
    },
    finishLoading: (state, action: PayloadAction<string>) => {
      state[action.payload] = false; // 특정 액션에 대한 로딩 상태를 false로 설정
    },
  },
});

export const loadingActions = loadingSlice.actions;
export default loadingSlice;
