# 接口文档

## 用户操作

### ✨用户注册

#### 接口地址

`/user/signup`

#### 返回格式

json

#### 请求类型

POST

#### 请求参数说明

|   参数   |  类型  | 必填 |     说明     |
| :------: | :----: | :--: | :----------: |
| account  | String |  是  | 用户注册账号 |
| password | String |  是  | 用户注册密码 |
| nickname | String |  是  | 用户注册昵称 |

#### 返回参数说明

|   参数   |  类型  |                说明                 |
| :------: | :----: | :---------------------------------: |
|   code   |  int   | 请求结果200为注册成功，-1为注册失败 |
| message  | String |              描述信息               |
| nickname | String |            用户注册昵称             |
|   uid    | String |               用户ID                |
| account  | String |              用户账号               |
| userType |  int   |              用户类型               |

#### json返回示例

```json
{
	code:200,
	message:注册成功,
	nickname:LiHua,
	uid:2045267191,
	account:123456,
	userType:0
}
```



------

### ✨获取用户信息

#### 接口地址

`/user/info`

#### 返回格式

json

#### 请求类型

GET

#### 请求参数说明

| 参数 |  类型  | 必填 |  说明  |
| :--: | :----: | :--: | :----: |
| uid  | String |  是  | 用户id |

#### 返回参数说明

|  参数   |  类型  |                说明                 |
| :-----: | :----: | :---------------------------------: |
|  code   |  int   | 请求结果200为获取成功，-1为登录失败 |
| message | String |              描述信息               |
|  data   | Object |              用户信息               |

#### json返回示例

```json
{
	code:200,
	message:查询成功,
    data:{
        nickname:LiHua,
		uid:2045267191,
		account:123456,
		userType:0,
    	avatar:"http://localhost:8080/statci/avatar/001.png"
    }
}
```


------

### ✨修改用户昵称

#### 接口地址

`/user/nickname`

#### 返回格式

json

#### 请求类型

POST

#### 请求参数说明

|    参数    |  类型  | 必填 |  说明  |
|:--------:| :----: | :--: |:----:|
|   uid    | String |  是  | 用户ID |
| nickname | String |  是  | 用户昵称 |

#### 返回参数说明

|  参数   |  类型  |            说明             |
| :-----: | :----: | :-------------------------: |
|  code   |  int   | 请求结果200为成功，-1为失败 |
| message | String |          结果描述           |

#### json返回示例

```json
{
	code:200,
	message:修改成功
}
```

------



### ✨修改用户密码

#### 接口地址

`/user/password`

#### 返回格式

json

#### 请求类型

POST

#### 请求参数说明

|    参数    |  类型  | 必填 |  说明  |
|:--------:| :----: | :--: |:----:|
|   uid    | String |  是  | 用户ID |
| password | String |  是  | 用户密码 |

#### 返回参数说明

|  参数   |  类型  |            说明             |
| :-----: | :----: | :-------------------------: |
|  code   |  int   | 请求结果200为成功，-1为失败 |
| message | String |          结果描述           |

#### json返回示例

```json
{
	code:200,
	message:修改成功
}
```

------



### ✨用户登录

#### 接口地址

`/user/login`

#### 返回格式

json

#### 请求类型

POST

#### 请求参数说明

|   参数   |  类型  | 必填 |   说明   |
| :------: | :----: | :--: | :------: |
| account  | String |  是  | 用户账号 |
| password | String |  是  | 用户密码 |

#### 返回参数说明

|   参数   |  类型  |                说明                 |
| :------: | :----: | :---------------------------------: |
|   code   |  int   | 请求结果200为登录成功，-1为登录失败 |
| message  | String |              描述信息               |
| nickname | String |              用户昵称               |
|   uid    | String |               用户ID                |
| account  | String |              用户账号               |
| userType |  int   |              用户类型               |
|  token   | String |              登录凭证               |
|  avatar  | String |              头像地址               |

#### json返回示例

```json
{
	code:200,
	message:登录成功,
	nickname:LiHua,
	uid:2045267191,
	account:123456,
	userType:0,
    token:41737a45-bb97-42b8-8078-88decef581c8,
    avatar:"http://localhost:8080/statci/avatar/001.png"
}
```

------



## 博客操作

### ✨文章分类查询

#### 接口地址

`/category/list`

#### 返回类型

json

#### 请求类型

GET

#### 请求参数说明

| 参数 | 类型 | 必填 | 说明 |
| :--: | :--: | :--: | :--: |
|  \   |  \   |  \   |  \   |

#### 返回参数说明

|  参数   |    类型     |           说明            |
| :-----: | :---------: | :-----------------------: |
|  code   |     int     | 请求结果200为成功-1为失败 |
| message |   String    |         描述信息          |
|  rows   | object/json |         查询结果          |

#### json返回示例

```json
{
	code:200,
    message:查询成功,
    rows:{
        {
        	id:123135,
        	name:科技
    	},
    	{
    		id:346165,
    		name:生活
		},
		{
            id:23346,
            name:技术
        }
    }
}
```

-----

### ✨文章分类增加

#### 接口地址

`/category/add`

#### 返回类型

json

#### 请求类型

POST

#### 请求参数说明

| 参数 |  类型  | 必填 |   说明   |
| :--: | :----: | :--: | :------: |
| name | String |  是  | 分类名称 |

#### 返回参数说明

|  参数   |  类型  |            说明             |
| :-----: | :----: | :-------------------------: |
|  code   |  int   | 请求结果200为成功，-1为失败 |
| message | String |          结果描述           |

#### json返回示例

```json
{
	code:200,
    message:添加成功
}
```

----

### ✨文章分类删除

#### 接口地址

`/category/delete`

#### 返回类型

json

#### 请求类型

POST

#### 请求参数说明

| 参数 |  类型  | 必填 |  说明  |
| :--: | :----: | :--: | :----: |
|  id  | String |  是  | 分类id |

#### 返回参数说明

|  参数   |  类型  |            说明             |
| :-----: | :----: | :-------------------------: |
|  code   |  int   | 请求结果200为成功，-1为失败 |
| message | String |          结果描述           |

#### json返回示例

```json
{
	code:200,
    message:删除成功
}
```

-----

### ✨文章分类更新

#### 接口地址

`/category/update`

#### 返回类型

json

#### 请求类型

POST

#### 请求参数说明

| 参数 |  类型  | 必填 |  说明  |
| :--: | :----: | :--: | :----: |
|  id  | String |  是  | 分类id |
| name | String |  是  | 分类名 |

#### 返回参数说明

|  参数   |  类型  |            说明             |
| :-----: | :----: | :-------------------------: |
|  code   |  int   | 请求结果200为成功，-1为失败 |
| message | String |          结果描述           |

#### json返回示例

```json
{
	code:200,
    message:更新成功
}
```

-----

### ✨查询文章

#### 接口地址

`/article/search`

#### 返回类型

json

#### 请求类型

GET

#### 请求参数说明

|    参数    |  类型  | 必填 |       说明        |
| :--------: | :----: | :--: | :---------------: |
|  keyword   | String |  否  |    标题或内容关键字     |
| categoryId | String |  否  |      分类ID       |
|    page    |  int   |  否  |   页码 默认为1    |
|  pageSize  |  int   |  否  | 分页大小 默认为10 |

#### 返回参数说明

|  参数   |  类型  |           说明            |
| :-----: | :----: | :-----------------------: |
|  code   |  int   | 请求结果200为成功-1为失败 |
| message | String |       结果描述信息        |
|  data   | Object |         分页数据          |

#### json返回示例

```json
{
	code:200,
    message:查询成功,
    data:{
        keyword:关键字,
        category:分类ID,
        page:页码,
        pageSize:分页大小,
        rows:[
            {
                id:文章id1,
                title:文章标题1,
                categoryId:文章所属分类ID,
                createTime:文章创建时间1
            },
            {
                id:文章id2,
                title:文章标题2,
                categoryId:文章所属分类ID,
                createTime:文章创建时间2
            },
            {
                id:文章id3,
                title:文章标题3,
                categoryId:文章所属分类ID,
                createTime:文章创建时间3
            }
        ],
        count:文章数量
    }
}
```

----

### ✨增加文章

#### 接口地址

```
/articel/add
```

#### 返回类型

json

#### 请求类型

POST

#### 请求参数说明

|    参数    |  类型  | 必填 |    说明    |
| :--------: | :----: | :--: | :--------: |
|   title    | String |  是  |  文章标题  |
| categoryId | String |  是  | 所属分类ID |
|  content   | String |  是  |  文章内容  |

#### 返回参数说明

|  参数   |  类型  |            说明             |
| :-----: | :----: | :-------------------------: |
|  code   |  int   | 请求结果200为成功，-1为失败 |
| message | String |          结果描述           |

#### json返回示例

```json
{
	code:200,
    message:添加成功
}
```

----

### ✨删除文章

#### 接口地址

```
/articel/delete
```

#### 返回类型

json

#### 请求类型

POST

#### 请求参数说明

| 参数 |  类型  | 必填 |  说明  |
| :--: | :----: | :--: | :----: |
|  id  | String |  是  | 文章ID |

#### 返回参数说明

|  参数   |  类型  |            说明             |
| :-----: | :----: | :-------------------------: |
|  code   |  int   | 请求结果200为成功，-1为失败 |
| message | String |          结果描述           |

#### json返回示例

```
{
	code:200,
    message:删除成功
}
```

----

----



### ✨更新文章

#### 接口地址

```
/articel/update
```

#### 返回类型

json

#### 请求类型

POST

#### 请求参数说明

|    参数    |  类型  | 必填 |    说明    |
| :--------: | :----: | :--: | :--------: |
|     id     | String |  是  |   文章ID   |
|   title    | String |  是  |  文章标题  |
| categoryId | String |  是  | 所属分类ID |
|  content   | String |  是  |  文章内容  |

#### 返回参数说明

|  参数   |  类型  |            说明             |
| :-----: | :----: | :-------------------------: |
|  code   |  int   | 请求结果200为成功，-1为失败 |
| message | String |          结果描述           |

#### json返回示例

```
{
	code:200,
    message:更新成功
}
```

----



### ✨获取文章详情

#### 接口地址

```
/articel/detail
```

#### 返回类型

json

#### 请求类型

GET

#### 请求参数说明

| 参数 |  类型  | 必填 |  说明  |
| :--: | :----: | :--: | :----: |
|  id  | String |  是  | 文章ID |

#### 返回参数说明

|  参数   |  类型  |            说明             |
| :-----: | :----: | :-------------------------: |
|  code   |  int   | 请求结果200为成功，-1为失败 |
| message | String |          结果描述           |
|  data   | Object |          文章信息           |

#### json返回示例

```
{
	code:200,
    message:获取成功,
    data:{
    	id:123456,
    	title:abc,
    	categoryId,
    	content:文章内容,
    	createTime:1564891633
    }
}
```

----



## 文件上传

### ✨富文本图片上传

#### 接口地址

```
/upload/image
```

#### 返回类型

json

#### 请求类型

POST

#### 请求参数说明

| 参数 | 类型 | 必填 |    说明    |
| :--: | :--: | :--: | :--------: |
| file | 图片 |  是  | 上传的图片 |

#### 返回参数说明

|  参数   |  类型  |            说明             |
| :-----: | :----: | :-------------------------: |
|  code   |  int   | 请求结果200为成功，-1为失败 |
| message | String |          结果说明           |
|  data   |  List  |   返回图片静态地址等数据    |

#### json返回示例

```
{
	code:200,
	message:上传成功,
	data:[
		{
			url:"http://localhost:8080/static/1.png"
			alt:"",
			herf:""
		}
	]
}
```



----

### ✨用户头像上传

#### 接口地址

```
/upload/avatar
```

#### 返回类型

json

#### 请求类型

POST

#### 请求参数说明

| 参数 | 类型 | 必填 |    说明    |
| :--: | :--: | :--: | :--------: |
| file | 图片 |  是  | 上传的图片 |

#### 返回参数说明

|  参数   |  类型  |            说明             |
| :-----: | :----: | :-------------------------: |
|  code   |  int   | 请求结果200为成功，-1为失败 |
| message | String |          结果说明           |
|  data   |  List  |   返回图片静态地址等数据    |

#### json返回示例

```
{
	code:200,
	message:上传成功,
	data:[
		{
			url:"http://localhost:8080/static/1.png"
			alt:"",
			herf:""
		}
	]
}
```

----

## 评论

### ✨发表评论

#### 接口地址

```
/comment/post
```

#### 返回类型

json

#### 请求类型

POST

#### 请求参数说明

|  参数   |  类型  | 必填 |   说明   |
| :-----: | :----: | :--: | :------: |
|   id    | String |  是  |  文章ID  |
|   uid   | String |  是  |  用户ID  |
| comment | String |  是  | 评论内容 |

#### 返回参数说明

|  参数   |  类型  |            说明             |
| :-----: | :----: | :-------------------------: |
|  code   |  int   | 请求结果200为成功，-1为失败 |
| message | String |          结果描述           |

#### json返回示例

```
{
	code:200,
    message:发表成功
}
```

----

### ✨删除评论

#### 接口地址

```
/comment/delete
```

#### 返回类型

json

#### 请求类型

POST

#### 请求参数说明

| 参数 |  类型  | 必填 |  说明  |
| :--: | :----: | :--: | :----: |
|  id  | String |  是  | 文章ID |
| cid  | String |  是  | 评论ID |

#### 返回参数说明

|  参数   |  类型  |            说明             |
| :-----: | :----: | :-------------------------: |
|  code   |  int   | 请求结果200为成功，-1为失败 |
| message | String |          结果描述           |

#### json返回示例

```
{
	code:200,
    message:删除成功
}
```

----

### ✨获取评论

#### 接口地址

```
/comment/list
```

#### 返回类型

json

#### 请求类型

GET

#### 请求参数说明

| 参数 |  类型  | 必填 |  说明  |
| :--: | :----: | :--: | :----: |
|  id  | String |  是  | 文章ID |

#### 返回参数说明

|  参数   |  类型  |            说明             |
| :-----: | :----: | :-------------------------: |
|  code   |  int   | 请求结果200为成功，-1为失败 |
| message | String |          结果描述           |
|  data   |  List  |          评论列表           |

#### json返回示例

```
{
	code:200,
    message:查询成功,
    data:[
    	{
​		cid:评论Id,
​		uid:发表评论用户id,
​		content:评论内容
​	}，
​	{
​		cid:123465,
​		uid:564898,
​		content:评论内容2
​	}，
	{
​		cid:656488,
​		uid:5565468,
​		content:评论内容3
​	}
    ]
}
```

----



----

# 数据表示例

## 用户表 User

| 表头 |  UID   | nickname | account | password | userType | token  |
| :--: | :----: | :------: | :-----: | :------: | :------: | :----: |
| 类型 | String |  String  | String  |  String  |   int    | String |

## 分类表 Category

| 表头 |   id   |  name  |
| :--: | :----: | :----: |
| 类型 | String | String |

## 文章表 Article

| 表头 |   id   | categoryId | title  | content | createTime | comment |
| :--: | :----: | :--------: | :----: | :-----: | :--------: | :-----: |
| 类型 | String |   String   | String | String  |   String   |  见下   |

comment数据结构 类型为String

[

​	{

​		cid:评论Id,

​		uid:发表评论用户id,

​		content:评论内容

​	}，

​	{

​		cid:123465,

​		uid:564898,

​		content:评论内容2

​	}，

{

​		cid:656488,

​		uid:5565468,

​		content:评论内容3

​	}

]

