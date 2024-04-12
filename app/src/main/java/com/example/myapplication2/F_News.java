package com.example.myapplication2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class F_News extends AppCompatActivity {
    TextView n_title1, n_title2, n_title3, n_title4, n_content1, n_content2, n_content3, n_content4;
    Button re, back_btn;
    static ArrayList<String> news_link = new ArrayList<>();
    ImageButton n1, n2, n3, n4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_news);
        back_btn = findViewById(R.id.backBtn);

        n_title1 = findViewById(R.id.news_title1);
        n_title2 = findViewById(R.id.news_title2);
        n_title3 = findViewById(R.id.news_title3);
        n_title4 = findViewById(R.id.news_title4);

        n_content1 = findViewById(R.id.news_content1);
        n_content2 = findViewById(R.id.news_content2);
        n_content3 = findViewById(R.id.news_content3);
        n_content4 = findViewById(R.id.news_content4);

        n1 = (ImageButton) findViewById(R.id.news1);
        n2 = findViewById(R.id.news2);
        n3 = findViewById(R.id.news3);
        n4 = findViewById(R.id.news4);

        getText();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(F_News.this, D_CommunityMainActivity.class);
                startActivity(intent);

            }
        });

        n1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(news_link.get(0));
                browserIntent.setData(uri);
                startActivity(browserIntent);
            }
        });
        n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(news_link.get(1));
                browserIntent.setData(uri);
                startActivity(browserIntent);
            }
        });
        n3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(news_link.get(2));
                browserIntent.setData(uri);
                startActivity(browserIntent);
            }
        });
        n4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(news_link.get(3));
                browserIntent.setData(uri);
                startActivity(browserIntent);
            }
        });
//        re = (Button)findViewById(R.id.re_btn);
//        re.setOnClickListener(new View.OnClickListener()
//        {@Override public void onClick(View v) {getText();}});


    }

    private void getText() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=102&sid2=252").get();
                    Elements total_data = doc.select(".type06_headline").select("li");

                    int i = 1;
                    for (Element elem : total_data) {
                        if (i > 4) {
                            break;
                        }
                        String title = elem.select("dl").select("dt a").text();
                        news_link.add(elem.getElementsByAttribute("href").attr("href"));
                        String content = elem.select("dl").select("dd span.lede").text();

                        if (title.length() > 23) {
                            title = title.substring(0, 23) + "...";
                        }

                        if (content.length() > 23) {
                            content = content.substring(0, 23) + "...";
                        }

                        switch (i) {
                            case 1:
                                n_title1.setText(title);
                                n_content1.setText(content);
                                break;
                            case 2:
                                n_title2.setText(title);
                                n_content2.setText(content);
                                break; // 종료
                            case 3:
                                n_title3.setText(title);
                                n_content3.setText(content);
                                break;
                            case 4:
                                n_title4.setText(title);
                                n_content4.setText(content);
                                break;
                        }
                        i++;

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}