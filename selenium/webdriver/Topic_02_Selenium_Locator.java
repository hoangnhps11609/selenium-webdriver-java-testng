package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_02_Selenium_Locator {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://demo.nopcommerce.com/register");
    }

    @Test
    public void TC_01_ID() {
        driver.findElement(By.id("FirstName")).sendKeys("TEST");
    }

    @Test
    public void TC_02_CLASS() {
        driver.findElement(By.className("fieldset"));
    }

    @Test
    public void TC_03_NAME() {
        driver.findElement(By.className("Company"));
    }

    @Test
    public void TC_04_TAGNAME() {
        driver.findElements(By.tagName("input"));
    }

    //Tìm link theo chính xác, tuyệt đối
    @Test
    public void TC_05_LINKTEXT() {
        driver.findElement(By.linkText("Search"));
    }


    //Tìm link theo keyword, tương đối
    @Test
    public void TC_06_PARTIALLINKTEXT() {
        driver.findElement(By.partialLinkText("products"));
    }

    @Test
    public void TC_08_XPath() {
        //CSS vs ID
        driver.findElement(By.xpath("//input[@id='FirstName']"));

        //CSS vs class
        driver.findElement(By.xpath("//span[@class='required']"));

        //Css vs name
        driver.findElement(By.xpath("//input[@name='FirstName']"));

        //Css vs tagName
        driver.findElement(By.xpath("//input"));

        //Css vs link
        driver.findElement(By.xpath("//a[@href='/recentlyviewedproducts']"));
        driver.findElement(By.xpath("//a[text()='Addresses']"));

        //Css vs partial link
        driver.findElement(By.xpath("//a[contains(@href, 'recentlyviewedproducts')]"));
        driver.findElement(By.xpath("//a[contains(text(), 'Recently viewed products')]"));
    }

    @AfterClass
    public void afterClass() {
//        driver.quit();
    }
}