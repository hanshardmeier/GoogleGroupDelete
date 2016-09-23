package com.hanshardmeier.main;

import java.awt.event.*;
import java.net.URI;
import java.awt.*;

public class MainFrame extends Frame implements ActionListener, WindowListener {
	/**
	* 
	*/
	private static final long serialVersionUID = 2926827057977317861L;

	private TextField txtRefreshToken; // Declare a TextField component
	private TextField txtAccessToken; // Declare a TextField component
	
	private Button btnDelete; // Declare a Button component
	private Button btnGet;
	
	public MainFrame() {
		setLayout(new GridLayout(4, 2,5,5));
		
		// Add Refresh Token line
		add(new Label("   Refresh Token: "));
		txtRefreshToken = new TextField("0", 50);
		txtRefreshToken.setEditable(true);
		//txtRefreshToken.setText("1/1RJXVxH2wW0gcOAeFbEM1u2xwfIKN6eWWViIrsCQjI8");
		add(txtRefreshToken);

		// Add Access Token line
		add(new Label("   Access Token: "));
		txtAccessToken = new TextField("0", 50);
		txtAccessToken.setEditable(true);
		//txtAccessToken.setText("ya29.Ci9nA7JDjDaLzi_OGzQ-6-PlE0fUa5D2_XIQXBHwJEacx6dfgYEE3_22Fb888biSlQ");
		add(txtAccessToken);
		
		// Add Action Button
		btnGet = new Button("Get Tokens");
		add(btnGet);
		btnGet.addActionListener(this);

		btnDelete = new Button("Delete Groups");
		add(btnDelete);
		btnDelete.addActionListener(this);

		// Set rest of window
		addWindowListener(this);
		setTitle("Google Group Deleter");
		setSize(600, 150);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == btnDelete) {
				GoogleConnection con = new GoogleConnection(txtRefreshToken.getText(), txtAccessToken.getText());
				int counter= con.deleteAllGroups();
				HelpClass.infoBox(counter + " groups deleted", "Success");
			} else if (e.getSource() == btnGet) {
				HelpClass.infoBox("Select and authorize 'Contacts v3' API","Get tokens");
				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().browse(new URI(Contants.TOKENS_URL));
				}else{
					HelpClass.infoBox("For the tokens visit: "+Contants.TOKENS_URL , "Could not open Browser.");
				}
			}
		} catch ( Exception e1) {
			HelpClass.infoBox(e1.getMessage(), "Exception");
			e1.printStackTrace();
		}

	}

	/* WindowEvent handlers */
	// Called back upon clicking close-window button
	@Override
	public void windowClosing(WindowEvent evt) {
		System.exit(0); // Terminate the program
	}

	@Override
	public void windowOpened(WindowEvent evt) {
	}

	@Override
	public void windowClosed(WindowEvent evt) {
	}

	@Override
	public void windowIconified(WindowEvent evt) {
	}

	@Override
	public void windowDeiconified(WindowEvent evt) {
	}

	@Override
	public void windowActivated(WindowEvent evt) {
	}

	@Override
	public void windowDeactivated(WindowEvent evt) {
	}
}
