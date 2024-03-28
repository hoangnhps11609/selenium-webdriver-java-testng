package webdriver;

import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_10_Button_Radio_Checkbox {
    WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Egov_Button() {
        driver.get("https://egov.danang.gov.vn/reg");
        WebElement registerButton = driver.findElement(By.id("button2"));
        //Verify button disable
        Assert.assertFalse(registerButton.isEnabled());

        driver.findElement(By.id("chinhSach")).click();
        toSleep(2);

        //Verify button enable
        Assert.assertTrue(registerButton.isEnabled());

        //Lấy mã màu
        String registerBgRGB = registerButton.getCssValue("background-color");
        //convert từ RGB qua Color
        Color registerBgColor = Color.fromString(registerBgRGB);
        //Convert qua kiểu Hexa
        String registerBgHexa = registerBgColor.asHex();
        Assert.assertEquals(registerBgHexa.toLowerCase(), "#ef5a00");
    }


        @Test
        public void TC_02_Fahasa_Button () {
            driver.get("https://www.fahasa.com/customer/account/create");
            driver.findElement(By.xpath("//a[text()='Đăng nhập']")).click();
            Assert.assertFalse(driver.findElement(By.className("fhs-btn-login")).isEnabled());
            Assert.assertEquals(Color.fromString(driver.findElement(By.className("fhs-btn-login")).getCssValue("background-color")).asHex().toLowerCase(), "#000000");
            driver.findElement(By.id("login_username")).sendKeys("abc@abc.com");
            driver.findElement(By.id("login_password")).sendKeys("123456");
            Assert.assertTrue(driver.findElement(By.className("fhs-btn-login")).isEnabled());
            Assert.assertEquals(Color.fromString(driver.findElement(By.className("fhs-btn-login")).getCssValue("background-color")).asHex().toLowerCase(), "#c92127");
        }

    @Test
    public void TC_02_Gocon_Button () {
        driver.get("https://play.goconsensus.com/u5d5156df");
        toSleep(2);
        driver.findElement(By.xpath("//div[@id='onetrust-close-btn-container']")).click();
        toSleep(2);
        Assert.assertTrue
                (driver.findElement(By.className("src-screens-broadcast-ui-features-selection-ui--continue-OqJQF")).isEnabled());
        driver.findElement(By.id("firstName")).sendKeys("abc");
        driver.findElement(By.id("lastName")).sendKeys("abc");
        driver.findElement(By.id("email")).sendKeys("abc@gmail.com");
        driver.findElement(By.id("phoneNumber")).sendKeys("98798789878");
        driver.findElement(By.id("organization")).sendKeys("abc");
        driver.findElement(By.id("downshift-0-input")).sendKeys("AD");
        driver.findElement(By.id("downshift-90-input")).sendKeys("02");

    }


    public void toSleep (Integer second){
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//    @AfterClass
//    public void afterClass() {
//        driver.quit();
//    }
}