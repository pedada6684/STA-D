import { useQuery } from "react-query";
import styles from "./EnterpriseInfo.module.css";
import { RootState } from "../../../store";
import { useSelector } from "react-redux";
import Loading from "../../../components/Loading";
import { EnterpriseData } from "./EnterprisesEdit";
import { GetEnterpriseInfo } from "./EnterpriseApi";

interface EnterpriseInfoProps {
  onLoaded: (data: EnterpriseData) => void;
}

export default function EnterpriseInfo({ onLoaded }: EnterpriseInfoProps) {
  const userId = useSelector((state: RootState) => state.user.userId);
  const tokenObj = useSelector((state: RootState) => state.token);
  // Redux 스토어에서 accessToken을 추출
  const accessToken = tokenObj.accessToken;
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ["user"],
    queryFn: () => GetEnterpriseInfo(userId, accessToken || ""),
    onSuccess: (data) => onLoaded(data),
  });

  if (isLoading) {
    return (
      <div>
        <Loading />
      </div>
    ); // 로딩 상태 표시
  }

  return (
    <div className={`${styles.container}`}>
      <div className={`${styles.item} ${styles.id}`}>
        <div className={`${styles.title}`}>아이디</div>
        <div className={`${styles.space}`}>{data.email}</div>
      </div>

      <div className={` ${styles.item}  ${styles.phone}`}>
        <div className={`${styles.title}`}>회사전화번호</div>
        <div className={`${styles.space}`}>{data.phone}</div>
      </div>
      <div className={`${styles.item} ${styles.regNum}`}>
        <div className={`${styles.title}`}>사업자 등록번호</div>
        <div className={`${styles.space}`}>{data.comNo}</div>
      </div>
      <div className={`${styles.item} ${styles.name}`}>
        <div className={`${styles.title}`}>등록 사원 이름</div>
        <div className={`${styles.space}`}>{data.name}</div>
      </div>
      <div className={`${styles.item} ${styles.name}`}>
        <div className={`${styles.title}`}>부서명</div>
        <div className={`${styles.noSpace}`}>{data.department}</div>
      </div>
    </div>
  );
}
