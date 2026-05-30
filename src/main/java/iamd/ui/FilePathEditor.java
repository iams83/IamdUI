package iamd.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import iamd.rsrc.Resources;

@SuppressWarnings("serial")
public class FilePathEditor extends TextLineEditor
{
    JFileChooser fileChooser = new JFileChooser();
    
    private File defaultDirectory;
    
    private List<FileSelectionListener> listeners = new ArrayList<>();
    
    public FilePathEditor()
    {
    } 

    public void setDefaultDirectory(File directory)
    {
        this.defaultDirectory = directory;
    }
    
    public void addFileSelectionListener(FileSelectionListener listener)
    {
        this.listeners.add(listener);
    }
    
    public void removeFileSelectionListener(FileSelectionListener listener)
    {
        this.listeners.remove(listener);
    }
    
    protected void notifyFileSelectionListeners(File selectedFile)
    {
        for (FileSelectionListener listener : this.listeners)
        {
            listener.fileSelected(selectedFile);
        }
    }
    
    @Override
    protected ImageIcon getEditIcon()
    {
        return Resources.OpenFileIcon;
    }

    @Override
    protected String onValuePanelClicked(String previousValue)
    {
        File parentDirectory = null;
        
        if (previousValue != null)
        {
            File previousFile = new java.io.File(previousValue);
            parentDirectory = previousFile.getParentFile();
        }
        
        if (parentDirectory == null && this.defaultDirectory != null)
        {
            parentDirectory = this.defaultDirectory;
        }
        
        fileChooser.setCurrentDirectory(parentDirectory);
        fileChooser.setSelectedFile(previousValue == null ? null : new java.io.File(previousValue));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            
            // Call listeners before returning - they can update fields that will be captured
            notifyFileSelectionListeners(selectedFile);
            
            SwingUtilities.invokeLater(() -> {
                confirmCurrentValue();
            });
            
            return selectedFile.getAbsolutePath();
        }
        return previousValue;
    }
    
    public interface FileSelectionListener
    {
        void fileSelected(File selectedFile);
    }
}
