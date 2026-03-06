package org.sammancoaching;

import org.sammancoaching.dependencies.BuildResult;
import org.sammancoaching.dependencies.Config;
import org.sammancoaching.dependencies.Emailer;
import org.sammancoaching.dependencies.Logger;
import org.sammancoaching.dependencies.Project;

public class Pipeline {
    private final Config config;
    private final Emailer emailer;
    private final Logger log;

    public Pipeline(Config config, Emailer emailer, Logger log) {
        this.config = config;
        this.emailer = emailer;
        this.log = log;
    }

    public void run(Project project) {
        boolean testsPassed = executeTests(project);
        boolean deploySuccessful = deployIfTestsPass(project, testsPassed);
        sendSummaryEmailIfEnabled(testsPassed, deploySuccessful);
    }

    private boolean executeTests(Project project) {
        if (project.hasTests()) {
            String testResult = project.runTests();
            if (BuildResult.SUCCESS.equals(testResult)) {
                log.info(BuildResult.MESSAGE_TESTS_PASSED);
                return true;
            } else {
                log.error(BuildResult.MESSAGE_TESTS_FAILED);
                return false;
            }
        } else {
            log.info(BuildResult.MESSAGE_NO_TESTS);
            return true;
        }
    }

    private boolean deployIfTestsPass(Project project, boolean testsPassed) {
        if (testsPassed) {
            String deploymentResult = project.deploy();
            if (BuildResult.SUCCESS.equals(deploymentResult)) {
                log.info(BuildResult.MESSAGE_DEPLOYMENT_SUCCESSFUL);
                return true;
            } else {
                log.error(BuildResult.MESSAGE_DEPLOYMENT_FAILED);
                return false;
            }
        }
        return false;
    }

    private void sendSummaryEmailIfEnabled(boolean testsPassed, boolean deploySuccessful) {
        if (config.sendEmailSummary()) {
            log.info(BuildResult.MESSAGE_SENDING_EMAIL);
            String emailMessage = determineEmailMessage(testsPassed, deploySuccessful);
            emailer.send(emailMessage);
        } else {
            log.info(BuildResult.MESSAGE_EMAIL_DISABLED);
        }
    }

    private String determineEmailMessage(boolean testsPassed, boolean deploySuccessful) {
        if (!testsPassed) {
            return BuildResult.EMAIL_TESTS_FAILED;
        }
        if (deploySuccessful) {
            return BuildResult.EMAIL_DEPLOYMENT_COMPLETED;
        }
        return BuildResult.EMAIL_DEPLOYMENT_FAILED;
    }
}
