package theironyard;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable, ChangeListener {
    @FXML
    TextField addressBar;

    @FXML
    WebView webView;

    public void goToUrl () {
        String url = addressBar.getText();
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        webView.getEngine().load(url);
    }

    public void onKeyPressed (KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            goToUrl();
        }
    }

    public void goBack () {
        try { // Stops errors from occurring when going forward and back to far
            webView.getEngine().getHistory().go(-1);
        } catch (Exception e) {

        }
    }

    public void goForward () {
        try { // Stops errors from occurring when going forward and back to far
            webView.getEngine().getHistory().go(1);
        } catch (Exception e) {

        }
    }

    @Override // Create worker object
    public void initialize(URL location, ResourceBundle resources) {
        Worker worker = webView.getEngine().getLoadWorker();
        worker.stateProperty().addListener(this); // adds listener
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        addressBar.setText(webView.getEngine().getLocation());
    }
}