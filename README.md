

# ADclicker

一个基于 Android 无障碍服务的广告自动跳过应用。

## 简介

ADclicker 是一款帮助用户自动跳过广告的应用。通过 Android 无障碍服务（Accessibility Service），应用可以自动检测并点击跳过按钮，为用户提供更流畅的使用体验。

## 功能特性

- **自动检测广告**：智能识别应用中的广告界面
- **一键跳过**：自动点击跳过按钮，无需手动操作
- **无障碍服务支持**：利用 Android 无障碍服务实现自动化操作
- **简洁界面**：简单易用的用户界面

## 技术架构

- **平台**：Android
- **开发语言**：Java/Kotlin
- **构建工具**：Gradle
- **核心服务**：Android AccessibilityService
- **UI 框架**：XML 布局 + Material Design

## 环境要求

- Android Studio Arctic Fox 或更高版本
- Gradle 7.x 或更高版本
- Android SDK 21 (Android 5.0) 或更高版本
- JDK 11 或更高版本

## 安装说明

1. 克隆或下载本项目到本地
2. 使用 Android Studio 打开项目
3. 连接 Android 设备或启动模拟器
4. 点击 Run 按钮或执行 `./gradlew installDebug` 命令进行安装

## 使用说明

1. 安装完成后，打开应用
2. 按照界面提示启用无障碍服务
3. 开启应用或浏览包含广告的内容时，应用将自动检测并点击跳过按钮

## 权限说明

本应用需要以下权限：
- **无障碍服务权限**：用于检测屏幕上的广告元素并自动执行点击操作
- **存储权限**（如需要）：用于保存相关配置

## 项目结构

```
adclicker/
├── app/
│   ├── src/main/
│   │   ├── java/com/anrola/adclicker/
│   │   │   ├── activity/
│   │   │   │   └── MainActivity.java       # 主界面
│   │   │   └── service/
│   │   │       └── MyAccessibilityService.java  # 无障碍服务
│   │   ├── res/                            # 资源文件
│   │   └── AndroidManifest.xml             # 应用清单
│   └── build.gradle.kts                    # 应用模块配置
├── build.gradle.kts                        # 项目配置
└── settings.gradle.kts                      # 设置文件
```

## 参与贡献

1. Fork 本仓库
2. 新建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 许可证

本项目采用 Apache License 2.0 许可证。

## 注意事项

- 请确保在不使用时及时关闭无障碍服务权限
- 本应用不会收集任何用户数据
- 部分设备可能需要手动允许自启动权限以确保服务正常运行