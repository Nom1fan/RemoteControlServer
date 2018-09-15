package manager;

import com.mmerhav.remotecontrolserver.manager.ExecutablesManagerImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ExecutablesManagerITest {

    private static ExecutablesManagerImpl executablesManager;

    @BeforeClass
    public static void beforeClass() throws IOException {
        executablesManager = new ExecutablesManagerImpl();
        executablesManager.init();
    }

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
