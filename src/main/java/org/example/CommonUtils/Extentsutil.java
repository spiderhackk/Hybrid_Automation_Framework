package org.example.CommonUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Extentsutil {


    public static ExtentReports getReport(){
        String path = System.getProperty("user.dir")+"/reports/report.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Execution Results");
        reporter.config().setDocumentTitle("Test Results");

        ExtentReports reports = new ExtentReports();
        reports.attachReporter(reporter);
        reports.setSystemInfo("Engineer","Shilajit");

        return  reports;
    }
}
