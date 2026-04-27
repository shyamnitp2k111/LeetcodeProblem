package Networking;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestConnection {
    public static void main(String[] args) {
        HttpURLConnection con;
        try {



                URL url = new URL("https://api-infra.java.oraclecorp.com/openjdk-session/aurora?connection=oci_stage");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-Vault-Token", "s.DTpcAZESbBKDhOGZMnQcOtRG");
                InputStream responseStream = con.getResponseCode() / 100 == 2
                        ? con.getInputStream()
                        : con.getErrorStream();
                Scanner s = new Scanner(responseStream).useDelimiter("\\A");
                String ociFnResponse = s.hasNext() ? s.next() : "";
                // logger.log(Level.INFO, "ociFnResponse is " + ociFnResponse);
                ociFnResponse = ociFnResponse.replace("{\"data\":", "");
                ociFnResponse = ociFnResponse.replace("}}", "}");

            System.out.println(ociFnResponse);



        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
