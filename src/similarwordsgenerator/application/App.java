package similarwordsgenerator.application;

import javafx.application.Application;
import javafx.stage.Stage;
import similarwordsgenerator.model.ProgramParameters;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private File userHomeProgram;
    private ProgramParameters parameters;
    private List<String> output = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        View view = new View();
        String mementoName = "memento";

        createLocationForFiles();
        loadParametersFromMemento(mementoName);
        view.init(primaryStage, parameters, userHomeProgram, mementoName, output);
    }

    private void loadParametersFromMemento(String mementoName) {
        boolean mementoExists = new File(userHomeProgram + File.separator + mementoName).exists();

        if (mementoExists) {
            Memento memento = Memento.loadMemento(userHomeProgram, mementoName);
            parameters = memento.getProgramParameters();
            output = memento.getOutput();
        } else {
            parameters = new ProgramParameters.Builder().build();
        }
    }

    private void createLocationForFiles() {
        String path = System.getProperty("user.home");
        try {
            path = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        new File(path).mkdir();
        userHomeProgram = new File(path);
    }
}
