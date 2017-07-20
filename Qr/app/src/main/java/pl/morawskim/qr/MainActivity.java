package pl.morawskim.qr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = "mmo_qr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startScan(View view) {
        Intent intent = new Intent("la.droid.qr.scan");
        intent.putExtra("la.droid.qr.complete", true);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 0);
        } else {
            Log.e(TAG, "Activity la.droid.qr.scan not found");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TextView textView = (TextView) findViewById(R.id.text_view);
        if (data != null) {
            String result = data.getExtras().getString("la.droid.qr.result");
            textView.setText(result);
        } else {
            textView.setText("");
        }
    }
}
