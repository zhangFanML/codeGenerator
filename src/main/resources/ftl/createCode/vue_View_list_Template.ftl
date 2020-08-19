<template>
	<div class="execution">
		<basic-container>
			<avue-crud
					ref="crud"
					:page="page"
					:data="tableData"
					:table-loading="tableLoading"
					:option="tableOption"
					@on-load="getList"
					@refresh-change="refreshChange"
					@row-update="handleUpdate"
					@row-save="handleSave"
					@row-del="rowDel"
					@row-click="handleRowClick"
					@search-change="searchChange"
			/>
		</basic-container>
	</div>
</template>

<script>
	${"import { queryList, addObj, putObj, delObj } from '@/api/"}${belongSystem}${"/"}${packageName}${"/"}${objectName}${"_form'"}
	${"import { tableOption } from '@/const/"}${belongSystem}${"/"}${packageName}${"/"}${objectName}${"_list'"}
	// import { mapGetters } from 'vuex'
	${"export default"} {
		name: 'Join',
		${"data()"}{
			return {
				tableData: [],
				page: {
					total: 0, // 总页数
					currentPage: 1, // 当前页数
					pageSize: 20 // 每页显示多少条
				},
				tableLoading: false,
				tableOption: tableOption
			}
		},
		computed: {
			// ...mapGetters(['permissions'])
		},
		created() {
		},
		mounted: function() {
		},
		methods: {
			getList(page, params) {
				this.tableLoading = true
				queryList(Object.assign({
					pageNum: page.currentPage,
					size: page.pageSize
				}, params)).then(response => {
					this.tableData = response.list
					this.page.total = response.page.totalResult
					this.tableLoading = false
				})
			},
			/**
			 * @title 打开新增窗口
			 * @detail 调用crud的handleadd方法即可
			 *
			 **/
			handleAdd: function() {
				this.$refs.crud.rowAdd()
			},
			handleEdit(row, index) {
				this.$refs.crud.rowEdit(row, index)
			},
			handleDel(row, index) {
				this.$refs.crud.rowDel(row, index)
			},
			rowDel: function(row, index) {
				var _this = this
				this.$confirm('是否确认删除此行数据', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function() {
					return delObj(row.id)
				}).then(data => {
					_this.tableData.splice(index, 1)
					_this.$message({
						showClose: true,
						message: '删除成功',
						type: 'success'
					})
					this.getList(this.page)
				})
			},
			/**
			 * @title 数据更新
			 * @param row 为当前的数据
			 * @param index 为当前更新数据的行数
			 * @param done 为表单关闭函数
			 *
			 **/
			handleUpdate: function(row, index, done) {
				putObj(row).then(data => {
					this.tableData.splice(index, 1, Object.assign({}, row))
					this.$message({
						showClose: true,
						message: '修改成功',
						type: 'success'
					})
					${"done()"}
					this.getList(this.page)
				})
			},
			/**
			 * @title 数据添加
			 * @param row 为当前的数据
			 * @param done 为表单关闭函数
			 *
			 **/
			handleSave: function(row, done) {
				addObj(row).then(data => {
					this.tableData.push(Object.assign({}, row))
					this.$message({
						showClose: true,
						message: '添加成功',
						type: 'success'
					})
					${"done()"}
					this.getList(this.page)
				})
			},
			/**
			 * 刷新回调
			 */
			refreshChange() {
				this.getList(this.page)
			},
			handleRowClick(row, event, column) {
				this.$notify({
					showClose: true,
					message: '单机' + JSON.stringify(row),
					type: 'success'
				})
			},
			/**
			 * 搜索过滤*/
			searchChange(params) {
				this.getList(this.page, params)
			}
		}
	}
</script>

<style lang="scss" scoped>
</style>
