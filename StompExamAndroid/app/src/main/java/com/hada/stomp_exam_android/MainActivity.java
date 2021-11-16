package com.hada.stomp_exam_android;

import static java.util.jar.Pack200.Packer.ERROR;
import static javax.net.ssl.SSLEngineResult.Status.CLOSED;
import static ua.naiksoftware.stomp.dto.LifecycleEvent.Type.OPENED;
import static ua.naiksoftware.stomp.provider.OkHttpConnectionProvider.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompHeader;

public class MainActivity extends AppCompatActivity {

    private StompClient stompClient;
    private List<StompHeader> headerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void initStomp(){
        stompClient= Stomp.over(Stomp.ConnectionProvider.OKHTTP, wsServerUrl);

        stompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    Log.d(TAG, "Stomp connection opened");
                    break;
                case ERROR:
                    Log.e(TAG, "Error", lifecycleEvent.getException());
                    if(lifecycleEvent.getException().getMessage().contains("EOF")){
                        isUnexpectedClosed=true;
                    }
                    break;
                case CLOSED:
                    Log.d(TAG, "Stomp connection closed");
                    if(isUnexpectedClosed){
                        /**
                         * EOF Error
                         */
                        initStomp();
                        isUnexpectedClosed=false;
                    }
                    break;
            }
        });

        // add Header
        headerList=new ArrayList<>();
        headerList.add(new StompHeader("Authorization", G.accessToken));
        stompClient.connect(headerList);
    }
}