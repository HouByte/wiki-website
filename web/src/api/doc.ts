import request from '../util/request'

export const docList =() => request.post('/doc/list')

export const docSearch =(id:any,keyword:any) => request.post('/doc/list/'+id, {keyword})

export const docQueryContent =(id: number) => request.post('/doc/query-content/'+id)

export const docSave =(data: any) => request.post('/doc/save',data)

export const docDel =(ids:Array<number>) => request.post('/doc/deletes', {ids});

export const docAddVote =(id: number) => request.post('/doc/vote/'+id)