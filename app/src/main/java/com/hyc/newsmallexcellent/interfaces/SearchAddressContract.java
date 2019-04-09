package com.hyc.newsmallexcellent.interfaces;

import com.hyc.newsmallexcellent.base.bean.PoiAddressBean;
import com.hyc.newsmallexcellent.base.interfaces.ILoading;
import java.util.List;

public interface SearchAddressContract {

  public interface IPresenter{
    void fetchAddressSuggest();
  }

  public interface IView extends ILoading {
    String getSearchKey();

    void loadSuggest(List<PoiAddressBean> poiAddressBeans);

  }

}
