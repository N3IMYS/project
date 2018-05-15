package com.roadassistance.roadassistanceapp.data.searchinprogress.web;

/**
 * Created by daniel on 07.03.2018.
 */

public interface ISearchInProgressWebRepository {
    void sendFBNTokenToServer(String token, ISerachInProgressWebRepositoryCallback callback);
    void getHelperCoordinates(long userId, ISerachInProgressWebRepositoryCallback callback);
}
