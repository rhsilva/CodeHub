package com.rafael.githubmngr.interactor.imp;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.rafael.githubmngr.API;
import com.rafael.githubmngr.bean.Repository;
import com.rafael.githubmngr.interactor.InteractorCallBack;
import com.rafael.githubmngr.interactor.RepositoryInfoInteractor;
import com.rafael.lib.utils.VolleyManager;
import com.rafael.lib.volley.request.GsonRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * /9/19.
 */
public class RepositoryInfoInteractorImp implements RepositoryInfoInteractor {

    @Override
    public void getRepositoryInfo(String repositoryName, String owner, String token, Object requestTag, final InteractorCallBack<Repository> interactorCallBack) {
        interactorCallBack.onLoading();
        String url = API.API_HOST + API.REPOSITORY_REPOS_URI + "/" + owner + "/" + repositoryName;
        Map<String, String> head = new HashMap<>();
        API.configAuthorizationHead(head, token);
        GsonRequest<Repository> request = new GsonRequest<Repository>(Request.Method.GET, url, "", head, new TypeToken<Repository>() {}.getType(),
                new Response.Listener<Repository>() {
                    @Override
                    public void onResponse(Repository response) {
                        interactorCallBack.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                interactorCallBack.onError(error);
            }
        });
        VolleyManager.addRequest(request, requestTag);
    }
}
