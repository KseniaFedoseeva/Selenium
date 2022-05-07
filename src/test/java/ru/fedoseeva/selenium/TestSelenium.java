package ru.fedoseeva.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestSelenium {

    private WebDriverWait wait;
    private WebDriver driver;

    @Before
    public void before(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 20, 1000);

        // Переход на страницу "Логин"
        driver.navigate().to("http://training.appline.ru/user/login");
        WebElement titleLoginPage = driver.findElement(By.xpath("//h2[@class='title' and contains(text(), 'Логин')]"));
        Assert.assertTrue("Страничка не загрузилась", titleLoginPage.isDisplayed() && titleLoginPage.getText().contains("Логин"));
    }

    @Test
    public void tests() {


        // Авторизация на странице (логин)
        WebElement login = driver.findElement(By.xpath("//input[@name='_username']"));
        login.click();
        login.sendKeys("Секретарь");
        Assert.assertEquals("Секретарь", "Секретарь");

        // Авторизация на странице (пароль)
        WebElement password = driver.findElement(By.xpath("//input[@name='_password']"));
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
        Assert.assertTrue("Страница не загружена", createBusinessTrip.isDisplayed());
        createBusinessTrip.click();
        WebElement titleCreateBusinessTrip = driver.findElement(By.xpath("//h1[text() = 'Создать командировку']"));
        Assert.assertTrue("Страница не загружена", titleCreateBusinessTrip.isDisplayed() &&
                titleCreateBusinessTrip.getText().contains("Создать командировку"));

        //Заполнение полей
        WebElement choseDepartment = driver.findElement(By.xpath("//option[text() = 'Выберите подразделение']"));
        choseDepartment.click();
        WebElement administrationDepartment = driver.findElement((By.xpath("//option[text() = 'Администрация']")));
        administrationDepartment.click();
        Assert.assertTrue("Не верно подразделение", administrationDepartment.getText().contains("Администрация"));

        WebElement list = driver.findElement(By.xpath("//a[text() = 'Открыть список']"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        list.click();

        WebElement indicateItem = driver.findElement(By.xpath("//span[text() = 'Укажите организацию']"));
        indicateItem.click();

        WebElement choseCompany = driver.findElement((By.xpath("//div[text() = '(Яндекс)Призрачная Организация Охотников']")));
        choseCompany.click();
        String comp = "(Яндекс)Призрачная Организация Охотников";
        WebElement company = driver.findElement(By.name("crm_business_trip[company]"));
        Assert.assertEquals("Не верна организация", comp, company.getAttribute("value"));


        WebElement bookindTicket = driver.findElement(By.xpath("//label[text() = 'Заказ билетов']"));
        bookindTicket.click();

        WebElement leavingCity = driver.findElement(By.name("crm_business_trip[departureCity]"));
        leavingCity.click();
        leavingCity.clear();
        String depCity = "Россия, Пермь";
        leavingCity.sendKeys(depCity);
        Assert.assertEquals("Не верен пункт прибытия", depCity, leavingCity.getAttribute("value"));

        WebElement arrivalCity = driver.findElement(By.name("crm_business_trip[arrivalCity]"));
        arrivalCity.click();
        String arrCity = "Россия, Москва";
        arrivalCity.sendKeys(arrCity);
        Assert.assertEquals("Не верен пункт прибытия", arrCity, arrivalCity.getAttribute("value"));

        WebElement dateDeparture = driver.findElement(By.xpath("//input[starts-with(@name, 'date_selector_crm_business_trip_departureDatePlan') " +
                "and @placeholder = 'Укажите дату']"));

        dateDeparture.click();
        String depDate = "20.05.2022";
        dateDeparture.sendKeys(depDate);
        dateDeparture.submit();
        Assert.assertEquals("Не верна дата отправления", depDate, dateDeparture.getAttribute("value"));

        WebElement dateArrival = driver.findElement(By.xpath("//input[starts-with(@name, 'date_selector_crm_business_trip_returnDatePlan') " +
                "and @placeholder = 'Укажите дату']"));
        String arrDate = "26.05.2022";
        dateArrival.sendKeys(arrDate);
        dateArrival.submit();
        Assert.assertEquals("Не верна дата прибытия", arrDate, dateArrival.getAttribute("value"));

        //Сохранить и закрыть

        WebElement saveAndClose = driver.findElement(By.xpath("//button[@data-action='{\"route\":\"crm_business_trip_index\"}']"));

        saveAndClose.submit();

        // Проверка не заполненных полей
        WebElement listEmploee = driver.findElement(By.xpath("//span[text() = 'Список командируемых сотрудников не может быть пустым']"));
       Assert.assertTrue("Отсутствует сообщение о списке командируемых", listEmploee.isDisplayed());

    }

    @After
    public void after() {
        driver.quit();
    }

}
