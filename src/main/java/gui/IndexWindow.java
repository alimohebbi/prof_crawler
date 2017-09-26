package gui;

;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class IndexWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	JLabel label_book_dir;
	JLabel label_index_dir;
	JLabel lblState;
	Initial init=null;
	Main main;
	JCheckBox chckbx_page_rank;


	/**
	 * Create the dialog.
	 */
	public IndexWindow(JFrame frame, Main main) {
		super(frame);
		this.main= main;
		setVisible(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblSetDirectoryOf = new JLabel("Set Directory of Books");
			lblSetDirectoryOf.setBounds(20, 25, 139, 14);
			contentPanel.add(lblSetDirectoryOf);
		}
		{
			JButton button = new JButton("Open");
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0)
				{
					JFileChooser fc= new JFileChooser();
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnVal = fc.showOpenDialog(null);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File f = fc.getSelectedFile();
						f.getAbsolutePath();
						label_book_dir.setText(f.getAbsolutePath());

					}
				}
			});
			button.setBounds(169, 23, 72, 19);
			button.setFont(new Font("Tahoma", Font.PLAIN, 9));
			contentPanel.add(button);
		}
		{
			label_book_dir = new JLabel("");
			label_book_dir.setBounds(20, 50, 369, 14);
			label_book_dir.setVerticalAlignment(SwingConstants.TOP);
			contentPanel.add(label_book_dir);
		}

		JLabel lblSetDirectoryOf_1 = new JLabel("Set Directory of Index");
		lblSetDirectoryOf_1.setBounds(20, 87, 139, 14);
		contentPanel.add(lblSetDirectoryOf_1);

		JButton button = new JButton("Open");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				JFileChooser fc= new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					f.getAbsolutePath();
					label_index_dir.setText(f.getAbsolutePath());

				}
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 9));
		button.setBounds(169, 85, 72, 19);
		contentPanel.add(button);

		label_index_dir = new JLabel("index");
		label_index_dir.setVerticalAlignment(SwingConstants.TOP);
		label_index_dir.setBounds(20, 111, 369, 14);
		contentPanel.add(label_index_dir);

		JButton btnStartIndexing = new JButton("Start Indexing");
		btnStartIndexing.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblState.setText("Working...");
				lblState.repaint();
				initial_Ir(false);
				lblState.setText("Index Created");
			}
		});
		btnStartIndexing.setBounds(20, 136, 118, 23);
		contentPanel.add(btnStartIndexing);
		{
			JButton btnNewButton = new JButton("Load Index");
			btnNewButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					lblState.setText("Working...");
					initial_Ir(true);
					lblState.setText("Index Loaded");
				}
			});

			btnNewButton.setBounds(20, 170, 118, 23);
			contentPanel.add(btnNewButton);
		}


		{
			lblState = new JLabel("State");
			lblState.setBounds(20, 204, 118, 14);
			contentPanel.add(lblState);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						if(init!=null)
							IndexWindow.this.main.btnSearch.setEnabled(true);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				/********/
				cancelButton.setVisible(false);
				buttonPane.add(cancelButton);
			}
		}
	}

	void initial_Ir(boolean mode)
	{

		try {
			if(!mode)
				init= new Initial(label_book_dir.getText(), label_index_dir.getText(),mode);
			else
				init= new Initial(label_book_dir.getText(), label_index_dir.getText(),mode);
		} catch (IOException e) {
			e.printStackTrace();
		}

		main.init=init;
	}
}
