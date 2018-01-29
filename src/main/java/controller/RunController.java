package controller;

public class RunController {
    private Model model;
    private Board board;
    private RunMenu menu;

    public RunController(Model model, Board board, RunMenu menu){
        this.board = board;
        this.model = model;
        this.menu = menu;
    }
}
