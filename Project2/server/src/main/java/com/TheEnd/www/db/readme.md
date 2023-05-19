# 本文介绍接口 `DBOperators` 应当接收和返回的数据类型

>
>注意，非必要变量可能为null：如无特殊声明，所有变量默认为String
>

## ArrayList dealUser(UserOpType t, ArrayList content)

### UserOpType.Login

```java
{username,password}
//return True/False 
```

### UserOpType.CreateUser

```java
{username,password,user_id,phone,password}
//registration_time is current_timestamp
//False when username exists
```

### UserOpType.DeleteUser

```java
{username}
//False for user not exists
```

### UserOpType.ChangePassword

```java
{username,old_password,new_passwd}
//return False if old_password is wrong
```

## ArrayList dealPost(PostOpType t, ArrayList content)

### PostOpType.GetPost

```java
{someEnum‘s name(String type!!!),content_1,...}
        这个枚举和对应的内容你自己，完了我这边改,枚举记得实现getname以通过给的string通过valueOf转换为Enum
```

### PostOpType.AddPost

```java
{title, author_name, city, country, content}
//return null
```
### PostOpType.AddReply

```java
{post_id, content, author_name}
//return null
```
### PostOpType.AddSecReply

```java
{reply_id, content, author_name}
//return null
```
### PostOpType.GetPost

```java
{post_id}
//return Post类，包含ArrayList<Reply>以及Reply下的ArrayList<SecReply>
```
## ArrayList dealRelation(PostOpType t, ArrayList content)
### RelationOpType.Like/Fav/Share
```java
{post_id, user_name}
//return null
```
### RelationOpType.Follow/DeleteFollow
```java
{followee, follower}
//return null
```
## ArrayList dealShow(PostOpType t, ArrayList content)
### ShowOpType.Show(Like/Fav/Share)Posts
```java
{user_name}
//return list of SimplePost object
```
### ShowOpType.Show(Followers/Followees)
```java
{user_name}
//return list of User object
```
### ShowOpType.ShowUserPost
```java
{user_name}
//return list of SimplePost object
```
### ShowOpType.ShowUserReplyPost
```java
{user_name}
//展示用户回复的post，附加用户回复内容
//return list of SimplePost object, which contains an appendix of reply content
```
> *其余同理，写完了之后记得在这里更新api信息*

## ArrayList dealSearch(SearchOpType t, ArrayList content)
- search options:
  -  0. search from title and post content(always used);
  -  1. search from reply and sec_reply content; 
  -  2. search with time interval 
### SearchOpType.SearchDefault
```java
//only option 0.
{keyword, limit, offset}
//return SimplePost list
```
### SearchOpType.SearchOpt1
```java
//option 1 added.
{keyword, limit, offset}
//reply/secreply content is in SimplePost
```
### SearchOpType.SearchOpt2
```java
//option 2 added.
{keyword, time_start, time_end, limit, offset}
//time is like 2023-5-19 00:00:00
```
### SearchOpType.SearchOpt12
```java
//option 1 and 2 added.
{keyword, time_start, time_end, limit, offset}
```


```java
{num(integer,表示同时搜索几个选项),option_1,option_2,...,option_num,offset(integer 用于dense_rank),limit(integer,同样用于dense_rank)}
//注意:如果offset或limit为null则表示没有这两项限制
```
