package hudson.plugins.tfs.actions;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import hudson.FilePath;
import hudson.plugins.tfs.model.ChangeSet;
import hudson.plugins.tfs.model.Project;
import hudson.plugins.tfs.model.Server;
import hudson.plugins.tfs.model.Workspace;
import hudson.plugins.tfs.model.Workspaces;
import hudson.plugins.tfs.util.BuildVariableResolver;
import hudson.plugins.tfs.util.DateUtil;

/**
 * Méthode de checkout
 * @author Erik Ramfelt
 * @author Christophe UNVOAS
 * @see http://msdn.microsoft.com/fr-fr/library/fx7sdeyf(v=vs.80).aspx
 */
public class CheckoutAction {
  
    private static final Logger LOGGER = Logger.getLogger(CheckoutAction.class.getName());

    private final String workspaceName;
    private final String projectPath;
    private final String localFolder;
    private final boolean useUpdate;

    public CheckoutAction(String workspaceName, String projectPath, String localFolder, boolean useUpdate) {
        this.workspaceName = workspaceName;
        this.projectPath = projectPath;
        this.localFolder = localFolder;
        this.useUpdate = useUpdate;
    }

    /**
     * Get files from current date (latest version).
     * @param server
     * @param workspacePath
     * @param lastBuildTimestamp
     * @param currentBuildTimestamp
     *    Version la plus récente (T)
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @throws ParseException
     */
    public List<ChangeSet> checkout(Server server, FilePath workspacePath, Calendar lastBuildTimestamp, Calendar currentBuildTimestamp) throws IOException, InterruptedException, ParseException {
        LOGGER.log(Level.INFO, "checkout latest");
        Workspaces workspaces = server.getWorkspaces();
        Project project = server.getProject(projectPath);
        
        if (workspaces.exists(workspaceName) && !useUpdate) {
            Workspace workspace = workspaces.getWorkspace(workspaceName);
            workspaces.deleteWorkspace(workspace);
        }
        
        Workspace workspace;
        if (! workspaces.exists(workspaceName)) {
            FilePath localFolderPath = workspacePath.child(localFolder);
            if (!useUpdate && localFolderPath.exists()) {
                localFolderPath.deleteContents();
            }
            workspace = workspaces.newWorkspace(workspaceName);
            workspace.mapWorkfolder(project, localFolder);
        } else {
            workspace = workspaces.getWorkspace(workspaceName);
        }
        
        project.getFiles(localFolder, "D" + DateUtil.TFS_DATETIME_FORMATTER.get().format(currentBuildTimestamp.getTime()));
        
        if (lastBuildTimestamp != null) {
            return project.getDetailedHistory(lastBuildTimestamp, currentBuildTimestamp);
        }
        
        return new ArrayList<ChangeSet>();
    }

    /**
     * Get files from label (labeled version).
     * @param server
     * @param workspacePath
     * @param type 
     *    Date/heure (D10/20/2005)
     *    Version d'ensemble de modifications (C1256)
     *    Étiquette (Lmylabel)
     *    Version d'espace de travail (Wworkspacename ; propriétaire)
     * @param label
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @throws ParseException
     */
    public List<ChangeSet> checkout(Server server, FilePath workspacePath, String type, String label) throws IOException, InterruptedException, ParseException {
      LOGGER.log(Level.INFO, "checkout "+type+" "+label);
      
        Workspaces workspaces = server.getWorkspaces();
        Project project = server.getProject(projectPath);
        
        if (workspaces.exists(workspaceName) && !useUpdate) {
            Workspace workspace = workspaces.getWorkspace(workspaceName);
            workspaces.deleteWorkspace(workspace);
        }
        
        Workspace workspace;
        if (! workspaces.exists(workspaceName)) {
            FilePath localFolderPath = workspacePath.child(localFolder);
            if (!useUpdate && localFolderPath.exists()) {
                localFolderPath.deleteContents();
            }
            workspace = workspaces.newWorkspace(workspaceName);
            workspace.mapWorkfolder(project, localFolder);
        } else {
            workspace = workspaces.getWorkspace(workspaceName);
        }
        
        project.getFiles(localFolder, type + label);
                
        return new ArrayList<ChangeSet>();
    }
}
