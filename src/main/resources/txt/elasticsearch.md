##elasticSearch基本概念:  [ES中文官方文档](https://www.elastic.co/guide/cn/elasticsearch/guide/current/_most_important_queries.html)
 #####Elastic 本质上是一个分布式数据库，允许多台服务器协同工作，每台服务器可以运行多个 Elastic 实例。单个 Elastic 实例称为一个节点（node）。一组节点构成一个集群（cluster）。
 ```
 索引(index): 索引相当于Mysql里面的数据库,每个 Index （即数据库）的名字必须是小写。        
 文档(document): 文档相当于数据库的每行数据.
 字段(filed): 字段相当于数据库字段. 
 倒排索引:将字段拆分,并把 每个有该字段的文档记录  例如  王(document) : 1001文档,1002文档
 ```
---
match 查询:       
你在一个全文字段上使用 match 查询，在执行查询前，它将用正确的分析器去分析查询字符串  
```
{ "match": { "tweet": "About Search" }}
```     
如果在一个精确值的字段上使用它，例如数字、日期、布尔或者一个 not_analyzed 字符串字段，那么它将会精确匹配给定的值：
```
{ "match": { "age":    26           }}
{ "match": { "date":   "2014-09-01" }}
{ "match": { "public": true         }}
{ "match": { "tag":    "full_text"  }}
```
multi_match 查询可以在多个字段上执行相同的 match 查询：
```
{
    "multi_match": {
        "query":    "full text search",
        "fields":   [ "title", "body" ] //在这两个字段上查询匹配"full text search"的数据
    }
}
```

range 查询找出那些落在指定区间内的数字或者时间：
```
{
    "range": {
        "age": {
            "gte":  20, 大于等于
            "gt":  20,  大于
            "lt":   30  小于
            "lte":  30  小于等于
        }
    }
}
```
term 查询被用于精确值匹配，这些精确值可能是数字、时间、布尔或者那些 not_analyzed 的字符串：     
term 查询对于输入的文本不 分析 ，所以它将给定的值进行精确查询。
```
{ "term": { "age":    26           }}
{ "term": { "date":   "2014-09-01" }}
{ "term": { "public": true         }}
{ "term": { "tag":    "full_text"  }}
```

terms 查询和 term 查询一样，但它允许你指定多值进行匹配。  
如果这个字段包含了指定值中的任何一个值，那么这个文档满足条件：
```  
一定要了解 term 和 terms 是 包含（contains） 操作，而非 等值（equals） （判断）。  
如果我们进行term查询 { "term" : { "tags" : "search" } }他实际是查询tags字段里面包含search的  
解决方式 :  需要另起索引记录这个字段里面 search的出现次数 查询时带上该字段
{ "tags" : ["search"] }
{ "terms": { "tag": [ "search", "full_text", "nosql" ] }}

```
exists 查询和 missing 查询被用于查找那些指定字段中有值 (exists) 或无值 (missing) 的文档  
```
{
    "exists":   {
        "field":    "title" //个人理解 想当于MYSQL  where title is not null
    }
}
```
---
bool 查询来多查询组合在一起，成为用户自己想要的布尔查询。  
 - must:文档 必须 匹配这些条件才能被包含进来。  
 - must_not:文档 必须不 匹配这些条件才能被包含进来。
 - should:如果满足这些语句中的任意语句，将增加 _score ，否则，无任何影响。它们主要用于修正每个文档的相关性得分。
 - filter:必须 匹配，但它以不评分、过滤模式来进行。这些语句对评分没有贡献，只是根据过滤标准来排除或包含文档。
---
sort 参数进行排序: 
```
{
    "query" : {
        "bool" : {
            "filter" : { "term" : { "user_id" : 1 }}
        }
    },
    "sort": { "date": { "order": "desc" }}
    "sort": "number_of_children" //简便写法 字段默认升序,_score 的值进行降序
}
```
字段有多个值的排序，使用 min 、 max 、 avg 或是 sum 
```
"sort": {
    "dates": {   //意思就是dates 里面有多个 date值.
        "order": "asc",
        "mode":  "min"
    }
}
```
SELECT document FROM   products   
WHERE  
productID = "KDKE-B-9947-#kL5" OR (productID = "JODL-X-1937-#pV7" AND price  = 30 )
```
{   productID = "KDKE-B-9947-#kL5" 和 (productID = "JODL-X-1937-#pV7" AND price = 30 )平级,属于should
   "query" : {
      "filtered" : {
         "filter" : {
            "bool" : {
              "should" : [
                { "term" : {"productID" : "KDKE-B-9947-#kL5"}}, 
                { "bool" : { 
                  "must" : [
                    { "term" : {"productID" : "JODL-X-1937-#pV7"}}, 
                    { "term" : {"price" : 30}} 
                  ]
                }}
              ]
           }
         }
      }
   }
}
```
---
在收件箱中，且没有被读过的  
不在 收件箱中，但被标注重要的
```

{
  "query": {
      "constant_score": {
          "filter": {
              "bool": {
                 "should": [
                    { "bool": {
                          "must": [
                             { "term": { "folder": "inbox" }}, 
                             { "term": { "read": false }}
                          ]
                    }},
                    { "bool": {
                          "must_not": {
                             "term": { "folder": "inbox" } 
                          },
                          "must": {
                             "term": { "important": true }
                          }
                    }}
                 ]
              }
            }
        }
    }
}

```


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