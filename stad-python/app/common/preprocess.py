from kiwipiepy import Kiwi
import re

match = "/-'?!.,#$%\'()*+-/:;<=>@[\\]^_`{|}~" + '""“”’' + '∞θ÷α•à−β∅³π‘₹´°£€\×™√²—–&'
match_mapping = {"‘": "'", "₹": "e", "´": "'", "°": "", "€": "e", "™": "tm", "√": " sqrt ", "×": "x", "²": "2",
                 "—": "-", "–": "-", "’": "'", "_": "-", "`": "'", '“': '"', '”': '"', '“': '"', "£": "e",
                 '∞': 'infinity', 'θ': 'theta', '÷': '/', 'α': 'alpha', '•': '.', 'à': 'a', '−': '-', 'β': 'beta',
                 '∅': '', '³': '3', 'π': 'pi', }


def clean(text: str, mapped: str, mapping: dict):
    for p in mapping:
        text = text.replace(p, mapping[p])

    for p in mapped:
        text = text.replace(p, f' {p} ')

    specials = {'\u200b': ' ', '…': ' ... ', '\ufeff': '', 'करना': '', 'है': ''}
    for s in specials:
        text = text.replace(s, specials[s])

    return text.strip()


def clean_str(text: str):
    pattern = '([a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+)'  # E-mail제거
    text = re.sub(pattern=pattern, repl='', string=text)

    pattern = '(http|ftp|https)://(?:[-\w.]|(?:%[\da-fA-F]{2}))+'  # URL제거
    text = re.sub(pattern=pattern, repl='', string=text)

    pattern = '([ㄱ-ㅎㅏ-ㅣ]+)'  # 한글 자음, 모음 제거
    text = re.sub(pattern=pattern, repl='', string=text)

    pattern = '([A-z]+)'  # 영어 대, 소문자 제거
    text = re.sub(pattern=pattern, repl='', string=text)

    pattern = '<[^>]*>'  # HTML 태그 제거
    text = re.sub(pattern=pattern, repl='', string=text)

    pattern = '[^\w\s\n]'  # 이모티콘 제거
    text = re.sub(pattern=pattern, repl='', string=text)

    pattern = '[-=+,#/\?:^$.@*\"※~&%ㆍ!』\\‘|\(\)\[\]\<\>`\'…》]'  # 특수문자 제거
    text = re.sub(pattern=pattern, repl='', string=text)

    text = re.sub('\n', '.', string=text)  # enter 제거

    text = re.sub(r'[@%\*=()/~#&\+á?\xc3\xa1\-\|\.\:\;\!\-\,\_\~$\'\"]', '', text)  # remove punctuation
    text = re.sub(r'\d+', '', text)  # remove number
    text = re.sub(r'\s+', ' ', text)  # remove extra space
    text = re.sub(r'\s+', ' ', text)  # remove spaces
    text = re.sub(r"^\s+", '', text)  # remove space from start
    text = re.sub(r'\s+$', '', text)  # remove space from the end

    return text


def remove_text(text: list) -> list:
    lines = [clean_str(clean(i, match, match_mapping)) for i in text if i != '\n']
    return lines


def preprocess(text: str) -> str:
    text = remove_text(text.split('\n'))
    token_list = kiwi.tokenize(' '.join(text))

    wordslist = []
    for token in token_list:
        if token.tag == 'NNG' and token.len > 1:
            wordslist.append(token.form)
    return ' '.join(list(set(wordslist)))


kiwi = Kiwi()
