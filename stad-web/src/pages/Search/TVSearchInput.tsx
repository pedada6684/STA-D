import { ChangeEvent, KeyboardEvent, MouseEvent } from "react";
import { Video } from "../Category/TVDetail";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { GetSearch } from "./TVSearchAPI";
import { useMutation } from "react-query";
import search from "../../assets/ic_sharp-search.png";
import styles from "./TVSearch.module.css";
import { searchProps } from "./TVSearch";
interface SearchInputProps {
  value?: string;
  onChange: (e: ChangeEvent<HTMLInputElement>) => void; // 검색키워드 입력
  onSearch: (results: searchProps[]) => void;
  onSearchAttempted: () => void; // 검색 시도 여부 체크
}

export default function TVSearchInput({
  value = "",
  onChange,
  onSearch,
  onSearchAttempted,
}: SearchInputProps) {
  const handleSearchClick = async (e: MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    onSearchAttempted(); // 검색 시도 상태 업데이트
    const response = await GetSearch(value);
    console.log(response);
    // 검색 결과가 있으면 그 결과를 사용하고, 없으면 null로 설정
    onSearch(response ? response : null);
  };
  const handleKeyPress = async (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      e.preventDefault();
      executeSearch();
    }
  };

  const executeSearch = async () => {
    onSearchAttempted();
    const response = await GetSearch(value);
    onSearch(response ? response : []);
  };

  return (
    <div className={`${styles.inputContainer}`}>
      <input
        className={`${styles.Input}`}
        value={value}
        onKeyPress={handleKeyPress}
        onChange={onChange}
        placeholder="검색어를 입력하세요"
      />
      <button onClick={handleSearchClick}>
        <img src={search} alt="검색창" />
      </button>
    </div>
  );
}
