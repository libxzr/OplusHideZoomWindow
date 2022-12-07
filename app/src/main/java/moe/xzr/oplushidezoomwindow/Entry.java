package moe.xzr.oplushidezoomwindow;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Entry implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if (!"android".equals(lpparam.packageName))
            return;

        // Part 1: Hide it!
        Class<?> windowStateAnimatorClass =
                XposedHelpers.findClass("com.android.server.wm.WindowStateAnimator",
                        lpparam.classLoader);
        XposedHelpers.findAndHookConstructor("com.android.server.wm.WindowSurfaceController",
                lpparam.classLoader, String.class, int.class, int.class,
                windowStateAnimatorClass, int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Object windowStateAnimator = param.args[3];
                        Object windowState =
                                XposedHelpers.getObjectField(windowStateAnimator, "mWin");
                        Object windowStateExt =
                                XposedHelpers.getObjectField(windowState, "mWindowStateExt");
                        int mode = (int) XposedHelpers.callMethod(windowState, "getWindowingMode");

                        boolean isZoomWindow = (boolean) XposedHelpers.callMethod(
                                windowStateExt, "checkIfWindowingModeZoom", mode);
                        String owningPackage =
                                (String) XposedHelpers.callMethod(windowState, "getOwningPackage");
                        String typeName =
                                XposedHelpers.callMethod(windowState, "getWindowTag").toString();

                        if (isZoomWindow ||
                                // Edge panel
                                "com.oplus.appplatform".equals(owningPackage) ||
                                // Edge handle
                                "OplusOSZoomFloatHandleView".equals(typeName) ||
                                // Screenshot preview
                                "com.oplus.screenshot/LongshotCapture".equals(typeName) ||
                                // IME
                                "InputMethod".equals(typeName)) {
                            int flags = (int) param.args[2];
                            flags |= 0x00000040;
                            param.args[2] = flags;
                        }
                    }
                });

        // Part2: Do not render shadow behind zoom window, otherwise it'll still appear.
        XposedHelpers.findAndHookMethod("com.android.server.wm.Task",
                lpparam.classLoader, "getShadowRadius", boolean.class,
                XC_MethodReplacement.returnConstant(0.0f));

        // Part3: Rebuild SurfaceController on window mode changes to make sure Part1 will get
        // applied instantly.
        XposedHelpers.findAndHookMethod("com.android.server.wm.Task",
                lpparam.classLoader, "setWindowingModeInSurfaceTransaction",
                int.class, boolean.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        Object windowState = XposedHelpers.callMethod(
                                param.thisObject, "getTopVisibleAppMainWindow");
                        Object activityRecord = XposedHelpers.callMethod(
                                param.thisObject, "getTopResumedActivity");
                        Object rootWindowContainer = XposedHelpers.getObjectField(
                                param.thisObject, "mRootWindowContainer");
                        XposedHelpers.callMethod(windowState, "destroySurfaceUnchecked");
                        XposedHelpers.callMethod(activityRecord, "stopIfPossible");
                        XposedHelpers.callMethod(
                                rootWindowContainer, "resumeFocusedTasksTopActivities");
                    }
                });
    }
}
