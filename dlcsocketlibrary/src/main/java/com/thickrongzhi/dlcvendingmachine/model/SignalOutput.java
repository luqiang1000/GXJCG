package com.thickrongzhi.dlcvendingmachine.model;

public class SignalOutput
{
    public String result;
    public String signalName;
    @Override
    public String toString()
    {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("SignalOutput{signalName='");
        localStringBuilder.append(this.signalName);
        localStringBuilder.append('\'');
        localStringBuilder.append(", result='");
        localStringBuilder.append(this.result);
        localStringBuilder.append('\'');
        localStringBuilder.append('}');
        return localStringBuilder.toString();
    }
}
