package com.mmerhav.remotecontrolserver.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExecutablesManagerITest {

    @Autowired
    private ExecutablesManagerImpl executablesManager;

    @Test
    public void getTeamViewerPathByCommand_Success() {
        String teamViewerPath = executablesManager.getExecutableAbsolutePath("TeamViewer");
        assertEquals("C:/Progra~2/TeamViewer/TeamViewer.exe", teamViewerPath);
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
