package com.tencent.rtc.aio.wrapper;

import static com.tencent.liteav.TXLiteAVCode.ERR_CAMERA_NOT_AUTHORIZED;
import static com.tencent.liteav.TXLiteAVCode.ERR_DISCONNECTED;
import static com.tencent.liteav.TXLiteAVCode.ERR_MIC_NOT_AUTHORIZED;
import static com.tencent.liteav.TXLiteAVCode.ERR_MIC_START_FAIL;
import static com.tencent.liteav.TXLiteAVCode.ERR_MIC_STOP_FAIL;
import static com.tencent.liteav.TXLiteAVCode.ERR_NULL;
import static com.tencent.liteav.TXLiteAVCode.ERR_ROOM_ENTER_FAIL;
import static com.tencent.liteav.TXLiteAVCode.ERR_ROOM_ID_INVALID;
import static com.tencent.liteav.TXLiteAVCode.ERR_SDK_APPID_INVALID;
import static com.tencent.liteav.TXLiteAVCode.ERR_SPEAKER_START_FAIL;
import static com.tencent.liteav.TXLiteAVCode.ERR_SPEAKER_STOP_FAIL;
import static com.tencent.trtc.TRTCCloudDef.TRTCAVStatusChangeReasonBufferingBegin;
import static com.tencent.trtc.TRTCCloudDef.TRTCAVStatusChangeReasonBufferingEnd;
import static com.tencent.trtc.TRTCCloudDef.TRTCAVStatusChangeReasonLocalStarted;
import static com.tencent.trtc.TRTCCloudDef.TRTCAVStatusChangeReasonLocalStopped;
import static com.tencent.trtc.TRTCCloudDef.TRTCAVStatusChangeReasonRemoteStarted;
import static com.tencent.trtc.TRTCCloudDef.TRTCAVStatusChangeReasonRemoteStopped;
import static com.tencent.trtc.TRTCCloudDef.TRTCAVStatusPlaying;
import static com.tencent.trtc.TRTCCloudDef.TRTCAVStatusStopped;
import static com.tencent.trtc.TRTCCloudDef.TRTCRoleAnchor;
import static com.tencent.trtc.TRTCCloudDef.TRTCRoleAudience;
import static com.tencent.trtc.TRTCCloudDef.TRTC_APP_SCENE_LIVE;
import static com.tencent.trtc.TRTCCloudDef.TRTC_APP_SCENE_VIDEOCALL;
import static com.tencent.trtc.TRTCCloudDef.TRTC_AUDIO_QUALITY_DEFAULT;
import static com.tencent.trtc.TRTCCloudDef.TRTC_AUDIO_QUALITY_MUSIC;
import static com.tencent.trtc.TRTCCloudDef.TRTC_AUDIO_QUALITY_SPEECH;
import static com.tencent.trtc.TRTCCloudDef.TRTC_AUDIO_ROUTE_EARPIECE;
import static com.tencent.trtc.TRTCCloudDef.TRTC_AUDIO_ROUTE_SPEAKER;
import static com.tencent.trtc.TRTCCloudDef.TRTC_VIDEO_BUFFER_TYPE_TEXTURE;
import static com.tencent.trtc.TRTCCloudDef.TRTC_VIDEO_PIXEL_FORMAT_Texture_2D;
import static com.tencent.trtc.TRTCCloudDef.TRTC_VIDEO_STREAM_TYPE_BIG;
import static com.tencent.trtc.TRTCCloudDef.TRTC_VIDEO_STREAM_TYPE_SMALL;
import static com.tencent.trtc.TRTCCloudDef.TRTC_VIDEO_STREAM_TYPE_SUB;
import static io.agora.rtc2.Constants.AUDIO_ROUTE_DEFAULT;
import static io.agora.rtc2.Constants.AUDIO_ROUTE_EARPIECE;
import static io.agora.rtc2.Constants.AUDIO_ROUTE_SPEAKERPHONE;
import static io.agora.rtc2.Constants.CAMERA;
import static io.agora.rtc2.Constants.CHANNEL_PROFILE_COMMUNICATION;
import static io.agora.rtc2.Constants.CHANNEL_PROFILE_LIVE_BROADCASTING;
import static io.agora.rtc2.Constants.CLIENT_ROLE_AUDIENCE;
import static io.agora.rtc2.Constants.CLIENT_ROLE_BROADCASTER;
import static io.agora.rtc2.Constants.CONNECTION_STATE_CONNECTED;
import static io.agora.rtc2.Constants.CONNECTION_STATE_CONNECTING;
import static io.agora.rtc2.Constants.CONNECTION_STATE_DISCONNECTED;
import static io.agora.rtc2.Constants.ERR_ADM_STOP_PLAYOUT;
import static io.agora.rtc2.Constants.ERR_CONNECTION_INTERRUPTED;
import static io.agora.rtc2.Constants.ERR_CONNECTION_LOST;
import static io.agora.rtc2.Constants.ERR_FAILED;
import static io.agora.rtc2.Constants.ERR_INVALID_APP_ID;
import static io.agora.rtc2.Constants.ERR_INVALID_CHANNEL_NAME;
import static io.agora.rtc2.Constants.LOCAL_VIDEO_STREAM_REASON_OK;
import static io.agora.rtc2.Constants.LOCAL_VIDEO_STREAM_STATE_CAPTURING;
import static io.agora.rtc2.Constants.RECORD_AUDIO;
import static io.agora.rtc2.Constants.REMOTE_AUDIO_REASON_INTERNAL;
import static io.agora.rtc2.Constants.REMOTE_AUDIO_REASON_LOCAL_MUTED;
import static io.agora.rtc2.Constants.REMOTE_AUDIO_REASON_LOCAL_UNMUTED;
import static io.agora.rtc2.Constants.REMOTE_AUDIO_REASON_NETWORK_CONGESTION;
import static io.agora.rtc2.Constants.REMOTE_AUDIO_REASON_NETWORK_RECOVERY;
import static io.agora.rtc2.Constants.REMOTE_AUDIO_REASON_REMOTE_MUTED;
import static io.agora.rtc2.Constants.REMOTE_AUDIO_REASON_REMOTE_UNMUTED;
import static io.agora.rtc2.Constants.REMOTE_AUDIO_STATE_DECODING;
import static io.agora.rtc2.Constants.REMOTE_AUDIO_STATE_STARTING;
import static io.agora.rtc2.Constants.REMOTE_AUDIO_STATE_STOPPED;
import static io.agora.rtc2.Constants.REMOTE_VIDEO_STATE_REASON_INTERNAL;
import static io.agora.rtc2.Constants.REMOTE_VIDEO_STATE_STARTING;
import static io.agora.rtc2.Constants.REMOTE_VIDEO_STATE_STOPPED;
import static io.agora.rtc2.Constants.USER_OFFLINE_BECOME_AUDIENCE;
import static io.agora.rtc2.Constants.USER_OFFLINE_DROPPED;
import static io.agora.rtc2.Constants.USER_OFFLINE_QUIT;
import static io.agora.rtc2.Constants.VideoSourceType.VIDEO_SOURCE_CAMERA_PRIMARY;
import static io.agora.rtc2.Constants.VideoSourceType.VIDEO_SOURCE_CAMERA_SECONDARY;
import static io.agora.rtc2.Constants.VideoSourceType.VIDEO_SOURCE_SCREEN_PRIMARY;
import static io.agora.rtc2.IRtcEngineEventHandler.ErrorCode.ERR_JOIN_CHANNEL_REJECTED;
import static io.agora.rtc2.IRtcEngineEventHandler.ErrorCode.ERR_OK;
import static io.agora.rtc2.IRtcEngineEventHandler.WarnCode.WARN_INVALID_VIEW;
import static io.agora.rtc2.video.VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_LANDSCAPE;
import static io.agora.rtc2.video.VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;

import com.tencent.liteav.device.TXDeviceManager;
import com.tencent.rtc.aio.AIORTCEngine;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;
import com.tencent.trtc.TRTCStatistics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.ClientRoleOptions;
import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.video.VideoCanvas;
import io.agora.rtc2.video.VideoEncoderConfiguration;

public class TRTCCloudWrapper extends AIORTCEngine {

    private final static String             TAG = TRTCCloudWrapper.class.getSimpleName();

    private static TRTCCloudWrapper         mInstance = null;

    private IRtcEngineEventHandler          mTRTCEngineAdapterListener;

    private TRTCCloud                       mTRTCCloud;

    private TXLivePlayer                    mLivePlayer;

    private int                             mTRTCAppId;

    private int                             mAppScene = TRTC_APP_SCENE_VIDEOCALL;

    private TRTCCloudDef.TRTCParams         mTRTCParams;

    private long                            mJoinRoomTime = 0;

    private long                            mRejoinStartTime = 0;

    private int                             mCurUserRole = CLIENT_ROLE_BROADCASTER;

    private int                             mTargetUserRole = CLIENT_ROLE_AUDIENCE;

    private int                             mAudioQuality = TRTC_AUDIO_QUALITY_DEFAULT;

    private int                             mVideoStreamType = TRTC_VIDEO_STREAM_TYPE_BIG;

    private boolean                         mSpeakerphoneEnableFlag = true;

    private boolean                         mUseFrontCamera = true;

    private boolean                         mIsStartPreview = true;

    private TXCloudVideoView                mLocalVideoView = null;

    private boolean                         mEnableAudioFlag = true;

    private boolean                         mEnableVideoFlag = true;

    private String                          mUserId;

    private int                             mConnectionState = CONNECTION_STATE_DISCONNECTED;

    private IRtcEngineEventHandler.RtcStats mLastStats = new IRtcEngineEventHandler.RtcStats();

    private TRTCCloudWrapper(Context context, String appId, IRtcEngineEventHandler listener) {
        mTRTCCloud = TRTCCloud.sharedInstance(context);
        mLivePlayer = new TXLivePlayer(context);
        mTRTCEngineAdapterListener = listener;
        mTRTCCloud.addListener(mTRTCEventHandler);
        mTRTCAppId = Integer.parseInt(appId);
    }

    public static TRTCCloudWrapper create(Context context, String appId, IRtcEngineEventHandler listener) {
        if (mInstance == null) {
            mInstance = new TRTCCloudWrapper(context, appId, listener);
        }
        return mInstance;
    }

    public static void destroy() {
        if (mInstance != null) {
            mInstance.unInit();
        }
        mInstance = null;
    }

    public void unInit() {
        if (mLivePlayer != null) {
            mLivePlayer.stopPlay(true);
            mLivePlayer.setPlayerView(null);
        }
        mTRTCCloud.addListener(null);
        TRTCCloud.destroySharedInstance();
    }

    public static SurfaceView createRendererView(Context context) {
        return new SurfaceView(context);
    }

    private void apiLog(String msg) {
        Log.i(TAG, "(" + TRTCCloudWrapper.this.hashCode() + ")trtc_adapter_api " + msg);
    }

    private void apiLogError(String msg) {
        Log.e(TAG, "(" + TRTCCloudWrapper.this.hashCode() + ")trtc_adapter_api " + msg);
    }

    public int convertAgoraToTRTCVolume(int volume) {
        double trtcVolume = (double) volume * 0.25;
        return (int)(trtcVolume);
    }

    public int transLeaveReason(int reason) {
        switch (reason) {
            case 0:
                return USER_OFFLINE_QUIT;
            case 1:
                return USER_OFFLINE_DROPPED;
            case 3:
                return USER_OFFLINE_BECOME_AUDIENCE;
            default:
                return -1;
        }
    }

    public int transAgoraAudioRemoteReason(int reason) {
        switch (reason) {
            case TRTCAVStatusChangeReasonBufferingBegin:
                return REMOTE_AUDIO_REASON_NETWORK_CONGESTION;
            case TRTCAVStatusChangeReasonBufferingEnd:
                return REMOTE_AUDIO_REASON_NETWORK_RECOVERY;
            case TRTCAVStatusChangeReasonLocalStarted:
                return REMOTE_AUDIO_REASON_LOCAL_UNMUTED;
            case TRTCAVStatusChangeReasonLocalStopped:
                return REMOTE_AUDIO_REASON_LOCAL_MUTED;
            case TRTCAVStatusChangeReasonRemoteStarted:
                return REMOTE_AUDIO_REASON_REMOTE_UNMUTED;
            case TRTCAVStatusChangeReasonRemoteStopped:
                return REMOTE_AUDIO_REASON_REMOTE_MUTED;
            default:
                return REMOTE_AUDIO_REASON_INTERNAL;
        }
    }

    public int transErrorCode(int agoraErrorCode) {
        switch (agoraErrorCode) {
            case ERR_NULL:
                return ERR_OK;
            case ERR_ROOM_ENTER_FAIL:
                return ERR_JOIN_CHANNEL_REJECTED;
            case ERR_SDK_APPID_INVALID:
                return ERR_INVALID_APP_ID;
            case ERR_ROOM_ID_INVALID:
                return ERR_INVALID_CHANNEL_NAME;
            case ERR_MIC_START_FAIL:
            case ERR_SPEAKER_START_FAIL:
            case ERR_DISCONNECTED:
                return ERR_CONNECTION_INTERRUPTED;
            case ERR_MIC_STOP_FAIL:
            case ERR_SPEAKER_STOP_FAIL:
                return ERR_ADM_STOP_PLAYOUT;
            default:
                return ERR_FAILED;
        }
    }

    public int transCommunicationMode(int route) {
        if (route == 1) {
            return TRTC_AUDIO_ROUTE_EARPIECE;
        }
        return TRTC_AUDIO_ROUTE_SPEAKER;
    }

    public int transToTRTCAudioQuality(int quality) {
        switch (quality) {
            case 1: // SPEECH_STANDARD (1)：Specifies a 32 kHz sampling rate, speech encoding, mono, and a maximum encoding rate of 18 Kbps.
                return TRTC_AUDIO_QUALITY_SPEECH;
            case 2: // MUSIC_STANDARD (2)：Specifies a 48 kHz sampling rate, music encoding, mono, and a maximum encoding rate of 64 Kbps.
                return TRTC_AUDIO_QUALITY_DEFAULT;
            case 4: // MUSIC_HIGH_QUALITY (4)：Specifies a 48 kHz sampling rate, music encoding, mono, and a maximum encoding rate of 96 Kbps.
                return TRTC_AUDIO_QUALITY_MUSIC;
            case 5: // MUSIC_HIGH_QUALITY_STEREO (5)：Specifies a 48 kHz sampling rate, music encoding, two-channel, and a maximum encoding rate of 128 Kbps.
                return TRTC_AUDIO_QUALITY_MUSIC;
            default:
                return TRTC_AUDIO_QUALITY_DEFAULT;
        }
    }

    public int transToTRTCRole(int role) {
        return role == CLIENT_ROLE_BROADCASTER ? TRTCRoleAnchor : TRTCRoleAudience;
    }

    public Constants.VideoSourceType transToAgoraVideoSourceType(int type) {
        switch (type) {
            case TRTC_VIDEO_STREAM_TYPE_BIG:
            case TRTC_VIDEO_STREAM_TYPE_SMALL:
                return VIDEO_SOURCE_CAMERA_PRIMARY;
            default:
                return VIDEO_SOURCE_SCREEN_PRIMARY;
        }
    }

    private boolean checkVideoEnable() {
        if (!mEnableVideoFlag) {
            apiLogError("video is not enable!");
            return false;
        }
        return true;
    }

    private boolean checkAudioEnable() {
        if (!mEnableAudioFlag) {
            apiLogError("audio is not enable!");
            return false;
        }
        return true;
    }


    private final TRTCCloudListener mTRTCEventHandler = new TRTCCloudListener() {
        @Override
        public void onWarning(int warningCode, String warningMsg, Bundle extraInfo) {
            apiLog("onWarning warningCode: " + warningCode + " warningMsg: " + warningMsg);
        }

        @Override
        public void onError(int errCode, String errMsg, Bundle extraInfo) {
            apiLog("onError Error Code :" + errCode + "," + errMsg);
            if (errCode == ERR_CAMERA_NOT_AUTHORIZED || errCode == ERR_MIC_NOT_AUTHORIZED) {
                notifyPermissionError(errCode);
                return;
            }
            int errorCode = transErrorCode(errCode);
            if (errorCode == ERR_CONNECTION_INTERRUPTED) {
                mConnectionState = CONNECTION_STATE_DISCONNECTED;
            }
            mTRTCEngineAdapterListener.onError(errorCode);
        }

        public void notifyPermissionError(int errorCode) {
            int agoraErrorCode = RECORD_AUDIO;
            if (errorCode == ERR_CAMERA_NOT_AUTHORIZED) {
                agoraErrorCode = CAMERA;
            }
            mTRTCEngineAdapterListener.onPermissionError(agoraErrorCode);
        }

        @Override
        public void onEnterRoom(long result) {
            if (result < 0) {
                apiLogError("enter room fail.");
                return;
            }
            apiLog("onEnterRoom:" + result);
            mConnectionState = CONNECTION_STATE_CONNECTED;
            try {
                String channelName = String.valueOf(mTRTCParams.roomId);
                mTRTCEngineAdapterListener.onJoinChannelSuccess(channelName,
                        Integer.parseInt(mTRTCParams.userId), (int) result);
            } catch (NumberFormatException e) {
                apiLogError("uid is not Integer type uid:" + mTRTCParams.userId);
            }
        }

        @Override
        public void onExitRoom(int reason) {
            apiLog("onExitRoom " + reason);
            mTRTCEngineAdapterListener.onLeaveChannel(mLastStats);
        }

        @Override
        public void onUserAudioAvailable(String userId, boolean available) {
            apiLog("onUserAudioAvailable " + userId + ", " + available);
            try {
                mTRTCEngineAdapterListener.onUserMuteAudio(Integer.parseInt(userId), !available);
            } catch (NumberFormatException e) {
                apiLogError("uid is not Integer type uid:" + userId);
            }
        }

        @Override
        public void onSwitchRole(int errCode, String errMsg) {
            apiLog("onSwitchRole11 " + errCode + ", " + errMsg);
            if (errCode == 0) {
                mTRTCEngineAdapterListener.onClientRoleChanged(mCurUserRole, mTargetUserRole, null);
                mCurUserRole = mTargetUserRole;
            } else {
                // The error code does not correspond, Unified processing based on CLIENT_ROLE_CHANGE_FAILED_REQUEST_TIME_OUT(3)。
                mTRTCEngineAdapterListener.onClientRoleChangeFailed(3, mCurUserRole);
            }
        }

        @Override
        public void onAudioRouteChanged(int newRoute, int oldRoute) {
            apiLog("onAudioRouteChanged newRoute: " + newRoute + " oldRoute: " + oldRoute);
            int route;
            switch (newRoute) {
                case TRTCCloudDef.TRTC_AUDIO_ROUTE_SPEAKER:
                    route = AUDIO_ROUTE_SPEAKERPHONE;
                    break;
                case TRTCCloudDef.TRTC_AUDIO_ROUTE_EARPIECE:
                    route = AUDIO_ROUTE_EARPIECE;
                    break;
                default:
                    route = AUDIO_ROUTE_DEFAULT;
                    break;
            }
            mSpeakerphoneEnableFlag = (newRoute == TRTC_AUDIO_ROUTE_SPEAKER);
            mTRTCEngineAdapterListener.onAudioRouteChanged(route);
        }

        @Override
        public void onRemoteUserEnterRoom(String userId) {
            apiLog("onUserEnter userId: " + userId);
            int elapsed = (int) (System.currentTimeMillis() - mJoinRoomTime);
            try {
                mTRTCEngineAdapterListener.onUserJoined(Integer.parseInt(userId), elapsed);
            } catch (NumberFormatException e) {
                apiLogError("uid is not Integer type uid:" + userId);
            }
        }

        @Override
        public void onRemoteUserLeaveRoom(String userId, int reason) {
            apiLog("onUserExit userId: " + userId + " reason: " + reason);
            try {
                mTRTCEngineAdapterListener.onUserOffline(Integer.parseInt(userId), transLeaveReason(reason));
            } catch (NumberFormatException e) {
                apiLogError("uid is not Integer type uid:" + userId);
            }
        }

        @Override
        public void onUserVideoAvailable(String userId, boolean available) {
            apiLog("onUserVideoAvailable userId: " + userId + " available: " + available);
            int elapsed = (int) (System.currentTimeMillis() - mJoinRoomTime);
            int state = available ? REMOTE_VIDEO_STATE_STARTING : REMOTE_VIDEO_STATE_STOPPED;
            try {
                mTRTCEngineAdapterListener.onRemoteVideoStateChanged(Integer.parseInt(userId),
                        state, REMOTE_VIDEO_STATE_REASON_INTERNAL, elapsed);
                mTRTCEngineAdapterListener.onUserMuteVideo(Integer.parseInt(userId), !available);
            } catch (NumberFormatException e) {
                apiLogError("uid is not Integer type uid:" + userId);
            }
        }

        @Override
        public void onUserVoiceVolume(ArrayList<TRTCCloudDef.TRTCVolumeInfo> userVolumes, int totalVolume) {
            int userCount = userVolumes.size();
            int validUserCount = 0;
            if (userCount > 0) {
                IRtcEngineEventHandler.AudioVolumeInfo[] speakers = new IRtcEngineEventHandler.AudioVolumeInfo[userCount];
                for (int i = 0; i < userCount; i++) {
                    String userId = userVolumes.get(i).userId;
                    if (!TextUtils.isEmpty(userId)) {
                        validUserCount++;
                        speakers[i] = new IRtcEngineEventHandler.AudioVolumeInfo();
                        speakers[i].uid = Integer.parseInt(userId);
                        speakers[i].volume = (int) (userVolumes.get(i).volume / 100f * 255);
                    }
                }
                if (validUserCount > 0) {
                    mTRTCEngineAdapterListener.onAudioVolumeIndication(speakers, totalVolume);
                }
            }
        }

        @Override
        public void onStatistics(TRTCStatistics statics) {
            int totalLocalAudioBitrate = 0;
            int totalRemoteAudioBitrate = 0;
            int totalLocalVideoBitrate = 0;
            int totalRemoteVideoBitrate = 0;
            int localSize = statics.localArray.size();
            int remoteSize = statics.remoteArray.size();
            TRTCStatistics.TRTCLocalStatistics localBigStream = null;
            for (int i = 0; i < localSize; i++) {
                totalLocalAudioBitrate += statics.localArray.get(i).audioBitrate;
                totalLocalVideoBitrate += statics.localArray.get(i).videoBitrate;
                if (statics.localArray.get(i).streamType == TRTC_VIDEO_STREAM_TYPE_BIG) {
                    localBigStream = statics.localArray.get(i);
                }
            }
            for (int i = 0; i < remoteSize; i++) {
                totalRemoteAudioBitrate += statics.remoteArray.get(i).audioBitrate;
                totalRemoteVideoBitrate += statics.remoteArray.get(i).videoBitrate;
            }
            if (localSize > 0) {
                reportLocalVideoStats(totalLocalVideoBitrate, localBigStream);
                reportLocalAudioStats(totalLocalAudioBitrate, localBigStream);
            }
            reportRtcStats(totalLocalAudioBitrate, totalRemoteAudioBitrate,
                    totalLocalVideoBitrate, totalRemoteVideoBitrate, statics);
            try {
                for (int i = 0; i < remoteSize; i++) {
                    reportRemoteVideoStats(i, statics);
                    reportRemoteAudioStats(i, statics);
                }
            } catch (NumberFormatException e) {
                apiLogError("uid is not Integer type uid:");
            }
        }

        public void reportRtcStats(int totalLocalAudioBitrate, int totalRemoteAudioBitrate,
                                   int totalLocalVideoBitrate, int totalRemoteVideoBitrate,
                                   TRTCStatistics statics) {
            IRtcEngineEventHandler.RtcStats stats = new IRtcEngineEventHandler.RtcStats();
            stats.totalDuration = (int) (System.currentTimeMillis() - mJoinRoomTime) * 1000;
            stats.cpuAppUsage = statics.appCpu;
            stats.cpuTotalUsage = statics.systemCpu;
            stats.lastmileDelay = statics.rtt;
            stats.rxAudioKBitRate = totalRemoteAudioBitrate;
            stats.txAudioKBitRate = totalLocalAudioBitrate;
            stats.rxVideoKBitRate = totalRemoteVideoBitrate;
            stats.txVideoKBitRate = totalLocalVideoBitrate;
            stats.txKBitRate = totalLocalAudioBitrate + totalLocalVideoBitrate;
            stats.rxKBitRate = totalRemoteAudioBitrate + totalRemoteVideoBitrate;
            stats.rxBytes = (int) statics.receiveBytes;
            stats.txBytes = (int) statics.sendBytes;
            stats.users = statics.remoteArray.size() + 1;
            mLastStats = stats;
            mTRTCEngineAdapterListener.onRtcStats(stats);
        }

        public void reportLocalAudioStats(int totalLocalAudioBitrate, TRTCStatistics.TRTCLocalStatistics statics) {
            if (statics == null) {
                apiLogError("reportLocalAudioStats is null");
                return;
            }
            IRtcEngineEventHandler.LocalAudioStats localAudioStats = new IRtcEngineEventHandler.LocalAudioStats();
            localAudioStats.sentBitrate = totalLocalAudioBitrate;
            localAudioStats.sentSampleRate = statics.audioSampleRate;
            mTRTCEngineAdapterListener.onLocalAudioStats(localAudioStats);
        }

        public void reportLocalVideoStats(int totalLocalVideoBitrate, TRTCStatistics.TRTCLocalStatistics statics) {
            if (statics == null) {
                apiLogError("reportLocalVideoStats is null");
                return;
            }
            IRtcEngineEventHandler.LocalVideoStats localVideoStats = new IRtcEngineEventHandler.LocalVideoStats();
            localVideoStats.encodedFrameWidth = statics.width;
            localVideoStats.encodedFrameHeight = statics.height;
            localVideoStats.sentBitrate = totalLocalVideoBitrate;
            localVideoStats.sentFrameRate = statics.frameRate;
            if (mUserId.length() != 0) {
                localVideoStats.uid = Integer.parseInt(mUserId);
            }
            mTRTCEngineAdapterListener.onLocalVideoStats(VIDEO_SOURCE_CAMERA_PRIMARY, localVideoStats);
        }

        public void reportRemoteAudioStats(int count, TRTCStatistics statics) {
            IRtcEngineEventHandler.RemoteAudioStats remoteAudioStats = new IRtcEngineEventHandler.RemoteAudioStats();
            remoteAudioStats.uid = Integer.parseInt(statics.remoteArray.get(count).userId);
            remoteAudioStats.jitterBufferDelay = statics.remoteArray.get(count).jitterBufferDelay;
            remoteAudioStats.audioLossRate = statics.remoteArray.get(count).audioPacketLoss;
            remoteAudioStats.receivedSampleRate = statics.remoteArray.get(count).audioSampleRate;
            remoteAudioStats.receivedBitrate = statics.remoteArray.get(count).audioBitrate;
            remoteAudioStats.totalFrozenTime = statics.remoteArray.get(count).audioTotalBlockTime;
            remoteAudioStats.frozenRate = statics.remoteArray.get(count).audioBlockRate;
            remoteAudioStats.quality = -1;
            remoteAudioStats.networkTransportDelay = -1;
            remoteAudioStats.numChannels = -1;
            remoteAudioStats.totalActiveTime = -1;
            remoteAudioStats.publishDuration = -1;
            remoteAudioStats.qoeQuality = -1;
            remoteAudioStats.qualityChangedReason = -1;
            remoteAudioStats.mosValue = -1;
            mTRTCEngineAdapterListener.onRemoteAudioStats(remoteAudioStats);
        }

        public void reportRemoteVideoStats(int count, TRTCStatistics statics) {
            IRtcEngineEventHandler.RemoteVideoStats remoteVideoStats = new IRtcEngineEventHandler.RemoteVideoStats();
            remoteVideoStats.uid = Integer.parseInt(statics.remoteArray.get(count).userId);
            remoteVideoStats.width = statics.remoteArray.get(count).width;
            remoteVideoStats.height = statics.remoteArray.get(count).height;
            remoteVideoStats.e2eDelay = statics.remoteArray.get(count).point2PointDelay;
            remoteVideoStats.receivedBitrate = statics.remoteArray.get(count).videoBitrate;
            remoteVideoStats.frameLossRate = statics.remoteArray.get(count).videoPacketLoss;
            remoteVideoStats.rxStreamType = statics.remoteArray.get(count).streamType;
            remoteVideoStats.totalFrozenTime = statics.remoteArray.get(count).videoTotalBlockTime;
            remoteVideoStats.frozenRate = statics.remoteArray.get(count).videoBlockRate;
            remoteVideoStats.publishDuration = -1;
            remoteVideoStats.rendererOutputFrameRate = -1;
            remoteVideoStats.decoderOutputFrameRate = -1;
            remoteVideoStats.packetLossRate = -1;
            remoteVideoStats.totalActiveTime = -1;
            remoteVideoStats.avSyncTimeMs = -1;
            mTRTCEngineAdapterListener.onRemoteVideoStats(remoteVideoStats);
        }

        @Override
        public void onSendFirstLocalAudioFrame() {
            int elapsed = (int) (System.currentTimeMillis() - mJoinRoomTime);
            mTRTCEngineAdapterListener.onFirstLocalAudioFramePublished(elapsed);
        }

        @Override
        public void onSendFirstLocalVideoFrame(int streamType) {
            apiLog("onSendFirstLocalVideoFrame  streamType:" + streamType);
            int elapsed = (int) (System.currentTimeMillis() - mJoinRoomTime);
            mTRTCEngineAdapterListener.onFirstLocalVideoFramePublished(
                    transToAgoraVideoSourceType(streamType), elapsed);
        }

        @Override
        public void onFirstVideoFrame(String userId, int streamType, int width, int height) {
            apiLog("onFirstVideoFrame userId: " + userId
                    + " streamType: " + streamType + " width: " + width + " height: " + height);
            int elapsed = (int) (System.currentTimeMillis() - mJoinRoomTime);
            try {
                if (userId.length() == 0) {
                    mTRTCEngineAdapterListener.onFirstLocalVideoFrame(
                            transToAgoraVideoSourceType(streamType), width, height, elapsed);
                } else {
                    mTRTCEngineAdapterListener.onFirstRemoteVideoFrame(Integer.parseInt(userId), width, height, elapsed);
                }
            } catch (NumberFormatException e) {
                apiLogError("uid is not Integer type uid:" + userId);
            }
        }

        @Override
        public void onConnectionRecovery() {
            apiLog("onConnectionRecovery");
            mConnectionState = CONNECTION_STATE_CONNECTED;
            try {
                String channelName = String.valueOf(mTRTCParams.roomId);
                mTRTCEngineAdapterListener.onRejoinChannelSuccess(channelName, Integer.parseInt(mUserId),
                        (int) (System.currentTimeMillis() - mRejoinStartTime));
            } catch (NumberFormatException e) {
                apiLogError("uid is not Integer type uid:" + mTRTCParams.userId);
            } finally {
                mRejoinStartTime = 0;
            }
        }

        @Override
        public void onUserVideoSizeChanged(String userId, int streamType, int newWidth, int newHeight) {
            apiLog("onUserVideoSizeChanged userId: "
                    + userId + ",streamType: " + streamType + ",newWidth: " + newWidth + ",newHeight: " + newHeight);
            Constants.VideoSourceType sourceType = (streamType == TRTC_VIDEO_STREAM_TYPE_SUB
                    ? VIDEO_SOURCE_CAMERA_SECONDARY : VIDEO_SOURCE_CAMERA_PRIMARY);
            mTRTCEngineAdapterListener.onVideoSizeChanged(sourceType, Integer.parseInt(userId), newWidth, newHeight, 0);
        }

        @Override
        public void onFirstAudioFrame(String userId) {
            apiLog("onFirstAudioFrame userId: " + userId);
            int elapsed = (int) (System.currentTimeMillis() - mJoinRoomTime);
            try {
                if (userId != null) {
                    mTRTCEngineAdapterListener.onRemoteAudioStateChanged(Integer.parseInt(userId),
                            REMOTE_AUDIO_STATE_DECODING, REMOTE_AUDIO_REASON_INTERNAL, elapsed);
                }
            } catch (NumberFormatException e) {
                apiLogError("uid is not Integer type uid:" + userId);
            }
        }

        @Override
        public void onRemoteAudioStatusUpdated(String userId, int status, int reason, Bundle extraInfo) {
            apiLog("onRemoteAudioStatusUpdated userId: " + userId + ",status: " + status + ",reason: " + reason);
            int elapsed = (int) (System.currentTimeMillis() - mJoinRoomTime);
            if (status != TRTCAVStatusPlaying && status != TRTCAVStatusStopped) {
                return;
            }
            int remoteState = status == TRTCAVStatusPlaying ? REMOTE_AUDIO_STATE_STARTING : REMOTE_AUDIO_STATE_STOPPED;
            mTRTCEngineAdapterListener.onRemoteAudioStateChanged(Integer.parseInt(userId),
                    remoteState, transAgoraAudioRemoteReason(reason), elapsed);

        }

        @Override
        public void onTryToReconnect() {
            mConnectionState = CONNECTION_STATE_CONNECTING;
        }

        @Override
        public void onConnectionLost() {
            apiLog("onConnectionLost");
            mTRTCEngineAdapterListener.onError(ERR_CONNECTION_LOST);
            mTRTCEngineAdapterListener.onConnectionLost();
            mRejoinStartTime = System.currentTimeMillis();
        }

        @Override
        public void onCameraDidReady() {
            apiLog("onCameraDidReady");
            mTRTCEngineAdapterListener.onLocalVideoStateChanged(
                    VIDEO_SOURCE_CAMERA_PRIMARY,
                    LOCAL_VIDEO_STREAM_STATE_CAPTURING,
                    LOCAL_VIDEO_STREAM_REASON_OK);
        }
    };

    /**
     * 房间相关接口
     */
    @Override
    public int joinChannel(String token, String channelName, String optionalInfo, int uid) {
        apiLog("joinChannel " + token + ", " + channelName + ", " + optionalInfo + ", " + uid);
        mConnectionState = CONNECTION_STATE_CONNECTING;
        mJoinRoomTime = System.currentTimeMillis();
        mTRTCParams = new TRTCCloudDef.TRTCParams();
        mTRTCParams.userSig = token;
        mTRTCParams.userId = String.valueOf(uid);
        mTRTCParams.sdkAppId = mTRTCAppId;
        mTRTCParams.role = transToTRTCRole(mTargetUserRole);
        mTRTCParams.roomId = Integer.parseInt(channelName);
        mCurUserRole = mTargetUserRole;
        // If the user role is a broadcaster, call the startLocalAudio method.
        if (mCurUserRole == TRTCRoleAnchor && mEnableAudioFlag) {
            mTRTCCloud.startLocalAudio(mAudioQuality);
        }

        mTRTCCloud.enterRoom(mTRTCParams, mAppScene);
        mUserId = String.valueOf(uid);
        return 0;
    }

    @Override
    public int joinChannel(String token, String channelId, int uid, ChannelMediaOptions options) {
        apiLog("joinChannel " + token + ", " + channelId + ", " + options.toString() + ", " + uid);
        muteLocalVideoStream(!options.publishCameraTrack);
        muteLocalAudioStream(!options.publishMicrophoneTrack);
        mTRTCCloud.setDefaultStreamRecvMode(options.autoSubscribeAudio, options.autoSubscribeVideo);
        setChannelProfile(options.channelProfile);
        joinChannel(token, channelId, "", uid);
        setClientRole(options.clientRoleType);
        return 0;
    }

    @Override
    public int leaveChannel() {
        apiLog("leaveChannel");
        mTRTCCloud.exitRoom();
        mUserId = "";
        mIsStartPreview = false;
        mSpeakerphoneEnableFlag = true;
        mLocalVideoView = null;
        mEnableAudioFlag = true;
        mEnableVideoFlag = true;
        mConnectionState = CONNECTION_STATE_DISCONNECTED;
        mTRTCCloud.setLocalVideoProcessListener(TRTC_VIDEO_PIXEL_FORMAT_Texture_2D,
                TRTC_VIDEO_BUFFER_TYPE_TEXTURE, null);
        return 0;
    }

    @Override
    public int setChannelProfile(int profile) {
        apiLog("setChannelProfile " + profile);
        if (profile == CHANNEL_PROFILE_COMMUNICATION) {
            mAppScene = TRTC_APP_SCENE_VIDEOCALL;
        } else if (profile == CHANNEL_PROFILE_LIVE_BROADCASTING) {
            mAppScene = TRTC_APP_SCENE_LIVE;
        } else {
            return -1;
        }
        return 0;
    }

    @Override
    public int setClientRole(int role) {
        apiLog("setClientRole " + role);
        mTargetUserRole = role;
        mTRTCCloud.switchRole(transToTRTCRole(role));
        return 0;
    }

    @Override
    public int setClientRole(int role, ClientRoleOptions options) {
        return setClientRole(role);
    }



    /**
     * 音频相关接口
     */
    @Override
    public int muteAllRemoteAudioStreams(boolean muted) {
        apiLog("muteAllRemoteAudioStreams " + muted);
        if (!checkAudioEnable()) {
            return -1;
        }
        mTRTCCloud.muteAllRemoteAudio(muted);
        return 0;
    }

    @Override
    public int muteLocalAudioStream(boolean muted) {
        apiLog("muteLocalAudioStream " + muted);
        if (!checkAudioEnable()) {
            return -1;
        }
        mTRTCCloud.muteLocalAudio(muted);
        return 0;
    }

    @Override
    public int muteRemoteAudioStream(int uid, boolean muted) {
        apiLog("muteRemoteAudioStream " + uid + ", " + muted);
        if (!checkAudioEnable()) {
            return -1;
        }
        mTRTCCloud.muteRemoteAudio(String.valueOf(uid), muted);
        return 0;
    }

    @Override
    public int adjustUserPlaybackSignalVolume(int uid, int volume) {
        apiLog("adjustUserPlaybackSignalVolume" + ",uid:" + uid + ",volume:" + volume);
        if (!checkAudioEnable()) {
            return -1;
        }
        mTRTCCloud.setRemoteAudioVolume(Integer.toString(uid), volume);
        return 0;
    }

    @Override
    public int disableAudio() {
        apiLog("disableAudio");
        mEnableAudioFlag = true;
        return 0;
    }

    @Override
    public int enableAudio() {
        apiLog("enableAudio");
        mEnableAudioFlag = true;
        return 0;
    }

    @Override
    public int enableAudioVolumeIndication(int interval, int smooth, boolean reportVad) {
        apiLog("enableAudioVolumeIndication"
                + ",interval:" + interval + ",smooth:" + smooth + ",reportVad:" + reportVad);
        if (!checkAudioEnable()) {
            return -1;
        }
        TRTCCloudDef.TRTCAudioVolumeEvaluateParams params = new TRTCCloudDef.TRTCAudioVolumeEvaluateParams();
        params.interval = interval;
        params.enablePitchCalculation = reportVad;
        if (interval <= 0) {
            mTRTCCloud.enableAudioVolumeEvaluation(false, params);
        } else {
            mTRTCCloud.enableAudioVolumeEvaluation(true, params);
        }
        return 0;
    }

    @Override
    public int setAudioProfile(int profile) {
        apiLog("setAudioProfile " + profile);
        if (!checkAudioEnable()) {
            return -1;
        }
        mAudioQuality = transToTRTCAudioQuality(profile);
        return 0;
    }

    @Override
    public int adjustRecordingSignalVolume(int volume) {
        apiLog("adjustRecordingSignalVolume " + volume);
        if (!checkAudioEnable()) {
            return -1;
        }
        int convertVolume = convertAgoraToTRTCVolume(volume);
        mTRTCCloud.getAudioEffectManager().setVoiceCaptureVolume(convertVolume);
        return 0;
    }

    @Override
    public int enableLocalAudio(boolean enabled) {
        apiLog("enableLocalAudio " + enabled);
        if (!checkAudioEnable()) {
            return -1;
        }
        if (enabled) {
            mTRTCCloud.startLocalAudio(mAudioQuality);
        } else {
            mTRTCCloud.stopLocalAudio();
        }
        return 0;
    }

    @Override
    public int adjustPlaybackSignalVolume(int volume) {
        apiLog("adjustPlaybackSignalVolume " + volume);
        if (!checkAudioEnable()) {
            return -1;
        }
        mTRTCCloud.setAudioPlayoutVolume(convertAgoraToTRTCVolume(volume));
        return 0;
    }

    @Override
    public int setVideoEncoderConfiguration(VideoEncoderConfiguration config) {
        if (config == null) {
            apiLogError("setVideoEncoderConfiguration error config is null");
            return -1;
        }
        apiLog("setVideoEncoderConfiguration width: " + config.dimensions.width
                + " height: " + config.dimensions.height + " bitrate: " + config.bitrate
                + " frameRate: " + config.frameRate + " orientationMode: " + config.orientationMode);
        if (!checkVideoEnable()) {
            return -1;
        }
        int adjustWidth = config.dimensions.width;
        int adjustHeight = config.dimensions.height;

        JSONObject jsonFuncParam = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        int tmpBitrate = config.bitrate;

        if (config.orientationMode == ORIENTATION_MODE_FIXED_PORTRAIT) {
            if (config.dimensions.width > config.dimensions.height) {
                adjustWidth = config.dimensions.height;
                adjustHeight = config.dimensions.width;
            }
        }
        if (config.orientationMode == ORIENTATION_MODE_FIXED_LANDSCAPE) {
            if (config.dimensions.width < config.dimensions.height) {
                adjustWidth = config.dimensions.height;
                adjustHeight = config.dimensions.width;
            }
        }

        try {
            jsonParam.put("videoWidth", adjustWidth);
            jsonParam.put("videoHeight", adjustHeight);
            jsonParam.put("videoFps", config.frameRate);
            jsonParam.put("videoBitrate", tmpBitrate);
            jsonParam.put("streamType", 0);

            jsonFuncParam.put("api", "setVideoEncodeParamEx");
            jsonFuncParam.put("params", jsonParam);

        } catch (JSONException e) {
            apiLogError("setVideoEncoderConfiguration err :JSONException");
            return -1;
        }
        mTRTCCloud.callExperimentalAPI(jsonFuncParam.toString());
        return 0;
    }

    /**
     * 音频路由相关接口
     */
    @Override
    public int setDefaultAudioRoutetoSpeakerphone(boolean defaultToSpeaker) {
        apiLog("setEnableSpeakerphone " + defaultToSpeaker);
        if (!checkAudioEnable()) {
            return -1;
        }
        if (defaultToSpeaker) {
            mTRTCCloud.setAudioRoute(TRTCCloudDef.TRTC_AUDIO_ROUTE_SPEAKER);
        } else {
            mTRTCCloud.setAudioRoute(TRTCCloudDef.TRTC_AUDIO_ROUTE_EARPIECE);
        }
        mSpeakerphoneEnableFlag = defaultToSpeaker;
        return 0;
    }

    @Override
    public int setEnableSpeakerphone(boolean enabled) {
        apiLog("setEnableSpeakerphone " + enabled);
        if (!checkAudioEnable()) {
            return -1;
        }
        if (enabled) {
            mTRTCCloud.setAudioRoute(TRTCCloudDef.TRTC_AUDIO_ROUTE_SPEAKER);
        } else {
            mTRTCCloud.setAudioRoute(TRTCCloudDef.TRTC_AUDIO_ROUTE_EARPIECE);
        }
        return 0;
    }

    @Override
    public boolean isSpeakerphoneEnabled() {
        apiLog("isSpeakerphoneEnabled " + mSpeakerphoneEnableFlag);
        return mSpeakerphoneEnableFlag;
    }

    @Override
    public int setRouteInCommunicationMode(int route) {
        apiLog("setRouteInCommunicationMode " + route);
        mTRTCCloud.setAudioRoute(transCommunicationMode(route));
        return 0;
    }

    /**
     * 视频相关
     */
    @Override
    public int muteAllRemoteVideoStreams(boolean muted) {
        apiLog("muteAllRemoteVideoStreams " + muted);
        if (!checkVideoEnable()) {
            return -1;
        }
        mTRTCCloud.muteAllRemoteVideoStreams(muted);
        return 0;
    }

    @Override
    public int muteLocalVideoStream(boolean muted) {
        apiLog("muteLocalVideoStream " + muted);
        if (!checkVideoEnable()) {
            return -1;
        }
        mTRTCCloud.muteLocalVideo(mVideoStreamType, muted);
        return 0;
    }

    @Override
    public int muteRemoteVideoStream(int userId, boolean muted) {
        apiLog("muteRemoteVideoStream " + userId + ", " + muted);
        if (!checkVideoEnable()) {
            return -1;
        }
        mTRTCCloud.muteRemoteVideoStream(String.valueOf(userId), mVideoStreamType, muted);
        return 0;
    }

    @Override
    public int setupRemoteVideo(VideoCanvas remote) {
        apiLog("setupRemoteVideo " + remote);
        if (!checkVideoEnable()) {
            return -1;
        }
        if (remote == null) {
            mTRTCCloud.stopAllRemoteView();
            return 0;
        }
        if (remote.view == null) {
            apiLogError("setupRemoteVideo remote view is null!");
            return -1;
        }
        if (!(remote.view instanceof SurfaceView)) {
            apiLogError("setupRemoteVideo remote view is not SurfaceView!");
            return -1;
        }
        mTRTCCloud.startRemoteView(String.valueOf(remote.uid), mVideoStreamType,
                new TXCloudVideoView((SurfaceView) remote.view));
        return 0;
    }

    @Override
    public int setupLocalVideo(VideoCanvas local) {
        apiLog("setupLocalVideo " + local);
        if (!checkVideoEnable()) {
            return -1;
        }
        if (local == null) {
            return 0;
        }
        if (null == local.view) {
            apiLogError("setupLocalVideo local view is null!");
            mTRTCEngineAdapterListener.onError(WARN_INVALID_VIEW);
            return -1;
        }
        if (!(local.view instanceof SurfaceView)) {
            apiLogError("setupLocalVideo local view is not SurfaceView!");
            return -1;
        }
        mLocalVideoView = new TXCloudVideoView((SurfaceView) local.view);
        return 0;
    }

    @Override
    public int startPreview() {
        apiLog("startPreview");
        if (!checkVideoEnable()) {
            return -1;
        }
        mTRTCCloud.startLocalPreview(mUseFrontCamera, mLocalVideoView);
        mIsStartPreview = true;
        return 0;
    }

    @Override
    public int stopPreview() {
        apiLog("stopPreview");
        if (!checkVideoEnable()) {
            return -1;
        }
        mTRTCCloud.stopLocalPreview();
        mIsStartPreview = false;
        return 0;
    }

    @Override
    public int updateChannelMediaOptions(ChannelMediaOptions options) {
        apiLog("updateChannelMediaOptions");
        muteLocalAudioStream(!options.publishMicrophoneTrack);
        muteLocalVideoStream(!options.publishCameraTrack);
        setClientRole(options.clientRoleType);
        return 0;
    }

    @Override
    public int enableVideo() {
        apiLog("enableVideo");
        mEnableVideoFlag = true;
        return 0;
    }

    @Override
    public int disableVideo() {
        apiLog("disableVideo");
        mEnableVideoFlag = false;
        return 0;
    }

    @Override
    public int setInEarMonitoringVolume(int volume) {
        apiLog("setInEarMonitoringVolume " + volume);
        if (!checkAudioEnable()) {
            return -1;
        }
        mTRTCCloud.getAudioEffectManager().setVoiceEarMonitorVolume(convertAgoraToTRTCVolume(volume));
        return 0;
    }

    @Override
    public int setLocalRenderMode(int renderMode, int mirrorMode) {
        apiLog("setLocalRenderMode, renderMode= " + renderMode + ", mirrorMode= " + mirrorMode);
        if (!checkVideoEnable()) {
            return -1;
        }
        TRTCCloudDef.TRTCRenderParams renderParams = new TRTCCloudDef.TRTCRenderParams();
        if (renderMode == VideoCanvas.RENDER_MODE_FIT) {
            renderParams.fillMode = TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FIT;
        } else {
            renderParams.fillMode = TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FILL;
        }
        switch (mirrorMode) {
            case Constants.VIDEO_MIRROR_MODE_AUTO:
                renderParams.mirrorType = TRTCCloudDef.TRTC_VIDEO_MIRROR_TYPE_AUTO;
                break;
            case Constants.VIDEO_MIRROR_MODE_ENABLED:
                renderParams.mirrorType = TRTCCloudDef.TRTC_VIDEO_MIRROR_TYPE_ENABLE;
                break;
            default:
                renderParams.mirrorType = TRTCCloudDef.TRTC_VIDEO_MIRROR_TYPE_DISABLE;
                break;
        }
        mTRTCCloud.setLocalRenderParams(renderParams);
        return 0;
    }

    @Override
    public int setRemoteRenderMode(int uid, int renderMode, int mirrorMode) {
        apiLog("setRemoteRenderMode " + uid + ", renderMode=" + renderMode + ", mirrorMode=" + mirrorMode);
        if (!checkVideoEnable()) {
            return -1;
        }
        TRTCCloudDef.TRTCRenderParams renderParams = new TRTCCloudDef.TRTCRenderParams();
        if (renderMode == VideoCanvas.RENDER_MODE_FIT) {
            renderParams.fillMode = TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FIT;
        } else {
            renderParams.fillMode = TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FILL;
        }
        switch (mirrorMode) {
            case Constants.VIDEO_MIRROR_MODE_AUTO:
                renderParams.mirrorType = TRTCCloudDef.TRTC_VIDEO_MIRROR_TYPE_AUTO;
                break;
            case Constants.VIDEO_MIRROR_MODE_ENABLED:
                renderParams.mirrorType = TRTCCloudDef.TRTC_VIDEO_MIRROR_TYPE_ENABLE;
                break;
            default:
                renderParams.mirrorType = TRTCCloudDef.TRTC_VIDEO_MIRROR_TYPE_DISABLE;
                break;
        }
        mTRTCCloud.setRemoteRenderParams(String.valueOf(uid), TRTC_VIDEO_STREAM_TYPE_BIG, renderParams);
        return 0;
    }

    @Override
    public int enableInEarMonitoring(boolean enabled) {
        apiLog("enableInEarMonitoring " + enabled);
        if (!checkAudioEnable()) {
            return -1;
        }
        mTRTCCloud.getAudioEffectManager().enableVoiceEarMonitor(enabled);
        return 0;
    }

    @Override
    public int switchCamera() {
        apiLog("switchCamera" + ",is use front camera : " + !mUseFrontCamera);
        mUseFrontCamera = !mUseFrontCamera;

        TXDeviceManager manager = mTRTCCloud.getDeviceManager();
        manager.switchCamera(mUseFrontCamera);
        return 0;
    }

    @Override
    public void showDebugView(int showType) {
        mTRTCCloud.showDebugView(showType);
    }

    @Override
    public void setDebugViewMargin(int userId, TRTCCloud.TRTCViewMargin margin) {
        mTRTCCloud.setDebugViewMargin(String.valueOf(userId), margin);
    }
}
