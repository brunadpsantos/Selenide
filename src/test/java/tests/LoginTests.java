package tests;

import common.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;

public class LoginTests extends BaseTest {

    @DataProvider(name = "login-alerts")
    public Object[][] loginProvider() {
        return new Object[][]{
                {"papito@ninjaplus.com", "abc123", "Usuário e/ou senha inválidos"},
                {"404@gmail.com", "abc123", "Usuário e/ou senha inválidos"},
                {"", "abc123", "Opps. Cadê o email?"},
                {"papito@ninjaplus.com", "", "Opps. Cadê a senha?"}
        };
    }

    @Test
    public void shouldSeeLoggerdUser() {
        //o código pode ser assim:
        //login.open();
        //login.with("p apito@ninjaplus.com", "pws123");

        //ou assim:
        login
                .open()
                .with("papito@ninjaplus.com", "pwd123");
        side.loggedUser().shouldHave(text("Papito"));

        //Exemplos:
        //$("input[name=email]").setValue("papito@ninjaplus.com");
        //$("input[type=password]").setValue("pwd123");
        //$("#passId").setValue("pwd123");
        //$(byText("Entrar")).click();
        //$(".user .info span").shouldHave(text("Papito"));
    }

    // DDT (Data Driver Testing)
    @Test(dataProvider = "login-alerts")
    public void shouldSeeLoginAlerts(String email, String pass, String expectAlert) {
        //isChrome();
        //executeJavaScript("localStorage.clear();");
        //open("http://ninjaplus-web:5000/login");

        login
                .open()
                .with(email, pass)
                .alert().shouldHave(text(expectAlert));

        //$("input[name=email]").setValue(email);
        //$("#passId").setValue(pass);
        //$(byText("Entrar")).click();
        //$(".alert span").shouldHave(text(expectAlert));
    }

    //O AfterMethod executa sempre ao final de cada metodo.
    @AfterMethod
    public void cleanup() {
        login.clearSession();
    }
}
