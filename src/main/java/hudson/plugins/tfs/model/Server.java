package hudson.plugins.tfs.model;

import hudson.plugins.tfs.TfTool;
import hudson.plugins.tfs.commands.ServerConfigurationProvider;
import hudson.plugins.tfs.util.MaskedArgumentListBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author S606734
 *
 */
public class Server implements ServerConfigurationProvider {
    
    private final String url;
    private final String userName;
    private final String userPassword;
    private final String version;
    private final String typeVersion;
    private final String labelName;
    
    private Workspaces workspaces;
    private Map<String, Project> projects = new HashMap<String, Project>();
    private final TfTool tool;

    /**
     * Constructor for 1.21+ compatibility.
     * @param tool
     * @param url
     * @param username
     * @param password
     * @param version
     */
    public Server(TfTool tool, String url, String username, String password, String version, String typeVersion, String labelName) {
        this.tool = tool;
        this.url = url;
        this.userName = username;
        this.userPassword = password;
        this.version = version;
        if (typeVersion==null || "".equals(typeVersion.trim())) {
          this.typeVersion=ServerConfigurationProvider.VERSION_LATEST;
        } else {
          this.typeVersion = typeVersion;
        }
        this.labelName = labelName;
    }

    Server(String url) {
        this(null, url, null, null, ServerConfigurationProvider.TFS_TEE_CLC_10, null, null);
    }

    public Project getProject(String projectPath) {
        if (! projects.containsKey(projectPath)) {
            projects.put(projectPath, new Project(this, projectPath));
        }
        return projects.get(projectPath);
    }
    
    public Workspaces getWorkspaces() {
        if (workspaces == null) {
            workspaces = new Workspaces(this);
        }
        return workspaces;
    }
    
    public Reader execute(MaskedArgumentListBuilder arguments) throws IOException, InterruptedException {
        return tool.execute(arguments.toCommandArray(), arguments.toMaskArray());
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Getter for version.
     * @return the version
     */
    public String getVersion() {
        return version;
    }
    

    /**
     * @see hudson.plugins.tfs.commands.ServerConfigurationProvider#getLocalHostname()
     */
    public String getLocalHostname() throws IOException, InterruptedException {
        return tool.getHostname();
    }

    /**
     * @see hudson.plugins.tfs.commands.ServerConfigurationProvider#getTypeVersion()
     */
    public String getTypeVersion() {
      return this.typeVersion;
    }

    /**
     * @see hudson.plugins.tfs.commands.ServerConfigurationProvider#getLabelName()
     */
    public String getLabelName() {
      return this.labelName;
    }
}
