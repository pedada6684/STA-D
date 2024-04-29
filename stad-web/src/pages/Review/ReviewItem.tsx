import styles from "./ReveiwItem.module.css";
export default function ReviewItem() {
  return (
    <div className={`${styles.container}`}>
      <div className={`${styles.nc}`}>
        <div className={`${styles.nickname}`}>닉네임</div>
        <div className={`${styles.createdAt}`}>2023/03/21</div>
      </div>
      <div className={`${styles.gci}`}>
        <div className={`${styles.goods}`}>민형이가 좋아하는 딸기</div>
        <div className={`${styles.content}`}>
          괜찮았는데, 딸기가 좀 오래됐어요.
        </div>
        <div className={`${styles.image}`}>상품 이미지</div>
      </div>
    </div>
  );
}
