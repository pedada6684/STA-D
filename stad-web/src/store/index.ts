import { combineReducers, configureStore } from "@reduxjs/toolkit";
import storage from "redux-persist/lib/storage";
import loadingSlice from "./loading";
import userSlice from "./user";
import { persistReducer, persistStore } from "redux-persist";
import tokenSlice, { tokenActions } from "./token";

const persistConfig = {
  key: "root",
  storage,
  whiteList: ["loading", "user", "token"],
};

// 초기화 방지를 위한 redux-persist
const rootReducer = combineReducers({
  loading: loadingSlice.reducer,
  user: userSlice.reducer,
  token: tokenSlice.reducer,
});

const persistedReducer = persistReducer(persistConfig, rootReducer);

const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: [
          "persist/PERSIST",
          "persist/REHYDRATE",
          "persist/PAUSE",
          "persist/PURGE",
          "persist/REGISTER",
        ],
      },
    }),
});

// persistSotre 함수로 persistor 객체 생성
const persistor = persistStore(store);

// store와 persistor를 내보냄
export { store, persistor };

// store의 타입 미리 export 해두기
export type RootState = ReturnType<typeof store.getState>;
