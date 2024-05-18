import { useEffect, useState } from "react";
import { useQuery } from "react-query";
import { getAdList } from "./userAdvertAPI";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import Loading from "../Loading";
import Select from "react-select";
export interface adList {
  advertId: number;
  title: string;
  description?: string;
  startDate?: string;
  endDate?: string;
  advertType?: string;
  advertCategory?: string;
  directVideoUrl?: string;
  bannerImgUrl?: string;
  selectedContentList?: number[];
  advertVideoUrlList?: string[];
}
// selectAdListBox props 인터페이스
interface SelectAdListBoxProps {
  onAdSelect: (id: number | null) => void;
}
export default function SelectAdListBox({ onAdSelect }: SelectAdListBoxProps) {
  type OptionType = { value: number; label: string };

  const [selectedOption, setSelectedOption] = useState<OptionType | null>(null); // 선택된 옵션 상태
  const [options, setOptions] = useState<OptionType[]>([]); // 옵션 목록 상태
  const userId = useSelector((state: RootState) => state.user.userId);
  const accessToken = useSelector(
    (state: RootState) => state.token.accessToken
  );
  // 예시 광고 데이터
  const exampleAd: adList = {
    advertId: 1,
    title: "맘스터치",
  };
  // useQuery로 데이터 가져오기
  const { data: ads, isLoading } = useQuery(
    ["adsList", userId, accessToken], // 첫 번째 인자는 쿼리 키
    () => getAdList(userId, accessToken), // 두 번째 인자는 fetch 함수
    {
      enabled: !!userId, // userId가 truthy일 때만 쿼리 실행
    }
  );
  useEffect(() => {
    if (ads && ads.data) {
      let userAdsOptions = ads.data.map((ad: adList) => ({
        value: ad.advertId,
        label: ad.title,
      }));
      if (userId !== 1) {
        userAdsOptions = [
          { value: exampleAd.advertId, label: exampleAd.title },
          ...userAdsOptions,
        ];
      }
      setOptions(userAdsOptions);
      if (userAdsOptions.length > 0) {
        setSelectedOption(userAdsOptions[0]); // 첫 번째 옵션을 초기 선택으로 설정
        onAdSelect(userAdsOptions[0].value); // 부모 컴포넌트에 초기 선택값 전달
      }
    }
  }, [ads]);

  useEffect(() => {
    if (selectedOption) {
      console.log(selectedOption); // 옵션 잘 찍히나 확인
    }
  }, [selectedOption]); // selectedOption의 변화감지
  if (isLoading) {
    return <Loading />;
  }

  const handleChange = (option: OptionType | null) => {
    setSelectedOption(option);
    onAdSelect(option ? option.value : null); // 여기서 갖고온 value를 이제 부모 컴포넌트로 전달시키기
  };
  return (
    <Select
      defaultValue={selectedOption}
      options={options}
      value={selectedOption}
      onChange={handleChange}
      className="basic-select"
      classNamePrefix="select"
    />
  );
}
