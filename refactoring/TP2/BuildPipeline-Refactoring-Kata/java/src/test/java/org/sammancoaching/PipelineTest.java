package org.sammancoaching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sammancoaching.dependencies.Config;
import org.sammancoaching.dependencies.DeploymentEnvironment;
import org.sammancoaching.dependencies.Emailer;
import org.sammancoaching.dependencies.Logger;
import org.sammancoaching.dependencies.Project;
import org.sammancoaching.dependencies.TestStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PipelineTest {

    private Config config;
    private Emailer emailer;
    private Logger logger;
    private CapturingLogger capturingLogger;
    private Pipeline pipeline;

    @BeforeEach
    void setUp() {
        config = mock(Config.class);
        emailer = mock(Emailer.class);
        capturingLogger = new CapturingLogger();
        logger = capturingLogger;
        pipeline = new Pipeline(config, emailer, logger);
    }

    @Test
    void shouldRunTestsAndDeploySuccessfully() {
        Project project = Project.builder()
                .setTestStatus(TestStatus.PASSING_TESTS)
                .setDeploysSuccessfully(true)
                .build();
        when(config.sendEmailSummary()).thenReturn(true);

        pipeline.run(project);

        verify(emailer).send("Deployment completed successfully");
    }

    @Test
    void shouldSendFailureEmailWhenTestsFail() {
        Project project = Project.builder()
                .setTestStatus(TestStatus.FAILING_TESTS)
                .setDeploysSuccessfully(true)
                .build();
        when(config.sendEmailSummary()).thenReturn(true);

        pipeline.run(project);

        verify(emailer).send("Tests failed");
    }

    @Test
    void shouldSendFailureEmailWhenDeploymentFails() {
        Project project = Project.builder()
                .setTestStatus(TestStatus.PASSING_TESTS)
                .setDeploysSuccessfully(false)
                .build();
        when(config.sendEmailSummary()).thenReturn(true);

        pipeline.run(project);

        verify(emailer).send("Deployment failed");
    }

    @Test
    void shouldNotSendEmailWhenEmailIsDisabled() {
        Project project = Project.builder()
                .setTestStatus(TestStatus.PASSING_TESTS)
                .setDeploysSuccessfully(true)
                .build();
        when(config.sendEmailSummary()).thenReturn(false);

        pipeline.run(project);

        verify(emailer, never()).send(any());
    }

    @Test
    void shouldLogCorrectMessagesWhenTestsPass() {
        Project project = Project.builder()
                .setTestStatus(TestStatus.PASSING_TESTS)
                .setDeploysSuccessfully(true)
                .build();
        when(config.sendEmailSummary()).thenReturn(false);

        pipeline.run(project);

        assertTrue(capturingLogger.getLoggedLines().contains("INFO: Tests passed"));
        assertTrue(capturingLogger.getLoggedLines().contains("INFO: Deployment successful"));
    }

    @Test
    void shouldLogCorrectMessagesWhenTestsFail() {
        Project project = Project.builder()
                .setTestStatus(TestStatus.FAILING_TESTS)
                .build();
        when(config.sendEmailSummary()).thenReturn(false);

        pipeline.run(project);

        assertTrue(capturingLogger.getLoggedLines().contains("ERROR: Tests failed"));
    }

    @Test
    void shouldLogNoTestsWhenProjectHasNoTests() {
        Project project = Project.builder()
                .setTestStatus(TestStatus.NO_TESTS)
                .setDeploysSuccessfully(true)
                .build();
        when(config.sendEmailSummary()).thenReturn(false);

        pipeline.run(project);

        assertTrue(capturingLogger.getLoggedLines().contains("INFO: No tests"));
    }

    @Test
    void shouldLogCorrectMessageWhenDeploymentFails() {
        Project project = Project.builder()
                .setTestStatus(TestStatus.PASSING_TESTS)
                .setDeploysSuccessfully(false)
                .build();
        when(config.sendEmailSummary()).thenReturn(false);

        pipeline.run(project);

        assertTrue(capturingLogger.getLoggedLines().contains("ERROR: Deployment failed"));
    }
}
