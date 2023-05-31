package uitests;

import api.clients.UserClient;
import api.user.User;
import api.user.UserGenerator;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.LoginPage;
import pageobjects.MainPage;
import pageobjects.RegisterPage;

import static org.junit.Assert.assertTrue;

public class RegistrationTest {
    private WebDriver driver;
    private User user;
    private LoginPage loginPage;

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        user = UserGenerator.getRandom();
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\yandexdriver.exe");
        driver = new ChromeDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
    }

    @After
    public void tearDown() {
        driver.quit();
        UserClient userClient = new UserClient();
        ValidatableResponse response = userClient.login(user);
        String token = response.extract().path("accessToken");
        if (token != null) userClient.delete(token);
    }

    @Step("Переход на страницу регистрации")
    public void goToRegistrationTab() {
        MainPage objMainPage = new MainPage(driver);
        objMainPage.pressOnPersonalAccount();

        loginPage = new LoginPage(driver);
        loginPage.pressOnRegisterButton();

    }

    @Test
    @DisplayName("Регистрация пользователя, негативный кейс")
    @Description("Проверка ошибки для некорректного пароля")
    public void registrationNegativeTest() {
        goToRegistrationTab();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.inputAllFieldsAndPressRegistrationButton(user.getName(), user.getEmail(), RandomStringUtils.randomAlphabetic(3));
        assertTrue("Ожидается сообщение о неитьоподходящем пароле", registerPage.checkMessageAboutNotSuitablePasswordAppear());

    }

    @Test
    @DisplayName("Регистрация пользователя")
    @Description("Проверка уcпешной регистрации")
    public void registrationTest() {
        goToRegistrationTab();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.inputAllFieldsAndPressRegistrationButton(user.getName(), user.getEmail(), user.getPassword());
        registerPage.pressRegistrationButton();
        loginPage.waitLoginButtonToAppear();
        assertTrue("После успешной регистрации пользователь должен перенаправиться на окно со входом", loginPage.checkLoginButtonAppeared());
    }
}
