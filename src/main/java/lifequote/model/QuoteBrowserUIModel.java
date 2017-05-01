package lifequote.model;

import java.util.List;

/**
 * Created by milanashara on 4/30/17.
 */
public class QuoteBrowserUIModel {

    private int pageNum = 1;
    private int pageSize = 2;
    private long totalElement;
    private String searchStr = "";
    private List<QuoteUIModel> quoteUIModelList;

    public List<QuoteUIModel> getQuoteUIModelList() {
        return quoteUIModelList;
    }

    public void setQuoteUIModelList(List<QuoteUIModel> quoteUIModelList) {
        this.quoteUIModelList = quoteUIModelList;
    }

    public long getTotalElement() {
        return totalElement;
    }

    public void setTotalElement(long totalElement) {
        this.totalElement = totalElement;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    @Override
    public String toString() {
        return "QuoteBrowserUIModel{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", totalElement=" + totalElement +
                ", searchStr='" + searchStr + '\'' +
                '}';
    }
}
