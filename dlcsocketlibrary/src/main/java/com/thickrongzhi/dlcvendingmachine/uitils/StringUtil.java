package com.thickrongzhi.dlcvendingmachine.uitils;

import java.util.Arrays;

public class StringUtil
{
    public static byte[] arrayCopy(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
        byte[] arrayOfByte = new byte[paramInt2];
        System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
        return arrayOfByte;
    }
    
    public static byte[] arrayCopy(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    {
        byte[] arrayOfByte = new byte[paramArrayOfByte1.length + paramArrayOfByte2.length];
        System.arraycopy(paramArrayOfByte1, 0, arrayOfByte, 0, paramArrayOfByte1.length);
        System.arraycopy(paramArrayOfByte2, paramArrayOfByte1.length, arrayOfByte, 0, paramArrayOfByte2.length);
        return arrayOfByte;
    }
    
    public static byte[] concatAll(byte[] paramArrayOfByte, byte[]... paramVarArgs)
    {
        int j = paramArrayOfByte.length;
        int k = paramVarArgs.length;
        int i = 0;
        while (i < k)
        {
            j += paramVarArgs[i].length;
            i += 1;
        }
        byte[] arrayOfByte = Arrays.copyOf(paramArrayOfByte, j);
        j = paramArrayOfByte.length;
        k = paramVarArgs.length;
        i = 0;
        while (i < k)
        {
            paramArrayOfByte = paramVarArgs[i];
            System.arraycopy(paramArrayOfByte, 0, arrayOfByte, j, paramArrayOfByte.length);
            j += paramArrayOfByte.length;
            i += 1;
        }
        return arrayOfByte;
    }
    
    public static String getStringFitBits(String paramString, int paramInt, Object paramObject)
    {
        StringBuilder paramStringsb = new StringBuilder(paramString);
        while (paramStringsb.length() < paramInt) {
            paramStringsb.insert(0, paramObject);
        }
        return paramStringsb.toString();
    }
    
    public static String join(Object paramObject, Object... paramVarArgs)
    {
        paramObject = new StringBuffer(paramObject.toString());
        int j = paramVarArgs.length;
        int i = 0;
        while (i < j)
        {
            ((StringBuffer)paramObject).append(paramVarArgs[i].toString());
            i += 1;
        }
        return ((StringBuffer)paramObject).toString();
    }
    
    public static String join(String paramString, String... paramVarArgs)
    {
        StringBuffer paramStringsb = new StringBuffer(paramString);
        int j = paramVarArgs.length;
        int i = 0;
        while (i < j)
        {
            paramStringsb.append(paramVarArgs[i]);
            i += 1;
        }
        return paramStringsb.toString();
    }
}
