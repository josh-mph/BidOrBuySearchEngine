package sohama.com.bidorbuysearchengine.Helpers;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

import sohama.com.bidorbuysearchengine.singleton.BaseGenericModel;
import sohama.com.bidorbuysearchengine.singleton.SharedClass;

public class DataController {

    private BaseModel baseModel;
    private SharedClass sharedClass;
    private String EndPoint;
    private BaseGenericModel baseGenericModel;

    public DataController() {
        baseModel = BaseModel.getInstance();
        sharedClass = SharedClass.getInstance(null);
        baseGenericModel = BaseGenericModel.getInstance();
    }

    public void getResults(String params) {
        EndPoint = sharedClass.getEndPoint();//+"?"+params;
        new DataFetcher().execute();
    }

    private class DataFetcher extends AsyncTask<String, String, String> {

        int status = 0;

        @Override
        protected String doInBackground(String... strings) {
            try {

                URL url = new URL(EndPoint);

                HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(10 * 1000);
                //add headers
                httpURLConnection.setRequestProperty("Accept", "application/json; charset=UTF-8");
                httpURLConnection.setRequestProperty("X-BOB-AUTHID", "frRurWKTnhEAUxZXQsAtHjZe8ubALbXdsx9YtrDj");
                httpURLConnection.setRequestProperty("X-BOB-PLATFORM", "4");
                httpURLConnection.setRequestProperty("X-BOB-CID", "987654321");
                httpURLConnection.setConnectTimeout(600000);
                status = httpURLConnection.getResponseCode();

                if (status == 200) {
                    String responseString = "";
                    InputStream urlInputStream = null;
                    try {
                        urlInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                        responseString = IOUtils.toString(urlInputStream, Charset.forName("UTF-8"));
                    } catch (Exception ex) {
                        Log.e("NetworkConnector", String.format("doInBackground no input stream status: %d\n%s", status, ex.getStackTrace()));
                    } finally {
                        try {
                            if (urlInputStream != null) {
                                urlInputStream.close();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    return responseString;
                } else {
                    return "No Results";
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPreExecute() {
            baseGenericModel.showDialog();
        }

        @Override
        protected void onPostExecute(String s) {
            sharedClass.setResponseCode(status);
            sharedClass.setResponseObject(s);
            baseModel.triggerDataObserver("Search");
        }
    }

}
