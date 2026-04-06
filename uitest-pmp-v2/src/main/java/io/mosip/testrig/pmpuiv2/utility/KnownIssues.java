package io.mosip.testrig.pmpuiv2.utility;

import org.apache.log4j.Logger;
import org.testng.*;

import io.mosip.testrig.pmpuiv2.fw.util.AdminTestUtil;

import java.util.Map;

public class KnownIssues implements IInvokedMethodListener {

	private static final Logger logger = Logger.getLogger(KnownIssues.class);

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

		if (!method.isTestMethod())
			return;

		String methodName = testResult.getMethod().getMethodName();
		String className = testResult.getTestClass().getRealClass().getSimpleName();
		Map<String, String> knownIssues = AdminTestUtil.getKnownIssues();
		if (knownIssues == null || knownIssues.isEmpty())
			return;
		String bugId = knownIssues.get(methodName);
		if (bugId == null) {
			bugId = knownIssues.get(className);
		}

		if (bugId != null) {
			logger.warn("Skipping Known Issue: " + className + "." + methodName + " | Bug: " + bugId);
			testResult.setAttribute("KNOWN_ISSUE", bugId);
			throw new SkipException("Skipped due to Known Issue → " + bugId);
		}
	}
}