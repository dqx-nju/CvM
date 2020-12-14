# CvM

几个模块

- 应用模块 app
- 输入模块 input
- 音频模块 audio
- 世界模块 world
- 网络模块 net

## 应用模块 app

### Game 类

应用主入口：继承了 javafx 的 Application 类

应用生命周期方法： 
- onLaunch() 
- onFinish() 
- onExit()

### App 类

管理窗口、场景和根面板：
- stage 
- scene 
- root

管理和切换页面：
- viewMap currentView
- getView(String)
- regView(String, View)
- unregView(String)
- getCurrentView()
- gotoView(String)

管理引擎的启动和停止：engine

提供生命周期对象供框架内部实现：
- onLaunch
- onFinish
- onExit

提供生命周期方法供框架内部调用：
- launch()
- finish()

### View 类

持有面板：pane

页面生命周期方法：
- onLaunch() onFinish()
- onEnter() onLeave()
- onStart() onUpdate() onStop()

### Engine 类

控制游戏主循环：timer

获取当前时间：
- nowNanos(nowMillis nowSecs)
- lastNanos(lastMillis lastSecs)
- deltaNanos(deltaMillis deltaSecs)

获取和设置fps:
- fps npf
- getFPS() getNPF() setFPS()

提供方法供框架内部调用:start() stop()


> 应用模块的主要目标：
> - 提供给二次开发应用的基本框架结构
> - 入口类（继承Game）包含各个页面类（继承View）
