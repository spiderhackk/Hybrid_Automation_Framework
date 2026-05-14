package Tests;

import TestComponents.BaseTest;
import org.example.Pages.DemoAppPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class DemoTest extends BaseTest {
    private final ThreadLocal<DemoAppPage> demoAppPage = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void launchApp(){
        WebDriver driver = setup();
        demoAppPage.set(new DemoAppPage(driver));
    }

    @AfterMethod(alwaysRun = true)
    public void clearPageObject() {
        demoAppPage.remove();
    }

    private DemoAppPage page() {
        return demoAppPage.get();
    }

    @Test( groups = "sanity")
    public void brokenLinksTest(){

        page().brokenLinks();

//        Assert.assertEquals("Demo","Dem");
        }

    @Test(groups = {"regression"})
    public void handleMouseAction() {

        page().handleMouseActions();
    }

    @Test(groups = {"regression"})
    public void handleAlerts(){
        page().handleAlerts();
    }

    @Test(groups = {"sanity", "regression"})
    public void handleBasicFormFields() {
        String formValues = page().fillBasicFormDetails(
                "Shilajit",
                "shilajit@test.com",
                "9876543210",
                "Kolkata, India"
        );

        Assert.assertEquals(formValues, "Shilajit|shilajit@test.com|9876543210|Kolkata, India");
    }

    @Test(groups = {"regression"})
    public void handleGenderRadioButton() {
        Assert.assertTrue(page().selectGender("male"));
    }

    @Test(groups = {"regression"})
    public void handleDayCheckboxes() {
        List<String> selectedDays = page().selectDays(Arrays.asList("Sunday", "Monday", "Friday"));

        Assert.assertTrue(selectedDays.contains("sunday"));
        Assert.assertTrue(selectedDays.contains("monday"));
        Assert.assertTrue(selectedDays.contains("friday"));
    }

    @Test(groups = {"regression"})
    public void handleCountryDropdown() {
        Assert.assertEquals(page().selectCountry("India"), "India");
    }

    @Test(groups = {"regression"})
    public void handleColorsMultiSelectDropdown() {
        List<String> selectedColors = page().selectColors(Arrays.asList("Blue", "Yellow", "White"));

        Assert.assertTrue(selectedColors.contains("Blue"));
        Assert.assertTrue(selectedColors.contains("Yellow"));
        Assert.assertTrue(selectedColors.contains("White"));
    }

    @Test(groups = {"regression"})
    public void handleDateRangePicker() {
        String result = page().selectDateRange("2026-05-01", "2026-05-10");

        Assert.assertEquals(result, "You selected a range of 9 days.");
    }

    @Test(groups = {"regression"})
    public void handleSingleFileUpload() {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/upload-sample.txt";
        String uploadStatus = page().uploadSingleFile(filePath);

        Assert.assertTrue(uploadStatus.contains("Single file selected: upload-sample.txt"));
    }

    @Test(groups = {"regression"})
    public void validateStaticWebTableData() {
        Assert.assertEquals(page().getStaticTablePrice("Learn Java"), "500");
    }

    @Test(groups = {"regression"})
    public void handlePaginationWebTable() {
        Assert.assertTrue(page().selectProductFromPagination("2", "Television"));
    }

    @Test(groups = {"smoke"},dataProvider ="data")
    public void handleRetry(boolean one,boolean two){
        Assert.assertEquals(one,two);
    }


    @DataProvider(name = "data")
    public Object[][] getData(){
        return new Object[][]{{true,false},{true,true}};
    }


}
