import Keyboard from "react-simple-keyboard";
import "react-simple-keyboard/build/css/index.css";
import { koreanLayout } from "./koreanLayout";
import { useEffect, useState } from "react";
import hangul from "hangul-js";
import styles from "./Keyboard.module.css";
interface keyboardProps {
  text: string;
  setText: (newText: string) => void;
  onEnter: () => void;
  isVisible: boolean;
}

export default function CustomKeyboard({
  text,
  setText,
  onEnter,
  isVisible,
}: keyboardProps) {
  const [layoutName, setLayoutName] = useState<string>("default");
  const onKeyPress = (key: string) => {
    if (key === "{pre}") {
      const res = text.slice(0, -1);
      setText(res);
    } else if (key === "{shift}") {
      setLayoutName((prev) => (prev === "default" ? "shift" : "default"));
    } else if (key === "{enterNum}" || key === "{enterText}") {
      onEnter();
      console.log(text);
    } else if (key === "{dot}" || key === "{space}") {
      setText(text + (key === "{dot}" ? "." : " "));
    } else {
      //  한글 라이브러리 사용해서 자모음 합치기
      setText(hangul.assemble(hangul.disassemble(text + key)));
    }
  };

  useEffect(() => {
    // isVisible이 변경될 때마다 로그를 찍어봄
    console.log("Keyboard visibility changed: ", isVisible);
  }, [isVisible]);

  return (
    <div
      className={`${styles.keyboardContainer} ${
        isVisible ? styles.nonActive : styles.active
      }`}
    >
      <Keyboard
        layoutName={layoutName}
        layout={{ ...koreanLayout }}
        onKeyPress={onKeyPress}
        display={{
          "{enterText}": "Enter",
          "{shift}": "↑",
          "{.}": ".",
          "{space}": " ",
          "{dot}": ".",
          "{pre}": "←",
        }}
      />
    </div>
  );
}
