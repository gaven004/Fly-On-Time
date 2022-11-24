package com.g.fot;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FetchAirportData {
    static String[] urls = {"http://airport.anseo.cn/c-china",
            "http://airport.anseo.cn/c-china__page-2",
            "http://airport.anseo.cn/c-china__page-3",
            "http://airport.anseo.cn/c-china__page-4",
            "http://airport.anseo.cn/c-china__page-5",
            "http://airport.anseo.cn/c-china__page-6",
            "http://airport.anseo.cn/c-china__page-7",
            "http://airport.anseo.cn/c-china__page-8",
            "http://airport.anseo.cn/c-china__page-9"};

    public static void main(String[] args) throws IOException {
        for (String url : urls) {
            Document doc = Jsoup.connect(url).get();
            Elements table = doc.select("body > div.aw-container-wrap > div > div:nth-child(2) > div > div.col-sm-12.col-md-9.aw-main-content > div > div.mod-body.clearfix > table > tbody > tr");
            for (Element tr : table) {
                Elements tds = tr.getElementsByTag("td");
                String code = null, name = null, city = null;
                int i = 0;
                for (Element td : tds) {
                    switch (i) {
                        case 0 -> city = td.text().split(" ")[0].trim();
                        case 1 -> name = td.text().split(" ")[0].trim();
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
}
