import request from '../util/request'

export const userSearch =(keyword:any) => request.post('/user/list', {keyword})

export const userSave =(data: any) => request.post('/user/save',data)

export const userDel =(id:number) => request.delete('/user/delete/'+id);