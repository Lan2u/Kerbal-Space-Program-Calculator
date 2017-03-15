package KerbalSpaceProgram;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Paul Lancaster on 02/08/2016
 * @author paul
 */
public class ksp extends Application{
    public TextField targetPeriapsisField;
    public TextField targetApoapsisField;
    public TextField controllingApoapsisField;
    public TextField controllingPeriapsisField;
    public TextField deltaVField;
    public TextField phaseAngleField;
    public SplitMenuButton targetParaUnitSelector;
    public SplitMenuButton targetApoUnitSelector;
    public SplitMenuButton controllingParaUnitSelector;
    public SplitMenuButton controllingApoUnitSelector;

    OrbitCalculator orbitCalculator = new OrbitCalculator();

    private static final double G = 6.674e-11;

    public void displaySGP(ActionEvent actionEvent) {
        System.out.println(Planet.KERBIN.standardGravitationalParameter);
    }
    // TODO: 02/08/2016 Learn more about using theses words to complete code faster eg (sout etc.)

    public enum Planet{
        KERBIN(5.2915793e22,600000), DUNA(4.5154812e21,320000), MUN(9.7600236e20,200000), MINMUS(2.6457897e19,60000);
        final double mass;
        final double radius;
        final double standardGravitationalParameter;
        Planet(double mass, double radius){
            this.mass = mass;
            this.radius = radius;
            this.standardGravitationalParameter = mass*G;
        }
    }

    public static void main(String[] args){
        launch(args);
    }
    @Override public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("resources/layout.fxml"));
        root.getStylesheets().add(getClass().getResource("resources/style.css").toExternalForm());
        primaryStage.setTitle("Kerbal Space Program Calculator");
        primaryStage.setScene(new Scene(root,600,230));
        primaryStage.show();
    }

    public void calculateIntercepts(ActionEvent actionEvent) {
        if(!controllingPeriapsisField.getText().isEmpty()&& !controllingApoapsisField.getText().isEmpty() && !targetPeriapsisField.getText().isEmpty() && !targetApoapsisField.getText().isEmpty()){
          //  double shipApoapsis = Double.parseDouble(controllingApoapsisField.getText());
            double shipPeriapsis = Double.parseDouble(controllingPeriapsisField.getText());
         //   double targetApoapsis = Double.parseDouble(targetApoapsisField.getText());
            double targetPeriapsis = Double.parseDouble(targetPeriapsisField.getText());

            //    if (controllingApoUnitSelector.getText().equalsIgnoreCase("km")){
            //       shipApoapsis = shipApoapsis * 1000;
            //     }
            //    if (targetApoUnitSelector.getText().equalsIgnoreCase("km")){
            //        targetApoapsis = targetApoapsis * 1000;
            //     }

            if (controllingParaUnitSelector.getText().equalsIgnoreCase("km")){
                shipPeriapsis = shipPeriapsis * 1000;
            }
            if (targetParaUnitSelector.getText().equalsIgnoreCase("km")){
                targetPeriapsis = targetPeriapsis * 1000;
            }

            double[] calculations = orbitCalculator.calculateIntercept(Planet.KERBIN, shipPeriapsis, targetPeriapsis);
            deltaVField.setText(String.valueOf(calculations[0]));
            phaseAngleField.setText(String.valueOf(calculations[1]));
            
        }else{
            if (controllingPeriapsisField.getText().isEmpty()){
                controllingPeriapsisField.setStyle("-fx-background-color: errorRed");
            }
            if (controllingApoapsisField.getText().isEmpty()){
                controllingApoapsisField.setStyle("-fx-background-color: errorRed");
            }
            if (targetPeriapsisField.getText().isEmpty()){
                targetPeriapsisField.setStyle("-fx-background-color: errorRed");
            }
            if (targetApoapsisField.getText().isEmpty()){
                targetApoapsisField.setStyle("-fx-background-color: errorRed");
            }
        }
    }
}
