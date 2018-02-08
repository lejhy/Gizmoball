package model;

import javafx.scene.input.KeyCode;

import java.io.*;
import java.util.*;

public class FileIO {
    private BufferedReader bR;
    private BufferedWriter bW;
    private Scanner sC;
    private Model model;

    public FileIO(Model m){
        model = m;
    }

    public boolean saveToFile(String fileName){
        return false;
    }

    public Model loadFromFile(String fileName) throws IOException {

        List<String> fileIn = new ArrayList<>();
        FileReader fr = new FileReader(fileName);
        bR = new BufferedReader(fr);
        String line = bR.readLine();

        while(line != null){
            if(!line.equals("\n")){
                fileIn.add(line);
            }
            System.out.println(line);
            line = bR.readLine();
        }

        Map<String, StandardGizmo> gizmos = new HashMap<String, StandardGizmo>();

        for(String s : fileIn){
            String[] tokens = s.split(" ");
            StandardGizmo gizmo;
            switch(tokens[0]) {
                case "Triangle":
                    gizmo = new TriangularBumper(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                    gizmos.put(tokens[1], gizmo);
                    break;

                case "Square":
                    gizmo = new SquareBumper(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                    gizmos.put(tokens[1], gizmo);
                    break;

                case "Circle":
                    gizmo = new CircularBumper(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                    gizmos.put(tokens[1], gizmo);
                    break;

                case "LeftFlipper":
                    gizmo = new LeftFlipper(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                    gizmos.put(tokens[1], gizmo);
                    break;

                case "RightFlipper":
                    gizmo = new RightFlipper(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                    gizmos.put(tokens[1], gizmo);
                    break;

                case "Absorber":
                    gizmo = new Absorber(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]));
                    gizmos.put(tokens[1], gizmo);
                    break;

                case "Ball":
                    Ball ball = new Ball(Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]), Double.parseDouble(tokens[5]), 0.5);
                    model.addBall(ball);
                    break;

                case "KeyConnect":
                    if (tokens[3].equals("down")) {
                        model.addKeyDown(Integer.parseInt(tokens[2]), gizmos.get(tokens[4]));
                    } else if (tokens[3].equals("up")) {
                        model.addKeyUp(Integer.parseInt(tokens[2]), gizmos.get(tokens[4]));
                    } else {
                        throw new RuntimeException("Corrupted Save File");
                    }
                    break;

                case "Connect":
                    gizmos.get(tokens[1]).addGizmoTrigger(gizmos.get(tokens[2]));
                    break;

                case "Rotate":
                    gizmos.get(tokens[1]).rotate();
                    break;
            }
        }

        for(StandardGizmo gizmo : gizmos.values()) {
            model.addGizmo(gizmo);
        }

        return model;

    }

    private void parseFile(){

    }

}
