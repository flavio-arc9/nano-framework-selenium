package core.utils;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features="src/test/java/resources/features/",
        glue={"core/util","core/steps"},
        plugin = {"html:target/cucumber-reports/cucumber.html"},
        publish = false,
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
@org.testng.annotations.Test
public class Test extends AbstractTestNGCucumberTests {}
