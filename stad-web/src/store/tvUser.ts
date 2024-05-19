import { PayloadAction, createSlice } from "@reduxjs/toolkit";
// 유저 프로필 인터페이스
interface Profile {
  userNickname: string;
  profile: string;
}
// 유저 아이디 + 프로필 인터페이스
interface User {
  userId: number;
  profiles: Profile[];
}

interface SelectedProfile extends Profile {
  userId: number;
}

interface tvUserType {
  isTvLoggedIn: boolean;
  users: User[]; // 여러 유저 관리
  selectedProfile?: SelectedProfile; // 선택한 프로필 저장
}

const initialState: tvUserType = {
  isTvLoggedIn: false,
  users: [],
};

const tvUserSlice = createSlice({
  name: "tvUser",
  initialState,
  reducers: {
    // 기존 유저 아니면 새로운 유저 추가
    addUser: (
      state,
      action: PayloadAction<{ userId: number; profile: Profile }>
    ) => {
      state.isTvLoggedIn = true;
      const existingUser = state.users?.find(
        (user) => user?.userId === action.payload.userId
      );
      if (existingUser) {
        // 기존 유저의 프로필 목록에 새로운 프로필 추가
        existingUser?.profiles.push(action.payload.profile);
        // 프로필이 4개를 초과하면 가장 오래된 프로필 삭제
        if (existingUser.profiles.length > 4) {
          existingUser.profiles.shift(); // 가장 오래된 프로필 제거
        }
      } else {
        // 새로운 유저 추가
        state.users?.push({
          userId: action.payload.userId,
          profiles: [action.payload.profile],
        });
      }
      // 유저가 4명을 초과하면 가장 오래된 유저 삭제
      if (state.users.length > 4) {
        state.users.shift();
      }
    },

    logoutUser: (state) => {
      state.isTvLoggedIn = false;
      state.users = [];
    },
    // 로그인 후 프로필 저장하기 위한 프로필 저장 슬라이스
    setSelectedProfile: (
      state,
      action: PayloadAction<{ userId: number; profile: Profile }>
    ) => {
      state.selectedProfile = {
        ...action.payload.profile,
        userId: action.payload.userId,
      }; // 선택된 프로필 설정
    },
  },
});

export const tvUserActions = tvUserSlice.actions;
export default tvUserSlice;
