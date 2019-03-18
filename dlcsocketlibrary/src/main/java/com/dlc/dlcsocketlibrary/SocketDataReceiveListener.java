package com.dlc.dlcsocketlibrary;

public abstract interface SocketDataReceiveListener
{
    public abstract void onSocketDataReceive(String paramString, int paramInt, byte[] paramArrayOfByte);
}