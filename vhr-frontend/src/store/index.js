import Vue from 'vue'
import Vuex from 'vuex'
import {getRequest} from "@/utils/api";
import {Notification} from 'element-ui';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

Vue.use(Vuex)

const now = new Date();

const store = new Vuex.Store({
    state: {
        routes: [],
        sessions: {},
        hrs: [],
        currentSession: null,
        currentHr: null,
        filterKey: '',
        stomp: null,
        isDot: {},
    },
    mutations: {
        init_currentHr(state, data) {
            state.currentHr = data
        },
        initRoutes(state, data) {
            state.routes = data;
        },
        changeCurrentSession(state, currentSession) {
            Vue.set(state.isDot, state.currentHr.username + '#' + currentSession.username, false)
            state.currentSession = currentSession;
        },
        addMessage(state, msg) {
            let storeName = state.currentHr.username + "#" + msg.to;
            let mss = state.sessions[storeName];
            if (!mss) {
                // 该方法不会自动刷新新加入的属性, vue默认只刷新已有的属性
                // state.sessions[storeName] = [];
                Vue.set(state.sessions, storeName, [])
            }
            state.sessions[storeName].push({
                content: msg.content,
                date: new Date(),
                self: !msg.notSelf
            })
        },
        INIT_DATA(state) {
            let data = localStorage.getItem('vue-chat-session');
            //console.log(data)
            if (data) {
                state.sessions = JSON.parse(data);
            }
        },
        INIT_HRS(state) {
            getRequest("/chat/hrs").then(resp => {
                if (resp) {
                    state.hrs = resp;
                }
            })
        }
    },
    actions: {
        connect(context) {
            context.state.stomp = Stomp.over(new SockJS("/ws/ep"));
            context.state.stomp.connect({}, success => {
                context.state.stomp.subscribe("/user/queue/chat", msg => {
                    let received = JSON.parse(msg.body);
                    received.notSelf = true;
                    received.to = received.from;
                    if (!context.state.currentSession || context.state.currentSession.username != received.from) {
                        Notification.info({
                            title: '【' + received.fromNickName + '】发来一条消息',
                            message: received.content.length > 10 ? received.content.substr(0, 10) + '...' : received.content,
                            position: 'bottom-right'
                        });
                        Vue.set(context.state.isDot, context.state.currentHr.username + '#' + received.to, true)
                    }
                    context.commit('addMessage', received)
                })
            }, error => {
                console.log("error")
            })
        },
        initData(context) {
            context.commit('INIT_DATA');
            context.commit('INIT_HRS');
        }
    }
})


store.watch(function (state) {
    return state.sessions
}, function (val) {
    // console.log('CHANGE: ', val);
    localStorage.setItem('vue-chat-session', JSON.stringify(val));
}, {
    deep: true/*这个貌似是开启watch监测的判断,官方说明也比较模糊*/
})


export default store;
