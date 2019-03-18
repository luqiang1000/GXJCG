package com.thickrongzhi.dlcvendingmachine.uitils;

import java.io.UnsupportedEncodingException;

public class CRC16Utils
{
    public static byte[] HexString2Bytes(String paramString)
    {
        if ((paramString != null) && (paramString.length() != 0))
        {
            byte[] arrayOfByte = new byte[paramString.length() / 2];
            paramString = paramString.getBytes();
            int i = 0;
            while (i < paramString.length / 2)
            {
                int j = i * 2;
                arrayOfByte[i] = uniteBytes(paramString[j], paramString[(j + 1)]);
                i += 1;
            }
            return arrayOfByte;
        }
        return null;
    }
    
    public static String getCRC(byte[] paramArrayOfByte)
    {
        byte[] arrayOfByte = new byte[1];
        int i = 0;
        while (i < paramArrayOfByte.length - 1)
        {
            if (i == 0) {
                arrayOfByte[0] = ((byte)(paramArrayOfByte[i] ^ paramArrayOfByte[(i + 1)]));
            } else {
                arrayOfByte[0] = ((byte)(arrayOfByte[0] ^ paramArrayOfByte[(i + 1)]));
            }
            i += 1;
        }
        return ByteUtil.bytes2HexStr(arrayOfByte);
    }
    
    public static String getCRC16(String paramString)
    {
        paramString = stringToHexByte(paramString);
        int i2 = paramString.length;
        int j = 0;
        int i = 41452;
        while (j < i2)
        {
            int i3 = paramString[j];
            int k = 0;
            while (k < 8)
            {
                int n = 1;
                int m;
                if ((i3 >> 7 - k & 0x1) == 1) {
                    m = 1;
                } else {
                    m = 0;
                }
                if ((i >> 15 & 0x1) != 1) {
                    n = 0;
                }
                int i1 = i << 1;
                i = i1;
                if ((m ^ n) != 0) {
                    i = i1 ^ 0x1021;
                }
                k += 1;
            }
            j += 1;
        }
        paramString = new StringBuilder(Integer.toHexString(0xFFFF & i));
        while (paramString.length() < 4) {
            paramString.insert(0, "0");
        }
        return paramString.toString().toUpperCase();
    }
    
    public static String getCRC16(byte[] paramArrayOfByte)
    {
        int i2 = paramArrayOfByte.length;
        int j = 0;
        int i = 41452;
        while (j < i2)
        {
            int i3 = paramArrayOfByte[j];
            int k = 0;
            while (k < 8)
            {
                int n = 1;
                int m;
                if ((i3 >> 7 - k & 0x1) == 1) {
                    m = 1;
                } else {
                    m = 0;
                }
                if ((i >> 15 & 0x1) != 1) {
                    n = 0;
                }
                int i1 = i << 1;
                i = i1;
                if ((m ^ n) != 0) {
                    i = i1 ^ 0x1021;
                }
                k += 1;
            }
            j += 1;
        }
        paramArrayOfByte = new StringBuilder(Integer.toHexString(0xFFFF & i));
        while (paramArrayOfByte.length() < 4) {
            paramArrayOfByte.insert(0, "0");
        }
        return paramArrayOfByte.toString().toUpperCase();
    }
    
    public static String str2HexStr(String paramString)
    {
        char[] arrayOfChar = "0123456789ABCDEF".toCharArray();
        StringBuilder localStringBuilder = new StringBuilder("");
        try
        {
            paramString = paramString.getBytes("gb2312");
        }
        catch (UnsupportedEncodingException paramString)
        {
            paramString.printStackTrace();
            paramString = null;
        }
        int i = 0;
        while (i < paramString.length)
        {
            localStringBuilder.append(arrayOfChar[((paramString[i] & 0xF0) >> 4)]);
            localStringBuilder.append(arrayOfChar[(paramString[i] & 0xF)]);
            i += 1;
        }
        return localStringBuilder.toString();
    }
    
    public static byte[] stringToHexByte(String paramString)
    {
        int j = paramString.length() / 2;
        byte[] arrayOfByte = new byte[j];
        paramString = paramString.toCharArray();
        int i = 0;
        while (i < j)
        {
            int k = i * 2;
            int m = toByte(paramString[k]);
            arrayOfByte[i] = ((byte)(toByte(paramString[(k + 1)]) | m << 4));
            i += 1;
        }
        return arrayOfByte;
    }
    
    private static int toByte(char paramChar)
    {
        return (byte)"0123456789ABCDEF".indexOf(paramChar);
    }
    
    public static byte uniteBytes(byte paramByte1, byte paramByte2)
    {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("0x");
        localStringBuilder.append(new String(new byte[] { paramByte1 }));
        int i = (byte)(Byte.decode(localStringBuilder.toString()).byteValue() << 4);
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("0x");
        localStringBuilder.append(new String(new byte[] { paramByte2 }));
        return (byte)(i ^ Byte.decode(localStringBuilder.toString()).byteValue());
    }
}
