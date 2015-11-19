package com.caij.codehub.interactor;

import com.caij.codehub.bean.Tree;
import com.caij.codehub.ui.callback.UiCallBack;

/**
 * Created by Caij on 2015/11/2.
 */
public interface FileTreeInteractor extends Interactor {

    public void loadFileTree(String name, String repo, String ref, Object requestTag, UiCallBack<Tree> uiCallBack);

}