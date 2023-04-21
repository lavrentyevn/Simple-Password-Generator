package com.company;

import com.apple.eawt.Application;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) throws Exception {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Name of Application");
        System.setProperty("apple.awt.application.name", "Password Generator");
        JFrame windowApplication = WindowApplication.getFrame();

    }
}

class WindowApplication {
    static JFrame getFrame() throws Exception{

        // adding dock image and name
        Application.getApplication().setDockIconImage(new ImageIcon("path_to_file/icon.png").getImage());

        // creating frame
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);

        jFrame.setTitle("Password Generator");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width / 2 - 250, dimension.height / 2 - 175, 500, 350);

        // background
        Color seafoam = new Color(159, 226, 191);

        // title
        JLabel title = new JLabel("Generate Your Password");
        title.setFont(new Font("Gill Sans", Font.PLAIN, 30));
        title.setForeground(Color.white);

        JPanel top = new JPanel(new BorderLayout());
        top.add(BorderLayout.LINE_START, title);
        top.setBackground(seafoam);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(BorderLayout.PAGE_START, top);
        JPanel left = new JPanel();

        // generate button
        JButton button = new JButton("Generate");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(seafoam);
        buttonPanel.add(button);
        buttonPanel.setMaximumSize(buttonPanel.getPreferredSize());
        // choosing length for password
        JPanel lengthPanel = new JPanel();
        lengthPanel.setBackground(seafoam);
        JLabel prelength = new JLabel("Choose Password Length: ");
        JTextField lengthChooser = new JTextField("10");

        lengthPanel.add(prelength);
        lengthPanel.add(lengthChooser);
        lengthPanel.setMaximumSize(lengthPanel.getPreferredSize());

        // checkboxes
        JPanel checkBoxesPanel = new JPanel();
        checkBoxesPanel.setBackground(seafoam);

        // lower checkbox
        JPanel checkboxPanel1 = new JPanel();
        checkboxPanel1.setBackground(seafoam);
        JLabel lowerText = new JLabel("Use lower letters? ");
        JCheckBox lowerChars = new JCheckBox();

        lowerChars.setSelected(true);

        checkboxPanel1.add(lowerText);
        checkboxPanel1.add(lowerChars);
        checkboxPanel1.setMaximumSize(checkboxPanel1.getPreferredSize());

        // upper checkbox
        JPanel checkboxPanel2 = new JPanel();
        checkboxPanel2.setBackground(seafoam);
        JLabel upperText = new JLabel("Use upper letters? ");
        JCheckBox upperChars = new JCheckBox();

        checkboxPanel2.add(upperText);
        checkboxPanel2.add(upperChars);
        checkboxPanel2.setMaximumSize(checkboxPanel2.getPreferredSize());


        // number checkbox
        JPanel checkboxPanel3 = new JPanel();
        checkboxPanel3.setBackground(seafoam);
        JLabel numberText = new JLabel("Use number letters? ");
        JCheckBox numberChars = new JCheckBox();

        checkboxPanel3.add(numberText);
        checkboxPanel3.add(numberChars);
        checkboxPanel3.setMaximumSize(checkboxPanel3.getPreferredSize());

        // special checkbox
        JPanel checkboxPanel4 = new JPanel();
        checkboxPanel4.setBackground(seafoam);
        JLabel specialText = new JLabel("Use special letters? ");
        JCheckBox specialChars = new JCheckBox();

        checkboxPanel4.add(specialText);
        checkboxPanel4.add(specialChars);
        checkboxPanel4.setMaximumSize(checkboxPanel4.getPreferredSize());

        // adding to checkbox panel
        checkboxPanel1.setAlignmentX(Component.LEFT_ALIGNMENT);
        checkboxPanel2.setAlignmentX(Component.LEFT_ALIGNMENT);
        checkboxPanel3.setAlignmentX(Component.LEFT_ALIGNMENT);
        checkboxPanel4.setAlignmentX(Component.LEFT_ALIGNMENT);

        checkBoxesPanel.setLayout(new BoxLayout(checkBoxesPanel, BoxLayout.Y_AXIS));
        checkBoxesPanel.add(checkboxPanel1);
        checkBoxesPanel.add(checkboxPanel2);
        checkBoxesPanel.add(checkboxPanel3);
        checkBoxesPanel.add(checkboxPanel4);
        checkBoxesPanel.setMaximumSize(checkBoxesPanel.getPreferredSize());

        // password fields
        JPanel passwordPanel = new JPanel();
        passwordPanel.setBackground(seafoam);
        JLabel prepassword = new JLabel("Your Password: ");
        JTextField password = new JTextField("password");

        password.setSize(password.getPreferredSize());

        passwordPanel.add(prepassword);
        passwordPanel.add(password);

        passwordPanel.setMaximumSize(passwordPanel.getPreferredSize());
        passwordPanel.revalidate();

        // copy to clipboard
        JPanel copyPanel = new JPanel();
        copyPanel.setBackground(seafoam);
        JButton copy = new JButton("Copy to clipboard");

        copy.addActionListener(actionEvent -> {
            StringSelection stringSelection = new StringSelection(password.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });

        copyPanel.setBackground(seafoam);
        copyPanel.add(copy);
        copyPanel.setMaximumSize(copyPanel.getPreferredSize());


        // adding panels to main panel
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        lengthPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        checkBoxesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        copyPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.add(buttonPanel);
        left.add(lengthPanel);
        left.add(checkBoxesPanel);
        left.add(passwordPanel);
        left.add(copyPanel);

        left.setBackground(seafoam);
        left.revalidate();

        // password generating
        AtomicReference<String> passwordGetter = new AtomicReference<>("");

        button.addActionListener(actionEvent -> {
            try {
                Integer.parseInt(lengthChooser.getText());

                if (Long.parseLong(lengthChooser.getText()) > 40 || Integer.parseInt(lengthChooser.getText()) <= 0)
                {
                    lengthChooser.setText("");
                }

                PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                        .setUseSpecial(specialChars.isSelected())
                        .setUseNumber(numberChars.isSelected())
                        .setUseUpper(upperChars.isSelected())
                        .setUseLower(lowerChars.isSelected())
                        .build();

                int length = Integer.parseInt(lengthChooser.getText());
                passwordGetter.set(passwordGenerator.generate(length));
                password.setText("");
                password.setText(String.valueOf(passwordGetter));
            }
            catch (Exception e) {

                lengthChooser.setText("10");
                password.setText("");
                password.setText("wrong length (must be between 1 and 40)");
            }

            password.setSize(password.getPreferredSize());
            passwordPanel.setMaximumSize(passwordPanel.getPreferredSize());
            left.revalidate();
        });

        // adding to frame
        contentPane.add(BorderLayout.CENTER, left);
        jFrame.setContentPane(contentPane);
        jFrame.getContentPane().setBackground(seafoam);

        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return jFrame;
    }
}

final class PasswordGenerator {
    private static String lowerLetters = "abcdefghijklmnopqrstuvwxyz";
    private static String upperLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String numberLetters = "0123456789";
    private static String specialLetters = "!@#$%&*()_+-=[]|,./?><";
    private static boolean useLower;
    private static boolean useUpper;
    private static boolean useNumber;
    private static boolean useSpecial;

    public PasswordGenerator(PasswordGeneratorBuilder passwordGeneratorBuilder) {
        this.useLower = passwordGeneratorBuilder.useLower;
        this.useUpper = passwordGeneratorBuilder.useUpper;
        this.useNumber = passwordGeneratorBuilder.useNumber;
        this.useSpecial = passwordGeneratorBuilder.useSpecial;
    }

    public static class PasswordGeneratorBuilder {
        private static boolean useLower = false;
        private static boolean useUpper = false;
        private static boolean useNumber = false;
        private static boolean useSpecial = false;

        public PasswordGeneratorBuilder setUseLower(boolean useLower) {
            PasswordGeneratorBuilder.useLower = useLower;
            return this;
        }

        public PasswordGeneratorBuilder setUseUpper(boolean useUpper) {
            PasswordGeneratorBuilder.useUpper = useUpper;
            return this;
        }

        public PasswordGeneratorBuilder setUseNumber(boolean useNumber) {
            PasswordGeneratorBuilder.useNumber = useNumber;
            return this;
        }

        public PasswordGeneratorBuilder setUseSpecial(boolean useSpecial) {
            PasswordGeneratorBuilder.useSpecial = useSpecial;
            return this;
        }
        public PasswordGenerator build() {
            return new PasswordGenerator(this);
        }
    }
    public String generate(int length) {
        StringBuilder password = new StringBuilder();
        Random random = new Random(System.nanoTime());

        List<String> allLetters = new ArrayList<>(4);
        if(useLower) {
            allLetters.add(lowerLetters);
        }
        if(useUpper) {
            allLetters.add(upperLetters);
        }
        if(useNumber) {
            allLetters.add(numberLetters);
        }
        if(useSpecial) {
            allLetters.add(specialLetters);
        }

        if(!useLower && !useUpper && !useNumber && !useSpecial) {
            return "no option chosen";
        }

        for (int i = 0; i < length; i++) {
            String chosenLetters = allLetters.get(random.nextInt(allLetters.size()));
            int position = random.nextInt(chosenLetters.length());
            password.append(chosenLetters.charAt(position));
        }
        return new String(password);
    }
}

