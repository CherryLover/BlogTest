package me.monster.blogtest.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * @description
 * @author: Created jiangjiwei in 2019-07-17 22:29
 */
public class MomentDetail extends BaseObservable {
    private String moment;
    private int goodCount;

    @Bindable
    public String getMoment() {
        return moment == null ? "" : moment;
    }

    @Bindable
    public int getGoodCount() {
        return goodCount;
    }

    public void setContent(String msg) {
        this.moment = msg;
        notifyChange();
    }

    public void updateGood(int count) {
        goodCount = count;
        notifyChange();
    }
}
