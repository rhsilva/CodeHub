package com.caij.codehub.interactor;

import com.caij.codehub.bean.Page;
import com.caij.codehub.bean.User;

import java.util.List;

public interface UserListInteractor extends Interactor {

    public void getFollowers(String token, String username, Page page, Object requestTag, InteractorCallBack<List<User>> interactorCallBack);

    public void getFollowing(String token, String username, Page page, Object requestTag, InteractorCallBack<List<User>> interactorCallBack);
}
