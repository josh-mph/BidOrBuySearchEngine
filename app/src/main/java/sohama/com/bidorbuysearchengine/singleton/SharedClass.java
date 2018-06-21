package sohama.com.bidorbuysearchengine.singleton;

import android.content.Context;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.File;

import sohama.com.bidorbuysearchengine.Helpers.DataController;

public class SharedClass {

    private static SharedClass instance = null;

    private transient Object responseObject;
    private transient int responseCode;
    private transient boolean isOffline;
    private transient DataController dataController;
    private transient String onlineTitle;
    private transient String EndPoint = "https://demo.bidorbuy.co.za/services/v3/tradesearch";
    private static transient Context appContext;

    protected SharedClass() {
        // Exists only to defeat instantiation.
    }

    public static SharedClass getInstance(Context appContext) {
        if (SharedClass.appContext == null)
            SharedClass.appContext = appContext;
        try {
            if (instance == null) {

                String sharedClassSerializedFileName = SharedClass.appContext.getFilesDir().getAbsolutePath() + "/shared.txt";
                File sharedClassSerializedFile = new File(sharedClassSerializedFileName);
                if (sharedClassSerializedFile.exists()) {
                    Gson gsonParser = new Gson();
                    String sharedClassSerializedContents = FileUtils.readFileToString(sharedClassSerializedFile, "UTF-8");
                    instance = gsonParser.fromJson(sharedClassSerializedContents, SharedClass.class);
                } else
                    instance = new SharedClass();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return instance;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static void setAppContext(Context appContext) {
        SharedClass.appContext = appContext;
    }

    public boolean isOffline() {
        return isOffline;
    }

    public void setOffline(boolean offline) {
        isOffline = offline;
    }

    public DataController getDataController() {
        return dataController;
    }

    public void setDataController(DataController dataController) {
        this.dataController = dataController;
    }

    public String getOnlineTitle() {
        return onlineTitle;
    }

    public void setOnlineTitle(String onlineTitle) {
        this.onlineTitle = onlineTitle;
    }

    public String getEndPoint() {
        return EndPoint;
    }
}
