package cussingfish.mafiaplayer;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebClient {
    public static String getConnection(String urlString, String json, String auth) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            if (auth != null) {
                connection.setRequestProperty("Authorization", auth);
            }
            if (json != null) {
                OutputStream os = connection.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                osw.write(json);
                osw.flush();
                osw.close();
                os.close();
            }
            int d = connection.getResponseCode();
            if (d == HttpURLConnection.HTTP_OK) {
                InputStream responseBody = connection.getInputStream();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    out.write(buffer, 0, length);
                }
                String responseBodyData = out.toString();
                return responseBodyData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
