<template>
  <div>
    <div class="search-box">
      <el-input
          class="search-input"
          placeholder="输入关键字进行搜索"
          prefix-icon="el-icon-search"
          v-model="keyword"
          @keydown.enter="loadHrs">
      </el-input>
      <el-button type="primary" @click="loadHrs">搜索</el-button>
    </div>

    <div class="cards">
      <el-card class="box-card" v-for="(hr, idx) in hrs" :key="idx">
        <div slot="header" class="clearfix">
          <span style="font-size: 18px; font-weight: bold">{{ hr.name }}</span>
          <el-button
              @click="doDelete(hr.id)"
              icon="el-icon-remove"
              style="float: right; padding: 3px 0; color: red"
              type="text"/>
        </div>
        <div class="userinfo-container">
          <div>用户名: {{hr.name}}</div>
          <div>手机号码: {{hr.phone}}</div>
          <div>电话号码: {{hr.telephone}}</div>
          <div>地址: {{hr.address}}</div>
          <div style="margin-bottom: 3px">用户状态:
            <el-switch
                style="display: inline"
                v-model="hr.enabled"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="启用"
                inactive-text="禁用"
                @change="activeChange(hr)">
            </el-switch>
          </div>
          <div>用户角色:
            <el-tag size="small" type="success" style="margin: 0 5px 5px 0" v-for="(role, idx2) in hr.roles" :key="idx2">
              {{ role.nameZh }}</el-tag>
            <el-popover
                placement="right"
                title="职位选择"
                @show="showSelect(hr)"
                @hide="closeSelect(hr)"
                width="200"
                trigger="click">
              <el-select v-model="selectedRoles" multiple placeholder="请选择">
                <el-option
                    v-for="(r, idx3) in allRoles"
                    :key="idx3"
                    :label="r.nameZh"
                    :value="r.id">
                </el-option>
              </el-select>
              <el-button slot="reference" icon="el-icon-edit" style="color: #13ce66; margin-left: 3px" type="text"/>
            </el-popover>
          </div>
        </div>
        </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "SysHr",
  data() {
    return {
      keyword: '',
      hrs: [],
      allRoles: [],
      selectedRoles: []
    }
  },
  mounted() {
    this.loadHrs();
  },
  methods: {
    loadHrs() {
      this.getRequest("/system/hr/?keyword=" + this.keyword).then(resp => {
        if (resp) {
          this.hrs = resp;
        }
      })
    },
    doDelete(hrid) {
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest("/system/hr/" + hrid).then(resp => {
          if (resp) {
            this.loadHrs();
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    activeChange(hr) {
      delete hr.roles;
      this.putRequest("/system/hr/", hr).then(resp => {
        if (resp) {
          this.loadHrs();
        }
        return false;
      })
    },
    showSelect(hr) {
      this.selectedRoles = [];
      hr.roles.forEach(role => {
        this.selectedRoles.push(role.id);
      });
      this.loadRoles();
    },
    loadRoles() {
      this.getRequest("/system/hr/role").then(resp => {
        if (resp) {
          this.allRoles = resp;
        }
      })
    },
    closeSelect(hr) {
      let flag = false;
      if (hr.roles.length != this.selectedRoles.length) {
        flag = true;
      } else {
        for (let i = 0; i < hr.roles.length ; i++) {
          if (!hr.roles[i].id in this.selectedRoles) {
            flag = true;
            break;
          }
        }
      }
      if (flag) {
        this.updateRoles(hr);
      }
    },
    updateRoles(hr) {
      let url = "/system/hr/role?hrid=" + hr.id;
      this.selectedRoles.forEach(role => {
        url += '&rids=' + role
      })
      this.putRequest(url).then(resp => {
        if (resp) {
          this.loadHrs()
        }
      })
    }
  }
}
</script>

<style>
  .search-box {
    margin-top: 20px;
    margin-bottom: 25px;
    display: flex;
    justify-content: center;
  }

  .search-input {
    width: 400px;
    margin-right: 10px;
  }

  .cards {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
  }

  .box-card {
    width: 400px;
    margin-bottom: 15px;
  }

  .userinfo-container {
    color: #4ec1f5;
    font-size: 14px;
    line-height: 22px;
  }
</style>
