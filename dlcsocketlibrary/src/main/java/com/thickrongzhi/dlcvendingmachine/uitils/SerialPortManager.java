package com.thickrongzhi.dlcvendingmachine.uitils;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.thickrongzhi.dlcvendingmachine.SerialPort.SerialPort;
import com.thickrongzhi.dlcvendingmachine.core.CmdPack;
import com.thickrongzhi.dlcvendingmachine.serial.SerialReadThread;

import java.io.File;
import java.io.OutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Administrator
 */
public class SerialPortManager {
    private static final String TAG = "SerialPortManager=";
    public static SerialPortManager mSerialPortManager;
    private OutputStream mOutputStream;
    private SerialReadThread mReadThread = new SerialReadThread();
    private SerialPort mSerialPort;
    
    public SerialPort open(String paramString1, String paramString2) {
        LogUtils.e("打开串口");
//        if (this.mSerialPort != null) {
//            close();
//        }
//        try {
//            this.mSerialPort = new SerialPort(new File(paramString1), Integer.parseInt(paramString2), 0);
//            this.mReadThread.setInputStream(this.mSerialPort.getInputStream());
//            this.mReadThread.start();
//            this.mOutputStream = this.mSerialPort.getOutputStream();
//            return this.mSerialPort;
//        } catch (Throwable paramString) {
//            Log.e("SerialPortManager=", "打开串口失败", paramString);
//        }
        return null;
    }
    
    public static SerialPortManager getInstance() {
        LogUtils.e("get SerialPortManager");
        if (mSerialPortManager == null) {
            try {
                if (mSerialPortManager == null) {
                    mSerialPortManager = new SerialPortManager();
                }
//                mSerialPortManager.open("SerialPortManager.getInstance()/dev/ttyS0","38400");
            } catch (Exception e) {
                LogUtils.e("单例异常 " + e.getMessage());
            } finally {}
        }
        return mSerialPortManager;
    }
    
    private Observable<?> rxSendData(final byte[] paramArrayOfByte) {
        Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
//            public void subscribe(ObservableEmitter<Object> paramAnonymousObservableEmitter)
//                    throws Exception
                try {
                    SerialPortManager.this.sendData(paramArrayOfByte);
                } catch (Exception localException) {
                    localException.printStackTrace();
                    if (!emitter.isDisposed()) {
                        emitter.onError(localException);
                        return;
                    }
                }
                emitter.onNext(Boolean.valueOf(true));
                emitter.onComplete();
            }
        });
        return null;
    }
    
    private void sendData(byte[] paramArrayOfByte)
            throws Exception {
        this.mOutputStream.write(paramArrayOfByte);
    }
    
    public void close() {
        if (this.mSerialPort != null) {
            this.mSerialPort.close();
            this.mSerialPort = null;
        }
        if (this.mReadThread != null) {
            this.mReadThread.close();
        }
    }
    
    public SerialReadThread getReadThread() {
        return this.mReadThread;
    }
    
    
    public void sendCommand(CmdPack paramCmdPack) {
        String str = paramCmdPack.getPackHexStr();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("发送命令ABCD：");
        localStringBuilder.append(str);
        LogPlus.i(localStringBuilder.toString());
        Observable.just(paramCmdPack).subscribeOn(Schedulers.io()).flatMap(new Function() {
            @Override
            public Object apply(Object o) throws Exception {
//                return null;
//            }
//
//            public ObservableSource<?> apply(CmdPack paramAnonymousCmdPack)
//                    throws Exception
//            {
                return SerialPortManager.this.rxSendData(((CmdPack) o).pack());
            }
        }).subscribe(new Observer() {
            @Override
            public void onComplete() {}
            
            @Override
            public void onError(Throwable paramAnonymousThrowable) {
                Log.e("发送失败", paramAnonymousThrowable.toString());
            }
            
            @Override
            public void onNext(Object paramAnonymousObject) {}
            
            @Override
            public void onSubscribe(Disposable paramAnonymousDisposable) {}
        });
    }
}
