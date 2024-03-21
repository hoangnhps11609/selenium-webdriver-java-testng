package webdriver;

import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Ex_07_08_Textbox_TextArea_Dropdown_CustomDropdown {
    WebDriver driver;
    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Textbox_TextArea() throws InterruptedException {
        driver.get("http://live.techpanda.org/");
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
            Assert.assertTrue(driver.getPageSource().contains(firstName + " " + middleName + " " + lastName));
            Assert.assertTrue(driver.getPageSource().contains(email));
        }else{
            System.out.println("Register Fail");
        }
    }

    @Test
    public void TC_02_Textbox_TextArea() throws InterruptedException {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
//        String usernameElement = driver.findElement(By.xpath("//div[@class='orangehrm-login-error']//p[1]")).getText();
//        String passwordElement = driver.findElement(By.xpath("//div[@class='orangehrm-login-error']//p[2]")).getText();
//        Integer usernamePosition = ("Username: ").length();
//        Integer passwordPosition = ("Password: ").length();
//        String username = usernameElement.substring(usernamePosition + 1);
//        String password = usernameElement.substring(passwordPosition + 1);
        //
        driver.findElement(By.cssSelector(".oxd-input[name='username']")).sendKeys("Admin");
        driver.findElement(By.cssSelector(".oxd-input[name='password']")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        toSleep(3);
        //
        driver.findElement(By.xpath("//span[text()='PIM']")).click();
        toSleep(3);
        driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
        toSleep(3);
        //
        WebElement empIdElement = driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input"));
        String IDemp = empIdElement.getAttribute("value");
        System.out.println(IDemp);
        //
        Faker fake = new Faker();
        String firstNameEmp = fake.name().firstName();
        String lastNameEmp = fake.name().lastName();
        String usernameEmp = fake.internet().username();
        String passwordEmp = fake.internet().password();
        //
        driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys(firstNameEmp);
        driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys(lastNameEmp);
        driver.findElement(By.xpath("//input[@type='checkbox']/parent::label")).click();
        toSleep(3);
        //
        driver.findElement(By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input")).sendKeys(usernameEmp);
        driver.findElement(By.xpath("//label[text()='Password']/parent::div/following-sibling::div/input")).sendKeys(passwordEmp);
        driver.findElement(By.xpath("//label[text()='Confirm Password']/parent::div/following-sibling::div/input")).sendKeys(passwordEmp);
        //
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        toSleep(3);
        //
        Assert.assertEquals(driver.findElement(By.xpath("//input[@name='firstName']")).getAttribute("value"), firstNameEmp);
        Assert.assertEquals(driver.findElement(By.xpath("//input[@name='lastName']")).getAttribute("value"), lastNameEmp);
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getAttribute("value"), IDemp);


        //Step 8
        driver.findElement(By.xpath("//a[text()='Immigration']")).click();
        toSleep(3);

        //Step 9
        driver.findElement(By.xpath("//h6[text()='Assigned Immigration Records']/following-sibling::button")).click();
        toSleep(3);

        //Step 10
        Random random = new Random();
        String number = fake.number().digits(14);
        String textarea = fake.name().nameWithMiddle();
        driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).sendKeys(number);
        driver.findElement(By.xpath("//textarea")).sendKeys(textarea);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        toSleep(3);

        //Step 11
        driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();
        toSleep(3);

        //Step 12
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).getAttribute("value"), number);
        Assert.assertEquals(driver.findElement(By.xpath("//textarea")).getAttribute("value"), textarea);

        //Step 14
        driver.findElement(By.cssSelector(".oxd-userdropdown-tab")).click();
        toSleep(3);
        driver.findElement(By.xpath("//a[text()='Logout']")).click();
        toSleep(3);

        //Step 15
        driver.findElement(By.cssSelector(".oxd-input[name='username']")).sendKeys(usernameEmp);
        driver.findElement(By.cssSelector(".oxd-input[name='password']")).sendKeys(passwordEmp);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        toSleep(3);

        //Step 16
        driver.findElement(By.xpath("//span[text()='My Info']")).click();


        //Step 17
        System.out.println(driver.findElement(By.xpath("//input[@name='firstName']")).getAttribute("value"));
        Assert.assertEquals(driver.findElement(By.xpath("//input[@name='firstName']")).getAttribute("value"), firstNameEmp);
        Assert.assertEquals(driver.findElement(By.xpath("//input[@name='lastName']")).getAttribute("value"), lastNameEmp);
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getAttribute("value"), IDemp);

        //Step 18
        driver.findElement(By.xpath("//a[text()='Immigration']")).click();
        toSleep(3);
        List<WebElement> listIconPencil = driver.findElements(By.className("bi-pencil-fill"));
        WebElement iconPencil1 = listIconPencil.get(0);
        iconPencil1.click();
        toSleep(3);

        //Step 19
        String numberStep19 = driver.findElement(By.xpath("//label[contains(string(), 'Number')]/parent::div/following-sibling::div/input")).getAttribute("value");
        Assert.assertEquals(numberStep19, number);
        Assert.assertEquals(driver.findElement(By.tagName("textarea")).getAttribute("value"), textarea);
    }


    public void toSleep(Integer time) throws InterruptedException {
        Thread.sleep(time*1000);
    }


//    @AfterClass
//    public void afterClass() {
//        driver.quit();
//    }
}