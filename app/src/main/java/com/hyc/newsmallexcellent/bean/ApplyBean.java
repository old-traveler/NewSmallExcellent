package com.hyc.newsmallexcellent.bean;

import java.util.List;

public class ApplyBean {

  /**
   * pageNum : 1
   * pageSize : 10
   * size : 1
   * orderBy : null
   * startRow : 1
   * endRow : 1
   * total : 1
   * pages : 1
   * list : [{"id":1,"jobId":47,"applyUserId":2,"applyInformation":"我想要工作","applyData":1548219549000,"handleData":null,"handleStatus":0,"remark":null}]
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

  public static class ListBean {
    /**
     * id : 1
     * jobId : 47
     * applyUserId : 2
     * applyInformation : 我想要工作
     * applyData : 1548219549000
     * handleData : null
     * handleStatus : 0
     * remark : null
     */

    private int id;
    private int jobId;
    private int applyUserId;
    private String applyInformation;
    private long applyData;
    private long handleData;
    private int handleStatus;
    private String remark;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public int getJobId() {
      return jobId;
    }

    public void setJobId(int jobId) {
      this.jobId = jobId;
    }

    public int getApplyUserId() {
      return applyUserId;
    }

    public void setApplyUserId(int applyUserId) {
      this.applyUserId = applyUserId;
    }

    public String getApplyInformation() {
      return applyInformation;
    }

    public void setApplyInformation(String applyInformation) {
      this.applyInformation = applyInformation;
    }

    public long getApplyData() {
      return applyData;
    }

    public void setApplyData(long applyData) {
      this.applyData = applyData;
    }

    public long getHandleData() {
      return handleData;
    }

    public void setHandleData(long handleData) {
      this.handleData = handleData;
    }

    public int getHandleStatus() {
      return handleStatus;
    }

    public void setHandleStatus(int handleStatus) {
      this.handleStatus = handleStatus;
    }

    public String getRemark() {
      return remark;
    }

    public void setRemark(String remark) {
      this.remark = remark;
    }
  }
}
