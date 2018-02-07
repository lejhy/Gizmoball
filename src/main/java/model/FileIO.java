package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        for(String s : fileIn){
            String[] tokens = s.split(" ");
            switch(tokens[0]) {
                case "Triangle":
                    TriangularBumper tb = new TriangularBumper(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), model);
                    break;

                case "Square":
                    SquareBumper sb = new SquareBumper(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), model);
                    break;

                case "Circle":
                    CircularBumper cb = new CircularBumper(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), model);
                    break;

                case "LeftFlipper":
                    LeftFlipper lf = new LeftFlipper(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), model);
                    break;

                case "RightFlipper":
                    RightFlipper rf = new RightFlipper(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), model);
                    break;

                case "Absorber":
                    break;

                case "ball":
                    Ball ball = new Ball(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), 0.5);
                    break;

                case "KeyConnect":
                    break;

                case "Connect":
                    break;

                case "Rotate":
                    break;
            }
        }

        return model;

    }

    private void parseFile(){

    }

}
