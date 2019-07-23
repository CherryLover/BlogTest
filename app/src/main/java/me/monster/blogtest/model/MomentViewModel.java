package me.monster.blogtest.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @description
 * @author: Created jiangjiwei in 2019-07-18 22:40
 */
public class MomentViewModel extends ViewModel {

    private MutableLiveData<String> name = new MutableLiveData<>("Abs");
    private MutableLiveData<Integer> good = new MutableLiveData<>();

    public void setNameValue(String value) {
        name.setValue(value);
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<Integer> getGood() {
        return good;
    }

    public void setGoodValue(int goodValue) {
        good.setValue(goodValue);
    }

    public void upGood() {
        good.setValue(good.getValue() == null ? 1 : good.getValue() + 1);
    }
}
