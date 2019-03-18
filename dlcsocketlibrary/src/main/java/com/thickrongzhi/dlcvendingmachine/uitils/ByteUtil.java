package com.thickrongzhi.dlcvendingmachine.uitils;

import android.os.SystemClock;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class ByteUtil
{
    private static String[] binaryArray = { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111" };
    private static String hexStr = "0123456789ABCDEF";
    
    public static String bytes2BinStr(byte[] paramArrayOfByte)
    {
        String str = "";
        int j = paramArrayOfByte.length;
        int i = 0;
        while (i < j)
        {
            int k = paramArrayOfByte[i];
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append(str);
            localStringBuilder.append(binaryArray[((k & 0xF0) >> 4)]);
            str = localStringBuilder.toString();
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(str);
            localStringBuilder.append(binaryArray[(k & 0xF)]);
            str = localStringBuilder.toString();
            i += 1;
        }
        return str;
    }
    
    public static String bytes2HexStr(byte[] paramArrayOfByte)
    {
        StringBuilder localStringBuilder = new StringBuilder();
        if ((paramArrayOfByte != null) && (paramArrayOfByte.length > 0))
        {
            char[] arrayOfChar = new char[2];
            int i = 0;
            while (i < paramArrayOfByte.length)
            {
                arrayOfChar[0] = Character.forDigit(paramArrayOfByte[i] >>> 4 & 0xF, 16);
                arrayOfChar[1] = Character.forDigit(paramArrayOfByte[i] & 0xF, 16);
                localStringBuilder.append(arrayOfChar);
                i += 1;
            }
            return localStringBuilder.toString().toUpperCase();
        }
        return "";
    }
    
    public static String bytes2HexStr(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
        byte[] arrayOfByte = new byte[paramInt2];
        System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
        return bytes2HexStr(arrayOfByte);
    }
    
    public static String convertHexToString(String paramString)
    {
        StringBuilder localStringBuilder1 = new StringBuilder();
        StringBuilder localStringBuilder2 = new StringBuilder();
        int j;
        for (int i = 0; i < paramString.length() - 1; i = j)
        {
            j = i + 2;
            i = Integer.parseInt(paramString.substring(i, j), 16);
            localStringBuilder1.append((char)i);
            localStringBuilder2.append(i);
        }
        return localStringBuilder1.toString();
    }
    
    public static String decimal2fitHex(long paramLong)
    {
        String str = Long.toHexString(paramLong).toUpperCase();
        if (str.length() % 2 != 0)
        {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("0");
            localStringBuilder.append(str);
            return localStringBuilder.toString();
        }
        return str.toUpperCase();
    }
    
    public static String decimal2fitHex(long paramLong, int paramInt)
    {
        StringBuilder localStringBuilder = new StringBuilder(Long.toHexString(paramLong).toUpperCase());
        while (localStringBuilder.length() < paramInt) {
            localStringBuilder.insert(0, '0');
        }
        return localStringBuilder.toString();
    }
    
    public static String fitDecimalStr(int paramInt1, int paramInt2)
    {
        StringBuilder localStringBuilder = new StringBuilder(String.valueOf(paramInt1));
        while (localStringBuilder.length() < paramInt2) {
            localStringBuilder.insert(0, "0");
        }
        return localStringBuilder.toString();
    }
    
    private static int hexChar2byte(char paramChar)
    {
        switch (paramChar)
        {
            default:
                switch (paramChar)
                {
                    default:
                        switch (paramChar)
                        {
                            default:
                                return -1;
                        }
                    case 'F':
                        return 15;
                    case 'E':
                        return 14;
                    case 'D':
                        return 13;
                    case 'C':
                        return 12;
                    case 'B':
                        return 11;
                }
                return 10;
            case '9':
                return 9;
            case '8':
                return 8;
            case '7':
                return 7;
            case '6':
                return 6;
            case '5':
                return 5;
            case '4':
                return 4;
            case '3':
                return 3;
            case '2':
                return 2;
            case '1':
                return 1;
        }
        return 0;
    }
    
    public static String hexStr2BinArr(String paramString)
    {
        int j = paramString.length() / 2;
        byte[] arrayOfByte = new byte[j];
        int i = 0;
        while (i < j)
        {
            String str = hexStr;
            int k = i * 2;
            arrayOfByte[i] = ((byte)((byte)(str.indexOf(paramString.charAt(k)) << 4) | (byte)hexStr.indexOf(paramString.charAt(k + 1))));
            i += 1;
        }
        return bytes2BinStr(arrayOfByte);
    }
    
    public static byte[] hexStr2bytes(String paramString)
    {
        int j = paramString.length() / 2;
        byte[] arrayOfByte = new byte[j];
        paramString = paramString.toUpperCase().toCharArray();
        int i = 0;
        while (i < j)
        {
            int k = i * 2;
            int m = hexChar2byte(paramString[k]);
            arrayOfByte[i] = ((byte)(hexChar2byte(paramString[(k + 1)]) | m << 4));
            i += 1;
        }
        return arrayOfByte;
    }
    
    public static long hexStr2decimal(String paramString)
    {
        return Long.parseLong(paramString, 16);
    }
    
    public static String hexStr2decimalStr(String paramString)
    {
        return new BigInteger(paramString, 16).toString(10);
    }
    
    public static String hexStr2decimalString(String paramString, int paramInt)
    {
        paramString = new StringBuilder(String.valueOf(Long.parseLong(paramString, 16)));
        while (paramString.length() < paramInt) {
            paramString.insert(0, '0');
        }
        return paramString.toString();
    }
    
    public static String hexString(String paramString, int paramInt)
    {
        Object localObject2 = "";
        localObject1 = localObject2;
        try
        {
            byte[] arrayOfByte = paramString.getBytes("utf-8");
            localObject1 = localObject2;
            int i = arrayOfByte.length - 1;
            paramString = (String)localObject2;
            for (;;)
            {
                localObject1 = paramString;
                if (i < 0) {
                    break;
                }
                localObject1 = paramString;
                Object localObject3 = Integer.toHexString(arrayOfByte[i] & 0xFF);
                localObject2 = localObject3;
                localObject1 = paramString;
                if (((String)localObject3).length() == 1)
                {
                    localObject1 = paramString;
                    localObject2 = new StringBuilder();
                    localObject1 = paramString;
                    ((StringBuilder)localObject2).append('0');
                    localObject1 = paramString;
                    ((StringBuilder)localObject2).append((String)localObject3);
                    localObject1 = paramString;
                    localObject2 = ((StringBuilder)localObject2).toString();
                }
                localObject1 = paramString;
                localObject3 = new StringBuilder();
                localObject1 = paramString;
                ((StringBuilder)localObject3).append(paramString);
                localObject1 = paramString;
                ((StringBuilder)localObject3).append(((String)localObject2).toUpperCase());
                localObject1 = paramString;
                paramString = ((StringBuilder)localObject3).toString();
                i -= 1;
            }
            return (String)localObject1;
        }
        catch (UnsupportedEncodingException paramString)
        {
            paramString.printStackTrace();
            while (((String)localObject1).length() < paramInt)
            {
                paramString = new StringBuilder();
                paramString.append("0");
                paramString.append((String)localObject1);
                localObject1 = paramString.toString();
            }
        }
    }
    
    public static String str2HexString(String paramString)
    {
        char[] arrayOfChar = "0123456789ABCDEF".toCharArray();
        StringBuilder localStringBuilder = new StringBuilder();
        try
        {
            paramString = paramString.getBytes("utf8");
        }
        catch (Exception paramString)
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
    
    public static void timeTest()
    {
        String str = decimal2fitHex(SystemClock.uptimeMillis(), 8);
        PrintStream localPrintStream = System.out;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("timeTest: hexTime: ");
        localStringBuilder.append(str);
        localPrintStream.println(localStringBuilder.toString());
    }
}
