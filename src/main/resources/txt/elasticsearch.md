##elasticSearch基本概念:
 #####Elastic 本质上是一个分布式数据库，允许多台服务器协同工作，每台服务器可以运行多个 Elastic 实例。单个 Elastic 实例称为一个节点（node）。一组节点构成一个集群（cluster）。
 ```
 索引(index): 索引相当于Mysql里面的数据库,每个 Index （即数据库）的名字必须是小写。        
 文档(document): 文档相当于数据库的每行数据.
 字段(filed): 字段相当于数据库字段. 
 倒排索引:将字段拆分,并把 每个有该字段的文档记录  例如  王(document) : 1001文档,1002文档
 ```
---
```
"query" : { //查询
	"bool" : { //多个条件条件
		"must" : [ //必须同时成立
			{
				"match" : { //匹配
					"phoneCategory" : "小米"  //手机品牌
				},
				"match" : { //匹配
					"price" : "1999"  //价格 =1999
				},
			}
		]
	} //es在保存文档类型时会将数据拆开进行倒序索引保存
}  //相当于mysql     phoneCategory  like  '%小%' and phoneCategory like   '%米%' and price = 1999
------------------------------------------会跳舞的分割线--------------------------------------------------------
"query" : { //查询
	"bool" : { //多个条件条件
		"should" : [ //应该成立
			{
				"match" : { //匹配
					"phoneCategory" : "小米"  //手机品牌
				},
				"match" : { //匹配
					"phoneCategory" : "华为"  
				},
			}
		]
	}
}  //相当于mysql  phoneCategory  like  '%小%' and phoneCategory like   '%米%' or   phoneCategory  like  '%华%' and phoneCategory like   '%为%'
------------------------------------------会跳舞的分割线--------------------------------------------------------

"query" : { //查询
	"bool" : { //多个条件条件
		"must" : [ //必须同时成立
			{
				"match" : { //匹配
					"phoneCategory" : "小米"  //手机品牌
				}
			}
		],
		"filter" : {
			"range" : {
				"price" : {
					"gt" : 5000
				}
			}
		}
	}
}  //相当于mysql     phoneCategory  like  '%小%' and phoneCategory like   '%米%'  and price > 5000  
------------------------------------------会跳舞的分割线--------------------------------------------------------

"query" : {
	"match_phrase" : { // 完全匹配
		"phoneCategory" : "小米"
	},
	"heightlight" : {
		"fields" : {
			"phoneCategory" : {}
		}
	}//高亮
}  //相当于mysql     phoneCategory  = '小米'
------------------------------------------会跳舞的分割线--------------------------------------------------------

"aggs" : { //聚合分组
	"price_group" : { //聚合分组名称
		"terms" : { //分组
			"field" : "price" //分组字段
		}
	},
	size : 0 //忽略某些字段
} //相当于  select price as key(固定展示为key),  count(*) as doc_count  group by price 
------------------------------------------会跳舞的分割线--------------------------------------------------------

"aggs" : { //聚合分组
	"price_avg" : { //平均值分组名称
		"avg" : { //平均值
			"field" : "price" //字段
		}
	},
	size : 0 //忽略某些字段
} //相当于  select  avg(price) as value  group by price 
------------------------------------------会跳舞的分割线--------------------------------------------------------
{ //索引创建  映射关系
	"properties" : {
		"name" : {
			"type" : "text", //有分词效果,可以拆开查询
			"index" : true
		},
		"sex" : {
			"type" : "keyword", //关键字 不能拆分 必须完全匹配
			"index" : true
		},
		"tel" : {
			"type" : "keyword",  // 不能被查询
			"index" : false
		},
	}
}


```