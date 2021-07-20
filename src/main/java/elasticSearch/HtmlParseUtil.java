package elasticSearch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

/**
 * @ClassName HtmlParseUtil
 * @Description
 * @Author wpj
 * @Date 2021/6/15 22:47
 **/

public class HtmlParseUtil {

    public static void main(String[] args) throws IOException {
        String url = "www.baidu.com";
//        解析网页
        Document document = Jsoup.parse(new URL(url), 30000);
        Element elementById = document.getElementById("sss");//页面元素标签id
        System.out.println(elementById.html());
        Elements elementsByTag = elementById.getElementsByTag("li"); //获取所有li标签
        for (Element element : elementsByTag) {
            element.getElementsByTag("img").eq(0).attr("source-data-lazy"); //懒加载图片
            element.getElementsByClass("p-name").eq(0).text(); //获取name
        }
    }
}
