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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.LoginPage;
import pageobjects.MainPage;
import pageobjects.ProfilePage;

import static org.junit.Assert.assertTrue;

public class GoToPersonalAccountTest {
    private final UserClient userClient = new UserClient();
    private WebDriver driver;
    private User user;

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
    @DisplayName("Переход в личный кабинет")
    @Description("Проверка перехода по клику на «Личный кабинет»")
    public void goToPersonalAccountTest() {
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\yandexdriver.exe");
        driver = new ChromeDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
        MainPage objMainPage = new MainPage(driver);
        objMainPage.pressOnPersonalAccount();
        LoginPage loginPage = new LoginPage(driver);
        assertTrue("У неавторизованного пользователя должна быть кнопка Войти", loginPage.checkLoginButtonAppeared());

        loginPage.fillEmailPasswordAndPressLoginButton(user.getEmail(), user.getPassword());
        objMainPage.pressOnPersonalAccount();

        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.waitProfileLabelToAppear();
        assertTrue("Надпись профиль должна отображаться у авторизованного пользователя", profilePage.checkProfileLabelAppeared());
    }

}
