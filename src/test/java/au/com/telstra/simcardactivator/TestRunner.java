package au.com.telstra.simcardactivator;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(CucumberTestRunner.class);
        
        System.out.println("Tests run: " + result.getRunCount());
        System.out.println("Failures: " + result.getFailureCount());
        System.out.println("Errors: " + (result.getRunCount() - result.getFailureCount()));
        
        for (Failure failure : result.getFailures()) {
            System.out.println("Failure: " + failure.toString());
        }
        
        System.exit(result.wasSuccessful() ? 0 : 1);
    }
}
