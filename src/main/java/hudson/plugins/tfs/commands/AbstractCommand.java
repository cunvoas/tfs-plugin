package hudson.plugins.tfs.commands;

import hudson.Util;
import hudson.plugins.tfs.util.MaskedArgumentListBuilder;
import hudson.util.ArgumentListBuilder;

public abstract class AbstractCommand implements Command {

    private final ServerConfigurationProvider config;
    
    public AbstractCommand(ServerConfigurationProvider configurationProvider) {
        this.config = configurationProvider;
    }

    protected void addServerArgument(ArgumentListBuilder arguments) {
        if (ServerConfigurationProvider.TFS_TEE_CLC_11.equals(config.getVersion())) {
            arguments.add(String.format("-collection:%s", config.getUrl()));
        } else {
            arguments.add(String.format("-server:%s", config.getUrl()));
        }
    }
    
    protected void addLoginArgument(MaskedArgumentListBuilder arguments) {
        if ((Util.fixEmpty(config.getUserName()) != null) && (config.getUserPassword()!= null)) {
            arguments.addMasked(String.format("-login:%s,%s", 
                    config.getUserName(), 
                    config.getUserPassword()));
        }
    }

    public ServerConfigurationProvider getConfig() {
        return config;
    }
}
