package com.caij.codehub.present;

import com.caij.codehub.bean.User;
import com.caij.codehub.interactor.InteractorFactory;
import com.caij.codehub.interactor.UserInteractor;
import com.caij.codehub.present.ui.MainUi;
import com.caij.codehub.present.ui.UserUi;
import com.caij.lib.utils.VolleyManager;

/**
 * Author Caij
 * Email worldcaij@gmail.com
 * Created by Caij on 2015/11/16.
 */
public class MainPresent extends Present<MainUi>{

    protected final Object requestTag;
    protected final UserInteractor mUserInterctor;

    public MainPresent(MainUi ui) {
        super(ui);
        requestTag = new Object();
        mUserInterctor = InteractorFactory.newPresentInstance(UserInteractor.class);
    }

    public void getUserInfo(String token, String username) {
        mUserInterctor.getUserInfo(token, username, requestTag, new DefaultInteractorCallback<User>(mUi) {
            @Override
            public void onSuccess(User user) {
                mUi.onGetUserInfoSuccess(user);
            }

            @Override
            public void onLoading() {

            }

            @Override
            public void onError(int msgId) {
                mUi.showError(msgId);
            }
        });
    }

    @Override
    public void onDeath() {
        VolleyManager.cancelRequestByTag(requestTag);
    }
}
