package com.ace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    private JFrame frame;
    private JTextField cardNumberField;
    private JLabel resultLabel;

    public App() {
        frame = new JFrame("Card Type Checker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberField = new JTextField(15);
        JButton checkButton = new JButton("Check");
        checkButton.addActionListener(new CheckButtonListener());

        resultLabel = new JLabel();

        panel.add(cardNumberLabel);
        panel.add(cardNumberField);
        panel.add(checkButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(resultLabel, BorderLayout.SOUTH);
    }

    public void show() {
        frame.setVisible(true);
    }

    private class CheckButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String cardNumber = cardNumberField.getText();
    
            if (isValidCardNumber(cardNumber)) {
                String cardType = getCardType(cardNumber);
                resultLabel.setText("Card type: " + cardType);
            } else {
                resultLabel.setText("Invalid card number");
            }
        }
    
        private boolean isValidCardNumber(String cardNumber) {
            // Validate the card number format and length here
            // You can use regular expressions or a validation algorithm to ensure it meets the required format
            // Return true if the card number is valid; otherwise, return false
    
            // Dummy implementation:
            // Check if the card number has 16 digits
            return cardNumber.matches("\\d{16}");
        }
    
        private String getCardType(String cardNumber) {
            // Perform your card type detection logic here
            // You can use regular expressions, validation algorithms, or a card number lookup service
            // Return "Credit Card" or "Debit Card" based on the card number
    
            // Dummy implementation:
            if (cardNumber.startsWith("4")) {
                return "Visa (Credit Card)";
            } else if (cardNumber.startsWith("5")) {
                return "Mastercard (Credit Card)";
            } else if (cardNumber.startsWith("6")) {
                return "Discover (Credit Card)";
            } else if (cardNumber.startsWith("34") || cardNumber.startsWith("37")) {
                return "American Express (Credit Card)";
            } else {
                return "Debit Card";
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                App checker = new App();
                checker.show();
            }
        });
    }
}
