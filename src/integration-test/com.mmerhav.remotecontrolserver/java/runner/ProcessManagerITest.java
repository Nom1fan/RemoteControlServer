package runner;

import com.mmerhav.remotecontrolserver.RemoteControlServerApplication;
import com.mmerhav.remotecontrolserver.runner.ProcessManager;
import com.mmerhav.remotecontrolserver.runner.RunProcessResult;
import com.mmerhav.remotecontrolserver.runner.StopProcessResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RemoteControlServerApplication.class)
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
