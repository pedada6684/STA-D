import { useNavigate } from "react-router-dom";
import Container from "../../components/Container/Container";
import WebNav from "../../components/Nav/WebNav";
import styles from "./WebLogin.module.css";
import axios, { AxiosResponse } from "axios";
import { useMutation } from "react-query";
import { FormEvent } from "react";
import { useDispatch } from "react-redux";
import { tokenActions } from "../../store/token";
import Cookies from "universal-cookie";

interface LoginProps {
  email: string;
  password: string;
}

interface LoginResponse {
  headers: {
    accessToken: string;
  };
}

export default function WebLogin() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const cookies = new Cookies();
  // 로그인 요청
  const loginMutation = useMutation<
    AxiosResponse<LoginResponse>,
    Error,
    LoginProps
  >((data) => axios.post(`/api/v1/auth/weblogin`, data), {
    onSuccess: (response) => {
      console.log(response);
      const fullToken = response.headers["authorization"];
      if (fullToken) {
        const accessToken = fullToken.replace("Bearer ", "");
        console.log(accessToken);
        // axios 인스턴스에 토큰 설정
        axios.defaults.headers.common[
          "Authorization"
        ] = `Bearer ${accessToken}`;
        dispatch(tokenActions.getAccessToken(accessToken));
      }
      const refreshToken = response.headers["refreshToken"];
      console.log(refreshToken);
      if (refreshToken) {
        // 리프레시 토큰을 쿠키에 저장
        cookies.set("refreshToken", refreshToken, { path: "/" });
      }
      // 페이지 이동
      navigate({
        pathname: "/web-main",
      });
    },
    onError: (error) => {
      console.log("로그인 실패", error);
    },
  });

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const target = e.target as HTMLFormElement;
    const email = target.elements.namedItem("email") as HTMLInputElement;
    const password = target.elements.namedItem("password") as HTMLInputElement;
    try {
      await loginMutation.mutateAsync({
        email: email.value,
        password: password.value,
      });
    } catch (error) {}
  };
  return (
    <div>
      <Container>
        <WebNav />
        <div className={`${styles.content}`}>
          <form onSubmit={handleSubmit} className={`${styles.container}`}>
            <div className={`${styles.title}`}>로그인</div>
            <div className={`${styles.inputContainer}`}>
              <div className={`${styles.item}`}>
                <input
                  type="email"
                  name="email"
                  placeholder="이메일을 입력해주세요."
                />
              </div>
              <div className={`${styles.item}`}>
                <input
                  type="password"
                  name="password"
                  placeholder="비밀번호를 입력해주세요."
                />
              </div>
            </div>
            <div className={styles.submitContainer}>
              <button type="submit" className={styles.submitButton}>
                로그인
              </button>
            </div>
            {/* <div className={`${styles.signUp}`}>
              아직 회원이 아니십니까?<div>회원가입</div>
            </div> */}
          </form>
        </div>
      </Container>
    </div>
  );
}
