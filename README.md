## Classes

<dl>
<dt><a href="#AppManager">AppManager</a></dt>
<dd></dd>
</dl>

## Typedefs

<a name="AppManager"></a>

## AppManager
**Kind**: global class

* [AppManager](#AppManager)
    * [new AppManager()](#new_AppManager_new)
    * [.sendIntent(action, params, onSuccess, [onError])](#AppManager+sendIntent)
    * [.setIntentListener(callback)](#AppManager+setIntentListener)
    * [.sendIntentResponse(action, result, intentId, onSuccess, [onError])](#AppManager+sendIntentResponse)

<a name="new_AppManager_new"></a>

### new AppManager()
The class representing dapp manager for launcher.


<a name="AppManager+sendIntent"></a>

### appManager.sendIntent(action, params, onSuccess, [onError])
Send a intent by action.

**Kind**: instance method of [<code>AppManager</code>](#AppManager)

| Param | Type | Description |
| --- | --- | --- |
| action | <code>string</code> | The intent action. |
| params | <code>Object</code> | The intent params. |
| onSuccess | <code>function</code> | The function to call when success. |
| [onError] | <code>function</code> | The function to call when error, the param is a String. Or set to null. |

<a name="AppManager+setIntentListener"></a>

### appManager.setIntentListener(callback)
Set intent listener for message callback.

**Kind**: instance method of [<code>AppManager</code>](#AppManager)

| Param | Type | Description |
| --- | --- | --- |
| callback | [<code>onReceiveIntent</code>](#onReceiveIntent) | The function receive the intent. |

<a name="AppManager+sendIntentResponse"></a>

### appManager.sendIntentResponse(action, result, intentId, onSuccess, [onError])
Send a intent respone by id.

**Kind**: instance method of [<code>AppManager</code>](#AppManager)

| Param | Type | Description |
| --- | --- | --- |
| action | <code>string</code> | The intent action. |
| result | <code>Object</code> | The intent respone result. |
| intentId | <code>long</code> | The intent id. |
| onSuccess | <code>function</code> | The function to call when success. |
| [onError] | <code>function</code> | The function to call when error, the param is a String. Or set to null. |

