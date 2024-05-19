import { ChangeEvent, MouseEvent, useEffect, useState } from "react";
import styles from "./EnterpriseInfo.module.css";
import axios from "axios";
import { useSelector } from "react-redux";
import { RootState } from "../../../store";

export interface EnterpriseData {
  userId?: number;
  email: string;
  name: string;
  phone: string;
  department?: string;
  company: string;
  comNo: string;
  password: string;
  profile: File | null;
  [key: string]: string | number | File | null | undefined;
}

interface EditProps {
  data?: EnterpriseData;
  onSaveClick: (e: MouseEvent<HTMLDivElement>) => void;
  updateData: (updatedData: EnterpriseData) => void;
}

export default function EnterpriseEdit({
  data,
  onSaveClick,
  updateData,
}: EditProps) {
  const userId = useSelector((state: RootState) => state.user.userId);
  const userCompany = useSelector((state: RootState) => state.user.userCompany);
  console.log(userCompany);
  const [formData, setFormData] = useState<EnterpriseData>(
    data || {
      userId: userId,
      email: "",
      name: "",
      phone: "",
      department: "",
      company: userCompany,
      comNo: "",
      password: "",
      profile: null,
    }
  );
  useEffect(() => {
    if (data) {
      setFormData({
        ...data,
        userId: userId, // userId 업데이트 보장
        company: userCompany,
      });
    }
  }, [data]);

  const handleChange =
    (field: keyof EnterpriseData) => (e: ChangeEvent<HTMLInputElement>) => {
      const value = e.target.value === "" ? null : e.target.value;
      const updatedData = { ...formData, [field]: value };
      setFormData(updatedData);
      updateData(updatedData);
    };
  return (
    <div>
      <div className={`${styles.container}`}>
        <div className={`${styles.item} ${styles.id}`}>
          <div className={`${styles.title}`}>아이디</div>
          <div className={`${styles.space}`}>
            <input
              type="text"
              value={formData.email}
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
              value={formData.phone}
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
              value={formData.comNo}
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
              value={formData.name}
              className={styles.txtInput}
              onChange={handleChange("name")}
            />
          </div>
        </div>
        <div className={`${styles.item} ${styles.name}`}>
          <div className={`${styles.title}`}>부서명</div>
          <div className={`${styles.space}`}>
            <input
              type="text"
              value={formData.department}
              className={styles.txtInput}
              onChange={handleChange("department")}
            />
          </div>
        </div>
        <div className={`${styles.item} ${styles.name}`}>
          <div className={`${styles.title}`}>비밀번호</div>
          <div className={`${styles.space}`}>
            <input
              type="password"
              value={formData.password}
              className={styles.txtInput}
              onChange={handleChange("password")}
              required
            />
          </div>
        </div>
      </div>
    </div>
  );
}
