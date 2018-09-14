package logic;

import com.mmerhav.remotecontrolserver.logic.RemoteControlLogicImpl;
import com.mmerhav.remotecontrolserver.manager.ExecutablesManager;
import com.mmerhav.remotecontrolserver.manager.ExecutablesManagerImpl;
import com.mmerhav.remotecontrolserver.runner.ProcessManagerImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class RemoteControlLogicITest {

    private static RemoteControlLogicImpl remoteControlLogic;

    @Mock
    private HttpServletResponse response;

    @BeforeClass
    public static void beforeClass() throws IOException {
        remoteControlLogic = new RemoteControlLogicImpl();
        ExecutablesManagerImpl mapper = new ExecutablesManagerImpl();
        mapper.init();
        remoteControlLogic.setExecutablesManager(mapper);
        remoteControlLogic.setProcessManager(new ProcessManagerImpl());
    }

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void startAndStopProcess_success() throws IOException {
        String processName = "TeamViewer";
        remoteControlLogic.startProcess(processName, response);
        remoteControlLogic.stopProcess(processName, response);

        verify(response, times(0)).sendError(anyInt());
    }

}


