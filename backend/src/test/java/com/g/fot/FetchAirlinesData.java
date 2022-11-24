package com.g.fot;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FetchAirlinesData {
    public static void main(String[] args) throws IOException {
        String url = "https://www.ip138.com/hangkong/";
        Document doc = Jsoup.connect(url).get();

        Elements table = doc.select("body > div > div.container > div.content > div.mod-panel > div.bd > div > table > tbody > tr");
        for (Element tr : table) {
            if (tr.hasAttr("bgcolor")) {
                continue;
            }

            Elements tds = tr.getElementsByTag("td");
            String code = null, name = null, city = null;
            int i = 0;
            for (Element td : tds) {
                switch (i) {
                    case 0 -> name = td.text().split(" ")[0].trim();
                    case 1 -> city = td.text().split(" ")[0].trim();
                    case 2 -> code = td.text().split(" ")[0].trim();
                }
                i++;
            }

            if (code != null && code.length() > 0) {
                System.out.printf("%s,%s,%s%n", code, name, city);
            }
        }
    }
}
