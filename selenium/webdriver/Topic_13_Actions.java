package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


///QUAN TRỌNG SỬ DỤNG NHIỀU NHẤT LÀ HOVER, CÒN LẠI HỌC CHƠI CHO BIẾT THÔI NHA

public class Topic_13_Actions {
    WebDriver driver;
    Actions actions;

    JavascriptExecutor javascriptExecutor;

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        actions = new Actions(driver);
        javascriptExecutor = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Hover_Tooltip() {
        driver.get("https://automationfc.github.io/jquery-tooltip/");
        WebElement inputBox = driver.findElement(By.id("age"));
        actions.moveToElement(inputBox).perform();
        toSleep(3);
        //chạy debugger trong web
        // setTimeout(() => {debugger;}, 5000);
        Assert.assertEquals(driver.findElement(By.className("ui-tooltip-content")).getText(), "We ask for your age only for statistical purposes.");
    }


    @Test
    public void TC_02_Hover_Menu_Fahasa() {
        driver.get("https://www.fahasa.com/");
        actions.moveToElement(driver.findElement(By.className("icon_menu"))).perform();
        toSleep(2);
        actions.moveToElement(driver.findElement(By.xpath("//a[@title= 'Bách Hóa Online - Lưu Niệm']"))).perform();
        toSleep(2);
        driver.findElement(By.xpath("//div[contains(@class, 'fhs_menu_content')]//a[text()='Thiết Bị Số - Phụ Kiện Số']")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("ol.breadcrumb strong")).getText(), "THIẾT BỊ SỐ - PHỤ KIỆN SỐ");
        Assert.assertTrue(driver.findElement(By.xpath("//strong[contains(text(), 'Thiết Bị Số - Phụ Kiện Số')]")).isDisplayed());
    }

    @Test
    public void TC_03_Click_and_Hold() {
        driver.get("https://automationfc.github.io/jquery-selectable/");
        List<WebElement> listNumber = driver.findElements(By.className("ui-state-default"));
        Assert.assertEquals(listNumber.size(), 20);
        actions.clickAndHold(listNumber.get(0))//Click chuột trái lên 1
                .pause(2000)
                .moveToElement(listNumber.get(14)) //di chuyển đến 20
                .pause(2000)
                .release() // nhả chuột
                .perform(); //execute tất cả các action
        toSleep(3);
        List<WebElement> listNumberSelected = driver.findElements(By.className("ui-selected"));
        Assert.assertEquals(listNumberSelected.size(), 12);
        //so sánh nội dung từng element
        String[] allNumberExpected = {"1", "2", "3", "5", "6", "7", "9", "10", "11", "13", "14", "15"};
        List<String> allNumberActual = new ArrayList<String>();
        for (WebElement item:listNumberSelected){
            allNumberActual.add(item.getText());
        }
        Assert.assertEquals(Arrays.asList(allNumberExpected), allNumberActual);
    }

    @Test
    public void TC_04_Click_and_Hold() {
        driver.get("https://automationfc.github.io/jquery-selectable/");
        //Tạo key theo hệ điều hành
        Keys keys = Platform.getCurrent().is(Platform.MAC) ? Keys.COMMAND : Keys.CONTROL;
        //Tổng element
        List<WebElement> listNumber = driver.findElements(By.className("ui-state-default"));
        Assert.assertEquals(listNumber.size(), 20);
        //Chọn từ 1 - 12
        actions.clickAndHold(listNumber.get(0))//Click chuột trái lên 1
                .pause(2000)
                .moveToElement(listNumber.get(11)) //di chuyển đến 20
                .pause(2000)
                .release() // nhả chuột
                .perform(); //execute tất cả các action
        toSleep(3);
        // chọn 13 14 15
        actions.keyDown(keys).perform();//nhấn phím CTRL xuống
        actions.click(listNumber.get(12))
                .click(listNumber.get(13))
                .click(listNumber.get(14))
                .keyUp(keys).perform();
        toSleep(3);
        List<WebElement> listNumberSelected = driver.findElements(By.className("ui-selected"));
        Assert.assertEquals(listNumberSelected.size(), 15);
        //so sánh nội dung từng element
        String[] allNumberExpected = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
        List<String> allNumberActual = new ArrayList<String>();
        for (WebElement item:listNumberSelected){
            allNumberActual.add(item.getText());
        }
        Assert.assertEquals(Arrays.asList(allNumberExpected), allNumberActual);
    }

    @Test
    public void TC_05_Click_and_Hold_Custom_Item() {
        driver.get("https://automationfc.github.io/jquery-selectable/");
        //Tạo key theo hệ điều hành
        Keys keys = Platform.getCurrent().is(Platform.MAC) ? Keys.COMMAND : Keys.CONTROL;
        //Tổng element
        List<WebElement> listNumber = driver.findElements(By.className("ui-state-default"));
        Assert.assertEquals(listNumber.size(), 20);
        // chọn 13 14 15
        actions.keyDown(keys).perform();//nhấn phím CTRL xuống
        actions.click(listNumber.get(0))
                .click(listNumber.get(2))
                .click(listNumber.get(5))
                .click(listNumber.get(10))
                .keyUp(keys).perform();
        toSleep(3);
        List<WebElement> listNumberSelected = driver.findElements(By.className("ui-selected"));
        Assert.assertEquals(listNumberSelected.size(), 4);
        //so sánh nội dung từng element
        String[] allNumberExpected = {"1", "3", "6", "11"};
        List<String> allNumberActual = new ArrayList<String>();
        for (WebElement item:listNumberSelected){
            allNumberActual.add(item.getText());
        }
        Assert.assertEquals(Arrays.asList(allNumberExpected), allNumberActual);
    }


    @Test
    public void TC_06_DoubleClick() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        WebElement doubleClickButton = driver.findElement(By.xpath("//button[text()= 'Double click me']"));
        //Cần scroll tới element rôồi mới double CLick, chỉ riêng Firefox và Salary  mới cần scroll
        if(driver.toString().contains("firefox")){
            //scrollIntoView(true): kéo mép trên cảu element lên trên cùng của viewport
            //scrollIntoView(false): kéo mép dưới cảu element dưới cùng cùng của viewport
            javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", doubleClickButton);
            toSleep(3);
        }else {
            actions.scrollToElement(doubleClickButton).perform();
            toSleep(3);
        }
        actions.doubleClick(doubleClickButton).perform();
        toSleep(3);
        Assert.assertEquals(driver.findElement(By.cssSelector("p#demo")).getText(), "Hello Automation Guys!");
    }


    @Test
    public void TC_07_RightClick() {
        driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
        //Kiêểm tra khi chưa click vào button
        Assert.assertFalse(driver.findElement(By.cssSelector("li.context-menu-icon-paste")).isDisplayed());
        actions.contextClick(driver.findElement(By.cssSelector("span.context-menu-one"))).perform();
        toSleep(2);
        //Đã Click chuột phải vào các element được visible
        Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-paste")).isDisplayed());
        actions.moveToElement(driver.findElement(By.cssSelector("li.context-menu-icon-paste"))).perform();
        toSleep(2);

        //Được cập nhật lại class của element này - kiểm tra sự kiện hover thành công
        Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-paste.context-menu-hover.context-menu-visible")).isDisplayed());

        //Click lên Paste
        actions.click(driver.findElement(By.cssSelector("li.context-menu-icon-paste"))).perform();
        toSleep(3);

        driver.switchTo().alert().accept();
        toSleep(2);

        //Kiểm tra Paste ko còn hiển thị
        Assert.assertFalse(driver.findElement(By.cssSelector("li.context-menu-icon-paste")).isDisplayed());
    }

    @Test
    public void TC_07_DragDropHTML4() {
        driver.get("https://automationfc.github.io/kendo-drag-drop/");
        WebElement smallCircle = driver.findElement(By.id("draggable"));
        WebElement bigCircle = driver.findElement(By.id("droptarget"));
        //Cách 1
//        actions.dragAndDrop(smallCircle, bigCircle).perform();

        //Cách 2
        actions.clickAndHold(smallCircle).moveToElement(bigCircle).release().perform();
        Assert.assertEquals(bigCircle.getText(), "You did great!");
        Assert.assertEquals(Color.fromString(bigCircle.getCssValue("background-color")).asHex().toLowerCase(), "#03a9f4");
    }

    @Test
    public void TC_08_DragDropHTML5_Css()  throws IOException {
        driver.get("https://automationfc.github.io/drag-drop-html5/");

        String columnA = "div#column-a";
        String columnB = "div#column-b";

        String projectPath = System.getProperty("user.dir");

        String dragAndDropFilePath = projectPath + "/dragAndDrop/drag_and_drop_helper.js";

        String jsContentFile = getContentFile(dragAndDropFilePath);

        jsContentFile = jsContentFile + "$('" + columnA + "').simulateDragDrop({ dropTarget: '" + columnB + "'});";

        // A -> B
        javascriptExecutor.executeScript(jsContentFile);
        toSleep(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("div#column-a>header")).getText(), "B");
        Assert.assertEquals(driver.findElement(By.cssSelector("div#column-b>header")).getText(), "A");

        // B -> A
        javascriptExecutor.executeScript(jsContentFile);
        toSleep(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("div#column-a>header")).getText(), "A");
        Assert.assertEquals(driver.findElement(By.cssSelector("div#column-b>header")).getText(), "B");

    }

    @Test
    public void TC_09_DragDropHTML5_XPath() throws AWTException {
        driver.get("https://automationfc.github.io/drag-drop-html5/");

        dragAndDropHTML5ByXpath("//div[@id='column-a']", "//div[@id='column-b']");
        toSleep(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("div#column-a>header")).getText(), "B");
        Assert.assertEquals(driver.findElement(By.cssSelector("div#column-b>header")).getText(), "A");

        dragAndDropHTML5ByXpath("//div[@id='column-a']", "//div[@id='column-b']");
        toSleep(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("div#column-a>header")).getText(), "A");
        Assert.assertEquals(driver.findElement(By.cssSelector("div#column-b>header")).getText(), "B");
    }

    public void dragAndDropHTML5ByXpath(String sourceLocator, String targetLocator) throws AWTException {

        WebElement source = driver.findElement(By.xpath(sourceLocator));
        WebElement target = driver.findElement(By.xpath(targetLocator));

        // Setup robot
        Robot robot = new Robot();
        robot.setAutoDelay(500);

        // Get size of elements
        Dimension sourceSize = source.getSize();
        Dimension targetSize = target.getSize();

        // Get center distance
        int xCentreSource = sourceSize.width / 2;
        int yCentreSource = sourceSize.height / 2;
        int xCentreTarget = targetSize.width / 2;
        int yCentreTarget = targetSize.height / 2;

        Point sourceLocation = source.getLocation();
        Point targetLocation = target.getLocation();

        // Make Mouse coordinate center of element
        sourceLocation.x += 20 + xCentreSource;
        sourceLocation.y += 110 + yCentreSource;
        targetLocation.x += 20 + xCentreTarget;
        targetLocation.y += 110 + yCentreTarget;

        // Move mouse to drag from location
        robot.mouseMove(sourceLocation.x, sourceLocation.y);

        // Click and drag
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x, ((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

        // Move to final position
        robot.mouseMove(targetLocation.x, targetLocation.y);

        // Drop
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public String getContentFile(String filePath) throws IOException {
        Charset cs = Charset.forName("UTF-8");
        FileInputStream stream = new FileInputStream(filePath);
        try {
            Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        } finally {
            stream.close();
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