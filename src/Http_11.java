import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Http_11 {

    public static void main(String[] args) throws Exception {

        URL urlForGet = new URL("https://yandex.ru/");
        URLConnection connectionForGet = urlForGet.openConnection();

        System.out.println("URL: " + urlForGet);
        System.out.println();

        printHeaders(connectionForGet);

        printBody(connectionForGet);

        System.out.println();


        // Сократим ссылку с помощью яндексового сокращателя ссылок
        URL urlForPost = new URL("https://clck.ru/--");

        Map<String, Object> params = new LinkedHashMap<>();
        String urlForShortening = "https://docs.oracle.com/javase/8/docs/";
        params.put("url", urlForShortening);

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection connectionForPost = (HttpURLConnection) urlForPost.openConnection();
        connectionForPost.setRequestMethod("POST");
        connectionForPost.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connectionForPost.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        connectionForPost.setDoOutput(true);
        connectionForPost.getOutputStream().write(postDataBytes);

        System.out.println("URL: " + urlForPost);
        System.out.println();

        System.out.println("Полная ссылка для сокращения: " + urlForShortening);
        System.out.println();

        printHeaders(connectionForPost);

        printBody(connectionForPost);

    }

    private static void printHeaders(URLConnection connection) {

        System.out.println("ЗАГОЛОВКИ: ");
        Map<String, java.util.List<String>> map;
        map = connection.getHeaderFields();
        for (Map.Entry<String, java.util.List<String>> param : map.entrySet()) {
            System.out.println(param.getKey() + " " + param.getValue());
        }
        System.out.println();

    }

    private static void printBody(URLConnection connection) {

        System.out.println("СОДЕРЖИМОЕ: ");
        try (InputStream inputStream = connection.getInputStream()) {
            Reader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            for (int c; (c = in.read()) >= 0; )
                System.out.print((char) c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();

    }

}
