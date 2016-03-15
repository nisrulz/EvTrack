#EvTrack

Event & Exception tracking made easy.
Based of [EasyDeviceInfo](https://github.com/nisrulz/mavenrepo/tree/master/releases/com/github/nisrulz/easydeviceinfo) lib and [okhttp](https://github.com/square/okhttp).

#Integration
- EvTrack is available in the MavenCentral, so getting it as simple as adding it as a dependency
```gradle
compile 'com.github.nisrulz:evtrack:1.0.1'
```

#Usage
+ Create an instance of the ***EvTrack*** class
```java
EvTrack evTrack = new EvTrack();
```

+ Next if in debug stage, enable logs
```java
evTrack.enableLogs();
```

+ To record an event, pass in an ArrayMap to recordEvent() function
```java
ArrayMap<String, String> event_params = new ArrayMap<String,String>();
event_params.put("key1", "value1");
event_params.put("key2", "value2");
..
event_params.put("key7", "value7");
evTrack.recordEvent(event_params, SERVER, httpCallback);
```

+ To record an exception, pass in an ArrayMap to recordException() function
```java
ArrayMap<String, String> exp_param = new ArrayMap<String,String>();
exp_param.put("key1", "value1");
exp_param.put("key2", "value2");
..
exp_param.put("key7", "value7");
evTrack.recordException(e, exp_param, SERVER , httpCallback);
```
+ Implement the Callback
```java
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
```


License
=======

    Copyright 2016 Nishant Srivastava

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

