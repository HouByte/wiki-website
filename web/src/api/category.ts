import request from '../util/request'

export const categoryList =() => request.post('/category/list')

export const categorySearch =(keyword:any) => request.post('/category/list', {keyword})

export const categorySave =(data: any) => request.post('/category/save',data)

export const categoryDel =(id:number) => request.delete('/category/delete/'+id);