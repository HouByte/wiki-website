import request from '../util/request'

export const userSearch =(keyword:any) => request.post('/user/list', {keyword})

export const userSave =(data: any) => request.post('/user/save',data)

export const userResetPassword =(data: any) => request.post('/user/reset-password',data)

export const userDel =(id:number) => request.delete('/user/delete/'+id);

export const userLogin =(data: any) => request.post('/user/login',data)