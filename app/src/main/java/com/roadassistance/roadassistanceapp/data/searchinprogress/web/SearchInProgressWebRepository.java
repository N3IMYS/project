package com.roadassistance.roadassistanceapp.data.searchinprogress.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by daniel on 07.03.2018.
 */

public class SearchInProgressWebRepository implements ISearchInProgressWebRepository {
    OkHttpClient client;
    Gson gson;

    public SearchInProgressWebRepository(OkHttpClient client, Gson gson) {
        this.client = client;
        this.gson = gson;
    }

    @Override
    public void sendFBNTokenToServer(String token, final ISerachInProgressWebRepositoryCallback callback) {
        JsonObject json = (JsonObject) new JsonParser().parse(token);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json.toString());
        //TODO Need to fill a request;
        Request request = null;
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (callback != null) {
                        callback.onSuccess();
                    } else {
                        if (callback != null) {
                            callback.onFailure();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void getHelperCoordinates(long userId, ISerachInProgressWebRepositoryCallback callback) {

    }
}
