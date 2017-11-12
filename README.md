# 陪你 —— 设计说明 #
*Samon Yu*

This application is made by Samon Yu & Lewis Qiu.

The website of this application is [https://github.com/samonysh/PeiNi2.git](https://github.com/samonysh/PeiNi2.git "PeiNi"), which includes the source code.

## 使用须知

请务必在安装时勾选所有权限，当前还未实现动态权限设置。

## 简介 ##

该app已经实现以下功能：

1. 用户登录与注册
2. 由子女发起的配对功能
3. 对父母和子女的录音进行录音与识别（借助科大讯飞的语音识别API）
4. 根据识别结果进行简单的语音操作

> 对于3的解释：
>
> 录音的文件为 query.wav

> 对于4的解释：
>
> 识别结果与操作
>
> | 识别结构包含字符 | 操作      |
> | -------- | ------- |
> | 配对（仅对子女） | 跳转到新建配对 |
> | 个人中心     | 跳转到个人中心 |
> | 退出       | 退出app   |

## For The Future

需要解决的问题：

1. 重构，使之可维护性提高
2. 动态权限的设置
3. 添加语音操作（例如闹钟）
