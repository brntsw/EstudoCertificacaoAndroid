package com.udacity.br.estudocertificadoandroid.receiver;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by BPardini on 10/02/2017.
 */

public class TurmasResultReceiver extends ResultReceiver {
    private Receiver receiver;

    public TurmasResultReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver){
        this.receiver = receiver;
    }

    public interface Receiver{
        void onReceiveResult(int resultCode, Bundle resultData);
    }

    protected void onReceiveResult(int resultCode, Bundle resultData){
        if(receiver != null){
            receiver.onReceiveResult(resultCode, resultData);
        }
    }
}
