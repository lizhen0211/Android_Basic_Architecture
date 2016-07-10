目录结构说明：
-src
  |-com.architecture
      |-base                   基础信息
            |-BaseApplication
      |-biz                    业务处理类：提供原子业务接口方法，可按业务分子包
      |-config                 各种配置信息
      |-domains                领域模型：类型常量、枚举
      |-services               service或IntentService扩展类：负责与后台或环境交互
      |-tools                  业务相关工具类
      |-ui
          |-animation          自定义动画组件
          |-base               基础activity
          |-preferences        fragment及preferences组件
          |-receiver           广播接收类
          |-view               业务相关窗口：包含Dialog、Popupwin等
          |-vm                 视图模型
          |-widget             自定义控件
      |-util
          |-database           数据库通用
          |-display            显示通用
          |-file               文件通用
          |-net                网络通过