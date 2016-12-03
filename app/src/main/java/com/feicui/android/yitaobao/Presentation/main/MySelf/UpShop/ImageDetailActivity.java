package com.feicui.android.yitaobao.Presentation.main.MySelf.UpShop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui.android.yitaobao.Model.MyCameraentry;
import com.feicui.android.yitaobao.R;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/1.
 *
 */
public class ImageDetailActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    @Bind(R.id.image_toolbar)
    Toolbar toolbar;
    @Bind(R.id.images_finishes)
    TextView succed;
    @Bind(R.id.vp_image)
    ViewPager viewPager;
    @Bind(R.id.image_box)
    CheckBox box;
    @Bind(R.id.image_number)
    TextView number;
    @Bind(R.id.image_set)
    TextView cover;
    @Bind(R.id.rl_bottom)
    RelativeLayout rv_bootom;


    private ArrayList<MyCameraentry> list;
    private ArrayList<MyCameraentry> newShowlist;

    private ImageAdapter adapter;
    private int position;
    private  static setImageData  activity;
    private boolean isCover;
    private MyCameraentry cameraentry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagedetail);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("图片信息");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getBackground().setAlpha(240);
        rv_bootom.getBackground().setAlpha(240);
        initView();
    }

    private void initView(){
        list = new ArrayList<>();
        newShowlist = new ArrayList<>();
        list = (ArrayList<MyCameraentry>) this.getIntent().getSerializableExtra("photo");
        position = Integer.valueOf(this.getIntent().getStringExtra("position"));
        adapter = new ImageAdapter(list, this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position, false);
        box.setChecked(list.get(position).isSelect());
        position = position+1;
        number.setText(position+"/"+list.size());
        succed.setText("完成"+MyCamera.showList.size()+"/8");
        viewPager.addOnPageChangeListener(this);
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(viewPager.getCurrentItem()).setSelect(isChecked);
                MyCamera.list.get(viewPager.getCurrentItem()).setSelect(isChecked);
            }
        });
        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onImageCkeck(list.get(viewPager.getCurrentItem()), box, viewPager.getCurrentItem());
                succed.setText("完成"+MyCamera.showList.size()+"/8");
            }
        });
        succed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("lsit", (Serializable)list);
                setResult(002, intent);
                finish();
            }
        });
//        cover.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(MyCamera.showList.size() > 0){
//                    if(list.get(viewPager.getCurrentItem()).isSelect()){
//                        if(isCover){
//                            cover.setText("取消");
//                            newShowlist.add(0, MyCamera.showList.get(1));
//                            MyCamera.showList.remove(1);
//                            MyCamera.showList.addAll(0, newShowlist);
//                            list.get(viewPager.getCurrentItem()).setSelect(true);
//                            isCover =false;
//                        }
//                        else{
//                            cover.setText("设置封面");
//                            newShowlist.add(0, list.get(viewPager.getCurrentItem()));
//                            MyCamera.showList.remove(0);
//                            MyCamera.showList.addAll(0, newShowlist);
//                            isCover = true;
//                        }
//                    }
//                    else{
//                        list.get(viewPager.getCurrentItem()).setSelect(true);
//                        box.setChecked(list.get(viewPager.getCurrentItem()).isSelect());
//                    }
//                }
//                else{
//                    MyCamera.showList.add(list.get(viewPager.getCurrentItem()));
//                }
//            }
//        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent();
            intent.putExtra("lsit", (Serializable)list);
            setResult(002, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        box.setChecked(list.get(position).isSelect());
        number.setText(position+"/"+list.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static void setActivity(setImageData data){
        activity = data;
    }

    public interface setImageData{
        void onImageCkeck(MyCameraentry myCameraentry, CheckBox checkBox, int position);
    }
}
