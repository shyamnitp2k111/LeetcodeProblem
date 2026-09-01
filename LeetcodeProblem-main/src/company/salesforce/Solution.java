package company.salesforce;

import java.util.*;

/*Count HTTP
lava 21
Response Codes
Problem Title:
HTTP Response Code Counter
Problem Description:
Given a set of HTTP access log entries, parse each line and count the occurrences of each HTTP response status code. Print each unique response code and its count.
Input Format:
Each line is an HTTP access log entry in Common Log Format, e.g.:
192.168.1.1 - - [10/Oct/2024:13:55:36
-0700] "GET /index.html HTTP/1.1"
200 2326
10.0.0.1 - - [10/Oct/2024:13:56:01
-0700] "POST /api/login HTTP/1.1"
401 512
192.168.1.1 - - [10/Oct/2024:13:56:15
-0700] "GET /dashboard HTTP/1.1"
200 8451
10.0.0.2 - - [10/Oct/2024:13:56:22
-0700] "GET /missing HTTP/1.1" 404
274
Output Format:
For each unique status code found, print:
Response Code: <code> | Count:
<count>
Sample Output:
Response Code: 200 | Count: 2
Response Code: 401 | Count: 1
Response Code: 404 | Count: 1

*/

public class Solution {


    //HTTP/1.1"
    
    public static void main(String[] args) {
        
        String str = """
        10.176.217.151 - svc_sls-artifact-g [27/May/2026:06:17:48 -0700] "HEAD /continuous/main/main.26.0526.0852.a5e746b/installers/tableau-setup-std-main.26.0526.0852.dmg HTTP/1.1" 200 - "-" "python-requests/2.32.3"
10.133.116.79 - nvinay [27/May/2026:06:23:07 -0700] "GET /continuous/main/ HTTP/1.1" 200 2280 "-" "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/148.0.0.0 Safari/537.36"
10.176.218.189 - svc_sls-artifact-g [27/May/2026:02:24:41 -0700] "HEAD /continuous/main/main.26.0526.0852.a5e746b/installers/tableau-setup-std-main.26.0526.0852.dmg HTTP/1.1" 200 - "-" "python-requests/2.32.3"
10.176.12.37 - svc_dckr_publisher [27/May/2026:00:37:52 -0700] "GET /continuous/main/main.26.0525.1531.0d5305e/installers/tableau-server-main.26.0525.1531.x86_64.rpm HTTP/1.1" 200 3411906051 "-" "curl/7.61.1"
10.176.217.151 - svc_sls-artifact-g [27/May/2026:04:56:58 -0700] "HEAD /continuous/main/main.26.0526.1720.b495b1a/installers/tableau-setup-std-main.26.0526.1720.dmg HTTP/1.1" 401 - "-" "python-requests/2.32.3"
10.133.116.77 - rojav [26/May/2026:23:27:38 -0700] "GET /remotebuilds/main/main.26.0525.2252.8aac2fe/ HTTP/1.1" 200 802 "-" "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/148.0.0.0 Safari/537.36"
10.176.218.189 - svc_sls-artifact-g [27/May/2026:00:03:44 -0700] "HEAD /continuous/main/main.26.0525.1531.0d5305e/installers/tableau-setup-std-main.26.0525.1531.dmg HTTP/1.1" 200 - "-" "python-requests/2.32.3"
10.176.218.189 - svc_sls-artifact-g [27/May/2026:00:27:50 -0700] "HEAD /continuous/tableau-2025-3/tableau-2025-3.26.0508.0337.f546aa7 HTTP/1.1" 301 - "-" "python-requests/2.32.3"
10.176.218.189 - svc_sls-artifact-g [26/May/2026:21:27:43 -0700] "HEAD /continuous/tableau-2025-3/tableau-2025-3.26.0508.0337.f546aa7 HTTP/1.1" 301 - "-" "python-requests/2.32.3"
10.176.218.189 - svc_sls-artifact-g [26/May/2026:19:18:14 -0700] "HEAD /continuous/main/main.26.0525.1531.0d5305e/installers/tableau-setup-std-main.26.0525.1531.dmg HTTP/1.1" 200 - "-" "python-requests/2.32.3"
10.176.218.189 - svc_sls-artifact-g [26/May/2026:16:16:02 -0700] "HEAD /continuous/main/main.26.0525.1531.0d5305e/installers/tableau-setup-std-main.26.0525.1531.dmg HTTP/1.1" 502 - "-" "python-requests/2.32.3"
10.133.117.121 - sjauhal [26/May/2026:15:46:52 -0700] "GET /installers/ HTTP/1.1" 200 1502 "https://devbuildsweb.tsi.lan/" "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/148.0.0.0 Safari/537.36"
10.133.117.121 - sjauhal [26/May/2026:15:46:52 -0700] "GET /installers HTTP/1.1" 301 335 "https://devbuildsweb.tsi.lan/" "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/148.0.0.0 Safari/537.36"
10.176.218.189 - svc_sls-artifact-g [26/May/2026:12:05:11 -0700] "HEAD /continuous/main/main.26.0525.1531.0d5305e HTTP/1.1" 301 - "-" "python-requests/2.32.3"
10.108.16.98 - spesalj [26/May/2026:10:59:58 -0700] "GET /continuous/main/?C=M;O=D HTTP/1.1" 401 468 "-" "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/148.0.0.0 Safari/537.36 Edg/148.0.0.0"
10.176.218.189 - svc_sls-artifact-g [26/May/2026:11:02:21 -0700] "HEAD /continuous/main/main.26.0526.0257.570f0c9/installers/tableau-setup-std-main.26.0526.0257.dmg HTTP/1.1" 200 - "-" "python-requests/2.32.3"
10.133.116.202 - singh.ankur [26/May/2026:07:01:07 -0700] "GET /remotebuilds/main/main.26.0525.2252.8aac2fe/ HTTP/1.1" 200 4958 "-" "curl/8.7.1"
10.133.116.202 - singh.ankur [26/May/2026:07:01:05 -0700] "GET /remotebuilds/main/main.26.0526.0141.45fe900/ HTTP/1.1" 503 4958 "-" "curl/8.7.1"
10.108.25.153 - svc_hammer1 [26/May/2026:06:00:14 -0700] "GET /maestro/main/ HTTP/1.1" 200 647 "-" "python-requests/2.32.3"
10.108.25.153 - svc_hammer1 [26/May/2026:06:00:13 -0700] "GET /continuous/ HTTP/1.1" 404 1166 "-" "python-requests/2.32.3"
10.108.25.153 - svc_hammer1 [26/May/2026:06:00:13 -0700] "GET /continuous HTTP/1.1" 301 335 "-" "python-requests/2.32.3"
10.176.217.151 - svc_sls-artifact-g [27/May/2026:03:49:02 -0700] "HEAD /continuous/main/main.26.0526.0852.a5e746b/installers/tableau-setup-std-main.26.0526.0852.dmg HTTP/1.1" 200 - "-" "python-requests/2.32.3"
10.133.116.77 - rojav [27/May/2026:04:00:33 -0700] "GET /maestro/main/ HTTP/1.1" 504 644 "-" "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/148.0.0.0 Safari/537.36"
        """;
        
        
        String[] listOfString = str.split(" ");
        
        Map<String, Integer> map = new HashMap<>();
        
        
        for(int index = 0; index < listOfString.length ; index++) {
            
            if(listOfString[index].contains("HTTP/1.1")) {
                String responseCode = listOfString[index + 1];
                map.put(responseCode, map.getOrDefault(responseCode, 0) + 1);
                index++;
            }
            
        }
        
        
        for(Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() +" " + entry.getValue());
        }
        
        
    }

}
