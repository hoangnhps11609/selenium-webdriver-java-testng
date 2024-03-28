package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Topic_09_Custom_Dropdown {
    WebDriver driver;

    //Tường minh: trạng thái cụ thể cho element
    //Visible/Invisible/Precence/Number/Clickable ....
    WebDriverWait explicitWait;

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        //Ngầm định: không rõ ràng chó  trạng tha cụ thể nào cùa element
        //Cho việc tìm element - findElement(s)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
    }

//    @Test
    public void TC_01_JQuery(){
        driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");

        selectItemDropdownCss("#number-button", "ul#number-menu div", "15");
        Assert.assertEquals(driver.findElement(By.cssSelector("#number-button .ui-selectmenu-text")).getText(), "15");
    }

//    @Test
    public void TC_02_React(){
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
        selectItemDropdownCss(".dropdown.icon", "span.text", "Jenny Hess");
        Assert.assertEquals(driver.findElement(By.cssSelector(".divider.text")).getText(), "Jenny Hess");
        selectItemDropdownCss(".dropdown.icon", "span.text", "Stevie Feliciano");
        Assert.assertEquals(driver.findElement(By.cssSelector(".divider.text")).getText(), "Stevie Feliciano");
    }

//    @Test
    public void TC_03_EditTable(){
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
        selectItemDropdownCss("input.search", ".item", "Aland Islands");
        Assert.assertEquals(driver.findElement(By.cssSelector(".divider.text")).getText(), "Aland Islands");
        selectItemDropdownCss("input.search", ".item", "Angola");
        Assert.assertEquals(driver.findElement(By.cssSelector(".divider.text")).getText(), "Angola");
    }


//    @Test
    public void TC_04_Honda(){
        driver.get("https://www.honda.com.vn/o-to/du-toan-chi-phi");
        driver.findElement(By.className("x-cookie")).click();
        selectItemDropdownCss("#selectize-input", "a.dropdown-item", "ACCORD Ghi Bạc/Đen");
        Assert.assertEquals(driver.findElement(By.cssSelector("#selectize-input")).getText(), "ACCORD Ghi Bạc/Đen");
        toSleep(5);
        selectItemDropdownCss(".sl-dt #province", "#province option", "Hà Nội");
        Assert.assertEquals(new Select(driver.findElement(By.cssSelector("#province"))).getFirstSelectedOption().getText(), "Hà Nội");
        toSleep(5);
        selectItemDropdownCss(".sl-dt #registration_fee", "#registration_fee option", "Khu vực I");
        Assert.assertEquals(new Select(driver.findElement(By.cssSelector("#registration_fee"))).getFirstSelectedOption().getText(), "Khu vực I");
    }

    @Test
    public void TC_04_Multiple_Choice_Dropdown(){
        driver.get("http://multiple-select.wenzhixin.net.cn/templates/template.html?v=189&url=basic.html");
        selectItemDropdownXPath("//button[@class='ms-choice']","//div[@class='ms-parent multiple-select ms-parent-open']// li", "January");
        toSleep(3);
        selectItemDropdownXPath("//button[@class='ms-choice']","//div[@class='ms-parent multiple-select ms-parent-open']// li", "January");
    }


    public void selectItemDropdownXPath(String selectParent, String itemChild, String itemExpected){
        driver.findElement(By.xpath(selectParent)).click();
        toSleep(5);
        List<String> listMonth = new ArrayList<>();
        String[] list = { "January", "Fernuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
        listMonth.add(Arrays.toString(list));
                
        List<WebElement> allItems =  explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(itemChild)));
        for (WebElement item: allItems){
            String textItem = item.getText();
            if(textItem.equals(itemExpected)){
                item.click();
                break;
            }
        }
    }




    public void selectItemDropdownCss(String selectParent, String itemChild, String itemExpected){
        // 1 - click vào dropdown
        driver.findElement(By.cssSelector(selectParent)).click();
        toSleep(5);
        // 2.1 hiển thị tất cà item trong dropdown
        // 2.2 hiển thị 1 phần của dropdown
        // chờ nó hiển th hết các item trong dropdown
        //Có case item ko visiable hết tất cả  (Angular/React/...)
        //Xuất hiện đầy đủ trong HTML
        List<WebElement> allItems =  explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(itemChild)));
        //allItems đang lưu trữ là 19
        for (WebElement item: allItems){
            //Nếu trường hợp element click chọn xong rồi các item còn lại sẽ không còn trong HTML nữa thì
            //Hàm getText sẽ bị fail
            String textItem = item.getText();
            //3- Kiểm tra text của từng item và thòa điều kiện thì click chọn
            if(textItem.equals(itemExpected)){
                item.click();
                break;
            }
        }
    }

    public void selectItemEditTableDropdownCss(String selectParent, String itemChild, String itemExpected){
        // 1 - click vào dropdown
        driver.findElement(By.cssSelector(selectParent)).sendKeys(itemExpected);
        toSleep(5);
        // 2.1 hiển thị tất cà item trong dropdown
        // 2.2 hiển thị 1 phần của dropdown
        // chờ nó hiển th hết các item trong dropdown
        //Có case item ko visiable hết tất cả  (Angular/React/...)
        //Xuất hiện đầy đủ trong HTML
        List<WebElement> allItems =  explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(itemChild)));
        //allItems đang lưu trữ là 19
        for (WebElement item: allItems){
            //Nếu trường hợp element click chọn xong rồi các item còn lại sẽ không còn trong HTML nữa thì
            //Hàm getText sẽ bị fail
            String textItem = item.getText();
            //3- Kiểm tra text của từng item và thòa điều kiện thì click chọn
            if(textItem.equals(itemExpected)){
                item.click();
                break;
            }
        }
    }

    public void toSleep(Integer second){
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