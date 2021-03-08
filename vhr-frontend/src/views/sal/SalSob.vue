<template>
  <div>
    <div class="head-bar">
      <el-button size="small" type="primary" icon="el-icon-plus" @click="openAddBox">添加工资账套</el-button>
      <el-button size="small" type="success" icon="el-icon-refresh" @click="loadSalaries"></el-button>
    </div>

    <el-dialog
        title="添加工资账套"
        :visible.sync="dialogVisible"
        width="40%"
        @close="cleanAddSalary">

      <div style="display: flex; justify-content: space-around">
        <div style="height: 300px;">
          <el-steps direction="vertical" :active="activeIdx">
            <el-step :title="item" v-for="(item, idx) in salaryItems" :key="idx"></el-step>
          </el-steps>
        </div>

        <div style="height: 300px; display: flex; align-items: center">
          <el-input v-model="salary[key]" v-show="activeIdx == idx" style="width: 200px;"
                    :placeholder="'请输入' + salaryItems[idx] + '...'"
                    v-for="(val, key, idx) in salary" :key="idx"></el-input>
        </div>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="itemUp">{{ activeIdx == 0 ? "取消" : "上一步" }}</el-button>
        <el-button size="small" type="primary" @click="itemDown">{{ activeIdx == 10 ? "完成" : "下一步" }}</el-button>
      </span>
    </el-dialog>

    <div>
      <el-table :data="allSalaries" border v-loading="loading">
        <el-table-column type="selection" width="45">
        </el-table-column>
        <el-table-column prop="name" label="账套名称" width="150">
        </el-table-column>
        <el-table-column prop="basicSalary" label="基本工资" width="100">
        </el-table-column>
        <el-table-column prop="trafficSalary" label="交通补贴" width="90">
        </el-table-column>
        <el-table-column prop="lunchSalary" label="午餐补助" width="90">
        </el-table-column>
        <el-table-column prop="bonus" label="奖金" width="90">
        </el-table-column>
        <el-table-column prop="createDate" label="启用时间" width="150">
        </el-table-column>
        <el-table-column label="养老金" align="center">
          <el-table-column prop="pensionPer" label="比率" width="70"></el-table-column>
          <el-table-column prop="pensionBase" label="基数" width="90"></el-table-column>
        </el-table-column>
        <el-table-column label="医疗保险" align="center">
          <el-table-column prop="medicalPer" label="比率" width="70"></el-table-column>
          <el-table-column prop="medicalBase" label="基数" width="90"></el-table-column>
        </el-table-column>
        <el-table-column label="公积金" align="center">
          <el-table-column prop="accumulationFundPer" label="比率" width="70"></el-table-column>
          <el-table-column prop="accumulationFundBase" label="基数" width="90"></el-table-column>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="openUpdateBox(scope.row)">修改</el-button>
            <el-button size="mini" type="danger" @click="deleteSalary(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="tail-bar">
      <el-button size="small" type="danger" icon="el-icon-delete" round>批量删除</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "SalSob",
  data() {
    return {
      loading: false,
      allSalaries: [],
      dialogVisible: false,
      dialogTitle: '',
      salaryItems: [
        '账套名称',
        '基本工资',
        '交通补贴',
        '午餐补助',
        '奖金',
        '养老金比率',
        '养老金基数',
        '医疗保险比率',
        '医疗保险基数',
        '公积金比率',
        '公积金基数'
      ],
      salary: {
        name: '',
        basicSalary: 0,
        trafficSalary: 0,
        lunchSalary: 0,
        bonus: 0,
        pensionPer: 0,
        pensionBase: 0,
        medicalPer: 0,
        medicalBase: 0,
        accumulationFundPer: 0,
        accumulationFundBase: 0,
      },
      activeIdx: 0,
      updateId: null,
    }
  },
  mounted() {
    this.loadSalaries();
  },
  methods: {
    loadSalaries() {
      this.loading = true;
      this.getRequest("/salary/sob/").then(resp => {
        if (resp) {
          this.allSalaries = resp;
        }
        this.loading = false;
      })
    },
    openAddBox() {
      this.dialogTitle = '添加工资账套';
      this.dialogVisible = true;
    },
    itemUp() {
      if (this.activeIdx == 0) {
        this.dialogVisible = false;
      } else {
        this.activeIdx--;
      }
    },
    itemDown() {
      if (this.activeIdx == 10) {
        if (this.updateId == null) {
          this.postRequest("/salary/sob/", this.salary).then(resp => {
            if (resp) {
              this.dialogVisible = false;
              this.loadSalaries();
            }
          })
        } else {
          this.putRequest("/salary/sob/" + this.updateId, this.salary).then(resp => {
            if (resp) {
              this.dialogVisible = false;
              this.loadSalaries();
            }
          })
        }
      } else {
        this.activeIdx++;
      }
    },
    cleanAddSalary() {
      this.salary = {
        name: '',
        basicSalary: 0,
        trafficSalary: 0,
        lunchSalary: 0,
        bonus: 0,
        pensionPer: 0,
        pensionBase: 0,
        medicalPer: 0,
        medicalBase: 0,
        accumulationFundPer: 0,
        accumulationFundBase: 0,
      };
      this.activeIdx = 0;
      this.updateId = null;
    },
    deleteSalary(salary) {
      this.$confirm('此操作将永久删除该【' + salary.name + '】, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest("/salary/sob/" + salary.id).then(resp => {
          if (resp) {
            this.loadSalaries();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    openUpdateBox(salary) {
      this.dialogTitle = '修改工资账套';
      this.updateId = salary.id;
      this.salary.name = salary.name;
      this.salary.basicSalary = salary.basicSalary;
      this.salary.trafficSalary = salary.trafficSalary;
      this.salary.lunchSalary = salary.lunchSalary;
      this.salary.bonus = salary.bonus;
      this.salary.pensionPer = salary.pensionPer;
      this.salary.pensionBase = salary.pensionBase;
      this.salary.medicalPer = salary.medicalPer;
      this.salary.medicalBase = salary.medicalBase;
      this.salary.accumulationFundPer = salary.accumulationFundPer;
      this.salary.accumulationFundBase = salary.accumulationFundBase;
      this.dialogVisible = true;
    }
  }
}
</script>

<style>
.head-bar {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
}

.tail-bar {
  margin-top: 10px;
}
</style>
