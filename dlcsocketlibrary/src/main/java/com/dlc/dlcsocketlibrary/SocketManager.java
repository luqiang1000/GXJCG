package com.dlc.dlcsocketlibrary;

import android.util.Log;

public class SocketManager {
    private static SocketManager socketManager;
    
    public static SocketManager getSocketManager() {
        if (socketManager == null) {
            socketManager = new SocketManager();
        }
        return socketManager;
    }
    
    private void spm(String paramString) {
        Log.d("spm", paramString);
    }
    
    public DLCSocket newDlcSocket() {
        return new DLCSocket();
    }
}