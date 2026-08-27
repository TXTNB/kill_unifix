# Kill UniFix

绕过网易版《我的世界》UniFix SDK强制更新检测的Xposed模块。

## 功能说明

网易版《我的世界》（包名：com.netease.x19）使用UniFix SDK进行资源更新和热修复，启动时会检测更新并弹出蓝色进度条强制下载。本模块通过替换服务器地址，将更新请求重定向到空服务，从而绕过更新检测，让游戏直接进入主界面。

## Hook逻辑

### 原理

UniFix SDK从服务器获取更新配置，地址为：
unifix.netease.com

模块将此地址替换为：
sekaiproject.netease

该地址返回空配置或无效响应，SDK认为无需更新，从而跳过强制更新流程。

### Hook目标

模块Hook两个关键方法：

1. com.netease.ntunisdk.unifix.UniFixBase.a(Context)
   - SDK主入口，获取服务器地址
   - afterHookedMethod拦截返回值并替换

2. com.netease.ntunisdk.unifix.util.v.b(Context)
   - SDK工具类，备用获取地址方法
   - 同样拦截返回值并替换

### 核心代码

```java
String result = (String) methodHookParam.getResult();
if (result != null) {
    methodHookParam.setResult(
        result.replace("unifix.netease.com", "sekaiproject.netease")
    );
}
```

为什么两个都要Hook

UniFixBase.a() 是主要方法，但部分版本会直接调用 util.v.b() 作为备用。两个都Hook确保在所有情况下都能生效。

执行流程

MC启动 -> UniFix SDK初始化 -> 请求更新配置 -> 地址被替换为无效地址 -> 获取不到更新 -> 跳过蓝条直接进入游戏

系统要求

· Android 5.0 (API 21) 或更高
· LSPosed (推荐) / VirtualXposed / 太极 等Xposed框架

下载安装

从 Releases 页面下载最新APK安装。

或自行编译：

```bash
git clone https://github.com/yourusername/kill.unifix.git
cd kill.unifix
./gradlew assembleDebug
```

使用方法

1. 安装APK
2. 在LSPosed等框架中激活模块
3. 选择目标应用：com.netease.x19（网易版我的世界）
4. 完全退出游戏，重新启动

验证是否生效

· 查看LSPosed日志，确认Hook成功
· 游戏启动后不再显示蓝色更新进度条
· 可直接进入游戏主界面

项目结构

```
kill.unifix/
├── app/
│   ├── libs/
│   │   └── api-82.jar
│   ├── src/main/
│   │   ├── assets/
│   │   │   └── xposed_init
│   │   ├── java/com/txt/kill/unifix/
│   │   │   ├── HookInit.java
│   │   │   └── MainActivity.java
│   │   └── AndroidManifest.xml
│   └── build.gradle
└── README.md
```

注意事项

· 仅对网易版《我的世界》（com.netease.x19）有效
· 绕过更新可能导致游戏不稳定
· 建议在游戏完全退出后激活模块
· 模块更新或游戏更新后可能需要重新适配

免责声明

本模块仅供技术学习研究。绕过更新可能违反游戏用户协议，请自行承担使用风险。请勿用于商业或非法用途。

License

Apache License 2.0
