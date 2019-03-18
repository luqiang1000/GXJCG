package com.thickrongzhi.dlcvendingmachine.model;

import java.util.ArrayList;
import java.util.List;

public class SignalState
{
    public boolean auxiliary;
    public boolean auxiliaryTow;
    public boolean compressor;
    public String errorType;
    public boolean guardDoor;
    public boolean guardLock;
    public boolean lighting;
    public ArrayList<List<Boolean>> mMouths = new ArrayList();
    public boolean offGoods;
    public String pulse;
    public String pulseTow;
    public boolean shipmentState;
    public String shipmentType;
    public boolean signalAuxiliary;
    public boolean signalAuxiliaryTow;
    public String temperature;
    public String water;
    
    public String toString()
    {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("SignalState{water='");
        localStringBuilder.append(this.water);
        localStringBuilder.append('\'');
        localStringBuilder.append(", shipmentType='");
        localStringBuilder.append(this.shipmentType);
        localStringBuilder.append('\'');
        localStringBuilder.append(", errorType='");
        localStringBuilder.append(this.errorType);
        localStringBuilder.append('\'');
        localStringBuilder.append(", pulse='");
        localStringBuilder.append(this.pulse);
        localStringBuilder.append('\'');
        localStringBuilder.append(", pulseTow='");
        localStringBuilder.append(this.pulseTow);
        localStringBuilder.append('\'');
        localStringBuilder.append(", temperature='");
        localStringBuilder.append(this.temperature);
        localStringBuilder.append('\'');
        localStringBuilder.append(", shipmentState=");
        localStringBuilder.append(this.shipmentState);
        localStringBuilder.append(", lighting=");
        localStringBuilder.append(this.lighting);
        localStringBuilder.append(", compressor=");
        localStringBuilder.append(this.compressor);
        localStringBuilder.append(", auxiliary=");
        localStringBuilder.append(this.auxiliary);
        localStringBuilder.append(", auxiliaryTow=");
        localStringBuilder.append(this.auxiliaryTow);
        localStringBuilder.append(", guardLock=");
        localStringBuilder.append(this.guardLock);
        localStringBuilder.append(", signalAuxiliary=");
        localStringBuilder.append(this.signalAuxiliary);
        localStringBuilder.append(", guardDoor=");
        localStringBuilder.append(this.guardDoor);
        localStringBuilder.append(", signalAuxiliaryTow=");
        localStringBuilder.append(this.signalAuxiliaryTow);
        localStringBuilder.append(", offGoods=");
        localStringBuilder.append(this.offGoods);
        localStringBuilder.append(", mMouths=");
        localStringBuilder.append(this.mMouths);
        localStringBuilder.append('}');
        return localStringBuilder.toString();
    }
}
