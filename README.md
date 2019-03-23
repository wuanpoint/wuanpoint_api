# wuanpoint_api
# 午安点评v1.0
* 午安点评v1.0即原午安影视的后端重构版
* 午安点评为前后分离架构
* 后端基于springboot+ssm+mysql







配置文件,名称为 wuanpoint_application.yml

vim wuanpoint_application.yml
下面是配置文件详细内容:

#########################################################################################################


spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://ip:port/database_name?useSSL=false&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true
    username: username
    password: password
server:
  port: port
mybatis:
  configuration:
    map-underscore-to-camel-case: true
WUAN_POINT:
  APP_NAME: #应用名称
  SECRET: #午安点评项目秘钥
  SCOPE: #授权范围
  JWT_SECRET: #JWT秘钥
  OIDC_DOMAIN_NAME: #OIDC域名
  SUB_APP: #午安点评作为子系统的id，暂无,233是随便写的一个数
