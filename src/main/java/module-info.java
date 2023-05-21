module iessfranciscodelosrios.proyecto3.GestorAutocares {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.xml.bind;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.base;
	requires java.desktop;
	opens iessfranciscodelosrios.proyecto3.GestorAutocares.model.connection to java.xml.bind;
    opens iessfranciscodelosrios.proyecto3.GestorAutocares to javafx.fxml;
    exports iessfranciscodelosrios.proyecto3.GestorAutocares;
}
