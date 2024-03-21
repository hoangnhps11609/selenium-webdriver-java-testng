package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import net.datafaker.Faker;
import javax.lang.model.element.Element;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Ex_06_WebBrowser_WebElement_Commands {
    WebDriver driver;
    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://live.techpanda.org/");
    }

    @Test
    public void TC_01_VerifyUrl() {
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");
        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");
    }

    @Test
    public void TC_02_VerifyTitle() {
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        Assert.assertEquals(driver.getTitle(), "Customer Login");
        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
        Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
    }

    @Test
    public void TC_02_NavigateFunction() {
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");
        driver.navigate().back();
        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");
        driver.navigate().forward();
        Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
    }

    @Test
    public void TC_04_GetPageSourceCode() {
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Login or Create an Account"));
        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Create an Account"));
    }

    @Test
    public void TC_01_Element_IsDisplayed(){
        driver.get("https://automationfc.github.io/basic-form/index.html");
        WebElement email = driver.findElement(By.xpath("//label[@for='email']"));
        WebElement age = driver.findElement(By.xpath("//label[@for='over_18']"));
        WebElement education = driver.findElement(By.xpath("//label[@for='edu']"));
        if (email.isDisplayed() == true && age.isDisplayed()==true && education.isDisplayed()==true){
            driver.findElement(By.id("mail")).sendKeys("Taoday@gmail.com");
            driver.findElement(By.id("edu")).sendKeys("fpt");
            driver.findElement(By.xpath("//input[@id='radio-disabled']")).isSelected();
        }
        Assert.assertEquals(driver.findElement(By.id("mail")).getText(), "Taoday@gmail.com");
        Assert.assertEquals(driver.findElement(By.id("edu")).getText(), "fpt");
        Assert.assertTrue(driver.findElement(By.xpath("//input[@id='radio-disabled']")).isEnabled());
    }

    @Test
    public void TC_02_Element_IsEnable(){
        driver.get("https://automationfc.github.io/basic-form/index.html");
        Assert.assertTrue(driver.findElement(By.id("mail")).isEnabled());
        Assert.assertTrue(driver.findElement(By.id("under_18")).isEnabled());
        Assert.assertTrue(driver.findElement(By.id("edu")).isEnabled());
        Assert.assertTrue(driver.findElement(By.id("job1")).isEnabled());
        Assert.assertTrue(driver.findElement(By.id("job2")).isEnabled());
        Assert.assertTrue(driver.findElement(By.id("development")).isEnabled());
        Assert.assertTrue(driver.findElement(By.id("slider-1")).isEnabled());
        Assert.assertFalse(driver.findElement(By.id("disable_password")).isEnabled());
        Assert.assertFalse(driver.findElement(By.id("radio-disabled")).isEnabled());
        Assert.assertFalse(driver.findElement(By.id("bio")).isEnabled());
        Assert.assertFalse(driver.findElement(By.id("job3")).isEnabled());
        Assert.assertFalse(driver.findElement(By.id("check-disbaled")).isEnabled());
        Assert.assertFalse(driver.findElement(By.id("slider-2")).isEnabled());
    }

    @Test
    public void TC_03_Element_isSelected(){
        driver.get("https://automationfc.github.io/basic-form/index.html");
        driver.findElement(By.id("under_18")).click();
        driver.findElement(By.id("java")).click();
        Assert.assertTrue(driver.findElement(By.id("under_18")).isSelected());
        Assert.assertTrue(driver.findElement(By.id("java")).isSelected());
        driver.findElement(By.id("java")).click();
        Assert.assertFalse(driver.findElement(By.id("java")).isSelected());
    }

    @Test
    public void TC_04_Element_Register_Function_At_MailChimp(){
        driver.get("https://login.mailchimp.com/signup/");
        driver.findElement(By.id("email")).sendKeys("Taoday@gmail.com");
        driver.findElement(By.id("new_password")).sendKeys("1");
        driver.findElement(By.id("new_password")).sendKeys("1a");
        driver.findElement(By.id("new_password")).sendKeys("A");
        driver.findElement(By.id("new_password")).sendKeys("@");
        driver.findElement(By.id("new_password")).sendKeys("123");
    }

    @Test
    public void TC_01_Login_with_empty_Email_Password(){
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        driver.findElement(By.id("send2")).click();
        Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(), "This is a required field.");
        Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(), "This is a required field.");
    }

    @Test
    public void TC_02_Login_with_Invalid_Email(){
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        driver.findElement(By.id("email")).sendKeys("123123@123213");
        driver.findElement(By.id("pass")).sendKeys("123123");
        driver.findElement(By.id("send2")).click();
        Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(), "Please enter a valid email address. For example johndoe@domain.com.");
    }

    @Test
    public void TC_03_Login_with_Invalid_Password(){
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        driver.findElement(By.id("email")).sendKeys("123123@gmail.com");
        driver.findElement(By.id("pass")).sendKeys("3123");
        driver.findElement(By.id("send2")).click();
        Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(), "Please enter 6 or more characters without leading or trailing spaces.");
    }

    @Test
    public void TC_04_Login_with_Incorrect_Email_Password(){
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        driver.findElement(By.id("email")).sendKeys("123123@gmail.com");
        driver.findElement(By.id("pass")).sendKeys("312223");
        driver.findElement(By.id("send2")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//span[text()='Invalid login or password.']")).getText(), "Invalid login or password.");
    }

    @Test
    public void TC_05_Login_Success() throws InterruptedException {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String firstName = faker.name().firstName();
        String middleName = faker.name().nameWithMiddle();
        String lastName = faker.name().lastName();
        String password = faker.internet().password();
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
        driver.findElement(By.id("firstname")).sendKeys(firstName);
        driver.findElement(By.id("middlename")).sendKeys(middleName);
        driver.findElement(By.id("lastname")).sendKeys(lastName);
        driver.findElement(By.id("email_address")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("confirmation")).sendKeys(password);
        driver.findElement(By.id("is_subscribed")).click();
        Thread.sleep(10000);
        driver.findElement(By.xpath("//button[@title='Register']//span[text()='Register']")).click();
        String successMes = driver.findElement(By.cssSelector(".success-msg span")).getText();
        if(successMes.equalsIgnoreCase("Thank you for registering with Main Website Store.")){
            driver.findElement(By.cssSelector("a.skip-account")).click();
            driver.findElement(By.xpath("//a[@title='Log Out']")).click();
            driver.findElement(By.cssSelector("a.skip-account")).click();
            driver.findElement(By.xpath("//a[@title='Log In']")).click();
            driver.findElement(By.id("email")).sendKeys(email);
            driver.findElement(By.id("pass")).sendKeys(password);
            driver.findElement(By.id("send2")).click();
            Assert.assertEquals(driver.findElement(By.cssSelector("p.hello strong")).getText(), "Hello, " + firstName + " " + middleName + " " + lastName + "!");
        }else{
            System.out.println("Register Fail");
        }

    }



//    @AfterClass
//    public void afterClass() {
//        driver.quit();
//    }
}