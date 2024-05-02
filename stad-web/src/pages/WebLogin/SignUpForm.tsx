import { ChangeEvent, FormEvent, useState } from "react";
import styles from "./SignUpForm.module.css";
import { useMutation } from "react-query";
import axios from "axios";
import { useNavigate } from "react-router-dom";
interface signUpFormData {
  email: string;
  phone: string[];
  name: string;
  password: string;
  company: string;
  comNo: string[]; // 사업자 등록번호
  department: string;
}

interface combinationData {
  email: string;
  phone: string;
  name: string;
  password: string;
  company: string;
  comNo: string; // 사업자 등록번호
  department: string;
}
export async function postSignUp(formData: combinationData) {
  const { data } = await axios.post(`/api/v1/auth/webjoin`, formData);
  return data;
}
export default function SignUpForm() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState<signUpFormData>({
    email: "",
    phone: ["", "", ""],
    name: "",
    password: "",
    company: "",
    comNo: ["", "", ""],
    department: "",
  });

  // 리액트 쿼리의 useMutation 훅 사용
  const mutation = useMutation(postSignUp, {
    onSuccess: (data) => {
      console.log("회원가입 완료", data);
      window.alert("회원가입이 완료되었습니다");
      navigate(`/web-login`);
    },
    onError: (error) => {
      console.log("회원가입 실패", error);
    },
  });

  // 비밀번호 확인을 위한 useState
  const [passwordConfirm, setPasswordConfirm] = useState("");
  // 비밀번호 불일치 케이스를 위한 에러문구
  const [error, setError] = useState("");

  // 회사 전화번호, 사업자 등록번호를 입력 필드 관리
  const handleComNoChange =
    (index: number) => (e: ChangeEvent<HTMLInputElement>) => {
      const newComNo = [...formData.comNo];
      newComNo[index] = e.target.value;
      setFormData({ ...formData, comNo: newComNo });
      console.log("회사전화번호", newComNo);
    };

  const handlePhoneChange =
    (index: number) => (e: ChangeEvent<HTMLInputElement>) => {
      const newPhone = [...formData.phone];
      newPhone[index] = e.target.value;
      setFormData({ ...formData, phone: newPhone });
    };

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    // 비밀번호 확인 필드만 따로 변경 감지 시켜주기
    if (name === "passwordConfirm") {
      setPasswordConfirm(value);
      // 비밀번호와 비밀번호 확인 입력이 동시에 검증
      if (formData.password !== value) {
        setError("비밀번호가 일치하지 않습니다.");
      } else {
        setError("");
      }
    } else {
      setFormData((prevState) => ({
        ...prevState,
        [name]: value,
      }));
    }
  };
  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    // 제출 전 회사 전화번호, 사업자 등록번호 결합
    const combinationData = {
      ...formData,
      phone: formData.phone.join("-"), // 각 phone 배열 하이픈으로 결함
      comNo: formData.comNo.join("-"),
    };

    //mutation mutate를 이용한 데이터 전송
    mutation.mutate(combinationData);

    console.log("보낼 FormData", combinationData);
  };
  return (
    <>
      <form className={`${styles.container}`} onSubmit={handleSubmit}>
        <div className={`${styles.itemContainer}`}>
          <div className={styles.item}>
            <div className={styles.title}>
              이메일<span>*</span>
            </div>
            <div className={styles.inputContainer}>
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="이메일을 입력해주세요."
                required
              />
            </div>
          </div>
          <div className={styles.item}>
            <div className={styles.title}>
              회사전화번호<span>*</span>
            </div>
            <div className={`${styles.inputContainer} ${styles.inputGroup}`}>
              <input
                type="text"
                value={formData.phone[0]}
                onChange={handlePhoneChange(0)}
                required
              />
              <span>-</span>
              <input
                type="text"
                value={formData.phone[1]}
                onChange={handlePhoneChange(1)}
                required
              />
              <span>-</span>
              <input
                type="text"
                value={formData.phone[2]}
                onChange={handlePhoneChange(2)}
                required
              />
            </div>
          </div>
          <div className={styles.item}>
            <div className={styles.title}>
              이름<span>*</span>
            </div>
            <div className={styles.inputContainer}>
              <input
                type="name"
                name="name"
                value={formData.name}
                onChange={handleChange}
                placeholder="이름을 입력해 주세요."
                required
              />
            </div>
          </div>
          <div className={styles.item}>
            <div className={styles.title}>
              비밀번호<span>*</span>
            </div>
            <div className={styles.inputContainer}>
              <input
                type="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                placeholder="비밀번호 (영문, 숫자, 특수문자 조합 8~16자리)"
                required
              />
            </div>
          </div>
          <div className={`${styles.item} ${styles.pwConfirm}`}>
            <div className={`${styles.items}`}>
              <div className={styles.title}>
                비밀번호확인<span>*</span>
              </div>
              <div className={styles.inputContainer}>
                <input
                  type="password"
                  name="passwordConfirm"
                  value={passwordConfirm}
                  onChange={handleChange}
                  required
                  placeholder="동일한 비밀번호 입력"
                />
              </div>
            </div>
            {error && <div className={styles.error}>{error}</div>}
          </div>
          <div className={styles.item}>
            <div className={styles.title}>
              회사명<span>*</span>
            </div>
            <div className={styles.inputContainer}>
              <input
                type="text"
                name="company"
                value={formData.company}
                onChange={handleChange}
                required
              />
            </div>
          </div>
          <div className={styles.item}>
            <div className={styles.title}>
              사업자등록번호<span>*</span>
            </div>
            <div className={`${styles.inputContainer} ${styles.inputGroup}`}>
              <input
                type="text"
                maxLength={3}
                value={formData.comNo[0]}
                onChange={handleComNoChange(0)}
                required
              />
              <span>-</span>
              <input
                type="text"
                maxLength={2}
                value={formData.comNo[1]}
                onChange={handleComNoChange(1)}
                required
              />
              <span>-</span>
              <input
                type="text"
                maxLength={5}
                value={formData.comNo[2]}
                onChange={handleComNoChange(2)}
                required
              />
            </div>
          </div>
          <div className={styles.item}>
            <div className={styles.title}>부서명(선택)</div>
            <div className={styles.inputContainer}>
              <input
                type="text"
                name="department"
                value={formData.department}
                onChange={handleChange}
                placeholder="부서명을 입력해주세요."
              />
            </div>
          </div>
        </div>
        <div className={styles.submitContainer}>
          <button type="submit" className={styles.submitButton}>
            회원가입
          </button>
        </div>
      </form>
    </>
  );
}
