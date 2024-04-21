import {
  SelectReviewGoodsBox,
  SelectReviewSortBox,
} from "../../components/Select/SelectBox";
import styles from "./Review.module.css";
import ReviewItem from "./ReviewItem";
export default function Review() {
  return (
    <div className={`${styles.container}`}>
      <div className={styles.title}>리뷰관리</div>
      <div className={`${styles.subTitle}`}>리뷰</div>
      <div className={`${styles.reviews}`}>
        <div className={`${styles.lowReviews} ${styles.category}`}>
          <div className={`${styles.reviewsText}`}>낮은 별점 리뷰</div>
          <div className={`${styles.reviewsNum}`}>210</div>
        </div>
        <div className={`${styles.recentReviews} ${styles.category}`}>
          <div className={`${styles.reviewsText}`}>최신 리뷰</div>
          <div className={`${styles.reviewsNum}`}>21</div>
        </div>
      </div>
      <div className={`${styles.select}`}>
        <SelectReviewSortBox />
        <SelectReviewGoodsBox />
      </div>
      <div className={`${styles.item}`}>
        <ReviewItem />
      </div>
    </div>
  );
}
