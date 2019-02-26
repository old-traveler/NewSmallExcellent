package com.hyc.newsmallexcellent.bean;

import java.io.Serializable;
import java.util.List;

public class AuthenticationBean {

  /**
   * pageNum : 1
   * pageSize : 20
   * size : 1
   * orderBy : null
   * startRow : 1
   * endRow : 1
   * total : 1
   * pages : 1
   * list : [{"id":1,"userId":2,"userName":"考虑太累了","authenticationType":0,"photoOne":"http://47.107.251.143/1548321894622754.jpg","photoTwo":"http://47.107.251.143/1548321921681526.jpg","authenticationData":"2019-01-24 17:25:30","result":null,"status":0,"remark":null}]
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

  public static class ListBean implements Serializable {
    /**
     * id : 1
     * userId : 2
     * userName : 考虑太累了
     * authenticationType : 0
     * photoOne : http://47.107.251.143/1548321894622754.jpg
     * photoTwo : http://47.107.251.143/1548321921681526.jpg
     * authenticationData : 2019-01-24 17:25:30
     * result : null
     * status : 0
     * remark : null
     */

    private int id;
    private int userId;
    private String userName;
    private int authenticationType;
    private String photoOne;
    private String photoTwo;
    private long authenticationData;
    private Object result;
    private int status;
    private Object remark;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public int getUserId() {
      return userId;
    }

    public void setUserId(int userId) {
      this.userId = userId;
    }

    public String getUserName() {
      return userName;
    }

    public void setUserName(String userName) {
      this.userName = userName;
    }

    public int getAuthenticationType() {
      return authenticationType;
    }

    public void setAuthenticationType(int authenticationType) {
      this.authenticationType = authenticationType;
    }

    public String getPhotoOne() {
      return photoOne;
    }

    public void setPhotoOne(String photoOne) {
      this.photoOne = photoOne;
    }

    public String getPhotoTwo() {
      return photoTwo;
    }

    public void setPhotoTwo(String photoTwo) {
      this.photoTwo = photoTwo;
    }

    public long getAuthenticationData() {
      return authenticationData;
    }

    public void setAuthenticationData(long authenticationData) {
      this.authenticationData = authenticationData;
    }

    public Object getResult() {
      return result;
    }

    public void setResult(Object result) {
      this.result = result;
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
  }
}
