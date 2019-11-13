import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        tags = "@GoogleSearch",
        plugin = {"pretty" ,"html:reports" ,
                "json:reports/cucumber.json" ,
                "junit:reports/cucumber.xml"}
)
public class TestRunner {
}