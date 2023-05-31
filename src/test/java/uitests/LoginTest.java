package uitests;

import api.clients.UserClient;
import api.user.User;
import api.user.UserGenerator;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.*;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class LoginTest {
    UserClient userClient = new UserClient();
    String loginButtonString;
    Boolean isYandex;
    private WebDriver driver;
    private User user;

    public LoginTest(String loginButtonString, Boolean isYandex) {
        this.loginButtonString = loginButtonString;
        this.isYandex = isYandex;
    }

    @Parameterized.Parameters
    public static Object[][] getOrder() {
        return new Object[][]{
                {Definitions.MAIN_LOGIN_BUTTON.toString(), true},
                {Definitions.MAIN_PERSONAL_ACCOUNT.toString(), false},
                {Definitions.REGISTRATION_LOGIN_BUTTON.toString(), false},
                {Definitions.PASSWORD_LOGIN_BUTTON.toString(), true},
        };
    }

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        user = UserGenerator.getRandom();
        userClient.create(user);
    }

    @After
    public void tearDown() {
        driver.quit();

        ValidatableResponse response = userClient.login(user);
        userClient.delete(response.extract().path("accessToken"));
    }

    @Test
    @DisplayName("Вход пользователя")
    @Description("Проверка логина пользователя из разных страниц")
    public void loginTest() {
        if (isYandex) System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\yandexdriver.exe");
        driver = new ChromeDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
        MainPage objMainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);


        if (loginButtonString.startsWith("MAIN")) {
            objMainPage.Login(loginButtonString);
        } else {
            objMainPage.pressOnPersonalAccount();
            if (loginButtonString.equals(Definitions.REGISTRATION_LOGIN_BUTTON)) {
                loginPage.pressOnRegisterButton();
                RegisterPage registerPage = new RegisterPage(driver);
                registerPage.pressOnLoginButton();
            } else {
                loginPage.pressOnForgotPassword();
                ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
                forgotPasswordPage.pressOnLoginButton();
            }
        }

        loginPage.fillEmailPasswordAndPressLoginButton(user.getEmail(), user.getPassword());
        objMainPage.waitOrderButtonToAppear();
        assertTrue("После успешного логина на главной странице должна появиться кнопка для оформления заказа",
                objMainPage.checkOrderButtonAppeared());

    }
}
