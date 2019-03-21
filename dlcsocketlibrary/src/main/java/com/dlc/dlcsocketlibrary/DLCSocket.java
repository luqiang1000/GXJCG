package com.dlc.dlcsocketlibrary;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class DLCSocket {
    private String address;
    private TimerTask checkConnectTask;
    private Timer checkConnectTimer;
    private ConnectStatusListener connectStatusListener;
    private HeartListener heartListener;
    private InputStream is;
    private boolean isConnect;
    private boolean isWriting;
    private OutputStream os;
    private int port;
    private Socket socket;
    private SocketDataReceiveListener socketDataReceiveListener;
    private TimerTask task;
    private Timer timer;
    
    private void spm(String paramString) {
        Log.d("spm", paramString);
    }
    
    public void cancelHeartTimer() {
        if (this.timer != null) {
            this.task.cancel();
            this.timer.cancel();
            this.task = null;
            this.timer = null;
        }
    }
    
    public void closeSocket()
            throws IOException {
        this.isConnect = false;
        if (this.is != null) {
            this.is.close();
        }
        if (this.os != null) {
            this.os.close();
        }
        if (this.socket != null) {
            this.socket.close();
        }
    }
    
    public void connect(final String paramString, final int paramInt) {
        this.address = paramString;
        this.port = paramInt;
        new Thread() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(DLCSocket.this.address, DLCSocket.this.port);
                    os = socket.getOutputStream();
                    is = socket.getInputStream();
                    isConnect = true;
                    startHeartTimer(15000);
                    
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while ((len = is.read(buffer)) != -1) {
//                        socketDataReceiveListener.onSocketDataReceive(address, port, buffer);
                        LogUtils.e("socket返回数据：" + new String(buffer));
                    }
                } catch (Exception e) {
                    LogUtils.e("连接异常 " + e.getMessage());
                } finally {
                    try {
                        cancelHeartTimer();
                        closeSocket();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    
    public byte[][] getFilterDatas(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2) {
        int i = 0;
        if ((paramArrayOfByte2 != null) && (paramArrayOfByte2.length >= paramArrayOfByte1.length + paramInt)) {
            byte[] arrayOfByte = new byte[paramInt];
            while (i < paramInt) {
                arrayOfByte[i] = paramArrayOfByte2[(paramArrayOfByte1.length + i)];
                i += 1;
            }
//            paramArrayOfByte2 = new StringBuilder();
//            paramArrayOfByte2.append("lengthBytes:");
//            paramArrayOfByte2.append(Integer.parseInt(new String(arrayOfByte)));
//            spm(paramArrayOfByte2.toString());
//            i = paramArrayOfByte1.length;
//            int j = Integer.parseInt(new String(arrayOfByte));
//            paramArrayOfByte1 = new StringBuilder();
//            paramArrayOfByte1.append("dataLength:");
//            paramArrayOfByte1.append(i + paramInt + j);
//            spm(paramArrayOfByte1.toString());
            return (byte[][]) Array.newInstance(Byte.TYPE, new int[]{3, 4});
        }
        return (byte[][]) Array.newInstance(Byte.TYPE, new int[]{0, 0});
    }
    
    public Socket getSocket() {
        return this.socket;
    }
    
    public void send(String paramString) {
        send(paramString.getBytes());
    }
    
    public void send(final byte[] paramArrayOfByte) {
        if (!isConnect || isWriting) {
            return;
        }
        this.isWriting = true;
        new Thread() {
            @Override
            public void run() {
                try {
                    os.write(paramArrayOfByte);
                    os.flush();
                } catch (IOException localIOException1) {
                    LogUtils.e("发送数据异常：" + localIOException1.getMessage());
                } finally {
                    try {
                        closeSocket();
                    } catch (IOException localIOException2) {
                        localIOException2.printStackTrace();
                    }
                }
//                DLCSocket.access$702(DLCSocket.this, false);
                DLCSocket.this.isWriting = false;
                super.run();
            }
        }.start();
    }
    
    public void setConnectStatusListener(ConnectStatusListener paramConnectStatusListener) {
        this.connectStatusListener = paramConnectStatusListener;
    }
    
    public void setHeartListener(HeartListener paramHeartListener) {
        this.heartListener = paramHeartListener;
    }
    
    public void setSocketDataReceiveListener(SocketDataReceiveListener
                                                     paramSocketDataReceiveListener) {
        this.socketDataReceiveListener = paramSocketDataReceiveListener;
    }
    
    public void startHeartTimer(long paramLong) {
        cancelHeartTimer();
        if (!isConnect) {
            return;
        }
        this.timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if (DLCSocket.this.heartListener != null) {
                    DLCSocket.this.heartListener.onHeart(address, port);
                }
            }
        };
        this.timer.schedule(task, paramLong, paramLong);
    }
}