package common;

import com.codeborne.selenide.Configuration;
import net.bytebuddy.matcher.ElementMatchers;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import pages.MoviePage;
import pages.SideBar;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.Buffer;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.screenshot;

public class BaseTest {

    protected static LoginPage login;
    protected static SideBar side;
    protected static MoviePage movie;

    //o BeforeMethod é executado antes de todos os métodos de testes
    @BeforeMethod
    public void start() {

        Properties prop = new Properties();
        InputStream inputFile = getClass().getClassLoader().getResourceAsStream("config.properties");
        try {
            prop.load(inputFile);
        } catch (Exception ex){
            System.out.println("Deu ruim ao carregar o config.properties. Trace =>" + ex.getMessage());
        }

        Configuration.browser = prop.getProperty("browser");
        Configuration.baseUrl = prop.getProperty("url");
        //configuração para definir quanto tempo ele tem que aguardar ate encontrar um elemento. (aqui esta para 30 segundos ou 30000 milisegundos)
        Configuration.timeout = Long.parseLong(prop.getProperty("timeout"));

        login = new LoginPage();
        side = new SideBar();
        movie = new MoviePage();
    }

    @AfterMethod
    public void finish(){
        // Tiramos um screenshot pelo selenide
      String tempShot = screenshot("temp_shot");

      // Queremos transformar em binário para anexar no report do allure
      try {
         BufferedImage bimage = ImageIO.read(new File(tempShot));
         ByteArrayOutputStream baos = new ByteArrayOutputStream();

         ImageIO.write(bimage, "png", baos);
         byte [] finalShot = baos.toByteArray();

         io.qameta.allure.Allure.addAttachment("Evidencia", new ByteArrayInputStream(finalShot));

      } catch (Exception ex){
          System.out.println(ex.getMessage());
          System.out.println("Deu erro ao anexar o Screenshot :(. Trace => " + ex.getMessage());
      }

    }
}
