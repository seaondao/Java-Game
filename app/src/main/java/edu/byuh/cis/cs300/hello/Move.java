package edu.byuh.cis.cs300.hello;

public class Move {
    private Cell before;
    private Cell destination;

    public Move(Cell before, Cell after){
        this.before = before;
        this.destination = after;
    }

}
