<p align="center">
  <a class="logo" href="https://github.com/qinweizhao/qwz-blog">
    <img src="https://cdn.jsdelivr.net/gh/qinweizhao/qwz-blog@master/logo.png" height="80" width="45%" alt="Authority">
  </a>
</p>

<p align="center">
👉 <a href="https://www.qinweizhao.com">https://www.qinweizhao.com</a> 👈
</p>

<p align="center">
  <a href="https://github.com/qinweizhao/qwz-blog" target="_blank">
    <img src="https://img.shields.io/github/v/release/qinweizhao/qwz-blog?include_prereleases" alt="Release"/>
  </a>
</p>


![Alt](https://repobeats.axiom.co/api/embed/407d1af8c2e1faff46c37b1336137e2d0d7e27c4.svg "Analytics image")
## 简介

根据个人需求，基于 Halo-1.4.2 博客改造的个人博客。

### 仓库结构

```
qwz-blog
├─blog-backend  后端源码
│
├─blog-frontend 前端源码
│  ├─blog-frontend-admin 后台管理
│  ├─blog-frontend-portal 前台门面
│ 
├─blog-resource 项目资源
│  ├─docker 容器部署
│  ├─image 图片
│  ├─jar 依赖包
│  ├─logs 项目日志
│  ├─sql 数据库文件
│  ├─static 静态资源
```

## 改造

### fix

- 前台搜索不准确
- 后台管理前端 UI 显示

### add

- 调整后台布局
- 打包时自动将前端代码编译并复制到后端的 resources 文件夹下

### update

- Gradle 转为 Maven
- 构造器注入更改为属性注入

### remove

- 两步验证
- 多主题选择
- 迁移数据库
- 后台布局设置
- 与 GitHub 交互的功能

