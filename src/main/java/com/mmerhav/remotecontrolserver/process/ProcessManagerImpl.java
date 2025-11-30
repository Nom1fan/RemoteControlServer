package com.mmerhav.remotecontrolserver.process;

import com.mmerhav.remotecontrolserver.exec.ExecutablesManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Supplier;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProcessManagerImpl implements ProcessManager {

    private static final String ATTEMPT_TO_STOP_RETURNED_STR_ERR_MSG_TEMPLATE = "Attempt to stop process: [%s]. Returned: %s";
    private static final int KILL_PROCESS_RETRY_ATTEMPTS = 3;
    private static final long KILL_PROCESS_RETRY_INTERVAL_MS = 1000;

    @Autowired
    private final ExecutablesManager executablesManager;

    @Override
    public Result runProcess(String executableName) {
        if (isInvalidExecName(executableName)) {
            String errMsg = "Remote executable not found: [%s]. Make sure the remote executable name is defined on the server.".formatted(executableName);
            log.warn(errMsg);
            return new Result(SC_BAD_REQUEST, errMsg);
        }
        String executableAbsolutePath = executablesManager.getExecutableAbsolutePath(executableName);
        File file = new File(executableAbsolutePath);
        if (!file.exists()) {
            String errMsg = "Cannot run process. Executable: [%s] does not exist".formatted(executableName);
            log.warn(errMsg);
            return new Result(SC_BAD_REQUEST, errMsg);
        }
        return runProcess(executableName, file);
    }

    @Override
    public Result stopProcess(String executableName) {
        try {
            return stopProcessInternal(executableName);
        } catch (IOException e) {
            String errMsg = ATTEMPT_TO_STOP_RETURNED_STR_ERR_MSG_TEMPLATE.formatted(executableName, e.getMessage());
            log.error(errMsg, e);
            return new Result(SC_INTERNAL_SERVER_ERROR, errMsg);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            String errMsg = ATTEMPT_TO_STOP_RETURNED_STR_ERR_MSG_TEMPLATE.formatted(executableName, e.getMessage());
            log.error(errMsg, e);
            return new Result(SC_INTERNAL_SERVER_ERROR, errMsg);
        }
    }

    private static Result runProcess(String execName, File file) {
        String dirPath = file.getParent();
        File dir = new File(dirPath);
        try {
            Runtime.getRuntime().exec(new String[]{execName}, null, dir);
            String msg = "Process [%s] has been successfully started".formatted(execName);
            log.info(msg);
            return new Result(SC_OK, msg);
        } catch (IOException e) {
            log.error("Failed to run process: [%s]".formatted(execName), e);
            return new Result(SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private Result stopProcessInternal(String executableName) throws IOException, InterruptedException {
        String stdError = retry(executableName, () -> {
            Process process = kill(executableName);
            return getStdError(process);
        });
        if (!stdError.isEmpty()) {
            String msg = ATTEMPT_TO_STOP_RETURNED_STR_ERR_MSG_TEMPLATE.formatted(executableName, stdError);
            log.info(msg);
            return new Result(SC_OK, msg);
        }
        return new Result(SC_OK, "Successfully stopped process: [%s]".formatted(executableName));
    }

    public static String retry(String executableName, Supplier<String> action) {
        Exception lastException = null;
        for (int i = 0; i < KILL_PROCESS_RETRY_ATTEMPTS; i++) {
            try {
                log.info("Trying to kill process: {}. Attempt #{} of {}", executableName, i + 1, KILL_PROCESS_RETRY_ATTEMPTS);
                return action.get();
            } catch (Exception e) {
                lastException = e;
                sleepAfterAttempt(i);
            }
        }
        log.error("Failed to stop process: {} after {} retries", executableName, KILL_PROCESS_RETRY_ATTEMPTS, lastException);
        throw new RuntimeException(lastException);
    }

    private static void sleepAfterAttempt(int i) {
        if (i < KILL_PROCESS_RETRY_ATTEMPTS - 1) {
            try {
                Thread.sleep(KILL_PROCESS_RETRY_INTERVAL_MS);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted during retry", ie);
            }
        }
    }

    public static String retryProcessKill(String executableName) throws IOException, InterruptedException {
        final String SUCCESS = "";
        String stdError = SUCCESS;
        for (int i = 0; i < KILL_PROCESS_RETRY_ATTEMPTS; i++) {
            log.info("Trying to kill process: {}. Attempt #{} of {}", executableName, i + 1, KILL_PROCESS_RETRY_ATTEMPTS);
            Process process = kill(executableName);
            stdError = getStdError(process);
            if (stdError.isEmpty()) {
                return SUCCESS;
            }
            if (i < KILL_PROCESS_RETRY_ATTEMPTS - 1) {
                Thread.sleep(KILL_PROCESS_RETRY_INTERVAL_MS);
            }
        }
        return stdError;
    }

    // Needs admin rights
    @SneakyThrows
    private static Process kill(String executableName) {
        return Runtime.getRuntime().exec(new String[]{"taskkill", "/f" ,"/im", "%s*".formatted(executableName), "/T"});
    }

    private boolean isInvalidExecName(String execName) {
        return !executablesManager.isValidExecutable(execName);
    }

    private static String getStdError(Process process) {
        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        StringBuilder stdErrorBuilder = new StringBuilder();
        String s;
        try {
            while ((s = stdError.readLine()) != null) {
                stdErrorBuilder.append(s);
            }
        } catch (IOException e) {
            String errMsg = "Failed to fetch std error when killing process. Error message: %s".formatted(e.getMessage());
            log.error(errMsg, e);
            return errMsg;
        }
        return stdErrorBuilder.toString();
    }
}