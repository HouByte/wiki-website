import request from '../util/request'

export const ebookList =() => request.post('/ebook/list')

export const ebookSave =(data: any) => request.post('/ebook/save',data)