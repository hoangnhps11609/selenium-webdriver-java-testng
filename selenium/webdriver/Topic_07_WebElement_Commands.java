package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Topic_07_WebElement_Commands {
    WebDriver driver;
    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.facebook.com/");
    }

    @Test
    public void TC_01() {
        //Xóa dữ liệu trong input
        driver.findElement(By.id("")).clear();

        //Dùng để nhập dữ liệu input
        driver.findElement(By.id("")).sendKeys("");

        //Dùng để clcik element
        driver.findElement(By.id("")).click();

        //Tìm node con từ node cha
        driver.findElement(By.id("")).findElement(By.id(""));

        //Tạo biến cho element
        WebElement element = driver.findElement(By.id(""));

        //Tão List biến cho element
        List<WebElement> list = driver.findElements(By.cssSelector(""));

        //Dùng để verify 1 checkbox/radio/dropdowm (Đã dc chọn chưa)
        Assert.assertTrue(driver.findElement(By.id("")).isSelected());
        Assert.assertFalse(driver.findElement(By.id("")).isSelected());

        //Dropdown (default/custom)
        Select select = new Select(driver.findElement(By.id("")));

        //Dùng để verify element có hiển thị ko
        Assert.assertTrue(driver.findElement(By.id("")).isDisplayed());
        Assert.assertFalse(driver.findElement(By.id("")).isDisplayed());

        //Dùng để verify element có dc thao tác lên hay ko
        Assert.assertTrue(driver.findElement(By.id("")).isEnabled());
        Assert.assertFalse(driver.findElement(By.id("")).isEnabled());

        //HTML Element
        driver.findElement(By.id("")).getAttribute("class");

        //Tab Accesibility/Properties trong Element
        driver.findElement(By.id("")).getAccessibleName();
        driver.findElement(By.id("")).getDomAttribute("checker");
        driver.findElement(By.id("")).getDomProperty("baseURL");
        driver.findElement(By.id("")).getDomProperty("outerURL");

        //Tab Styles trong Elements (GUI)
        driver.findElement(By.id("")).getCssValue("font-size");

        //Vị trí của element so vs màn hình
        Point location = driver.findElement(By.id("")).getLocation();
        location.getX();
        location.getY();

        //Chiều cao - rộng
        Dimension dimension = driver.findElement(By.id("")).getSize();

        //Location + size
        Rectangle rectangle = driver.findElement(By.id("")).getRect();

        //Location
        Point point = rectangle.getPoint();

        //Size
        Dimension size = rectangle.getDimension();
        size.getHeight();
        size.getWidth();

        //Shadow Element (JavascriptExecutor)
        driver.findElement(By.id("")).getShadowRoot();

        //Từ id/class/name/css/xpath có thể truy ra ngượ lại tagnam HTMl
        driver.findElement(By.id("")).getTagName();

        //Element A -> đầu ra của nó là tagneme của element A
        String formListtag = driver.findElement(By.id("")).getTagName();

        //get text
        driver.findElement(By.id("")).getText();

        //Chụp hình lỗi và lưu lại dưới dạng: Byte, file, base64
        driver.findElement(By.id("")).getScreenshotAs(OutputType.BASE64);
        driver.findElement(By.id("")).getScreenshotAs(OutputType.BYTES);
        driver.findElement(By.id("")).getScreenshotAs(OutputType.FILE);

        //Form (element nào là thẻ form hoặc nằm trong thẻ form)
        //Hành vi giống phím Enter
        driver.findElement(By.id("")).submit();

    }



    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}