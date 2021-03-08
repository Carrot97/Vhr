import Vue from 'vue'
import Router from 'vue-router'
import Login from "@/views/Login";
import Home from "@/views/Home";
import FriendChat from "@/views/chat/FriendChat";

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            name: 'Login',
            component: Login,
            hidden: true
        },
        {
            path: '/home',
            name: 'Home',
            component: Home,
            hidden: true,
            children: [
                {
                    path: '/chat',
                    name: '在线聊天',
                    component: FriendChat,
                    hidden: true
                },
            ]
        },
    ]
})

