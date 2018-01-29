package view;

public class RunMenu implements ViewMenu {
    private Board board;

    public RunMenu(Board board){
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
