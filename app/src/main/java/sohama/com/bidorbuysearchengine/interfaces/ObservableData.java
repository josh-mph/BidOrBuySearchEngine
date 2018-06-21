package sohama.com.bidorbuysearchengine.interfaces;

public interface ObservableData {
    void registerDataObserver(DataObserver dataObserver);
    void triggerDataObserver(String operation);
}
