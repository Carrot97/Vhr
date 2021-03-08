<template>
  <div>
    <el-form
        v-loading="loading"
        :model="loginForm"
        ref="loginForm"
        :rules="loginRules"
        class="loginContainer">
      <h2>欢迎登陆微人事</h2>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="请输入用户名" @keydown.enter.native="onSubmit"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="loginForm.password" type="text" auto-complete="off" placeholder="请输入密码" @keydown.enter.native="onSubmit"></el-input>
      </el-form-item>
      <el-checkbox v-model="checked" class="loginRememberMe">记住我</el-checkbox>
      <el-button type="primary" @click="onSubmit" style="width: 100%">登陆</el-button>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      loading: false,
      loginForm: {
        username: 'admin',
        password: '123',
      },
      loginRules: {
        username: [{
          required: true,
          message: "请输入用户名",
          trigger: 'blur'
        }],
        password: [{
          required: true,
          message: "请输入密码",
          trigger: 'blur'
        }]
      },
      checked: false,

    }
  },
  methods: {
    onSubmit() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          this.postKetValueRequest('/doLogin', this.loginForm).then(resp=>{
            this.loading = false;
            if (resp) {
              this.$store.commit("init_currentHr", resp.obj)
              window.sessionStorage.setItem("user", JSON.stringify(resp.obj));
              let redirect = this.$route.query.redirect;
              this.$router.replace((redirect == '/' || redirect == undefined)? '/home': redirect)
            }
          })
        } else {
          this.$message.error("请输入所有字段");
          return false;
        }
      });
    }
  }
}
</script>

<style>
  .loginContainer {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    padding: 30px 30px 25px 30px;
  }
  .loginRememberMe {
    text-align: left;
    margin: 10px auto;
  }
</style>
