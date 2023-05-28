package uitests;

import api.clients.UserClient;
import api.user.User;
import api.user.UserGenerator;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utilities.LocalStorage;
import pageobjects.LoginPage;
import pageobjects.MainPage;
import pageobjects.ProfilePage;

import static org.junit.Assert.assertTrue;

public class LogoutTest {
    private final UserClient userClient = new UserClient();
    private WebDriver driver;
    private String accessToken;
    private String refreshToken;


    private User user;

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        user = UserGenerator.getRandom();
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        refreshToken = response.extract().path("refreshToken");
    }

    @After
    public void tearDown() {
        driver.quit();
        ValidatableResponse response = userClient.login(user);
        userClient.delete(response.extract().path("accessToken"));
    }

    @Test
    @DisplayName("Выход из личного кабинета")
    @Description("Проверка выхода из личного кабинета")
    public void logoutTest() {
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\yandexdriver.exe");
        driver = new ChromeDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
        authorization();
        MainPage objMainPage = new MainPage(driver);
        objMainPage.pressOnPersonalAccount();
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.waitProfileLabelToAppear();
        profilePage.clickOnExitButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitLoginButtonToAppear();
        assertTrue("Пользователь должен перенаправиться на окно со входом", loginPage.checkLoginButtonAppeared());
    }

    @Step("Добавлениие информации об авторизованном пользователе в локал стораж")
    public void authorization() {
        LocalStorage localStorage = new LocalStorage(driver);
        localStorage.setItemInLocalStorage("accessToken", accessToken);
        localStorage.setItemInLocalStorage("refreshToken", refreshToken);
    }
}
