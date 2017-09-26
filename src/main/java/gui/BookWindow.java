package gui;


import entities.Professor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;

public class BookWindow extends JDialog {


	/**
	 * Create the dialog.
	 */
	JTextPane textPane_interest;
	JTextPane textPane_name;
	JTextPane textPane_homePage;
	JTextPane textPane_profile;

	Professor professor;
	public BookWindow(JFrame frame , Professor professor) {
		super(frame);
		setVisible(true);
		setBounds(100, 100, 747, 506);
		this.professor = professor;
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JScrollPane scroll = new JScrollPane();
		scroll.setSize(243, 330);
		scroll.setLocation(405, 64);
		panel.add(scroll);

		textPane_interest = new JTextPane();
		scroll.setViewportView(textPane_interest);

		JLabel lblReviews = new JLabel("Interests");
		lblReviews.setBounds(405, 39, 66, 14);
		panel.add(lblReviews);

		textPane_name = new JTextPane();
		textPane_name.setBounds(94, 39, 220, 20);
		panel.add(textPane_name);

		textPane_homePage = new JTextPane();
		textPane_homePage.setBounds(94, 132, 220, 48);

		panel.add(textPane_homePage);

		textPane_profile = new JTextPane();
		textPane_profile.setBounds(94, 70, 220, 48);
		panel.add(textPane_profile);


		JLabel lblTitle = new JLabel("Name");
		lblTitle.setBounds(10, 45, 46, 14);
		panel.add(lblTitle);

		JLabel lblAuthor = new JLabel("Home Page");
		lblAuthor.setBounds(10, 138, 46, 14);

		panel.add(lblAuthor);

		addButton(professor, panel);


		frame.setVisible(true);
		JLabel lblScore = new JLabel("Profile");
		lblScore.setBounds(10, 76, 46, 14);
		panel.add(lblScore);


		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(94, 374, 220, 38);
		panel.add(scrollPane);



		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(94, 191, 220, 172);
		panel.add(scrollPane_1);



		setTexts();
	}

	private void addButton(Professor professor, JPanel panel) {

		String homeUrl = professor.getHomepage();
		String profileUrl = professor.getProfileUrl();

		JButton buttonHome = getjButton(homeUrl);
		JButton buttonProfile = getjButton(profileUrl);

		buttonProfile.setBounds(10, 86, 80, 20);
		buttonHome.setBounds(10, 148,  80,20 );


		panel.add(buttonHome);
		panel.add(buttonProfile);


	}

	private JButton getjButton(String url) {
		JButton button = new JButton();
		button.setText("Go");
		button.setOpaque(false);
		button.setBackground(Color.WHITE);
		button.setToolTipText(url);
		if (url == null)
			url = "http://notfound.com";
		URI uri = URI.create(url);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				open(uri);
			}
		});
		button.setVisible(true);
		return button;
	}


	void setTexts(){
		textPane_homePage.setText(professor.getHomepage());

		textPane_profile.setText(professor.getProfileUrl());

		textPane_interest.setText(professor.getInterests());
		textPane_name.setText(professor.getName());


	}



	private static void open(URI uri) {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(uri);
			} catch (IOException e) { /* TODO: error handling */ }
		} else { /* TODO: error handling */ }
	}
}
