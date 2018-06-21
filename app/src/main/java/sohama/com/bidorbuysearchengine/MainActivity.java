package sohama.com.bidorbuysearchengine;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import sohama.com.bidorbuysearchengine.Adapters.SearchAdapter;
import sohama.com.bidorbuysearchengine.Helpers.BaseModel;
import sohama.com.bidorbuysearchengine.Helpers.DataController;
import sohama.com.bidorbuysearchengine.Models.ResultsObject;
import sohama.com.bidorbuysearchengine.Models.TradeObject;
import sohama.com.bidorbuysearchengine.interfaces.DataObserver;
import sohama.com.bidorbuysearchengine.interfaces.GenericObserver;
import sohama.com.bidorbuysearchengine.networkmanager.NetworkUtil;
import sohama.com.bidorbuysearchengine.singleton.BaseGenericModel;
import sohama.com.bidorbuysearchengine.singleton.SharedClass;

public class MainActivity extends AppCompatActivity implements DataObserver, GenericObserver {

    ResultsObject resultsObject = new ResultsObject();
    BaseModel baseModel = BaseModel.getInstance();
    DataController dataController;
    SharedClass sharedClass;
    private BaseGenericModel baseGenericModel = BaseGenericModel.getInstance();

    private SearchAdapter adapter;
    private List<TradeObject> tradeObjectList = new ArrayList<>();
    private List<TradeObject> tradeObjectListTemp = new ArrayList<>();
    private SearchView searchView;
    FloatingActionButton search_fab;

    String resultsPerPage;
    String pageNumber;
    String IncludedKeywords;
    String TradeType;
    String OrderBy;

    String SearchParams = "";
    String SearchValue = "";

    private RelativeLayout progress_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        baseModel.registerDataObserver(this);
        BaseGenericModel baseGenericModel = BaseGenericModel.getInstance();
        baseGenericModel.registerGenericObserver(this);
        sharedClass = SharedClass.getInstance(this);

        progress_layout = findViewById(R.id.progress_layout);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.results_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new SearchAdapter(this, tradeObjectList);
        recyclerView.setAdapter(adapter);

        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (NetworkUtil.getConnectivityStatus(MainActivity.this) == 0) {
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (query.equals("")) {
                    Toast.makeText(MainActivity.this, "Please fill in Search text.", Toast.LENGTH_LONG).show();
                } else {
                    SearchValue = query;
                    String searchStringBuilder = "query=" + query;
                    if (!SearchParams.equals(""))
                        searchStringBuilder += SearchParams;
                    dataController = new DataController();
                    dataController.getResults(searchStringBuilder);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                search_fab.setImageResource(R.drawable.ic_search);
                searchView.setVisibility(View.GONE);
                return false;
            }
        });

        search_fab = (FloatingActionButton) findViewById(R.id.search_fab);
        search_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchView.getVisibility() == View.VISIBLE) {
                    SearchFilters();
                } else {
                    searchView.setVisibility(View.VISIBLE);
                    search_fab.setImageResource(R.drawable.ic_add);
                }
            }
        });
    }

    @Override
    public void updateContent(String operation) {
        Log.e("Results", operation);
        Gson gson = new Gson();

        String responseObject = sharedClass.getResponseObject().toString();

        if (!(responseObject.equals("failed") || responseObject.equals("No Results"))) {
            resultsObject = gson.fromJson(responseObject, ResultsObject.class);
            tradeObjectList = resultsObject.getTrade();
            adapter.tradeObjectList = tradeObjectList;
            adapter.notifyDataSetChanged();
            search_fab.setImageResource(R.drawable.ic_search);
            searchView.setVisibility(View.GONE);
            baseGenericModel.hideDialog();
        } else {
            Toast.makeText(this, "No Results Found!!!", Toast.LENGTH_LONG).show();
            baseGenericModel.hideDialog();
        }
    }

    @Override
    public void manageDialog(boolean show) {
        if (show) {
            progress_layout.setVisibility(View.VISIBLE);
        } else {
            progress_layout.setVisibility(View.INVISIBLE);
        }

    }

    private void SearchFilters() {
        final Dialog dialogAlert = new Dialog(MainActivity.this, R.style.DialogStyle);
        dialogAlert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = getLayoutInflater().inflate(R.layout.search_filter_dialog, null);
        dialogAlert.setContentView(v);

        final EditText resultsPerPageEditText = (EditText) v.findViewById(R.id.results_per_page);

        final EditText pageNumberEditText = (EditText) v.findViewById(R.id.page_number);

        final Spinner keywordList = (Spinner) v.findViewById(R.id.keyword_list);
        keywordList.setPopupBackgroundResource(R.drawable.button_border);

        final Spinner tradeTypeList = (Spinner) v.findViewById(R.id.trade_type_list);
        tradeTypeList.setPopupBackgroundResource(R.drawable.button_border);

        final Spinner orderByList = (Spinner) v.findViewById(R.id.order_by_list);
        orderByList.setPopupBackgroundResource(R.drawable.button_border);

        Button cancel = (Button) v.findViewById(R.id.cancel_search_enquiry_btn);
        Button apply = (Button) v.findViewById(R.id.apply_btn);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAlert.dismiss();
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchParams = "";
                resultsPerPage = resultsPerPageEditText.getText().toString();
                pageNumber = pageNumberEditText.getText().toString();
                IncludedKeywords = keywordList.getSelectedItem().toString();
                TradeType = tradeTypeList.getSelectedItem().toString();
                OrderBy = orderByList.getSelectedItem().toString();

                if (!resultsPerPage.equals("")) {
                    SearchParams = SearchParams + "resultsPerPage=" + resultsPerPage + "&";
                }
                if (!pageNumber.equals("")) {
                    SearchParams = SearchParams + "pageNumber=" + pageNumber + "&";
                }
                if (!IncludedKeywords.equals("Default")) {
                    SearchParams = SearchParams + "IncludedKeywords=" + IncludedKeywords + "&";
                }
                if (!TradeType.equals("Default")) {
                    SearchParams = SearchParams + "TradeType=" + TradeType + "&";
                }
                if (!OrderBy.equals("Default")) {
                    SearchParams = SearchParams + "OrderBy=" + OrderBy;
                }

                Toast.makeText(v.getContext(), "Filters Applied.", Toast.LENGTH_LONG).show();

                if (!SearchValue.equals("")) {
                    String searchStringBuilder = "query=" + SearchValue;
                    if (!SearchParams.equals(""))
                        searchStringBuilder += "&" + SearchParams;
                    dataController = new DataController();
                    dataController.getResults(searchStringBuilder);
                }
                dialogAlert.dismiss();
            }
        });

        dialogAlert.show();
    }

    @Override
    public void updateOfflineStatus() {
        if (sharedClass != null)
            if (sharedClass.isOffline()) {
                Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
    }

    @Override
    public void onBackPressed() {

    }
}
