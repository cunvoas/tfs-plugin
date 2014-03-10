package hudson.plugins.tfs.commands;

import hudson.plugins.tfs.util.MaskedArgumentListBuilder;


public class NewWorkspaceCommand extends AbstractCommand {
    private final String workspaceName;

    public NewWorkspaceCommand(ServerConfigurationProvider provider, String workspaceName) {
        super(provider);
        this.workspaceName = workspaceName;
    }

    public MaskedArgumentListBuilder getArguments() {
        ServerConfigurationProvider config = super.getConfig();
        
        MaskedArgumentListBuilder arguments = new MaskedArgumentListBuilder();        
        arguments.add("workspace");
        arguments.add("-new");
        
        // on TFS 2010 server is replaced by collection and the order change
        if (ServerConfigurationProvider.TFS_TEE_CLC_11.equals(config.getVersion())) {
            addServerArgument(arguments);
            arguments.add(String.format("%s;%s", workspaceName, getConfig().getUserName()));
            addLoginArgument(arguments);
            arguments.add("-noprompt");
            
        } else {
            arguments.add(String.format("%s;%s", workspaceName, getConfig().getUserName()));
            arguments.add("-noprompt");
            addServerArgument(arguments);
            addLoginArgument(arguments);
        }
        
        return arguments;
    }
}
