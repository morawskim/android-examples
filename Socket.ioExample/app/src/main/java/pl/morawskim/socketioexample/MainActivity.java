package pl.morawskim.socketioexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "socket.io";
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect();
    }

    protected void connect()
    {
        try {
            socket = IO.socket("http://192.168.0.104:3000");
        } catch (URISyntaxException e) {
            Log.e(LOG_TAG, e.toString());
            return;
        }

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                for (Object o : args) {
                    Log.e(LOG_TAG, "Connected:" + o.toString());
                }
            }
        }).on("message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                for (Object o : args) {
                    Log.i(LOG_TAG, "Message:" + o.toString());
                }
                if (args.length == 1 && args[0] instanceof String) {
                    final String m = args[0].toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView textView = (TextView)findViewById(R.id.messages);
                            textView.setText(textView.getText() + "\n" + m);
                        }
                    });
                }
            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                for (Object o : args) {
                    Log.i(LOG_TAG, "Disconnect:" + o.toString());
                }
            }
        }).on(Socket.EVENT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                for (Object o : args) {
                    Log.e(LOG_TAG, "Error:" + o.toString());
                }
            }
        });

        socket.connect();
    }

    public void sendMessage(View view)
    {
        EditText editText = (EditText)findViewById(R.id.message);
        socket.send(editText.getText().toString());
        editText.setText("");
    }
}
