/*
 * Copyright 2014 Goblom.
 */
package codes.goblom.mcap.testing;

import codes.goblom.mcap.impl.MinecraftUser;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Goblom
 */
public class ImageTest extends JFrame implements ActionListener {

    public static void main(String[] args) {
        ImageTest img = new ImageTest();
        img.setSize(640, 75);
        img.setVisible(true);
        img.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        img.setResizable(false);
    }

    private JLabel name, size;
    private JTextField nameValue, sizeValue;
    private JCheckBox helmet, dim, onlyHead;
    private JPanel panel;
    private JButton update;

    private ImageTest() {
        super("Avatar Test");

        this.name = new JLabel("Player Name");
        this.nameValue = new JTextField(10);
        this.nameValue.setText("Goblom");
        this.size = new JLabel("Size");
        this.sizeValue = new JTextField(5);
        this.sizeValue.setText("100");
        this.helmet = new JCheckBox("With Helmet");
        this.helmet.setSelected(true);
        this.dim = new JCheckBox("3D");
        this.onlyHead = new JCheckBox("Only Head");
        this.update = new JButton("Preview");
        this.update.addActionListener(this);
        this.panel = new JPanel();

        setLayout(new BorderLayout());

        this.panel.add(name);
        this.panel.add(nameValue);
        this.panel.add(size);
        this.panel.add(sizeValue);
        this.panel.add(helmet);
        this.panel.add(dim);
        this.panel.add(onlyHead);
        this.panel.add(update);

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println(getSize().width);
        String name = nameValue.getText().trim();
        if (name == null || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty!");
            return;
        }

        int size = 100;
        boolean helmet = this.helmet.isSelected();

        try {
            size = Integer.parseInt(sizeValue.getText().trim());
        } catch (Exception e) {
            sizeValue.setText("100");
        }

        if (size <= 0) {
            JOptionPane.showMessageDialog(this, "Size must be greater then 0!");
            return;
        }
        
        MinecraftUser av = onlyHead.isSelected() ? Testing.getAvatar(name) : Testing.getSkin(name);
                      av.withHelmet(helmet);
                      av.withSize(size);
        Image image = dim.isSelected() ? av.get3d() : av.get2d();
        
        if (image == null) {
            JOptionPane.showMessageDialog(this, "Something happened when trying to load the preview.");
            return;
        }
        
        JLabel l = new JLabel(new ImageIcon(image));
        
        JOptionPane.showMessageDialog(this, l, "Avatar Preview", JOptionPane.PLAIN_MESSAGE, null);
    }
}
