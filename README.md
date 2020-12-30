# CvM 葫芦娃大战妖精

## 游戏介绍



## 使用说明

### 1、联机对战



### 2、回放 log



##模块介绍

- app 应用模块：整个应用的控制
- view 页面模块：游戏页面
- net 网络模块：服务器与客户端通信
- world 世界模块：游戏数据实体
- input 输入模块：键盘、鼠标等输入
- output 输出模块：战斗数据保存

### 1、应用模块 app

#### Game 类

继承了 javafx 的 Application 类，封装了一些应用生命周期方法： 

- onLaunch() 
- onFinish() 
- onExit()

#### App 类

- 管理窗口、场景和根面板：

- 管理和切换页面：
  - viewMap currentView
  - getView(String)
  - regView(String, View)
  - unregView(String)
  - getCurrentView()
  - gotoView(String)

- 管理引擎的启动和停止：engine

- 提供生命周期对象供框架内部实现

- 提供生命周期方法供框架内部调用

#### View 类

持有面板：pane

页面生命周期方法：

- onLaunch() onFinish()
- onEnter() onLeave()
- onStart() onUpdate() onStop()

#### Engine 类

- 控制游戏主循环

- 设置时间
- 设置 fps



### 2、页面模块 view

-  class HomeView：游戏客户端启动后的主页
  - 连接服务器
  - 开始游戏
  - 结束游戏
  - 按 L 后读取文件回放 
- class PlayView：联机对战的游戏界面
  - 控制角色图片的移动
  - 控制角色血量变化
  - 显示部分对战信息
  - 显示角色技能参数
- class FilePlayView：在 PlayView 的基础上删去交互功能，仅用于回放
- class ServerView：负责服务器端的连接、发送、接收和处理



### 3、世界模块 world



### 4、网络模块 net

#### Msg 接口

应用层信息接口

- send(DatagramSocket ds, String IP, int UDP_PORT)：发送信息
- parse(DataInputStream dis)：解析信息并处理

#### 服务器向客户端发送的信息

- class START_MSG：每一局的开始向双方发送开局信息

- class S_MOVE_MSG：向双方发送请求移动的结果

- class BLOOD_MSG：向双方发送攻击后血量变化信息
- class INFORM_MSG：用于同步服务器与客户端的对战数据
- class DEAD_MSG：向双方发送游戏结束信息

#### 客户端向服务器发送的信息

- class FINISH_MSG：客户端结束该局后向服务器发送结束信息
- class MOVE_MSG：角色请求移动
- class ATTACK_MSG：角色请求发起攻击

- class NetClient：客户端类，负责连接、发送、接收和处理



### 5、输入模块 input

#### Key 枚举类

包含常用键盘按键的键值

- 数字 0-9
- 字母 A-Z
- 功能键 F1-F12
- 常用按键：上下左右SPACE\ENTER\ESC

#### KeyInput 类

封装了按键按下、保持、释放的监听函数

- boolean isPressed(Key)
- boolean isReleased(Key)
- boolean isHeld(Key)



### 6、输出模块 output

#### PlayFile 类

- List\<String\> list：存储对战信息，实时更新
- addStatement(String)：添加信息到 list
- save_file(String)：保存所有信息到本地文件