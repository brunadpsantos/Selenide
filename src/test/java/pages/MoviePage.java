package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import models.MovieModel;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.io.File;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class MoviePage {

    public MoviePage add() {
        $(".movie-add").click();
        return this;
    }

    public MoviePage search(String value){
        $("input[placeholder^=Pesquisar]").setValue(value);
        $("#search-movie").click();
        return this;
    }

    public MoviePage create(MovieModel movie) {
        $("input[name=title]").setValue(movie.title);
        this.selectStatus(movie.status);
        $("input[name=year]").setValue(movie.year.toString());
        $("input[name=release_date]").setValue(movie.releaseDate);
        this.inputCast(movie.cast);
        $("textarea[name=overview]").setValue(movie.plot);
        this.upload(movie.cover);
        $("#create-movie").click();

        return this;
    }

    public ElementsCollection items() {
        //dois dolares tras uma lista de elemento.
        return $$("table tbody tr");
    }

    private void upload(File cover) {
//        Código para caso o elemento que faz o upload da foto esteja oculto (Forma de executar código de javaScript.
//        String jsScript = "document.getElementById('upcover').classList.remove('el-upload_input');";
//        executeJavaScript(jsScript);

        $("#upcover").uploadFile(cover);
    }

    private void inputCast(List<String> cast)   {
        SelenideElement element = $(".cast");

        for(String actor : cast) {
            element.setValue(actor);
            element.sendKeys(Keys.TAB);
        }
    }

    private void selectStatus(String status) {
        $("input[placeholder=Status]").click();
        //colocar $$ para trazer tudo que contem no span no caso trará todas as opçoes de li da lista.
        $$("ul li span").findBy(text(status)).click();
    }
}
