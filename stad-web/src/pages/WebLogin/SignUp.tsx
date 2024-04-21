import Container from "../../components/Container/Container";
import WebNav from "../../components/Nav/WebNav";
import styles from "./SignUp.module.css";
import company from "../../assets/black_company.png";
import { MouseEvent, useState } from "react";
import SignUpForm from "./SignUpForm";
export default function SignUp() {
  const [isClick, setIsClick] = useState(false);
  const handleClick = (e: MouseEvent<HTMLDivElement>) => {
    setIsClick(true);
  };
  return (
    <div>
      <Container>
        <WebNav />
        <div className={`${styles.content}`}>
          <div className={`${styles.title}`}>회원가입</div>
          <div
            className={`${styles.btn} ${isClick ? styles.activeBorder : ""}`}
            onClick={handleClick}
          >
            <img src={company} alt="기업 아이콘" />
            <div className={`${styles.subTitle}`}>기업회원</div>
          </div>
          <div className={`${styles.description}`}>
            회원가입에 필요한 정보를 입력해 주세요.
          </div>
          <SignUpForm />
        </div>
      </Container>
    </div>
  );
}
