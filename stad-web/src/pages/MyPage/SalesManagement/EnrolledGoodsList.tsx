import styles from "./EnrolledList.module.css";
import {useEffect, useState} from "react";
import {useSelector} from "react-redux";
import {RootState} from "../../../store";
import {getAdvertList, getProductTypeList} from "../../AdEnroll/AdEnrollApi";

interface productType {
  productTypeId? : number;
  productImgUrl? : string;
  name? : string;
  price? : number;
  quantity? : number;
  cnt? : number;
}

export default function EnrolledGoodsList() {
  const [productTypeList, setProductTypeList] = useState<productType[]>([]); // 광고 목록
  const userId = useSelector((state: RootState)=> state.user.userId);

  useEffect(() => {
    const fetchAdsList = async () => {
      try {
        const data = await getProductTypeList(userId);
        setProductTypeList(data);
      } catch (error) {
        console.error('상품 목록 조회 실패 : ', error);
      }
    };

    fetchAdsList();
  }, []);

  return (
    <div className={`${styles.container}`}>
      <div className={`${styles.title}`}>등록 상품 목록</div>
      <div className={`${styles.gridGoodsContainer}`}>
        <div className={`${styles.gridCategory}`}>이미지</div>
        <div className={`${styles.gridCategory}`}>상품명</div>
        <div className={`${styles.gridCategory}`}>가격정보</div>
        <div className={`${styles.gridCategory}`}>보유재고</div>
        <div className={`${styles.gridCategory}`}>주문 수</div>
        {productTypeList.map((pt) => (
            <>
        <div className={`${styles.gridItem} ${styles.gridImg}`}>
          <img src={pt.productImgUrl} alt="Ad"/>
        </div>
          <div className={`${styles.gridItem}`}>{pt.name}</div>
        <div className={`${styles.gridItem}`}>{pt.price}</div>
        <div className={`${styles.gridItem}`}>{pt.quantity}</div>
        <div className={`${styles.gridItem}`}>{pt.cnt !== null ? pt.cnt : 0}</div>
        </>
        ))
        }
      </div>
    </div>
  );
}
