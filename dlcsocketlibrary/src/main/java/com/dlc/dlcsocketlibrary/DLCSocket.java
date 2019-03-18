package com.dlc.dlcsocketlibrary;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
            // ERROR //
            @Override
            public void run() {
                // Byte code:
                //   0: aload_0
                //   1: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   4: new 32	java/net/Socket
                //   7: dup
                //   8: aload_0
                //   9: getfield 21	com/dlc/dlcsocketlibrary/DLCSocket$1:val$address	Ljava/lang/String;
                //   12: aload_0
                //   13: getfield 23	com/dlc/dlcsocketlibrary/DLCSocket$1:val$port	I
                //   16: invokespecial 34	java/net/Socket:<init>	(Ljava/lang/String;I)V
                //   19: invokestatic 38	com/dlc/dlcsocketlibrary/DLCSocket:access$002	(Lcom/dlc/dlcsocketlibrary/DLCSocket;Ljava/net/Socket;)Ljava/net/Socket;
                //   22: pop
                //   23: aload_0
                //   24: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   27: aload_0
                //   28: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   31: invokestatic 42	com/dlc/dlcsocketlibrary/DLCSocket:access$000	(Lcom/dlc/dlcsocketlibrary/DLCSocket;)Ljava/net/Socket;
                //   34: invokevirtual 46	java/net/Socket:getInputStream	()Ljava/io/InputStream;
                //   37: invokestatic 50	com/dlc/dlcsocketlibrary/DLCSocket:access$102	(Lcom/dlc/dlcsocketlibrary/DLCSocket;Ljava/io/InputStream;)Ljava/io/InputStream;
                //   40: pop
                //   41: aload_0
                //   42: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   45: aload_0
                //   46: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   49: invokestatic 42	com/dlc/dlcsocketlibrary/DLCSocket:access$000	(Lcom/dlc/dlcsocketlibrary/DLCSocket;)Ljava/net/Socket;
                //   52: invokevirtual 54	java/net/Socket:getOutputStream	()Ljava/io/OutputStream;
                //   55: invokestatic 58	com/dlc/dlcsocketlibrary/DLCSocket:access$202	(Lcom/dlc/dlcsocketlibrary/DLCSocket;Ljava/io/OutputStream;)Ljava/io/OutputStream;
                //   58: pop
                //   59: aload_0
                //   60: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   63: iconst_1
                //   64: invokestatic 62	com/dlc/dlcsocketlibrary/DLCSocket:access$302	(Lcom/dlc/dlcsocketlibrary/DLCSocket;Z)Z
                //   67: pop
                //   68: aload_0
                //   69: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   72: invokestatic 66	com/dlc/dlcsocketlibrary/DLCSocket:access$400	(Lcom/dlc/dlcsocketlibrary/DLCSocket;)Lcom/dlc/dlcsocketlibrary/ConnectStatusListener;
                //   75: ifnull +24 -> 99
                //   78: aload_0
                //   79: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   82: invokestatic 66	com/dlc/dlcsocketlibrary/DLCSocket:access$400	(Lcom/dlc/dlcsocketlibrary/DLCSocket;)Lcom/dlc/dlcsocketlibrary/ConnectStatusListener;
                //   85: aload_0
                //   86: getfield 21	com/dlc/dlcsocketlibrary/DLCSocket$1:val$address	Ljava/lang/String;
                //   89: aload_0
                //   90: getfield 23	com/dlc/dlcsocketlibrary/DLCSocket$1:val$port	I
                //   93: iconst_1
                //   94: invokeinterface 72 4 0
                //   99: sipush 1024
                //   102: newarray byte
                //   104: astore_2
                //   105: aload_0
                //   106: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   109: invokestatic 76	com/dlc/dlcsocketlibrary/DLCSocket:access$300	(Lcom/dlc/dlcsocketlibrary/DLCSocket;)Z
                //   112: ifeq +80 -> 192
                //   115: aload_0
                //   116: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   119: invokestatic 80	com/dlc/dlcsocketlibrary/DLCSocket:access$100	(Lcom/dlc/dlcsocketlibrary/DLCSocket;)Ljava/io/InputStream;
                //   122: aload_2
                //   123: invokevirtual 86	java/io/InputStream:read	([B)I
                //   126: istore_1
                //   127: iload_1
                //   128: ifle -23 -> 105
                //   131: new 88	java/io/ByteArrayOutputStream
                //   134: dup
                //   135: sipush 1024
                //   138: invokespecial 91	java/io/ByteArrayOutputStream:<init>	(I)V
                //   141: astore_3
                //   142: aload_3
                //   143: aload_2
                //   144: iconst_0
                //   145: iload_1
                //   146: invokevirtual 95	java/io/ByteArrayOutputStream:write	([BII)V
                //   149: aload_3
                //   150: invokevirtual 98	java/io/ByteArrayOutputStream:flush	()V
                //   153: aload_3
                //   154: invokevirtual 102	java/io/ByteArrayOutputStream:toByteArray	()[B
                //   157: astore_3
                //   158: aload_0
                //   159: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   162: invokestatic 106	com/dlc/dlcsocketlibrary/DLCSocket:access$500	(Lcom/dlc/dlcsocketlibrary/DLCSocket;)Lcom/dlc/dlcsocketlibrary/SocketDataReceiveListener;
                //   165: ifnull -60 -> 105
                //   168: aload_0
                //   169: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   172: invokestatic 106	com/dlc/dlcsocketlibrary/DLCSocket:access$500	(Lcom/dlc/dlcsocketlibrary/DLCSocket;)Lcom/dlc/dlcsocketlibrary/SocketDataReceiveListener;
                //   175: aload_0
                //   176: getfield 21	com/dlc/dlcsocketlibrary/DLCSocket$1:val$address	Ljava/lang/String;
                //   179: aload_0
                //   180: getfield 23	com/dlc/dlcsocketlibrary/DLCSocket$1:val$port	I
                //   183: aload_3
                //   184: invokeinterface 112 4 0
                //   189: goto -84 -> 105
                //   192: aload_0
                //   193: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   196: invokevirtual 115	com/dlc/dlcsocketlibrary/DLCSocket:closeSocket	()V
                //   199: goto +8 -> 207
                //   202: astore_2
                //   203: aload_2
                //   204: invokevirtual 118	java/io/IOException:printStackTrace	()V
                //   207: aload_0
                //   208: invokevirtual 121	com/dlc/dlcsocketlibrary/DLCSocket$1:interrupt	()V
                //   211: aload_0
                //   212: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   215: iconst_0
                //   216: invokestatic 62	com/dlc/dlcsocketlibrary/DLCSocket:access$302	(Lcom/dlc/dlcsocketlibrary/DLCSocket;Z)Z
                //   219: pop
                //   220: aload_0
                //   221: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   224: invokestatic 66	com/dlc/dlcsocketlibrary/DLCSocket:access$400	(Lcom/dlc/dlcsocketlibrary/DLCSocket;)Lcom/dlc/dlcsocketlibrary/ConnectStatusListener;
                //   227: ifnull +115 -> 342
                //   230: goto +91 -> 321
                //   233: astore_2
                //   234: goto +138 -> 372
                //   237: astore_2
                //   238: aload_2
                //   239: invokevirtual 118	java/io/IOException:printStackTrace	()V
                //   242: aload_0
                //   243: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   246: astore_3
                //   247: new 123	java/lang/StringBuilder
                //   250: dup
                //   251: invokespecial 124	java/lang/StringBuilder:<init>	()V
                //   254: astore 4
                //   256: aload 4
                //   258: ldc 126
                //   260: invokevirtual 130	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   263: pop
                //   264: aload 4
                //   266: aload_2
                //   267: invokevirtual 134	java/io/IOException:toString	()Ljava/lang/String;
                //   270: invokevirtual 130	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   273: pop
                //   274: aload_3
                //   275: aload 4
                //   277: invokevirtual 135	java/lang/StringBuilder:toString	()Ljava/lang/String;
                //   280: invokestatic 139	com/dlc/dlcsocketlibrary/DLCSocket:access$600	(Lcom/dlc/dlcsocketlibrary/DLCSocket;Ljava/lang/String;)V
                //   283: aload_0
                //   284: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   287: invokevirtual 115	com/dlc/dlcsocketlibrary/DLCSocket:closeSocket	()V
                //   290: goto +8 -> 298
                //   293: astore_2
                //   294: aload_2
                //   295: invokevirtual 118	java/io/IOException:printStackTrace	()V
                //   298: aload_0
                //   299: invokevirtual 121	com/dlc/dlcsocketlibrary/DLCSocket$1:interrupt	()V
                //   302: aload_0
                //   303: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   306: iconst_0
                //   307: invokestatic 62	com/dlc/dlcsocketlibrary/DLCSocket:access$302	(Lcom/dlc/dlcsocketlibrary/DLCSocket;Z)Z
                //   310: pop
                //   311: aload_0
                //   312: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   315: invokestatic 66	com/dlc/dlcsocketlibrary/DLCSocket:access$400	(Lcom/dlc/dlcsocketlibrary/DLCSocket;)Lcom/dlc/dlcsocketlibrary/ConnectStatusListener;
                //   318: ifnull +24 -> 342
                //   321: aload_0
                //   322: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   325: invokestatic 66	com/dlc/dlcsocketlibrary/DLCSocket:access$400	(Lcom/dlc/dlcsocketlibrary/DLCSocket;)Lcom/dlc/dlcsocketlibrary/ConnectStatusListener;
                //   328: aload_0
                //   329: getfield 21	com/dlc/dlcsocketlibrary/DLCSocket$1:val$address	Ljava/lang/String;
                //   332: aload_0
                //   333: getfield 23	com/dlc/dlcsocketlibrary/DLCSocket$1:val$port	I
                //   336: iconst_0
                //   337: invokeinterface 72 4 0
                //   342: aload_0
                //   343: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   346: invokevirtual 142	com/dlc/dlcsocketlibrary/DLCSocket:cancelHeartTimer	()V
                //   349: aload_0
                //   350: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   353: ldc 143
                //   355: invokestatic 139	com/dlc/dlcsocketlibrary/DLCSocket:access$600	(Lcom/dlc/dlcsocketlibrary/DLCSocket;Ljava/lang/String;)V
                //   358: aload_0
                //   359: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   362: ldc 145
                //   364: invokestatic 139	com/dlc/dlcsocketlibrary/DLCSocket:access$600	(Lcom/dlc/dlcsocketlibrary/DLCSocket;Ljava/lang/String;)V
                //   367: aload_0
                //   368: invokespecial 147	java/lang/Thread:run	()V
                //   371: return
                //   372: aload_0
                //   373: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   376: invokevirtual 115	com/dlc/dlcsocketlibrary/DLCSocket:closeSocket	()V
                //   379: goto +8 -> 387
                //   382: astore_3
                //   383: aload_3
                //   384: invokevirtual 118	java/io/IOException:printStackTrace	()V
                //   387: aload_0
                //   388: invokevirtual 121	com/dlc/dlcsocketlibrary/DLCSocket$1:interrupt	()V
                //   391: aload_0
                //   392: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   395: iconst_0
                //   396: invokestatic 62	com/dlc/dlcsocketlibrary/DLCSocket:access$302	(Lcom/dlc/dlcsocketlibrary/DLCSocket;Z)Z
                //   399: pop
                //   400: aload_0
                //   401: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   404: invokestatic 66	com/dlc/dlcsocketlibrary/DLCSocket:access$400	(Lcom/dlc/dlcsocketlibrary/DLCSocket;)Lcom/dlc/dlcsocketlibrary/ConnectStatusListener;
                //   407: ifnull +24 -> 431
                //   410: aload_0
                //   411: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   414: invokestatic 66	com/dlc/dlcsocketlibrary/DLCSocket:access$400	(Lcom/dlc/dlcsocketlibrary/DLCSocket;)Lcom/dlc/dlcsocketlibrary/ConnectStatusListener;
                //   417: aload_0
                //   418: getfield 21	com/dlc/dlcsocketlibrary/DLCSocket$1:val$address	Ljava/lang/String;
                //   421: aload_0
                //   422: getfield 23	com/dlc/dlcsocketlibrary/DLCSocket$1:val$port	I
                //   425: iconst_0
                //   426: invokeinterface 72 4 0
                //   431: aload_0
                //   432: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   435: invokevirtual 142	com/dlc/dlcsocketlibrary/DLCSocket:cancelHeartTimer	()V
                //   438: aload_0
                //   439: getfield 19	com/dlc/dlcsocketlibrary/DLCSocket$1:this$0	Lcom/dlc/dlcsocketlibrary/DLCSocket;
                //   442: ldc 143
                //   444: invokestatic 139	com/dlc/dlcsocketlibrary/DLCSocket:access$600	(Lcom/dlc/dlcsocketlibrary/DLCSocket;Ljava/lang/String;)V
                //   447: aload_2
                //   448: athrow
                //
                // Exception table:
                //   from	to	target	type
                //   192	199	202	java/io/IOException
                //   0	99	233	finally
                //   99	105	233	finally
                //   105	127	233	finally
                //   131	189	233	finally
                //   238	283	233	finally
                //   0	99	237	java/io/IOException
                //   99	105	237	java/io/IOException
                //   105	127	237	java/io/IOException
                //   131	189	237	java/io/IOException
                //   283	290	293	java/io/IOException
                //   372	379	382	java/io/IOException
            }
        }
                .start();
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
        if (this.isWriting) {
            return;
        }
        this.isWriting = true;
        new Thread() {
            @Override
            public void run() {
                try {
                    DLCSocket.this.os.write(paramArrayOfByte);
                    DLCSocket.this.os.flush();
                } catch (IOException localIOException1) {
                    localIOException1.printStackTrace();
                    try {
                        DLCSocket.this.closeSocket();
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
    
    public void setSocketDataReceiveListener(SocketDataReceiveListener paramSocketDataReceiveListener) {
        this.socketDataReceiveListener = paramSocketDataReceiveListener;
    }
    
    public void startHeartTimer(long paramLong) {
        cancelHeartTimer();
        if (!this.isConnect) {
            return;
        }
        this.timer = new Timer();
        this.task = new TimerTask() {
            @Override
            public void run() {
                if (DLCSocket.this.heartListener != null) {
                    DLCSocket.this.heartListener.onHeart(DLCSocket.this.address, DLCSocket.this.port);
                }
            }
        };
        this.timer.schedule(this.task, paramLong, paramLong);
    }
}