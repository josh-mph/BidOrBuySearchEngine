package sohama.com.bidorbuysearchengine.interfaces;

public interface ObservableGeneric {
    void registerGenericObserver(GenericObserver genericObserver);
    void manageOfflineTextView();
    void showDialog();
    void hideDialog();
}
