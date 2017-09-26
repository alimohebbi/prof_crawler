package gui;


import entities.Professor;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class SearchWindow extends JFrame {

	private JPanel contentPane;

	private JTextArea textAria_description;

	JList list;
	DefaultListModel listModel = new DefaultListModel();
	Initial init;
	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public SearchWindow( Initial init) {
		this.init = init;
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 688, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setVisible(true);
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);



		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setBounds(22, 45, 67, 14);
		panel.add(lblAuthor);

		JLabel lblBookTitle = new JLabel("Book Title");
		lblBookTitle.setBounds(22, 23, 67, 14);
		panel.add(lblBookTitle);



		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(22, 70, 67, 14);
		panel.add(lblDescription);

		JLabel lblReview = new JLabel("Review");
		lblReview.setBounds(22, 209, 67, 14);
		panel.add(lblReview);

		JButton btnSearch = new JButton("Search");
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				searchButton();
			}
		});
		btnSearch.setBounds(22, 369, 89, 23);
		panel.add(btnSearch);

		JScrollPane scroll = new JScrollPane();
		scroll.setSize(220, 271);
		scroll.setLocation(378, 42);

		panel.add(scroll);
		list = new JList(listModel);
		list.setBackground(SystemColor.info);
		scroll.setViewportView(list);
		list.setVisibleRowCount(10);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JButton btnOpenBook = new JButton("Open Book");
		btnOpenBook.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				openProfessorEntry();
			}
		});
		btnOpenBook.setBounds(378, 347, 119, 23);
		panel.add(btnOpenBook);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(104, 73, 183, 122);
		panel.add(scrollPane);

		textAria_description = new JTextArea();
		scrollPane.setViewportView(textAria_description);
		textAria_description.setLineWrap(true);
		textAria_description.setToolTipText("");
		textAria_description.setColumns(10);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(104, 209, 183, 124);
		panel.add(scrollPane_1);


	}

	void searchButton()
	{


		String description = textAria_description.getText();

		ScoreDoc[] hits ;
		ArrayList<Professor> result = null;
		try {
			hits = init.irSystem.search(description);
			result = init.irSystem.getProfessors(hits, init.professors);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

			listModel.clear();
		for (Professor professor : result) {
			listModel.addElement(professor.getName());
		}
		list.repaint();

	}
	void openProfessorEntry(){
		String name = list.getSelectedValue().toString();
		init.professors.forEach(v-> {
			if (v.getName().equals(name))
				new BookWindow(this, v);
		});



	}
}
