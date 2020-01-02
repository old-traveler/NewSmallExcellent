package com.hyc.newsmallexcellent.bean

/**
 * 作者: 贺宇成
 * 时间: 2019/4/13
 * 描述:
 */
class WorkRecordBean {

  /**
   * pageNum : 1
   * pageSize : 5
   * size : 1
   * orderBy : null
   * startRow : 1
   * endRow : 1
   * total : 1
   * pages : 1
   * list : [
   * {"id":6,"jobId":55,"applyUserId":2,"applyInformation":"111","applyData":"2019-04-08 14:45:42"
   * ,"handleData":"2019-04-08 14:48:55","handleStatus":2,"remark":"研发工程师","jobSalary":30
   * ,"jobSalaryUnit":"元/小时"}]
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

  var pageNum: Int = 0
  var pageSize: Int = 0
  var size: Int = 0
  var orderBy: Any? = null
  var startRow: Int = 0
  var endRow: Int = 0
  var total: Int = 0
  var pages: Int = 0
  var firstPage: Int = 0
  var prePage: Int = 0
  var nextPage: Int = 0
  var lastPage: Int = 0
  var isIsFirstPage: Boolean = false
  var isIsLastPage: Boolean = false
  var isHasPreviousPage: Boolean = false
  var isHasNextPage: Boolean = false
  var navigatePages: Int = 0
  var list: List<ListBean>? = null
  var navigatepageNums: List<Int>? = null

  class ListBean {
    /**
     * id : 6
     * jobId : 55
     * applyUserId : 2
     * applyInformation : 111
     * applyData : 2019-04-08 14:45:42
     * handleData : 2019-04-08 14:48:55
     * handleStatus : 2
     * remark : 研发工程师
     * jobSalary : 30.0
     * jobSalaryUnit : 元/小时
     */

    var id: Int = 0
    var jobId: Int = 0
    var applyUserId: Int = 0
    var applyInformation: String? = null
    var applyData: String? = null
    var handleData: String? = null
    var handleStatus: Int = 0
    var remark: String? = null
    var jobSalary: Double = 0.toDouble()
    var jobSalaryUnit: String? = null
  }
}
