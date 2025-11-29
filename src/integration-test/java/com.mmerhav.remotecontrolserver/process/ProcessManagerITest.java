package com.mmerhav.remotecontrolserver.process;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProcessManagerITest {

    @Autowired
    private ProcessManager processManager;

    @Test
    public void runNotepadExecutable_Success() {
        RunProcessResult result = processManager.runProcess("C:/WINDOWS/system32/notepad.exe");
        Assertions.assertTrue(result.isSuccess());
    }

    @Test
    public void stopNotePadExecutable_Success() {
        StopProcessResult result = processManager.stopProcess("notepad");
        Assertions.assertTrue(result.isSuccess());
    }

    @Test
    public void runNonexistentExecutable_Failure() {
        RunProcessResult result = processManager.runProcess("C:/Nonexistent/path/nonexistent.exe");
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNotNull(result.getErrMsg());
        System.out.println(result.getErrMsg());
    }
}