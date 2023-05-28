package uitests;

import api.clients.UserClient;
import api.user.User;
import api.user.UserGenerator;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
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

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        user = UserGenerator.getRandom();
    }

    @After
    public void tearDown() {
        driver.quit();
        UserClient userClient = new UserClient();
        ValidatableResponse response = userClient.login(user);
        String token = response.extract().path("accessToken");
        userClient.delete(token);
    }

    @Test
    @DisplayName("Регистрация пользователя")
    @Description("Проверка ошибки для некорректного пароля и умпешной регистрации")
    public void registrationTest() {
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\yandexdriver.exe");
        driver = new ChromeDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
        MainPage objMainPage = new MainPage(driver);
        objMainPage.pressOnPersonalAccount();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.pressOnRegisterButton();

        RegisterPage registerPage = new RegisterPage(driver);

        registerPage.inputAllFieldsAndPressRegistrationButton(user.getName(), user.getEmail(), RandomStringUtils.randomAlphabetic(3));
        assertTrue(registerPage.checkMessageAboutNotSuitablePasswordAppear());
        registerPage.inputPassword(user.getPassword());
        registerPage.pressRegistrationButton();
        loginPage.waitLoginButtonToAppear();
        assertTrue("После успешной регистрации пользователь должен перенаправиться на окно со входом", loginPage.checkLoginButtonAppeared());
    }
}
