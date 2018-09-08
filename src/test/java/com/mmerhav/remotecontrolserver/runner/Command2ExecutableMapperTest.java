package com.mmerhav.remotecontrolserver.runner;

import com.mmerhav.remotecontrolserver.mapper.Command2ExecutableMapper;
import com.mmerhav.remotecontrolserver.mapper.CommandNotFoundException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Command2ExecutableMapperTest {

    @Autowired
    private Command2ExecutableMapper command2ExecutableMapper;

    @Test
    public void getTeamViewerPathByCommand_Success() {
        String teamViewerPath = command2ExecutableMapper.getExecutableAbsolutePath("TeamViewer");
        Assert.assertEquals("C:/program files/TeamViewer/TeamViewer.exe", teamViewerPath);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getByNonExistentCommand_Failure() {
        expectedException.expect(CommandNotFoundException.class);
        expectedException.expectMessage("FakeCommand");
        command2ExecutableMapper.getExecutableAbsolutePath("FakeCommand");
    }
}
