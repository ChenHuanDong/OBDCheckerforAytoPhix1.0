package com.example.administrator.obdcheckerforaytophix10.main.servierbt;

import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.administrator.obdcheckerforaytophix10.MainActivity;
import com.example.administrator.obdcheckerforaytophix10.tool.DensityUtils;
import com.example.administrator.obdcheckerforaytophix10.tool.FileLTool;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by CHD on 2017/10/24.
 */


//车速
//                    mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
//转速
// mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x63, (byte) 0x0D});

public class BluetoothService extends Service {

    //保存是哪种解析的协议的
    //  1---can拓展（单双ECU）   2---kwp（单）
    private int myProtocol = 0;

    private MyBinder mBinder;
    private BluetoothChatUtil mBlthChatUtil;
    private Context mContext;
    private BluetoothAdapter mBluetoothAdapter;

    //车速的拼接(16)
    private String speed = "";
    private String speedReal = "";
    private ArrayList<String> data;
    private int speedFinal = 0;
    private int speedFinalTwo = 0;

    //这个是判断Logs的状态的  1  2  3  4  代表四条PID数据
    private int state;
    //这个是分别存放 四个PID的数据
    private ArrayList<Integer> mDataFour;
    //  四个int 分别是 四个之前的数据 在else 里面 如果下位机发送错误数据  就把之前的上一条数据存到集合里面
    //--------------------下面的广播也别忘了写
    private int oldspeed = 0;
    private int oldwater = 0;
    private int oldzhuansu = 0;
    private int olddoor = 0;
    private long startTime = 0;
    private long endTime = 0;
    private int myCount = 0;
    //判断是都点击了结束按钮     这个现在在 实时数据那里用到了   还在Log那里用到了
    private boolean isFinish = false;
    //这个是判断是否连接上设备
    private boolean isConnect = false;


    private String a = "";


    //这个是监听扫描到的设备的广播
    private BroadcastReceiver mBR = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtil.chunyLog().d("收到广播");
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice scanDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (scanDevice == null || scanDevice.getName() == null)
                    return;
                //蓝牙设备名称
                String name = scanDevice.getName();
                String address = scanDevice.getAddress();
                LogUtil.chunyLog().d("搜索到设备" + name + "蓝牙地址" + address);

                //如果搜索到公司产品就连接上
                if (name.equals("Autophix")) {
                    LogUtil.chunyLog().d("找到Autophix");
                    //停止扫描
                    mBluetoothAdapter.cancelDiscovery();
                    mBlthChatUtil.connect(scanDevice);
                }


            }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();

        //初始化
        mBinder = new MyBinder();
        mContext = this;

        data = new ArrayList<>();

        //注册扫描结果的广播
        IntentFilter intentFilterBR = new IntentFilter();
        intentFilterBR.addAction(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mBR, intentFilterBR);


        state = 1;
        mDataFour = new ArrayList<>();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBR);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
//            sendServiceBR("设备不支持蓝牙");
        } else {
            //设备支持蓝牙
        }


        if (!mBluetoothAdapter.isEnabled()) {
//            sendServiceBR("蓝牙未开启正在开启");
        } else {
//            sendServiceBR("蓝牙已开启");
        }


        mBlthChatUtil = BluetoothChatUtil.getInstance(mContext);
        mBlthChatUtil.registerHandler(mHandler);

        return mBinder;
    }


    public class MyBinder extends Binder {

        //开启蓝牙
        public void openBluetooth(Activity activity) {
            Intent intent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(intent, 1);
            LogUtil.chunyLog().d("打开蓝牙---Service");
        }


        public void scanBTDevice() {
            mBluetoothAdapter.startDiscovery();
            LogUtil.chunyLog().d("开始扫描---Service");
        }

        //得到Set集合
        public Set<BluetoothDevice> getSetBT() {
            return mBluetoothAdapter.getBondedDevices();
        }

        public void connectDevice(String name) {

            //这个是获取本地的已连接的设备地址
            Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
            //   devices.size就是本地连接上的个数

            LogUtil.fussenLog().d(name+"---");

            for (BluetoothDevice device : devices) {
                LogUtil.chunyLog().d("本地已连接设备" + device.getName() + "adress" + device.getAddress());
                //CARAPP V100   8C:DE:52:C4:B2:C7             Autophix  8C:DE:52:65:1B:F9
                if (device.getName().equals(name)) {
                    LogUtil.fussenLog().d("进入连接");
                    mBlthChatUtil.connect(device);
                }
//                if (device.getName().equals("Autophix")) {
//                    LogUtil.fussenLog().d("进入连接");
//                    mBlthChatUtil.connect(device);
//                }

            }

        }


        public void connectDeviceCarApp100() {

            //这个是获取本地的已连接的设备地址
            Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();

            for (BluetoothDevice device : devices) {
                LogUtil.chunyLog().d("本地已连接设备" + device.getName() + "adress" + device.getAddress());
                //CARAPP V100   8C:DE:52:C4:B2:C7             Autophix  8C:DE:52:65:1B:F9
                //OBD  II    00:00:00:11:01:42
                //另一个Autophix   8C:DE:52:DA:A7:D8
                if (device.getName().equals("Autophix")) {
                    LogUtil.fussenLog().d("进入连接");
                    mBlthChatUtil.connect(device);
                }

            }
        }


        public void writeData(byte[] out) {
            mBlthChatUtil.write(out);
        }

        //返回蓝牙连接状态
        public boolean isOpenBluetooth() {
            return mBluetoothAdapter.isEnabled();
        }

        //绑定Handler
        public void regiHanlder(Handler handler) {
            mBlthChatUtil.registerHandler(handler);
        }

        //是否点击判断改变
        public void setIsFinishLog(boolean sf) {
            isFinish = sf;
        }

        //得到是否点击的状态改变
        public boolean getIsFinishLog() {
            return isFinish;
        }

        //获取连接状态
        public boolean getConnect() {
            return isConnect;
        }

        //改变连接状态
        public void setConnect(boolean asd) {
            isConnect = asd;
        }


    }


    //自定义私有化发送广播方法
    //这个是发送状态
    private void sendServiceBR(String s) {
        Intent intent = new Intent("bluetoothBT---state");
        intent.putExtra("msg", s);
        this.sendBroadcast(intent);
    }

    //这个是发送实时数据的广播
    private void sendServiceBRData(int s, String key) {
        Intent intent = new Intent("bluetoothBT---data");
        intent.putExtra(key, s);
        intent.putExtra("key", key);
        this.sendBroadcast(intent);
    }

    //发送Logs回放的广播   发送的是集合  分别是  车速 水温 转速 节气门位置     count 是发送的次数   最后面的是 是否停止
    private void sendServiceBRLogs(String key, ArrayList<Integer> out, int count, boolean fini) {
        Intent intent = new Intent("bluetoothBT---logdata");
        intent.putExtra(key, out);
        intent.putExtra("count", count);
        intent.putExtra("finish", fini);
        this.sendBroadcast(intent);
    }

    //这个是发送故障码的广播   item是故障码具体内容   boo是是否是重要的故障码    最后的asd是状态1是发送故障码 2 是清除故障码
    //如果个数大于10 则 画面是向右平移的
    private void sendServiceBREC(String key, String item, boolean boo, int asd) {
        Intent intent = new Intent("bluetoothBT---errorcode");
        intent.putExtra(key, item);
        intent.putExtra("red", boo);
        intent.putExtra("state", asd);
        this.sendBroadcast(intent);
    }

    //这个是发送性能测试的广播
    private void sendPerformanceBR(String key, int s) {
        Intent intent = new Intent("bluetoothBT---performance");
        intent.putExtra("key", key);
        intent.putExtra("data", s);
        this.sendBroadcast(intent);
    }


    //自定义解析方法
    private void myOwnMethod(String a) {
        //12 * 3 = 36
        ArrayList<String> lOne = new ArrayList<>();
        ArrayList<String> lTwo = new ArrayList<>();
        //第一个的ID
        String ecuId = a.substring(9, 11);
        while (a.length() > 36) {
            if (a.substring(9, 11).equals(ecuId)) {
                lOne.add(a.substring(0, 37).trim());
            } else {
                lTwo.add(a.substring(0, 37).trim());
            }
            a = a.substring(36, a.length()).trim();
        }

        //--------------------------------------------------------
        //最后这个要封装起来的

        //自定义方法的话需要提供   lOne这个集合  这一个参数就行
        myOwnSendB(lOne, 1);
        if (lTwo.size() > 0) {
            myOwnSendB(lTwo, 2);
        }


    }

    //第二个参数是 判断传入的是第一个ECU还是第二个ECU   第一个就是1  第二个就是2
    private void myOwnSendB(ArrayList<String> ll, int onetwo) {
        //这个是长度
        int oneItem = Integer.parseInt(ll.get(0).substring(15, 17).trim(), 16);
        //这个是存放所有有效的数据
        ArrayList<String> oneData = new ArrayList<>();
        //这个是最终的存放要发送出去的
        ArrayList<String> oneLast = new ArrayList<>();


        for (int i = 0; i < ll.size(); i++) {
            if (i == 0) {
                //第一个
                oneItem = oneItem - 6;
                oneData.add(ll.get(0).substring(24, 26));
                oneData.add(ll.get(0).substring(27, 29));
                oneData.add(ll.get(0).substring(30, 32));
                oneData.add(ll.get(0).substring(33, 35));
            } else if (i == ll.size() - 1) {
                //这个是最后的
                switch (oneItem) {
                    case 1:
                        oneData.add(ll.get(0).substring(15, 17));
                        break;
                    case 2:
                        oneData.add(ll.get(0).substring(15, 17));
                        oneData.add(ll.get(0).substring(18, 20));
                        break;
                    case 3:
                        oneData.add(ll.get(0).substring(15, 17));
                        oneData.add(ll.get(0).substring(18, 20));
                        oneData.add(ll.get(0).substring(21, 23));
                        break;
                    case 4:
                        oneData.add(ll.get(0).substring(15, 17));
                        oneData.add(ll.get(0).substring(18, 20));
                        oneData.add(ll.get(0).substring(21, 23));
                        oneData.add(ll.get(0).substring(24, 26));
                        break;
                    case 5:
                        oneData.add(ll.get(0).substring(15, 17));
                        oneData.add(ll.get(0).substring(18, 20));
                        oneData.add(ll.get(0).substring(21, 23));
                        oneData.add(ll.get(0).substring(24, 26));
                        oneData.add(ll.get(0).substring(27, 29));
                        break;
                    case 6:
                        oneData.add(ll.get(0).substring(15, 17));
                        oneData.add(ll.get(0).substring(18, 20));
                        oneData.add(ll.get(0).substring(21, 23));
                        oneData.add(ll.get(0).substring(24, 26));
                        oneData.add(ll.get(0).substring(27, 29));
                        oneData.add(ll.get(0).substring(30, 32));
                        break;
                    case 7:
                        oneData.add(ll.get(0).substring(15, 17));
                        oneData.add(ll.get(0).substring(18, 20));
                        oneData.add(ll.get(0).substring(21, 23));
                        oneData.add(ll.get(0).substring(24, 26));
                        oneData.add(ll.get(0).substring(27, 29));
                        oneData.add(ll.get(0).substring(30, 32));
                        oneData.add(ll.get(0).substring(33, 35));
                        break;
                }
            } else {
                //这个是中间的
                oneItem = oneItem - 7;
                oneData.add(ll.get(0).substring(15, 17));
                oneData.add(ll.get(0).substring(18, 20));
                oneData.add(ll.get(0).substring(21, 23));
                oneData.add(ll.get(0).substring(24, 26));
                oneData.add(ll.get(0).substring(27, 29));
                oneData.add(ll.get(0).substring(30, 32));
                oneData.add(ll.get(0).substring(33, 35));
            }
        }


        for (int i = 0; i < oneData.size(); i++) {
            if ((i % 2) == 0) {
                oneLast.add(DensityUtils.getAll(oneData.get(i).concat(oneData.get(i + 1))));
            }
        }

        if (onetwo == 1) {
            for (String aaa : oneLast) {
                LogUtil.fussenLog().d("第一个ECU故障码:" + aaa);
            }
        }
        if (onetwo == 2) {
            for (String aaa : oneLast) {
                LogUtil.fussenLog().d("第二个ECU故障码:" + aaa);
            }
        }

        for (String aaa : oneLast) {
            if (onetwo == 1) {
                //第一个集合的
                if ((int) SPUtil.get(mContext, "test", 0) == 8) {
                    //储存码
                    sendServiceBREC("key", aaa, true, 1);
                } else {
                    //未知码  历史码
                    sendServiceBREC("key", aaa, true, 1);
                }
            } else {
                if ((int) SPUtil.get(mContext, "test", 0) == 8) {
                    //储存码
                    sendServiceBREC("key", aaa, false, 1);
                } else {
                    //未知码  历史码
                    sendServiceBREC("key", aaa, false, 1);
                }
            }
        }


    }

    //lwp  协议解析故障码
    public void myDataAna(String str, ArrayList<String> llist) {
        while (str.length() > 33) {
            llist.add(DensityUtils.getAll(str.substring(12, 14).concat(str.substring(15, 17))));
            llist.add(DensityUtils.getAll(str.substring(18, 20).concat(str.substring(21, 23))));
            llist.add(DensityUtils.getAll(str.substring(24, 26).concat(str.substring(27, 29))));
            str = str.substring(32, str.length());
        }
        if (str.substring(1, 2).equals("7")) {
            //第二位是7说明长度是33   是正常的一条数据长度   里面可能有00占位
            llist.add(DensityUtils.getAll(str.substring(12, 14).concat(str.substring(15, 17))));
            llist.add(DensityUtils.getAll(str.substring(18, 20).concat(str.substring(21, 23))));
            llist.add(DensityUtils.getAll(str.substring(24, 26).concat(str.substring(27, 29))));
        } else {
            //长度小于最大长度
            switch (str.substring(1, 2)) {
                case "0":
                    break;
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    llist.add(DensityUtils.getAll(str.substring(12, 14).concat(str.substring(15, 17))));
                    break;
                case "4":
                    llist.add(DensityUtils.getAll(str.substring(12, 14).concat(str.substring(15, 17))));
                    break;
                case "5":
                    llist.add(DensityUtils.getAll(str.substring(12, 14).concat(str.substring(15, 17))));
                    llist.add(DensityUtils.getAll(str.substring(18, 20).concat(str.substring(21, 23))));
                    break;
                case "6":
                    llist.add(DensityUtils.getAll(str.substring(12, 14).concat(str.substring(15, 17))));
                    llist.add(DensityUtils.getAll(str.substring(18, 20).concat(str.substring(21, 23))));
                    break;
            }
        }

        //现在先把P0000的全发过去然后在那边做判断如果是P000就不添加进去
        for (String a : llist) {
            if (!a.equals("P0000")) {
                if ((int) SPUtil.get(mContext, "test", 0) == 8) {
                    //如果是储存码就是  importent
                    sendServiceBREC("key", a, true, 1);
                } else {
                    //历史码和未知码 都在这里面发   都是非重要的
                    sendServiceBREC("key", a, false, 1);
                }
            }
        }

        if ((int) SPUtil.get(mContext, "test", 0) == 8) {
            //最后改变SP=10  然后继续询问07  未知码
            SPUtil.put(mContext, "test", 10);
            mBinder.writeData(new byte[]{0x30, 0x37, (byte) 0x0D});
        } else if ((int) SPUtil.get(mContext, "test", 0) == 10) {
            //最后改变SP=11  然后继续询问0a  历史码
            SPUtil.put(mContext, "test", 11);
            mBinder.writeData(new byte[]{0x30, 0x41, (byte) 0x0D});
        }


    }


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothChatUtil.STATE_CONNECTED:
                    //连接成功的话
                    sendServiceBR("连接成功");
                    break;
                case BluetoothChatUtil.STATAE_CONNECT_FAILURE:
//                    mBinder.connectDevice();
                    LogUtil.fussenLog().d("连接失败");
                    sendServiceBR("连接失败");
                    break;
                case BluetoothChatUtil.MESSAGE_DISCONNECTED:
                    LogUtil.fussenLog().d("信息未连接");
                    isConnect = false;
                    sendServiceBR("断开连接");
                    break;
                case BluetoothChatUtil.MESSAGE_READ:

                    byte[] buf0 = msg.getData().getByteArray(BluetoothChatUtil.READ_MSG);

                    a = DensityUtils.bytesToHexString(buf0);


//                    String aaa = DensityUtils.hexStr2Str(DensityUtils.bytesToHexString(buf0));


                    //3E  是>
                    int count = a.indexOf("3E0000");

                    //这个是空格加换行  两种方式换行？？   200000也能换行
                    int wow = a.indexOf("200D00");
                    int wa = a.indexOf("200000");

                    int ssp = (int) SPUtil.get(mContext, "test", 0);

                    //SP:   0-------未存储
                    //      1-------发送过ATH1
                    //      2-------发送过ATSP0
                    //      3-------发送过0100

                    if (ssp == 1 && count != -1) {
                        mBinder.writeData(new byte[]{0x41, 0x54, 0x53, 0x50, 0x30, (byte) 0x0D});
                        SPUtil.put(mContext, "test", 2);
                        LogUtil.fussenLog().d("接收ATH1并且发送ATSP0再并且把SP存成2");
                    } else if (ssp == 2 && count != -1) {
                        mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x30, (byte) 0x0D});
                        SPUtil.put(mContext, "test", 3);
                        LogUtil.fussenLog().d("接收ATSP0并且发送0100再并且把SP存成3");
                    } else if (ssp == 3 && count != -1) {
                        //ATDP
                        mBinder.writeData(new byte[]{0x41, 0x54, 0x44, 0x50, (byte) 0x0D});
                        SPUtil.put(mContext, "test", 9);
                        LogUtil.fussenLog().d("接收0100并且发送ATDP(查询协议)再并且把SP存成9");
                    } else if (ssp == 4 || ssp == 5) {

                        switch (myProtocol) {
                            case 2:

                                if (wow != -1) {
                                    speed = speed + a.substring(0, wow);
                                }
                                if (wa != -1) {
                                    speed = speed + a.substring(0, wa + 2);
                                }
                                if (count != -1) {
                                    speed = speed + a.substring(count, count + 2);
                                    speedReal = DensityUtils.hexStr2Str(speed);


                                    if (speedReal.length() == 21) {
                                        //长度是21说明正确
                                        //截取  是左闭右开
                                        // LogUtil.fussenLog().d("这个是拼接后的:" + speedReal);
                                        speedReal = speedReal.substring(15, 17).trim();
//                            LogUtil.fussenLog().d(speedReal);
                                        speedFinal = Integer.parseInt(speedReal, 16);
                                        //LogUtil.fussenLog().d("最终的速度:" + speedFinal);
                                        //清空
                                        speed = "";
                                        speedReal = "";
                                        if (ssp == 4) {
                                            //发送最终速度的广播
                                            sendServiceBRData(speedFinal, "车速");
                                            if (!isFinish) {
                                                SPUtil.put(mContext, "test", 5);
                                                mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x35, (byte) 0x0D});
                                            }
                                        } else {
                                            //发送最终速度的广播
                                            sendServiceBRData(speedFinal - 40, "水温");
//                                    SPUtil.put(mContext, "test", 4);
//                                    mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
                                            //存6然后发送转速
                                            SPUtil.put(mContext, "test", 6);
                                            mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x63, (byte) 0x0D});
                                        }
                                    } else {
                                        LogUtil.fussenLog().d("长度不是21------又要去看看哪错了");
                                        LogUtil.fussenLog().d("这个是真的：" + speedReal);
                                        //清空
                                        speed = "";
                                        speedReal = "";
                                        if (ssp == 4) {
                                            SPUtil.put(mContext, "test", 5);
                                            mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x35, (byte) 0x0D});
                                        } else {
                                            SPUtil.put(mContext, "test", 6);
                                            mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x63, (byte) 0x0D});
                                        }
                                    }
                                }

                                break;
                            case 1:
                                //can
                                if (wow != -1) {
                                    speed = speed + a.substring(0, wow);
                                }
                                if (wa != -1) {
                                    speed = speed + a.substring(0, wa + 2);
                                }
                                if (count != -1) {
                                    speed = speed + a.substring(count, count + 2);
                                    speedReal = DensityUtils.hexStr2Str(speed);

                                    if (speedReal.length() >= 24) {
                                        speedFinal = Integer.parseInt(speedReal.substring(21, 23).trim(), 16);
                                        speed = "";
                                        speedReal = "";
                                        if (ssp == 4) {
                                            sendServiceBRData(speedFinal, "车速");
                                            if (!isFinish) {
                                                SPUtil.put(mContext, "test", 5);
                                                mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x35, (byte) 0x0D});
                                            }
                                        } else {
                                            sendServiceBRData(speedFinal - 40, "水温");
                                            //存6然后发送转速
                                            SPUtil.put(mContext, "test", 6);
                                            mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x63, (byte) 0x0D});
                                        }
                                    } else {
                                        LogUtil.fussenLog().d("长度出现问题了(can)---这个是真的：" + speedReal);
                                        //清空
                                        speed = "";
                                        speedReal = "";
                                        if (ssp == 4) {
                                            SPUtil.put(mContext, "test", 5);
                                            mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x35, (byte) 0x0D});
                                        } else {
                                            SPUtil.put(mContext, "test", 6);
                                            mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x63, (byte) 0x0D});
                                        }
                                    }
                                }
                                break;
                            case 3:
                                if (wow != -1) {
                                    speed = speed + a.substring(0, wow);
                                }
                                if (wa != -1) {
                                    speed = speed + a.substring(0, wa + 2);
                                }
                                if (count != -1) {
                                    speed = speed + a.substring(count, count + 2);
                                    speedReal = DensityUtils.hexStr2Str(speed);

                                    if (speedReal.length() == 16) {
                                        speedFinal = Integer.parseInt(speedReal.substring(13, 15).trim(), 16);
                                        speed = "";
                                        speedReal = "";
                                        if (ssp == 4) {
                                            sendServiceBRData(speedFinal, "车速");
                                            if (!isFinish) {
                                                SPUtil.put(mContext, "test", 5);
                                                mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x35, (byte) 0x0D});
                                            }
                                        } else {
                                            sendServiceBRData(speedFinal - 40, "水温");
                                            //存6然后发送转速
                                            SPUtil.put(mContext, "test", 6);
                                            mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x63, (byte) 0x0D});
                                        }
                                    } else {
                                        LogUtil.fussenLog().d("长度出现问题了(can)---这个是真的：" + speedReal);
                                        //清空
                                        speed = "";
                                        speedReal = "";
                                        if (ssp == 4) {
                                            SPUtil.put(mContext, "test", 5);
                                            mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x35, (byte) 0x0D});
                                        } else {
                                            SPUtil.put(mContext, "test", 6);
                                            mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x63, (byte) 0x0D});
                                        }
                                    }
                                }
                                break;
                        }
                    } else if (ssp == 6) {
                        //这里是转速
                        switch (myProtocol) {
                            case 2:
                                if (wow != -1) {
                                    speed = speed + a.substring(0, wow);
                                }
                                if (wa != -1) {
                                    speed = speed + a.substring(0, wa + 2);
                                }
                                if (count != -1) {
                                    speed = speed + a.substring(count, count + 2);
                                    speedReal = DensityUtils.hexStr2Str(speed);
                                    if (speedReal.length() == 24) {
                                        speedFinal = Integer.parseInt(speedReal.substring(15, 17).trim(), 16);
                                        speedFinalTwo = Integer.parseInt(speedReal.substring(18, 20).trim(), 16);
                                        speed = "";
                                        speedReal = "";
                                        sendServiceBRData((int) ((speedFinal * 64) + ((speedFinalTwo * 63.75) / 255)), "转速");
                                        //LogUtil.fussenLog().d("这个是转速最终的" + (int) ((speedFinal * 64) + ((speedFinalTwo * 63.75) / 255)));
                                        SPUtil.put(mContext, "test", 7);
                                        mBinder.writeData(new byte[]{0x30, 0x31, 0x31, 0x31, (byte) 0x0D});
                                    } else {
                                        //长度不是24
                                        //节气门位置    30 31 31 31 0D    最后要*100 / 255
                                        speed = "";
                                        speedReal = "";
                                        SPUtil.put(mContext, "test", 7);
                                        mBinder.writeData(new byte[]{0x30, 0x31, 0x31, 0x31, (byte) 0x0D});
                                    }
                                }
                                break;
                            case 1:
                                //can
                                if (wow != -1) {
                                    speed = speed + a.substring(0, wow);
                                }
                                if (wa != -1) {
                                    speed = speed + a.substring(0, wa + 2);
                                }
                                if (count != -1) {
                                    speed = speed + a.substring(count, count + 2);
                                    speedReal = DensityUtils.hexStr2Str(speed);
                                    if (speedReal.length() >= 27) {
                                        //长度55
                                        speedFinal = Integer.parseInt(speedReal.substring(21, 23).trim(), 16);
                                        speedFinalTwo = Integer.parseInt(speedReal.substring(24, 26).trim(), 16);
                                        speed = "";
                                        speedReal = "";
                                        sendServiceBRData((int) ((speedFinal * 64) + ((speedFinalTwo * 63.75) / 255)), "转速");
                                        SPUtil.put(mContext, "test", 7);
                                        mBinder.writeData(new byte[]{0x30, 0x31, 0x31, 0x31, (byte) 0x0D});
                                    } else {
                                        //长度错误
                                        LogUtil.fussenLog().d("can的转速长度错误");
                                        speed = "";
                                        speedReal = "";
                                        SPUtil.put(mContext, "test", 7);
                                        //发送节气门
                                        mBinder.writeData(new byte[]{0x30, 0x31, 0x31, 0x31, (byte) 0x0D});
                                    }
                                }

                                break;
                        }


                    } else if (ssp == 7) {
                        //这个是解析节气门位置的
                        switch (myProtocol) {
                            case 2:
                                if (wow != -1) {
                                    speed = speed + a.substring(0, wow);
                                }
                                if (wa != -1) {
                                    speed = speed + a.substring(0, wa + 2);
                                }
                                if (count != -1) {
                                    speed = speed + a.substring(count, count + 2);
                                    speedReal = DensityUtils.hexStr2Str(speed);
                                    //LogUtil.fussenLog().d("这个是拼接后的:" + speedReal);
                                    if (speedReal.length() == 21) {
                                        speedFinal = Integer.parseInt(speedReal.substring(15, 17).trim(), 16);

                                        //sendServiceBRData(  (int) (speedFinal* 100.f / 255.0f), "节气门");
                                        //LogUtil.fussenLog().d( "节气门位置" + (int) (speedFinal* 100.f / 255.0f));
                                        //SPUtil.put(mContext, "test", 7);
                                        speed = "";
                                        speedReal = "";

                                        sendServiceBRData((int) (speedFinal * 100.f / 255.0f), "节气");

                                        SPUtil.put(mContext, "test", 4);
                                        mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
                                    } else {
                                        //长度不是21
                                        LogUtil.fussenLog().d("长度错了！！");
                                        speed = "";
                                        speedReal = "";

                                        SPUtil.put(mContext, "test", 4);
                                        mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
                                    }
                                }
                                break;
                            case 1:
                                if (wow != -1) {
                                    speed = speed + a.substring(0, wow);
                                }
                                if (wa != -1) {
                                    speed = speed + a.substring(0, wa + 2);
                                }
                                if (count != -1) {
                                    speed = speed + a.substring(count, count + 2);
                                    speedReal = DensityUtils.hexStr2Str(speed);

                                    if (speedReal.length() >= 24) {
                                        speedFinal = Integer.parseInt(speedReal.substring(21, 23).trim(), 16);
                                        speed = "";
                                        speedReal = "";

                                        sendServiceBRData((int) (speedFinal * 100.f / 255.0f), "节气");

                                        SPUtil.put(mContext, "test", 4);
                                        mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
                                    } else {
                                        LogUtil.fussenLog().d("长度错了！！(can)---节气门位置");
                                        speed = "";
                                        speedReal = "";

                                        SPUtil.put(mContext, "test", 4);
                                        mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
                                    }
                                }
                                break;
                        }


                    } else if (ssp == 8) {
                        //这是故障码
                        //03

                        switch (myProtocol) {
                            case 2:
                                if (wow != -1) {
                                    speed = speed + a.substring(0, wow);
                                }
                                if (wa != -1) {
                                    speed = speed + a.substring(0, wa + 2);
                                }
                                if (count != -1) {
                                    speed = speed + a.substring(count, count + 2);
                                    speedReal = DensityUtils.hexStr2Str(speed);

//                            LogUtil.fussenLog().d(speedReal);

                                    //自定义方法解析   传入参数 然后会向传入的集合加入元素\
                                    myDataAna(speedReal, data);
                                    data.clear();
                                    speed = "";
                                    speedReal = "";


                                    //长度是65    判断校验后面是不是尖括号     32 * 2 + 1
                                    //如果小于三条个数据的话长度是 33    32+1
                                    //87 F1 11 43 01 06 01 07 01 08 E487 F1 11 43 01 05 00 00 00 00 D2>
                                    //87 F1 11 43 01 06 01 07 01 08 E4
                                    //87 F1 11 43 01 05 00 00 00 00 D2>
                                    //     8x 是多少就取多少


                                }
                                break;
                            case 1:

                                if (wow != -1) {
                                    speed = speed + a.substring(0, wow);
                                }
                                if (wa != -1) {
                                    speed = speed + a.substring(0, wa + 2);
                                }
                                if (count != -1) {
                                    speed = speed + a.substring(0, count + 2);
                                    speedReal = DensityUtils.hexStr2Str(speed);

                                    //自定义解析的方法
                                    myOwnMethod(speedReal);

                                    speed = "";
                                    speedReal = "";

                                    //先歇着里  看看全部的啥样
                                    SPUtil.put(mContext, "test", 10);
                                    mBinder.writeData(new byte[]{0x30, 0x37, (byte) 0x0D});

                                }
                                break;
                        }


                    } else if (ssp == 9) {
                        //这个是查询协议   不用处理
                        //sendServiceBR("解析完成");


                        if (wow != -1) {
                            speed = speed + a.substring(0, wow);
                        }
                        if (wa != -1) {
                            speed = speed + a.substring(0, wa + 2);
                        }
                        if (count != -1) {
                            speed = speed + a.substring(0, count + 2);
                            speedReal = DensityUtils.hexStr2Str(speed);

                            if (speedReal.indexOf("CAN") != -1 && speedReal.indexOf("29") != -1) {
                                LogUtil.fussenLog().d("can协议(拓展)");
                                //这里需要做存一下  是什么协议的
                                myProtocol = 1;
                                Toast.makeText(mContext, "can 拓展", Toast.LENGTH_SHORT).show();
                            } else if (speedReal.indexOf("KWP") != -1) {
                                LogUtil.fussenLog().d("kwp协议");
                                //这里需要做存一下  是什么协议的
                                Toast.makeText(mContext, "kwp", Toast.LENGTH_SHORT).show();
                                myProtocol = 2;
                            } else if (speedReal.indexOf("CAN") != -1 && speedReal.indexOf("11") != -1) {
                                LogUtil.fussenLog().d("can协议(正常)");
                                myProtocol = 3;
                                Toast.makeText(mContext, "can 正常", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "两个协议都不是", Toast.LENGTH_SHORT).show();
                                LogUtil.fussenLog().d("两个协议都不是");
                                LogUtil.fussenLog().d("最终的：" + speedReal);
                            }

                            speed = "";
                            speedReal = "";
                            sendServiceBR("解析完成");

                        }


                    } else if (ssp == 10) {
                        //07
                        switch (myProtocol) {
                            case 2:
                                if (wow != -1) {
                                    speed = speed + a.substring(0, wow);
                                }
                                if (wa != -1) {
                                    speed = speed + a.substring(0, wa + 2);
                                }
                                if (count != -1) {


                                    speed = speed + a.substring(count, count + 2);
                                    speedReal = DensityUtils.hexStr2Str(speed);


//                            LogUtil.fussenLog().d(speedReal);

                                    myDataAna(speedReal, data);
                                    data.clear();
                                    speed = "";
                                    speedReal = "";

                                }
                                break;
                            case 1:
                                if (wow != -1) {
                                    speed = speed + a.substring(0, wow);
                                }
                                if (wa != -1) {
                                    speed = speed + a.substring(0, wa + 2);
                                }
                                if (count != -1) {
                                    speed = speed + a.substring(0, count + 2);
                                    speedReal = DensityUtils.hexStr2Str(speed);

                                    //自定义解析的方法
                                    myOwnMethod(speedReal);

                                    speed = "";
                                    speedReal = "";


                                    //先歇着里  看看全部的啥样
                                    SPUtil.put(mContext, "test", 11);
                                    mBinder.writeData(new byte[]{0x30, 0x41, (byte) 0x0D});
                                }

                                break;
                        }


                    } else if (ssp == 11) {
                        //这个是0a历史码
                        switch (myProtocol) {
                            case 2:
                                if (wow != -1) {
                                    speed = speed + a.substring(0, wow);
                                }
                                if (wa != -1) {
                                    speed = speed + a.substring(0, wa + 2);
                                }
                                if (count != -1) {

                                    speed = speed + a.substring(count, count + 2);
                                    speedReal = DensityUtils.hexStr2Str(speed);


                                    //这个是瞎写的  只要不是返回的是 >  就行
                                    if (speedReal.length() > 10) {
                                        myDataAna(speedReal, data);
                                    }
                                    data.clear();
                                    speed = "";
                                    speedReal = "";
                                }
                                break;
                            case 1:

                                if (wow != -1) {
                                    speed = speed + a.substring(0, wow);
                                }
                                if (wa != -1) {
                                    speed = speed + a.substring(0, wa + 2);
                                }
                                if (count != -1) {
                                    speed = speed + a.substring(0, count + 2);
                                    speedReal = DensityUtils.hexStr2Str(speed);


                                    if (speedReal.length() > 20) {
                                        //自定义解析的方法
                                        myOwnMethod(speedReal);
                                    }


                                    speed = "";
                                    speedReal = "";


                                }

                                break;
                        }
                    } else if (ssp == 12) {
                        //这里是清除故障码
                        if (wow != -1) {
                            speed = speed + a.substring(0, wow);
                        }
                        if (wa != -1) {
                            speed = speed + a.substring(0, wa + 2);
                        }
                        if (count != -1) {


                            speed = speed + a.substring(count, count + 2);
                            speedReal = DensityUtils.hexStr2Str(speed);

                            //如果  81 F1 11 44 C7>  如果是11 44 的话就发送广播
                            if (speedReal.length() == 15) {
                                if (speedReal.substring(6, 8).equals("11") && speedReal.substring(9, 11).equals("44")) {
                                    sendServiceBREC("key", "0", false, 2);
                                }
                            }


                            data.clear();
                            speed = "";
                            speedReal = "";


                        }

                    } else if (ssp == 13) {
                        //13   就是Log回放的
                        switch (myProtocol) {
                            case 2:
                                if (wow != -1) {
                                    speed = speed + a.substring(0, wow);
                                }
                                if (wa != -1) {
                                    speed = speed + a.substring(0, wa + 2);
                                }
                                if (count != -1) {
                                    speed = speed + a.substring(count, count + 2);
                                    speedReal = DensityUtils.hexStr2Str(speed);

                                    if (speedReal.length() == 21 || speedReal.length() == 24) {

//                                LogUtil.fussenLog().d(speedReal);

                                        speedFinal = Integer.parseInt(speedReal.substring(15, 17).trim(), 16);
                                        speedFinalTwo = Integer.parseInt(speedReal.substring(18, 20).trim(), 16);
                                        speed = "";
                                        speedReal = "";
                                        //下面应该是根据int 型判断 状态
                                        switch (state) {
                                            case 1:
                                                //车速
                                                //把开始时间记录下来
                                                startTime = System.currentTimeMillis();
                                                state = 2;
                                                oldspeed = speedFinal;
                                                mDataFour.add(speedFinal);
                                                //发送水温
                                                mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x35, (byte) 0x0D});
                                                break;
                                            case 2:
                                                //水温
                                                state = 3;
                                                oldwater = speedFinal - 40;
                                                mDataFour.add(oldwater);
                                                //发送转速
                                                mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x63, (byte) 0x0D});
                                                break;
                                            case 3:
                                                //转速
                                                state = 4;
                                                oldzhuansu = (int) ((speedFinal * 64) + ((speedFinalTwo * 63.75) / 255));
                                                mDataFour.add(oldzhuansu);
                                                //发送节气门位置
                                                mBinder.writeData(new byte[]{0x30, 0x31, 0x31, 0x31, (byte) 0x0D});
                                                break;
                                            case 4:
                                                //节气门位置
                                                state = 1;
                                                olddoor = (int) (speedFinal * 100.f / 255.0f);
                                                mDataFour.add(olddoor);
                                                //这里要发送广播
                                                myCount++;
                                                sendServiceBRLogs("key", mDataFour, myCount, isFinish);
                                                mDataFour.clear();
                                                //记录结束时间
                                                endTime = System.currentTimeMillis();
                                                //这里要 做时间判断  总时间是1s   让线程Sleep  1000 - 解析发送的所有时间
                                                //发送车速
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        if (!isFinish) {
                                                            if (endTime - startTime < 1000) {
                                                                try {
                                                                    Thread.sleep(1000 - (endTime - startTime));
                                                                    mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
                                                                } catch (InterruptedException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            } else {
                                                                mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
                                                            }
                                                        }

                                                    }
                                                }).start();
                                                break;
                                        }
                                    } else {
                                        //长度不是21或者24   说明不对了
                                        speed = "";
                                        speedReal = "";
                                        switch (state) {
                                            case 1:
                                                state = 2;
                                                startTime = System.currentTimeMillis();
                                                mDataFour.add(oldspeed);
                                                //发送水温
                                                mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x35, (byte) 0x0D});
                                                break;
                                            case 2:
                                                state = 3;
                                                mDataFour.add(oldwater);
                                                //发送转速
                                                mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x63, (byte) 0x0D});
                                                break;
                                            case 3:
                                                state = 4;
                                                mDataFour.add(oldzhuansu);
                                                //发送节气门位置
                                                mBinder.writeData(new byte[]{0x30, 0x31, 0x31, 0x31, (byte) 0x0D});
                                                break;
                                            case 4:
                                                state = 1;
                                                mDataFour.add(olddoor);


                                                //这里要发送广播
                                                myCount++;
                                                sendServiceBRLogs("key", mDataFour, myCount, isFinish);
                                                mDataFour.clear();

                                                endTime = System.currentTimeMillis();

                                                //不要开两个线程，所以这个直接判断是不是isFinish 然后直接发
                                                //要不错了两个线程一起就回错了
                                                //这份是错了就不发送数据  可是还判断是否继续发
                                                if (!isFinish) {
                                                    mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
                                                }

//                                                new Thread(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//
//                                                        if (!isFinish) {
//                                                            if (endTime - startTime < 1000) {
//                                                                try {
//                                                                    Thread.sleep(1000 - (endTime - startTime));
//                                                                    mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
//                                                                } catch (InterruptedException e) {
//                                                                    e.printStackTrace();
//                                                                }
//                                                            } else {
//                                                                mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
//                                                            }
//
//                                                        }
//
//                                                    }
//                                                }).start();


                                                break;
                                        }


                                    }
                                }
                                break;
                            case 1:
                                if (wow != -1) {
                                    speed = speed + a.substring(0, wow);
                                }
                                if (wa != -1) {
                                    speed = speed + a.substring(0, wa + 2);
                                }
                                if (count != -1) {
                                    speed = speed + a.substring(count, count + 2);
                                    speedReal = DensityUtils.hexStr2Str(speed);

                                    if (speedReal.length() >= 27) {
                                        speedFinal = Integer.parseInt(speedReal.substring(21, 23).trim(), 16);
                                        speedFinalTwo = Integer.parseInt(speedReal.substring(24, 26).trim(), 16);
                                        speed = "";
                                        speedReal = "";
                                        if (state == 3) {
                                            //转速
                                            state = 4;
                                            oldzhuansu = (int) ((speedFinal * 64) + ((speedFinalTwo * 63.75) / 255));
                                            mDataFour.add(oldzhuansu);
                                            //发送节气门位置
                                            mBinder.writeData(new byte[]{0x30, 0x31, 0x31, 0x31, (byte) 0x0D});
                                        }

                                    } else if (speedReal.length() >= 24) {
                                        speedFinal = Integer.parseInt(speedReal.substring(21, 23).trim(), 16);
                                        speed = "";
                                        speedReal = "";
                                        switch (state) {
                                            case 1:
                                                startTime = System.currentTimeMillis();
                                                state = 2;
                                                oldspeed = speedFinal;
                                                mDataFour.add(speedFinal);
                                                //发送水温
                                                mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x35, (byte) 0x0D});
                                                break;
                                            case 2:
                                                //水温
                                                state = 3;
                                                oldwater = speedFinal - 40;
                                                mDataFour.add(oldwater);
                                                //发送转速
                                                mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x63, (byte) 0x0D});
                                                break;
                                            case 4:
                                                state = 1;
                                                olddoor = (int) (speedFinal * 100.f / 255.0f);
                                                mDataFour.add(olddoor);
                                                //这里要发送广播
                                                myCount++;
                                                sendServiceBRLogs("key", mDataFour, myCount, isFinish);
                                                mDataFour.clear();
                                                //记录结束时间
                                                endTime = System.currentTimeMillis();
                                                //这里要 做时间判断  总时间是1s   让线程Sleep  1000 - 解析发送的所有时间
                                                //发送车速
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (!isFinish) {

                                                            if (endTime - startTime < 1000) {
                                                                try {
                                                                    Thread.sleep(1000 - (endTime - startTime));
                                                                    mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
                                                                } catch (InterruptedException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            } else {
                                                                mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
                                                            }
                                                        }

                                                    }
                                                }).start();
                                                break;
                                        }


                                    } else {
                                        //长度不正确
                                        speed = "";
                                        speedReal = "";
                                        switch (state) {
                                            case 1:
                                                state = 2;
                                                startTime = System.currentTimeMillis();
                                                mDataFour.add(oldspeed);
                                                //发送水温
                                                mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x35, (byte) 0x0D});
                                                break;
                                            case 2:
                                                state = 3;
                                                mDataFour.add(oldwater);
                                                //发送转速
                                                mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x63, (byte) 0x0D});
                                                break;
                                            case 3:
                                                state = 4;
                                                mDataFour.add(oldzhuansu);
                                                //发送节气门位置
                                                mBinder.writeData(new byte[]{0x30, 0x31, 0x31, 0x31, (byte) 0x0D});
                                                break;
                                            case 4:
                                                state = 1;
                                                mDataFour.add(olddoor);
                                                //这里要发送广播
                                                myCount++;
                                                sendServiceBRLogs("key", mDataFour, myCount, isFinish);
                                                mDataFour.clear();


                                                endTime = System.currentTimeMillis();

                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        if (!isFinish) {

                                                            if (endTime - startTime < 1000) {
                                                                try {
                                                                    Thread.sleep(1000 - (endTime - startTime));
                                                                    mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
                                                                } catch (InterruptedException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            } else {
                                                                mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
                                                            }

                                                        }

                                                    }
                                                }).start();


                                                break;
                                        }
                                    }
                                }
                                break;
                        }

                    } else if (ssp == 14) {
                        //性能测试   这里只弄了can协议的  kwp == 2
                        if (myProtocol == 1) {
                            if (wow != -1) {
                                speed = speed + a.substring(0, wow);
                            }
                            if (wa != -1) {
                                speed = speed + a.substring(0, wa + 2);
                            }
                            if (count != -1) {
                                speed = speed + a.substring(count, count + 2);
                                speedReal = DensityUtils.hexStr2Str(speed);

                                if (speedReal.length() >= 24) {
                                    speedFinal = Integer.parseInt(speedReal.substring(21, 23).trim(), 16);
                                    speed = "";
                                    speedReal = "";
                                    //这个应该是性能测试的广播
                                    sendPerformanceBR("车速", speedFinal);
                                } else {
                                    LogUtil.fussenLog().d("长度出现问题了(can)---性能测试：" + speedReal);
                                    //清空
                                    speed = "";
                                    speedReal = "";
                                }
                                //
                                if (!isFinish) {
                                    //ssp  变成15 发送转速
                                    SPUtil.put(mContext, "test", 15);
                                    mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x63, (byte) 0x0D});
                                }

                            }
                        } else if (myProtocol == 2) {

                            if (wow != -1) {
                                speed = speed + a.substring(0, wow);
                            }
                            if (wa != -1) {
                                speed = speed + a.substring(0, wa + 2);
                            }
                            if (count != -1) {
                                speed = speed + a.substring(count, count + 2);
                                speedReal = DensityUtils.hexStr2Str(speed);


                                if (speedReal.length() == 21) {

                                    speedReal = speedReal.substring(15, 17).trim();
//                            LogUtil.fussenLog().d(speedReal);
                                    speedFinal = Integer.parseInt(speedReal, 16);
                                    //LogUtil.fussenLog().d("最终的速度:" + speedFinal);
                                    //清空
                                    speed = "";
                                    speedReal = "";


                                    sendPerformanceBR("车速", speedFinal);

                                    if (!isFinish) {
                                        SPUtil.put(mContext, "test", 15);
                                        mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x63, (byte) 0x0D});

                                    }

                                } else {

                                    LogUtil.fussenLog().d("性能测试  车速出错");

                                    //清空
                                    speed = "";
                                    speedReal = "";

                                    SPUtil.put(mContext, "test", 15);
                                    mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x63, (byte) 0x0D});


                                }
                            }


                        }
                    } else if (ssp == 15) {
                        //性能测试  转速   这里只弄了can协议的
                        if (myProtocol == 1) {
                            if (wow != -1) {
                                speed = speed + a.substring(0, wow);
                            }
                            if (wa != -1) {
                                speed = speed + a.substring(0, wa + 2);
                            }
                            if (count != -1) {
                                speed = speed + a.substring(count, count + 2);
                                speedReal = DensityUtils.hexStr2Str(speed);

                                if (speedReal.length() >= 27) {
                                    speedFinal = Integer.parseInt(speedReal.substring(21, 23).trim(), 16);
                                    speedFinalTwo = Integer.parseInt(speedReal.substring(24, 26).trim(), 16);
                                    speed = "";
                                    speedReal = "";
                                    //这个应该是性能测试的广播
                                    sendPerformanceBR("转速", (int) ((speedFinal * 64) + ((speedFinalTwo * 63.75) / 255)));
                                } else {
                                    LogUtil.fussenLog().d("长度出现问题了(can)---性能测试：" + speedReal);
                                    //清空
                                    speed = "";
                                    speedReal = "";
                                }
                                //
                                if (!isFinish) {
                                    //ssp  变成14 发送车速
                                    SPUtil.put(mContext, "test", 14);
                                    mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
                                }
                            }
                        } else if (myProtocol == 2) {
                            //kwp转速
                            if (wow != -1) {
                                speed = speed + a.substring(0, wow);
                            }
                            if (wa != -1) {
                                speed = speed + a.substring(0, wa + 2);
                            }
                            if (count != -1) {
                                speed = speed + a.substring(count, count + 2);
                                speedReal = DensityUtils.hexStr2Str(speed);
                                if (speedReal.length() == 24) {
                                    speedFinal = Integer.parseInt(speedReal.substring(15, 17).trim(), 16);
                                    speedFinalTwo = Integer.parseInt(speedReal.substring(18, 20).trim(), 16);
                                    speed = "";
                                    speedReal = "";
                                    //这里要发送广播
                                    sendPerformanceBR("转速", (int) ((speedFinal * 64) + ((speedFinalTwo * 63.75) / 255)));
                                    if (!isFinish) {
                                        SPUtil.put(mContext, "test", 14);
                                        mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
                                    }

                                } else {
                                    //长度不是24
                                    //节气门位置    30 31 31 31 0D    最后要*100 / 255
                                    LogUtil.fussenLog().d("性能测试 转速出错");
                                    speed = "";
                                    speedReal = "";
                                    if (!isFinish) {
                                        SPUtil.put(mContext, "test", 14);
                                        mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
                                    }
                                }


                            }


                        }

                    }
                    //上面的括号是根据SP判断的
                    //这个break是Handler 的里面的switch case 里面的
                    break;
            }

        }
    };


}
