package view;

public class BuildMenu implements ViewMenu {
    private Board board;

    public BuildMenu(Board board){
        this.board = board;
    }

    public Board getBoard(){
        return board;
    }

    public boolean setBoard(Board board){
        this.board = board;
        return this.board.equals(board);
    }
}
