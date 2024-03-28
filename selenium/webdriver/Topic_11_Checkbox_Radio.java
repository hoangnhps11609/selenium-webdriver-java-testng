package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.xpath.XPath;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Topic_11_Checkbox_Radio {
    WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Default_Telerik_Checkbox() {
        driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
        WebElement rearSideAirbags = driver.findElement(By.xpath("//label[text()='Rear side airbags']/preceding-sibling::span/input"));
        WebElement rainSensor = driver.findElement(By.xpath("//label[text()='Rain sensor']/preceding-sibling::span/input"));
        checkElement(rearSideAirbags);
        checkElement(rainSensor);
        Assert.assertTrue(rearSideAirbags.isSelected());
        Assert.assertTrue(rainSensor.isSelected());
    }

    @Test
    public void TC_02_Default_Telerik_Radio() {
        driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");
        WebElement engine1 = driver.findElement(By.xpath("//input[@id='engine1']"));
        WebElement engine6 = driver.findElement(By.xpath("//input[@id='engine6']"));
        checkElement(engine1);
        Assert.assertTrue(engine1.isSelected());
        Assert.assertFalse(engine6.isSelected());
        checkElement(engine6);
        Assert.assertTrue(engine6.isSelected());
        Assert.assertFalse(engine1.isSelected());
    }

    @Test
    public void TC_03_Select_All_Checkbox() {
        driver.get("https://automationfc.github.io/multiple-fields/");
        List<WebElement> listCheckbox = driver.findElements(By.cssSelector("span.form-checkbox-item input"));
        for (WebElement item:listCheckbox){
//            System.out.println(item.getAttribute("value"));
            checkElement(item);
        }
        toSleep(2);
        for (WebElement item:listCheckbox){
            Assert.assertTrue(item.isSelected());
        }
        toSleep(2);
        for (WebElement item:listCheckbox){
            checkElement(item);
        }

        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        //
        listCheckbox = driver.findElements(By.cssSelector("span.form-checkbox-item input"));
        for (WebElement item:listCheckbox){
            if(item.getAttribute("value").equals("Gallstones") && !item.isSelected()){
                checkElement(item);
            }
        }
        for (WebElement item:listCheckbox){
            if (item.getAttribute("value").equals("Gallstones")){
                Assert.assertTrue(item.isSelected());
            }else{
                Assert.assertFalse(item.isSelected());
            }
        }
    }

    @Test
    public void TC_04_Custom_Radio() {
        driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
        //Case 1:
        //Dùng thẻ input để clcik =>thẻ input bị che bởi 1 element khác =>  failed
        //WebElement click(): the element must be visible, and it must a height and width greater than 0
        //isSelected: only applies to input elements

        //Case 2:
        //Dùng thẻ div bên ngoài để click =>Pass
        //Dùng thẻ div để verify =>Failed
        //driver.findelement(By.xpath("//div[text()= 'Đăng ký cho người thân']/preceding:sibling::div/div[@class='mat-radio-outer-circle']")).click();
        //Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Đăng lý cho người thân']/preceding:sibling::div/div[@class='mat-radio-outer-circle']")).isSelected);

        //Case 3:
        //Dùng thẻ div bên ngoài để click ==> Pass
        //Dùng thẻ input để verify => Pass
        //1 element mà càn define tới 2 locator thì sau này khó maintain, mất nhiều thời gian
        //driver.findElement(By.xpath("//div[text()='Đăng lý cho người thân']/preceding:sibling::div/div[@class='mat-radio-outer-circle']")).click();
        //Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Đăng lý cho người thân']/preceding:sibling::div/input")).isSelected);

        //Case 4
        //Dùng thẻ input để click => JavascriptExecutor(JS)
        //DÙng thẻ input để verify =>isSelected: only applies to input elements
        //Chỉ cần dùng 1 locator
        By registerRadio = By.xpath("//div[text()='Đăng lý cho người thân']/preceding:sibling::div/input");
        ((JavascriptExecutor) driver).executeScript("argments[0].click()", driver.findElement(registerRadio));
        Assert.assertTrue(driver.findElement(registerRadio).isSelected());
    }

    public void checkElement(WebElement webElement){
        if(!webElement.isSelected()){
            webElement.click();
        }
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