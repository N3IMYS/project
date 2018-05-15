package com.roadassistance.roadassistanceapp.business.searchinprogress;

/**
 * Created by daniel on 07.03.2018.
 */

public interface ISearchInProgressInteractor {
    void sendFBNTokenToServer(String token, ISearchInProgressInteractorCallback callback);

    void getHelperCoordinates(long userId, ISearchInProgressInteractorCallback callback);

}
