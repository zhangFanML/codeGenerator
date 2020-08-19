import request from '@/utils/request'
const prefix = '/api/v1/${belongSystem}'
export function addObj(obj) {
	return request({
		url: prefix + '/${objectNameLower}',
		method: 'post',
		data: obj
	})
}

export function putObj(obj) {
	return request({
		url: prefix + '/${objectNameLower}',
		method: 'put',
		data: obj
	})
}

export function patchObj(obj) {
	return request({
		url: prefix + '/${objectNameLower}',
		method: 'patch',
		data: obj
	})
}

export function queryList(obj) {
	return request({
		url: prefix + '/${objectNameLower}',
		method: 'get',
		params: obj
	})
}

export function getObj(id) {
	return request({
		url: prefix + '/${objectNameLower}/' + id,
		method: 'get'
	})
}

export function delObj(obj) {
	return request({
		url: '/${objectNameLower}/',
		method: 'delete',
		params: obj
	})
}