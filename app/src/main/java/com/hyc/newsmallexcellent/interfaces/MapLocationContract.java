package com.hyc.newsmallexcellent.interfaces;

import com.amap.api.services.core.PoiItem;
import java.util.List;

public interface MapLocationContract {

  public interface IPresenter  {

    void fetchNearPosition(Double latitude, Double longitude);

  }

  public interface IView{

    void loadNearPosition(List<PoiItem> poiItems);
  }


}
