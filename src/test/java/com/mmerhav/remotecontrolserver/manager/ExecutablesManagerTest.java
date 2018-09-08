package com.mmerhav.remotecontrolserver.manager;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExecutablesManagerTest {

    @Autowired
    private ExecutablesManager executablesManager;

    @Test
    public void getTeamViewerPathByCommand_Success() {
        String teamViewerPath = executablesManager.getExecutableAbsolutePath("TeamViewer");
        assertEquals("C:/Program Files (x86)/TeamViewer/TeamViewer.exe", teamViewerPath);
    }

    @Test
    public void isValidExecutable_Success() {
        boolean isValid = executablesManager.isValidExecutable("TeamViewer");
        assertTrue(isValid);
    }

    @Test
    public void isValidExecutable_Failure() {
        boolean isValid = executablesManager.isValidExecutable("FakeExecName");
        assertFalse(isValid);
    }
}
