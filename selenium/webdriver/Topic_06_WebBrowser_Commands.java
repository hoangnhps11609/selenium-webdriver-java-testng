package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.lang.model.element.Element;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.time.Duration.*;

public class Topic_06_WebBrowser_Commands {
    WebDriver driver;
    WebElement element;

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();

        //Selenium ver 2/3
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //Selenium ver 4
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        driver.manage().window().maximize();
        driver.get("https://www.facebook.com/");
    }

    @Test
    public void TC_01() throws MalformedURLException {
        //Mở URL
        driver.get("https://www.facebook.com/");

        //Nếu có 1 tab/window thì chức năng tương tự quit
        //nếu nhiều hơn 1, thì đóng tab/window hiện tại
        driver.close();

        //Đóng tất cả browser
        driver.quit();

        // 2 hàm chịu ảnh hưởng timeout của inplicitWait
        //findElement, findElements

        //Tìm Element
        //Nếu không tìm thấy -> Fail : NoSuchElementException
        //Trả về 1 Element, nếu tìm thấy nhiều hơn -> trả về cái đầu tiên
        WebElement email = driver.findElement(By.id("email"));

        //Tìm List Element, nếu ko tìm thấy trả List rỗng
        List<WebElement> listInput = driver.findElements(By.tagName("input"));

        //Dùng để lấy URL hiện tại
        driver.getCurrentUrl();

        //Lấy pageSource HTML/CSS/JS của page hiện tại
        //Verify tương đối
        driver.getPageSource();

        //Lấy Title của page hiện tại
        driver.getTitle();

        //Lấy ID cùa tab/window hiện tại
        driver.getWindowHandle();
        driver.getWindowHandles();

        //Cookies
        driver.manage().getCookies();

        //Get log ở DEV TOOL
        driver.manage().logs().get(LogType.DRIVER);

        //Apply cho việc tìm Element
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        //Chờ page load xong
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        //Set trước khi dùng với thư viện JavaScriptExecutor
        //Inject 1 đoạn code JS vào trong Browser/Element
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

        //Selenium 4 mới có
        driver.manage().timeouts().getImplicitWaitTimeout();
        driver.manage().timeouts().getPageLoadTimeout();
        driver.manage().timeouts().getScriptTimeout();

        //Chạy full màn hình
        driver.manage().window().maximize();
        driver.manage().window().fullscreen();
        driver.manage().window().minimize();

        //Test GUI
        //TEst Responsive (Resolution)
        driver.manage().window().setSize(new Dimension(1366, 1600));
        driver.manage().window().getSize();

        //Set cho browser ở vị trí nào so vói độ phân giải màn hình
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().getPosition();

        //Điều hướng trang web
        driver.navigate().back();
        driver.navigate().forward();
        driver.navigate().refresh();

        //Thao tác với History của web page
        driver.navigate().to("https://www.facebook.com/\"");
        driver.navigate().to(new URL("https://www.facebook.com/"));

        //Alert/Window(Tab)/Frame(iFrame)
        driver.switchTo().alert().accept();
        driver.switchTo().alert().dismiss();
        driver.switchTo().alert().getText();
        driver.switchTo().alert().sendKeys("Test");

        //Lấy ra ID của window/Tab hiện tại
        String windowID = driver.getWindowHandle();
        driver.switchTo().window(windowID);

        //Switch/handle frame (iframe)
        //Index /ID / Element
        driver.switchTo().frame(0);
        driver.switchTo().frame("12323");
        driver.switchTo().frame(driver.findElement(By.id("")));

        //Switch về HTMl chứ frame trước đó
        driver.switchTo().defaultContent();

        //Từ frame trong di ta frame ngoài chứa nó
        driver.switchTo().parentFrame();
    }



    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}