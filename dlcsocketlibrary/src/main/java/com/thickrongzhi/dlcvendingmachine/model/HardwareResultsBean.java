package com.thickrongzhi.dlcvendingmachine.model;

public class HardwareResultsBean
{
    public AisleShipment mAisleShipment;
    public int mCommands;
    public SignalOutput mSignalOutput;
    public SignalState mSignalState;
    @Override
    public String toString()
    {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("HardwareResultsBean{mCommands=");
        localStringBuilder.append(this.mCommands);
        localStringBuilder.append(", mAisleShipment=");
        localStringBuilder.append(this.mAisleShipment);
        localStringBuilder.append(", mSignalOutput=");
        localStringBuilder.append(this.mSignalOutput);
        localStringBuilder.append(", mSignalState=");
        localStringBuilder.append(this.mSignalState);
        localStringBuilder.append('}');
        return localStringBuilder.toString();
    }
}
