import { ChangeEvent, useEffect, useState } from "react";
import styles from "../../components/Enroll/Advertisement.module.css";
import ToggleButton from "../../components/Button/ToggleButton";
import InputContainer from "../../components/Container/InputContainer";
import EnrollButton from "../../components/Button/EnrollButton";
import { useLocation } from "react-router-dom";
import DigitalModifyButton from "../../components/Button/DigitalModifyButton";
interface digitalForm {
  advertId?: number;
  title?: string;
  description?: string;
  startDate?: string;
  endDate?: string;
  advertType?: string;
  advertCategory?: string;
  directVideoUrl?: string;
  bannerImgUrl?: string;
  selectedContentList?: number[];
  advertVideoUrlList?: advertVideoUrlList[];
}
export interface advertVideoUrlList {
  advertVideoId: number;
  advertVideoUrl: string;
}

export default function DigitalDetail() {
  const location = useLocation();
  const initialFormData: digitalForm = location.state.formData;
  const [formData, setFormData] = useState<digitalForm>(initialFormData);

  useEffect(() => {
    console.log("FormData on DigitalDetail page:", formData);
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
    console.log("FormData on DigitalDetail page:", formData);
  }, []);

  return (
    <div className={`${styles.container}`}>
      <div className={`${styles.item}`}>
        <div className={`${styles.title}`}>
          <div className={`${styles.name}`}>
            메인링크<span>*</span>
          </div>
          <ToggleButton />
        </div>
        {!isDigitalMainExpanded && (
          <InputContainer>
            <div className={`${styles.linkUrl}`}>
              <div className={`${styles.subTitle}`}>연결 URL</div>
              <div>
                <input
                  type="text"
                  name="directVideoUrl"
                  value={formData.directVideoUrl}
                  onChange={handleChange}
                  className={`${styles.input}`}
                  required
                />
              </div>
            </div>
          </InputContainer>
        )}
      </div>
      <DigitalModifyButton
        formData={formData}
      />
    </div>
  );
}
