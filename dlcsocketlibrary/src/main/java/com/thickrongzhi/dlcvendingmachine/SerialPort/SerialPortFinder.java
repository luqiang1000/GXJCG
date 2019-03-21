package com.thickrongzhi.dlcvendingmachine.SerialPort;

import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Iterator;
import java.util.Vector;

public class SerialPortFinder {
    private static final String TAG = "SerialPort";
    private Vector<Driver> mDrivers = null;
    Vector localVector;
    
    public String[] getAllDevices() {
        localVector = new Vector();
        try {
            Iterator localIterator1 = getDrivers().iterator();
            while (localIterator1.hasNext()) {
                Driver localDriver = (Driver) localIterator1.next();
                Iterator localIterator2 = localDriver.getDevices().iterator();
                while (localIterator2.hasNext()) {
                    localVector.add(String.format("%s (%s)", new Object[]{((File) localIterator2.next()).getName(), localDriver.getName()}));
                }
            }
            return (String[]) localVector.toArray(new String[localVector.size()]);
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
        return null;
    }
    
    public String[] getAllDevicesPath() {
        localVector = new Vector();
        try {
            Iterator localIterator1 = getDrivers().iterator();
            while (localIterator1.hasNext()) {
                Iterator localIterator2 = ((Driver) localIterator1.next()).getDevices().iterator();
                while (localIterator2.hasNext()) {
                    localVector.add(((File) localIterator2.next()).getAbsolutePath());
                }
            }
            return (String[]) localVector.toArray(new String[localVector.size()]);
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
        return null;
    }
    
    Vector<Driver> getDrivers()
            throws IOException {
        if (this.mDrivers == null) {
            this.mDrivers = new Vector();
            LineNumberReader localLineNumberReader = new LineNumberReader(new FileReader("/proc/tty/drivers"));
            for (; ; ) {
                Object localObject = localLineNumberReader.readLine();
                if (localObject == null) {
                    break;
                }
                String str = ((String) localObject).substring(0, 21).trim();
                String[] localObjectSA = ((String) localObject).split(" +");
                if ((localObjectSA.length >= 5) && (localObjectSA[(localObjectSA.length - 1)].equals("serial"))) {
                    StringBuilder localStringBuilder = new StringBuilder();
                    localStringBuilder.append("Found new driver ");
                    localStringBuilder.append(str);
                    localStringBuilder.append(" on ");
                    localStringBuilder.append(localObjectSA[(localObjectSA.length - 4)]);
                    Log.d("SerialPort", localStringBuilder.toString());
                    this.mDrivers.add(new Driver(str, localObjectSA[(localObjectSA.length - 4)]));
                }
            }
            localLineNumberReader.close();
        }
        return this.mDrivers;
    }
    
    public class Driver {
        private String mDeviceRoot;
        Vector<File> mDevices = null;
        private String mDriverName;
        
        public Driver(String paramString1, String paramString2) {
            this.mDriverName = paramString1;
            this.mDeviceRoot = paramString2;
        }
        
        public Vector<File> getDevices() {
            if (this.mDevices == null) {
                this.mDevices = new Vector();
                File[] arrayOfFile = new File("/dev").listFiles();
                if (arrayOfFile != null) {
                    int i = 0;
                    while (i < arrayOfFile.length) {
                        if (arrayOfFile[i].getAbsolutePath().startsWith(this.mDeviceRoot)) {
                            StringBuilder localStringBuilder = new StringBuilder();
                            localStringBuilder.append("Found new device: ");
                            localStringBuilder.append(arrayOfFile[i]);
                            Log.d("SerialPort", localStringBuilder.toString());
                            this.mDevices.add(arrayOfFile[i]);
                        }
                        i += 1;
                    }
                }
            }
            return this.mDevices;
        }
        
        public String getName() {
            return this.mDriverName;
        }
    }
}

