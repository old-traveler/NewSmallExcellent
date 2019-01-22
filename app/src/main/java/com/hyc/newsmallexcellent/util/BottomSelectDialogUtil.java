package com.hyc.newsmallexcellent.util;

import android.content.Context;
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
import com.hyc.newsmallexcellent.bean.AddressBean;
import com.hyc.newsmallexcellent.widget.RecycleViewDivider;
import java.util.ArrayList;
import java.util.Arrays;
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
        new RecycleViewDivider(dialog.getContext(), LinearLayoutManager.VERTICAL, 1, Color.GRAY));
    BaseRecycleAdapter<String, SimpleTextViewHolder>
        adapter =
        new BaseRecycleAdapter<>(data, R.layout.item_simple_text, SimpleTextViewHolder.class);
    adapter.setOnItemClickListener(onItemClickListener);
    recyclerView.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
    recyclerView.setAdapter(adapter);
    showBottomSheetDialog(dialog, view);
  }

  public static void showCityListDialog(BottomSheetDialog dialog, Context context,
      AddressBean addressBean,
      OnItemClickListener<String> onItemClickListener) {
    View view = LayoutInflater.from(context).inflate(R.layout.dialog_category, null);
    ArrayList<String> provinces = new ArrayList<>();
    for (AddressBean.ProvincesBean province : addressBean.getProvinces()) {
      provinces.add(province.getProvinceName());
    }
    RecyclerView recyclerView = view.findViewById(R.id.rv_bottom);
    recyclerView.addItemDecoration(
        new RecycleViewDivider(dialog.getContext(), LinearLayoutManager.VERTICAL, 1, Color.GRAY));
    BaseRecycleAdapter<String, SimpleTextViewHolder>
        adapter =
        new BaseRecycleAdapter<>(provinces, R.layout.item_simple_text, SimpleTextViewHolder.class);
    adapter.setOnItemClickListener(new OnItemClickListener<String>() {
      boolean isProvinces = true;

      @Override
      public void onItemClick(String itemData, View view, int position) {
        if (isProvinces) {
          isProvinces = false;
          ArrayList<String> arrayList = new ArrayList<>();
          List<AddressBean.ProvincesBean.CitysBean> citys =
              addressBean.getProvinces().get(position).getCitys();
          for (AddressBean.ProvincesBean.CitysBean city : citys) {
            arrayList.add(city.getCitysName());
          }
          adapter.setDataList(arrayList);
        } else {
          onItemClickListener.onItemClick(itemData, view, position);
        }
      }
    });
    recyclerView.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
    recyclerView.setAdapter(adapter);
    showBottomSheetDialog(dialog, view);
  }

  public static void showSalarySelectDialog(BottomSheetDialog dialog,
      OnItemClickListener<String> onItemClickListener) {
    View view = LayoutInflater.from(dialog.getContext()).inflate(R.layout.dialog_category, null);
    RecyclerView recyclerView = view.findViewById(R.id.rv_bottom);
    recyclerView.addItemDecoration(
        new RecycleViewDivider(dialog.getContext(), LinearLayoutManager.VERTICAL, 1, Color.GRAY));
    BaseRecycleAdapter<String, SimpleTextViewHolder>
        adapter =
        new BaseRecycleAdapter<>(Arrays.asList(new String[] { "时结", "日结", "月结" }),
            R.layout.item_simple_text, SimpleTextViewHolder.class);
    adapter.setOnItemClickListener(new OnItemClickListener<String>() {
      String unit;

      @Override
      public void onItemClick(String itemData, View view, int position) {
        if (unit == null){
          List<String> data;
          if (itemData.equals("时结")) {
            data = Arrays.asList(new String[] { "0 - 20元", "20 - 40元", "40元以上" });
          } else if (itemData.equals("日结")) {
            data = Arrays.asList(new String[] { "0 - 60元", "60 - 100元", "100元以上" });
          } else {
            data = Arrays.asList(new String[] { "0 - 1000元", "1000 - 3000元", "3000元以上" });
          }
          unit = itemData;
          adapter.setDataList(data);
        }else {
          onItemClickListener.onItemClick(String.format("%s/%s",itemData,unit),view,position);
        }

      }
    });
    recyclerView.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
    recyclerView.setAdapter(adapter);
    showBottomSheetDialog(dialog, view);
  }
}
