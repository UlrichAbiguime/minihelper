android 应用构架说明

com.barfoo.app   全局应用级别
    1. BarfooApp  应用基类
        a. 登录验证标记，登录用户信息
        b. 网络检测
        
    2. UpdateManager   应用版本管理
        a. 版式升级交互
        
    3. SkinManager  应用皮肤管理
        a. 皮肤下载、安装
        b. 皮肤选择应用
        
    4. AppConfig    应用配置(单例)
    
    5. BarfooError 应用程序异常管理
    
    6. AppManager  页面管理
    

com.barfoo.core

    1. ImageManger  图片管理
        a. 下载网络文件
        b. 保存文件到本地SDCARD或缓存(文件管理)
        c. 从缓存或本地读取图片(使用软链)
        d. 异步下载,并执行回调操作
        e. 异步下载，并发布下载进度
        
    2. HttpHelper 网络通信
        a. 单例获取 HttpClient
        b. Cookie 管理操作
        c. 超时配置
        d. Http get|post 通信
        
    3. AsyncRunner 异步交互接口(onReading,onDoing,OnDone,OnError)
        a. 接口定义
        
        
com.barfoo.widget

    1. PullDownListView 下拉刷新分页控件
    2. CustomWebClient  配置图片及连接点击操作
    3. LoadingDialog    模拟进度框
    4. LinkView         带链接的Html 内容显示控件
    5. ActivityViewList activity tab 管理页
    6. ProcessDownLoadImage  带进度的图片显示控件
    7. TreeCheckViewAdp   二级树型多选

    
    

        
        
        
    
