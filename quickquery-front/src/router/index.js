import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * 没有权限控制的路由
 */
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/common/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/common/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/common/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/common/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/common/error-page/401'),
    hidden: true
  }
]

/**
 * 有权限控制的路由
 */
export const asyncRoutes = [
  {
    path: '/',
    component: Layout,
    meta: {
      title: 'OrderQuery',
      icon: 'component'
    },
    children: [
      {
        path: '',
        component: () => import('@/views/common/order-search/search-component'),
        name: 'OrderQuery',
        meta: {
          title: 'OrderQuery'
        }
      }]
  },
  {
    path: '/graph-manage',
    component: Layout,
    alwaysShow: true, // will always show the root menu
    name: 'GraphManege',
    meta: {
      title: 'Graph管理',
      icon: 'form'
    },
    children: [
      {
        path: '',
        component: () => import('@/views/manage/orderquery/graph-manage'),
        name: 'GraphList',
        meta: {
          title: 'Graph列表'
        }
      },
      {
        path: 'info',
        component: () => import('@/views/manage/orderquery/components/graph-component'),
        name: 'GraphInfo',
        meta: {
          title: 'Graph详情'
        },
        hidden: true
      },
      {
        path: 'new',
        component: () => import('@/views/manage/orderquery/components/graph-component'),
        name: 'NewGraph',
        meta: {
          title: 'Graph配置'
        },
        hidden: true
      },
      {
        path: 'edit',
        component: () => import('@/views/manage/orderquery/components/graph-component'),
        name: 'EditGraph',
        meta: {
          title: 'Graph详情'
        },
        hidden: true
      }
    ]
  },
  {
    path: '/db-manage',
    component: Layout,
    alwaysShow: true, // will always show the root menu
    name: 'DatabaseManage',
    meta: {
      title: 'Database管理',
      icon: 'table'
    },
    children: [
      {
        path: '',
        component: () => import('@/views/manage/orderquery/db-manage'),
        name: 'DatabaseList',
        meta: {
          title: 'Database列表'
        }
      }
    ]
  },
  {
    path: '/hash-manage',
    component: Layout,
    alwaysShow: true, // will always show the root menu
    name: 'ShardingManage',
    meta: {
      title: 'Sharding管理',
      icon: 'guide'
    },
    children: [
      {
        path: '',
        component: () => import('@/views/manage/orderquery/hash-manage'),
        name: 'ShardingList',
        meta: {
          title: 'Sharding列表'
        }
      },
      {
        path: 'info',
        component: () => import('@/views/manage/orderquery/components/hash-component'),
        name: 'HashInfo',
        meta: {
          title: 'Hash规则'
        },
        hidden: true
      },
      {
        path: 'new',
        component: () => import('@/views/manage/orderquery/components/hash-component'),
        name: 'NewHash',
        meta: {
          title: 'Hash规则'
        },
        hidden: true
      },
      {
        path: 'edit',
        component: () => import('@/views/manage/orderquery/components/hash-component'),
        name: 'EditHash',
        meta: {
          title: 'Hash规则'
        },
        hidden: true
      }
    ]
  },
  {
    path: '/order-search',
    component: () => import('@/views/common/order-search/search-component'),
    name: 'OrderSearch',
    meta: {
      title: '单据关联查询',
      icon: 'component'
    },
    hidden: true
  },

  {
    path: '/error',
    component: Layout,
    redirect: 'noRedirect',
    name: 'ErrorPages',
    meta: {
      title: 'Error Pages',
      icon: '404'
    },
    children: [
      {
        path: '401',
        component: () => import('@/views/common/error-page/401'),
        name: 'Page401',
        meta: { title: '401', noCache: true }
      },
      {
        path: '404',
        component: () => import('@/views/common/error-page/404'),
        name: 'Page404',
        meta: { title: '404', noCache: true }
      }
    ],
    hidden: true
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
