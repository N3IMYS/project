package com.roadassistance.roadassistanceapp.business.searchinprogress;

import com.roadassistance.roadassistanceapp.data.searchinprogress.web.ISearchInProgressWebRepository;
import com.roadassistance.roadassistanceapp.data.searchinprogress.web.ISerachInProgressWebRepositoryCallback;

/**
 * Created by daniel on 07.03.2018.
 */

public class SearchInProgressInteractor implements ISearchInProgressInteractor {

    ISearchInProgressWebRepository webRepository;

    public SearchInProgressInteractor(ISearchInProgressWebRepository webRepository) {
        this.webRepository = webRepository;
    }

    @Override
    public void sendFBNTokenToServer(String token, final ISearchInProgressInteractorCallback callback) {
        webRepository.sendFBNTokenToServer(token, new ISerachInProgressWebRepositoryCallback() {
            @Override
            public void onSuccess() {
                if (callback != null) {
                    callback.onSuccess();
                }
            }

            @Override
            public void onFailure() {
                if (callback != null) {
                    callback.onFailure();
                }
            }
        });
    }

    @Override
    public void getHelperCoordinates(long userId, ISearchInProgressInteractorCallback callback) {

    }
}
