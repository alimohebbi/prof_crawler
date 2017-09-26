package gui;



import config.Config;
import crawler.FacultyParser;
import crawler.HomePageParser;
import crawler.ProfilePageParser;
import entities.Professor;
import org.apache.log4j.Logger;
import storage.Storage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CrawlWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	final static Logger logger = Logger.getLogger(CrawlWindow.class);



	/**
	 * Create the dialog.
	 */
	String path="";
	JLabel facultySavePath;
	public JLabel lblog ;
	private JTextField textField;


	public CrawlWindow(JFrame frame) {
		super(frame);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblSetCrawledBook = new JLabel("Set Crawled Book Path");
		lblSetCrawledBook.setBounds(39, 78, 132, 14);

		JButton btnOpen = new JButton("Open");
		btnOpen.setBounds(181, 76, 64, 19);
		btnOpen.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0)
			{

				JFileChooser fc= new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					path=f.getAbsolutePath();
					facultySavePath.setText(path);

				}
			}
		});
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		facultySavePath = new JLabel("");
		facultySavePath.setBounds(261, 78, 194, 34);
		facultySavePath.setVerticalAlignment(SwingConstants.TOP);
		JLabel lblEnterSeedUrl = new JLabel("Enter Seed URL");
		lblEnterSeedUrl.setBounds(39, 50, 132, 14);

		textField = new JTextField();
		textField.setBounds(181, 47, 224, 20);
		textField.setColumns(10);

		JButton btnStart = new JButton("Start");
		btnStart.setBounds(39, 118, 75, 23);
		btnStart.setForeground(Color.BLACK);
		btnStart.setBackground(Color.ORANGE);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		lblog= new JLabel("");
		lblog.setBounds(39, 159, 331, 54);
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblog.setText("Waiting...");
				lblog.repaint();
//				Crawling crawler = new Crawling(textField.getText(), Integer.parseInt(textPane.getText()),facultySavePath.getText());
				String baseUrl = textField.getText();
				String savePath = facultySavePath.getText();
				try {
					starCrawling(baseUrl, savePath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				lblog.setText("Done!");

			}
		});
		lblog.setBackground(Color.WHITE);
		lblog.setVisible(true);
		contentPanel.setLayout(null);
		contentPanel.add(lblog);
		contentPanel.add(btnStart);
		contentPanel.add(lblSetCrawledBook);
		contentPanel.add(btnOpen);
		contentPanel.add(facultySavePath);

		contentPanel.add(lblEnterSeedUrl);
		contentPanel.add(textField);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");

				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	private void starCrawling(String baseUrl, String savePath) throws IOException {

//		ArrayList<Professor> professors = new ArrayList<>();

/*		for(int i = 0; i<14; i++) {
			FacultyParser facultyParser = new FacultyParser(baseUrl+ "/all?page="+i);
			ArrayList<Professor> p= new ArrayList<Professor>(facultyParser.getProfessors().values());

			professors.addAll(p);
		}*/
		FacultyParser facultyParser = new FacultyParser(baseUrl);


		ArrayList<Professor> professors = new ArrayList<Professor>(facultyParser.getProfessors().values());



		for (Professor professor: professors) {
			ProfilePageParser profilePageParser = new ProfilePageParser(professor);
			String interests = profilePageParser.getInterests();
			if(!Config.HOME_IN_PROFILE){
				HomePageParser homePageParser = new HomePageParser(professor);
				interests += " " + homePageParser.getInterests();
			}
			professor.setInterests(interests);
			if (interests.length()< 200)
				logger.warn("Lack of interest: " + professor.getName() );
		}

		Storage.saveProfessors(professors, savePath);

	}
}
