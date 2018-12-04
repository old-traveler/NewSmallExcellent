package com.hyc.newsmallexcellent.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hyc.newsmallexcellent.R;
import com.hyc.newsmallexcellent.helper.ImageRequestHelper;
import com.hyc.newsmallexcellent.helper.SpCacheHelper;
import com.hyc.newsmallexcellent.model.UserModel;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.nav_view)
  NavigationView navView;

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.item_resume){
      Bundle bundle = new Bundle();
      bundle.putInt("user_id",new UserModel().getCurUserId());
      Intent intent = new Intent(this,ResumeActivity.class);
      startActivity(intent.putExtras(bundle));
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    ImageView headImageView = navView.getHeaderView(0).findViewById(R.id.iv_main_head);
    ImageRequestHelper.loadHeadImage(this,new UserModel().getCurHeadUrl()
        ,headImageView);
    navView.setNavigationItemSelectedListener(this::onOptionsItemSelected);

  }
}
