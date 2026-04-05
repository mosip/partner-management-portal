package io.mosip.testrig.pmpuiv2.utility;

import org.apache.log4j.Logger;
import org.testng.*;

import io.mosip.testrig.pmpuiv2.fw.util.AdminTestUtil;

import java.util.Map;

public class KnownIssues implements IInvokedMethodListener {

    private static final Logger logger = Logger.getLogger(KnownIssues.class);

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

        if (!method.isTestMethod()) return;

        String methodName = testResult.getMethod().getMethodName();
        String className = testResult.getTestClass().getRealClass().getSimpleName();

        Map<String, String> knownIssues = AdminTestUtil.getKnownIssues(); // change to Map

        for (Map.Entry<String, String> entry : knownIssues.entrySet()) {

            String key = entry.getKey();
            String bugId = entry.getValue();

            if (key.equalsIgnoreCase(className) || key.equalsIgnoreCase(methodName)) {

                logger.warn("Skipping Known Issue: " + className + "." + methodName + " | Bug: " + bugId);

                testResult.setAttribute("KNOWN_ISSUE", bugId);

                throw new SkipException("Skipped due to Known Issue → " + bugId);
            }
        }
    }
}