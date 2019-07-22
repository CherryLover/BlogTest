package me.monster.blogtest.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @description
 * @author: Created jiangjiwei in 2019-07-18 22:40
 */
public class MomentViewModel extends ViewModel {
    //    private MutableLiveData<String> _name = new MutableLiveData<>("Abs");
//    private LiveData<String> name = _name;
//
//    public LiveData<String> getName() {
//        return name;
//    }
//
//    public void onShow() {
//        _name.setValue("Tester in Fragment");
//    }
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
}
