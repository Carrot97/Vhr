<template>
  <div>
    <div class="roleAddBox">
      <el-input size="medium" placeholder="请输入英文名" v-model="role.name">
        <template slot="prepend">ROLE_</template>
      </el-input>
      <el-input size="medium" placeholder="请输入中文名" v-model="role.nameZh" clearable @keydown.enter.native="doAdd">
      </el-input>
      <el-button size="medium" type="primary" @click="doAdd">添加</el-button>
    </div>

    <el-collapse accordion v-model="activeName" class="roleCollapse" @change="collapseChange">
      <el-collapse-item :title="role.nameZh" :name="role.id" v-for="(role, index) in roles" :key="index">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>可访问资源</span>
            <el-button style="float: right; padding: 3px 0; color: #fd0000"
                       class="el-icon-remove"
                       @click="doDelete(role.id)"></el-button>
          </div>
          <div>
            <el-tree
                node-key="id"
                :props="props"
                :data="menuTree"
                show-checkbox
                ref="tree"
                :key="index"
                :default-checked-keys="selectedMenus"
            ></el-tree>
            <div style="display: flex; justify-content: flex-end">
              <el-button size="small" @click="cancelUpdate">取消修改</el-button>
              <el-button size="small" type="primary" @click="doUpdate(role.id, index)">确认修改</el-button>
            </div>
          </div>
        </el-card>
      </el-collapse-item>
    </el-collapse>

  </div>
</template>

<script>
export default {
  name: "PermissManage",
  data() {
    return {
      role: {
        name: '',
        nameZh: ''
      },
      activeName: -1,
      roles: [],
      menuTree: [],
      selectedMenus: [],
      props: {
        label: 'name',
        children: 'children'
      },
    }
  },
  mounted() {
    this.loadRoles();
  },
  methods: {
    loadRoles() {
      this.getRequest("/system/basic/permiss/").then(resp => {
        if (resp) {
          this.roles = resp;
        }
      })
    },
    loadMenuTree() {
      this.getRequest("/system/basic/permiss/menus").then(resp => {
        if (resp) {
          this.menuTree = resp;
        }
      })
    },
    loadSelectedMenus(rid) {
      this.getRequest("/system/basic/permiss/mids/" + rid).then(resp => {
        if (resp) {
          this.selectedMenus = resp;
        }
      })
    },
    collapseChange(name) {
      if (name) {
        this.loadMenuTree();
        this.loadSelectedMenus(name);
      }
    },
    doAdd() {
      if (this.role.name && this.role.nameZh) {
        this.postRequest("/system/basic/permiss/", this.role).then(resp => {
          if (resp) {
            this.role.name = '';
            this.role.nameZh = '';
            this.loadRoles();
          }
        })
      } else {
        this.$message.error("名称未输入")
      }
    },
    doUpdate(rid, index) {
      let tree = this.$refs.tree[index];
      // true值绑定选中的叶子节点
      let CheckedKeys = tree.getCheckedKeys(true);
      let url = "/system/basic/permiss/?rid=" + rid;
      CheckedKeys.forEach(key => {
        url += '&mids=' + key;
      })
      this.putRequest(url).then(resp => {
        if (resp) {
          this.loadSelectedMenus(rid);
        }
      })
    },
    cancelUpdate() {
      this.activeName = -1;
    },
    doDelete(id) {
      this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest("/system/basic/permiss/" + id).then(resp => {
          if (resp) {
            this.loadRoles();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    }
  }
}
</script>

<style>
.roleAddBox {
  margin-bottom: 15px;
}

.roleAddBox .el-input {
  width: 250px;
  margin-right: 10px;
}

.roleCollapse {
  width: 630px;
}
</style>
