package com.thickrongzhi.dlcvendingmachine.uitils;

import android.util.Log;
import io.reactivex.annotations.Nullable;

public class LogPlus
{
    private static final int CURRENT_LOG_LEVEL = 3;
    private static final String PREFIX = "DLC_";
    
    public static void d(String paramString)
    {
        log(3, null, paramString, null);
    }
    
    public static void d(@Nullable String paramString1, String paramString2)
    {
        log(3, paramString1, paramString2, null);
    }
    
    public static void d(@Nullable String paramString1, String paramString2, @Nullable Throwable paramThrowable)
    {
        log(3, paramString1, paramString2, paramThrowable);
    }
    
    public static void d(String paramString, @Nullable Throwable paramThrowable)
    {
        log(3, null, paramString, paramThrowable);
    }
    
    public static void e(String paramString)
    {
        log(6, null, paramString, null);
    }
    
    public static void e(@Nullable String paramString1, String paramString2)
    {
        log(6, paramString1, paramString2, null);
    }
    
    public static void e(@Nullable String paramString1, String paramString2, @Nullable Throwable paramThrowable)
    {
        log(6, paramString1, paramString2, paramThrowable);
    }
    
    public static void e(String paramString, @Nullable Throwable paramThrowable)
    {
        log(6, null, paramString, paramThrowable);
    }
    
    public static void i(String paramString)
    {
        log(4, null, paramString, null);
    }
    
    public static void i(@Nullable String paramString1, String paramString2)
    {
        log(4, paramString1, paramString2, null);
    }
    
    public static void i(@Nullable String paramString1, String paramString2, @Nullable Throwable paramThrowable)
    {
        log(4, paramString1, paramString2, paramThrowable);
    }
    
    public static void i(String paramString, @Nullable Throwable paramThrowable)
    {
        log(4, null, paramString, paramThrowable);
    }
    
    private static void log(int paramInt, String paramString1, String paramString2, Throwable paramThrowable)
    {
        if (paramInt < 3) {
            return;
        }
        Object localObject = Thread.currentThread().getStackTrace()[4];
        String str1 = ((StackTraceElement)localObject).getFileName();
        int i = ((StackTraceElement)localObject).getLineNumber();
        String str2 = ((StackTraceElement)localObject).getMethodName();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(str2);
        localStringBuilder.append("(");
        localStringBuilder.append(str1);
        localStringBuilder.append(":");
        localStringBuilder.append(i);
        localStringBuilder.append(")");
        localStringBuilder.append(paramString2);
        paramString2 = localStringBuilder.toString();
        if ((paramString1 != null) && (paramString1.length() != 0) && (paramString1.trim().length() != 0))
        {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("DLC_");
            ((StringBuilder)localObject).append(paramString1);
            paramString1 = ((StringBuilder)localObject).toString();
        }
        else
        {
            paramString1 = ((StackTraceElement)localObject).getClassName();
            paramString1 = paramString1.substring(paramString1.lastIndexOf('.') + 1);
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("DLC_");
            ((StringBuilder)localObject).append(paramString1);
            paramString1 = ((StringBuilder)localObject).toString();
        }
        if (paramThrowable == null)
        {
            switch (paramInt)
            {
                case 6:
                    Log.e(paramString1, paramString2);
                    return;
                case 5:
                    Log.w(paramString1, paramString2);
                    return;
                case 4:
                    Log.i(paramString1, paramString2);
                    return;
                case 3:
                    Log.d(paramString1, paramString2);
                    return;
                default:
                    Log.v(paramString1,paramString2);
                    return;
            }
        }
        switch (paramInt)
        {
            case 6:
                Log.e(paramString1, paramString2, paramThrowable);
                return;
            case 5:
                Log.w(paramString1, paramString2, paramThrowable);
                return;
            case 4:
                Log.i(paramString1, paramString2, paramThrowable);
                return;
            case 3:
                Log.d(paramString1, paramString2, paramThrowable);
                return;
            default:
                Log.v(paramString1, paramString2, paramThrowable);
                return;
        }
    }
    
    public static void v(String paramString)
    {
        log(2, null, paramString, null);
    }
    
    public static void v(@Nullable String paramString1, String paramString2)
    {
        log(2, paramString1, paramString2, null);
    }
    
    public static void v(@Nullable String paramString1, String paramString2, @Nullable Throwable paramThrowable)
    {
        log(2, paramString1, paramString2, paramThrowable);
    }
    
    public static void v(String paramString, @Nullable Throwable paramThrowable)
    {
        log(2, null, paramString, paramThrowable);
    }
    
    public static void w(String paramString)
    {
        log(5, null, paramString, null);
    }
    
    public static void w(@Nullable String paramString1, String paramString2)
    {
        log(5, paramString1, paramString2, null);
    }
    
    public static void w(@Nullable String paramString1, String paramString2, @Nullable Throwable paramThrowable)
    {
        log(5, paramString1, paramString2, paramThrowable);
    }
    
    public static void w(String paramString, @Nullable Throwable paramThrowable)
    {
        log(5, null, paramString, paramThrowable);
    }
    
    public static void write(Throwable paramThrowable) {}
}
