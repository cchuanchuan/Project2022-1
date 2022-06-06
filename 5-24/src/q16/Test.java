package q16;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test extends JFrame {
    public Test() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("q15.Test app");
        setSize(300, 200);
        setLocation(10, 200);
        JPanel content = new JPanel();
        JButton myButton = new JButton("q15.Test button");
        content.add(myButton);
        setContentPane(content);
        // your code for handling button click event goes here

        myButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myButton.setText(Test.generateLabel());
            }
        });
    }

    public static String generateLabel() {
        List<String> labels = new ArrayList<String>();
        labels.add("q15.Test button");
        labels.add("Press me");
        labels.add("Do you feel lucky?");
        labels.add("Try me");
        labels.add("q15.Test me");
        Random random = new Random();
        int randomInteger = random.nextInt(labels.size());
        return labels.get(randomInteger);
    }

    public static void main(String[] args) {
        JFrame myApp = new Test();
        myApp.show();
    }
}