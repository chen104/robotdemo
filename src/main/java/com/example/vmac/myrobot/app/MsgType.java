package com.example.vmac.myrobot.app;

/**
 * 和平板通信的消息类型
 *
 * @author xiaowei
 */
public class MsgType {
        // 平板接受rk3288的数据格式： 80 。。。数据(第一个字节是0x80，数据位是从第9个字节开始)

        // 平板发送给rk3288的数据格式：00 80 60 60 数据长度 (給mcu) 数据(数据位是从第9个字节开始)
        // 数据=消息类型+数据类型(byte or String)+数据长度(给3288) + 内容


        /**
         * 数据类型
         */

        public static  final  int CUSTOM=9000;
        public static  final  int MACHINE=9001;

        // 平板接受的消息编号---- 1--- 8399
        // 平板发送的消息编号---- 8400-65535


        /**
         * 消息类型----接受语音识别的结果--同时是结束录音的消息
         */
        public static final int RECEIVER_MSG_VOICE_RECOGNIZER_RESULT = 1;

        /**
         * 消息类型---接受rk3288音频播放结束
         */
        public static final int RECEIVER_MSG_PLAY_SOUND_END = 2;

        /**
         * 消息----接受rk3288是否更新app的请求，
         */
        public static final int RECEIVER_MSG_UPDATE_INFO = 3;

        /**
         * 消息----接受rk3288更新app的进度，
         */
        public static final int RECEIVER_MSG_UPDATE_PROGRESS = 4;

        /**
         * 消息----接受rk3288有更新固件的请求，
         */
        public static final int RECEIVER_MSG_UPDATE_FIREWARE = 5;

        /**
         * 消息----收到唤醒的消息，
         */
        public static final int RECEIVER_MSG_WAKE_UP = 6;

        /**
         * 自定义语音成功
         */
        public static final int RECEIVER_CUSTOM_SUCCESS = 7;

        /**
         * 自定义语音失败
         */
        public static final int RECEIVER_CUSTOM_FAIL = 8;

        /**
         * 退出识别，进入待唤醒时--pad跳转到唤醒界面
         */
        public static final int RECEIVER_MSG_SUSPEND = 9;


        /**
         * 感应器信息
         */
        public static final int RECEIVER_MSG_SENSOR = 12;


        /**
         * 手势识别结果,如石头剪刀布.
         */
        public static final int RECEIVER_Msg_Finger_RESULT = 13;

        /**
         * 切换平板相关的界面
         */
        public static final int RECEIVER_MSG_SWITCH_ACTIVITY = 14;

        public static final int RECEIVER_GUID_SHOW_PHOTO = 15;

        /**
         * 接收系统设置信息到pad
         */
        public static final int RECEIVER_MSG_SYSTEM_INFO = 16;

        /**
         * 接收人脸识别结果
         */
        public static final int RECEIVER_MSG_FACE_RECOGNIZE_RESULT = 17;


        /**
         * 启动平板的activity
         */
        public static final int RECEIVER_MSG_START_ACTIVITY = 18;

        /**
         * 销毁平板的activity
         * 参数 ： pkgName +ClassName
         * hehui add
         */
        public static final int RECEIVER_MSG_FINISH_ACTIVITY = 29;

        /**
         * 接收考勤注册人脸识别信息
         */
        public static final int RECEIVER_ATTENDANCE_REGISTER_PHOTO_RESULT = 25;

        /**
         * 消息类型--接收进入考勤
         */
        public static final int RECEIVER_ATTENDANCE_RECOGNIZING = 32;


        /**
         * 头部开机成功指令
         */
        public static final int ON_SYSTEM_BOOT = 39;


        /**
         * 消息类型---播放音频
         */
        public static final int PLAY_SOUND = 8400;

        /**
         * 消息类型---播放TTS
         */
        public static final int PLAY_TTS = 8401;

        /**
         * 打开地图
         */
        public static final int OPEN_MAP = 8007;

        /**
         * 关闭地图
         */

        public static final int CLOSE_MAP = 8008;

        /**
         * 消息类型--健康数据
         */
        public static final int SMARTHOME_HEALTH_DATA = 8402;

        /**
         * 消息类型--开始声控
         */
        public static final int START_RECOGNIZER = 8403;

        /**
         * 消息类型--取消声控
         */
        public static final int STOP_RECOGNIZER = 8404;

        /**
         * 消息类型--设置wifi密码
         */
        public static final int WIFI_PASSWORD = 8405;

        /**
         * pad 可以 请求 3288 进行更新请求，3288 返回是否需要更新。
         */
        public static final int UPDATE_APP = 8406;

        /**
         * 消息类型--停止滑动声音SeekBar时的进度
         */
        public static final int VOICE_PROGRESS = 8407;

        /**
         * 消息类型--停止滑动亮度SeekBar时的进度
         */
        public static final int LIGHT_PROGRESS = 8408;

        /**
         * 消息类型--用户确认升级固件
         */
        public static final int COMFIRM_UPDATE_FIREWARE = 8409;

        /**
         * 消息类型--自定义声控对话
         */
        public static final int CUSTOM_DIALOGUE = 8410;

        /**
         * 消息类型--自定义开机语音
         */
        public static final int CUSTOM_START_VOICE = 8411;

        /**
         * 消息类型--自定义感应反馈
         */
        public static final int CUSTOM_SENSOR_VOICE = 8412;

        /**
         * 消息类型--自定义待机语
         */
        public static final int CUSTOM_STANDBY_VOICE = 8413;

        /**
         * 消息类型--删除自定义聊聊天-可能是file or string
         */
        public static final int DELETE_DIALOGUE = 8414;

        /**
         * 消息类型--删除待机语-可能是file or string
         */
        public static final int DELETE_STANDBY_VOICE = 8415;

        /**
         * 消息类型--设置开机语 或 感应器 为 系统自带提示语or 自定义提示语
         */
        public static final int Setting_Custom_Voice = 8416;

        /**
         * 消息类型--进入触控模式--有pad控制
         */
        public static final int ACCESS_TOUCH_MODE = 8418;

        /**
         * 消息类型--进入声控模式--有pad控制
         */
        public static final int ACCESS_VOICE_MODE = 8419;

        /**
         * 发送动作指令
         */
        public static final int ACTION = 8420;

        /**
         * 发送眼睛指令
         */
        public static final int EYE_MOTION = 8422;

        /**
         * 消息类型--识别结果
         */
        public static final int FACE_REGONGNIZE_RESULT = 8423;

        /**
         * * 消息类型--蜻蜓FM
         */
        public static final int QINGTINGFM = 8425;


        /**
         * 消息类型--心跳
         */
        public static final int HEARTBEAT = 8427;


//        public static final int FILE_TYPE = 8427;


        /**
         * 消息类型--pad 端 关闭小app ，此时声控识别结果交给3288主控程序来处理.
         */
        public static final int CLOSE_PAD_APP = 8428;

        /**
         * 消息类型--pad 端打开小app,此时声控识别结果由这个小app来处理，同时也不能唤醒.
         */
        public static final int OPEN_PAD_APP = 8429;

        /**
         * 消息类型--发送停止音频播放
         */
        public static final int STOP_SOUND = 8430;

        /**
         * 消息类型--根据路径显示图片
         */
        public static final int SHOW_PHOTO = 8431;

        /**
         * 消息类型--获取系统信息
         */
        public static final int GET_SYSTEM_INFO = 8432;

        /**
         * hehui add
         * 暂时没有使用
         * 消息类型--启动activity
         */
        public static final int SEND_START_ACTIVITY = 8433;


        /**
         * 消息类型--打开娱乐模式下的具体应用
         */
        public static final int RECEIVER_OPEN_ENTERTAINMENT_APP = 8435;

        /**
         * 消息类型--娱乐模式开启信息
         */
        public static final int SEND_ENTERTAINMENT_MODE_ON = 8440;

        /**
         * 消息类型--娱乐模式关闭信息
         */
        public static final int SEND_ENTERTAINMENT_MODE_OFF = 8441;

        /**
         * 蓝牙先关以85开头
         */
        public static final int SEND_BLUETOOTH_PAIR_DEVICE = 8501;//欲匹配蓝牙设备

        /**
         * 消息类型--拍照
         */
        public static final int SEND_TAKE_PHOTO_START = 8442;

        /**
         * 消息类型--接收拍照结果
         */
        public static final int RECEIVER_PICTURE_RESULT = 19;

        /**
         * 消息类型--快速注册照片结果
         */
        public static final int RECEIVER_MSG_QUICK_REGISTER_RESULT = 24;

        /**
         * 接收声控是否快速注册
         */
        public static final int RECEIVER_IS_QUICK_REGISTER = 8450;

        /**
         * 消息类型--发送确定快速注册
         */
        public static final int SEND_CONFIRM_QUICK_REGISTER = 8451;

        /**
         * 消息类型--发送取消快速注册
         */
        public static final int SEND_CANCLE_QUICK_REGISTER = 8452;

        /**
         * 消息类型--接收快速注册结果
         */
        public static final int RECEIVER_QUICK_REGISTER_RESULT = 8453;

        /**
         * 消息类型--接收快速注册无法拍照
         */
        public static final int RECEIVER_TAKE_PHOTO_FAILED = 8454;

        /**
         * 消息类型--发送退出考勤模式
         */
        public static final int SEND_EXIT_ATTENDANCE_SESSION = 8455;

        /**
         * 消息类型--发送快速注册询问姓名
         */
        public static final int SEND_QUICK_REGISTER_ENTER_NAME = 8456;

        /**
         * 消息类型---播放拼接语音 (tts + 音频)
         */
        public static final int PLAY_LINK_SOUND = 8459;

        /**
         * 消息类型---可以播放人体感应音频.
         */
        public static final int ENABLE_PLAY_BODY_SENSOR_SOUND = 8460;


        /**
         * 消息类型---不可以播放人体感应音频.
         */
        public static final int DISABLE_PLAY_BODY_SENSOR_SOUND = 8461;


        /**
         * 迎宾模式消息 333 开头
         *
         */
        /**
         * 迎宾模式开始命令
         */
        public static final int SEND_PAD_MEETING_START = 3330;

        /**
         * 迎宾模式欢迎语设置命令
         */
        public static final int SEND_3288_MEETING_SETTING = 3331;

        /**
         * 迎宾模式欢迎文字设置命令
         */
        public static final int SEND_3288_MEETING_VIDEO_PLAYING = 3332;

        /**
         * 迎宾模式播放tts
         */
        public static final int SEND_3288_MEETING_TTS_PLAYING = 3333;

        /**
         * 迎宾模式欢迎语音设置命令
         */
        public static final int SEND_3288_MEETING_VIDEO_SETTING = 3334;

        /**
         * 迎宾模式欢迎横幅设置命令
         */
        public static final int SEND_3288_MEETING_WELCONME_TEXT = 3335;

        /**
         * 迎宾模式欢迎横幅文字上传
         */
        public static final int SEND_3288_MEETING_WELCONME_TEXT_INFO = 3336;

        /**
         * 迎宾模式欢迎语类型
         */
        public static final int SEND_3288_MEETING_WELCONME_TYPE = 3337;

        /**
         * 迎宾模式结束命令
         */
        public static final int SEND_3288_MEETING_EXIT = 3338;

        /**
         * 迎宾模式播放tts
         */
        public static final int SEND_3288_MEETING_WELCOME_TTS_INFO = 3339;

        /**
         * 迎宾模式未设置
         */
        public static final int RECEIVER_3288_MEETING_NOSET = 3340;

        /**
         * 迎宾模式未设置
         */
        public static final int RECEIVER_3288_MEETING_HASSET = 3341;

        /**
         * 迎宾模式未设置
         */
        public static final int SEND_3288_MEETING_CHECK_DATA = 3342;

        /**
         * 迎宾模式启动
         */
        public static final int RECEIVER_3288_MEETING_STAR_AVTIBITY = 3344;


        /**
         * 主持人模式----切换执行模式
         */
        public static final int RECIVER_PAD_PRESENTER_SWITCH_MODE = 3361;

        /**
         * 主持人模式----执行下一步
         */
        public static final int RECIVER_PAD_PRESENTER_EXECUTE_NEXT = 3362;

        /**
         * 主持人模式----开始,需要传入任务的名字
         */
        public static final int RECIVER_PAD_PRESENTER_EXECUTE_START = 3363;

        /**
         * 主持人模式----取消整个任务的执行
         */
        public static final int RECIVER_PAD_PRESENTER_EXECUTE_CANCEL = 3364;

        /**
         * 主持人模式启动
         */
        public static final int RECEIVER_3288_PRESIDE_STAR_AVTIBITY = 3366;

        /**
         * 主持人模式启动
         */
        public static final int SEND_3288_PRESIDE_STAR_AVTIBITY = 3365;

        /**
         * 主持人模式----暂停任务中当前执行的模块
         */
        public static final int RECIVER_PAD_PRESENTER_EXECUTE_PAUSE = 3367;


        /**
         * 主持人模式退出
         */
        public static final int RECEIVER_3288_PRESIDE_EXIT = 3370;

        /**
         * 导购模式----发送pad进入导购模式界面
         */
        public static final int SEND_PAD_SHOPPING_MODE_ENTER = 3371;

        /**
         * 导购模式----接收首次引导index     值为 跳转到的页面(图片)的index
         */
        public static final int RECIVER_MSG_GUIDE_ACTIVITY = 3372;

        /**
         * 导购模式----发送首次引导界面点击index
         */
        public static final int SEND_MSG_GUIDE_ACTIVITY = 3373;

        /**
         * 导购模式----发送平板销毁回到的状态
         */
        public static final int SEND_MSG_STATE = 3374;

        /**
         * 导购模式----接收 活动内容为空
         */
        public static final int RECIVER_MSG_ACTIVITY_IS_EMPTY = 3375;

        /**
         * 导购模式----发送问答、活动、售卖的编辑操作
         */
        public static final int SEND_MSG_EDIT_DATA = 3376;

        /**
         * 主持人模式 执行任务
         */
        public static final int RECIVE_PAD_PRESENTER_RUNTASK = 3379;

        /**
         * 导购模式---发送活动执行消息  数据格式 “id:name”
         */
        public static final int SEND_MSG_ACTIVITY_PLAYING = 3377;

        /**
         * 导购模式---发送售卖执行消息
         */
        public static final int SEND_MSG_SELL_PLAYING = 3378;

        /**
         * 导购模式---接收活动执行消息  数据格式 “name”
         */
        public static final int RECIVER_MSG_ACTIVITY_PLAYING = 3381;

        /**
         * 导购模式---接收售卖执行消息
         */
        public static final int RECIVER_MSG_SELL_PLAYING = 3380;

        /**
         * 导购模式---接收退出导购模式
         */
        public static final int RECIVER_MSG_EXIT_SHOPPING_GUIDE_MODE = 3382;

        /**
         * 导购模式---导购模式获取所有问答数据
         */
        public static final int SHOPPING_GUIDE_GET_QA_DATA = 3383;

        /**
         * 打开人脸识别
         */
        public static final int RECIVER_OPEN_FACE_RECOGNIZE = 8600;

        /**
         * 打开考勤模式
         */
        public static final int RECEIVE_OPEN_ATTENDANCE = 8601;

        /**
         * 退出考勤模式
         */
        public static final int RECEIVE_CLOSE_ATTENDANCE = 8602;

        /**
         * 退出人脸识别
         */
        public static final int RECEIVE_CLOSE_FACE_RECOGNIZE = 8603;

        /**
         * 人脸追踪开始
         */
        public static final int SEND_FACE_TRACK_START = 8604;

        /**
         * 人脸追踪结束
         */
        public static final int SEND_FACE_TRACK_STOP = 8605;

        /**
         * 人脸追踪位置
         */
        public static final int SEND_FACE_TRACK_WHERE = 8606;

        /**
         * 考勤中进行签退
         */
        public static final int RECEIVE_ATTENDANCE_LEAVE_EARLY = 8607;

        /**
         * 打开快速注册
         */
        public static final int OPEN_FACE_QUICK_REGIST = 8608;

        /**
         * 控制头部舞蹈
         */
        public static final int SEND_HEAD_DANCE_MUSIC = 8609;

        /**
         * 退出快速注册
         */
        public static final int CLOSE_QUICK_REGIST = 8610;


        /**
         * 导购模式启动子功能首页
         */
        public static final int SHOPPING_MODE_START_ID = 8612;

        /**
         * 声控打开路线指引主界面
         */
        public static final int RECEIVE_PAD_ROUTE_START = 8701;


        /**
         * 声控打开具体路线指引界面
         */
        public static final int RECEIVE_PAD_ROUTE_CONTENT_START = 8702;


        /**
         * 接受平板界面退出
         */
        public static final int SEND_8288_ROUTE_CLODES = 8703;

        /**
         * 发送消息给3288打开了路线详情界面
         */
        public static final int SEND_8288_ROUTE_CONTENT_START = 8704;

        /**
         * 匹配到咨询问答问题时，咨询问答的id
         */
        public static final int ADVISORY_ID = 8613;

        /**
         * 匹配到商品导购名称时，商品的id
         */
        public static final int MERCHANDISE_ID = 8615;

        /**
         * 考勤语音播放完成消息类型
         */
        public static final int RECEIVE_ATTENDANCE_PLAY_END = 8616;

        /**
         * 执行导览任务
         */
        public static final int MAP_GUIDE_RUN_TASK = 8617;

        /**
         * 去某个点
         */
        public static final int MAP_GUIDE_MOVE2_POINT = 8618;

        public static final int RECEIVE_PAD_START_DANCE_PERFORM = 8619;

        /**
         * 商品导购了解详情，商品的id
         */
        public static final int MERCHANDISE_DETAILS = 8620;

        /**
         * 启动某个舞蹈
         */
        public static final int SEND_PAD_START_ONE_DANCE_PERFORM = 8621;

        /**
         * slam 启动成功
         */
        public static final int SLAM_OK = 8623;

        /**
         * 平板设置促销状态
         */
        public static final int SALE_EMOTION_STATE = 8622;

        /**
         * 贵宾引导是否播放自定义欢迎语
         */
        public static final int SEND_PAD_START_PLAYING_WELCOME = 8630;

        /**
         * 贵宾引导是否播放名字
         */
        public static final int SEND_PAD_START_PLAYING_NAME = 8631;

        /**
         * 贵宾指引进入模式
         */
        public static final int SEND_PAD_VIP_ENTER = 8720;

        /**
         * 接收声控平板vip退出
         */
        public static final int SEND_PAD_VIP_EXIT = 8633;

        /**
         * 消息类型--发送退出贵宾引导模式
         */
        public static final int SEND_EXIT_VIPGUIDE_SESSION = 8634;

        /**
         * 消息类型--无任务结束音频
         */
        public static final int SEND_EXIT_VIPGUIDE_PLAYING = 8638;


        /**
         * 消息类型--发送贵宾引导模式下的跳舞指令
         */
        public static final int SEND_EXIT_VIPGUIDE_PLAY_SOUND = 8639;

        /**
         * 消息类型--发送贵宾引导模式下的随机动作指令
         */
        public static final int SEND_EXIT_VIPGUIDE_ACTION = 8700;

        /**
         * 停止地图任务
         */
        public static final int SEND_STOP_MAP_TASK = 8624;

        /**
         * 退出导览界面
         */
        public static final int RECEIVE_GUIDE_CLOSE = 8625;

        /**
         * 进入导览模式
         */
        public static final int RECEIVE_MAP_ENTER_GUIDE_MODE = 8626;

        /**
         * 进入办公模式
         */
        public static final int RECEIVE_3288_START_OFFICE_MODE = 8640;

        /**
         * 导览模式启动子功能首页
         */
        public static final int GUIDE_MODE_START_ID = 8627;

        /**
         * 关闭路线内容界面
         */
        public static final int RECEIVE_3288_CLOSE_ROUTE_CONTENT = 8641;

        /**
         * 三方组网相关.
         * 控制3288断开连接胸口的热点
         */
        public static final int SEND_3288_BROKEN_NETWORK = 8653;

        /**
         * 三方组网相关.
         * 接收手持平板发送过来的指令，让胸口连接机器人热点
         */
        public static final int RECEIVE_PAD_CONN_HOTSPOT = 8660;

        /**
         * 三方组网相关.
         * 接受手持平板发送过来的指令，让胸口断开与机器人热点的连接。
         */
        public static final int RECEIVE_PAD_BROKEN_NETWORK = 8661;

        /**
         * 三方组网相关.
         * 手持pad发送的指令，手持平板已经三方联网成功，
         * 通知胸口打开三方组网成功的界面 ，并且播放音频.
         */
        public static final int RECEIVE_PAD_CONN_SUCCESS = 8672;

        /**
         * 手持导览模式设置任务
         */
        public static final int MOBILE_PAD_MAP_TASK = 8662;

        /**
         * 触控导览模式进入
         */
        public static final int SEND_PAD_MAP_MODE_ENTER = 8663;

        /**
         * 触控导览模式退出
         */
        public static final int EXIT_GUIDE_MODE = 8664;

        /**
         * 手持平板版本更新
         */
        public static final int SEND_PAD_VERSION_UPDAT = 8670;

        /**
         * 接受手持发送过来的动作舞蹈指令
         */
        public static final int RECEIVE_PAD_EXECUTE_ACTION = 8665;

        /**
         * 接受去出生点的指令
         */
        public static final int RECEIVER_MAP_MOVE_TO_INIT_POINT = 8821;

        /*
        * 收到头部传过来关机命令
        * */
        public static final int RECEIVE_SHUT_DOWN = 34;

        /*
        * 收到头部传过来关机确认指令
        * */
        public static final int RECEIVE_SHUT_DOWN_CONFIRM = 35;

        /*
        * 收到头部传过来关机取消指令
        * */
        public static final int RECEIVE_SHUT_DOWN_CANCLE = 36;

        /**
         * 向头部发送关机确认命令
         */
        public static final int SEND_SHUT_DOWN_CONFIRM = 8665;

        /**
         * 向头部发送关机取消命令
         */
        public static final int SEND_SHUT_DOWN_CANCLE = 8666;

        /**
         * 移动到地图点  参数位置点名
         */
        public static final int MOVE_TO_MAP_POINT = 8822;

        /**
         * 移动到地图点 参数人物名点
         */
        public static final int MOVE_TO_MAP_PERSON = 8823;

        /**
         * 删除地图
         */
        public static final int SEND_PAD_DELETE_MAP = 8824;

        /**
         * 地图重命名
         */
        public static final int SEND_PAD_RENAME_MAP = 8825;

        /**
         * 声控启动地图导览任务列表界面
         */
        public static final int SEND_MAP_POINT_HASMANYTASK = 8826;

        /**
         * 声控启动地图任务执行界面
         */
        public static final int SEND_MAP_POINT_RUNTASK = 8827;

        /**
         * 声控启动地图任务点执行界面
         */
        public static final int SEND_MAP_POINT_RUNPOINTER = 8828;

        /**
         * 加载地图
         */
        public static final int SEND_MAP_PAD_LOAD_MAP = 8829;

        /**
         * 接受抵达地图点时通知 参数任务点名
         */
        public static final int REACH_MAP_POINT = 8830;

        /**
         * 声控启动地图导览任务点列表界面
         */
        public static final int SEND_MAP_POINT_CANGOTO = 8831;

        /**
         * 地图扫描过程 每隔一分钟开始播放音频   开始指令OPEN  结束CLOSE
         */
        public static final int PLAY_SCAN_MAP_GUIDE_VOICE = 8632;

        /**
         * 接受导览任务  暂停指令
         */
        public static final int SEND_PAD_MAP_GUIDE_PAUSE = 8635;

        /**
         * 接受导览任务  继续指令
         */
        public static final int SEND_PAD_MAP_GUIDE_CONTINUE = 8636;

        /**
         * 接受导览任务  下一步指令
         */
        public static final int SEND_PAD_MAP_GUIDE_NEXT = 8637;
//    /**
//     * 与手持网络连接
//     */
//    public static final int CONNECT_HEARTBEAT = 8671;

        /**
         * 向头部发送电量查询消息
         */
        public static final int SEND_QUERY_POWER_INFO = 8667;

        /*
        * 收到头部传过来电量信息
        * */
        public static final int RECEIVE_POWER_INFO = 37;

        /**
         * 发送停止动作和舞蹈指令到胸口平板
         */
        public static final int SEND_PAD_STOP_ACTION = 8668;


        //收到头部传来显示电量界面指令
        public static final int RECEIVE_SHOW_POWER_INFO = 38;

        /**
         * 考勤陌生人
         */
        public static final int ATTENDANCE_STRANGER = 40;

        /**
         * 收到充电状态改变信息
         */
        public static final int RECEIVE_CHARGING_STATE_CHANGE = 41;

        /**
         * 获取头下发的时间
         */
        public static final int RECEIVE_MSG_CURRENT_TIME = 8870;

        /**
         * 获取当前头部 wifi 状态及对应的 ssid
         */
        public static final int RECEIVER_MSG_WIFI_STATUS_SSID = 8880;

        /**
         * 通知头部下发当前 wifi 状态及对应的 ssid
         */
        public static final int SEND_MSG_WIFI_STATUS_SSID = 8881;

        /**
         * 接收头部当前连接的 wifi 的 ssid 和 password
         */
        public static final int RECEIVE_MSG_WIFI_SSID_AND_PWD = 8882;

        /**
         * 发送 mac address 到头部
         */
        public static final int SEND_MSG_MAC_ADDRESS = 8883;

        /**
         * 接收消息，发送 MacAddress 到头部
         */
        public static final int RECEIVER_MSG_SEND_MAC_ADDRESS = 8884;

        /**
         * 接收 双向视频 手机端 MacAddress
         */
        public static final int RECEIVER_MSG_MOBILE_MAC_ADDRESS = 8885;

        /**
         * 通知头部发送 Ip 给胸口
         */
        public static final int SEND_MSG_GET_HEAD_IP = 8886;

        /**
         * 接收头部 Ip
         */
        public static final int RECEIVER_MSG_HEAD_IP = 8887;

        /**
         * 进入物体识别
         */
        public static final int OPEN_OBJECT_RECOGNITION = 8673;

        /**
         * 关闭物体识别
         */
        public static final int CLOSE_OBJECT_RECOGNITION = 8674;

        /**
         * 物体识别结果
         */
        public static final int OBJECT_RECOGNITION_RESULT = 8675;

        /**
         * 发消息告知头部 已接收到 头部的开机信息
         */
        public static final int SEND_MSG_RECEIVER_SYSTEM_BOOT = 8900;

        /**
         * 办公模式---发送退出办公模式
         */
        public static final int SEND_MSG_EXIT_OFFICE_MODE = 8903;

        /**
         * 办公模式启动子功能
         */
        public static final int OFFICE_MODE_START_ID = 8904;

        /**
         * GPU, 发现前面有人，发送距离。。。
         */
        public static final int GPU_FIND_PEOPLE_INFO = 8905;

        /**
         * GPU, 发现前面没有人
         */
        public static final int GPU_NO_PEOPLE_INFO = 8906;

        /**
         * 开启人体追踪
         */
        public static final int OPEN_BODY_TRACK = 8907;

        /**
         * 关闭人体追踪
         */
        public static final int CLOSE_BODY_TRACK = 8908;

        /**
         * 通知头部下发当前连接 wifi 的 ssid 和 password
         */
        public static final int GET_HEAD_WIFI_INFO = 9000;

        /**
         * 头部没有外网 wifi ssid 和 密码
         */
        public static final int HEAD_NO_WIFI_INFO = 9001;

        /**
         * 开始远程视频
         */
        public static final int START_REMOTE_VIDEO = 9002;

        /**
         * 结束远程视频
         */
        public static final int STOP_REMOTE_VIDEO = 9003;

        /**
         * 胸口断网后，发送消息给 手持平板。。。。
         */
        public static final int SEND_BREAK_NET_INFO_TO_MOVEAPD = 9004;
        /**
         * 查询头部固件版本
         */
        public static final int SEND_TOU_DEVICE_VERSION= 9005;

        /**
         * 给胸口发送头部固件版本
         */
        public static final int RECEIVER_TOU_DEVICE_VERSION= 9006;
        /**
         * 发送贵宾接待地点
         */
        public static final int SEND_RECEPTION_ROUTE = 9007;
        /**
         * 发送贵宾退出，不播退出音频
         */
        public static final int SEND_EXIT_VIPGUIDE_NOSOUND = 9008;

        /**
         * 打开酒店预定
         */
        public static final int RECEIVER_TOU_HOTEL_OPEN = 9009;

        /**
         * 播放视频
         */
        public static final int PLAY_VIDEO = 9100;

        /**
         * 消息类型--查询自定义聊聊天所有问题
         */
        public static final int DELETE_ALLQUESTION = 9010;

        /**
         * 向头部发送胸口的声控识别结果
         */
        public static final int SEND_RECOGNITION_RESULT = 9101;

        /**
         * 向头部发送胸口的触摸处理结果
         */
        public static final int SEND_SENSOR_RESULT = 9102;

}
