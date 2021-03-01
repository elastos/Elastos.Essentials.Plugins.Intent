/*
 * Copyright (c) 2021 Elastos Foundation
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.elastos.essentials.plugins.appmanager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class AppManagerPlugin extends CordovaPlugin {
    private static final String LOG_TAG = "AppManager";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            switch (action) {
                case "sendIntent":
                    this.sendIntent(args, callbackContext);
                    break;
                case "setIntentListener":
                    this.setIntentListener(callbackContext);
                    break;
                case "sendIntentResponse":
                    this.sendIntentResponse(args, callbackContext);
                    break;

                default:
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            callbackContext.error(e.getLocalizedMessage());
        }
        return true;
    }

    protected void sendIntent(JSONArray args, CallbackContext callbackContext) throws Exception {
        String action = args.getString(0);
        String params = args.getString(1);

        IntentInfo info = new IntentInfo(action, params, (success, data)->{
            PluginResult result = new PluginResult(PluginResult.Status.OK, data);
            result.setKeepCallback(false);
            callbackContext.sendPluginResult(result);
        });
        IntentManager.getShareInstance().sendIntent(info);
        PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
        pluginResult.setKeepCallback(true);
        callbackContext.sendPluginResult(pluginResult);
    }

    protected void sendIntentResponse(JSONArray args, CallbackContext callbackContext) throws Exception {
        String result = args.getString(0);
        long intentId = args.getLong(1);

        IntentManager.getShareInstance().sendIntentResponse(result, intentId);
        callbackContext.success("ok");
    }

    protected void setIntentListener(CallbackContext callbackContext) throws Exception {
        PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
        pluginResult.setKeepCallback(true);
        callbackContext.sendPluginResult(pluginResult);
        IntentManager.getShareInstance().setListenerReady(callbackContext);
    }
}
