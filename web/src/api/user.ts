import request from '../util/request'
import {AxiosRequestConfig} from "axios";

export const userSearch =(keyword:any) => request.post('/user/list', {keyword})

export const userSave =(data: any) => request.post('/user/save',data)

export const userResetPassword =(data: any) => request.post('/user/reset-password',data)

export const userDel =(id:number) => request.delete('/user/delete/'+id);

export const userLogin =(data: any) => request.post('/user/login',data)

export const userLogout =(token: any) => request.post('/user/logout/'+token)

export const userUpdateEnable =(id:number,enable: boolean) => request.post('/user/update/enable/'+id, {enable})