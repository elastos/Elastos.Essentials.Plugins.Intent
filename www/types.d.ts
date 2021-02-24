/*
* Copyright (c) 2018-2021 Elastos Foundation
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

/**
* This is about AppManager which makes it possible to send intents, messages, etc. between DApps.
* <br><br>
* There is no need to use 'AppManagerPlugin' as the plugin name in the manifest.json if you want to use
* this facility, because it's available by default.
* <br><br>
* Usage:
* <br>
* declare let appManager: AppManagerPlugin.AppManager;
*/

declare namespace AppManagerPlugin {

    /**
     * Information about an intent request.
     */
    type ReceivedIntent = {
        /** The action requested from the receiving application. */
        action: string;
        /** Custom intent parameters provided by the calling application. */
        params: any;
        /** Application package id of the calling application. */
        intentId: Number;
        /** In case the intent comes from outside elastOS and was received as a JWT, this JWT is provided here. */
        originalJwtRequest?: string;
    }

    /**
     * The class representing dapp manager for launcher.
     */
    interface AppManager {
        /**
         * Send a intent by action.
         *
         * @param action     The intent action.
         * @param params     The intent params.
         * @param options    Optional options passed to sendIntent().
         * @param onSuccess  The function to call when success.
         * @param onError    The function to call when error, the param is a String. Or set to null.
         */
        sendIntent(action: string, params: any): Promise<any>;

        /**
         * @deprecated Replaced by getStartIntent() but this keeps receiving the start intent for some time for compatibility.
         *
         * Set intent listener for message callback.
         *
         * @param callback   The function receive the intent.
         */
        setIntentListener(callback: (msg: ReceivedIntent)=>void);

        /**
         * Send a intent response by id.
         *
         * @deprecated @param action The intent action. Not used any more. Pass null.
         * @param result     The intent response result.
         * @param intentId   The intent id.
         * @param onSuccess  The function to call when success.
         * @param onError    The function to call when error, the param is a String. Or set to null.
         */
        sendIntentResponse(action: string, result: any, intentId: Number): Promise<any>;

        /**
         *
         * Check is there is a pending intent for the current application. A pending intent is an action
         * requested by a third party application, launching the current application to execute a specific
         * action. In such case, when hasPendingIntent() is true, we want to directly show the appropriate
         * application screen instead of going through the home screen.
         *
         * @param onSuccess  Callback that returns if there is a pending intent or not.
         * @param onError    Function called in case of error.
         */
        hasPendingIntent(onSuccess: (hasPendingIntent: boolean) => void, onError?: (err: any) => void);
    }
}