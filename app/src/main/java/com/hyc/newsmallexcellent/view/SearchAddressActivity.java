package com.hyc.newsmallexcellent.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.adapter.viewholder.SearchAddressViewHolder;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.adapter.BaseRecycleAdapter;
import com.hyc.newsmallexcellent.base.bean.PoiAddressBean;
import com.hyc.newsmallexcellent.base.interfaces.OnItemClickListener;
import com.hyc.newsmallexcellent.base.utils.KeyBoardUtils;
import com.hyc.newsmallexcellent.interfaces.SearchAddressContract;
import com.hyc.newsmallexcellent.presenter.SearchAddressPresenter;
import java.util.List;

public class SearchAddressActivity extends BaseMvpActivity<SearchAddressPresenter>
    implements SearchAddressContract.IView {

  @BindView(R.id.et_input)
  EditText etInput;
  @BindView(R.id.rv_search_address)
  RecyclerView rvSearchAddress;

  private BaseRecycleAdapter<PoiAddressBean,SearchAddressViewHolder> suggestAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_search_address);
    super.onCreate(savedInstanceState);
    setToolBar();
    initSuggestList();
    initSearchListener();
  }

  private void initSearchListener() {
    etInput.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void afterTextChanged(Editable editable) {
        presenter.fetchAddressSuggest();
      }
    });
  }

  private void initSuggestList() {
    rvSearchAddress.setLayoutManager(new LinearLayoutManager(this));
    suggestAdapter = new BaseRecycleAdapter<>(R.layout.item_showaddress,SearchAddressViewHolder.class);
    rvSearchAddress.setAdapter(suggestAdapter);
    suggestAdapter.setOnItemClickListener((itemData, view, position) -> {
      Intent intent = new Intent(this,MapActivity.class)
          .putExtra("lat",itemData.getLatitude())
          .putExtra("lon",itemData.getLongitude());
      setResult(RESULT_OK,intent);
      finish();
    });
    rvSearchAddress.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        KeyBoardUtils.closeKeyBoard(etInput, SearchAddressActivity.this);
      }
    });
  }

  @Override
  protected SearchAddressPresenter createPresenter() {
    return new SearchAddressPresenter();
  }

  @Override
  public String getSearchKey() {
    return etInput.getText().toString();
  }

  @Override
  public void loadSuggest(List<PoiAddressBean> poiAddressBeans) {
    suggestAdapter.setDataList(poiAddressBeans);
  }
}
