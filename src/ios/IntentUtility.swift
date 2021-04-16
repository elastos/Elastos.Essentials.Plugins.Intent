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

 import Foundation
 import WebKit


public func isJSONType(_ str: String) -> Bool {
    let _str = str.trimmingCharacters(in: .whitespacesAndNewlines)
    if (_str.hasPrefix("{") && _str.hasSuffix("}"))
            || (_str.hasPrefix("[") && _str.hasSuffix("]")) {
        return true
    }
    return false
}

 public func openUrl(_ urlString: String) {
     let url = URL(string: urlString)!
     if #available(iOS 10, *) {
         UIApplication.shared.open(url, options: [:],
                                     completionHandler: {
                                     (success) in
         })
     }
     else {
         UIApplication.shared.openURL(url);
     }
 }

 public func anyToString(_ value: Any) -> String {
    if (value is String) {
        return (value as! String);
    }
    else if (value is Bool) {
        return (value as! Bool).toString();
    }
    else if (value is [Any]) {
        return (value as! [Any]).description
    }
    else if (value is [String: Any]) {
        return (value as! [String: Any]).toString()!;
    }
    else if (value is Int) {
        return String(value as! Int)
    }
    else if (value is Double) {
        return String(value as! Double)
    }
    else if (value is NSNull) {
        return "null"
    }
    else {
        return "\(value)"
    }
}

public func anyToJsonFieldString(_ value: Any) -> String {
    if (JSONSerialization.isValidJSONObject(value)) {
        do {
            let data = try JSONSerialization.data(withJSONObject: value, options: [])
            return String(data:data, encoding: .utf8)!
        } catch (let e) {
            print(e.localizedDescription)
        }
    }
    return anyToString(value)
}

 //----------------------------------------------------------------------
 // Extend String to be able to throw simple String Errors
 extension String: LocalizedError{

    public var errorDescription: String? { return self }

    func toDict() -> [String : Any]? {
        let data = self.data(using: String.Encoding.utf8)
        if let dict = try? JSONSerialization.jsonObject(with: data!, options: JSONSerialization.ReadingOptions.mutableContainers) as? [String : Any] {
            return dict
        }
        return nil
    }

    func fromBase64() -> String? {
        guard let data = Data(base64Encoded: self, options: Data.Base64DecodingOptions(rawValue: 0)) else {
            return nil
        }

        return String(data: data as Data, encoding: String.Encoding.utf8)
    }

    func toBase64() -> String? {
        guard let data = self.data(using: String.Encoding.utf8) else {
            return nil
        }

        return data.base64EncodedString(options: Data.Base64EncodingOptions(rawValue: 0))
    }

    func toBase64Data() -> Data? {
        var st = self;
        if (self.count % 4 <= 2){
            st += String(repeating: "=", count: (self.count % 4))
        }
        return Data(base64Encoded: st)
    }

    func encodingURL() -> String {
        return self.addingPercentEncoding(withAllowedCharacters: .urlHostAllowed)!
    }

    func encodingQuery() -> String {
        return self.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
    }

    /*
     * Considering that the current string is a string representation of a (invalid but common) JSON object such as:
     * {key:"value"}
     * This methods makes sure to quote all keys are quoted so a further conversion to a dictionary would always succeed.
     */
    func quotedJsonStringKeys() -> String {
        return self.replacingOccurrences(of: "(\\\"(.*?)\\\"|(\\w+))(\\s*:\\s*(\\\".*?\\\"|.))", with: "\"$2$3\"$4", options: .regularExpression)
    }

    func toBool() -> Bool {
        if (self == "true") {
            return true;
        }
        else {
            return false;
        }
    }


    func indexOf(_ sub:String, backwards:Bool = false) -> Int {
        var pos = -1
        if let range = range(of:sub, options: backwards ? .backwards : .literal ) {
            if !range.isEmpty {
                pos = self.distance(from:startIndex, to:range.lowerBound)
            }
        }
        return pos
    }

    func trim() -> String {
        return self.trimmingCharacters(in: CharacterSet.whitespaces)
    }

    func subString(to: Int) -> String {
        var to = to
        if to > self.count {
            to = self.count
        }
        return String(self.prefix(to))
    }

    func subString(from: Int) -> String {
        if from >= self.count {
            return ""
        }
        let startIndex = self.index(self.startIndex, offsetBy: from)
        let endIndex = self.endIndex
        return String(self[startIndex..<endIndex])
    }

    func subString(start: Int, end: Int) -> String {
        if start < end {
            let startIndex = self.index(self.startIndex, offsetBy: start)
            let endIndex = self.index(self.startIndex, offsetBy: end)

            return String(self[startIndex..<endIndex])
        }
        return ""
    }

    /** i18n - strings localization */
    var localized: String {
        if let _ = UserDefaults.standard.string(forKey: "i18n_language") {} else {
            // we set a default, just in case
            UserDefaults.standard.set("en", forKey: "i18n_language")
            UserDefaults.standard.synchronize()
        }

        let lang = UserDefaults.standard.string(forKey: "i18n_language") ?? "en"

        let path = Bundle.main.path(forResource: "Strings/" + lang, ofType: "lproj")
        let bundle = Bundle(path: path!)

        return NSLocalizedString(self, tableName: nil, bundle: bundle!, value: "", comment: "")
    }
 }

 extension Bool {
    func toString() -> String {
        return self.description;
    }
 }

 extension Dictionary {
    func percentEncoded() -> Data? {
        return map { key, value in
            let escapedKey = "\(key)".addingPercentEncoding(withAllowedCharacters: .urlQueryValueAllowed) ?? ""
            let escapedValue = "\(value)".addingPercentEncoding(withAllowedCharacters: .urlQueryValueAllowed) ?? ""
            return escapedKey + "=" + escapedValue
        }
        .joined(separator: "&")
        .data(using: .utf8)
    }

    func toString() -> String? {
        let data = try? JSONSerialization.data(withJSONObject: self, options: [])
        if let str = String(data: data!, encoding: String.Encoding.utf8) {
            // JSONSerialization espaces slashes... (bug since many years). ios13 has a fix, but only ios13.
            let fixedString = str.replacingOccurrences(of: "\\/", with: "/")

            return fixedString
        }
        return nil
    }
 }

 extension CharacterSet {
    static let urlQueryValueAllowed: CharacterSet = {
        let generalDelimitersToEncode = ":#[]@" // does not include "?" or "/" due to RFC 3986 - Section 3.4
        let subDelimitersToEncode = "!$&'()*+,;="

        var allowed = CharacterSet.urlQueryAllowed
        allowed.remove(charactersIn: "\(generalDelimitersToEncode)\(subDelimitersToEncode)")
        return allowed
    }()
 }

 extension URL {
    public var parametersFromQueryString : [String: String]? {
        guard let components = URLComponents(url: self, resolvingAgainstBaseURL: true),
              let queryItems = components.queryItems else { return nil }
        return queryItems.reduce(into: [String: String]()) { (result, item) in
            result[item.name] = item.value
        }
    }
 }

