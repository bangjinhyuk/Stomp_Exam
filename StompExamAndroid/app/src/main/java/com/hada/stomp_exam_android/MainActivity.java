package com.hada.stomp_exam_android;

import static ua.naiksoftware.stomp.provider.OkHttpConnectionProvider.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicBoolean;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class MainActivity extends AppCompatActivity {

    //url을 설정할때 연결하려는 주소가 http라면 ws, https라면 wss로 바꿔야한다. 또한 마지막에 /websocket을 꼭 붙여야한다.
    private String url = "ws://bangi98.cafe24.com:9091/stomp/websocket"; // 소켓에 연결하는 엔드포인트가 /socket일때 다음과 같음
    private StompClient stompClient =  Stomp.over(Stomp.ConnectionProvider.OKHTTP, url);
    private Button enter_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enter_button = findViewById(R.id.enter_button);
        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.d(TAG, "onClick: enter_button");
                    runStomp();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @SuppressLint("CheckResult")
    public void runStomp() throws JSONException {
        //subscribr 할 주소
        stompClient.topic("/sub/chat/room/7ef40e12-a3c5-4a1c-ab99-a914940b1a92").subscribe(
                (topicMessage) -> Log.i("message Recieve", topicMessage.getPayload())
        );

        //헤더 추가 할때 => 토큰 넣기
//        List<StompHeader> headerList = new ArrayList<>();
//        headerList.add(new StompHeader("inviteCode","test0912"));
//        headerList.add(new StompHeader("username", text.value));
//        headerList.add(new StompHeader("positionType", "1"));
//        stompClient.connect(headerList);


        stompClient.lifecycle().subscribe((lifecycleEvent ->
                Log.i("lifecycleEvent", lifecycleEvent.getType().toString())
        ));

        stompClient.connect();


        JSONObject data = new JSONObject();
        data.put("roomId", "7ef40e12-a3c5-4a1c-ab99-a914940b1a92");
        data.put("writer", "bbangi");
        stompClient.send("/pub/chat/enter", data.toString()).subscribe();
    }


}