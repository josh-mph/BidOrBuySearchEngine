package sohama.com.bidorbuysearchengine.Helpers;

import sohama.com.bidorbuysearchengine.interfaces.DataObserver;
import sohama.com.bidorbuysearchengine.interfaces.ObservableData;

public class BaseModel implements ObservableData {

    private DataObserver dataObserver;

    private static BaseModel instance = null;

    private BaseModel() {
    }

    public static BaseModel getInstance() {
        if (instance == null) {
            instance = new BaseModel();
        }
        return instance;
    }

    @Override
    public void registerDataObserver(DataObserver dataObserver) {
        this.dataObserver = dataObserver;
    }


    @Override
    public void triggerDataObserver(String operation) {
        dataObserver.updateContent(operation);
    }

}
