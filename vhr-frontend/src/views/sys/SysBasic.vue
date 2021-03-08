<template>
  <div>
    <template>
      <el-tabs v-model="activeName" class="tabParent">
        <el-tab-pane label="部门管理" name="depmanage"><DepManage></DepManage></el-tab-pane>
        <el-tab-pane label="职位管理" name="posmanage"><PosManage></PosManage></el-tab-pane>
        <el-tab-pane label="职称管理" name="joblevelmanage"><JobLevelManage></JobLevelManage></el-tab-pane>
        <el-tab-pane label="奖惩规则" name="ecmanage"><EcManage></EcManage></el-tab-pane>
        <el-tab-pane label="权限组" name="permissmanage"><PermissManage></PermissManage></el-tab-pane>
      </el-tabs>
    </template>
  </div>
</template>

<script>
import DepManage from "@/components/sys/basic/DepManager";
import PosManage from "@/components/sys/basic/PosManage";
import EcManage from "@/components/sys/basic/EcManage";
import JobLevelManage from "@/components/sys/basic/JobLevelManage";
import PermissManage from "@/components/sys/basic/PermissManage";

export default {
  name: "SysBasic",
  data() {
    return {
      activeName: 'depmanage'
    };
  },
  components: {
    DepManage,
    PosManage,
    EcManage,
    JobLevelManage,
    PermissManage,
  },
  methods: {
  }
}
</script>

<style scoped>
/deep/.el-tabs__item {
  font-size: 18px !important;
}

</style>
