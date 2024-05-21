import React, { useState } from "react";
import styles from "./CollapsibleText.module.css";

interface CollapsibleTextProps {
  text: string;
  maxLength: number;
}

const CollapsibleText: React.FC<CollapsibleTextProps> = ({
  text,
  maxLength,
}) => {
  const [isExpanded, setIsExpanded] = useState(false);

  const toggleExpand = () => {
    setIsExpanded(!isExpanded);
  };

  const displayText = isExpanded ? text : `${text.substring(0, maxLength)}...`;

  return (
    <div className={styles.collapsibleText}>
      <span>{displayText}</span>
      {text.length > maxLength && (
        <span onClick={toggleExpand} className={styles.readMore}>
          {isExpanded ? " 접기" : " 더보기"}
        </span>
      )}
    </div>
  );
};

export default CollapsibleText;
