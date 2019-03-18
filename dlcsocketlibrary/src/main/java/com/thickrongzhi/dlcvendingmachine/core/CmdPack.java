package com.thickrongzhi.dlcvendingmachine.core;

import android.text.TextUtils;
import com.thickrongzhi.dlcvendingmachine.uitils.ByteUtil;
import com.thickrongzhi.dlcvendingmachine.uitils.CRC16Utils;
import com.thickrongzhi.dlcvendingmachine.uitils.StringUtil;

public class CmdPack
{
    public String addr;
    public String address = "00";
    public String appdata = "";
    public int command;
    public String commandStr;
    public String crc16;
    public String packHexStr;
    public String ptrol = "01";
    public String res = "000000";
    public int slen;
    public String syn = "3BB3";
    public String version = "10";
    
    public static Builder newBuilder()
    {
        return new Builder();
    }
    
    public String getAppdata()
    {
        return this.appdata;
    }
    
    public int getCommand()
    {
        return this.command;
    }
    
    public String getCommandStr()
    {
        return this.commandStr;
    }
    
    public String getPackHexStr()
    {
        return this.packHexStr;
    }
    
    public int getSlen()
    {
        return this.slen;
    }
    
    public byte[] pack()
    {
        return ByteUtil.hexStr2bytes(this.packHexStr);
    }
    
    public void pack2HexStr()
    {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(this.syn);
        localStringBuilder.append(this.address);
        localStringBuilder.append(this.addr);
        localStringBuilder.append(ByteUtil.decimal2fitHex(this.slen, 2));
        localStringBuilder.append(this.commandStr);
        localStringBuilder.append(this.version);
        localStringBuilder.append(this.appdata);
        this.crc16 = CRC16Utils.getCRC(ByteUtil.hexStr2bytes(localStringBuilder.toString())).toUpperCase();
        localStringBuilder.append(this.crc16);
        this.packHexStr = localStringBuilder.toString();
    }
    
    public static class Builder
    {
        private CmdPack mCmdPack = new CmdPack();
        
        public Builder()
        {
            this.mCmdPack.slen = 2;
        }
        
        public CmdPack build()
        {
            this.mCmdPack.pack2HexStr();
            return this.mCmdPack;
        }
        
        public Builder setAddr(String paramString)
        {
            if (!TextUtils.isEmpty(paramString)) {
                this.mCmdPack.addr = StringUtil.getStringFitBits(paramString, 2, Character.valueOf('0'));
            }
            return this;
        }
        
        public Builder setAppdata(String paramString)
        {
            if (TextUtils.isEmpty(paramString)) {
                this.mCmdPack.appdata = "";
            } else {
                this.mCmdPack.appdata = paramString;
            }
            this.mCmdPack.slen = (this.mCmdPack.appdata.length() / 2 + 2);
            return this;
        }
        
        public Builder setAppdata(byte[] paramArrayOfByte)
        {
            if (paramArrayOfByte == null)
            {
                this.mCmdPack.slen = 2;
                return this;
            }
            this.mCmdPack.appdata = ByteUtil.bytes2HexStr(paramArrayOfByte);
            this.mCmdPack.slen = (this.mCmdPack.appdata.length() / 2 + 2);
            return this;
        }
        
        public Builder setCommand(int paramInt)
        {
            this.mCmdPack.command = paramInt;
            this.mCmdPack.commandStr = ByteUtil.decimal2fitHex(paramInt, 2);
            return this;
        }
    }
}
