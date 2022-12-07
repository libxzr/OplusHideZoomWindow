# OplusHideZoomWindow

从截图和录屏中隐藏小窗的魔法。

<img src="gifs/1.gif" width="250" height="445" />

## 兼容性

这是一个 Xposed 模块。

仅支持 ColorOS 13 和 氧 OS 13。

仅针对 `Oneplus 8 OxygenOS 13.0.0 F.13` 和 `Oneplus 8T ColorOS 13.0.0 F.13` 适配。

**不保证任何其它机型或其它版本的可用性。**

## 功能

- 从截图和录屏中隐藏小窗。
- 从截图和录屏中隐藏侧边栏。
- 从截图和录屏中隐藏输入法。
- 从录屏中隐藏截图的预览窗。

## 已知问题

当应用在小窗模式和全屏模式之间切换时：
- 界面会闪烁。
- 应用会重走一遍生命周期。（`onPause()`->`onStop()`->`onStart()`->`onResume()`）

所有这些都不会被修复，因为它们是实现功能的技术妥协。

## 下载

- [Releases](https://github.com/libxzr/OplusHideZoomWindow/releases)

## 技术细节

https://blog.xzr.moe/archives/136/

## 注意事项

这仅仅只是一个技术验证，请勿用于**学习通考试作弊**和非法用途。

## 开源许可

- [MIT](LICENSE)
