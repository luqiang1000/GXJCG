package com.thickrongzhi.dlcvendingmachine.SerialPort;

import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialPort
{
    private static final String DEFAULT_SU_PATH = "/system/bin/su";
    private static final String TAG = "SerialPort";
    private static String sSuPath = "/system/bin/su";
    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;
    
    static
    {
        System.loadLibrary("serial_port");
    }
    
    public SerialPort(File paramFile, int paramInt)
            throws SecurityException, IOException
    {
        this(paramFile, paramInt, 0);
    }
    
    public SerialPort(File paramFile, int paramInt1, int paramInt2)
            throws SecurityException, IOException
    {
        if ((!paramFile.canRead()) || (!paramFile.canWrite())) {}
        try
        {
            Process localProcess = Runtime.getRuntime().exec(sSuPath);
            Object localObject = new StringBuilder();
            ((StringBuilder)localObject).append("chmod 666 ");
            ((StringBuilder)localObject).append(paramFile.getAbsolutePath());
            ((StringBuilder)localObject).append("\nexit\n");
            localObject = ((StringBuilder)localObject).toString();
            localProcess.getOutputStream().write(((String)localObject).getBytes());
            if ((localProcess.waitFor() == 0) && (paramFile.canRead()))
            {
                boolean bool = paramFile.canWrite();
                if (bool)
                {
                    this.mFd = open(paramFile.getAbsolutePath(), paramInt1, paramInt2);
                    if (this.mFd != null)
                    {
                        this.mFileInputStream = new FileInputStream(this.mFd);
                        this.mFileOutputStream = new FileOutputStream(this.mFd);
                        return;
                    }
                    Log.e("SerialPort", "native open returns null");
                    throw new IOException();
                }
            }
            throw new SecurityException();
        }
        catch (Exception paramFile1)
        {
            paramFile1.printStackTrace();
            throw new SecurityException();
        }
    }
    
    public SerialPort(String paramString, int paramInt)
            throws SecurityException, IOException
    {
        this(new File(paramString), paramInt, 0);
    }
    
    public SerialPort(String paramString, int paramInt1, int paramInt2)
            throws SecurityException, IOException
    {
        this(new File(paramString), paramInt1, paramInt2);
    }
    
    private static native FileDescriptor open(String paramString, int paramInt1, int paramInt2);
    
    public static void setSuPath(String paramString)
    {
        if (paramString == null) {
            return;
        }
        sSuPath = paramString;
    }
    
    public native void close();
    
    public InputStream getInputStream()
    {
        return this.mFileInputStream;
    }
    
    public OutputStream getOutputStream()
    {
        return this.mFileOutputStream;
    }
}
