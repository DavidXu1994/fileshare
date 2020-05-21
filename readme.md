项目架构：Springboot+mybatis+thymeleaf+bootstrap
数据库：mysql
web服务器：tomcat（springboot内嵌）
==========================================

工程目录
Java
controller：控制层，所有对前台提供接口入口
entity：
po：实体对象，和数据库表对应
vo：后台返回前台包装对象，字段最简化
dto：后台之间数据传输

mybatis：
dao：数据库交互
mapper：mybatis接口
service：业务方法，接口类型
serviceImpl：实现service接口
FileshareApplication：main方法入口

resource
mappers：mybatis配置文件
templates：html页面

application.properties:配置文件

pom.xml:jar包依赖

==========================================
执行流程
controller-->service-->serviceImpl-->mapper-->mappers.xml-->结果返回controller--->thymeleaf解析--->html+bootstrap 展示渲染

controller：@RequestMapping 路径唯一；ModelAndView 数据名和页面数据一致；
mappers.xml:namespace、ID、type注意事项



