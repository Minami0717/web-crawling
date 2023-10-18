package com.minami.webcrawling;

import com.minami.webcrawling.gameInfo.model.Game;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class CrawlingExample {
    private WebDriver driver;

    private static final String url = "https://search.naver.com/search.naver?where=nexearch&sm=tab_etc&mra=bkJB&pkid=3001&qvt=0&query=모바일게임랭킹";
    public List<Game> crawling() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/quarpronuet/Downloads/chromedriver-win64/chromedriver.exe");
        //크롬 드라이버 셋팅 (드라이버 설치한 경로 입력)

        //driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");       //팝업안띄움
        options.addArguments("headless");                       //브라우저 안띄움
        options.addArguments("--disable-gpu");			//gpu 비활성화
        options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음
        driver = new ChromeDriver(options);

        List<Game> gameList = getGameList();

        //driver.close();	//탭 닫기
        driver.quit();	//브라우저 닫기

        return gameList;
    }


    /**
     * data가져오기
     */
    private List<Game> getGameList() {
        List<Game> gameList = new ArrayList<>();

        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));	//⭐⭐⭐
        //드라이버가 실행된 뒤 최대 10초 기다리겠다.

        driver.get(url);    //브라우저에서 url로 이동한다.
        //Thread.sleep(1000); //브라우저 로딩될때까지 잠시 기다린다.

        String selector = ".list_info";

        //WebElement sentence = driver.findElement(By.cssSelector("#sentence-example-list .sentence-list li"));
        //System.out.println(sentence.getText());
        //この先腕を磨いていけば、いつかはこの男に勝てる日がくるのだろうか。 ...
        //ベニー松山『風よ。龍に届いているか(下)』
        // findElement (끝에 s없음) 는 해당되는 선택자의 첫번째 요소만 가져온다

        for (int i = 0; i < 20; i++) {
            webDriverWait.until(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector))
                    //cssSelector로 선택한 부분이 존재할때까지 기다려라
            );

            WebElement element = driver.findElements(By.cssSelector(selector)).get(i);
            List<WebElement> elements = element.findElements(By.cssSelector(".info_box"));

            for (WebElement e : elements) {
                String name = e.findElement(By.cssSelector(".title")).getText();
                List<WebElement> cateInfo = e.findElements(By.cssSelector(".cate_info .text"));
                Game g = Game.builder()
                        .name(name)
                        .genre(cateInfo.get(0).getText())
                        .company(cateInfo.get(1).getText())
                        .build();

                gameList.add(g);
            }

            WebElement next = driver.findElement(By.cssSelector(".pg_next"));
            next.click();
        }

        return gameList;
    }
}
