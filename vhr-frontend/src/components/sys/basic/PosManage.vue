<template>
  <div>
    <div>
      <el-input class="jobLevelAddInput"
          placeholder="添加职位"
          prefix-icon="el-icon-plus"
          v-model="inputPos.name"
          @keydown.enter="handleAdd">
      </el-input>
      <el-button type="primary" @click="handleAdd">添加</el-button>
    </div>
    <div class="posTable">
      <el-table
          :data="positions"
          stripe
          border
          style="width: 70%"
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
            label="名称"
            width="180">
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

      <el-dialog title="修改职位" :visible.sync="editBoxVisible" width="400px">
        <div class="editBox">
          <el-tag size="medium">职位名称</el-tag>
          <el-input size="small" v-model="editPos.name" autocomplete="off" class="editInput"></el-input>
        </div>
        <div class="editBox">
          <el-tag size="medium">状态</el-tag>
          <el-switch class="editInput"
                     v-model="editPos.enabled"
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
  name: "PosManage",
  data() {
    return {
      inputPos: {
        name: ''
      },
      editPos: {
        name: '',
        enabled: ''
      },
      positions: [],
      editBoxVisible: false,
      multipleSelection: []
    }
  },
  mounted() {
    this.loadPositions();
  },
  methods: {
    loadPositions() {
      this.getRequest("/system/basic/position/").then(resp => {
        if (resp) {
          this.positions = resp;
        }
      })
    },
    handleAdd() {
      if (this.inputPos.name) {
        this.postRequest("/system/basic/position/", this.inputPos).then(resp => {
          if (resp) {
            this.inputPos.name = "";
            this.loadPositions();
          }
        })
      } else {
        this.$message.error("职位名称为空")
      }
    },
    openEditBox(index, data) {
      Object.assign(this.editPos, data)
      this.editBoxVisible = true;
    },
    handleEdit() {
      if (this.editPos.name) {
        this.putRequest("/system/basic/position/", this.editPos).then(resp => {
          if (resp) {
            this.editPos.name = {name: ''};
            this.loadPositions();
            this.editBoxVisible = false;
          }
        })
      } else {
        this.$message.error("职位名称为空")
      }
    },
    handleDelete(index, data) {
      this.$confirm('此操作将永久删除【'+data.name+'】职位, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest("/system/basic/position/" + data.id).then(resp => {
          if (resp) {
            this.loadPositions();
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
      this.$confirm('此操作将永久删除【'+ this.multipleSelection.length +'】个职位, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let ids = "?"
        this.multipleSelection.forEach(item => {
          ids += 'ids=' + item.id + '&';
        })
        this.deleteRequest("/system/basic/position/" + ids).then(resp => {
          if (resp) {
            this.loadPositions();
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

<style scoped>
.jobLevelAddInput {
  width: 300px;
  margin-right: 10px;
}

.posTable {
  margin-top: 20px;
}

.batchDeleteButton {
  margin-top: 10px;
}

.editInput {
  width: 250px;
  margin-left: 10px;
}

.editBox {
  margin-bottom: 15px;
}
</style>
