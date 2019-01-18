package com.hyc.newsmallexcellent.bean;

import java.util.List;

public class JobBean {

  /**
   * pageNum : 1
   * pageSize : 1
   * size : 1
   * orderBy : null
   * startRow : 0
   * endRow : 0
   * total : 1
   * pages : 1
   * list : [{"id":10,"userId":2,"jobTitle":"服务员","jobDescribe":"餐厅服务员","jobCategory":"服务员","jobSalary":3000,"jobSalaryUnit":"元/月","jobCount":5,"workingHours":"周末","workingDays":"60","contact":"贺宇成","telephone":"15273311295","isAuthentication":0,"releaseDate":"2019-01-18 14:51:40","closingDate":"2019-02-18 14:41:32","issuePlace":"景山公园-绮望楼","status":0,"remark":null,"longitude":"116.396807","latitude":"39.923843","city":"衡阳"}]
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
     * id : 10
     * userId : 2
     * jobTitle : 服务员
     * jobDescribe : 餐厅服务员
     * jobCategory : 服务员
     * jobSalary : 3000.0
     * jobSalaryUnit : 元/月
     * jobCount : 5
     * workingHours : 周末
     * workingDays : 60
     * contact : 贺宇成
     * telephone : 15273311295
     * isAuthentication : 0
     * releaseDate : 2019-01-18 14:51:40
     * closingDate : 2019-02-18 14:41:32
     * issuePlace : 景山公园-绮望楼
     * status : 0
     * remark : null
     * longitude : 116.396807
     * latitude : 39.923843
     * city : 衡阳
     */

    private int id;
    private int userId;
    private String jobTitle;
    private String jobDescribe;
    private String jobCategory;
    private double jobSalary;
    private String jobSalaryUnit;
    private int jobCount;
    private String workingHours;
    private String workingDays;
    private String contact;
    private String telephone;
    private int isAuthentication;
    private String releaseDate;
    private String closingDate;
    private String issuePlace;
    private int status;
    private Object remark;
    private String longitude;
    private String latitude;
    private String city;

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

    public String getJobTitle() {
      return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
      this.jobTitle = jobTitle;
    }

    public String getJobDescribe() {
      return jobDescribe;
    }

    public void setJobDescribe(String jobDescribe) {
      this.jobDescribe = jobDescribe;
    }

    public String getJobCategory() {
      return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
      this.jobCategory = jobCategory;
    }

    public double getJobSalary() {
      return jobSalary;
    }

    public void setJobSalary(double jobSalary) {
      this.jobSalary = jobSalary;
    }

    public String getJobSalaryUnit() {
      return jobSalaryUnit;
    }

    public void setJobSalaryUnit(String jobSalaryUnit) {
      this.jobSalaryUnit = jobSalaryUnit;
    }

    public int getJobCount() {
      return jobCount;
    }

    public void setJobCount(int jobCount) {
      this.jobCount = jobCount;
    }

    public String getWorkingHours() {
      return workingHours;
    }

    public void setWorkingHours(String workingHours) {
      this.workingHours = workingHours;
    }

    public String getWorkingDays() {
      return workingDays;
    }

    public void setWorkingDays(String workingDays) {
      this.workingDays = workingDays;
    }

    public String getContact() {
      return contact;
    }

    public void setContact(String contact) {
      this.contact = contact;
    }

    public String getTelephone() {
      return telephone;
    }

    public void setTelephone(String telephone) {
      this.telephone = telephone;
    }

    public int getIsAuthentication() {
      return isAuthentication;
    }

    public void setIsAuthentication(int isAuthentication) {
      this.isAuthentication = isAuthentication;
    }

    public String getReleaseDate() {
      return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
      this.releaseDate = releaseDate;
    }

    public String getClosingDate() {
      return closingDate;
    }

    public void setClosingDate(String closingDate) {
      this.closingDate = closingDate;
    }

    public String getIssuePlace() {
      return issuePlace;
    }

    public void setIssuePlace(String issuePlace) {
      this.issuePlace = issuePlace;
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

    public String getLongitude() {
      return longitude;
    }

    public void setLongitude(String longitude) {
      this.longitude = longitude;
    }

    public String getLatitude() {
      return latitude;
    }

    public void setLatitude(String latitude) {
      this.latitude = latitude;
    }

    public String getCity() {
      return city;
    }

    public void setCity(String city) {
      this.city = city;
    }
  }
}
