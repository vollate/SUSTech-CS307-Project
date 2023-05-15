# 本文介绍接口 `DBOperators` 应当接收和返回的数据类型
>
>注意，非必要变量可能为null：如无特殊声明，所有变量默认为String
>
## ArrayList dealUser(UserOpType t, ArrayList content)

### UserOpType.Login

```java
{username,password}
```

### UserOpType.CreateUser

```java
{username,user_id,registration_time,phone,password}
```

### UserOpType.DeleteUser

```java
{username}
```
### UserOpType.ChangePassword
```java
{username,old_password,new_passwd}
//return null if old_password is wrong
```
## ArrayList dealPost(PostOpType t, ArrayList content)
### PostOpType.GetPost
```java
{someEnum‘s name(String type!!!),content_1,...}
这个枚举和对应的内容你自己，完了我这边改,枚举记得实现getname以通过给的string通过valueOf转换为Enum
```
### PostOpType.GetReply
```java
同上
```
>*其余同理，写完了之后记得在这里更新api信息*
## ArrayList dealSearch(SearchOpType t, ArrayList content)
```java
{num(integer,表示同时搜索几个选项),option_1,option_2, ... ,option_num, offset(integer 用于dense_rank), limit(integer, 同样用于dense_rank)}
//注意:如果offset或limit为null则表示没有这两项限制
```
