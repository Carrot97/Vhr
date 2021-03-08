<template>
  <div>
    <div class="searchBox">
      <el-input
          placeholder="输入关键字进行过滤"
          prefix-icon="el-icon-search"
          v-model="filterText">
      </el-input>
    </div>
    <div class="deptTree">
      <el-tree
          class="filter-tree"
          :data="depts"
          :props="defaultProps"
          :expand-on-click-node="false"
          :filter-node-method="filterNode"
          ref="tree">
        <span class="custom-tree-node" style="width: 100%; padding: 10px" slot-scope="{ node, data }">
          <span>{{ node.label }}</span>
          <span>
            <el-button class="deptBtn"
                type="primary"
                size="mini"
                @click="() => openAddBox(data)">
              添加部门
            </el-button>
            <el-button class="deptBtn"
                type="danger"
                size="mini"
                @click="() => deleteDept(data)">
              删除部门
            </el-button>
          </span>
        </span>
      </el-tree>

      <el-dialog title="添加部门" :visible.sync="addBoxVisible" width="400px">
        <div class="addBox">
          <el-tag size="medium">上级部门</el-tag>
          <span class="addInput">{{ parentName }}</span>
        </div>
        <div class="addBox">
          <el-tag size="medium">新部门名</el-tag>
          <el-input size="small" class="addInput" v-model="deptInput.name"/>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button size="small" @click="addBoxVisible = false">取 消</el-button>
          <el-button size="small" type="primary" @click="doAdd">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "DepManage",
  data() {
    return {
      addBoxVisible: false,
      deptInput: {
        name: '',
        parentId: ''
      },
      parentName: '',
      filterText: '',
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      depts: [],
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  mounted() {
    this.loadDeptTree();
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    loadDeptTree() {
      this.getRequest("/system/basic/department/").then(resp => {
        if (resp) {
          this.depts = resp;
        }
      })
    },
    openAddBox(data) {
      this.parentName = data.name;
      this.deptInput.parentId = data.id;
      this.addBoxVisible = true;
    },
    doAdd() {
      if (this.deptInput.name) {
        this.postRequest("/system/basic/department/", this.deptInput).then(resp => {
          if (resp) {
            this.parentName = '';
            this.deptInput.parentId = '';
            this.deptInput.name = '';
            this.addBoxVisible = false;
            this.activeAppendToTree(this.depts, resp.obj);
          }
        })
      } else {
        this.$message.error("部门名称为空")
      }
    },
    activeAppendToTree(tree, node) {
      for (let i = 0; i < tree.length; i++) {
        let dept = tree[i]
        if (dept.id == node.parentId) {
          dept.children = dept.children.concat(node);
          dept.isParent = true;
          return
        } else {
          this.activeAppendToTree(dept.children, node)
        }
      }
    },
    deleteDept(data) {
      if (data.isParent) {
        this.$message.error("该部门包含子部门，无法删除");
      } else {
        this.$confirm('此操作将永久删除【'+data.name+'】部门, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest("/system/basic/department/" + data.id).then(resp => {
            if (resp) {
              this.activeDeleteFromTree(null, this.depts, data.id);
            }
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }
    },
    activeDeleteFromTree(parent, tree, id) {
      for (let i = 0; i < tree.length; i++) {
        let dept = tree[i]
        if (dept.id == id) {
          tree.splice(i, 1)
          if (tree.length == 0) {
            parent.isParent = false;
          }
          return
        } else {
          this.activeDeleteFromTree(dept, dept.children, id)
        }
      }
    }
  },
}
</script>

<style>
.searchBox {
  width: 500px;
  margin-bottom: 20px;
}

.deptTree {
  width: 500px;
}

.deptBtn {
  padding: 3px;
  float: right;
  margin-left: 5px;
}

.addInput {
  width: 250px;
  margin-left: 10px;
}

.addBox {
  margin-bottom: 15px;
}
</style>
