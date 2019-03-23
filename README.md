# wuanpoint_api
# 午安点评v1.0
* 午安点评v1.0即原午安影视的后端重构版
* 午安点评为前后分离架构
* 后端基于springboot+ssm+mysql







配置文件,名称为 wuanpoint_application.yml

vim wuanpoint_application.yml
下面是配置文件详细内容:

#########################################################################################################


spring:<br/>
&emsp;&emsp;datasource:<br/>
&emsp;&emsp;&emsp;&emsp;driver-class-name: com.mysql.jdbc.Driver<br/>
&emsp;&emsp;&emsp;&emsp;url: jdbc:mysql://ip:port/database_name?useSSL=false&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true<br/>
&emsp;&emsp;&emsp;&emsp;username: username<br/>
&emsp;&emsp;&emsp;&emsp;password: password<br/>
server:<br/>
&emsp;&emsp;port: port<br/>
mybatis:<br/>
&emsp;&emsp;configuration:<br/>
&emsp;&emsp;&emsp;&emsp;map-underscore-to-camel-case: true<br/>
WUAN_POINT:<br/>
&emsp;&emsp;APP_NAME: #应用名称<br/>
&emsp;&emsp;SECRET: #午安点评项目秘钥<br/>
&emsp;&emsp;SCOPE: #授权范围<br/>
&emsp;&emsp;JWT_SECRET: #JWT秘钥<br/>
&emsp;&emsp;OIDC_DOMAIN_NAME: #OIDC域名<br/>
&emsp;&emsp;SUB_APP: #午安点评作为子系统的id，暂无,233是随便写的一个数<br/>
