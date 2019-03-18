package com.thickrongzhi.dlcvendingmachine.interfaces;

import com.thickrongzhi.dlcvendingmachine.model.HardwareResultsBean;

public abstract interface HardwareResults
{
    public abstract void hardwareError(String paramString);
    
    public abstract void hardwareResults(HardwareResultsBean paramHardwareResultsBean);
}
