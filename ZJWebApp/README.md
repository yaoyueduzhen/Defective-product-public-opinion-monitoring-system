### 南京大学iip研究组江苏质监局项目

---

#### 采用技术

1. 整体使用springboot框架避免了许多配置

2. dao层使用spring data jpa来访问mysql数据库，使用spring data solr来对solr进行检索，使用spring data redis来连接redis

3. 前端网页模板使用的是thymeleaf，样式框架使用的bootstrap
---
#### 运行方法
1. git clone git@github.com:ayuileng/zhijianju.git
2. cd zhijianju
3. 修改src/main/resources/application.properties中数据源的参
4. mvnw spring-boot:run
---
#### 项目部署实例
http://iip.nju.edu.cn:8888/