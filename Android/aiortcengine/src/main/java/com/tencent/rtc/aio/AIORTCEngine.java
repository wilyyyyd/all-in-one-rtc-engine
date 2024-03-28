package com.tencent.rtc.aio;

import com.tencent.trtc.TRTCCloud;

import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.ClientRoleOptions;
import io.agora.rtc2.video.VideoCanvas;
import io.agora.rtc2.video.VideoEncoderConfiguration;

/*
* 需要适配的接口
* */
public abstract class AIORTCEngine {
    /**
     * 加入频道
     * 该方法让用户加入通话频道，加入成功后本地会触发 onJoinChannelSuccess 回调。通信场景下的用户和直播场景下的主播加入频道后，远端会触发 onUserJoined 回调。
     *
     * @param token        用户签名（必填），当前 userId 对应的验证签名，相当于使用云服务的登录密码，用户可以通过官网的方式自己生成，也可以填空字符串，sdk内部自己生成
     * @param channelId    频道名，限制长度为 64 字节。以下为支持的字符集范围（共 89 个字符）:
     *                     - 大小写英文字母（a-zA-Z）；
     *                     - 数字（0-9）；
     *                     - 空格、 ! 、 # 、 $ 、 % 、 & 、 ( 、 ) 、 + 、 - 、 : 、 ; 、 < 、 = 、 . 、 > 、 ? 、 @ 、 [ 、 ] 、 ^ 、 _ 、 { 、 } 、 | 、 ~ 、 , 。
     * @param optionalInfo (非必选项) 预留参数。
     * @param uid          用户 ID。该参数用于标识在实时音视频互动频道中的用户。
     */
    abstract public int joinChannel(String token, String channelId, String optionalInfo, int uid);

    /**
     * 设置媒体选项并进入房间（频道）
     * 相比上一个joinChannelByToken方法，该方法该方法增加了 options 参数，用于配置用户加入频道时是否自动订阅频道内所有远端音视频流。
     *
     * @param token     用户签名（必填），当前 userId 对应的验证签名，相当于使用云服务的登录密码，用户可以通过官网的方式自己生成，也可以填空字符串，sdk内部自己生成
     * @param channelId 频道名，限制长度为 64 字节。以下为支持的字符集范围（共 89 个字符）:
     *                  - 大小写英文字母（a-zA-Z）；
     *                  - 数字（0-9）；
     *                  - 空格、 ! 、 # 、 $ 、 % 、 & 、 ( 、 ) 、 + 、 - 、 : 、 ; 、 < 、 = 、 . 、 > 、 ? 、 @ 、 [ 、 ] 、 ^ 、 _ 、 { 、 } 、 | 、 ~ 、 , 。
     * @param uid       用户标识（必填
     * @param options   频道媒体设置选项。详见 AgoraRtcChannelMediaOptions。
     * @note mediaOptions目前仅生效publishCameraTrack、publishMicrophoneTrack、clientRoleType、autoSubscribeAudio、autoSubscribeVideo、channelProfile，其他设置不生效。
     */
    abstract public int joinChannel(String token, String channelId, int uid, ChannelMediaOptions options);

    /**
     * 离开频道
     * 调用该接口使用户离开当前房间，并释放摄像头、麦克风、扬声器等资源，等资源释放完毕后触发 onLeaveChannel 回调。
     */
    abstract public int leaveChannel();

    /**
     * 设置频道场景
     * SDK 会针对不同的使用场景采用不同的优化策略，以获取最佳的音视频传输体验。
     *
     * @param profile 频道使用场景。详见 AgoraChannelProfile，仅支持AgoraChannelProfileCommunication和AgoraChannelProfileLiveBroadcasting
     * @note 该方法必须在 joinChannel 前调用和进行设置，进入频道后无法再设置。
     */
    abstract public int setChannelProfile(int profile);

    /**
     * 设置用户角色
     * 在加入频道前和加入频道后均可调用该方法设置用户角色。如果你在加入频道后调用该方法切换用户角色，
     * 调用成功后：本地会触发 onClientRoleChanged 回调，远端会触发 onUserJoined 回调。
     *
     * @param role 用户的角色：
     * - CLIENT_ROLE_BROADCASTER (1)：主播
     * - CLIENT_ROLE_AUDIENCE (2)：观众。
     * @note 当您创建的实例为RtcEngine时，远端会触发 onUserJoined 或 onUserOffline(USER_OFFLINE_BECOME_AUDIENCE) 回调。
     */
    abstract public int setClientRole(int role);

    /**
     * 设置直播场景下的用户角色和级别
     * 该方法在加入频道前后均可调用，该方法与 setClientRole [1/2] 的区别在于，该方法还支持设置用户级别。
     * 用户角色（role）确定用户在 SDK 层的权限，包含是否可以发送流、是否可以接收流、是否可以旁路推流等。
     * 用户级别（level）需要与角色结合使用，确定用户在其权限范围内可以享受到的服务。例如对于观众，选择接收低延时还是超低延时的视频流。不同的级别会影响计费。
     *
     * @param role    用户的角色：CLIENT_ROLE_BROADCASTER (1)：主播，CLIENT_ROLE_AUDIENCE (2)：观众。
     * @param options 用户具体设置，包含用户级别。详见 ClientRoleOptions。
     * @note 当您创建的实例为TRTCCloud时，options参数无效。
     */
    abstract public int setClientRole(int role, ClientRoleOptions options);

    /**
     * 取消或恢复订阅所有远端用户的音频流
     * 成功调用该方法后，本地用户会取消或恢复订阅所有远端用户的音频流。
     *
     * @param muted 是否取消订阅所有远端用户的音频流：
     * - true: 取消订阅所有远端用户的音频流.
     * - false:（默认）订阅所有远端用户的音频流。
     */
    abstract public int muteAllRemoteAudioStreams(boolean muted);

    /**
     * 取消或恢复发布本地音频流
     * 成功调用该方法后，远端会触发 onUserMuteAudio 回调和 onRemoteAudioStateChanged 回调。
     *
     * @param muted 是否取消发布本地音频流：
     * - true: 取消发布。
     * - false: 发布。
     */
    abstract public int muteLocalAudioStream(boolean muted);

    /**
     * 取消或恢复订阅指定远端用户的音频流
     *
     * @param uid   指定用户的用户 ID。
     * @param muted 是否取消订阅指定远端用户的音频流：
     * - true: 取消订阅指定用户的音频流。
     * - false:（默认）订阅指定用户的音频流。
     * @note 该方法需要在加入频道后调用。
     */
    abstract public int muteRemoteAudioStream(int uid, boolean muted);

    /**
     * 调节本地播放的指定远端用户信号音量
     *
     * @param uid    要调节的用户ID
     * @param volume 音量范围为 0~100。
     */
    abstract public int adjustUserPlaybackSignalVolume(int uid, int volume);

    /**
     * 调节本地播放的所有远端用户信号音量。
     *
     * @param volume 音量，取值范围为 [0,400]。
     */
    abstract public int adjustPlaybackSignalVolume(int volume);

    /**
     * 关闭音频模块
     *
     * @note 当您创建的实例为RtcEngine时，调用该方法会停止麦克风采集,停止发布本地音频流，停止接收所有远端音频流。
     * 而当您创建创建的实例为TRTCCLoud时，调用该方法后，音频相关接口将不生效。
     */
    abstract public int disableAudio();

    /**
     * 启用音频模块
     *
     * @note 当您创建的实例为RtcEngine时，调用该方法会开始麦克风采集,发布本地音频流，接收并播放所有远端音频流。
     * 而当您创建创建的实例为TRTCCLoud时，调用该方法后，音频相关接口将生效。
     */
    abstract public int enableAudio();

    /**
     * 启用用户音量大小提示。
     *
     * @param interval  回调的触发间隔，单位为毫秒
     *                  * - <= 0: 关闭回调。
     *                  * - > 0: 返回音量提示的间隔，单位为毫秒，最小间隔为 100ms，推荐值：300ms。
     * @param smooth    平滑系数，该参数目前不生效。
     * @param reportVad 是否开启本地人声检测功能，在 enableLocalAudio之前调用才可以生效。
     * @return
     * @note 开启此功能后，engine会按设置的时间间隔触发 reportAudioVolumeIndicationOfSpeakers 回调报告音量信息。
     */
    abstract public int enableAudioVolumeIndication(int interval, int smooth, boolean reportVad);

    /**
     * 设置音频编码属性。
     *
     * @param profile 音频编码属性，包含采样率、码率、编码模式和声道数
     * @note 进房前和开启本地音频采集前调用可生效。
     */
    abstract public int setAudioProfile(int profile);

    /**
     * 调节音频采集信号音量
     *
     * @param volume 音量，取值范围为 [0,400]。
     * @return
     */
    abstract public int adjustRecordingSignalVolume(int volume);

    /**
     * 开启耳返功能。
     *
     * @param enabled 开启/关闭耳返功能：true: 开启耳返功能，false: （默认）关闭耳返功能。
     */
    abstract public int enableInEarMonitoring(boolean enabled);

    /**
     * 开启本地音频的采集和发布
     *
     * @param enabled
     * @Note SDK 默认不开启麦克风，当用户需要发布本地音频时，需要调用该接口开启麦克风采集，并将音频编码并发布到当前的房间中。
     * 开启本地音频的采集和发布后，房间中的其他用户会收到 onUserAudioAvailable(userId, true) 的通知。
     */
    abstract public int enableLocalAudio(boolean enabled);

    /**
     * 设置耳返音量
     *
     * @param volume 音量大小，取值范围为 0 - 400，默认值：400。
     */
    abstract public int setInEarMonitoringVolume(int volume);

    /**
     * 开启或关闭扬声器播放
     *
     * @param enabled 设置是否开启扬声器播放：true: 开启。音频路由为扬声器，false: 关闭。音频路由为听筒。
     */
    abstract public int setEnableSpeakerphone(boolean enabled);

    /**
     * 检查扬声器状态启用状态。
     *
     * @return true: 扬声器已开启，语音会输出到扬声器。
     * false: 扬声器未开启，语音会输出到非扬声器（听筒，耳机等）。
     */
    abstract public boolean isSpeakerphoneEnabled();

    /**
     * 设置默认的音频路由。
     *
     * @param defaultToSpeaker 是否使用扬声器作为默认的音频路由：true: 设置默认音频路由为扬声器，false: 设置默认音频路由为听筒。
     * @note 该方法需要在加入频道前调用。如需在加入频道后切换音频路由，请调用 setEnableSpeakerphone。
     */
    abstract public int setDefaultAudioRoutetoSpeakerphone(boolean defaultToSpeaker);

    /**
     * 启用视频模块
     * 该方法可以在加入频道前或者通话中调用，在加入频道前调用则自动开启视频模块，成功调用该方法后，远端会触发 onRemoteVideoStateChanged 回调。
     *
     * @note 当您创建的实例为RtcEngine时，该方法会启动本地摄像头、发布本地视频流、接收并播放远端视频流。
     * @note 当您创建的实例为TRTCCloud时，调用该方法后，则可以正常使用视频相关接口。
     */
    abstract public int enableVideo();

    /**
     * 关闭视频模块
     * 该方法用于关闭视频模块，可以在加入频道前或者通话中调用，成功调用该方法后，远端会触发 onUserEnableVideo (false) 回调。
     *
     * @note 当您创建的实例为RtcEngine时，该方法会停止摄像头采集，停止发布本地视频流，停止接收远端所有的视频流
     * @note 当您创建的实例为TRTCCloud时，调用该方法后，所有的视频相关接口均不生效。
     */
    abstract public int disableVideo();

    /**
     * 取消或恢复订阅所有远端用户的视频流
     * 成功调用该方法后，本地用户会取消或恢复订阅所有远端用户的视频流，包括在调用该方法后加入频道的用户的视频流。
     *
     * @param muted 是否取消订阅所有远端用户的视频流：true: 取消订阅所有用户的视频流，false:（默认）订阅所有用户的视频流。
     */
    abstract public int muteAllRemoteVideoStreams(boolean muted);

    /**
     * 取消或恢复发布本地视频流。
     * 成功调用该方法后，远端会触发 onUserMuteVideo 回调。
     *
     * @param muted 是否取消发送本地视频流：true: 取消发送本地视频流，false: （默认）发送本地视频流。
     */
    abstract public int muteLocalVideoStream(boolean muted);

    /**
     * 取消或恢复订阅指定远端用户的视频流
     *
     * @param userId 指定用户的用户 ID。
     * @param muted  是否取消订阅指定远端用户的视频流：true: 取消订阅指定用户的视频流，false: （默认）订阅指定用户的视频流。
     */
    abstract public int muteRemoteVideoStream(int userId, boolean muted);

    /**
     * 设置视频编码属性。
     *
     * @param config 用于设置视频编码器的相关参数
     * @return - 0: Success.
     * - < 0: Failure.
     * @note 仅支持dimensions、frameRate、bitrate、minbitrate、orientationMode、 mirrorMode几项配置。
     * bitrate推荐取值：请参见TRTCVideoResolution 在各档位注释的最佳码率，也可以在此基础上适当调高。比如：TRTCVideoResolution_1280_720 对应 1200kbps 的目标码率，您也可以设置为 1500kbps 用来获得更好的观感清晰度。
     * minVideoBitrate推荐取值：您可以通过同时设置 videoBitrate 和 minVideoBitrate 两个参数，用于约束 SDK 对视频码率的调整范围：
     * 如果您追求 弱网络下允许卡顿但要保持清晰 的效果，可以设置 minVideoBitrate 为 videoBitrate 的 60%。
     * 如果您追求 弱网络下允许模糊但要保持流畅 的效果，可以设置 minVideoBitrate 为一个较低的数值（比如 100kbps）。
     * 如果您将 videoBitrate 和 minVideoBitrate 设置为同一个值，等价于关闭 SDK 对视频码率的自适应调节能力。
     */
    abstract public int setVideoEncoderConfiguration(VideoEncoderConfiguration config);

    /**
     * 开启本地摄像头的预览画面
     *
     * @note 在 joinChannel 之前调用此函数，SDK 只会开启摄像头，并一直等到您调用 joinChannel 之后才开始推流。
     * 在 joinChannel 之后调用此函数，SDK 会开启摄像头并自动开始视频推流。
     * 调用该方法前，必须先调用 setupLocalVideo 初始化本地视图。
     */
    abstract public int startPreview();

    /**
     * 停止摄像头预览
     * <p>
     * 调用 startPreview 开启预览后，如果你想关闭本地视频预览，请调用该方法。
     */
    abstract public int stopPreview();

    /**
     * 加入频道后更新频道媒体选项
     *
     * @param options 频道媒体选项，详见 ChannelMediaOptions。
     */
    public abstract int updateChannelMediaOptions(ChannelMediaOptions options);

    /**
     * 设置本地画面的渲染参数
     * <p>
     * After initialzing the local video view, you can call this method to update
     * its rendering mode. It affects only the video view that the local user sees, not the published local video stream.
     *
     * @param renderMode 画面填充模式。
     * @param mirrorMode 画面镜像模式。
     * @note 可设置的参数包括有：填充模式以及左右镜像。
     */
    abstract public int setLocalRenderMode(int renderMode, int mirrorMode);

    /**
     * 设置远端画面的渲染模式
     * 初始化远端用户视图后，你可以调用该方法更新远端用户视图在本地显示时的渲染和镜像模式。该方法只影响本地用户看到的视频画面。
     *
     * @param userId     远端用户 ID。
     * @param renderMode 画面填充模式。
     * @param mirrorMode 画面镜像模式。
     */
    abstract public int setRemoteRenderMode(int userId, int renderMode, int mirrorMode);

    /**
     * 初始化本地视图
     *
     * @param local 本地视频显示属性。详见 VideoCanvas。
     */
    abstract public int setupLocalVideo(VideoCanvas local);

    /**
     * 初始化远端用户视图。
     *
     * @param remote 远端视频显示属性。详见 VideoCanvas。
     */
    abstract public int setupRemoteVideo(VideoCanvas remote);

    /**
     * 选择通话音量模式下的音频路由。
     *
     * @param route 期望使用的音频路由：
     *              -1：系统默认的音频路由。
     *              0：带麦克风的耳机。
     *              1：听筒。
     *              2：不带麦克风的耳机。
     *              3：设备自带的扬声器。
     *              4：（暂不支持）外接的扬声器。
     *              5：蓝牙耳机。
     *              6：USB 设备。
     * @note 当您创建的实例为TRTCCloud时，route 期望使用的音频路由：
     * 0,2：有线耳机播放。
     * 1：听筒。
     * 5：蓝牙耳机。
     * 6：USB设备。
     * 其他值均为：扬声器。
     */
    abstract public int setRouteInCommunicationMode(int route);

    /**
     * 切换前置/后置摄像头。
     */
    abstract public int switchCamera();

    /**
     * TRTC 特有
     */
    abstract public void showDebugView(int showType);

    /**
     * TRTC 特有
     */
    abstract public void setDebugViewMargin(int userId, TRTCCloud.TRTCViewMargin margin);
}
