package core.pages;

import org.openqa.selenium.By;

import core.utils.Action;
import core.utils.Core;

public class ExamplePage extends Action {
    
    By inputBuscar = By.id("searchbox_input");
    By btnBuscar = By.xpath("//button[@aria-label='Search' and @type='submit']");

    public void ingresarBusqueda(String busqueda) {
        sendKeys(this.inputBuscar, busqueda);
    }

    public void realizarBusqueda() {
        click(this.btnBuscar);
        sleep(3);
    }

    public String obtenerTitulo() {
        return Core.getDriver().getTitle();
    }
}
