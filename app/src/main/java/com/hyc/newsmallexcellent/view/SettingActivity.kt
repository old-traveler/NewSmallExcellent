package com.hyc.newsmallexcellent.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import com.hyc.newsmallexcellent.R
import com.hyc.newsmallexcellent.base.helper.ToastHelper
import com.hyc.newsmallexcellent.helper.SpCacheHelper
import com.hyc.newsmallexcellent.model.UserModel
import kotlinx.android.synthetic.main.activity_setting.fl_change_pwd
import kotlinx.android.synthetic.main.activity_setting.fl_update
import kotlinx.android.synthetic.main.activity_setting.tv_out
import kotlinx.android.synthetic.main.activity_setting.tv_phone

/**
 * 作者: 贺宇成
 * 时间: 2019/4/17
 * 描述:
 */
class SettingActivity : AppCompatActivity(), OnClickListener {
  override fun onClick(v: View?) {
    when (v?.id) {
      R.id.fl_change_pwd -> {
        startActivity(Intent(this, ChangePasswordActivity::class.java))
      }
      R.id.fl_update -> {
        ToastHelper.toast("已是最新版本")
      }
      R.id.tv_out -> {
        UserModel().logout()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_setting)
    setToolBar(R.id.toolbar, "设置")
    fl_change_pwd.setOnClickListener(this)
    tv_out.setOnClickListener(this)
    fl_update.setOnClickListener(this)
    tv_phone.text = SpCacheHelper.getString("accountName".toLowerCase())
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == android.R.id.home) {
      super.onBackPressed()
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  fun setToolBar(toolBarId: Int, title: String) {
    val toolbar = findViewById<Toolbar>(toolBarId)
    if (toolbar != null) {
      setSupportActionBar(toolbar)
    }
    val actionBar = supportActionBar
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true)
      actionBar.setDisplayShowTitleEnabled(false)
    }
    toolbar.title = title
  }

}