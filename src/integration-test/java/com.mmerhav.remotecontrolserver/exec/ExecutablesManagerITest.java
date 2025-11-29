package com.mmerhav.remotecontrolserver.exec;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
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