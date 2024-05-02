import {ChangeEvent, useEffect, useState} from "react";
import styles from "./Advertisement.module.css";
import ToggleButton from "../Button/ToggleButton";
import InputContainer from "../Container/InputContainer";
import EnrollButton from "../Button/EnrollButton";
import {useLocation} from "react-router-dom";
interface digitalForm {
  userId?: number;
  title?: string;
  description?: string;
  startDate?: string;
  endDate?: string;
  category?: string;
  directVideoUrl?: string;
  bannerImgUrl?: string;
  selectedContentList?: number[];
  advertVideoUrlList?: string[];
}
export default function Digital() {

  const location = useLocation();
  const initialFormData: digitalForm = location.state;
  const [formData, setFormData] = useState<digitalForm>(initialFormData);

  useEffect(() => {
    console.log("FormData on Digital page:", formData);
  }, [formData]);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: value,
    }));
  };

  const [isDigitalMainExpanded, setDigitalMainExpanded] = useState(false);

  const toggleDigitalMain = () =>
    setDigitalMainExpanded(!isDigitalMainExpanded);

  useEffect(() => {
    console.log("FormData on Digital page:", formData);
  }, []);

  return (
    <div className={`${styles.container}`}>
      <div className={`${styles.item}`}>
        <div className={`${styles.title}`}>
          <div className={`${styles.name}`}>
            메인링크<span>*</span>
          </div>
          <ToggleButton
            isExpanded={isDigitalMainExpanded}
            onToggle={toggleDigitalMain}
          />
        </div>
        {isDigitalMainExpanded && (
          <InputContainer>
            <div className={`${styles.linkUrl}`}>
              <div className={`${styles.subTitle}`}>연결 URL</div>
              <div>
                <input
                  type="text"
                  name="directVideoUrl"
                  onChange={handleChange}
                  className={`${styles.input}`}
                  required
                />
              </div>
            </div>
          </InputContainer>
        )}
      </div>
      <EnrollButton formData={formData} from="digital"/>
    </div>
  );
}
