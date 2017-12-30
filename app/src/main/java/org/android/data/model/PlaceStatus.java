package org.android.data.model;

import android.support.annotation.NonNull;

/**
 * Created by mehdi on 28/12/2017.
 */

public class PlaceStatus {


    public boolean isOpen = true;
    public boolean isMaintenance = false;
    public String MaintenanceDesc = "";


    public PlaceStatus(boolean isOpen, boolean isMaintenance,String maintenanceDesc) {

        this.isOpen = isOpen;
        this.isMaintenance = isMaintenance;
        this.MaintenanceDesc = maintenanceDesc;

    }


    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setMaintenanceDesc(String maintenanceDesc) {
        MaintenanceDesc = maintenanceDesc;
    }

    public void setMaintenance(boolean maintenance) {
        isMaintenance = maintenance;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isMaintenance() {
        return isMaintenance;
    }

    public String getMaintenanceDesc() {
        return MaintenanceDesc;
    }
}
