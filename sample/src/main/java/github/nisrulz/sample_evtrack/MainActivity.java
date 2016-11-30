/*
 * Copyright (C) 2016 Nishant Srivastava
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package github.nisrulz.sample_evtrack;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import github.nisrulz.evtrack.EvTrack;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

  private EvTrack evTrack;
  private final String SERVER = "http://xx.xxx.xxx.xx:xxxx/";

  private final Logger logger = Logger.withTag(this.getClass().getSimpleName());

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
    public void onFailure(Call call, IOException e) {
      logger.log("Something went wrong with XYZ.").withCause(e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
      logger.log(String.valueOf(response.code()));
    }
  };
}
