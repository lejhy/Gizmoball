package model;

import javafx.scene.input.KeyCode;
import physics.Angle;

import java.io.*;
import java.util.*;

public class FileIO {
    private String filePath = "";
    private BufferedReader bR;
    private BufferedWriter bW;
    private Scanner sC;
    private Model model;

    public FileIO(Model m){
        model = m;
    }

    public void setFilePath(String s) {
        filePath = s;
    }

    public String getFilePath() {
        return filePath;
    }

    public void saveToFile() {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "utf-8"))) {
            Map<StandardGizmo, String> gizmos = new HashMap<>();
            // Gizmos
            int id = 0;
            for (StandardGizmo gizmo : model.getGizmos()) {
                String output = gizmo.toString(id);
                String identifier = output.split(" ")[1];
                writer.write(output);
                writer.write("\n");
                gizmos.put(gizmo, identifier);
                Angle rotation = new Angle(gizmo.getRotation().radians());
                while (rotation.compareTo(Angle.ZERO) > 0) {
                    writer.write("Rotate " + identifier);
                    writer.write("\n");
                    rotation = rotation.minus(Angle.DEG_90);
                }
                id++;
            }
            // Gizmo connects
            for (StandardGizmo gizmo : model.getGizmos()) {
                String identifier = gizmos.get(gizmo);
                for (StandardGizmo trigger : gizmo.getTriggers()) {
                    writer.write("Connect " + identifier + " " + gizmos.get(trigger));
                    writer.write("\n");
                }
            }
            // Key down connects
            Map<KeyCode, Set<StandardGizmo>> keyDowns = model.getKeyDownTriggers();
            for (KeyCode key : keyDowns.keySet()) {
                Set<StandardGizmo> currentKeyGizmos = keyDowns.get(key);
                for (StandardGizmo gizmo : currentKeyGizmos) {
                    writer.write("KeyConnect key " + key.getName() + " down " + gizmos.get(gizmo));
                    writer.write("\n");
                }
            }
            // Key up connects
            Map<KeyCode, Set<StandardGizmo>> keyUps = model.getKeyUpTriggers();
            for (KeyCode key : keyUps.keySet()) {
                Set<StandardGizmo> currentKeyGizmos = keyUps.get(key);
                for (StandardGizmo gizmo : currentKeyGizmos) {
                    writer.write("KeyConnect key " + key.getName() + " up " + gizmos.get(gizmo));
                    writer.write("\n");
                }
            }
            // Balls
            for (Ball ball : model.getBalls()) {
                writer.write(ball.toString());
                writer.write("\n");
            }
            // Gravity and Friction
            writer.write("Gravity "+model.getGravityForce(1));
            writer.write("\n");
            writer.write("Friction "+model.getFrictionMU(1)+" "+model.getFrictionMU(2));
            writer.write("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Model loadFromFile(){

        try {
            FileReader fr = new FileReader(filePath);
            List<String> fileIn = new ArrayList<>();
            bR = new BufferedReader(fr);
            String line = bR.readLine();

            while(line != null){
                if(!line.equals("\n")){
                    fileIn.add(line);
                }
                System.out.println(line);
                line = bR.readLine();
            }

            model.clear();
            Map<String, StandardGizmo> gizmos = new HashMap<String, StandardGizmo>();

            for(String s : fileIn){
                String[] tokens = s.split(" ");
                StandardGizmo gizmo;
                switch(tokens[0]) {
                    case "Triangle":
                        if(!checkTokens(tokens[0], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3])))
                            break;
                        gizmo = new TriangularBumper(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                        gizmos.put(tokens[1], gizmo);
                        break;

                    case "Square":
                        if(!checkTokens(tokens[0], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3])))
                            break;
                        gizmo = new SquareBumper(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                        gizmos.put(tokens[1], gizmo);
                        break;

                    case "Circle":
                        if(!checkTokens(tokens[0], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3])))
                            break;
                        gizmo = new CircularBumper(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                        gizmos.put(tokens[1], gizmo);
                        break;

                    case "LeftFlipper":
                        if(!checkTokens(tokens[0], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3])))
                            break;
                        gizmo = new LeftFlipper(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                        gizmos.put(tokens[1], gizmo);
                        break;

                    case "RightFlipper":
                        if(!checkTokens(tokens[0], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3])))
                            break;
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
                            model.addKeyDown(KeyCode.getKeyCode(tokens[2]), gizmos.get(tokens[4]));
                        } else if (tokens[3].equals("up")) {
                            model.addKeyUp(KeyCode.getKeyCode(tokens[2]), gizmos.get(tokens[4]));
                        } else {
                            throw new RuntimeException("Corrupted Save File");
                        }
                        break;

                    case "Connect":
                        gizmos.get(tokens[1]).addGizmoTrigger(gizmos.get(tokens[2]));
                        break;

                    case "Rotate":
                        gizmos.get(tokens[1]).rotate(Angle.DEG_90);
                        break;

                    case "Gravity":
                        model.setGravityForce(Double.parseDouble(tokens[1]));
                        break;

                    case "Friction":
                        model.setFrictionMU(Double.parseDouble(tokens[1]), 1);
                        model.setFrictionMU(Double.parseDouble(tokens[2]), 2);
                        break;

                    default:
                        if(tokens[0].isEmpty()) {
                            break;
                        } else {
                            System.out.println(tokens[0] + " is not a gizmo.");
                            break;
                        }
                }
            }

            for(StandardGizmo gizmo : gizmos.values()) {
                model.addGizmo(gizmo);
            }
        } catch (RuntimeException e) {
            System.out.println("ERROR: File may be too large or invalid.");
            System.out.println(e);
        } catch (IOException e) {
            System.out.println("ERROR: Invalid file.");
        }

        return model;

    }

    private boolean checkTokens(String gizmo, int xcord, int ycord) {
        if(xcord < 0 || xcord > 19) {
            System.out.println(gizmo + " with X coordinate " + xcord + " is invalid.");
            return false;
        } else if(ycord < 0 || ycord > 19) {
            System.out.println(gizmo + " with Y coordinate " + ycord + " is invalid.");
            return false;
        } else {
            return true;
        }
    }

}
