package com.example.nguyenduythanh.news;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<News> newsArrayList;
    NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        newsArrayList = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadData().execute("http://vnexpress.net/rss/thoi-su.rss");
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, NewsWebView.class);
                intent.putExtra("link", newsArrayList.get(position).getLink());
                startActivity(intent);
            }
        });
    }

    public class ReadData extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readContentFromURL(params[0]);
        }

        @Override
        protected void onPostExecute(String xmlString) {
            // Tạo đối tượng parser dùng để đọc xmlString vào document
            XMLDomParser parser = new XMLDomParser();
            Document document = parser.getDocument(xmlString);
            super.onPostExecute(xmlString);
            // tạo nodelist với item, decreption lấy ra từ document
            NodeList listOfItem = document.getElementsByTagName("item");
            NodeList listOfDescription = document.getElementsByTagName("description");
            String title = "";
            String link = "";
            String image = "";
            String pubDate = "";
            for (int i = 0; i < listOfItem.getLength(); i++) {
                // Lấy nội dung cData, nơi chứa link ảnh
                String cData = listOfDescription.item(i + 1).getTextContent();
                Pattern pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = pattern.matcher(cData);
                if (matcher.find()) {
                    image = matcher.group(1);
                }
                Element element = (Element) listOfItem.item(i);
                title = parser.getValue(element, "title");
                link = parser.getValue(element, "link");
                pubDate = parser.getValue(element, "pubDate");
                newsArrayList.add(new News(title, link, image, pubDate));
            }
            newsAdapter = new NewsAdapter(MainActivity.this, android.R.layout.simple_list_item_1, newsArrayList);
            listView.setAdapter(newsAdapter);
        }
    }

    // Hàm đọc nội dung từ URL
    private static String readContentFromURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // Tạo đối tượng URL và đối tượng kết nối URL
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();

            // Dùng bufferedReader để đọc từ kết nối
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}