<template>
  <div>
    <el-container>

      <!--上方导航栏-->
      <el-header class="homeHeader">
        <div class="title">微人事</div>
        <div>
          <el-button @click="goChat" style="margin-right: 10px;color: #000000; height: 30px; width: 30px" type="text" icon="el-icon-bell"></el-button>
          <el-dropdown @command="commandHandler">
            <span class="el-dropdown-link">
              {{ user.name }}<img :src="user.userface">
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="userCenter">个人中心</el-dropdown-item>
              <el-dropdown-item command="setting">用户设置</el-dropdown-item>
              <el-dropdown-item command="logout" divided>注销登陆</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>
      <!--/上方导航栏-->

      <el-container>
        <!--侧边栏-->
        <el-aside width="200px">
          <el-row class="tac">
            <el-menu router unique-opened>
              <el-submenu :index="index+''" v-for="(item, index) in routes" :key="index">
                <template slot="title">
                  <i style="color: #3f9dfd;margin-right: 5px;" :class="item.iconcls"></i>
                  <span>{{ item.name }}</span>
                </template>
                <el-menu-item :index="child.path" v-for="(child, jndex) in item.children" :key="jndex">
                  {{ child.name }}
                </el-menu-item>
              </el-submenu>
            </el-menu>
          </el-row>
        </el-aside>
        <!--/侧边栏-->

        <!--主文本区-->
        <el-main>
          <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path != '/home'">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ this.$router.currentRoute.name }}</el-breadcrumb-item>
          </el-breadcrumb>
          <div class="welcome" v-if="this.$router.currentRoute.path == '/home'">
            欢迎来到微人事
          </div>
          <router-view class="homeRouterView"/>
        </el-main>
        <!--/主文本区-->
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      user: JSON.parse(window.sessionStorage.getItem("user"))
    }
  },
  methods: {
    commandHandler(cmd) {
      if (cmd == 'userCenter') {
        console.log("userCenter")
      } else if (cmd == 'setting') {
        console.log("setting")
      } else if (cmd == 'logout') {
        this.$confirm('确认注销?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.getRequest("/logout");
          window.sessionStorage.removeItem("user")
          this.$store.commit('initRoutes', [])
          this.$router.replace("/")
        }).catch(() => {
        });
      }
    },
    goChat() {
      this.$router.push("/chat")
    }
  },
  computed: {
    activeRoutes() {
      return this.$router.options.routes.filter(function (route) {
        return !route.hidden
      })
    },
    routes() {
      return this.$store.state.routes;
    }
  }
}
</script>

<style>
.homeHeader {
  background: #4ec1f5;
  display: flex;
  align-items: center;
  box-sizing: border-box;
  /*两端对齐*/
  justify-content: space-between;
  padding: 0 15px;
}

.homeHeader .title {
  color: #ffffff;
  font-size: 30px;
  font-family: 黑体;
}

.el-dropdown-link {
  cursor: pointer;
  color: #ffffff;
  display: flex;
  align-items: center;
}

.el-dropdown-link img {
  width: 40px;
  height: 40px;
  border-radius: 5px;
  margin-left: 10px;
}

.welcome {
  text-align: center;
  padding-top: 50px;
  font-family: 黑体;
  font-size: 40px;
  color: #3f9dfd;
}

.homeRouterView {
  margin-top: 15px;
}
</style>
