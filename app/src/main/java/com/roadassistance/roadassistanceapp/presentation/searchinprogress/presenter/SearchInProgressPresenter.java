package com.roadassistance.roadassistanceapp.presentation.searchinprogress.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.RemoteMessage.Notification;
import com.roadassistance.roadassistanceapp.business.searchinprogress.ISearchInProgressInteractor;
import com.roadassistance.roadassistanceapp.business.searchinprogress.ISearchInProgressInteractorCallback;

import static android.content.ContentValues.TAG;

/**
 * Created by daniel on 07.03.2018.
 */

public class SearchInProgressPresenter extends FirebaseInstanceIdService implements ISearchInProgressPresenter,
        ISearchInProgressInteractorCallback {

    ISearchInProgressInteractor mInteractor;
    Context mContext;

    public SearchInProgressPresenter(ISearchInProgressInteractor interactor, Context context) {
        mInteractor = interactor;
        mContext = context;
    }

    public SearchInProgressPresenter() {
    }

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        mInteractor.sendFBNTokenToServer(refreshedToken, this);
    }

    @Override
    public void onSuccess() {


    }

    @Override
    public void onFailure() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Server Error")
                .setMessage("Try again , or check your internet connection")
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String token = FirebaseInstanceId.getInstance().getToken();
                        sendRegistrationToServer(token);
                        dialog.dismiss();
                    }
                }).create();
    }



}
