package com.hyc.newsmallexcellent.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * 作者: 贺宇成
 * 时间: 2019/4/8
 * 描述:
 */
public class ReportBean  {

  /**
   * pageNum : 1
   * pageSize : 10
   * size : 3
   * orderBy : null
   * startRow : 1
   * endRow : 3
   * total : 3
   * pages : 1
   * list : [{"id":1,"reportUserId":2,"bereportUserId":2,"reportContent":"","reportData":1548222844000,"status":0,"remark":null,"result":null},{"id":2,"reportUserId":2,"bereportUserId":2,"reportContent":"","reportData":1548222846000,"status":0,"remark":null,"result":null},{"id":3,"reportUserId":2,"bereportUserId":2,"reportContent":"举报工作内容虚假","reportData":1554710945000,"status":0,"remark":null,"result":null}]
   * firstPage : 1
   * prePage : 0
   * nextPage : 0
   * lastPage : 1
   * isFirstPage : true
   * isLastPage : true
   * hasPreviousPage : false
   * hasNextPage : false
   * navigatePages : 8
   * navigatepageNums : [1]
   */

  private int pageNum;
  private int pageSize;
  private int size;
  private Object orderBy;
  private int startRow;
  private int endRow;
  private int total;
  private int pages;
  private int firstPage;
  private int prePage;
  private int nextPage;
  private int lastPage;
  private boolean isFirstPage;
  private boolean isLastPage;
  private boolean hasPreviousPage;
  private boolean hasNextPage;
  private int navigatePages;
  private List<ListBean> list;
  private List<Integer> navigatepageNums;

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

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public Object getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(Object orderBy) {
    this.orderBy = orderBy;
  }

  public int getStartRow() {
    return startRow;
  }

  public void setStartRow(int startRow) {
    this.startRow = startRow;
  }

  public int getEndRow() {
    return endRow;
  }

  public void setEndRow(int endRow) {
    this.endRow = endRow;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

  public int getFirstPage() {
    return firstPage;
  }

  public void setFirstPage(int firstPage) {
    this.firstPage = firstPage;
  }

  public int getPrePage() {
    return prePage;
  }

  public void setPrePage(int prePage) {
    this.prePage = prePage;
  }

  public int getNextPage() {
    return nextPage;
  }

  public void setNextPage(int nextPage) {
    this.nextPage = nextPage;
  }

  public int getLastPage() {
    return lastPage;
  }

  public void setLastPage(int lastPage) {
    this.lastPage = lastPage;
  }

  public boolean isIsFirstPage() {
    return isFirstPage;
  }

  public void setIsFirstPage(boolean isFirstPage) {
    this.isFirstPage = isFirstPage;
  }

  public boolean isIsLastPage() {
    return isLastPage;
  }

  public void setIsLastPage(boolean isLastPage) {
    this.isLastPage = isLastPage;
  }

  public boolean isHasPreviousPage() {
    return hasPreviousPage;
  }

  public void setHasPreviousPage(boolean hasPreviousPage) {
    this.hasPreviousPage = hasPreviousPage;
  }

  public boolean isHasNextPage() {
    return hasNextPage;
  }

  public void setHasNextPage(boolean hasNextPage) {
    this.hasNextPage = hasNextPage;
  }

  public int getNavigatePages() {
    return navigatePages;
  }

  public void setNavigatePages(int navigatePages) {
    this.navigatePages = navigatePages;
  }

  public List<ListBean> getList() {
    return list;
  }

  public void setList(List<ListBean> list) {
    this.list = list;
  }

  public List<Integer> getNavigatepageNums() {
    return navigatepageNums;
  }

  public void setNavigatepageNums(List<Integer> navigatepageNums) {
    this.navigatepageNums = navigatepageNums;
  }

  public static class ListBean implements Parcelable {
    /**
     * id : 1
     * reportUserId : 2
     * bereportUserId : 2
     * reportContent :
     * reportData : 1548222844000
     * status : 0
     * remark : null
     * result : null
     */

    private int id;
    private int reportUserId;
    private int bereportUserId;
    private String reportContent;
    private String reportData;
    private int status;
    private Object remark;
    private String result;

    protected ListBean(Parcel in) {
      id = in.readInt();
      reportUserId = in.readInt();
      bereportUserId = in.readInt();
      reportContent = in.readString();
      reportData = in.readString();
      status = in.readInt();
      result = in.readString();
    }

    public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
      @Override
      public ListBean createFromParcel(Parcel in) {
        return new ListBean(in);
      }

      @Override
      public ListBean[] newArray(int size) {
        return new ListBean[size];
      }
    };

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public int getReportUserId() {
      return reportUserId;
    }

    public void setReportUserId(int reportUserId) {
      this.reportUserId = reportUserId;
    }

    public int getBereportUserId() {
      return bereportUserId;
    }

    public void setBereportUserId(int bereportUserId) {
      this.bereportUserId = bereportUserId;
    }

    public String getReportContent() {
      return reportContent;
    }

    public void setReportContent(String reportContent) {
      this.reportContent = reportContent;
    }

    public String getReportData() {
      return reportData;
    }

    public void setReportData(String reportData) {
      this.reportData = reportData;
    }

    public int getStatus() {
      return status;
    }

    public void setStatus(int status) {
      this.status = status;
    }

    public Object getRemark() {
      return remark;
    }

    public void setRemark(Object remark) {
      this.remark = remark;
    }

    public String getResult() {
      return result;
    }

    public void setResult(String result) {
      this.result = result;
    }

    @Override
    public int describeContents() {
      return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(id);
      dest.writeInt(reportUserId);
      dest.writeInt(bereportUserId);
      dest.writeString(reportContent);
      dest.writeString(reportData);
      dest.writeInt(status);
      dest.writeString(result);
    }
  }
}
