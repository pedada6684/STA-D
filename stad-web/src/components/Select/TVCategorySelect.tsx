import Select, { SingleValue } from "react-select";
export interface OptionType {
  value: string;
  label: string;
}
export interface TVCategorySelectProps {
  onChange: (option: SingleValue<OptionType>) => void;
}
export default function TVCategorySelect({ onChange }: TVCategorySelectProps) {
  const options: OptionType[] = [
    { value: "드라마", label: "드라마" },
    { value: "예능", label: "예능" },
    { value: "교양/다큐멘터리", label: "교양/다큐멘터리" },
    { value: "해외", label: "해외" },
  ];

  const customStyles = {
    valueContainer: (provided: any) => ({
      ...provided,
      padding: "2px 2rem",
      backgroundColor: "#141414",
      color: "white",
    }),
    singleValue: (provided: any) => ({
      ...provided,
      color: "white",
      fontSize: "1.3rem",
    }),
    menu: (provided: any) => ({
      ...provided,
      border: "1px solid #565656",
    }),
    menuList: (provided: any) => ({
      ...provided,
      backgroundColor: "#212121",
    }),
    option: (provided: any) => ({
      ...provided,
      color: "white",
      backgroundColor: "#212121",
    }),
  };

  return (
    <Select
      options={options}
      placeholder="장르"
      styles={customStyles}
      onChange={onChange}
    />
  );
}
