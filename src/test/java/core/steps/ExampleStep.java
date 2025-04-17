package core.steps;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import core.page.ExamplePage;
import core.util.Excel;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
 
public class ExampleStep {
    
    private ExamplePage page;

    @Given("ingreso el término de búsqueda {string}")
    public void ingresoElTerminoDeBusqueda(String buscar) {
        page = new ExamplePage();
        page.ingresarBusqueda(buscar);

        List<Map<String, String>> data = Excel.getData("data.csv");
        System.out.println("Colum1: " + data.get(0).get("colum1"));
        System.out.println("Colum2: " + data.get(0).get("colum2"));
    }
    
    @When("presiono el botón de búsqueda")
    public void presionoElBotónDeBúsqueda() {
        page.realizarBusqueda();
    }

    @Then("se muestran los resultados relacionados con {string}")
    public void seMuestranLosResultadosRelacionadosCon(String resultado) {
        String obtenido = page.obtenerTitulo();
        Assert.assertEquals(obtenido, resultado+" at DuckDuckGo");
    }
}
