package Darshan;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFileChooser;

import java.awt.Component;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.net.URI;


class TextEditor implements ActionListener {

    JFrame f;
    
    JMenuBar menuBar;
    JMenu File,
            Edit,
            Themes,
           // Help,
            More;
    JTextArea textArea;
    JScrollPane scroll;
    JMenuItem Dark,
            Light,
            Default,
            Save,
            
            Open,
            Close,
            Cut,
            Copy,
            Paste,
            New,
            SelectAll,
           
            Contact_us,
            Mobile,
            
            FontSize;
    JPanel saveFileOptionWindow;
    JLabel fileLabel, dirLabel;
    JTextField fileName, dirName;

    TextEditor(){
        f = new JFrame("Untitled_Document-1"); //setting the frame
        Toolkit t= Toolkit.getDefaultToolkit();
       Image img =t.getImage("download.png"); //adding image
        f.setIconImage(img);
       
       
        
        menuBar = new JMenuBar();
menuBar.setBackground(Color.lightGray);
        //menues
        File = new JMenu("File");
        Edit = new JMenu("Edit");
        Themes = new JMenu("Themes");
        
        More =new JMenu("More");

        //adding menues to menubar
        menuBar.add(File);
        menuBar.add(Edit);
        menuBar.add(Themes);
       
        menuBar.add(More);
        f.setJMenuBar(menuBar);

        //adding submenus to file
        Save = new JMenuItem("Save");
        
        Open = new JMenuItem("Open");       //file menu
        New = new JMenuItem("New");
        Close = new JMenuItem("Exit");
        File.add(Open);
        File.add(New);
        File.add(Save);
        
        File.add(Close);

        Cut = new JMenuItem("Cut");
        Copy = new JMenuItem("Copy");        //edit menu
        Paste = new JMenuItem("Paste");
        SelectAll = new JMenuItem("Select all");
        FontSize = new JMenuItem("Font size");
        Edit.add(Cut);
        Edit.add(Copy);
        Edit.add(Paste);
        Edit.add(SelectAll);
        Edit.add(FontSize);

        //adding themes
        Dark = new JMenuItem("Dark");
        Light = new JMenuItem("Light");
        Default = new JMenuItem("Default ");
        Themes.add(Dark);
        Themes.add(Light);
        Themes.add(Default);

       
        
        //More menu
        
      Contact_us =new JMenuItem("contact us");
       
        More.add(Contact_us);
       
       
       
        
      
        
        //Textarea
        textArea = new JTextArea(32,88);
        f.add(textArea);

        //scrollpane
        scroll = new JScrollPane(textArea);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        f.add(scroll);

        //adding event listeners for cut , copy & paste
        Cut.addActionListener(this);
        Copy.addActionListener(this);
        Paste.addActionListener(this);
        SelectAll.addActionListener(this);
        FontSize.addActionListener(this); //change the font size
        Open.addActionListener(this); //open the file
        Save.addActionListener(this); //Save the file
        
        New.addActionListener(this); //Create the new document
        Dark.addActionListener(this); //dark theme
        Light.addActionListener(this); //moonlight theme
        Default.addActionListener(this); // default theme
     
        Contact_us.addActionListener(this);
        Close.addActionListener(this); //close the window

        f.addWindowListener(new WindowListener() {
            //Override
            public void windowOpened(WindowEvent windowEvent) {}

            //Override
            public void windowClosing(WindowEvent e) {
                int confirmExit = JOptionPane.showConfirmDialog(f,"Do you really want to exit?","Confirmation",JOptionPane.YES_NO_OPTION);

                if (confirmExit == JOptionPane.YES_OPTION)
                    f.dispose();
                else if (confirmExit == JOptionPane.NO_OPTION)
                    f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }

            //Override
            public void windowClosed(WindowEvent windowEvent) {}

            //Override
            public void windowIconified(WindowEvent windowEvent) {}

            //Override
            public void windowDeiconified(WindowEvent windowEvent) {}

            //Override
            public void windowActivated(WindowEvent windowEvent) {}

            //Override
            public void windowDeactivated(WindowEvent windowEvent) {}
        });

        //Keyboard Listeners
        KeyListener k = new KeyListener() {
            //Override
            public void keyTyped(KeyEvent e) { }

            //Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_S && e.isControlDown())
                    saveTheFile(); //Saving the file
            }

            //Override
            public void keyReleased(KeyEvent e) { }
        };
        textArea.addKeyListener(k);

        //Default Operations for frame
        f.setSize(1000,600);
        f.setResizable(true);
        f.setLocation(250,100);
        
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //Override
    public void actionPerformed(ActionEvent e) {
        //Copy paste operations
        if (e.getSource()==Cut)
            textArea.cut();
        if (e.getSource()==Copy)
            textArea.copy();
        if (e.getSource()==Paste)
            textArea.paste();
        if (e.getSource()==SelectAll)
            textArea.selectAll();

        //change the fontsize by value
        if (e.getSource()==FontSize){

            String sizeOfFont = JOptionPane.showInputDialog(f,"Enter Font Size");
                if (sizeOfFont != null){
                    int convertSizeOfFont = Integer.parseInt(sizeOfFont);
                    Font font = new Font(Font.SERIF,Font.BOLD,convertSizeOfFont);
                    textArea.setFont(font);
                }
        }

        //Open the file
        if (e.getSource()==Open){
            JFileChooser chooseFile = new JFileChooser();
            int i = chooseFile.showOpenDialog(f);
            if (i == JFileChooser.APPROVE_OPTION){
                File file = chooseFile.getSelectedFile(); //select the file
                String filePath = file.getPath(); //get the file path
                String fileNameToShow = file.getName(); //get the file name
                f.setTitle(fileNameToShow);

               try {
                   BufferedReader readFile = new BufferedReader(new FileReader(filePath));
                   String tempString1 = "";
                   String tempString2 = "";

                   while ((tempString1 = readFile.readLine()) != null)
                        tempString2 += tempString1 + "\n";

                   textArea.setText(tempString2);
                   readFile.close();
               }catch (Exception ae){
                   ae.printStackTrace();
               }
            }
        }


        //Save the file
        if (e.getSource()==Save) saveTheFile();


        //New menu operations
        if (e.getSource()==New) textArea.setText("");


        //Exit from the window
        if (e.getSource()==Close) System.exit(1);


        //themes area
        if (e.getSource()==Dark){
            textArea.setBackground(Color.black);        
            textArea.setForeground(Color.WHITE);
        }

        if (e.getSource()==Light){
            textArea.setBackground(new Color(107, 169, 255));
            textArea.setForeground(Color.white);
        }

        if (e.getSource() == Default){
            textArea.setBackground(new Color(255, 255, 255));
            textArea.setForeground(Color.black);
        }

       
    }

//Save the file
    public void saveTheFile(){
        saveFileOptionWindow = new JPanel(new GridLayout(2,1));
        
        fileLabel = new JLabel("Filename      :- ");
        dirLabel = new JLabel("Save File To :- ");
        fileName = new JTextField();
        dirName = new JTextField();
        
        saveFileOptionWindow.add(fileLabel);
        saveFileOptionWindow.add(fileName);
        saveFileOptionWindow.add(dirLabel);
        saveFileOptionWindow.add(dirName);

        JOptionPane.showMessageDialog(f,saveFileOptionWindow); //show the saving dialogue box
        String fileContent = textArea.getText();
        String filePath = dirName.getText();

        try {
            BufferedWriter writeContent = new BufferedWriter(new FileWriter(filePath));
            writeContent.write(fileContent);
            writeContent.close();
            JOptionPane.showMessageDialog(f,"File Successfully saved!");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TextEditor();
    }
}

