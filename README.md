#### LoginModule

集成了启动引导模块，登录，注册，忘记密码，邮箱绑定相关功能

#### 功能介绍
- 启动页，引导页
- 手机号注册，登录
- 微信账号注册，登录
- 忘记密码，通过手机号，活体校验，邮箱验证找回密码

#### 安装

在工程根目录的build.gradle文件里添加如下maven地址：

```gradle
allprojects {
    repositories {
        ...
        maven { url 'file:///Users/syl/.m2/repository/' }
        ...
    }
}
```

在项目模块的buile.gradle文件里面加入如下依赖：

```gradle
    compile 'com.heima.iou:hmloginmodulelocal:1.0.0'

```

外部引用：

```gradle
    compile 'com.heima.iou:hmbasebizlocal:1.0.0'
    compile 'com.heima.iou:hmcityselectlocal:1.0.0'
```

#### 使用说明

支持的路由

| 页面 | 路由url | 备注 |
| ------ | ------ | ------ |
| 启动页 | hmiou://m.54jietiao.com/login/launchAdvertisementDetail?url=* |  |
| 引导页 | hmiou://m.54jietiao.com/login/guide |  |
| 登录方式选择页面 | hmiou://m.54jietiao.com/login/selecttype |  |
| 输入手机号码判断手机号是否存在的页面 | hmiou://m.54jietiao.com/login/inputmobile |  |
| 手机号登陆页面 | hmiou://m.54jietiao.com/login/mobilelogin?mobile=* |  |
| 手机号注册页面 | hmiou://m.54jietiao.com/login/register_by_mobile?mobile=* |  |
| 手机号注册页面 | hmiou://m.54jietiao.com/login/register_by_mobile?mobile=* |  |
| 通过微信注册页面 | hmiou://m.54jietiao.com/login/register_by_wx?wx_chat_sn=* |  |

路由文件

```json
{
  "login": [
    {
      "url": "hmiou://m.54jietiao.com/login/launchAdvertisementDetail?url=*",
      "iclass": "",
      "aclass": "com.hm.iou.loginmodule.business.launch.view.LaunchAdvertisementDetailActivity"
    },
    {
      "url": "hmiou://m.54jietiao.com/login/guide",
      "iclass": "",
      "aclass": "com.hm.iou.loginmodule.business.guide.view.GuideActivity"
    },
    {
      "url": "hmiou://m.54jietiao.com/login/selecttype",
      "iclass": "",
      "aclass": "com.hm.iou.loginmodule.business.type.SelectLoginTypeActivity"
    },
    {
      "url": "hmiou://m.54jietiao.com/login/inputmobile",
      "iclass": "",
      "aclass": "com.hm.iou.loginmodule.business.login.view.InputMobileActivity"
    },
    {
      "url": "hmiou://m.54jietiao.com/login/mobilelogin?mobile=*",
      "iclass": "",
      "aclass": "com.hm.iou.loginmodule.business.login.view.MobileLoginActivity"
    },
    {
      "url": "hmiou://m.54jietiao.com/login/bindemail?mobile=*",
      "iclass": "",
      "aclass": "com.hm.iou.loginmodule.business.email.BindEmailActivity"
    },
    {
      "url": "hmiou://m.54jietiao.com/login/find_by_input_mobile",
      "iclass": "",
      "aclass": "com.hm.iou.loginmodule.business.password.view.FindByInputMobileActivity"
    },
    {
      "url": "hmiou://m.54jietiao.com/login/find_by_email?mobile=*&tip_email=*",
      "iclass": "",
      "aclass": "com.hm.iou.loginmodule.business.password.view.FindByEmailActivity"
    },
    {
      "url": "hmiou://m.54jietiao.com/login/find_by_sms?mobile=*",
      "iclass": "",
      "aclass": "com.hm.iou.loginmodule.business.password.view.FindBySMSActivity"
    },
    {
      "url": "hmiou://m.54jietiao.com/login/reset_login_psd?mobile=*",
      "iclass": "",
      "aclass": "com.hm.iou.loginmodule.business.password.view.ResetLoginPsdActivity"
    },
    {
      "url": "hmiou://m.54jietiao.com/login/register_by_mobile?mobile=*",
      "iclass": "",
      "aclass": "com.hm.iou.loginmodule.business.register.view.RegisterByMobileActivity"
    },
    {
      "url": "hmiou://m.54jietiao.com/login/register_by_wx_chat?mobile=*",
      "iclass": "",
      "aclass": "com.hm.iou.loginmodule.business.register.view.RegisterByWXChatActivity"
    },
    {
      "url": "hmiou://m.54jietiao.com/login/customer_service",
      "iclass": "",
      "aclass": "com.hm.iou.loginmodule.business.service.CustomerServiceActivity"
    }
  ]
  }
```

#### 集成说明



#### Author

石磊