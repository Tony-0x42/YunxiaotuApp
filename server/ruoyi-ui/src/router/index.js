import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: 路由配置项
 *
 * hidden: true                     // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true                 // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                  // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                  // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                  // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect             // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'               // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * query: '{"id": 1, "name": "ry"}' // 访问路由的默认传递参数
 * roles: ['admin', 'common']       // 访问路由的角色权限
 * permissions: ['a:a:a', 'b:b:b']  // 访问路由的菜单权限
 * meta : {
    noCache: true                   // 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
    title: 'title'                  // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'                // 设置该路由的图标，对应路径src/assets/icons/svg
    breadcrumb: false               // 如果设置为false，则不会在breadcrumb面包屑中显示
    activeMenu: '/system/user'      // 当路由设置了该属性，则会高亮相对应的侧边栏。
  }
 */

// 公共路由
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login'),
    hidden: true
  },
  {
    path: '/register',
    component: () => import('@/views/register'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error/401'),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: 'index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/index'),
        name: 'Index',
        meta: { title: '首页', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/lock',
    component: () => import('@/views/lock'),
    hidden: true,
    meta: { title: '锁定屏幕' }
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'profile',
        component: () => import('@/views/system/user/profile/index'),
        name: 'Profile',
        meta: { title: '个人中心', icon: 'user' }
      }
    ]
  }
]

// 动态路由，基于用户权限动态去加载
export const dynamicRoutes = [
  {
    path: '/system/user-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:user:edit'],
    children: [
      {
        path: 'role/:userId(\\d+)',
        component: () => import('@/views/system/user/authRole'),
        name: 'AuthRole',
        meta: { title: '分配角色', activeMenu: '/system/user' }
      }
    ]
  },
  {
    path: '/system/role-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:role:edit'],
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/views/system/role/authUser'),
        name: 'AuthUser',
        meta: { title: '分配用户', activeMenu: '/system/role' }
      }
    ]
  },
  {
    path: '/system/dict-data',
    component: Layout,
    hidden: true,
    permissions: ['system:dict:list'],
    children: [
      {
        path: 'index/:dictId(\\d+)',
        component: () => import('@/views/system/dict/data'),
        name: 'Data',
        meta: { title: '字典数据', activeMenu: '/system/dict' }
      }
    ]
  },
  {
    path: '/monitor/job-log',
    component: Layout,
    hidden: true,
    permissions: ['monitor:job:list'],
    children: [
      {
        path: 'index/:jobId(\\d+)',
        component: () => import('@/views/monitor/job/log'),
        name: 'JobLog',
        meta: { title: '调度日志', activeMenu: '/monitor/job' }
      }
    ]
  },
  {
    path: '/tool/gen-edit',
    component: Layout,
    hidden: true,
    permissions: ['tool:gen:edit'],
    children: [
      {
        path: 'index/:tableId(\\d+)',
        component: () => import('@/views/tool/gen/editTable'),
        name: 'GenEdit',
        meta: { title: '修改生成配置', activeMenu: '/tool/gen' }
      }
    ]
  },
  {
    path: '/batch/customer',
    component: Layout,
    hidden: true,
    permissions: ['batch:customer:list'],
    children: [
      {
        path: 'detail/:customerId(\\d+)',
        component: () => import('@/views/batch/customer/detail'),
        name: 'BatchCustomerDetail',
        meta: { title: '账号详情', activeMenu: '/batch/customer' }
      },
      {
        path: 'qrcode/:customerId(\\d+)',
        component: () => import('@/views/batch/customer/qrcode'),
        name: 'BatchCustomerQrCode',
        meta: { title: '注册二维码', activeMenu: '/batch/customer' }
      },
      {
        path: 'add',
        component: () => import('@/views/batch/customer/form'),
        name: 'BatchCustomerAdd',
        meta: { title: '新增账号', activeMenu: '/batch/customer' }
      },
      {
        path: 'edit/:customerId(\\d+)',
        component: () => import('@/views/batch/customer/form'),
        name: 'BatchCustomerEdit',
        meta: { title: '编辑账号', activeMenu: '/batch/customer' }
      }
    ]
  },
  {
    path: '/batch/notice',
    component: Layout,
    hidden: true,
    permissions: ['batch:notice:list'],
    children: [
      {
        path: 'add',
        component: () => import('@/views/batch/notice/form'),
        name: 'BatchNoticeAdd',
        meta: { title: '新增公告', activeMenu: '/batch/notice' }
      },
      {
        path: 'edit/:noticeId(\\d+)',
        component: () => import('@/views/batch/notice/form'),
        name: 'BatchNoticeEdit',
        meta: { title: '编辑公告', activeMenu: '/batch/notice' }
      }
    ]
  },
  {
    path: '/batch/home',
    component: Layout,
    hidden: true,
    permissions: ['batch:home:list'],
    children: [
      {
        path: 'add/:type?',
        component: () => import('@/views/batch/home/form'),
        name: 'BatchHomeAdd',
        meta: { title: '新增首页配置', activeMenu: '/batch/home' }
      },
      {
        path: 'edit/:type/:id(\\d+)',
        component: () => import('@/views/batch/home/form'),
        name: 'BatchHomeEdit',
        meta: { title: '修改首页配置', activeMenu: '/batch/home' }
      }
    ]
  },
  {
    path: '/batch/config',
    component: Layout,
    hidden: true,
    permissions: ['batch:config:list'],
    children: [
      {
        path: 'add',
        component: () => import('@/views/batch/config/form'),
        name: 'BatchConfigVersionAdd',
        meta: { title: '新增版本', activeMenu: '/batch/config' }
      },
      {
        path: 'edit/:versionId(\\d+)',
        component: () => import('@/views/batch/config/form'),
        name: 'BatchConfigVersionEdit',
        meta: { title: '修改版本', activeMenu: '/batch/config' }
      }
    ]
  },
  {
    path: '/batch/tutorial',
    component: Layout,
    hidden: true,
    permissions: ['batch:tutorial:list'],
    children: [
      {
        path: 'add',
        component: () => import('@/views/batch/tutorial/form'),
        name: 'BatchTutorialAdd',
        meta: { title: '新增教程', activeMenu: '/batch/tutorial' }
      },
      {
        path: 'edit/:tutorialId(\\d+)',
        component: () => import('@/views/batch/tutorial/form'),
        name: 'BatchTutorialEdit',
        meta: { title: '修改教程', activeMenu: '/batch/tutorial' }
      },
      {
        path: 'category/add',
        component: () => import('@/views/batch/tutorial/categoryForm'),
        name: 'BatchTutorialCategoryAdd',
        meta: { title: '新增分类', activeMenu: '/batch/tutorial' }
      },
      {
        path: 'category/edit/:categoryId(\\d+)',
        component: () => import('@/views/batch/tutorial/categoryForm'),
        name: 'BatchTutorialCategoryEdit',
        meta: { title: '修改分类', activeMenu: '/batch/tutorial' }
      }
    ]
  },
  {
    path: '/batch/document',
    component: Layout,
    hidden: true,
    permissions: ['batch:document:list'],
    children: [
      {
        path: 'add',
        component: () => import('@/views/batch/document/form'),
        name: 'BatchDocumentAdd',
        meta: { title: '新增文档', activeMenu: '/batch/document' }
      },
      {
        path: 'edit/:documentId(\\d+)',
        component: () => import('@/views/batch/document/form'),
        name: 'BatchDocumentEdit',
        meta: { title: '修改文档', activeMenu: '/batch/document' }
      }
    ]
  },
  {
    path: '/batch/brand',
    component: Layout,
    hidden: true,
    permissions: ['batch:brand:list'],
    children: [
      {
        path: 'add',
        component: () => import('@/views/batch/brand/form'),
        name: 'BatchBrandAdd',
        meta: { title: '新增品牌专区', activeMenu: '/batch/brand' }
      },
      {
        path: 'edit/:brandId(\\d+)',
        component: () => import('@/views/batch/brand/form'),
        name: 'BatchBrandEdit',
        meta: { title: '修改品牌专区', activeMenu: '/batch/brand' }
      }
    ]
  },
  {
    path: '/batch/contact',
    component: Layout,
    hidden: true,
    permissions: ['batch:contact:list'],
    children: [
      {
        path: 'add',
        component: () => import('@/views/batch/contact/form'),
        name: 'BatchContactAdd',
        meta: { title: '新增联系方式', activeMenu: '/batch/contact' }
      },
      {
        path: 'edit/:contactId(\\d+)',
        component: () => import('@/views/batch/contact/form'),
        name: 'BatchContactEdit',
        meta: { title: '修改联系方式', activeMenu: '/batch/contact' }
      }
    ]
  }
]

// 防止连续点击多次路由报错
let routerPush = Router.prototype.push
let routerReplace = Router.prototype.replace
// push
Router.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(err => err)
}
// replace
Router.prototype.replace = function push(location) {
  return routerReplace.call(this, location).catch(err => err)
}

export default new Router({
  mode: 'history', // 去掉url中的#
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})
