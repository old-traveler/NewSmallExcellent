package com.hyc.newsmallexcellent.interfaces;

import com.hyc.newsmallexcellent.base.bean.PoiAddressBean;
import java.util.List;

public interface SearchAddressContract {

  public interface IPresenter{
    void fetchAddressSuggest();
  }

  public interface IView{
    String getSearchKey();

    void loadSuggest(List<PoiAddressBean> poiAddressBeans);

  }

}
