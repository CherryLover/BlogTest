package me.monster.blogtest.model;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

/**
 * @description
 * @author: Created jiangjiwei in 2019-07-17 22:29
 */
public class MomentDetail {
    //    ObservableField<String> content = new ObservableField<>();
//
//    public ObservableField<String> getContent() {
//        return content;
//    }
//
//    public void setContent(String str) {
//        content.set(str);
//    }
    private ObservableField<String> content = new ObservableField<>();
    private ObservableInt good = new ObservableInt();



    public ObservableInt getGood() {
        return good;
    }

    public void updateGood() {
        good.set(good.get() + 1);
    }

    public ObservableField<String> getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public String getContentValue() {
        return content.get();
    }
}
