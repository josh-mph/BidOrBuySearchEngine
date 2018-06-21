package sohama.com.bidorbuysearchengine.Models;

import java.util.List;

public class ResultsObject {
    private long totalResults;
    private int pageNumber;
    private int resultsPerPage;
    private List<TradeObject> trade;

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public List<TradeObject> getTrade() {
        return trade;
    }

    public void setTrade(List<TradeObject> trade) {
        this.trade = trade;
    }
}
