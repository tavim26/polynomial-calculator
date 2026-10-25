module io.github.tavim26.polynomial {
        requires javafx.controls;
        requires javafx.fxml;

        opens io.github.tavim26.polynomial.gui to javafx.fxml;
        exports io.github.tavim26.polynomial.gui;
        }