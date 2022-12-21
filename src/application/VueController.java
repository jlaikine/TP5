package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Service.ConnectBDD;
import entite.Post;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class VueController implements Initializable{
	
	@FXML
	private Button Affiche;
	
	@FXML
	private TextArea text;
	
	@FXML
	private Label message;
	
	private ConnectBDD connectBDD;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.connectBDD = new ConnectBDD();
		connectBDD.ConnectionBDD();
	}
	
	public void ajout() {
		try {
			connectBDD.ajoutBDD();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			message.setText("Les données sont déjà insérées");
		}
	}
	
	public void affiche() {
		ArrayList<Post> ar = null;;
		try {
			ar = connectBDD.affichage();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			message.setText("Ajouter des données");
		}
		
		if (Affiche.getText().equals("AFFICHER")) {
			for (int i=0; i<ar.size(); i++) {
				text.setText(text.getText());
				text.setText(text.getText() + ar.get(i).getTitle() + " " + ar.get(i).getBody() + "\n");
			}
			Affiche.setText("MASQUER");
			message.setText("");
		}
		else {
			text.setText("");
			Affiche.setText("AFFICHER");
			message.setText("");
		}
	}
	
	public void init() throws IOException {
		ArrayList<Post> ar = ConnectBDD.getPost();
		
		for (int i=0; i<ar.size(); i++) {
			text.setText(text.getText());
			text.setText(text.getText() + ar.get(i).getTitle() + " " + ar.get(i).getBody() + "\n");
		}
		
	}
}