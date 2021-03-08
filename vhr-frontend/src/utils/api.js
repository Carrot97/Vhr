import axios from "axios";
import { Message } from 'element-ui';
import router from "@/router";

axios.interceptors.response.use(success => {
    if (success.status && success.status == 200 && success.data.status >= 500) {
        Message.error({message: success.data.msg})
        return;
    }
    if (success.data.msg) {
        Message.success({message: success.data.msg})
    }
    return success.data;
},error => {
    if (error.response.status == 504 || error.response.status == 404) {
        Message.error({message: "后端服务器下线"})
    } else if (error.response.status == 403) {
        Message.error({message: "权限不足"})
    } else if (error.response.status == 401) {
        Message.error({message: "未登陆"})
        router.replace("/");
    } else {
        if (error.response.data.msg) {
            Message.error({message: error.data.msg});
        } else {
            Message.error({message: "未知错误"});
        }
    }
    return;
})

let base = '';

export const postKetValueRequest = (url, params) => {
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params,
        transformRequest: [function (data) {
            let res = '';
            for (let i in data) {
                res += encodeURIComponent(i) + '=' + encodeURIComponent(data[i]) + '&';
            }
            return res;
        }],
        header: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
}

export const getRequest = (url, params) => {
    return axios({
        method: 'get',
        url: `${base}${url}`,
        data: params,
    })
}

export const postRequest = (url, params) => {
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params,
    })
}

export const putRequest = (url, params) => {
    return axios({
        method: 'put',
        url: `${base}${url}`,
        data: params,
    })
}

export const deleteRequest = (url, params) => {
    return axios({
        method: 'delete',
        url: `${base}${url}`,
        data: params,
    })
}

