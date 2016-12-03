package com.feicui.android.yitaobao.Presentation.main.MySelf;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.feicui.android.yitaobao.Base.MvpPresenter;
import com.feicui.android.yitaobao.Model.CachePrefereces;
import com.feicui.android.yitaobao.Model.GoodsResult;
import com.feicui.android.yitaobao.Model.HXEntry.EventType;
import com.feicui.android.yitaobao.Model.HXEntry.SimpleEvent;
import com.feicui.android.yitaobao.Model.RegisterResult;
import com.feicui.android.yitaobao.Model.UserEntry;
import com.feicui.android.yitaobao.Model.UserLoginResult;
import com.feicui.android.yitaobao.Model.UserUp;
import com.feicui.android.yitaobao.NetWork.ShopClient;
import com.feicui.android.yitaobao.NetWork.UICallback;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/11/24.
 */
public class MyselfPresentator extends MvpPresenter<MyselfView> {
    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    private Call call = null;

    public void getLoginData(String name, String pwd){
        call = ShopClient.getInstance().getLogin(name, pwd);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                Toast.makeText((Context) getView(), "error:" + e.toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponseUI(Call call, String context) {
                UserLoginResult result = new Gson().fromJson(context, UserLoginResult.class);
                if(result.getCode().equals("1")&&result.getMsg().equals("succeed")){
                    UserEntry userEntry = result.getData();
                    CachePrefereces.setUser(userEntry);
//                    HXManager.getInstance().isLogin(userEntry.getName(), userEntry.getPassword());
                }
                else{
                    Toast.makeText((Context) getView(), "登录失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getRegister(String name, String pwd, final String nickname, final File file){
        call = ShopClient.getInstance().getRegister(name, pwd);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureUI(Call call, IOException e) {

            }

            @Override
            public void onResponseUI(Call call, String context) {
                RegisterResult result = new Gson().fromJson(context, RegisterResult.class);
                if(result.getCode()==1&&result.getMsg().equals("succeed")){
                    UserEntry userEntry = result.getUserEntry();
                    userEntry.setNickname(nickname);
                    Toast.makeText((Context) getView(), "注册成功", Toast.LENGTH_SHORT).show();
                    Log.i("userEntry", userEntry.getName()+ "***"
                    +userEntry.getNickname()+"***"+userEntry.getPassword()+"***"+
                    userEntry.getUuid()+"***"+userEntry.getUsernname()+"&&&&&"+file.getAbsolutePath());
                    getUp(userEntry, file);
                }
                else{
                    Toast.makeText((Context) getView(), result.getCode(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getUp(UserEntry userEntry, File file){
        call = ShopClient.getInstance().getUpData(userEntry, file);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureUI(Call call, IOException e) {

            }

            @Override
            public void onResponseUI(Call call, String context) {
                Log.i("UP", context);
                RegisterResult result = new Gson().fromJson(context, RegisterResult.class);
                if(result.getCode()==1){
                    Toast.makeText((Context) getView (), "更新成功！", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText((Context) getView (), result.getCode()+"", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void getImage(File file){
        call = ShopClient.getInstance().getUpImage(file);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureUI(Call call, IOException e) {

            }

            @Override
            public void onResponseUI(Call call, String context) {
                Log.i("image", context);
                RegisterResult result = new Gson().fromJson(context, RegisterResult.class);
                if(result.getCode()==1){
                    Toast.makeText((Context) getView (), "更新成功！", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText((Context) getView (), result.getCode()+"", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getData(UserUp entry){
        call = ShopClient.getInstance().getData(entry);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureUI(Call call, IOException e) {

            }

            @Override
            public void onResponseUI(Call call, String context) {
                RegisterResult result = new Gson().fromJson(context, RegisterResult.class);
                if(result.getCode()==1){
                    Toast.makeText((Context) getView (), "更新成功！", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText((Context) getView (), result.getCode()+"", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private int pageInt = 1;
    public void getMyshopData(int pageNo, String type, String master ){
        call = ShopClient.getInstance().getMyShopData(pageNo, type, master);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureUI(Call call, IOException e) {
            }

            @Override
            public void onResponseUI(Call call, String context) {
                GoodsResult result = new Gson().fromJson(context, GoodsResult.class);
                switch (result.getCode()){
                    case 1:
                        getView().addRefreshData(result.getDatas());
                        pageInt = 2;
                        break;
                    case 2:
                        break;
                }
            }
        });
    }

    public void getMoreData(String type){
        call = ShopClient.getInstance().getData(pageInt, type);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureUI(Call call, IOException e) {
            }

            @Override
            public void onResponseUI(Call call, String context) {
                GoodsResult result = new Gson().fromJson(context, GoodsResult.class);
                switch (result.getCode()){
                    case 1:
                        getView().addMoreData(result.getDatas());
                        pageInt ++;
                        break;
                    case 2:
                        break;
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SimpleEvent event){
        if(event.getType() == EventType.LOGIN ){
            Toast.makeText((Context) getView(), "登录成功", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new UserLoginResult());
        }
        if(event.getType() == EventType.LOGIN_ERROR){
            Toast.makeText((Context) getView(), "登录失败", Toast.LENGTH_SHORT).show();
        }
    }
    @NonNull
    @Override
    protected MyselfView getNullObject() {
        return MyselfView.NULL;
    }

    @Override
    public void onDestry() {
        super.onDestry();
        if(call != null){
            call.cancel();
        }
        EventBus.getDefault().unregister(this);
    }
}
