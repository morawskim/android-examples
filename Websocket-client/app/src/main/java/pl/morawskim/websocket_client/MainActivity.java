package pl.morawskim.websocket_client;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    protected final static String LOG_TAG = "websocket-client";
    private WebSocketClient webSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect();
    }

    protected void connect()
    {
        URI uri;
        try {
            uri = new URI("ws://echo.websocket.org");
        } catch (URISyntaxException e) {
            Log.e(LOG_TAG, e.getMessage());
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                webSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
                Log.i(LOG_TAG, "Websocket was opened ");
            }

            @Override
            public void onMessage(String message) {
                final String m = message;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView = (TextView)findViewById(R.id.messages);
                        textView.setText(textView.getText() + "\n" + m);
                    }
                });
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.i(LOG_TAG, "Websocket was closed: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                Log.e(LOG_TAG, ex.getMessage());
                Log.e(LOG_TAG, ex.toString());
            }
        };
        webSocketClient.connect();
    }

    public void sendMessage(View view) {
        EditText editText = (EditText)findViewById(R.id.message);
        webSocketClient.send(editText.getText().toString());
        editText.setText("");
    }
}
