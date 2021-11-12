module com.ufape.sistemasdistribuidos {
    requires transitive javafx.controls;
    requires javafx.fxml;
	requires org.apache.commons.lang3;
	requires poi;
	requires json.simple;
	requires transitive javafx.graphics;
	requires org.apache.httpcomponents.httpclient;
	requires org.apache.httpcomponents.httpcore;
	requires java.dotenv;
	
    opens com.ufape.sistemasdistribuidos to javafx.fxml;
    exports com.ufape.sistemasdistribuidos;
    exports com.ufape.sistemasdistribuidos.gui;
}
