package dpatterns;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;

import commands.Command;
import commands.CommandFactory;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.border.BevelBorder;
import java.awt.SystemColor;

public class Main {

  private static JFrame frmLanchat;
  private static JTextField textField;
  private static JTextPane textPane;
  private static JScrollPane scrollPane;
  private static ChatSocket socket;
  private static ArrayList<String> inputs = new ArrayList<String>();
  private static int history_pos = 0;
  public static boolean muted = false;
  public static boolean initialized = false;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          Main.initialize();
          Main.frmLanchat.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    int port = args.length > 0 ? Integer.parseInt(args[0]) : 40000;
    String addr = args.length > 1 ? args[1] : "224.0.0.1";
    System.out.println(addr + ":" + port);
    
    while (!initialized) {
      Thread.yield();
    }
    
    socket = new ChatSocket(port, addr);
    while (true) {
      String m = socket.receive();
      if (!m.equals("")) {
        if (!muted) {
          java.awt.Toolkit.getDefaultToolkit().beep();
        }
        insertMessage(m + "<br/>");
      }
    }
  }

  public static void insertMessage(Object message) {
    String content = textPane.getText();
    int pos = content.indexOf("</body>");
    textPane.setText(content.substring(0, pos - 1) + message + content.substring(pos));
  }

  /**
   * Initialize the contents of the frame.
   */
  private static void initialize() {
    frmLanchat = new JFrame();
    frmLanchat.getContentPane().setBackground(Color.WHITE);
    frmLanchat.setBackground(Color.WHITE);
    frmLanchat.setTitle("LANChat");
    frmLanchat.setResizable(false);
    frmLanchat.setBounds(200, 200, 640, 480);
    frmLanchat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frmLanchat.getContentPane().setLayout(null);

    textField = new JTextField();
    textField.setBackground(SystemColor.control);
    textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
    textField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent arg0) {
        if (arg0.getKeyCode() == KeyEvent.VK_ENTER && !textField.getText().equals("")) {
          String message = textField.getText();
          Command cmd = CommandFactory.extractCommand(socket, message);
          if (cmd != null) {
            cmd.execute();
          } else {
            socket.say(message);
          }
          textField.setText("");
          if (inputs.size() == 0 || !inputs.get(inputs.size() - 1).equals(message)) {
            inputs.add(message);
          }
          history_pos = inputs.size();
        } else if (arg0.getKeyCode() == KeyEvent.VK_UP) {
          if (inputs.size() > 0 && history_pos > 0) {
            history_pos--;
            textField.setText(inputs.get(history_pos));
          }
        } else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
          if (inputs.size() > 0 && history_pos < inputs.size() - 1) {
            history_pos++;
            textField.setText(inputs.get(history_pos));
          }
        }
      }
    });
    textField.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    textField.setBounds(10, 414, 614, 26);
    frmLanchat.getContentPane().add(textField);
    textField.requestFocusInWindow();

    scrollPane = new JScrollPane();
    scrollPane.setBackground(Color.WHITE);
    scrollPane.setFocusTraversalKeysEnabled(false);
    scrollPane.setFocusable(false);
    scrollPane.setBorder(null);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setBounds(10, 11, 614, 392);
    frmLanchat.getContentPane().add(scrollPane);

    textPane = new JTextPane();
    textPane.setFocusable(false);
    textPane.setFocusTraversalKeysEnabled(false);
    textPane.setBackground(Color.WHITE);
    textPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
    textPane.setAutoscrolls(false);
    scrollPane.setViewportView(textPane);
    textPane.setEditable(false);
    textPane.setBorder(null);
    textPane.setContentType("text/html");
    textPane.putClientProperty(JTextPane.HONOR_DISPLAY_PROPERTIES, true);
    textPane.setText("<html><head></head><body></body></html>");
    ((DefaultCaret)textPane.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    
    initialized = true;
  }
}
