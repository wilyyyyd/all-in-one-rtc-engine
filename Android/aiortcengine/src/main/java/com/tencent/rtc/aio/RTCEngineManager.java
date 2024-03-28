package com.tencent.rtc.aio;

import android.content.Context;
import android.view.SurfaceView;

import com.tencent.rtc.aio.wrapper.AgoraEngineWrapper;
import com.tencent.rtc.aio.wrapper.TRTCCloudWrapper;

import io.agora.rtc2.IRtcEngineEventHandler;


public class RTCEngineManager {

    private static boolean      useTRTC   = true;
    private static AIORTCEngine sInstance = null;

    private RTCEngineManager() {
    }

    public static synchronized AIORTCEngine create(Context context,
                                                   String appId,
                                                   IRtcEngineEventHandler listener,
                                                   boolean enableTRTC) {
        useTRTC = enableTRTC;
        if (sInstance == null) {
            if (useTRTC) {
                sInstance = TRTCCloudWrapper.create(context, appId, listener);
                return sInstance;
            }
            sInstance = AgoraEngineWrapper.create(context, appId, listener);
            return sInstance;
        }
        return sInstance;
    }

    public static synchronized void destroy() {
        if (useTRTC) {
            TRTCCloudWrapper.destroy();
        } else {
            AgoraEngineWrapper.destroy();
        }
        sInstance = null;
    }

    public static synchronized SurfaceView createRendererView(Context context) {
        if (useTRTC) {
            return TRTCCloudWrapper.createRendererView(context);
        } else {
            return AgoraEngineWrapper.createRendererView(context);
        }
    }

}
