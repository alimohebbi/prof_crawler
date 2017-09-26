package gui;



import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {

	private JFrame frame;
	Initial init;
	Main main;
	JButton btnSearch;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
		main = this;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		//		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnCrawling = new JButton("Crawling");
		btnCrawling.setBounds(39, 98, 107, 54);
		btnCrawling.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				new CrawlWindow(frame);
			}
		});
		


		JButton btnIndexing = new JButton("Indexing");
		btnIndexing.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				IndexWindow iw= new IndexWindow(frame,main);
				
			}
		});
		btnIndexing.setBounds(164, 98, 101, 54);

		btnSearch = new JButton("Search");
		btnSearch.setEnabled(false);
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new SearchWindow(init);
			}
		});
		btnSearch.setBounds(283, 98, 107, 54);
		panel.setLayout(null);
		panel.add(btnCrawling);
		panel.add(btnIndexing);
		panel.add(btnSearch);
	}

}
