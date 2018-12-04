package com.hyc.newsmallexcellent.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.base.interfaces.ILoading;
import com.hyc.newsmallexcellent.base.widger.LoadingDialog;

public abstract class BaseMvpActivity<P extends BasePresenter> extends AppCompatActivity implements
    ILoading {

  public Toolbar mToolbar;
  private LoadingDialog loadingDialog;
  private Unbinder unbinder;

  public P presenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter = createPresenter();
    presenter.attachView(this);
    unbinder = ButterKnife.bind(this);
  }

  protected abstract P createPresenter();

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenter.detachView();
    if (unbinder != null){
      unbinder.unbind();
    }
  }
  public void setToolBar() {
    this.setToolBar(R.id.toolbar);
  }

  public void setToolBar(int toolBarId) {
    Toolbar toolbar = findViewById(toolBarId);
    if (toolbar != null) {
      setSupportActionBar(toolbar);
      this.mToolbar = toolbar;
    }
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setDisplayShowTitleEnabled(false);
    }
  }

  public void setNoBackToolBar() {
    this.setNoBackToolBar(R.id.toolbar);
  }

  public void setNoBackToolBar(int toolBarId) {
    Toolbar toolbar = findViewById(toolBarId);
    if (toolbar != null) {
      setSupportActionBar(toolbar);
      this.mToolbar = toolbar;
    }
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(false);
      actionBar.setDisplayShowTitleEnabled(false);
    }
  }

  public void setHomeResId(int resId) {
    ActionBar actionBar = getSupportActionBar();
    if (mToolbar != null && actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setHomeAsUpIndicator(resId);
    }
  }

  public void setToolBarTitle(String title) {
    if (mToolbar == null) {
      setToolBar();
    }
    mToolbar.setTitle(title);
  }

  public void setToolBar(int toolBarId, String title, int homeResId) {
    Toolbar toolbar = findViewById(toolBarId);
    if (toolbar != null) {
      setSupportActionBar(toolbar);
      toolbar.setTitle(title);
      this.mToolbar = toolbar;
    }
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setDisplayShowTitleEnabled(false);
      actionBar.setHomeAsUpIndicator(homeResId);
    }
    mToolbar.setTitle(title);
  }

  public void setToolBarTitle(int titleId) {
    if (mToolbar == null) {
      setToolBar();
    }
    mToolbar.setTitle(titleId);
  }

  public void showLoadingView() {
    if (null != loadingDialog
        && loadingDialog.isShowing()) {
      return;
    } else if (null == loadingDialog) {
      LoadingDialog.Builder loadBuilder
          = new LoadingDialog.Builder(this)
          .setCancelable(true)
          .setCancelOutside(true);
      loadingDialog = loadBuilder.create();
    }
    loadingDialog.show();
  }

  public void closeLoadingView() {
    if (loadingDialog != null && loadingDialog.isShowing()) {
      loadingDialog.dismiss();
    }
  }

  public void setStatusBarColor(int statusColor) {
    Window window = getWindow();
    window.setStatusBarColor(getResources().getColor(statusColor));
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        super.onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public Bundle getDataBundle(){
    Bundle bundle = getIntent().getExtras();
    return bundle == null ? new Bundle() : bundle;
  }

}
