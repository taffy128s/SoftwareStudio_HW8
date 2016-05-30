package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import javax.swing.JScrollPane;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Server extends JFrame implements Runnable {

    private ServerSocket servSock;
    private JTextArea textArea = new JTextArea();

    public Server(){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(300, 200);
        this.setVisible(true);
        this.setResizable(false);

        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.textArea.setEditable(false);
        this.setPreferredSize(new Dimension(300, 200));
        JScrollPane scrollPane = new JScrollPane(this.textArea);
        this.add(scrollPane);
        this.pack();

        try {
            InetAddress IP = InetAddress.getLocalHost();
            textArea.append("IP of my system is: " + IP.getHostAddress() + "\n");
            textArea.append("Waiting to connect......\n");

            servSock = new ServerSocket(2000);
            Thread thread = new Thread(this);
            thread.start();

        } catch (java.io.IOException e) {
            System.out.println("Socket error.");
            System.out.println("IOException: " + e.toString());
        }
    }

    @Override
    public void run() {
        BufferedReader reader;
        while (true) {
            try{
                Socket clntSock = servSock.accept();

                reader = new BufferedReader(new InputStreamReader(clntSock.getInputStream()));
                String line = reader.readLine();
                textArea.append("The result from APP: " + line + "\n");

            } catch (Exception e) {
                break;
            }
        }
    }
}
