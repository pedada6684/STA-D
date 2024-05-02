import { createSlice } from "@reduxjs/toolkit";
// 토글 버튼 상태관리를 위한 redux
// 토글 키를 매개변수로 일반화 처리
interface toggleReduxProps {
  [key: string]: boolean;
}

const initialState: toggleReduxProps = {
  isMerTitleExpanded: true,
  isMerThumbnailExpanded: true,
  isMerShipPriceExpanded: true,
  isExpDateExpanded: true,
  isMerAddExpanded: true,
};

const toggleSlice = createSlice({
  name: "toggle",
  initialState,
  reducers: {
    // 액션 dispatch 되었을 때 실행할 함수
    toggle: (state, action) => {
      // action의 실제 데이터 값은 payload로 key 가져오기
      const key = action.payload;
      // 해당 key가 state 객체에 있는지 확인
      if (key in state) {
        // 상태 반전
        state[key] = !state[key];
      }
    },
  },
});

export const toggleActions = toggleSlice.actions;
export default toggleSlice;
