package TestComponents;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

public class Retry implements IRetryAnalyzer, IAnnotationTransformer {
    int count = 0;
    int maxTry = 2;

    @Override
    public boolean retry(ITestResult result) {
        if(count < maxTry) {

            count++;

            System.out.println("Retrying Test: "
                    + result.getName());

            return true;
        }

        return false;
    }
}
