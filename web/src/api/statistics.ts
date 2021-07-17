import request from '../util/request'

export const getStatistics =() => request.post('/statistics/get-statistics')
export const get30Statistics =() => request.post('/statistics/get-30-statistics')