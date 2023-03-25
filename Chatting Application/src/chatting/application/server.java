package chatting.application;

import javax.swing.*;  //For Frame
import javax.swing.border.*;  //Packages For Using Empty BackGround 
import java.awt.*;     //For Colour
import java.awt.event.*; //For Performing Actions like Closing arrow mark
import java.util.*;       //Used for Using Calender 
import java.text.*;       //To Print Time 
import java.net.*;        //Used for Server And socket programming
import java.io.*; 


public class server implements ActionListener {     //ActionListener is used to perform action like back icon
    
    JTextField text;            //Globally Declaring To Perform Action 
    JPanel a1;    //Globally DEclared To Print The Text Over The Panel
    static Box vertical = Box.createVerticalBox();  //To Print Messages Vertically
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    
    server() {
        f.setLayout(null);
    
        JPanel p1 = new JPanel();                                   //Panel Creation
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));              //Back Click Icon
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);
        
        back.addMouseListener(new MouseAdapter() {                                                          //Action for Back Click
            public void mouseclicked(MouseEvent ae) {
                System.exit(0);
            }
        });
        
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));               //Profile Image Icon
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);
        
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));            //VideoCall Icon
        Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);
        
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));            //PhoneCall Icon
        Image i11 = i10.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(355,20,35,30);
        p1.add(phone);
        
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));             //Morevert Icon
        Image i14 = i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(410,20,10,25);
        p1.add(morevert);
        
        JLabel name = new JLabel("Person A");                                                               //Name Of Sender(Person A)
        name.setBounds(110,15,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);
        
        JLabel status = new JLabel("Active Now");                                                           //Status Text
        status.setBounds(110,35,100,18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        p1.add(status);
        
        a1 = new JPanel();                                                                               //Message Send Or Receive View Panel
        a1.setBounds(5,75,440,570);
        f.add(a1);
         
        text = new JTextField();                                                                     //Text Typing Field
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(text);
        
        JButton send = new JButton("send");                                                                 //Send Button
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);            //To Print The Text in The Chat Panel
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(send);
       
        f.setSize(450,700);                                                                            //Panel Background Location and Size
        f.setLocation(200,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
    }
    
    
    public void actionPerformed(ActionEvent ae) {  
       try {
        String out = text.getText();     //Get Text Into The Out
        
        JPanel p2 = formatLabel(out);
        
        a1.setLayout(new BorderLayout());
        
        JPanel right = new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);     //To Print The Text At The End Of The Line
        vertical.add(right);                              //To Add To Right Side
        vertical.add(Box.createVerticalStrut(15));  //Vertical Space of 15 Between Each Text
        
        a1.add(vertical,BorderLayout.PAGE_START); //Start From The Page_Start
        
        dout.writeUTF(out);
        
        text.setText("");                                    //To Empty Text Field after The Text is Sent
        
        f.repaint();  //These Three Are JFrame Functions Used To Print The Text In The Panel
        f.invalidate();
        f.validate();
        
       } catch (Exception e) {
           e.printStackTrace();
       }
        
    }
     
    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16)); // Setting Font Size To Display In Chat
        output.setBackground(new Color(37,211,102));            //Chat Text Background
        output.setOpaque(true);                                //Used To Make Visible Of Background Colour
        output.setBorder(new EmptyBorder(15,15,15,50)); //Sizing Of The Chat Text And Background
        
        
        
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();                      //Time Format Apply
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));  //To Print Time Dynamically
        
        panel.add(time);                               //To Add Time To Panel
        
        
        
        return panel;
        
    }
    
    
    public static void main(String[] args) {                                                                    //Main Function
        new server();
        
        try {
            ServerSocket skt = new ServerSocket(6001);   //Creating SetrverSocket
         
            while(true) {            //Accepting Messages Infinitly
                Socket s = skt.accept();        //Storing Of Messages
                DataInputStream din = new DataInputStream(s.getInputStream());  //Get Input 
                dout = new DataOutputStream(s.getOutputStream()); //Send Output
                
                while(true) {
                    String msg = din.readUTF(); //Read The Message Sent by Client
                    JPanel panel = formatLabel(msg);   //Print The received Message
                    
                    JPanel left = new JPanel(new BorderLayout());  //Printing the message on Left Which Is Received From The Client
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
  
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
}
