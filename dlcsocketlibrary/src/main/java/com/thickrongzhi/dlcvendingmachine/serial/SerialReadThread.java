package com.thickrongzhi.dlcvendingmachine.serial;

import com.blankj.utilcode.util.LogUtils;
import com.thickrongzhi.dlcvendingmachine.interfaces.HardwareResults;
import com.thickrongzhi.dlcvendingmachine.model.AisleShipment;
import com.thickrongzhi.dlcvendingmachine.model.HardwareResultsBean;
import com.thickrongzhi.dlcvendingmachine.model.SignalOutput;
import com.thickrongzhi.dlcvendingmachine.model.SignalState;
import com.thickrongzhi.dlcvendingmachine.uitils.ByteUtil;
import com.thickrongzhi.dlcvendingmachine.uitils.CRC16Utils;
import com.thickrongzhi.dlcvendingmachine.uitils.LogPlus;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SerialReadThread
        extends Thread
{
    private int APPDATA_LOCATION;
    private int COMMAND_BYTE;
    private int COMMAND_LOCATION;
    private boolean CRC;
    private int CRC_BYTE;
    private byte FRAME_HEAD_0;
    private byte FRAME_HEAD_1;
    private int MIN_SIZE;
    private int SLEN;
    private int SLEN_BYTE;
    private byte[] buffer = new byte[8192];
    private int currentSize = 0;
    private HardwareResults mHardwareResults;
    private BufferedInputStream mInputStream;
    
    private HardwareResultsBean aisleShipment(int paramInt, String paramString)
    {
        HardwareResultsBean localHardwareResultsBean = new HardwareResultsBean();
        localHardwareResultsBean.mCommands = paramInt;
        if (paramInt != 185) {}
        Object localObject;
        switch (paramInt)
        {
//            default:
//                return localHardwareResultsBean;
            case 167:
                localObject = new SignalOutput();
                ((SignalOutput)localObject).signalName = paramString.substring(1, 2);
                ((SignalOutput)localObject).result = paramString.substring(3, paramString.length());
                localHardwareResultsBean.mSignalOutput = ((SignalOutput)localObject);
                return localHardwareResultsBean;
            case 166:
                localObject = new AisleShipment();
                ((AisleShipment)localObject).water = ByteUtil.hexStr2decimalStr(paramString.substring(0, 2));
                ((AisleShipment)localObject).aisleType = paramString.substring(3, 4);
                ((AisleShipment)localObject).liftMachine = paramString.substring(5, 6).equals("1");
                ((AisleShipment)localObject).againstTheft = paramString.substring(7, 8).equals("1");
                ((AisleShipment)localObject).numberType = paramString.substring(9, 10).equals("0");
                ((AisleShipment)localObject).number = ByteUtil.hexStr2decimalStr(paramString.substring(10, 12));
                ((AisleShipment)localObject).column = ByteUtil.hexStr2decimalStr(paramString.substring(12, 14));
                ((AisleShipment)localObject).control = paramString.substring(15, paramString.length());
                localHardwareResultsBean.mAisleShipment = ((AisleShipment)localObject);
                return localHardwareResultsBean;
            default:
            localObject = new SignalState();
            ((SignalState)localObject).water = ByteUtil.hexStr2decimalStr(paramString.substring(0, 2));
            ((SignalState)localObject).shipmentState = paramString.substring(3, 4).equals("0");
            ((SignalState)localObject).shipmentType = paramString.substring(5, 6);
            ((SignalState)localObject).errorType = paramString.substring(7, 8);
            ((SignalState)localObject).offGoods = paramString.substring(9, 10).equals("1");
            String str = ByteUtil.hexStr2BinArr(paramString.substring(10, 12));
            ((SignalState)localObject).lighting = str.substring(str.length() - 1).equals("1");
            ((SignalState)localObject).compressor = str.substring(str.length() - 2, str.length() - 1).equals("1");
            ((SignalState)localObject).auxiliary = str.substring(str.length() - 3, str.length() - 2).equals("1");
            ((SignalState)localObject).auxiliaryTow = str.substring(str.length() - 4, str.length() - 3).equals("1");
            ((SignalState)localObject).guardLock = str.substring(str.length() - 5, str.length() - 4).equals("1");
            str = ByteUtil.hexStr2BinArr(paramString.substring(12, 14));
            ((SignalState)localObject).signalAuxiliary = str.substring(0, 1).equals("1");
            ((SignalState)localObject).guardDoor = str.substring(1, 2).equals("1");
            ((SignalState)localObject).signalAuxiliaryTow = str.substring(2, 3).equals("1");
            ((SignalState)localObject).pulse = ByteUtil.hexStr2decimalStr(paramString.substring(14, 16));
            ((SignalState)localObject).pulseTow = ByteUtil.hexStr2decimalStr(paramString.substring(16, 18));
            ((SignalState)localObject).temperature = ByteUtil.convertHexToString(paramString.substring(18, 28)).trim();
            ((SignalState)localObject).mMouths.add(getMouths(paramString.substring(30, 32), paramString.substring(28, 30)));
            ((SignalState)localObject).mMouths.add(getMouths(paramString.substring(34, 36), paramString.substring(32, 34)));
            ((SignalState)localObject).mMouths.add(getMouths(paramString.substring(38, 40), paramString.substring(36, 38)));
            ((SignalState)localObject).mMouths.add(getMouths(paramString.substring(42, 44), paramString.substring(40, 42)));
            ((SignalState)localObject).mMouths.add(getMouths(paramString.substring(46, 48), paramString.substring(44, 46)));
            ((SignalState)localObject).mMouths.add(getMouths(paramString.substring(50, 52), paramString.substring(48, 50)));
            ((SignalState)localObject).mMouths.add(getMouths(paramString.substring(54, 56), paramString.substring(52, 54)));
            ((SignalState)localObject).mMouths.add(getMouths(paramString.substring(58, 60), paramString.substring(56, 58)));
            ((SignalState)localObject).mMouths.add(getMouths(paramString.substring(62, 64), paramString.substring(60, 62)));
            ((SignalState)localObject).mMouths.add(getMouths(paramString.substring(66, 68), paramString.substring(64, 66)));
            localHardwareResultsBean.mSignalState = ((SignalState)localObject);
        }
        return localHardwareResultsBean;
    }
    
    private List<Boolean> getMouths(String paramString1, String paramString2)
    {
        ArrayList localArrayList = new ArrayList();
        paramString1 = ByteUtil.hexStr2BinArr(paramString1);
        paramString2 = ByteUtil.hexStr2BinArr(paramString2);
        int i = paramString2.length();
        while (i > 0)
        {
            int k = i - 1;
            localArrayList.add(Boolean.valueOf(paramString2.substring(k, i).equals("0")));
            if (i == 1)
            {
                int j = paramString1.length();
                while (j > 4)
                {
                    localArrayList.add(Boolean.valueOf(paramString1.substring(k, i).equals("0")));
                    j -= 1;
                }
            }
            i -= 1;
        }
        return localArrayList;
    }
    
    private void onDataReceive(byte[] paramArrayOfByte, int paramInt){
//        try
//        {
            LogUtils.e("onDataReceive:"+ByteUtil.bytes2HexStr(paramArrayOfByte));
//            System.arraycopy(paramArrayOfByte, 0, this.buffer, this.currentSize, paramInt);
//            this.currentSize += paramInt;
//            paramInt = 0;
//            int i;
//            label352:
//            for (;;)
//            {
//                i = paramInt;
//                if (paramInt >= this.currentSize) {
//                    break label355;
//                }
//                i = paramInt;
//                if (this.currentSize - paramInt < this.MIN_SIZE) {
//                    break label355;
//                }
//                paramArrayOfByte = this.buffer;
//                i = paramInt + 1;
//                if (paramArrayOfByte[paramInt] != this.FRAME_HEAD_0)
//                {
//                    paramInt = i;
//                }
//                else
//                {
//                    paramArrayOfByte = this.buffer;
//                    paramInt = i + 1;
//                    if (paramArrayOfByte[i] == this.FRAME_HEAD_1)
//                    {
//                        i = paramInt - 2;
//                        int j = (int)ByteUtil.hexStr2decimal(ByteUtil.bytes2HexStr(this.buffer, this.SLEN + i, this.SLEN_BYTE));
//                        int k = this.MIN_SIZE + j - this.COMMAND_BYTE;
//                        int m = i + k;
//                        if (this.currentSize >= m)
//                        {
//                            String str = ByteUtil.bytes2HexStr(this.buffer, m - this.CRC_BYTE, this.CRC_BYTE);
//                            paramArrayOfByte = new byte[k - this.CRC_BYTE];
//                            System.arraycopy(this.buffer, i, paramArrayOfByte, 0, paramArrayOfByte.length);
//                            String  paramArrayOfByteS;
//                            if (this.CRC) {
//                                paramArrayOfByteS = CRC16Utils.getCRC(paramArrayOfByte);
//                            } else {
//                                paramArrayOfByteS = CRC16Utils.getCRC16(paramArrayOfByte);
//                            }
//                            if (!str.equals(paramArrayOfByteS)) {
//                                break label352;
//                            }
//                            processReceiveData(ByteUtil.bytes2HexStr(this.buffer, this.COMMAND_LOCATION + i, this.COMMAND_BYTE), ByteUtil.bytes2HexStr(this.buffer, this.APPDATA_LOCATION + i, j - 2), ByteUtil.bytes2HexStr(this.buffer, i, k));
//                            if (this.currentSize > m)
//                            {
//                                paramArrayOfByte = new byte[this.currentSize - m];
//                                System.arraycopy(this.buffer, m, paramArrayOfByte, 0, paramArrayOfByte.length);
//                                System.arraycopy(paramArrayOfByte, 0, this.buffer, 0, paramArrayOfByte.length);
//                                this.currentSize -= m;
//                                break;
//                            }
//                            if (this.currentSize != m) {
//                                break label352;
//                            }
//                            this.currentSize = 0;
//                        }
//                        i = paramInt;
//                        break label355;
//                    }
//                }
//            }
//            label355:
//            if (i == this.currentSize) {
//                this.currentSize = 0;
//            }
//            return;
//        }
//        catch (Exception paramArrayOfByte1)
//        {
//            this.currentSize = 0;
//            this.mHardwareResults.hardwareError(paramArrayOfByte1.toString());
//        }
    }
    
    private void processReceiveData(final String paramString1, final String paramString2, final String paramString3)
    {
        Observable.unsafeCreate(new ObservableSource()
        {
            @Override
            public void subscribe(Observer observer) {
//            public void subscribe(Observer<? super HardwareResultsBean> paramAnonymousObserver){
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append("分发器处理=：");
                localStringBuilder.append(paramString1);
                localStringBuilder.append("=");
                localStringBuilder.append(paramString2);
                localStringBuilder.append("=");
                localStringBuilder.append(paramString3);
                LogPlus.e(localStringBuilder.toString());
                try
                {
                    int i = (int)ByteUtil.hexStr2decimal(paramString1);
                    observer.onNext(SerialReadThread.this.aisleShipment(i, paramString2));
                    observer.onComplete();
                    return;
                }
                catch (Exception localException)
                {
                    observer.onError(localException);
                    observer.onComplete();
                    LogPlus.e(localException.toString());
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer()
        {
            @Override
            public void onComplete() {}
            @Override
            public void onError(Throwable paramAnonymousThrowable)
            {
                SerialReadThread.this.mHardwareResults.hardwareError(paramAnonymousThrowable.toString());
            }
    
    
            @Override
            public void onNext(Object paramAnonymousHardwareResultsBean)
            {
                SerialReadThread.this.mHardwareResults.hardwareResults((HardwareResultsBean)paramAnonymousHardwareResultsBean);
            }
            @Override
            public void onSubscribe(Disposable paramAnonymousDisposable) {}
        });
    }
    
    public void close()
    {
        interrupt();
        try
        {
            this.mInputStream.close();
            return;
        }
        catch (IOException localIOException)
        {
            localIOException.printStackTrace();
        }
    }
    @Override
    public void run()
    {
        super.run();
        byte[] arrayOfByte = new byte[2048];
        while (!isInterrupted()) {
            try
            {
                int i = this.mInputStream.read(arrayOfByte);
                if (i > 0) {
                    onDataReceive(arrayOfByte, i);
                }
            }
            catch (IOException localIOException)
            {
                this.mHardwareResults.hardwareError(localIOException.toString());
                return;
            }
        }
    }
    
    public SerialReadThread setAppdataLocation(int paramInt)
    {
        this.APPDATA_LOCATION = paramInt;
        return this;
    }
    
    public SerialReadThread setCommandByte(int paramInt1, int paramInt2)
    {
        this.COMMAND_BYTE = paramInt1;
        this.COMMAND_LOCATION = paramInt2;
        return this;
    }
    
    public SerialReadThread setCrcByte(int paramInt, boolean paramBoolean)
    {
        this.CRC = paramBoolean;
        this.CRC_BYTE = paramInt;
        return this;
    }
    
    public SerialReadThread setFrameHead(byte paramByte1, byte paramByte2)
    {
        this.FRAME_HEAD_0 = paramByte1;
        this.FRAME_HEAD_1 = paramByte2;
        return this;
    }
    
    public void setHardwareResults(HardwareResults paramHardwareResults)
    {
        this.mHardwareResults = paramHardwareResults;
    }
    
    public void setInputStream(InputStream paramInputStream)
    {
        this.mInputStream = new BufferedInputStream(paramInputStream);
    }
    
    public SerialReadThread setMinSize(int paramInt)
    {
        this.MIN_SIZE = paramInt;
        return this;
    }
    
    public SerialReadThread setSlen(int paramInt1, int paramInt2)
    {
        this.SLEN = paramInt1;
        this.SLEN_BYTE = paramInt2;
        return this;
    }
}
