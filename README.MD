# PicaComic-Api 

本项目是哔咔漫画的Api，实现了大部分常用接口  
如果您喜欢本项目，欢迎点个Star/Fork~ QwQ  
许可证MIT LICENSE，请勿用于非法用途  
如有任何建议欢迎issue或mail:[shirosaki@flannep.com][1]



## 主目录:

- [API版本](#API版本)
- [已实现的接口](#已实现的接口)
- [未来更新计划](#未来更新计划)
- [教程目录](#教程目录)
- [更新日志](#更新日志)
- [注意事项和联系方式](#注意事项)


## API版本
```
V2.0.0-beta.1.1
```

## 已实现的接口
- 初始化界面
    - 获取软件最新版本信息
    - 获取公告栏信息
    - 获取首页banner
- 用户信息
    - 登陆 
    - 获取个人用户信息
        - 注册邮箱/性别/名称/生日
        - 登陆IP/注册时间/激活时间
        - 头衔/个人签名/经验值/等级
        - 其他信息...
    - 签到
    - 获取我收藏的本子 
- 获取本子信息
    - 获取搜索热词
    - 获取搜索页本子分类列表
    - 点赞/收藏本子
    - 搜索本子
    - 获取本子详细信息
        - 点赞数/喜欢数/评论数
        - 作者/汉化组/上传时间
        - 标签/分类
        - 章节/图片资源地址
        - 其他信息...
-----

## 未来更新计划

> - [ ] 评论区浏览接口
> - [ ] 评论区评论、点赞接口
> - [ ] 实现PicaException的更多异常类
> - [ ] 返回集实现iterable接口
-----


## 教程目录

- [使用教程](#使用教程)
    - [用户登陆](#用户登陆)
        - [使用PicaLoginApi模拟登陆](#使用PicaLoginApi模拟登陆)
        - [获取authorization用于身份验证](#获取authorization用于身份验证)
    - [PicaHeader(哔咔请求头)](#PicaHeader(哔咔请求头))
        - [什么是哔咔请求头](#什么是哔咔请求头)
        - [构造一个PicaHeader](#构造一个PicaHeader)
        - [使用PicaHeader构造其他的PicaApi](#使用PicaHeader构造其他的PicaApi)
        - [添加/移除请求头](#添加/移除请求头)
    - [PicaResult(哔咔请求结果)](#PicaResult(哔咔请求结果))
        - [请求结果样例](#请求结果样例)
        - [获取错误信息](#获取错误信息)
    - [PicaException](#PicaException)
    - [PicaIndexApi 登陆初始化接口](#PicaIndexApi登陆初始化接口)
        - [获取公告信息](#获取公告信息)
        - [获取横幅广告](#获取横幅广告)
        - [获取最新版本信息](#获取最新版本信息)
    - [PicaSearchApi搜索界面接口](#PicaSearchApi搜索界面接口)
        - [获取搜索热词](#获取搜索热词)
        - [获取本子分类](#获取本子分类)
        - [搜索指定关键词](#搜索指定关键词)
    - [PicaBookApi本子操作接口](#PicaBookApi本子操作接口)
        - [BookSimpleInfo/BookDetailInfo](#BookSimpleInfo/BookDetailInfo)
        - [点赞/收藏本子](#点赞/收藏本子)
        - [获取本子的详细信息](#获取本子的详细信息)
        - [获取本子的章节信息](#获取本子的章节信息)
        - [获取本子的图片资源文件](#获取本子的图片资源文件)
            - [请求图片资源](#请求图片资源)
    - [PicaUserApi用户操作接口](#PicaUserApi用户操作接口)
        - [获取我的个人信息](#获取我的个人信息)
        - [获取其他用户的个人信息](#获取其他用户的个人信息)
        - [获取我收藏的本子](#获取我收藏的本子)
        - [签到](#签到)
    - [PicaCommentApi评论操作接口]
        - [待完成]        
            
            

 <span id="使用教程"></span>
# 使用教程
 <span id="用户登录"></span>
## 用户登陆
由于哔咔绝大部分接口都需要用户登陆后才可使用，在请求前必须获取身份验证字段，供后续请求使用。
 <span id="模拟登陆"></span>
### 使用PicaLoginApi模拟登陆
```java
String username = "username@example.com";
String password = "password";
//构建登陆api
PicaLoginApi lapi = new PicaLoginApi(username, password);
//获取服务端返回的登陆结果
PicaLoginResult loginResult = lapi.login();

```
如果登陆成功，将会返回一个登陆结果对象  
密码错误将会抛出一个PicaException  
网络异常等其他错误将会抛出其他的Exception  
 <span id="身份验证"></span>
### 获取authorization用于身份验证

```java
//如果登陆成功，获取token
loginResult.getToken();
```

获取到的这个字段将用于未来构建请求使用，本字段大约能使用1周，如获取后再次登陆(使用API或使用哔咔客户端)也不会失效。
 <span id="哔咔请求头"></span>
## PicaHeader(哔咔请求头)
 <span id="请求头说明"></span>
### 什么是哔咔请求头
由于哔咔的接口需要大量请求头，所以对请求头进行了封装，以下称为PicaHeader(哔咔请求头)  

PicaHeader基本是每个Api的构造函数参数    

注意：PicaHeader 非线程安全，且可能会被调用方修改，如有其他需求，请使用clone方法获取一个拷贝。

一般来说只需要设定Authorization字段即可，其他字段会由调用方自行设置
 <span id="构建哔咔请求头"></span>
### 构造一个PicaHeader
```java
PicaHeader header = new PicaHeader();
//设定身份验证字段 token为上一章用户登陆所获得的字符串
header.setAuthorization(token);
```
 <span id="使用哔咔请求头构建api"></span>
### 使用PicaHeader构造其他的PicaApi

```java
PicaBookApi bapi = new PicaBookApi(header);
PicaSearchApi sapi = new PicaSearchApi(header);
PicaUserApi uapi = new PicaUserApi(header);
PicaIndexApi iapi = new PicaIndexApi(header);
```
 <span id="添加移除请求头"></span>
### 添加/移除请求头

为了避免未来哔咔请求变化，本请求头提供了两个方法用于自定义操作请求头。
```java
/**
 * 添加自定义的请求头
 * 如果已经定义了，将会覆盖掉内置的请求头
 *
 * @param key
 * @param value
 */
public void setCustomHeader(String key, String value)

/**
 * 移除请求头
 *
 * @param key
 */
public void removeCustomHeader(String key)
```

更多方法及接口说明请参阅源码。  

注：为了不给哔咔官方造成困扰，每个请求头都会额外添加一个识别字段，如果您不愿意添加，请使用以上方法移除。  

|key|value|
|---|---|
|sources|PicaComic-api v2.0.0 beta|


 <span id="哔咔请求结果"></span>
## PicaResult(哔咔请求结果)
本类代表了一个基本的请求结果，所有的请求结果类都应当继承于此类。 
 <span id="请求结果样例"></span> 
### 请求结果样例

以请求用户信息为例（信息已脱敏、删减）

```json
{
	"code": 200,
	"message": "success",
	"data": {
		"user": {
			"_id": "58299sadfsafvqvbqwvqwvqwv",
			"birthday": "1997-06-30T00:00:00.000Z",
			"gender": "bot",
			"name": "神楽めあ",
			"resendCount": 0,
			"forgotCount": 0
		}
	}
}
```

### 获取错误信息

```java
//获取某个请求结果，这里为了直观说明，已经向上转型
PicaResult result = sapi.getCategoryInfo();
//快速判断是否错误
if (result.hasError()) {
    result.getError();
    result.getMessage();
}
```
通常来说，调用方基本无需自行判断请求是否错误。  
获取请求结果时，方法发现错误后会抛出PicaException异常。  
具体请参阅下一章节: PicaException

## PicaException
本类是哔咔请求异常的类，所有哔咔请求异常应当继承于此类。  

捕获错误样例: 

```java
try {
    PicaResult result = sapi.getCategoryInfo();
}catch (PicaException e){
    //获取原始的请求结果
    PicaResult result = e.getResult();
    //获取汉化的错误信息（等待完善）
    e.getErrorMessage();
}
```

未来将会提供更多类型的Exception  



## PicaIndexApi登陆初始化接口

### 获取公告信息

获取第一页公告样例：  

```java
PicaIndexApi iapi = new PicaIndexApi(header);
//获取指定页码的公告
PicaAnnouncementResult annoResult = iapi.getAnnouncement(1);
//获取公告页码信息
PageInfo pageInfo = annoResult.getPageInfo();
//获取当前页码
pageInfo.getPage();
//获取总页码
pageInfo.getPages();
//遍历每条公告
for (Announcement announcement : annoResult.getAnnouncements()) {
    announcement.getTitle();
    announcement.getContent();
}
```

### 获取横幅广告

获取首页上方横幅广告样例:

```java
PicaIndexApi iapi = new PicaIndexApi(header);

for (Banner banner : iapi.getBanner().getBanners()) {
    banner.getTitle();
    banner.getShortDescription();
}
```

### 获取最新版本信息

以获取安卓版信息为例：

```java
//获取最新版本信息，字段可以是 android 或 ios
PicaInitResult initResult = iapi.getInit("android");
//获取最新版本信息
LatestApplication latestInfo = initResult.getLatestApplication();
//获取版本号
latestInfo.getVersion();
//获取下载地址
latestInfo.getDownloadURL();
//获取更新信息
latestInfo.getUpdated_at();
//注意，如果请求的是ios的则不包含此字段
latestInfo.getApk();
```

更多方法及接口说明请参阅源码。  

## PicaSearchApi搜索界面接口

### 获取搜索热词

```java
PicaSearchApi sapi = new PicaSearchApi(header);
//获取热词
List<String> wordList = sapi.getHotKeywords();
//遍历
for (String hotKeyword : wordList) {
    System.out.println(hotKeyword);
}
```
### 获取本子分类

```java
//获取分类信息
PicaCategoryResult categoryInfo = sapi.getCategoryInfo();
//获取分类List
List<Category> categories = categoryInfo.getCategories();
//遍历
for (Category category : categories) {
    //分类名称
    category.getTitle();
    //注意：描述不一定存在
    category.getDescription();
}
```

### 搜索指定关键词

提供两个重载方法
```java
//搜索指定关键词（获取第一页的结果）
search(String keyword);
//搜索指定关键词（指定页码的结果）
search(String keyword,int page);
```

一个遍历搜索结果的样例

```java
PicaSearchApi sapi = new PicaSearchApi(header);
//搜索指定关键词
PicaBooksResult sresult = sapi.search("神楽めあ");
//获取页码信息
PageInfo pageInfo = sresult.getPageInfo();
//遍历搜索结果页
for (int i = 1; i < pageInfo.getPages(); i++) {
    //获取指定页码的本子信息
    sresult = sapi.search("神楽めあ", i);
    //获取本子信息List
    List<BookSimpleInfo> comics = sresult.getComics();
    for (BookSimpleInfo comic : comics) {
        //获取本子名
        comic.getTitle();
        comic.get_id();
        //其他方法请参见源码
    }
}
```

## PicaBookApi本子操作接口

### BookSimpleInfo/BookDetailInfo

本子的信息都封装在这两个类中，其中``BookDetailInfo``是``BookSimpleInfo``的子类
  
通常情况下，这两个信息的出现位置：

BookSimpleInfo:
- 搜索页结果
- 我收藏的本子列表

样例:

```json
{
    "_id": "5c5d3ce58bc6462b894661bc",
    "title": "[神乐mea]虚拟YouTuber杂图",
    "author": "给伊莉雅补魔的人",
    "pagesCount": 18,
    "epsCount": 1,
    "finished": false,
    "categories": [
        "全彩",
        "同人",
        "短篇",
        "CG雜圖"
    ],
    "thumb": {
        "fileServer": "https://storage1.picacomic.com",
        "path": "7a68dd30-f2a7-4892-88df-19add8e73175.jpg",
        "originalName": "-59a398c3eda030db.jpg"
    },
    "id": "5c5d3ce58bc6462b894661bc",
    "likesCount": 1040
}
```

BookDetailInfo:
- 本子的信息页(即点击本子后加载的详细信息)

样例:
```json

{
	"_id": "5c5d3ce58bc6462b894661bc",
	"_creator": {
		"_id": "5a22331586wq1f856wq443ccfaf3",
		"gender": "bot",
		"name": "神楽めあ",
		"slogan": "迷迭迷迭paryi桑",
		"title": "手冲人",
		"verified": false,
		"exp": 17366,
		"level": 13,
		"characters": [
			"knight"],
		"avatar": {
			"fileServer": "https://storage1.picacomic.com",
			"path": "ed1fdd84-a11a-4d97-a86b-868da8dadcea.jpg",
			"originalName": "avatar.jpg"
		},
		"character": "https://www.picacomic.com/characters/frame_knight_50_99.png?r=3"
	},
	"title": "[神乐mea]虚拟YouTuber杂图",
    "description": "mea酱的色图是真的少",
    "thumb": {
        "fileServer": "https://storage1.picacomic.com",
        "path": "7a68dd30-f2a7-4892-88df-19add8e73175.jpg",
        "originalName": "-59a398c3eda030db.jpg"
    },
    "author": "给伊莉雅补魔的人",
    "categories": [
        "全彩",
        "同人",
        "短篇",
        "CG雜圖"
    ],
    "tags": [
        "全彩"
    ],
    "pagesCount": 18,
    "epsCount": 1,
    "finished": false,
    "updated_at": "2019-02-08T08:25:09.002Z",
    "created_at": "2019-02-06T03:36:50.219Z",
    "viewsCount": 109447,
    "likesCount": 1040,
    "isFavourite": true,
    "isLiked": true,
    "commentsCount": 539
}

```
注意：每个本子的**_id**字段用于获取本子的详细信息。

### 点赞/收藏本子

```java
//点赞
PicaActionResult actionResult = bapi.setLike("5c5d3ce58bc6462b894661bc");
actionResult.getAction();
//收藏
bapi.setFavorite("5c5d3ce58bc6462b894661bc");
```
两个方法都会返回一个``PicaActionResult``对象，使用``getAction()``方法可以获取到一个String。

|操作|状态1|状态2|
|----|----|----|
|点赞|like|unlike|
|收藏|favorite|un_favourite|


### 获取本子的详细信息

本子ID的获取方式请参阅: BookSimpleInfo/BookDetailInfo  

有一个重载方法：

```java
//使用本子ID获取
getBookDetailInfo(String bookID);
//也可以使用BookSimpleInfo获取
getBookDetailInfo(BookSimpleInfo info);
```

```java
PicaBookApi bapi = new PicaBookApi(header);
//使用本子ID获取
PicaBookDetailInfoResult detailResult = bapi.getBookDetailInfo("5c5d3ce58bc6462b894661bc");
//从返回的结果中获取信息
BookDetailInfo bookDetailInfo = detailResult.getBookDetailInfo();
//获取汉化组
bookDetailInfo.getChineseTeam();
//获取本子的标签
bookDetailInfo.getTags();
//获取本子介绍
bookDetailInfo.getDescription();
```

更多方法及接口说明请参阅``BookDetailInfo``源码


### 获取本子的章节信息


有一个重载方法:
```java
//获取本子的章节信息(第一页)
getEpisodeInfo(String bookID);
//获取本子章节信息(可指定页码)
getEpisodeInfo(String bookID,int page);
```
页码包含在返回的结果中，截至2019/03/22，一页为40章。

样例: 

```java
PicaBookApi bapi = new PicaBookApi(header);
//获取章节信息
PicaEpisodeInfoResult epiResult = bapi.getEpisodeInfo("5c5d3ce58bc6462b894661bc");
//遍历章节
for (Episode episode : epiResult.getEpisodes()) {
    //获取章节ID，本ID用于获取本子的图片资源信息
    episode.getOrder();
    //获取章节标题
    episode.getTitle();
}
```

### 获取本子的图片资源文件

本子的图片资源被封装在Media对象中

方法:
```java
getBookMedia(String bookID,int episodeID,int page);
```
截至2019/03/22，每一页本子为40张图片。

样例:

```java
PicaBookApi bapi = new PicaBookApi(header);
//获取本子的图片请求结果
PicaBookMediaResult mdResult = bapi.getBookMedia("5c5d3ce58bc6462b894661bc", 1, 1);
//获取当前的章节
mdResult.getEpisode();
//获取当前页的页码信息
mdResult.getPageInfo();
//获取图片List
List<Media> mediaList = mdResult.getMedia();
for (Media md : mediaList) {
    //获取图片的直链地址
    String url = md.getURL();
}
```

注意：URL有访问限制，需要设定哔咔请求头，否则短时间内访问图片超过5张左右服务器会返回403，处理方法请看下一章。

#### 请求图片资源
由于哔咔服务器的限制，需要对哔咔请求头进行再次设置。

首先对于获得的Media对象，需要先设置:
```java
md.setUsingBackupServer(true);
```
注：这里是因为哔咔的请求地址和URL的服务器地址不一致，经测试必须设定服务器为s3.picacomic.com才不会403。

返回的URL将会指向哔咔的资源服务器，然后再设置PicaHeader：
```java
//设定host字段，否则会403
header.setHost(md.getHostHeader());
//设定目标请求地址
header.setTargetURL(md.getURL());
```

推荐重新创建一个请求头

获取图片样例:

```java
for (Media md : mediaList) {
    //重新构造一个请求头
    PicaHeader header = new PicaHeader();
    //设定服务器为备用资源服务器
    md.setUsingBackupServer(true);
    header.setHost(md.getHostHeader());
    header.setTargetURL(md.getURL());
    byte[] file = NetUtil.getByte(header);
    //图片保存操作
}
```

Media对象的更多方法请参阅源码

## PicaUserApi用户操作接口

### 获取我的个人信息

注意：获取自己的个人信息，信息会比获取他人的信息少，如果需要获取详细的信息，请参阅:获取其他用户的个人信息


样例:
```java
PicaUserApi uapi = new PicaUserApi(header);
PicaUserResult userResult = uapi.getMyProfile();
//必须使用此方法
userResult.getUserSimpleInfo();
```

注意：这里的``PicaUserResult``提供了两个方法，**必须**使用``getUserSimpleInfo()``方法，否则对``UserDetailInfo``操作时可能会导致NPE。

返回的对象实际上是一个getter，具体调用方法请参看源码。

获取的数据样例(已脱敏):
```json
{
	"code": 200,
	"message": "success",
	"data": {
			"user": {
			"_id": "xxxxxxxxxxxxxxxxx",
			"birthday": "1990-01-01T00:00:00.000Z",
			"email": "123456789@qq.com",
			"gender": "m",
			"name": "我的ID",
			"slogan": "个人签名",
			"title": "萌新",
			"verified": false,
			"exp": 1234,
			"level": 4,
			"characters": [],
			"created_at": "2016-01-01T01:01:01.000Z",
			"avatar": {
				"originalName": "avatar.jpg",
				"path": "xxxxxxxx-xxxx-aaaa-ssss-xxxxxxxxxxxx.jpg",
				"fileServer": "https://storage1.picacomic.com"
			},
			"isPunched": false
		}
	}
}
```

### 获取其他用户的个人信息

调用本接口需要目标用户的ID，可以是自己的IP


样例:
```java
PicaUserApi uapi = new PicaUserApi(header);
PicaUserResult userResult = uapi.getUserProfile("userid");
UserDetailInfo userDetailInfo = userResult.getUserDetailInfo();
```

获取的数据样例(已脱敏):

```json
{
	"code": 200,
	"message": "success",
	"data": {
		"user": {
			"_id": "582995xxxxxx43585xxxxxxx",
			"birthday": "1999-01-01T00:00:00.000Z",
			"email": "123456789@qq.com",
			"gender": "bot",
			"name": "xx汉化组ww",
			"password": "$2a$08$wA44er0afQjeRInkxxxxxxxxxxxxxxxxJYgLjppzK8j/tAyY2",
			"activation_code": "asdfqwef-e5bb-43db-af07-zxcvbnmkjhgf",
			"activation_date": "2016-11-10T00:00:00.000Z",
			"last_login_date": "2019-01-27T00:00:01.123Z",
			"slogan": "欢迎各位~\n希望各位能多多评论呀！",
			"ip": "123.45.67.89",  
			"title": "简介写的棒棒的大佬",
			"verified": false,
			"exp": 80000,
			"level": 28,
			"updated_at": "2019-01-27T14:21:36.891Z",
			"created_at": "2016-11-11T10:45:40.000Z",
			"avatar": {
				"originalName": "avatar.jpg",
				"path": "qwerasdf-d0ef-4db7-afbe-qwertyuiolkj.jpg",
				"fileServer": "https://storage1.picacomic.com"
			},
			"resendCount": 0,
			"forgotCount": 0,
			"character": "https://www.picacomic.com/characters/frame_knight_500_999.png?r=3"
		}
	}
}
```

注：密码字段已被加密。

### 获取我收藏的本子

方法:
```java
getMyFavoriteBooks(int pageID);
```
页码ID可以先填写1，然后从获取后的数据中取得页码信息再遍历。

```java
PicaUserApi uapi = new PicaUserApi(header);
//以获取第一页为例
PicaBooksResult booksResult = uapi.getMyFavoriteBooks(1);
//获取页码信息
booksResult.getPageInfo();
List<BookSimpleInfo> comicList = booksResult.getComics();
for (BookSimpleInfo simpleInfo : comicList) {
    //获取本子ID
    simpleInfo.get_id();
    simpleInfo.getTitle();
}
```

可以通过PicaBookApi获取本子的详细信息，这里不再赘述。

### 签到

样例:
```java
PicaPunchinResult punchResult = uapi.punchin();
//获取签到状态，成功返回字符串ok，否则返回fail
punchResult.getStatus();
//获取签到状态，成功返回boolean true，否则返回false
punchResult.isSuccess();
//获取最后签到日期，只能在签到成功时获取，否则会null
punchResult.getPunchInLastDay();
```

## PicaCommentApi评论操作接口

待完成...

-----

## 更新日志
> ### 2019-03-28 (V2.0.0-beta.1.1)
>	1.修正了NetUtil中Okhttp client重复的问题
> ### 2019-03-23 (V2.0.0-beta.1.0)
>       1. 重构了接口设计，使用PicaHeader作为构造参数以支持自定义请求头
>       2. 重写了NetUtil的实现方法(GET和POST请求)
>       3. 增加了点赞/喜欢的接口
>       4. 修正了签到接口无效的bug
>### 2019-02-14 (V1.0.1)
>       1. 更新了README排版，增加使用教程和部分样例
>       2. 移动了部分类到其他包
>### 2019-01-31 (V1.0.0)
>       初始化仓库

-----


## 注意事项
本项目仅供学习使用，请勿用于非法用途。  
如有任何问题，欢迎联系: [Shirosaki@flannep.com][1]



  [1]: mailto:shirosaki@flannep.com
