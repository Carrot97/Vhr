<template>
  <div id="app">
    <div class="sidebar">
      <card></card>
      <list></list>
    </div>
    <div class="main">
      <message></message>
      <usertext></usertext>
    </div>
  </div>
</template>

<script>
import card from "@/components/chat/card";
import list from "@/components/chat/list";
import message from "@/components/chat/message";
import usertext from "@/components/chat/usertext";

export default {
  name: 'FriendChat',
  data () {
    return {

    }
  },
  mounted:function() {
    this.$store.dispatch('initData');
    this.$store.dispatch('connect')
  },
  components:{
    card,
    list,
    message,
    usertext
  }
}
</script>

<style lang="scss" scoped>
#app {
  margin: 100px auto;
  width: 800px;
  height: 600px;
  overflow: hidden;
  border-radius: 10px;
  box-shadow: 0 0 5px 5px #ddd;
  .sidebar, .main {
    height: 100%;
  }
  .sidebar {
    float: left;
    color: #f4f4f4;
    background-color: #2e3238;
    width: 200px;
  }
  .main {
    position: relative;
    overflow: hidden;
    background-color: #eee;
  }
}
</style>
