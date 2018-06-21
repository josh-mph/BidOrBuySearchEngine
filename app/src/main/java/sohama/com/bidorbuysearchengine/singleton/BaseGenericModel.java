package sohama.com.bidorbuysearchengine.singleton;

import sohama.com.bidorbuysearchengine.interfaces.GenericObserver;
import sohama.com.bidorbuysearchengine.interfaces.ObservableGeneric;

public class BaseGenericModel implements ObservableGeneric {

    private static BaseGenericModel instance = null;

    protected BaseGenericModel() {
        // Exists only to defeat instantiation.
    }

    public static BaseGenericModel getInstance() {
        if (instance == null) {
            instance = new BaseGenericModel();
        }
        return instance;
    }

    GenericObserver genericObserver;

    @Override
    public void hideDialog() {
        this.genericObserver.manageDialog(false);
    }

    @Override
    public void registerGenericObserver(GenericObserver genericObserver) {
        this.genericObserver = genericObserver;
    }

    @Override
    public void showDialog() {
        this.genericObserver.manageDialog(true);
    }

    @Override
    public void manageOfflineTextView() {
        if (this.genericObserver != null) {
            this.genericObserver.updateOfflineStatus();
        }
    }

}
