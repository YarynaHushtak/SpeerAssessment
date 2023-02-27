package com.assessment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WikiLink {
    public static WebDriver driver;

    public static void main(String[] args) throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        getWikiLinksInWiki(url, 20);
    }

    public static void getWikiLinksInWiki(String url, int n) throws Exception {
        if (url.contains("https://en.wikipedia.org")) {
            driver.get(url);
        } else {
            throw new Exception("Invalid link");
        }
        List<WebElement> aTags = driver.findElements(By.xpath("//a"));
        List<String> links = new ArrayList<String>();
        if (n <= 20) {
            for (int i = 0; i < n; i++) {
                if (!links.contains(aTags.get(i).getAttribute("href"))) {
                    links.add(aTags.get(i).getAttribute("href"));
                }
            }
        } else {
            throw new Exception("Too many links");
        }
        // JSON Section ObjectMapper mapper = new ObjectMapper();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> testMap = new HashMap<String, String>();
        for (String string : links) {
            testMap.put(string, url);
        }
        
        testMap.put("totalCount", Integer.toString(aTags.size()));
        testMap.put("uniqueCount", Integer.toString(links.size()));
        mapper.writeValue(new File(System.getProperty("user.dir") + "\\result.json"), testMap);
    }
}
