package pl.morawskim.dnssettings;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Otherwise, we get android.os.NetworkOnMainThreadException
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void resolve(View view) {
        TextView textView = (TextView) findViewById(R.id.host_addr);
        EditText editText = (EditText) findViewById(R.id.hostname);
        String host = editText.getText().toString();
        textView.setText("");

        try {
            if (!host.isEmpty()) {
                InetAddress inetAddress = InetAddress.getByName(host);
                String hostAddress = inetAddress.getHostAddress();
                textView.setText(hostAddress);
            }
        } catch (UnknownHostException e) {
            Log.e("MyApplication", "Attempt to resolve host name failed");
        }

    }
}
