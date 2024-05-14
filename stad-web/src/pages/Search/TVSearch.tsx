import { ChangeEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import TVNav from "../../components/Nav/TVNav";
import Content from "../../components/Container/Content";
import TVSearchInput from "./TVSearchInput";
import styles from "./TVSearch.module.css";
import TVContainer from "../../components/Container/TVContainer";
import CustomKeyboard from "../../components/Keyboard/CustomKeyboard";
import { GetSearch } from "./TVSearchAPI";

export interface searchProps {
  conceptId: number;
  thumbnailUrl: string;
  title: string;
}

export default function TVSearch() {
  const [searchValue, setSearchValue] = useState("");
  const navigate = useNavigate();
  const [searchResults, setSearchResults] = useState<searchProps[] | null>(
    null
  );
  // 검색 시도 상태 추가
  const [searchAttempted, setSearchAttempted] = useState(false);

  // function handleChange(e: ChangeEvent<HTMLInputElement>) {
  //   const nextValue = e.target.value;
  //   console.log("검색 키워드", nextValue);
  //   setSearchValue(nextValue);
  //   // 검색창의 값이 변경될 때마다 이전 검색 결과를 초기화하고, 검색 시도 상태를 false로 설정
  //   setSearchResults(null); // 이전 검색 결과 초기화
  //   setSearchAttempted(false); // 검색 시도 상태 초기화
  // }

  const setText = (newText: string) => {
    setSearchValue(newText);
    // 검색창의 값이 변경될 때마다 이전 검색 결과를 초기화하고, 검색 시도 상태를 false로 설정
    setSearchResults(null);
    setSearchAttempted(false);
  };

  const executeSearch = async () => {
    setSearchAttempted(true);
    const response = await GetSearch(searchValue);
    setSearchResults(response ? response : []);
  };

  return (
    <div>
      <TVContainer>
        <TVNav />
        <Content>
          <TVSearchInput
            value={searchValue}
            onChange={(e) => setText(e.target.value)}
            onSearch={setSearchResults}
            onSearchAttempted={() => setSearchAttempted(true)} // 검색 시도 됨 여부 체크
          />
          <CustomKeyboard
            text={searchValue}
            setText={setSearchValue}
            onEnter={executeSearch}
          />
          {searchValue.length > 0 && searchAttempted && (
            <>
              {searchResults && searchResults.length > 0 ? (
                <>
                  <div className={styles.gridContainer}>
                    {searchResults.map((data, index) => (
                      <div
                        className={`${styles.thumbnail}`}
                        key={index}
                        onClick={() => navigate(`/tv/${data.conceptId}`)}
                      >
                        <img src={data.thumbnailUrl} alt="영상 썸네일" />
                        <div className={`${styles.title}`}>{data.title}</div>
                      </div>
                    ))}
                  </div>
                </>
              ) : (
                <div className={styles.noContent}>검색 결과가 없습니다.</div>
              )}
            </>
          )}
        </Content>
      </TVContainer>
    </div>
  );
}
