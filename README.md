# EvTrack

### Specs
[ ![Download](https://api.bintray.com/packages/nisrulz/maven/com.github.nisrulz%3Aevtrack/images/download.svg) ](https://bintray.com/nisrulz/maven/com.github.nisrulz%3Aevtrack/_latestVersion) [![API](https://img.shields.io/badge/API-9%2B-orange.svg?style=flat)](https://android-arsenal.com/api?level=9)

### Show some :heart:
[![GitHub stars](https://img.shields.io/github/stars/nisrulz/evtrack.svg?style=social&label=Star)](https://github.com/nisrulz/evtrack) [![GitHub forks](https://img.shields.io/github/forks/nisrulz/evtrack.svg?style=social&label=Fork)](https://github.com/nisrulz/evtrack/fork) [![GitHub watchers](https://img.shields.io/github/watchers/nisrulz/evtrack.svg?style=social&label=Watch)](https://github.com/nisrulz/evtrack) [![GitHub followers](https://img.shields.io/github/followers/nisrulz.svg?style=social&label=Follow)](https://github.com/nisrulz/evtrack)
[![Twitter Follow](https://img.shields.io/twitter/follow/nisrulz.svg?style=social)](https://twitter.com/nisrulz)


Event & Exception tracking made easy.

Based of [EasyDeviceInfo](https://github.com/nisrulz/easydeviceinfo) lib and [okhttp](https://github.com/square/okhttp).

# Including in your project
EvTrack is available in the Jcenter, so getting it as simple as adding it as a dependency
```gradle
compile 'com.github.nisrulz:evtrack:{latest version}'
```
where `{latest version}` corresponds to published version in [ ![Download](https://api.bintray.com/packages/nisrulz/maven/com.github.nisrulz%3Aevtrack/images/download.svg) ](https://bintray.com/nisrulz/maven/com.github.nisrulz%3Aevtrack/_latestVersion)


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


# Pull Requests
I welcome and encourage all pull requests. It usually will take me within 24-48 hours to respond to any issue or request. Here are some basic rules to follow to ensure timely addition of your request:
  1. Match coding style (braces, spacing, etc.) This is best achieved using `CMD`+`Option`+`L` (Reformat code) on Mac (not sure for Windows) with Android Studio defaults.
  2. If its a feature, bugfix, or anything please only change code to what you specify.
  3. Please keep PR titles easy to read and descriptive of changes, this will make them easier to merge :)
  4. Pull requests _must_ be made against `develop` branch. Any other branch (unless specified by the maintainers) will get rejected.
  5. Check for existing [issues](https://github.com/nisrulz/evtrack/issues) first, before filing an issue.
  6. Have fun!

### Created & Maintained By
[Nishant Srivastava](https://github.com/nisrulz) ([@nisrulz](https://www.twitter.com/nisrulz))

> If you found this library helpful or you learned something from the source code and want to thank me, consider [buying me a cup of](https://www.paypal.me/nisrulz) :coffee:

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
