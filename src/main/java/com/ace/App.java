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
            // Remove non-digit characters from the card number
            String sanitizedCardNumber = cardNumber.replaceAll("\\D", "");

            // Check if the card number has a valid length
            if (sanitizedCardNumber.length() < 12 || sanitizedCardNumber.length() > 19) {
                return false;
            }

            // Apply the Luhn algorithm to validate the card number
            int sum = 0;
            boolean alternate = false;

            for (int i = sanitizedCardNumber.length() - 1; i >= 0; i--) {
                int digit = Character.getNumericValue(sanitizedCardNumber.charAt(i));

                if (alternate) {
                    digit *= 2;
                    if (digit > 9) {
                        digit = (digit % 10) + 1;
                    }
                }

                sum += digit;
                alternate = !alternate;
            }

            return sum % 10 == 0;
        }

        private String getCardType(String cardNumber) {
            // Perform your card type detection logic here
            // You can use card number lookup services or additional validation algorithms
            // Return the card type (e.g., "Visa", "Mastercard", "American Express") or "Unknown"

            // Dummy implementation:
            if (cardNumber.startsWith("4")) {
                return "Visa";
            } else if (cardNumber.startsWith("5")) {
                return "Mastercard";
            } else if (cardNumber.startsWith("37") || cardNumber.startsWith("34")) {
                return "American Express";
            } else {
                return "Unknown";
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
