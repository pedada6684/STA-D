import {useSelector} from "react-redux";
import {RootState} from "../../store";

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

export const getContentConcept = async () => {

    try {
        const response = await fetch(`/api/contents-concept/all`, {
            method: 'GET',
        });
        if (!response.ok) {
            throw new Error('컨텐츠 전체 목록 조회 실패');
        }
        const data = await response.json(); // JSON 형태로 응답 받음
        const result = data.data.map((item:any) => ({
            value : item.id,
            label : item.title
        }));
        console.log(result);
        return result;
    } catch (error) {
        console.error('컨텐츠 전체 목록 조회 실패 : ', error);
        return null;
    }
};

export const getAdvertList = async (userId : number) => {
    try {
        const response = await fetch(`/api/advert/get-list?userId=${userId}`, {
            method: 'GET',
        });
        if (!response.ok) {
            throw new Error('광고 목록 조회 실패');
        }
        const data = await response.json(); // JSON 형태로 응답 받음
        const result = data.data;
        console.log(result);
        return result;
    } catch (error) {
        console.error('광고 목록 조회 실패 : ', error);
        return null;
    }
};

export const getProductTypeList = async (userId : number) => {
    try {
        const response = await fetch(`/api/type/list-by-user-id?userId=${userId}`, {
            method: 'GET',
        });
        if (!response.ok) {
            throw new Error('상품 목록 조회 실패');
        }
        const data = await response.json(); // JSON 형태로 응답 받음
        const result = data.data;
        console.log(result);
        return result;
    } catch (error) {
        console.error('광고 목록 조회 실패 : ', error);
        return null;
    }
};

export const deleteAdvert = async (advertId : number | undefined) => {
    try {
        const response = await fetch(`/api/advert?advertId=${advertId}`, {
            method: 'DELETE',
        });
        if (!response.ok) {
            throw new Error('광고 삭제 실패');
        }
        const data = await response.json(); // JSON 형태로 응답 받음
        const result = data.result;
        console.log(result);
        return result;
    } catch (error) {
        console.error('광고 삭제 실패 : ', error);
        return null;
    }
};

interface adType {
    advertId? : number;
    title? : string;
    description? : string;
    startDate? : string;
    endDate? : string;
    advertType? : string;
    advertCategory? : string;
    directVideoUrl?: string;
    bannerImgUrl?: string;
    selectedContentList? : number[];
    advertVideoUrlList? : string[];
}
export const modifyAdvert = async (body : adType | undefined) => {
    const formData = new FormData();
    try {
        const response = await fetch(`/api/advert`, {
            method: 'PUT',
            body : formData
        });
        if (!response.ok) {
            throw new Error('광고 수정 실패');
        }
        const data = await response.json(); // JSON 형태로 응답 받음
        const result = data.result;
        console.log(result);
        return result;
    } catch (error) {
        console.error('광고 수정 실패 : ', error);
        return null;
    }
};

export const productThumbnailUpload = async (files: FileList | null) => {
    if (!files || files.length === 0) return;

    const formData = new FormData();
    formData.append('thumbnail',files[0]);

    try {
        const response = await fetch(`/api/product-img/add-thumbnail`, {
            method: 'POST',
            body: formData
        });
        if (!response.ok) {
            throw new Error('상품 썸네일 업로드 요청 실패');
        }
        const data = await response.json(); // JSON 형태로 응답 받음
        const result = data;
        console.log(result);
        return result;
    } catch (error) {
        console.error('상품 썸네일 업로드 서버 요청 실패 : ', error);
        return null;
    }
};

export const productDetailImgUpload = async (files: FileList | null) => {
    if (!files || files.length === 0) return;

    const formData = new FormData();
    Array.from(files).forEach(file => {
        formData.append('imgs', file);
    });
    try {
        const response = await fetch(`/api/product-img/add-img-list`, {
            method: 'POST',
            body: formData
        });
        if (!response.ok) {
            throw new Error('상품 상세 이미지 업로드 요청 실패');
        }
        const data = await response.json(); // JSON 형태로 응답 받음
        const result = data;
        console.log(result);
        return result;
    } catch (error) {
        console.error('상품 상세 이미지 업로드 서버 요청 실패 : ', error);
        return null;
    }
};

