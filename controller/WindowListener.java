package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import model.ExtractData;
import model.HideData;

public class WindowListener extends JPanel implements ActionListener
{
    private HideData hd;
    private ExtractData ed;
    private JLabel fileLabel, bmpLabel;
    private JRadioButton hide;

    private JFileChooser fc;
    private String filePath, imagePath;

    private boolean validFile, validBMP = false;

    public WindowListener( HideData hd, ExtractData ed, JLabel fileLabel, JLabel bmpLabel )
    {
        this.hd = hd;
        this.ed = ed;
        this.fileLabel = fileLabel;
        this.bmpLabel = bmpLabel;
    }

    public void setHideRadio(JRadioButton hide)
    {
        this.hide = hide;
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        switch ( e.getActionCommand() )
        {
            case "Run":
                if(hide.isSelected())
                {
                    if(validBMP && validFile)
                    {
                        byte[] ans = this.hd.hideData(imagePath.trim(), filePath.trim());
                        this.hd.writeImage(ans);
                    }
                }
                else
                {
                    if(validBMP)
                    {
                        this.ed.rebuildPayload(this.ed.extractData(this.ed.readImage(imagePath)), this.ed.getExtenstion());
                    }
                }
                break;

            case "Browse .bmp...":
                fc = new JFileChooser();
                int returnVal = fc.showOpenDialog( this );
                if ( returnVal == JFileChooser.APPROVE_OPTION )
                {
                    File file = fc.getSelectedFile();
                    String fileName = file.getName();
                    if ( fileName.substring( fileName.lastIndexOf( "." ), fileName.length() ).equals( ".bmp" ) )
                    {
                        validBMP = true;
                        imagePath = file.getAbsolutePath();
                        this.bmpLabel.setText("Select .bmp: " + fileName);
                    }
                }
                else
                {
                    validBMP = false;
                    this.bmpLabel.setText("Select .bmp: .bmp files supported only");
                }
                break;

            case "Browse file...":
                fc = new JFileChooser();
                returnVal = fc.showOpenDialog( this );
                if ( returnVal == JFileChooser.APPROVE_OPTION )
                {
                    File file = fc.getSelectedFile();
                    String fileName = file.getName();
                    validFile = true;
                    filePath = file.getAbsolutePath();
                    this.fileLabel.setText("Select file: " + fileName);
                }
                break;

            default:
                break;
        }
    }

}
