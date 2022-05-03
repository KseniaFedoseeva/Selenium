package ru.fedoseeva.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TestSelenium {

    private WebDriverWait wait;
    private WebDriver driver;


    @Test
    public void tests() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        // Переход на страницу "Логин"
        driver.navigate().to("http://training.appline.ru/user/login");
        WebElement titleLoginPage = driver.findElement(By.xpath("//h2[@class='title' and contains(text(), 'Логин')]"));
        Assert.assertTrue("Страничка не загрузилась", titleLoginPage.isDisplayed() && titleLoginPage.getText().contains("Логин"));

        // Авторизация на странице (логин)
        //    String fieldXPath = "//input[@class='span2']";
        WebElement login = driver.findElement(By.xpath("//input[@name='_username']"));
        //       wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='_username']")));
        login.click();
        login.sendKeys("Секретарь");
        Assert.assertEquals("Секретарь", "Секретарь");
        // wait.until(ExpectedConditions.textToBe(By.xpath("//input[@name='_username']"), "Секретарь"));

        // Авторизация на странице (пароль)
        WebElement password = driver.findElement(By.xpath("//input[@name='_password']"));
        //       wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='_username']")));
        password.click();
        password.sendKeys("testing");
        Assert.assertEquals("testing", "testing");

        WebElement enterButton = driver.findElement(By.xpath("//button[@type='submit']"));
        enterButton.click();

        //Загрузка страницы Training
        WebElement titleTrainig = driver.findElement(By.xpath("//h1[text() = 'Панель быстрого запуска']"));
        Assert.assertTrue("Страница не загрузилась", titleTrainig.isDisplayed() && titleTrainig.getText().
                contains("Панель быстрого запуска"));

        //Выбор раздела "Командировки"
        WebElement costs = driver.findElement(By.xpath("//span[contains(@class, 'title') and text()='Расходы']"));
        costs.click();
        WebElement businessTrip = driver.findElement(By.xpath("//span[(@class = 'title') and text()= 'Командировки']"));
        businessTrip.click();

        //Создание командировки
        WebElement createBusinessTrip = driver.findElement(By.xpath("//a[@title = 'Создать командировку']"));
        createBusinessTrip.click();
        WebElement titleCreateBusinessTrip = driver.findElement(By.xpath("//h1[text() = 'Создать командировку']"));
        Assert.assertTrue("Страница не загружена", titleCreateBusinessTrip.isDisplayed() &&
                titleCreateBusinessTrip.getText().contains("Создать командировку"));

        //Заполнение полей
        WebElement choseDepartment = driver.findElement(By.xpath("//option[text() = 'Выберите подразделение']"));
        choseDepartment.click();
        WebElement administrationDepartment = driver.findElement((By.xpath("//option[text() = 'Администрация']")));
        administrationDepartment.click();

       WebElement list = driver.findElement(By.xpath("//a[text() = 'Открыть список']"));
       try{
           Thread.sleep(3000);
       }
        catch (InterruptedException e){
           e.printStackTrace();
        }
       list.click();


        //       driver.quit();

    }


    private void scrollToElementJs(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void waitUtilElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void fillFields(WebElement element, String value) {

        scrollToElementJs(element);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitUtilElementToBeClickable(element);
        element.click();
        element.sendKeys(value);

        boolean checkFlag = wait.until(ExpectedConditions.attributeContains(element, "placeholder", value));
        Assert.assertTrue("Поле было заполнено некорректно", checkFlag);

    }
}
