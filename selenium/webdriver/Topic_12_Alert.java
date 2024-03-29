package webdriver;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v123.network.Network;
import org.openqa.selenium.devtools.v123.network.model.Headers;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Topic_12_Alert {
    WebDriver driver;
    WebDriverWait explicitWait;
    By result = By.id("result");

    String username = "admin";
    String password = "admin";

    String projectDirectory = System.getProperty("user.dir");

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Accept_Alert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();

        //switch to alert
//        Alert alert = driver.switchTo().alert();

        //switch and wait
        Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());
        toSleep(2);
        Assert.assertEquals(alert.getText(), "I am a JS Alert");
        alert.accept();
        toSleep(2);
        Assert.assertEquals(driver.findElement(result).getText(), "You clicked an alert successfully");
    }

    @Test
    public void TC_02_Confirm_Alert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();

        //switch to alert
//        Alert alert = driver.switchTo().alert();

        //switch and wait
        Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());
        toSleep(2);
        Assert.assertEquals(alert.getText(), "I am a JS Confirm");
        alert.dismiss();
        toSleep(2);
        Assert.assertEquals(driver.findElement(result).getText(), "You clicked: Cancel");
    }
    @Test
    public void TC_03_Prompt_Alert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();

        //switch to alert
//        Alert alert = driver.switchTo().alert();

        //switch and wait
        Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());
        toSleep(2);
        Assert.assertEquals(alert.getText(), "I am a JS prompt");
        String text = "test nha";
        alert.sendKeys(text);
        toSleep(2);
        alert.accept();
        Assert.assertEquals(driver.findElement(result).getText(), "You entered: " + text);
    }

    @Test
    public void TC_04_Authentication_Pass_To_URL() throws IOException {
        //Cách 1: truyền thẳng user /pass cjp URL
//        driver.get("http://" + username + ":" + password + "@" + "the-internet.herokuapp.com/basic_auth");
//        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(), 'Congratulations! You must have the proper credentials.')]")).isDisplayed());

        //Cách 2: từ page A thao tác lên 1 element sẽ chuyển qua page B (cần phải thao tác và Authen Alert trước)
        driver.get("http://the-internet.herokuapp.com");
        String authenURL = driver.findElement(By.xpath("//a[@href='/basic_auth']")).getAttribute("href");
        driver.get(getAuthenURL(authenURL, username, password));
        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(), 'Congratulations! You must have the proper credentials.')]")).isDisplayed());
    }

    @Test
    public void TC_05_Authentication_AutoIT() throws IOException {
        //Cách : chạy trên Windows (AutoIT)
        //Mac/Linux ko sử dụng dc
        //Mỗi browser câần sử dụng script khác nhau
        //thực thi đoạn code AutoIT để chờ Alert xuất hiện
        Runtime.getRuntime().exec(new String[]{ projectDirectory + "\\admin admin" +
                "AutoIT\\authen_firefox.exe", "admin", "admin"});
        driver.get("http://the-internet.herokuapp.com/basic_auth");
        toSleep(5);
        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(), 'Congratulations! You must have the proper credentials.')]")).isDisplayed());
    }

    @Test
    public void TC_06_Authentication_Selenium_4x() {
        //Cách 3
        //thư viện Alert ko sử dụng cho Authentication Alert được
        //Chrome Devtool Protocol(CDP) - Chrome/Edge(Chromium)
        //Cốc cốc / Opera (Chromium) - work around

        //Get Devtool Object
        DevTools devTools = ((HasDevTools) driver).getDevTools();

        // Start new session
        devTools.createSession();

        // Enable the Network domain of devtools
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        // Encode username/ password
        Map<String, Object> headers = new HashMap<String, Object>();
        String basicAuthen = "Basic " + new String(new Base64().encode(String.format("%s:%s", username, password).getBytes()));
        headers.put("Authorization", basicAuthen);

        // Set to Header
        devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));

        driver.get("https://the-internet.herokuapp.com/basic_auth");
        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(), 'Congratulations! You must have the proper credentials.')]")).isDisplayed());
    }

    public String getAuthenURL (String url, String username, String password){
        String[] authenArray = url.split("//");
        return authenArray[0] + "//" + username + ":" + password + "@" + authenArray[1];
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