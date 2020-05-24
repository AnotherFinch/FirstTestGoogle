import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class FirstTestGoogle {

    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void startUp(){
        System.setProperty("webdriver.chrome.driver", "src/test/webdriver/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 20);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @Test
    public void firstTest() throws InterruptedException {

        //1. Перейти по ссылке http://www.rgs.ru
        String baseUrl = "http://www.rgs.ru";
        driver.get(baseUrl);

        //2. Выбрать Меню
        String linkMainNavbar = "//*[@id='main-navbar-collapse']/ol[1]";
        WebElement mainNavbar = driver.findElement(By.xpath(linkMainNavbar));
        mainNavbar.click();

        //3. Выбрать категорию - ДМС
        String findDms = "//*[@id='rgs-main-menu-insurance-dropdown']/div[1]/div/div/div[2]/div[2]/ul/li[2]";
        WebElement dms = driver.findElement(By.xpath(findDms));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(findDms)));
        dms.click();

        //4. Проверить наличие заголовка - Добровольное медицинское страхование
        Thread.sleep(2000);
        String findRequest = "//*[contains(text(),'добровольное медицинское страхование')]";
        Assert.assertEquals("Запрашиваемый текст отсутсвует", "ДМС — добровольное медицинское страхование",
                driver.findElement(By.xpath(findRequest)).getText());

        //5. Нажать на кнопку - Отправить заявку
        String request = "//*[@id='rgs-main-context-bar']/div/div/div/div/a[3]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(request)));
        WebElement requesButton = driver.findElement(By.xpath(request));
        requesButton.click();

        //6. Проверить, что открылась страница , на которой присутствует текст - Заявка на добровольное медицинское страхование
        Thread.sleep(2000);
        String findRequest2 = "//*[contains(text(),'Заявка на добровольное медицинское страхование')]";
        Assert.assertEquals("Запрашиваемый текст отсутсвует", "Заявка на добровольное медицинское страхование",
                driver.findElement(By.xpath(findRequest2)).getText());

        //7.Заполнить поля
        Thread.sleep(2000);
        String lastName = "//*[@id='applicationForm']/div[2]/div[1]/input";
        WebElement elementLastName = driver.findElement(By.xpath(lastName));
        elementLastName.sendKeys("Соколов");

        String firstName = "//*[@id='applicationForm']/div[2]/div[2]/input";
        WebElement elementFirstName = driver.findElement(By.xpath(firstName));
        elementFirstName.sendKeys("Никита");

        String middleName = "//*[@id='applicationForm']/div[2]/div[3]/input";
        WebElement elementMiddleName = driver.findElement(By.xpath(middleName));
        elementMiddleName.sendKeys("Владимирович");

        String regions = "//*[@id='applicationForm']/div[2]/div[4]/select";
        WebElement regionsType = driver.findElement(By.xpath(regions));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(regions)));
        regionsType.click();
        Thread.sleep(2000);

        String moscow = "//*[@id='applicationForm']/div[2]/div[4]/select/option[2]";
        WebElement moscowType = driver.findElement(By.xpath(moscow));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(moscow)));
        moscowType.click();

        String phoneNumber = "//*[@id='applicationForm']/div[2]/div[5]/input";
        WebElement elementPhoneNumber = driver.findElement(By.xpath(phoneNumber));
        elementPhoneNumber.click();
        Thread.sleep(2000);
        elementPhoneNumber.sendKeys("9629709921");

        String email = "//*[@id='applicationForm']/div[2]/div[6]/input";
        WebElement elementEmail = driver.findElement(By.xpath(email));
        elementEmail.sendKeys("qwertyqwerty");

        String checkBox = "//*[@id='applicationForm']/div[2]/div[9]/label/input";
        WebElement elementCheckBox = driver.findElement(By.xpath(checkBox));
        elementCheckBox.click();

        String contactDate = "//*[@id='applicationForm']/div[2]/div[7]/input";
        WebElement elementContactDate = driver.findElement(By.xpath(contactDate));
        elementContactDate.click();
        Thread.sleep(2000);
        elementContactDate.sendKeys("29.05.2020");

        String comments = "//*[@id='applicationForm']/div[2]/div[8]/textarea";
        WebElement elementComments = driver.findElement(By.xpath(comments));
        elementComments.sendKeys("Убедительная просьба звонить после 18 00");

        // Проверить поля
        Assert.assertEquals("Не указана фамилия", "Соколов", elementLastName.getAttribute("value"));
        Assert.assertEquals("Не указано имя", "Никита", elementFirstName.getAttribute("value"));
        Assert.assertEquals("Не указано отчество", "Владимирович", elementMiddleName.getAttribute("value"));
        Assert.assertEquals("Не указан E-mail", "qwertyqwerty", elementEmail.getAttribute("value"));
        Assert.assertEquals("Не указана контактная дата ", "29.05.2020", elementContactDate.getAttribute("value"));
        Assert.assertEquals("Не указан телефон", "+7 (962) 970-99-21", elementPhoneNumber.getAttribute("value"));
        Assert.assertEquals("Не указан регион", "77", regionsType.getAttribute("value"));

        //кнопка
        String buttonSend = "//*[@id='button-m']";
        WebElement elementButtonSend = driver.findElement(By.xpath(buttonSend));
        elementButtonSend.click();

        //проверка сообщения об ошибки в эл почте
        String emailError = "//*[@id='applicationForm']/div[2]/div[6]/div/label/span";
        Assert.assertEquals("Поле 'Email' не содержит сообщение об ошибке",
                "Введите адрес электронной почты",driver.findElement(By.xpath(emailError)).getText());

    }

        @After
        public void end() throws InterruptedException {
        Thread.sleep(20000);
        driver.quit();
        }

    }

