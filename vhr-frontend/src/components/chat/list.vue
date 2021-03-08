<template>
  <div id="list">
    <ul style="padding-left: 0">
      <li v-for="item in hrs" :class="{ active: currentSession?item.name === currentSession.name:false }"
          v-on:click="changeCurrentSession(item)" :key="item.id"><!--   :class="[item.id === currentSession ? 'active':'']" -->
        <el-badge :is-dot="isDot[currentHr.username + '#' + item.username]"><img class="avatar" :src="item.userface" :alt="item.name"></el-badge>
        <p class="name">{{item.name}}</p>
      </li>
    </ul>
  </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
  name: 'list',
  data () {
    return {

    }
  },
  computed: mapState([
      'isDot',
      'hrs',
      'currentSession',
      'currentHr'
	]),
  methods:{
    changeCurrentSession (currentSession) {
      this.$store.commit('changeCurrentSession', currentSession)
    }
  }
}
</script>

<style lang="scss" scoped>
#list {
	li {
		padding: 0 15px;
		border-bottom: 1px solid #292C33;
		cursor: pointer;
    list-style: none;
		&:hover {
			background-color: rgba(255, 255, 255, 0.03);
		}
	}
  li.active {/*注意这个是.不是冒号:*/
			background-color: rgba(255, 255, 255, 0.1);
	}
	.avatar {
		border-radius: 2px;
		width: 30px;
		height: 30px;
		vertical-align: middle;
	}
	.name {
		display: inline-block;
		margin-left: 15px;
	}
}
</style>
