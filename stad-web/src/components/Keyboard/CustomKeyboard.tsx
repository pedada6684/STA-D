import Keyboard from "react-simple-keyboard";
import "react-simple-keyboard/build/css/index.css";
import { koreanLayout } from "./koreanLayout";
import { Dispatch, SetStateAction, useState } from "react";
import hangul from "hangul-js";
interface keyboardProps {
  text: string;
  setText: Dispatch<SetStateAction<string>>;
  onEnter: () => void;
}

export default function CustomKeyboard({
  text,
  setText,
  onEnter,
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
    } else if (key === "{dot}") {
      setText((prev: string) => prev + ".");
    } else if (key === "{space}") {
      setText((prev: any) => prev + " ");
    } else {
      //  한글 라이브러리 사용해서 자모음 합치기
      setText(hangul.assemble(hangul.disassemble(text + key)));
    }
  };

  return (
    <div>
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
