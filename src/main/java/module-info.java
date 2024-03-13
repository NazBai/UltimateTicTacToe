module com.example.ultimatetictactoe {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ultimatetictactoe to javafx.fxml;
    exports com.example.ultimatetictactoe;
}