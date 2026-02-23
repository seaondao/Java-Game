package edu.byuh.cis.cs300.hello;

public class Move {
    public Cell before;
    public Cell destination;

    public Move(Cell before, Cell after){
        this.before = before;
        this.destination = after;
    }

}
