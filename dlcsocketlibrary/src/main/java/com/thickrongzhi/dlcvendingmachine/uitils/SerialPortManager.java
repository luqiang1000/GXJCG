package com.thickrongzhi.dlcvendingmachine.uitils;

import android.util.Log;

import com.thickrongzhi.dlcvendingmachine.SerialPort.SerialPort;
import com.thickrongzhi.dlcvendingmachine.core.CmdPack;
import com.thickrongzhi.dlcvendingmachine.serial.SerialReadThread;

import java.io.File;
import java.io.OutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SerialPortManager
{
    private static final String TAG = "SerialPortManager=";
    public static SerialPortManager mSerialPortManager;
    private OutputStream mOutputStream;
    private SerialReadThread mReadThread = new SerialReadThread();
    private SerialPort mSerialPort;
    
    public static SerialPortManager getInstance()
    {
        if (mSerialPortManager == null) {
            try
            {
                if (mSerialPortManager == null) {
                    mSerialPortManager = new SerialPortManager();
                }
            }
            finally {}
        }
        return mSerialPortManager;
    }
    
    private Observable<?> rxSendData(final byte[] paramArrayOfByte)
    {
        Observable.create(new ObservableOnSubscribe()
        {
            public void subscribe(ObservableEmitter<Object> paramAnonymousObservableEmitter)
                    throws Exception
            {
                try
                {
                    SerialPortManager.this.sendData(paramArrayOfByte);
                }
                catch (Exception localException)
                {
                    localException.printStackTrace();
                    if (!paramAnonymousObservableEmitter.isDisposed())
                    {
                        paramAnonymousObservableEmitter.onError(localException);
                        return;
                    }
                }
                paramAnonymousObservableEmitter.onNext(Boolean.valueOf(true));
                paramAnonymousObservableEmitter.onComplete();
            }
        });
    }
    
    private void sendData(byte[] paramArrayOfByte)
            throws Exception
    {
        this.mOutputStream.write(paramArrayOfByte);
    }
    
    public void close()
    {
        if (this.mSerialPort != null)
        {
            this.mSerialPort.close();
            this.mSerialPort = null;
        }
        if (this.mReadThread != null) {
            this.mReadThread.close();
        }
    }
    
    public SerialReadThread getReadThread()
    {
        return this.mReadThread;
    }
    
    public SerialPort open(String paramString1, String paramString2)
    {
        if (this.mSerialPort != null) {
            close();
        }
        try
        {
            this.mSerialPort = new SerialPort(new File(paramString1), Integer.parseInt(paramString2), 0);
            this.mReadThread.setInputStream(this.mSerialPort.getInputStream());
            this.mReadThread.start();
            this.mOutputStream = this.mSerialPort.getOutputStream();
            paramString1 = this.mSerialPort;
            return paramString1;
        }
        catch (Throwable paramString1)
        {
            Log.e("SerialPortManager=", "打开串口失败", paramString1);
        }
        return null;
    }
    
    public void sendCommand(CmdPack paramCmdPack)
    {
        String str = paramCmdPack.getPackHexStr();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("发送命令ABCD：");
        localStringBuilder.append(str);
        LogPlus.i(localStringBuilder.toString());
        Observable.just(paramCmdPack).subscribeOn(Schedulers.io()).flatMap(new Function()
        {
            public ObservableSource<?> apply(CmdPack paramAnonymousCmdPack)
                    throws Exception
            {
                return SerialPortManager.this.rxSendData(paramAnonymousCmdPack.pack());
            }
        }).subscribe(new Observer()
        {
            public void onComplete() {}
            
            public void onError(Throwable paramAnonymousThrowable)
            {
                Log.e("发送失败", paramAnonymousThrowable.toString());
            }
            
            public void onNext(Object paramAnonymousObject) {}
            
            public void onSubscribe(Disposable paramAnonymousDisposable) {}
        });
    }
}
