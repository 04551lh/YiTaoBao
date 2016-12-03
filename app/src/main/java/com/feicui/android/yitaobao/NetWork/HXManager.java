package com.feicui.android.yitaobao.NetWork;

/**
 * Created by Administrator on 2016/12/2.
 */
public class HXManager {
    private HXManager(){}

    private static HXManager hxManager;

    public static HXManager getInstance(){
        if(hxManager == null){
            hxManager = new HXManager();
        }
        return hxManager;
    }

    /**
     * @description 环信登录
     */

//    public void isLogin(String hx_id, String password){
//        EMClient.getInstance().login(hx_id, password, new EMCallBack() {
//            @Override
//            public void onSuccess() {
//                Log.i("yzg", "登录成功");
//                EventBus.getDefault().post(new SimpleEvent(EventType.LOGIN));
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                Log.i("yzg", "Error >> ："+s);
//                EventBus.getDefault().post(new SimpleEvent(EventType.LOGIN_ERROR));
//            }
//
//            @Override
//            public void onProgress(int i, String s) {
//
//            }
//        });
//    }
}
