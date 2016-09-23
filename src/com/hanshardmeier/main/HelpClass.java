package com.hanshardmeier.main;

import javax.swing.JOptionPane;

public class HelpClass {

    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
	
}
