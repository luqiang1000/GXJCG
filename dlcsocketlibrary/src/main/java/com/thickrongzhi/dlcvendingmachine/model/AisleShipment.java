package com.thickrongzhi.dlcvendingmachine.model;

public class AisleShipment
{
    public boolean againstTheft;
    public String aisleType;
    public String column;
    public String control;
    public boolean liftMachine;
    public String number;
    public boolean numberType;
    public String water;
    
    public String toString()
    {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("AisleShipment{water='");
        localStringBuilder.append(this.water);
        localStringBuilder.append('\'');
        localStringBuilder.append(", aisleType='");
        localStringBuilder.append(this.aisleType);
        localStringBuilder.append('\'');
        localStringBuilder.append(", number='");
        localStringBuilder.append(this.number);
        localStringBuilder.append('\'');
        localStringBuilder.append(", column='");
        localStringBuilder.append(this.column);
        localStringBuilder.append('\'');
        localStringBuilder.append(", control='");
        localStringBuilder.append(this.control);
        localStringBuilder.append('\'');
        localStringBuilder.append(", liftMachine=");
        localStringBuilder.append(this.liftMachine);
        localStringBuilder.append(", againstTheft=");
        localStringBuilder.append(this.againstTheft);
        localStringBuilder.append(", numberType=");
        localStringBuilder.append(this.numberType);
        localStringBuilder.append('}');
        return localStringBuilder.toString();
    }
}
