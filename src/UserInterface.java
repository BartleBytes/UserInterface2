import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.io.*;

public class UserInterface extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private Random random = new Random();
    private Font dateTimeFont;

    public UserInterface() {
        setTitle("User Interface Demo");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create the file menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Create the color menu
        JMenu colorMenu = new JMenu("Color");
        JMenuItem changeColorItem = new JMenuItem("Change Background Color");

        // Create the date menu
        JMenu dateMenu = new JMenu("Date");
        JMenuItem printDateTimeItem = new JMenuItem("Print Date/Time");
        
        // Create the text menu
        JMenu textMenu = new JMenu("Text");
        JMenuItem writeTextFileItem = new JMenuItem("Write text to File");

        // Add action listeners to menu items
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        changeColorItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeBackgroundColor();
            }
        });

        printDateTimeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateDateTimeFont();
            }
        });
        writeTextFileItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		openTextEntryDialog();
        	}
        });

        // Add menu items to menus
        fileMenu.add(exitItem);
        colorMenu.add(changeColorItem);
        dateMenu.add(printDateTimeItem);
        textMenu.add(writeTextFileItem);

        // Add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(colorMenu);
        menuBar.add(dateMenu);
        menuBar.add(textMenu);

        // Set the menu bar
        setJMenuBar(menuBar);

        // Create the panel
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                printDateTime(g); // Print date and time on the panel
            }
        };
        add(panel);

        setVisible(true);

        // Initialize font for date and time
        updateDateTimeFont();
    }

    // Method to change background color to a random color
    private void changeBackgroundColor() {
        int red = random.nextInt(256); // Generates a random value between 0 and 255 for red component
        int green = random.nextInt(256); // Generates a random value between 0 and 255 for green component
        int blue = random.nextInt(256); // Generates a random value between 0 and 255 for blue component

        Color color = new Color(red, green, blue); // Creates a Color object with the random RGB values

        panel.setBackground(color); // Set panel's background color to the random color
    }

    // Method to update font for date and time
    private void updateDateTimeFont() {
        Font font = getRandomFont();
        dateTimeFont = font.deriveFont(Font.PLAIN, 14); // Set the font size to 14
        panel.repaint(); // Repaint the panel to update the font
    }

    // Method to print date and time
    private void printDateTime(Graphics g) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String dateTime = dateFormat.format(date);
        
        FontMetrics fm = g.getFontMetrics(dateTimeFont);
        int stringWidth = fm.stringWidth(dateTime);
        int ascent = fm.getAscent();

        int x = (panel.getWidth() - stringWidth) / 2; // Center horizontally
        int y = ascent; // At the top

        g.setFont(dateTimeFont);
        g.setColor(Color.BLACK); // Set text color to black
        g.drawString(dateTime, x, y); // Draw the date and time at coordinates (50, 100)
    }

    // Method to generate a random font
    private Font getRandomFont() {
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        int randomIndex = random.nextInt(fontNames.length);
        int randomStyle = random.nextInt(4); // random style: plain, bold, italic, or bold italic
        int randomSize = random.nextInt(20) + 10; // random font size between 10 and 30

        return new Font(fontNames[randomIndex], randomStyle, randomSize);
    }
    
    // Method to open a text entry dialog
    private void openTextEntryDialog() {
        JTextArea textArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        int option = JOptionPane.showConfirmDialog(this, panel, "Enter Text", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String text = textArea.getText();
            if (!text.isEmpty()) {
                writeTextToFile(text);
            }
        }
    }

    // Method to write text to file
    private void writeTextToFile(String text) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("log.txt"))) {
            writer.println(text);
            JOptionPane.showMessageDialog(this, "Text written to file successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error writing to file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new UserInterface();
    }
}


