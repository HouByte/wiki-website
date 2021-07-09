import request from '../util/request'

export const docList =() => request.post('/doc/list')

export const docSearch =(keyword:any) => request.post('/doc/list', {keyword})

export const docSave =(data: any) => request.post('/doc/save',data)

export const docDel =(ids:Array<number>) => request.post('/doc/deletes', {ids});