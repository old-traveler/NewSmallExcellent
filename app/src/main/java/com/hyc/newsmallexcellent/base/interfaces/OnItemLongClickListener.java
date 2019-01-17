package com.hyc.newsmallexcellent.base.interfaces;

import android.view.View;

public interface OnItemLongClickListener<D> {

  boolean onItemLongClick(D itemData, View view, int position);

}
