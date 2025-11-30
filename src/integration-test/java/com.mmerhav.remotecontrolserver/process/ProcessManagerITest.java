package com.mmerhav.remotecontrolserver.process;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProcessManagerITest {

    @Autowired
    private ProcessManager processManager;

    @Test
    public void runNotepadExecutable_Success() throws IOException {
        startNotepad();
    }

    private void startNotepad() throws IOException {
        Result result = processManager.runProcess("NOTEPAD");
        Assertions.assertEquals(SC_OK, result.statusCode());
    }

    @Test
    public void stopNotePadExecutable_Success() throws IOException {
        startNotepad();
        Result result = processManager.stopProcess("NOTEPAD");
        Assertions.assertEquals(SC_OK, result.statusCode());
    }

    @Test
    public void runNonexistentExecutable_Failure() throws IOException {
        Result result = processManager.runProcess("NonExistent");
        Assertions.assertEquals(SC_BAD_REQUEST, result.statusCode());
        Assertions.assertNotNull(result.msg());
    }
}