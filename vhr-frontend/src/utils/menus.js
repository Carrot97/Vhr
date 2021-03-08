import {getRequest} from "@/utils/api";

export const initMenu = (router, store) => {
    if (store.state.routes.length > 0) {
        // console.log("未刷新")
        return;
    }
    // console.log("刷新")
    getRequest("/system/config/menu").then(data => {
        if (data) {
            // console.log(data)
            let fmtRoutes = formatRoutes(data);
            router.addRoutes(fmtRoutes)
            store.commit('initRoutes', fmtRoutes)
            // console.log(store)
        }
    })
}

export const formatRoutes = (routes) => {
    let fmtRoutes = [];
    routes.forEach(router => {
        let {
            path,
            component,
            name,
            meta,
            iconcls,
            children
        } = router;
        if (children && children instanceof Array) {
            children = formatRoutes(children);
        }
        let fmtRouter = {
            path: path,
            name: name,
            iconcls: iconcls,
            meta: meta,
            children: children,
            component(resolve) {
                if (component.startsWith('Home')) {
                    require(['../views/' + component + '.vue'], resolve);
                } else if (component.startsWith('Emp')) {
                    require(['../views/emp/' + component + '.vue'], resolve);
                } else if (component.startsWith('Per')) {
                    require(['../views/per/' + component + '.vue'], resolve);
                } else if (component.startsWith('Sal')) {
                    require(['../views/sal/' + component + '.vue'], resolve);
                } else if (component.startsWith('Sta')) {
                    require(['../views/sta/' + component + '.vue'], resolve);
                } else if (component.startsWith('Sys')) {
                    require(['../views/sys/' + component + '.vue'], resolve);
                }
            }
        }
        fmtRoutes.push(fmtRouter)
    })

    return fmtRoutes;
}
