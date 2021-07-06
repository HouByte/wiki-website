import request from '../util/request'

export const ebookList =() => request.post('/ebook/list')

export const ebookSearch =(keyword:any) => request.post('/ebook/list', {keyword})

export const ebookSave =(data: any) => request.post('/ebook/save',data)

export const ebookDel =(id:number) => request.delete('/ebook/delete/'+id);