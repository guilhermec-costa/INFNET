package org.sammancoaching.dependencies;

public final class BuildResult {
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    public static final String MESSAGE_TESTS_PASSED = "Tests passed";
    public static final String MESSAGE_TESTS_FAILED = "Tests failed";
    public static final String MESSAGE_NO_TESTS = "No tests";
    public static final String MESSAGE_DEPLOYMENT_SUCCESSFUL = "Deployment successful";
    public static final String MESSAGE_DEPLOYMENT_FAILED = "Deployment failed";
    public static final String MESSAGE_EMAIL_DISABLED = "Email disabled";
    public static final String MESSAGE_SENDING_EMAIL = "Sending email";

    public static final String EMAIL_DEPLOYMENT_COMPLETED = "Deployment completed successfully";
    public static final String EMAIL_DEPLOYMENT_FAILED = "Deployment failed";
    public static final String EMAIL_TESTS_FAILED = "Tests failed";

    private BuildResult() {
    }
}
