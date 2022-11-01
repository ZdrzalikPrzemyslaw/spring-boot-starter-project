package tech.zdrzalik.courses.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import netscape.javascript.JSObject;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class LogtailAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        iLoggingEvent.getFormattedMessage();
        URL url = null;
        try {
            url = new URL("https://in.logtail.com/");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Authorization","Bearer bGuDiN7S7bXF9GD23yFJWKGw");

            connection.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("level",iLoggingEvent.getLevel());
            jsonObject.put("message",iLoggingEvent.getMessage());

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(jsonObject.toString());
            wr.close();
            int HttpResult = connection.getResponseCode();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }
}
