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
	requires java.sql;
	requires javafx.base;
	
    opens com.ufape.sistemasdistribuidos to javafx.fxml;
    opens com.ufape.sistemasdistribuidos.model to javafx.base;
    exports com.ufape.sistemasdistribuidos;
    exports com.ufape.sistemasdistribuidos.gui;
}
