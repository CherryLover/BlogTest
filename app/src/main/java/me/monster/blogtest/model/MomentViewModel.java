package me.monster.blogtest.model;

import androidx.annotation.DrawableRes;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import me.monster.blogtest.R;

/**
 * @description
 * @author: Created jiangjiwei in 2019-07-18 22:40
 */
public class MomentViewModel extends ViewModel {

    private MutableLiveData<String> name = new MutableLiveData<>("Abs");
    private MutableLiveData<Integer> good = new MutableLiveData<>();
    private int imageId = R.mipmap.avatar_2;

    private MutableLiveData<Integer> avatar = new MutableLiveData<>(R.mipmap.avatar_1);

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
        setAvatarValue(R.mipmap.avatar_2);
        good.setValue(good.getValue() == null ? 1 : good.getValue() + 1);
    }

    public int getImageId() {
        return imageId;
    }

    public MutableLiveData<Integer> getAvatar() {
        return avatar;
    }

    public void setAvatarValue(@DrawableRes int imageId) {
        avatar.setValue(imageId);
    }
}
