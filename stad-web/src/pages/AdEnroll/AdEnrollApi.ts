export const advertVideoUpload = async (files: FileList | null) => {
    if (!files || files.length === 0) return;

    const formData = new FormData();
    Array.from(files).forEach(file => {
        formData.append('videoList', file);
    });

    try {
        const response = await fetch(`/api/advert-video/add-video-list`, {
            method: 'POST',
            body: formData
        });
        if (!response.ok) {
            throw new Error('동영상 업로드 요청 실패');
        }
        const data = await response.json(); // JSON 형태로 응답 받음
        const result = data.data;
        console.log(result);
        return result;
    } catch (error) {
        console.error('동영상 업로드 서버 요청 실패 : ', error);
        return null;
    }
};

export const bannerImgUpload = async (files: FileList | null) => {
    if (!files || files.length === 0) return;

    const formData = new FormData();
    formData.append('bannerImg',files[0]);

    try {
        const response = await fetch(`/api/advert-video/add-banner`, {
            method: 'POST',
            body: formData
        });
        if (!response.ok) {
            throw new Error('배너 이미지 업로드 요청 실패');
        }
        const data = await response.json(); // JSON 형태로 응답 받음
        const result = data;
        console.log(result);
        return result;
    } catch (error) {
        console.error('배너 이미지 서버 요청 실패 : ', error);
        return null;
    }
};