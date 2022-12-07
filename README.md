# OplusHideZoomWindow

[中文版](README_zh-rCN.md)

Magic to hide zoom window from screenshots & screen-records.

<img src="gifs/1.gif" width="250" height="445" />

## Compatibility

This is a Xposed module.

Compatible for ColorOS 13 and OxygenOS 13 only.

Only adapted to `Oneplus 8 OxygenOS 13.0.0 F.13` and `Oneplus 8T ColorOS 13.0.0 F.13`. 

**There's no gurantee for any other version or device.**

## Features

- Hide zoom window from screenshots & screen-records.
- Hide edge panel from screenshots & screen-records.
- Hide input method from screenshots & screen-records.
- Hide screenshot preview from screen-records.

## Known bugs

When switching an app between zoom window and fullscreen mode:
- It flickers.
- The app will go around in its lifecycle. (`onPause()`->`onStop()`->`onStart()`->`onResume()`)

All of this won't be fixed as they are technical compromises.

## Download

- [Releases](https://github.com/libxzr/OplusHideZoomWindow/releases)

## Technical details

https://blog.xzr.moe/archives/136/

Sorry but there's no English version :) .

## Notice

This in only a POC. Do not use it in any illegal way.

## LICENSE

- [MIT](LICENSE)
