// Adapted from
// https://github.com/gijoehosaphat/react-native-keep-screen-on

package com.corbt.keepawake;

import android.view.View;
import android.app.Activity;
import android.view.WindowManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;


public class KCKeepAwake extends ReactContextBaseJavaModule {

    public KCKeepAwake(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    private static final String TAG = "KeepAwake";
    
    @Override
    public String getName() {
        return "KCKeepAwake";
    }
    
    @ReactMethod
    public void isLockedScreen(Callback successCallBack){
        Boolean status = false;
        try {
            final Context context = getReactApplicationContext();
            //Detect if lock screen is on
            KeyguardManager myKM = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            if( myKM.inKeyguardRestrictedInputMode()) {
                status = true;
                Log.d(TAG,"*** ASK FOR LOCK SCREEN " + status);
                successCallBack.invoke(status);
            } else {
                status = false;
                Log.d(TAG,"*** ASK FOR LOCK SCREEN " + status);
                successCallBack.invoke(status);
            }

        } catch (Exception e) {
        }
    }


    @ReactMethod
    public void activateAll() {

        //Use when React Native MainActivity is singleInstance android:launchMode="singleInstance"
        final Context context = getReactApplicationContext();
        
        Log.d(TAG,"*** activateAll start");
        String packageName = context.getApplicationContext().getPackageName();
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);

        //launchIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        //launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(launchIntent);
    }
    
    @ReactMethod
    public void deactivateAll() {
        final Activity activity = getCurrentActivity();

        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    activity.getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                    activity.getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
                    activity.getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
                    activity.getWindow().getDecorView().setSystemUiVisibility(
                                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                                    //| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    //| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                    Log.d(TAG, "*** All flags cleared");
                }
            });
        }
    }

    @ReactMethod
    public void activate() {
        final Activity activity = getCurrentActivity();

        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
            });
        }
    }

    @ReactMethod
    public void deactivate() {
        final Activity activity = getCurrentActivity();

        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
            });
        }
    }
}
