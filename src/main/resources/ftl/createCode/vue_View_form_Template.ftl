<template>
	<div class="execution">
		<basic-container>
			<div class="main-right">
				<avue-form ref="form" v-model="obj" :option="option" @reset-change="emptytChange" @submit="submit" />
			</div>
		</basic-container>
	</div>
</template>

<script>
	${"import { addObj } from '@/api/"}${belongSystem}${"/"}${packageName}${"/"}${objectName}${"_form'"}
	${"import { formOption } from '@/const/"}${belongSystem}${"/"}${packageName}${"/"}${objectName}${"_form'"}
	${"export"} default {
		${"data()"} {
			${"return"} {
				obj: {},
			}
		},
		computed:{
			${"option"}(){
				${"return"}{
					formOption
				}
			}
		},
		${"mounted"}(){

		},
		methods: {
			emptytChange() {
				this.$message.success('清空方法回调')
			},
			submit() {
				addObj(this.obj)
			}
		}
	}

</script>

<style lang="scss" scoped>

</style>
