package com.jspotify.search;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jspotify.meta.SpotifyItem;

public class Testing {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame("Testing");
				JPanel content = new JPanel();
				content.setLayout(new FlowLayout());
				
				final JTextField searchField = new JTextField(25);
				final JTextField typeField = new JTextField(1);
				
				JButton searchButton = new JButton("Search");
				searchButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String query = searchField.getText();
						int type = Integer.parseInt(typeField.getText());
						Search search = new Search(type, query);
						
						for (SpotifyItem item : search.getResults()) {
							System.out.println(item);
						}
					}
				});
				
				content.add(searchField);
				content.add(typeField);
				content.add(searchButton);
				
				frame.add(content);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
	
	
}
