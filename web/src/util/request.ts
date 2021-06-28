import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from "axios"
//创建axsio 赋给常量service
const service:AxiosInstance = axios.create({
    baseURL:process.env.VUE_APP_SERVER,
    timeout:100000
});
// 添加请求拦截器
service.interceptors.request.use(function(config:AxiosRequestConfig):AxiosRequestConfig {//config是请求时的配置信息。
    // 在发送请求之前做些什么
    // 设置请求头 携带token
    const token:string | null = localStorage.getItem('token')
    if(token){
        config.headers = config.headers || {}
        config.headers['XXXX'] = token
    }
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});
// 添加响应拦截器
service.interceptors.response.use(function (response:AxiosResponse):AxiosResponse {//response参数是响应对象
    // 对响应数据做点什么
    return response;
}, function (error) {
    // 对响应错误做点什么
    return Promise.reject(error);
});
export default service