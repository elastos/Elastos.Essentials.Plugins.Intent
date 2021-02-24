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

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

public class AppManagerPlugin extends CordovaPlugin {
    protected CallbackContext mMessageContext = null;
    protected CallbackContext mIntentContext = null;

    private boolean isChangeIconPath = false;


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
                case "hasPendingIntent":
                    this.hasPendingIntent(callbackContext);
                    break;

                default:
                    return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            callbackContext.error(e.getLocalizedMessage());
        }
        return true;
    }

    protected void sendIntent(JSONArray args, CallbackContext callbackContext) throws Exception {
        String action = args.getString(0);
        String params = args.getString(1);
        PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
        pluginResult.setKeepCallback(true);
        callbackContext.sendPluginResult(pluginResult);
    }

    protected void sendIntentResponse(JSONArray args, CallbackContext callbackContext) throws Exception {
        String result = args.getString(1);
        long intentId = args.getLong(2);

//        IntentManager.getShareInstance().sendIntentResponse(result, intentId);
        callbackContext.success("ok");
    }

    protected void setIntentListener(CallbackContext callbackContext) throws Exception {
        mIntentContext = callbackContext;
        PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
        pluginResult.setKeepCallback(true);
        callbackContext.sendPluginResult(pluginResult);
//        IntentManager.getShareInstance().setIntentReady(getModeId());
    }

    protected void hasPendingIntent(CallbackContext callbackContext) throws Exception {
//        Boolean ret = IntentManager.getShareInstance().getIntentCount(getModeId()) != 0;
//        callbackContext.success(ret.toString());
    }

    public Boolean isIntentReady() {
        return (mIntentContext != null);
    }

//    public void onReceiveIntent(IntentInfo info) {
//        if (mIntentContext == null)
//            return;
//
//        JSONObject ret = new JSONObject();
//        try {
//            if (info.registeredAction != null) {
//                ret.put("action", info.registeredAction);
//            }
//            else {
//                ret.put("action", info.action);
//            }
//            ret.put("params", info.params);
//            ret.put("from", info.fromId);
//            ret.put("intentId", info.intentId);
//            ret.put("originalJwtRequest", info.originalJwtRequest);
//            PluginResult result = new PluginResult(PluginResult.Status.OK, ret);
//            result.setKeepCallback(true);
//            mIntentContext.sendPluginResult(result);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void onReceiveIntentResponse(IntentInfo info) {
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("action", info.action);
//            if (info.params != null) {
//                obj.put("result", info.params);
//            }
//            else {
//                obj.put("result", "null");
//            }
//            obj.put("from", info.toId);
//            if (info.responseJwt != null)
//                obj.put("responseJWT", info.responseJwt);
//
//            info.onIntentResponseCallback.onIntentResponse(true, obj);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

}
