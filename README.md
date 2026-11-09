# Diplay for Lynk&Co 09 PHEV

**面向领克 09 PHEV 使用场景优化的 CarPlay 分支。**

我基于 [shihabal3amri/DiPlay](https://github.com/shihabal3amri/DiPlay) 的 `v0.2.22` 版本，围绕领克车机的品牌显示、导航播报和较保守的车机交互做了调整，并清理了闲置代码和资源。本仓库的维护目标是 **领克 09 PHEV**；原项目面向比亚迪的车型介绍和实测结论，不代表这个分支的适配范围。

[简体中文](README.md) · [繁體中文](README.zh-TW.md) · [本仓库发布页](https://github.com/chenkaijia113/Diplay-for-Lynk-Co-09-PHEV/releases) · [反馈问题](https://github.com/chenkaijia113/Diplay-for-Lynk-Co-09-PHEV/issues)

## 项目来源

- **直接上游：** [DiPlay / shihabal3amri](https://github.com/shihabal3amri/DiPlay)，对比基线为 [`v0.2.22`](https://github.com/shihabal3amri/DiPlay/tree/v0.2.22)，提交 `28d6002ba00320f0ba745212160b08759e4f60b9`。
- **底层来源：** [xcertplay / shilapi](https://github.com/shilapi/xcertplay)。
- **界面来源：** 上游的首页、设置与网站沿用了 [DiAuto](https://github.com/shihabal3amri/DiAuto) 的设计与相关实现。
- **本分支维护：** [chenkaijia113](https://github.com/chenkaijia113)，针对领克 09 PHEV 的使用需求进行调整。

CarPlay 接收、USB / 无线连接、音视频处理、Siri、设置与诊断等主体能力来自原作者及社区贡献。本分支的工作建立在这些成果之上，保留原有署名与许可证。

## 我在原版基础上做了哪些改进

以下内容根据本仓库源码与上游 `v0.2.22` 的实际差异整理。

| 改动 | 原版行为 | 本分支的调整 |
| --- | --- | --- |
| 领克品牌显示 | 默认车机 OEM 标签为 `BYD` | 改为 `Lynk & Co`；读取到空标签或旧的 `BYD` 标签时使用领克标签，其他自定义标签继续保留。 |
| 导航播报音质 | “更清晰的 USB 导航语音”默认关闭；开启时排除 8 / 16 kHz PCM，但仍提供 Opus 输出 | 扩展为导航与提示音的宽带 PCM 模式，启用时请求 **24 kHz 及以上 PCM**，并从这些输出流中排除 Opus；未保存过该偏好的安装默认启用。 |
| 导航播报音量 | 单独调节范围为 **−20～0 dB**，只能衰减 | 扩展到 **−30～+6 dB**，可降低或适度放大导航播报；PCM 放大时限制到 16 位采样范围，避免数值溢出。 |
| 私有方向盘服务的自动接管 | CarPlay 媒体会话启动 / 结束时，自动绑定 / 解绑吉利私有按键服务 | 移除这两处自动调用，保留通用媒体键处理，采用较保守的接入方式；这项调整不代表新增了领克 09 全套方向盘按键支持。 |
| 返回车机图标与预览 | 使用原版车机图标，默认预览还有单独的占位图 | 替换车机图标资源，让默认图标预览使用实际车机图标，统一显示与预览。 |
| 应用与版本标识 | 默认应用名 `DiPlay`，版本 `0.2.22` / code `41` | 默认应用名改为 `Apple CarPlay`，分支版本标记为 `0.2.22-lynkco-stable.2` / code `44`。 |
| 项目清理 | 存在闲置函数、导入、文案和无引用图片 | 删除已确认无引用的代码与资源，清理生成的安装包及系统杂项；保留构建、测试、兼容性代码和第三方许可证。 |

### 导航音频调整说明

宽带模式只调整导航 / 提示音相关输出的协商格式，未改写音乐、通话或语音识别流的格式。原有明确保存的开关值会保留；修改音质开关会重新连接 CarPlay，如果导航无声，可关闭该选项后重试。

导航音量调节即时生效，不改动音乐和通话的独立音量设置。`+6 dB` 是最大增益，较响的原始音频仍可能因限幅产生失真，建议从 `0 dB` 开始按需调整。

相关实现与测试：

- [领克默认标识与偏好](common/src/main/java/com/shilapi/xcertplay/AirPlayPersistence.kt)
- [导航音频格式协商](shared/src/main/java/com/shilapi/xcertplay/airplay/AirPlayInfoPlist.kt) · [格式协商测试](shared/src/test/java/com/shilapi/xcertplay/airplay/AirPlayInfoPlistTest.kt)
- [导航 PCM 增益](shared/src/main/java/com/shilapi/xcertplay/media/NavigationAudioGain.kt) · [增益与限幅测试](shared/src/test/java/com/shilapi/xcertplay/media/NavigationAudioGainTest.kt)
- [媒体键会话处理](common/src/main/java/com/shilapi/xcertplay/CarPlayMediaKeys.kt)
- [应用名称](mobile/src/main/res/values/strings.xml) · [分支版本](mobile/build.gradle.kts)

## 适配范围与当前状态

**本分支针对领克 09 PHEV 进行维护，不以比亚迪车型为适配目标。** 不同年款、车机硬件、系统固件和 iPhone / iOS 版本仍需分别验证。

仓库保留了上游的 BYD / DiLink、吉利和其他兼容性代码与历史文档，避免破坏原有依赖。这些内容是上游背景资料，并不表示本分支面向这些车型，也不应把其中的实测结论套用到领克 09 PHEV。

尤其需要区分：上游的吉利仪表投屏路径针对特定 `geely_fx11` / `CS11_A4` 车机，并包含领克 03 的验证记录。**这不能作为领克 09 PHEV 的仪表、HUD、车速或电池数据已适配的证明。** 本自述不宣称这些功能已在 09 PHEV 上全部实现。

`stable` 是分支版本名称，不是全车型或全部功能的验证认证。目前已核对源码差异及资源引用；本次整理环境缺少 Java Runtime，尚未完成完整单元测试、Lint 与 APK 构建。仓库中的现有测试用例不等于本分支已通过实车验收。

## 安装与构建

应用安装在 **Android 车机** 上，手机端使用 iPhone。主应用模块为 `:mobile`；`:maphost` 是地图嵌入示例，`:home` 是可选启动器。

当前仓库保存源码，未随初次上传发布 APK。上方的“本仓库发布页”仅指本项目发布入口；原作者的安装包不等同于这个领克分支的构建。

构建要求：JDK 25、Android SDK Platform 37、Build-Tools 36.0.0、NDK 28.2.13676358。完整说明见 [构建文档](docs/BUILD.md)。

```sh
./gradlew :mobile:assembleDebug
```

普通源码构建不包含运行时配件认证素材。需要独立连接 iPhone 的车测构建时，按构建文档通过 `DIPLAY_AUTH_ASSETS_DIR` 显式提供外部素材，再执行 `:mobile:assembleStandaloneDebug`；认证素材和签名密钥不进入仓库。

包名沿用上游：release 为 `com.shihab.diplay`，debug 为 `com.shihab.diplay.hudtest`。默认应用名的变化不会改变包名，也不代表可与相同包名的上游版本同时安装。

## 反馈与文档

请在[本仓库 Issues](https://github.com/chenkaijia113/Diplay-for-Lynk-Co-09-PHEV/issues)反馈领克 09 PHEV 分支的问题，附上车辆年款、车机系统 / 固件、iPhone 与 iOS 版本、连接方式、应用版本和复现步骤。可从“设置 → 诊断”导出报告，分享前检查其中是否有个人信息。

以下是保留的上游资料，涉及具体车型时请以文档注明的验证范围为准：

- [构建与车测打包](docs/BUILD.md)
- [连接与安装](docs/INSTALL.md)
- [兼容性记录](docs/COMPATIBILITY.md)
- [隐私与诊断](docs/PRIVACY.md)
- [上游版本说明](docs/RELEASE-NOTES-0.2.22.md)
- [上游验证记录](docs/VALIDATION.md)

## 致谢与许可

感谢 DiPlay 作者 **shihabal3amri**、xcertplay 作者 **shilapi**、DiAuto 作者及所有协议研究与兼容性贡献者。

底层代码沿用仓库中的 [GPL-3.0 许可证](LICENSE)；来自 DiAuto 的相关界面与网站代码保留 [AGPL-3.0 声明](docs/licenses/DiAuto-AGPL-3.0.txt)。第三方组件及素材分别遵循各自许可，详见[完整署名与许可说明](docs/THIRD_PARTY_NOTICES.md)。

本项目为独立社区修改版本，与领克 / 吉利或 Apple 无官方隶属或背书关系，未经 Apple CarPlay / MFi 官方认证。CarPlay 名称与相关图标属于 Apple Inc.。
