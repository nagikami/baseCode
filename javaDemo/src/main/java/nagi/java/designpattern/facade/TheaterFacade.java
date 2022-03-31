package nagi.java.designpattern.facade;

public class TheaterFacade {
    private DVDPlayer dvdPlayer;
    private Popcorn popcorn;

    public TheaterFacade() {
        dvdPlayer = DVDPlayer.getInstance();
        popcorn = Popcorn.getInstance();
    }

    public void  ready() {
        dvdPlayer.open();
        popcorn.open();
    }

    public void end() {
        dvdPlayer.close();
        popcorn.close();
    }
}
