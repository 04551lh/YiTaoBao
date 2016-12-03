package com.feicui.android.yitaobao.Presentation.main.MySelf.UpShop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.feicui.android.yitaobao.Model.MyCameraentry;
import com.feicui.android.yitaobao.Model.UpShop;
import com.feicui.android.yitaobao.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/30.
 *
 */
public class UpShopActivity extends AppCompatActivity {
    @Bind(R.id.tb_goods)
    Toolbar toolbar;
    @Bind(R.id.rv_upshop)
    RecyclerView recyclerView;
    @Bind(R.id.et_upshop_type)
    EditText type;
    @Bind(R.id.et_upshop_name)
    EditText name;
    @Bind(R.id.et_upshop_price)
    EditText price;
    @Bind(R.id.et_upshop_description)
    EditText description;

    private UpAdapter adapter;
    private ArrayList<MyCameraentry> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upshop);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        initView();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(UpShop.getList()!=null){
            list = UpShop.getList();
            adapter.notifyDataSetChanged();
        }
    }

    private void initView(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.Upshop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = new ArrayList<>();
        adapter = new UpAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(UpShopActivity.this, LinearLayoutManager.HORIZONTAL, false));
        adapter.setOnItemClickListener(new UpAdapter.setOnListener() {
            @Override
            public void onImageClick() {
                startActivity(new Intent(UpShopActivity.this, MyCamera.class));
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpShop e){
        ArrayList<MyCameraentry> list = e.getList();
        if(list.size() >= 0){
            for (int i = 0; i < list.size(); i++) {
                this.list.set(i, list.get(i));
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void upData(){

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
