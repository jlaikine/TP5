/**
 * 
 */
/**
 * @author Eleve
 *
 */
module TP3 {
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.base;
	requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml;
}
