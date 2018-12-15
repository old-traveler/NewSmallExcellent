package com.hyc.newsmallexcellent.base.interfaces;

import android.view.View;

public interface OnItemClickListener<D> {

  void onItemClick(D itemData, View view, int position);

}
