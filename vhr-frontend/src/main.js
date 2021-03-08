import Vue from 'vue'
import App from './App.vue'
import router from "@/router";
import store from "@/store";
import 'font-awesome/css/font-awesome.min.css';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import {getRequest} from "@/utils/api";
import {postRequest} from "@/utils/api";
import {putRequest} from "@/utils/api";
import {deleteRequest} from "@/utils/api";
import {postKetValueRequest} from "@/utils/api";
import {initMenu} from "@/utils/menus";

Vue.prototype.getRequest = getRequest;
Vue.prototype.postRequest = postRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.postKetValueRequest = postKetValueRequest;

Vue.use(ElementUI)
Vue.config.productionTip = false


router.beforeEach((to, from, next) => {
  if (to.path == '/') {
    next();
  } else {
    if (window.sessionStorage.getItem("user")) {
      initMenu(router, store);
      next();
    } else {
      next('/?redirect=' + to.path);
    }

  }
})


new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
