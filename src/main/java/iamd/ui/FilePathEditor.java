package iamd.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import iamd.rsrc.Resources;
import net.iharder.dnd.FileDrop;

@SuppressWarnings("serial")
public class FilePathEditor extends TextLineEditor
{
    private JFileChooser fileChooser = new JFileChooser();
    
    private File defaultDirectory;
    
    private List<FileSelectionListener> listeners = new ArrayList<>();
    
    public FilePathEditor()
    {
        super();
        
        new FileDrop(this, new FileDrop.Listener()
        {
            @Override
            public void filesDropped(File[] files)
            {
                if (files.length > 0)
                {
                    File droppedFile = files[0];

                    setValue(droppedFile.getAbsolutePath());

                    notifyFileSelectionListeners(droppedFile);

                    SwingUtilities.invokeLater(() -> {
                        confirmCurrentValue();
                    });
                }
            }
        });
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
            
            setValue(selectedFile.getAbsolutePath());

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