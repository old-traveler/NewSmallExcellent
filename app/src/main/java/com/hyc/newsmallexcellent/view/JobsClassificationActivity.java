package com.hyc.newsmallexcellent.view;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.adapter.JobsClassificationAdapter;
import com.hyc.newsmallexcellent.base.BaseMvpActivity;
import com.hyc.newsmallexcellent.base.helper.ToastHelper;
import com.hyc.newsmallexcellent.interfaces.JobsClassificationContract;
import com.hyc.newsmallexcellent.presenter.JobsClassificationPresenter;

import java.util.ArrayList;
import java.util.List;

public class JobsClassificationActivity extends BaseMvpActivity<JobsClassificationPresenter>
        implements JobsClassificationContract.View , View.OnClickListener {

    private RecyclerView classification_rv;
    private ImageView classification__return , classification_add;
    private EditText classification__et;
    private JobsClassificationAdapter jobsClassificationAdapter;
    private List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_jobs_classification);
        super.onCreate(savedInstanceState);
        initView();
        initialization();
    }

    @SuppressLint("WrongViewCast")
    private void initView() {
        classification_rv = findViewById(R.id.classification_rv);
        classification__return = findViewById(R.id.classification__return);
        classification_add = findViewById(R.id.classification_add);
        classification__et = findViewById(R.id.classification__et);

        classification_rv.setLayoutManager(new GridLayoutManager(classification_rv.getContext(),2));
        jobsClassificationAdapter = new JobsClassificationAdapter(this,list);
        classification_rv.setAdapter(jobsClassificationAdapter);
        classification_rv.setItemAnimator(new DefaultItemAnimator());
    }

    private void initialization() {
        classification__return.setOnClickListener(this);
        classification_add.setOnClickListener(this);
    }


    @Override
    protected JobsClassificationPresenter createPresenter() {
        return new JobsClassificationPresenter();
    }

    @Override
    public String getCategory() {
        return classification__et.getText().toString();
    }

    @Override
    public boolean verificationInput() {
        if (TextUtils.isEmpty(getCategory())){
            ToastHelper.toast("请输入所需要的添加的分类");
        }else {
            return true;
        }
        return false;
    }

    @Override
    public void addClassificationSuccess() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.classification__return:
                break;
            case R.id.classification_add:
                jobsClassificationAdapter.appendDataToList(classification__et.getText().toString());
                presenter.jobsClassification();
                break;
        }
    }



}
