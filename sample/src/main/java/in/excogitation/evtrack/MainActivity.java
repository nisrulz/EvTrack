package in.excogitation.evtrack;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import in.excogitation.sdk.android.EvTrack;

public class MainActivity extends AppCompatActivity {

    private EvTrack evTrack;
    private final String SERVER = "http://xx.xxx.xxx.xx:xxxx/";
    private final String LOGTAG = getClass().getSimpleName().toString();

    Button btn_event, btn_exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        evTrack = new EvTrack(getApplicationContext());
        evTrack.enableLogs();

        btn_event = (Button) findViewById(R.id.button_event);
        btn_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayMap<String, String> event_params = new ArrayMap<String, String>();
                event_params.put("sdk", "1.0");
                evTrack.recordEvent(event_params, SERVER + "test", httpCallback);
            }
        });


        btn_exp = (Button) findViewById(R.id.button_exp);
        btn_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    throw new RuntimeException("This is an Error");
                } catch (RuntimeException e) {
                    ArrayMap<String, String> exp_param = new ArrayMap<String, String>();
                    exp_param.put("code", "SP0002");
                    exp_param.put("sdk", "1.1");
                    evTrack.recordException(e, exp_param, SERVER + "test", httpCallback);
                }
            }
        });
    }

    private final Callback httpCallback = new Callback() {
        @Override
        public void onFailure(Request request, IOException e) {
            Log.e(LOGTAG, "Req Failed : " + e.getLocalizedMessage());
        }

        @Override
        public void onResponse(Response response) throws IOException {
            Log.i(LOGTAG, "Req Successful : " + String.valueOf(response.code()));
        }
    };
}
