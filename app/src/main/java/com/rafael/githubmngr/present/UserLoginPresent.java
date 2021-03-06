package com.rafael.githubmngr.present;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.rafael.githubmngr.API;
import com.rafael.githubmngr.R;
import com.rafael.githubmngr.bean.Token;
import com.rafael.githubmngr.interactor.AuthenticationInteractor;
import com.rafael.githubmngr.interactor.InteractorCallBack;
import com.rafael.githubmngr.interactor.InteractorFactory;
import com.rafael.githubmngr.present.ui.UserLoginUi;
import com.rafael.githubmngr.utils.Base64;

import java.util.List;

public class UserLoginPresent extends Present<UserLoginUi>{

    private final AuthenticationInteractor authenticationInteractor;

    public UserLoginPresent(UserLoginUi ui) {
        super(ui);
        authenticationInteractor = InteractorFactory.newInteractorInstance(AuthenticationInteractor.class);
    }

    public void login(final String username, final String pwd) {
        authenticationInteractor.login(username, pwd, this, new InteractorCallBack<Token>() {
            @Override
            public void onSuccess(Token token) {
                mUi.showProgressBarLoading(false);
                mUi.onLoginSuccess(token);
            }

            @Override
            public void onLoading() {
                mUi.showProgressBarLoading(true);
            }

            @Override
            public void onError(VolleyError error) {
                handlerLoginError(error, username, pwd);
            }
        });
    }

    private void handlerLoginError(VolleyError error, String username, String pwd) {
        if (error instanceof ServerError) {
            NetworkResponse response = ((ServerError) error).networkResponse;
            if (response != null) {
                int statusCode = response.statusCode;
                if (statusCode == 422) {
                    removeTokenByLogin(username, pwd);
                }else {
                    mUi.showError(R.string.login_error);
                    mUi.showProgressBarLoading(false);
                }
            }
        }else if (error instanceof AuthFailureError) {
            mUi.showError(R.string.password_error);
            mUi.showProgressBarLoading(false);
        } else {
            mUi.showError(R.string.login_error);
            mUi.showProgressBarLoading(false);
        }
    }

    private void removeTokenByLogin(final String username, final String pwd) {
        authenticationInteractor.getHaveTokens(username, pwd, this, new InteractorCallBack<List<Token>>() {
            @Override
            public void onSuccess(List<Token> tokens) {
                for (Token token : tokens) {
                    if (token != null && API.TOKEN_NOTE.equals(token.getNote())) {
                        authenticationInteractor.logout(Base64.encode(username + ":" + pwd), String.valueOf(token.getId()), this, new InteractorCallBack<NetworkResponse>() {
                            @Override
                            public void onSuccess(NetworkResponse networkResponse) {
                                login(username, pwd);
                            }

                            @Override
                            public void onLoading() {
                            }

                            @Override
                            public void onError(VolleyError error) {
                                mUi.showError(R.string.login_error);
                                mUi.showProgressBarLoading(false);
                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onLoading() {
            }

            @Override
            public void onError(VolleyError error) {
                mUi.showError(R.string.login_error);
                mUi.showProgressBarLoading(false);
            }
        });
    }

}
