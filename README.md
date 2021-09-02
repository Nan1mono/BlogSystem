# 简易博客系统

支持登录和注册

超过30分钟无操作，会自动退出登录

非登录状态不能查看、修改和删除文章

登录状态下 可以查看、修改、删除自己的文章，他人文章只能查看

# 环境

MySQL 5.5.27

Tomcat 8.5

jdk 1.8

IDEA

# 使用说明

jdbc相关的jar存放在web-WEB INF-lib下

需要tomcat安装目录下lib中的servlet包，自行导入

sql文件在blog.sql文件中，自行导入至数据库

数据库配置文件在db.properties中，修改自己需要的数据即可



@author Nanimono

@Email nanimono@163.com

@Date 2021.09.01 23:51