package edu.byuh.cis.cs300.hello;

public class Move {
    public Cell before;
    public Cell destination;

//Save the cell location of before and after.
    public Move(Cell before, Cell after){
        this.before = before;
        this.destination = after;
    }

}
