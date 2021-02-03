import org.testng.Assert;
import org.testng.annotations.Test;

public class Test4{
    @Test
    void testScreenshot() {
        System.out.println("This method test will create a screenshot on failure");
        Assert.assertEquals(1,2);
    }
}
