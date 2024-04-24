import { ChangeEvent, useState } from "react";
import styles from "./Advertisement.module.css";
import ToggleButton from "../Button/ToggleButton";
import InputContainer from "../Container/InputContainer";
import EnrollButton from "../Button/EnrollButton";
interface digitalForm {
  linkName: string;
  url: string;
}
export default function Digital() {
  const [formData, setFormData] = useState<digitalForm>();
  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
  };
  const [isDigitalMainExpanded, setDigitalMainExpanded] = useState(false);
  const toggleDigitalMain = () =>
    setDigitalMainExpanded(!isDigitalMainExpanded);
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
            <div className={`${styles.linkName}`}>
              <div className={`${styles.subTitle}`}>링크이름</div>
              <div>
                <input
                  type="text"
                  name="digital-name"
                  onChange={handleChange}
                  className={`${styles.input}`}
                  required
                />
              </div>
            </div>
            <div className={`${styles.linkUrl}`}>
              <div className={`${styles.subTitle}`}>연결 URL</div>
              <div>
                <input
                  type="text"
                  name="digital-url"
                  onChange={handleChange}
                  className={`${styles.input}`}
                  required
                />
              </div>
            </div>
          </InputContainer>
        )}
      </div>
      <EnrollButton />
    </div>
  );
}
