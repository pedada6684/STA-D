import { ChangeEvent, MouseEvent, useState } from "react";
import styles from "./EnterpriseInfo.module.css";

interface EnterpriseData {
  email: string;
  name: string;
  phone: string;
  company: string;
  comNo: string;
  password: string;
}

interface EditProps {
  onSaveClick: (e: MouseEvent<HTMLDivElement>) => void;
}

export default function EnterpriseEdit({ onSaveClick }: EditProps) {
  const [data, setData] = useState<EnterpriseData>({
    email: "ssafy@gmail.com",
    name: "홍길동",
    phone: "034-234-2342",
    company: "홍길동전자",
    comNo: "000-00-0000",
    password: "",
  });

  const handleChange =
    (field: keyof EnterpriseData) => (e: ChangeEvent<HTMLInputElement>) => {
      setData({ ...data, [field]: e.target.value });
    };
  return (
    <div>
      <div className={`${styles.container}`}>
        <div className={`${styles.item} ${styles.id}`}>
          <div className={`${styles.title}`}>아이디</div>
          <div className={`${styles.space}`}>
            <input
              type="text"
              value={data.email}
              className={styles.txtInput}
              onChange={handleChange("email")}
            />
          </div>
        </div>

        <div className={`${styles.item} ${styles.phone}`}>
          <div className={`${styles.title}`}>회사전화번호</div>
          <div className={`${styles.space}`}>
            <input
              type="text"
              value={data.phone}
              className={styles.txtInput}
              onChange={handleChange("phone")}
            />
          </div>
        </div>
        <div className={`${styles.item} ${styles.regNum}`}>
          <div className={`${styles.title}`}>사업자 등록번호</div>
          <div className={`${styles.space}`}>
            <input
              type="text"
              value={data.comNo}
              className={styles.txtInput}
              onChange={handleChange("comNo")}
            />
          </div>
        </div>
        <div className={`${styles.item} ${styles.name}`}>
          <div className={`${styles.title}`}>등록 사원 이름</div>
          <div className={`${styles.space}`}>
            <input
              type="text"
              value={data.name}
              className={styles.txtInput}
              onChange={handleChange("name")}
            />
          </div>
        </div>
        <div className={`${styles.item} ${styles.name}`}>
          <div className={`${styles.title}`}>비밀번호</div>
          <div className={`${styles.space}`}>
            <input
              type="text"
              value={data.password}
              className={styles.txtInput}
              onChange={handleChange("password")}
            />
          </div>
        </div>
      </div>
    </div>
  );
}
