# Fly-On-Time

网站地址：http://47.94.96.234:8080/

这是一个练习作品，按历史的航班动态数据，以出发地及目的地为统计口径，统计历史的准点率、延误率等，供出行参考，选择合适的出行时段和航班。

数据来源于 [和鲸社区](https://www.heywhale.com/) 的 [携程机票航班延误预测算法大赛数据集](https://www.heywhale.com/mw/dataset/59793a5a0d84640e9b2fedd3)， 数据集协议：CC-BY 4.0 转载需署名

数据导入ClickHouse，使用Spring JDBC集成，前端以纯HTML、Bootstrap实现，相对简单，项目共享在Github，欢迎有兴趣的朋友一起参与完善。