package webdriver;

import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_08_Default_Dropdown {
    WebDriver driver;
    Faker faker = new Faker();
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String email = faker.internet().emailAddress();
    String company = faker.company().name();

    String password = faker.internet().password();

    Integer day = faker.number().numberBetween(1, 31);
    Integer month = faker.number().numberBetween(1, 12);
    Integer year = faker.number().numberBetween(1914, 2024);


    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Register() throws InterruptedException {
        driver.get("https://demo.nopcommerce.com/");
        driver.findElement(By.className("ico-register")).click();
        toSleep(2);
        driver.findElement(By.xpath("//label[@for='gender-male']")).click();
        toSleep(2);
        driver.findElement(By.id("FirstName")).sendKeys(firstName);
        driver.findElement(By.id("LastName")).sendKeys(lastName);

        //chọn day
        Select dayOption = new Select(driver.findElement(By.name("DateOfBirthDay")));
        dayOption.selectByVisibleText(day.toString());

        driver.findElement(By.name("DateOfBirthDay")).isEnabled();
        driver.findElement(By.name("DateOfBirthDay")).isSelected();
        driver.findElement(By.name("DateOfBirthDay")).isDisplayed();

        //Verify dropdown là Single
        Assert.assertFalse(dayOption.isMultiple());

        //Verify số lượng option
        Assert.assertEquals(dayOption.getOptions().size(), 32);
        toSleep(2);
        //chọn month year
        new Select(driver.findElement(By.name("DateOfBirthMonth"))).selectByValue(month.toString());
        toSleep(2);
        new Select(driver.findElement(By.name("DateOfBirthYear"))).selectByVisibleText(year.toString());

        //
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Company")).sendKeys(company);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
        driver.findElement(By.id("register-button")).click();
        toSleep(2);
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='result']")).getText(), "Your registration completed");
    }

    @Test
    public void TC_02_Login() throws InterruptedException{
        driver.get("https://demo.nopcommerce.com/");
        toSleep(3);
        driver.findElement(By.className("ico-login")).click();
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.className("login-button")).click();
        toSleep(3);
        driver.findElement(By.className("ico-account")).click();
        toSleep(3);
        Assert.assertTrue(driver.findElement(By.id("gender-male")).isSelected());
        Assert.assertEquals(driver.findElement(By.id("FirstName")).getAttribute("value"), firstName);
        Assert.assertEquals(driver.findElement(By.id("LastName")).getAttribute("value"), lastName);
        Assert.assertEquals(driver.findElement(By.id("Email")).getAttribute("value"), email);
        Assert.assertEquals(driver.findElement(By.id("Company")).getAttribute("value"), company);
        Assert.assertEquals(new Select(driver.findElement(By.name("DateOfBirthDay"))).getFirstSelectedOption().getText(), day.toString());
        Assert.assertEquals(new Select(driver.findElement(By.name("DateOfBirthYear"))).getFirstSelectedOption().getText(), year.toString());
        String monthValue = new Select(driver.findElement(By.name("DateOfBirthMonth"))).getFirstSelectedOption().getText();
        Assert.assertEquals(driver.findElement(By.xpath("//select[@name='DateOfBirthMonth']/option[text()='" + monthValue + "']")).getAttribute("value"), month.toString());
    }



//    @AfterClass
//    public void afterClass() {
//        driver.quit();
//    }

    public void toSleep(Integer time) throws InterruptedException {
        Thread.sleep(time*1000);
    }
}