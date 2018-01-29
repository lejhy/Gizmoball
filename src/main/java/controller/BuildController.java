package controller;

public class BuildController {
    private Model model;
    private Board board;
    private BuildMenu menu;

    public BuildController(Model model, Board board, BuildMenu menu){
        this.board = board;
        this.model = model;
        this.menu = menu;
    }
}
