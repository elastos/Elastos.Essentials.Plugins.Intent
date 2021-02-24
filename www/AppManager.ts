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

let exec = cordova.exec;

class AppManagerImpl implements AppManagerPlugin.AppManager {
    constructor() {
    }

    sendIntent(action: string, params: any): Promise<any> {
        return new Promise((resolve, reject)=>{
            exec((ret)=>{
                if (typeof (ret.result) == "string") {
                    ret.result = JSON.parse(ret.result);
                }
                resolve(ret);
            },
            (err)=>{
              reject(err);
            },
            'AppManager', 'AppManager', [action, JSON.stringify(params)]);
          });
    }

    setIntentListener(callback: (msg: AppManagerPlugin.ReceivedIntent) => void) {
        function _onReceiveIntent(ret) {
            if (typeof (ret.params) == "string") {
                ret.params = JSON.parse(ret.params);
            }
            if (callback) {
                callback(ret);
            }
        }
        exec(_onReceiveIntent, null, 'AppManager', 'setIntentListener');
    }

    sendIntentResponse(action: string, result: any, intentId: Number): Promise<any> {
        return new Promise((resolve, reject)=>{
            exec((ret)=>{
                if (typeof (ret.result) == "string") {
                    ret.result = JSON.parse(ret.result);
                }
                resolve(ret);
            },
            (err)=>{
              reject(err);
            },
            'AppManager', 'sendIntentResponse', [action, JSON.stringify(result), intentId]);
        });
    }

    hasPendingIntent(onSuccess: (hasPendingIntent: boolean) => void, onError?: (err: any) => void) {
        function _onSuccess(ret: any) {
            if (typeof (ret) == "string") {
                if (ret == "true") {
                    ret = true;
                }
                else {
                    ret = false;
                }
            }
            if (onSuccess) {
                onSuccess(ret);
            }
        }
        exec(_onSuccess, onError, 'AppManager', 'hasPendingIntent', []);
    }
}

export = new AppManagerImpl();