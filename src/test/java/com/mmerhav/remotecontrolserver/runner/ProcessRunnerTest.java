package com.mmerhav.remotecontrolserver.runner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessRunnerTest {

    @Autowired
    private ProcessRunner processRunner;

    @Test
    public void runNotepadExecutable_Success() {
        RunProcessResult result = processRunner.runProcess("C:/Progra~2/TeamViewer/TeamViewer.exe");
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void runNonexistentExecutable_Failure() {
        RunProcessResult result = processRunner.runProcess("C:/Nonexistent/path/nonexistent.exe");
        Assert.assertFalse(result.isSuccess());
        Assert.assertNotNull(result.getErrMsg());
        System.out.println(result.getErrMsg());
    }
}
