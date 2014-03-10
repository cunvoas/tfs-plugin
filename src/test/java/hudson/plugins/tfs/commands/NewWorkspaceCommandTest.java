package hudson.plugins.tfs.commands;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import hudson.plugins.tfs.commands.NewWorkspaceCommand;
import hudson.plugins.tfs.util.MaskedArgumentListBuilder;

import org.junit.Test;

public class NewWorkspaceCommandTest {

    @Test
    public void assertArguments() {
        ServerConfigurationProvider config = mock(ServerConfigurationProvider.class);
        when(config.getUrl()).thenReturn("https//tfs02.codeplex.com");
        when(config.getUserName()).thenReturn("snd\\user_cp");
        when(config.getUserPassword()).thenReturn("password");
        when(config.getVersion()).thenReturn(ServerConfigurationProvider.TFS_TEE_CLC_10);
        
        MaskedArgumentListBuilder arguments = new NewWorkspaceCommand(config, "TheWorkspaceName").getArguments();
        
        
        assertNotNull("Arguments were null", arguments);
        assertEquals("workspace -new TheWorkspaceName;snd\\user_cp -noprompt -server:https//tfs02.codeplex.com -login:snd\\user_cp,password", arguments.toStringWithQuote());
    }
    
    @Test
    public void assertArgumentsTfs2008() {
        ServerConfigurationProvider config = mock(ServerConfigurationProvider.class);
        when(config.getUrl()).thenReturn("https//tfs02.codeplex.com");
        when(config.getUserName()).thenReturn("snd\\user_cp");
        when(config.getUserPassword()).thenReturn("password");
        when(config.getVersion()).thenReturn(ServerConfigurationProvider.TFS_TEE_CLC_10);
        
        MaskedArgumentListBuilder arguments = new NewWorkspaceCommand(config, "TheWorkspaceName").getArguments();

        assertNotNull("Arguments were null", arguments);
        assertEquals("workspace -new TheWorkspaceName;snd\\user_cp -noprompt -server:https//tfs02.codeplex.com -login:snd\\user_cp,password", arguments.toStringWithQuote());
    }

    
    @Test
    public void assertArgumentsTfs2010() {
        ServerConfigurationProvider config = mock(ServerConfigurationProvider.class);
        when(config.getUrl()).thenReturn("https//tfs02.codeplex.com");
        when(config.getUserName()).thenReturn("snd\\user_cp");
        when(config.getUserPassword()).thenReturn("password");
        when(config.getVersion()).thenReturn(ServerConfigurationProvider.TFS_TEE_CLC_11);
        
        MaskedArgumentListBuilder arguments = new NewWorkspaceCommand(config, "TheWorkspaceName").getArguments();

        assertNotNull("Arguments were null", arguments);
        assertEquals("workspace -new -collection:https//tfs02.codeplex.com TheWorkspaceName;snd\\user_cp -login:snd\\user_cp,password -noprompt", arguments.toStringWithQuote());
    }
}
