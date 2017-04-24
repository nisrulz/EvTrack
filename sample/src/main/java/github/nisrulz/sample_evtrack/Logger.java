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

import android.util.Log;

public class Logger {

  private final String TAG;
  private final int priority;

  public static Logger withTag(String tag) {
    return new Logger(tag);
  }

  private Logger(String TAG) {
    this.TAG = TAG;
    this.priority = Log.DEBUG; // This could be ERROR / INFO / VERBOSE
  }

  public Logger log(String message) {
    if (BuildConfig.DEBUG) {
      Log.println(priority, TAG, message);
    }
    return this;
  }

  public void withCause(Exception cause) {
    if (BuildConfig.DEBUG) {
      Log.println(priority, TAG, Log.getStackTraceString(cause));
    }
  }
}