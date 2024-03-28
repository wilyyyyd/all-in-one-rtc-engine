package com.tencent.rtc.aio.wrapper;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import com.tencent.rtc.aio.AIORTCEngine;
import com.tencent.trtc.TRTCCloud;

import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.ClientRoleOptions;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.video.VideoCanvas;
import io.agora.rtc2.video.VideoEncoderConfiguration;

public class AgoraEngineWrapper extends AIORTCEngine {
    private final static String TAG = AgoraEngineWrapper.class.getSimpleName();

    private static AgoraEngineWrapper sInstance = null;

    private RtcEngine              mARtcEngine = null;

    private AgoraEngineWrapper(Context context, String appId, IRtcEngineEventHandler listener) {
        try {
            mARtcEngine = RtcEngine.create(context, appId, listener);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    public static AgoraEngineWrapper create(Context context, String appId, IRtcEngineEventHandler listener) {
        if (sInstance == null) {
            sInstance = new AgoraEngineWrapper(context, appId, listener);
        }
        return sInstance;
    }

    public static void destroy() {
        if (sInstance != null) {
            sInstance.uninit();
        }
        sInstance = null;
    }

    public void uninit() {
        RtcEngine.destroy();
        mARtcEngine = null;
    }

    public static SurfaceView createRendererView(Context context) {
        Object view;
        view = new SurfaceView(context);
        ((SurfaceView) view).setVisibility(View.VISIBLE);
        return (SurfaceView) view;
    }

    @Override
    public int joinChannel(String token, String channelId, String optionalInfo, int uid) {
        return mARtcEngine.joinChannel(token, channelId, optionalInfo, uid);
    }

    @Override
    public int joinChannel(String token, String channelId, int uid, ChannelMediaOptions options) {
        return mARtcEngine.joinChannel(token, channelId, uid, options);
    }

    @Override
    public int adjustPlaybackSignalVolume(int volume) {
        return mARtcEngine.adjustPlaybackSignalVolume(volume);
    }

    @Override
    public int adjustRecordingSignalVolume(int volume) {
        return mARtcEngine.adjustRecordingSignalVolume(volume);
    }

    @Override
    public int setEnableSpeakerphone(boolean enabled) {
        return mARtcEngine.setEnableSpeakerphone(enabled);
    }

    @Override
    public int leaveChannel() {
        return mARtcEngine.leaveChannel();
    }

    @Override
    public int muteLocalAudioStream(boolean muted) {
        return mARtcEngine.muteLocalAudioStream(muted);
    }

    @Override
    public int muteRemoteAudioStream(int uid, boolean muted) {
        return mARtcEngine.muteRemoteAudioStream(uid, muted);
    }

    @Override
    public int setClientRole(int role) {
        return mARtcEngine.setClientRole(role);
    }

    @Override
    public int setClientRole(int role, ClientRoleOptions options) {
        return setClientRole(role);
    }

    @Override
    public int setChannelProfile(int profile) {
        return mARtcEngine.setChannelProfile(profile);
    }

    @Override
    public int enableLocalAudio(boolean enabled) {
        return mARtcEngine.enableLocalAudio(enabled);
    }

    @Override
    public int disableAudio() {
        return mARtcEngine.disableAudio();
    }

    @Override
    public int setDefaultAudioRoutetoSpeakerphone(boolean defaultToSpeaker) {
        return mARtcEngine.setDefaultAudioRoutetoSpeakerphone(defaultToSpeaker);
    }

    @Override
    public int setRouteInCommunicationMode(int route) {
        return mARtcEngine.setRouteInCommunicationMode(route);
    }

    @Override
    public boolean isSpeakerphoneEnabled() {
        return mARtcEngine.isSpeakerphoneEnabled();
    }

    @Override
    public int muteAllRemoteAudioStreams(boolean muted) {
        return mARtcEngine.muteAllRemoteAudioStreams(muted);
    }

    @Override
    public int setVideoEncoderConfiguration(VideoEncoderConfiguration config) {
        return mARtcEngine.setVideoEncoderConfiguration(config);
    }

    @Override
    public int setupRemoteVideo(VideoCanvas remote) {
        return mARtcEngine.setupRemoteVideo(remote);
    }

    @Override
    public int muteRemoteVideoStream(int userId, boolean muted) {
        return mARtcEngine.muteRemoteVideoStream(userId, muted);
    }

    @Override
    public int muteLocalVideoStream(boolean muted) {
        return mARtcEngine.muteLocalVideoStream(muted);
    }

    @Override
    public int setupLocalVideo(VideoCanvas local) {
        return mARtcEngine.setupLocalVideo(local);
    }

    @Override
    public int startPreview() {
        return mARtcEngine.startPreview();
    }

    @Override
    public int stopPreview() {
        return mARtcEngine.stopPreview();
    }

    @Override
    public int updateChannelMediaOptions(ChannelMediaOptions options) {
        return mARtcEngine.updateChannelMediaOptions(options);
    }

    @Override
    public int enableVideo() {
        return mARtcEngine.enableVideo();
    }

    @Override
    public int disableVideo() {
        return mARtcEngine.disableVideo();
    }

    @Override
    public int enableAudio() {
        return mARtcEngine.enableVideo();
    }

    @Override
    public int adjustUserPlaybackSignalVolume(int uid, int volume) {
        return mARtcEngine.adjustUserPlaybackSignalVolume(uid, volume);
    }

    @Override
    public int enableAudioVolumeIndication(int interval, int smooth, boolean reportVad) {
        return mARtcEngine.enableAudioVolumeIndication(interval, smooth, reportVad);
    }

    @Override
    public int muteAllRemoteVideoStreams(boolean muted) {
        return mARtcEngine.muteAllRemoteVideoStreams(muted);
    }

    @Override
    public int setInEarMonitoringVolume(int volume) {
        return mARtcEngine.setInEarMonitoringVolume(volume);
    }

    @Override
    public int setLocalRenderMode(int renderMode, int mirrorMode) {
        return mARtcEngine.setLocalRenderMode(renderMode, mirrorMode);
    }

    @Override
    public int setRemoteRenderMode(int userId, int renderMode, int mirrorMode) {
        return mARtcEngine.setRemoteRenderMode(userId, renderMode, mirrorMode);
    }

    @Override
    public int enableInEarMonitoring(boolean enabled) {
        return mARtcEngine.enableInEarMonitoring(enabled);
    }

    @Override
    public int setAudioProfile(int profile) {
        return mARtcEngine.setAudioProfile(profile);
    }

    @Override
    public int switchCamera() {
        return mARtcEngine.switchCamera();
    }

    @Override
    public void showDebugView(int showType) {
        return ;
    }

    @Override
    public void setDebugViewMargin(int userId, TRTCCloud.TRTCViewMargin margin) {
        return;
    }
}
