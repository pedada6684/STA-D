import styles from "./EnrolledList.module.css";
export default function EnrolledGoodsList() {
  return (
    <div className={`${styles.container}`}>
      <div className={`${styles.title}`}>등록 상품 목록</div>
      <div className={`${styles.gridGoodsContainer}`}>
        <div className={`${styles.gridCategory}`}>이미지</div>
        <div className={`${styles.gridCategory}`}>상품명</div>
        <div className={`${styles.gridCategory}`}>가격정보</div>
        <div className={`${styles.gridCategory}`}>보유재고</div>
        <div className={`${styles.gridCategory}`}>주문 수</div>
        {/* {dummyData.map((ad) => (
          <> */}
        <div className={`${styles.gridItem} ${styles.gridImg}`}>
          <img
            src="https://d2gfz7wkiigkmv.cloudfront.net/pickin/2/1/2/lCG7F0-DQ6GEDXR4olZn8w"
            alt="Ad"
          />
        </div>
        <div className={`${styles.gridItem}`}>퍼퓸 스프레이</div>
        <div className={`${styles.gridItem}`}>28,000원</div>
        <div className={`${styles.gridItem}`}>12개</div>
        <div className={`${styles.gridItem}`}>120개</div>
        {/* </>
        ))} */}
      </div>
    </div>
  );
}
