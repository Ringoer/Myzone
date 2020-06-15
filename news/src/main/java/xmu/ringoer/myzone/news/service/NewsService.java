package xmu.ringoer.myzone.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ringoer.myzone.news.dao.NewsDao;
import xmu.ringoer.myzone.news.domain.ElsInfo;
import xmu.ringoer.myzone.news.util.ResponseUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ringoer
 */
@Service
public class NewsService {

    @Autowired
    private NewsDao newsDao;

    public Object getElsInfo() {
        try {
            URL url = new URL("https://els.ztgame.com/index.shtml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            StringBuilder stringBuilder = new StringBuilder("");
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                stringBuilder.append(line);
            }
            reader.close();

            List<ElsInfo> elsInfos = new ArrayList<>();
            String[] news = stringBuilder.toString().split("<ul class=\"index_news_main\"")[1].split("<li style=\"\"");
            for (int i = 1; i < news.length; i++) {
                String date = news[i].split("<span class=\"day flr\">")[1].split("</span>")[0].trim();
                String type = news[i].split("<span class=\"flt\">")[1].split("</span>")[0].trim();
                String target = news[i].split("href=\"")[1].split("\"")[0].trim().replace("&amp;", "&");
                if(!target.contains("https")) {
                    target = "https://els.ztgame.com" + target;
                }
                String title = news[i].split("style=\"\">")[1].split("</a>")[0].trim();
                elsInfos.add(new ElsInfo(date, type, target, title));
            }

            return ResponseUtil.ok(elsInfos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseUtil.serious();
    }
}
