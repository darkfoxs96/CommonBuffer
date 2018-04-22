import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;


public class Go extends JFrame{
    private JTextField textField1;
    private JButton playButton;
    private JTextField textField2;
    private JPanel panel;
    public static Go app;
    private TrayIcon iconTr;
    private SystemTray sT = SystemTray.getSystemTray();
    public boolean chetTray = false; //переменная, чтобы был вывод сообщения в трее только при первом сворачивании
    public boolean flagStart = true;

    public Go(String name){
        super(name);
        setVisible(true);
        add(panel);
        setSize(400,600);
        setLocation(300,300);
        textField1.setText(FilePatch.ToFilePatch("ip"));
        textField2.setText(FilePatch.ToFilePatch("port"));

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartClientCommonBuffer.setIpPort(textField1.getText(),Integer.parseInt(textField2.getText()));
                Save.ToFileSave(textField1.getText(),textField2.getText());
                if(flagStart) {
                    new StartClientCommonBuffer("Start");
                    flagStart = false;
                }
            }
        });

        addWindowListener(new WindowListener() {
            public void windowClosing(WindowEvent winEvent) {
                System.exit(0);//при закрытии окна завершаем программу
            }
            public void windowActivated(WindowEvent winEvent) {}
            public void windowClosed(WindowEvent winEvent) {}
            public void windowDeactivated(WindowEvent winEvent) {}
            public void windowDeiconified(WindowEvent winEvent) {}
            public void windowIconified(WindowEvent winEvent) {}
            public void windowOpened(WindowEvent winEvent) {}
        });

        try {
            iconTr = new TrayIcon(ImageIO.read(new File("src/icon.gif")), "CommonBuffer"); //Ikonka.png - изображение, которое будет показываться в трее - картинка в каталоге исполняемого приложения
        } catch (IOException e) {
            System.out.println("Exception load image: " + e);
        }
        iconTr.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                setVisible(true);
                setState(JFrame.NORMAL);
                removeTr();
            }
        });
        //обработчик мыши
        MouseListener mouS = new MouseListener() {
            public void mouseClicked(MouseEvent ev) { }
            public void mouseEntered(MouseEvent ev) { }
            public void mouseExited(MouseEvent ev) {  }
            public void mousePressed(MouseEvent ev) { }
            public void mouseReleased(MouseEvent ev) {}
        };
        iconTr.addMouseListener(mouS);
        MouseMotionListener mouM = new MouseMotionListener() {
            public void mouseDragged(MouseEvent ev) { }
            //при наведении
            public void mouseMoved(MouseEvent ev) {
                boolean flg = false;
                iconTr.setToolTip("Двойной щелчок - развернуть");
            }
        };

        iconTr.addMouseMotionListener(mouM);
        addWindowStateListener(new WindowStateListener()
        {
            public void windowStateChanged(WindowEvent ev)
            {
                if(ev.getNewState() == JFrame.ICONIFIED)
                {
                    setVisible(false);
                    addT();
                }
            }
        });
    }

    // метод удаления из трея
    private void removeTr(){ sT.remove(iconTr);}

    // метод добавления в трей
    private void addT() {
        try{
            sT.add(iconTr);
            if (chetTray==false) { iconTr.displayMessage("CommonBuffer", "Программа свернулась", TrayIcon.MessageType.INFO); }
            chetTray = true;
        }
        catch(AWTException ex)
        { ex.printStackTrace(); }
    }

    }






