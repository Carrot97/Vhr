<template>
  <div>
    <div>
      <el-input class="jobLevelAddNameInput"
                placeholder="添加职称"
                prefix-icon="el-icon-plus"
                v-model="inputJobLevel.name"
                @keydown.enter="handleAdd">
      </el-input>
      <el-select v-model="inputJobLevel.titleLevel" placeholder="选择职称等级" class="jobLevelAddLevelInput">
        <el-option
            v-for="item in titleLevels"
            :key="item"
            :label="item"
            :value="item">
        </el-option>
      </el-select>
      <el-button type="primary" @click="handleAdd">添加</el-button>

      </div>
      <div class="posTable">
        <el-table
            :data="jobLevels"
            stripe
            border
            style="width: 75%"
            @selection-change="handleSelectionChange">
          <el-table-column
              type="selection"
              width="55">
          </el-table-column>
          <el-table-column
              prop="id"
              label="编号"
              width="70">
          </el-table-column>
          <el-table-column
              prop="name"
              label="职称"
              width="180">
          </el-table-column>
          <el-table-column
              prop="titleLevel"
              label="等级"
              width="100">
          </el-table-column>
          <el-table-column
              prop="createDate"
              label="创建时间"
              width="200">
          </el-table-column>
          <el-table-column
              label="状态"
              width="100">
            <template slot-scope="scope">
              <el-tag size="small" type="success" v-if="scope.row.enabled">已开启</el-tag>
              <el-tag size="small" type="danger" v-else>未开启</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                  size="mini"
                  @click="openEditBox(scope.$index, scope.row)">编辑</el-button>
              <el-button
                  size="mini"
                  type="danger"
                  @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-button type="danger" class="batchDeleteButton" :disabled="multipleSelection.length == 0"
                   @click="handleBatchDelete">批量删除</el-button>

        <el-dialog title="修改职称" :visible.sync="editBoxVisible" width="400px">
          <div class="editBox">
            <el-tag size="medium">职称名称</el-tag>
            <el-input size="small" v-model="editJobLevel.name" autocomplete="off" class="editInput"></el-input>
          </div>
          <div class="editBox">
            <el-tag size="medium">职称等级</el-tag>
            <el-select size="small" v-model="editJobLevel.titleLevel" class="editInput">
              <el-option
                  v-for="item in titleLevels"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>
          </div>
          <div class="editBox">
            <el-tag size="medium">状态</el-tag>
            <el-switch class="editInput"
                v-model="editJobLevel.enabled"
                active-text="开启"
                inactive-text="关闭">
            </el-switch>
          </div>
          <div slot="footer" class="dialog-footer">
            <el-button size="small" @click="editBoxVisible = false">取 消</el-button>
            <el-button size="small" type="primary" @click="handleEdit">确 定</el-button>
          </div>
        </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "JobLevelManage",
  data() {
    return {
      inputJobLevel: {
        name: '',
        titleLevel: '',
      },
      editJobLevel: {
        name: '',
        titleLevel: '',
        enabled: ''
      },
      titleLevels: ["正高级", "副高级", "中级", "初级", "员级"],
      jobLevels: [],
      editBoxVisible: false,
      multipleSelection: []
    }
  },
  mounted() {
    this.loadJobLevels();
  },
  methods: {
    loadJobLevels() {
      this.getRequest("/system/basic/joblevel/").then(resp => {
        if (resp) {
          this.jobLevels = resp;
        }
      })
    },
    handleAdd() {
      if (this.inputJobLevel.name && this.inputJobLevel.titleLevel) {
        this.postRequest("/system/basic/joblevel/", this.inputJobLevel).then(resp => {
          if (resp) {
            this.inputJobLevel.name = "";
            this.inputJobLevel.titleLevel = "";
            this.loadJobLevels();
          }
        })
      } else {
        this.$message.error("职称名称或等级为空")
      }
    },
    openEditBox(index, data) {
      Object.assign(this.editJobLevel, data)
      this.editBoxVisible = true;
    },
    handleEdit() {
      if (this.editJobLevel.name && this.editJobLevel.titleLevel) {
        this.putRequest("/system/basic/joblevel/", this.editJobLevel).then(resp => {
          if (resp) {
            this.editJobLevel = {name: '', titleLevel: ''};
            this.loadJobLevels();
            this.editBoxVisible = false;
          }
        })
      } else {
        this.$message.error("职称名称或等级为空")
      }
    },
    handleDelete(index, data) {
      this.$confirm('此操作将永久删除【'+data.name+'】职称, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest("/system/basic/joblevel/" + data.id).then(resp => {
          if (resp) {
            this.loadJobLevels();
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleBatchDelete() {
      this.$confirm('此操作将永久删除【' + this.multipleSelection.length + '】个职称, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let ids = "?"
        this.multipleSelection.forEach(item => {
          ids += 'ids=' + item.id + '&';
        })
        this.deleteRequest("/system/basic/joblevel/" + ids).then(resp => {
          if (resp) {
            this.loadJobLevels();
          }
        });
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
.jobLevelAddNameInput {
  width: 300px;
  margin-right: 10px;
}

.jobLevelAddLevelInput {
  width: 140px;
  margin-right: 10px;
}

.posTable {
  margin-top: 20px;
}

.editInput {
  width: 250px;
  margin-left: 10px;
}

.editBox {
  margin-bottom: 15px;
}

.batchDeleteButton {
  margin-top: 10px;
}
</style>
