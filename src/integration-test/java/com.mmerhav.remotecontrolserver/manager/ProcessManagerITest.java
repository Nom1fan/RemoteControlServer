package com.mmerhav.remotecontrolserver.manager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessManagerITest {

    @Autowired
    private ProcessManager processManager;

    @Test
    public void runNotepadExecutable_Success() {
        RunProcessResult result = processManager.runProcess("C:/WINDOWS/system32/notepad.exe");
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void stopNotePadExecutable_Success() {
        StopProcessResult result = processManager.stopProcess("notepad");
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void runNonexistentExecutable_Failure() {
        RunProcessResult result = processManager.runProcess("C:/Nonexistent/path/nonexistent.exe");
        Assert.assertFalse(result.isSuccess());
        Assert.assertNotNull(result.getErrMsg());
        System.out.println(result.getErrMsg());
    }
}
