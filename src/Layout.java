import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Layout implements ActionListener {
        private JFrame mainFrame;
        private JLabel statusLabel;
        private JLabel statusLabel1;
        private JLabel statusLabel2;
        private JTextArea ta; // Text area in the middle
        private static JTextArea ta1;
        private JTextArea ta2;
        private JPanel gamePanel;
        private JPanel smallPanel;
        private JMenuBar mb;
        private JMenu file, edit, help;
        private JScrollPane scroll;
        private JMenuItem cut, copy, paste, selectAll;
        private int WIDTH=800;
        private int HEIGHT=700;


        public Layout() {
            prepareGUI();
        }

        public static void main(String[] args) {
            Layout Layout = new Layout();
            Layout.showEventDemo();
        }

        private void prepareGUI() {
            mainFrame = new JFrame("Charity");
            mainFrame.setSize(WIDTH, HEIGHT);
            mainFrame.setLayout(new BorderLayout());

            //menu at top
            cut = new JMenuItem("cut");
            copy = new JMenuItem("copy");
            paste = new JMenuItem("paste");
            selectAll = new JMenuItem("selectAll");
            cut.addActionListener(this);
            copy.addActionListener(this);
            paste.addActionListener(this);
            selectAll.addActionListener(this);

            // Adding JTextArea in the middle
            ta = new JTextArea();
            ta.setRows(3); // Adjust rows for size
            ta.setColumns(20); // Adjust columns for width


//            mb = new JMenuBar();
//            file = new JMenu("File");
//            edit = new JMenu("Edit");
//            help = new JMenu("Help");
//            edit.add(cut);
//            edit.add(copy);
//            edit.add(paste);
//            edit.add(selectAll);
//            mb.add(file);
//            mb.add(edit);
//            mb.add(help);
////            //end menu at top


//            mainFrame.add(ta);
//            mainFrame.add(mb);  //add menu bar
//            mainFrame.add(ta);//add typing area
//            mainFrame.setJMenuBar(mb); //set menu bar

            statusLabel = new JLabel("Input a cause", JLabel.CENTER);
            statusLabel.setSize(350, 100);

            statusLabel1 = new JLabel("What State are you from", JLabel.CENTER);
            statusLabel1.setSize(350, 100);

            statusLabel2 = new JLabel("Charities Info", JLabel.CENTER);
            statusLabel2.setSize(350, 100);

            statusLabel.setFont(new Font("Silom", Font.PLAIN,14));
            statusLabel1.setFont(new Font("Silom", Font.PLAIN,14));
            statusLabel2.setFont(new Font("Silom", Font.PLAIN,14));



            mainFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent) {
                    System.exit(0);
                }
            });

            gamePanel = new JPanel();
            smallPanel = new JPanel();

            gamePanel.setLayout(new GridLayout(7,1));
            smallPanel.setLayout(new GridLayout(1,2));
            //   mainFrame.add(scrollPane);

            ta = new JTextArea(); //A text area so the user can input an url
            ta.setBounds(300, 5, WIDTH-100, HEIGHT-100);

            ta1 = new JTextArea();  // A text area so the user can input a search term
            ta1.setBounds(300, 5, WIDTH-100, HEIGHT-100);

            ta2 = new JTextArea();  // A text area that has the links from the url that contain the search term displayed
            ta2.setBounds(300, 5, WIDTH-100, HEIGHT-100);

            mainFrame.add(gamePanel);
            mainFrame.setVisible(true);
        }


        public void showEventDemo() {

            JButton startButton = new JButton("Start");

            startButton.setActionCommand("Start");

            JButton resetButton = new JButton("Reset");

            resetButton.setActionCommand("Reset");

            startButton.addActionListener(new ButtonClickListener());

            resetButton.addActionListener(new ButtonClickListener());

            scroll = new JScrollPane(ta2);
            gamePanel.add(statusLabel);
            gamePanel.add(ta);
            gamePanel.add(statusLabel1);
            gamePanel.add(ta1);
            gamePanel.add(statusLabel2);
            gamePanel.add(scroll);
            gamePanel.add(smallPanel);
            smallPanel.add(resetButton);
            smallPanel.add(startButton);

            mainFrame.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == cut)
                ta.cut();
            if (e.getSource() == paste)
                ta.paste();
            if (e.getSource() == copy)
                ta.copy();
            if (e.getSource() == selectAll)
                ta.selectAll();

        }

    public  void pull() throws ParseException {
        String output = "abc";
        String totlaJson="";
        String str3 = ta.getText();

        try {

            URL url = new URL("https://data.orghunter.com/v1/charitysearch?user_key=9058ab49c4cfea622eb9a247cf35d2bb&searchTerm="+ str3) ;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {

                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());

            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                totlaJson+=output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        //System.out.println(str);
        JSONObject jsonObject = (JSONObject) parser.parse(totlaJson);
        System.out.println(jsonObject);

        try {

            // String name = (String)jsonObject.get("data");

            JSONArray msg = (JSONArray) jsonObject.get("data");
            int n =   msg.size(); //(msg).length();

            ta2.setText("");   // Clear previous results

            for (int i = 0; i < n; ++i) {

                JSONObject test =(JSONObject) msg.get(i);
                System.out.println(test);

               // String hi = (String) test.get("state"); // to pull data from the first text bo
//                String str3 = ta.getText();
                System.out.println(str3);

                String benz = (String) test.get("state");
                String str1 = benz;
                //System.out.println(str1);
                String str2 = ta1.getText();
                //System.out.println(str2);

                if(str1.equalsIgnoreCase(str2)){
                String hb =(String) test.get("charityName");
                System.out.println(hb);
                ta2.append("Charity Name: " + hb + "\n");

                System.out.println(ta1.getText());
                System.out.println(benz);
                ta2.append("Charity is From: " + benz + "\n");

                String cool = (String) test.get("category");
                System.out.println(cool);
                ta2.append("Category: " + cool + "\n");

                String poland = (String) test.get("donationUrl");
                System.out.println(poland);
                ta2.append("Donate Link: " + poland + "\n");
                ta2.append("-------------------------------------\n");

}
                // System.out.println(person.getInt("key"));
            }
//            String name= (String)jsonObject.get("height");
            //System.out.println(name);

        }

        catch (Exception e) {
            e.printStackTrace();
        }




    }

        private class ButtonClickListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if (command.equals("Reset")) {
                    ta.setText("");
                    ta1.setText("");
                    ta2.setText("");

                }
                if (command.equals("Start")) {
                    try {
                        pull();
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
//                statusLabel.setText(");

                } else if (command.equals("Submit")) {
                    statusLabel.setText("Submit Button clicked.");
                } else {
                    statusLabel.setText("Reset Button clicked.");
                }
            }
        }
    }
