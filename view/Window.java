package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.WindowListener;
import model.ExtractData;
import model.HideData;

public class Window
{
    private JFrame frame;
    private HideData hd;
    private ExtractData ed;
    private ActionListener listener;

    private JButton run, browseFile, browseBMP;

    private JTextArea text;

    private JRadioButton hide, extract;

    private JLabel selectFileJLabel, selectBMPJLabel;

    private Font font;

    private JScrollPane spIn0;

    public Window( HideData hd, ExtractData ed )
    {
        this.hd = hd;
        this.ed = ed;
    }

    public void createGUI()
    {
        frame = new JFrame( "Steganography" );
        frame.setSize( 300, 160 );

        text = new JTextArea();

        spIn0 = new JScrollPane();
        spIn0.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );

        selectFileJLabel = new JLabel( "Select file:" );
        selectBMPJLabel = new JLabel( "Select .bmp: " );
        listener = new WindowListener(hd, ed, selectFileJLabel, selectBMPJLabel);

        createButtons();

        ((WindowListener) listener).setHideRadio(hide);

        JPanel panel = new JPanel();
        panel.setBackground( Color.gray );
        Dimension size = null;

        panel.setLayout( null );

        panel.add( selectFileJLabel );
        size = selectFileJLabel.getPreferredSize();
        selectFileJLabel.setBounds( 15, 5, size.width + 300, size.height );
        panel.add( browseFile );
        size = browseFile.getPreferredSize();
        browseFile.setBounds( 15, 25, size.width, size.height );

        panel.add( selectBMPJLabel );
        size = selectBMPJLabel.getPreferredSize();
        selectBMPJLabel.setBounds( 15, 65, size.width + 300, size.height );
        panel.add( browseBMP );
        size = browseBMP.getPreferredSize();
        browseBMP.setBounds( 15, 85, size.width, size.height );

        panel.add( hide );
        size = hide.getPreferredSize();
        hide.setBounds( 180, 25, size.width, size.height );
        panel.add( extract );
        size = extract.getPreferredSize();
        extract.setBounds( 180, 50, size.width, size.height );

        panel.add( run );
        size = run.getPreferredSize();
        run.setBounds( 180, 80, size.width, size.height );

        for ( Component c : panel.getComponents() )
        {
            if ( c instanceof JButton )
            {
                styleButton( (JButton) c );
            }
            else if ( c instanceof JTextArea )
            {
                styleTextArea( (JTextArea) c );
            }
            else if ( c instanceof JLabel )
            {
                styleLabel( (JLabel) c );
            }
            else if ( c instanceof JRadioButton )
            {
                styleRadioButton( (JRadioButton) c );
            }
        }

        frame.setResizable( false );
        frame.add( panel );
        frame.setLocationRelativeTo( null );
        frame.setVisible( true );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }

    public void createButtons()
    {
        run = new JButton( "Run" );
        run.addActionListener( listener );

        browseFile = new JButton( "Browse file..." );
        browseFile.addActionListener( listener );

        browseBMP = new JButton( "Browse .bmp..." );
        browseBMP.addActionListener( listener );

        hide = new JRadioButton("Hide data" );
        hide.setSelected( true );
        hide.addItemListener( (new ItemListener()
        {
            @Override
            public void itemStateChanged( ItemEvent arg0 )
            {
                if ( hide.isSelected() )
                {
                    extract.setSelected( false );
                }
                else
                {
                    extract.setSelected( true );
                }
            }
        }) );

        extract = new JRadioButton( "Extract data" );
        extract.addItemListener( (new ItemListener()
        {
            @Override
            public void itemStateChanged( ItemEvent arg0 )
            {
                if ( extract.isSelected() )
                {
                    hide.setSelected( false );
                }
                else
                {
                    hide.setSelected( true );
                }
            }
        }) );
    }

    public void styleButton( JButton button )
    {
        button.setFont( font );
        button.setBackground( new Color( 50, 50, 50 ) );
        button.setForeground( new Color( 30, 255, 0 ) );
        button.setBorder( BorderFactory.createLineBorder( new Color( 125, 125, 125 ) ) );
        button.setFocusPainted( false );
    }

    public void styleLabel( JLabel label )
    {
        label.setFont( new Font( "Arial", Font.BOLD, 12 ) );
        label.setBackground( new Color( 180, 180, 180 ) );
        label.setForeground( new Color( 0, 0, 0 ) );
    }

    public void styleTextArea( JTextArea textArea )
    {
        textArea.setFont( font );
        textArea.setBackground( new Color( 180, 180, 180 ) );
        textArea.setForeground( new Color( 0, 0, 0 ) );
        textArea.setBorder( BorderFactory.createLineBorder( new Color( 125, 125, 125 ) ) );
    }

    public void styleRadioButton( JRadioButton rButton )
    {
        rButton.setFont( new Font( "Arial", Font.BOLD, 12 ) );
        rButton.setBackground( Color.GRAY );
        rButton.setForeground( new Color( 0, 0, 0 ) );
    }
}
