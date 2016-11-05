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

package github.nisrulz.evtrack;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import github.nisrulz.easydeviceinfo.base.EasyAppMod;
import github.nisrulz.easydeviceinfo.base.EasyConfigMod;
import github.nisrulz.easydeviceinfo.base.EasyDeviceMod;
import github.nisrulz.easydeviceinfo.base.EasyIdMod;
import github.nisrulz.easydeviceinfo.base.EasyLocationMod;
import github.nisrulz.easydeviceinfo.base.EasyNetworkMod;
import github.nisrulz.easydeviceinfo.base.NetworkType;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class EvTrack {
  private EasyIdMod easyIdMod;
  private EasyAppMod easyAppMod;
  private EasyConfigMod easyConfigMod;
  private EasyNetworkMod easyNetworkMod;
  private EasyDeviceMod easyDeviceMod;
  private EasyLocationMod easyLocationMod;
  private OkHttpClient client;
  private final String LOGTAG = getClass().getSimpleName();

  private Context context;

  private boolean enableLogs_exp = false;
  private boolean enableLogs_event = false;

  public EvTrack(Context context) {
    this.context = context;
    easyLocationMod = new EasyLocationMod(context);
    easyIdMod = new EasyIdMod(context);
    easyAppMod = new EasyAppMod(context);
    easyConfigMod = new EasyConfigMod(context);
    easyNetworkMod = new EasyNetworkMod(context);
    easyDeviceMod = new EasyDeviceMod(context);
  }

  public void enableLogs() {
    enableLogs_event = true;
    enableLogs_exp = true;
  }

  public void recordEvent(ArrayMap<String, String> event_params, String url,
      Callback httpCallback) {

    //Initialize Http Client
    client = new OkHttpClient();

    //Log the Event details
    if (enableLogs_event) {
      Log.d(LOGTAG, "*-------------Event-------------*\n");
      for (int i = 0; i < event_params.size(); i++) {
        Log.d(LOGTAG, "\nKey : " + event_params.keyAt(i) + "\tValue : " + event_params.valueAt(i));
      }
    }

    //Get LatLong
    double[] latlong = easyLocationMod.getLatLong();

    //Push extra data in event request
    event_params.put("aid", easyIdMod.getAndroidID());
    event_params.put("act", easyAppMod.getActivityName());
    event_params.put("time", String.valueOf(easyConfigMod.getTime()));

    @NetworkType int network_type = easyNetworkMod.getNetworkType();

    switch (network_type) {
      case NetworkType.CELLULAR_2G:
        event_params.put("ct", "2g");
        break;
      case NetworkType.CELLULAR_3G:
        event_params.put("ct", "3g");
        break;
      case NetworkType.CELLULAR_4G:
        event_params.put("ct", "4g");
        break;
      case NetworkType.CELLULAR_UNIDENTIFIED_GEN:
        event_params.put("ct", "cug");
        break;
      case NetworkType.CELLULAR_UNKNOWN:
        event_params.put("ct", "na");
        break;
      case NetworkType.UNKNOWN:
      default:
        event_params.put("ct", "na");
        break;
      case NetworkType.WIFI_WIFIMAX:
        event_params.put("ct", "wifi");
        break;
    }
    event_params.put("lat", String.valueOf(latlong[0]));
    event_params.put("lon", String.valueOf(latlong[1]));

    if (context.checkCallingOrSelfPermission(android.Manifest.permission.INTERNET)
        == PackageManager.PERMISSION_GRANTED) {
      postReq(client, url, event_params, httpCallback);
    }
    else {
      Log.e(LOGTAG, "INTERNET permission not granted! No request was made to the server!");
    }
  }

  public void recordException(Exception exp, ArrayMap<String, String> exp_params, String url,
      Callback httpCallback) {

    //Initialize Http Client
    client = new OkHttpClient();

    exp_params.put("etype", exp.getClass().getName());
    exp_params.put("ecause", exp.getCause().toString());
    exp_params.put("emsg", exp.getMessage());
    exp_params.put("time", Long.toString(easyConfigMod.getTime()));
    exp_params.put("aid", easyIdMod.getAndroidID());
    exp_params.put("mk", easyDeviceMod.getManufacturer());
    exp_params.put("mo", easyDeviceMod.getModel());
    exp_params.put("osv", easyDeviceMod.getOSVersion());

    //Log the Exception details
    if (enableLogs_exp) {
      Log.e(LOGTAG, "*------SDK Encountered an exception !!------*\n");
      Log.e(LOGTAG, "Exception : " + exp_params.get("etype"));
      Log.e(LOGTAG, "Cause : " + exp_params.get("ecause"));
      Log.e(LOGTAG, "Msg : " + exp_params.get("emsg"));
      Log.e(LOGTAG, "Device : " + exp_params.get("mk") + "_" + exp_params.get("mo"));
    }

    if (context.checkCallingOrSelfPermission(android.Manifest.permission.INTERNET)
        == PackageManager.PERMISSION_GRANTED) {
      postReq(client, url, exp_params, httpCallback);
    }
    else {
      Log.e(LOGTAG, "INTERNET permission not granted! No request was made to the server!");
    }
  }

  /**
   * Make a POST Request
   */
  private void postReq(OkHttpClient client, String url, ArrayMap<String, String> params,
      Callback callback) {

    FormBody.Builder formdata = new FormBody.Builder();
    for (int i = 0; i < params.size(); i++) {
      formdata.add(params.keyAt(i), params.valueAt(i));
    }

    RequestBody formBody = formdata.build();

    Request request = new Request.Builder().url(url).post(formBody).build();
    try {
      Call call = client.newCall(request);
      call.enqueue(callback);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}