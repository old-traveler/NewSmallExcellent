package com.hyc.newsmallexcellent.util;

import android.graphics.Color;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.adapter.viewholder.SimpleTextViewHolder;
import com.hyc.newsmallexcellent.base.adapter.BaseRecycleAdapter;
import com.hyc.newsmallexcellent.base.interfaces.OnItemClickListener;
import com.hyc.newsmallexcellent.widget.RecycleViewDivider;
import java.util.List;

public class BottomSelectDialogUtil {

  public static void showBottomSheetDialog(BottomSheetDialog dialog, View view) {
    dialog.setContentView(view);
    dialog.show();
  }

  public static void showSimpleListTextDialog(BottomSheetDialog dialog, List<String> data,
      OnItemClickListener<String> onItemClickListener) {
    View view = LayoutInflater.from(dialog.getContext()).inflate(R.layout.dialog_category, null);
    RecyclerView recyclerView = view.findViewById(R.id.rv_bottom);
    recyclerView.addItemDecoration(
        new RecycleViewDivider(dialog.getContext(), LinearLayoutManager.VERTICAL,1,Color.GRAY));
    BaseRecycleAdapter<String, SimpleTextViewHolder>
        adapter =
        new BaseRecycleAdapter<>(data, R.layout.item_simple_text, SimpleTextViewHolder.class);
    adapter.setOnItemClickListener(onItemClickListener);
    recyclerView.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
    recyclerView.setAdapter(adapter);
    showBottomSheetDialog(dialog, view);
  }
}
