package com.feicui.android.yitaobao.Model.HXEntry;

/**
 * Created by Administrator on 2016/12/2.
 */
public final class SimpleEvent {
    public final EventType type;

    public EventType getType() {
        return type;
    }

    public SimpleEvent(EventType type){
        this.type = type;
    }
}
