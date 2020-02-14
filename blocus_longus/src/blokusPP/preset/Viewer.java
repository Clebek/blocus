package blokusPP.preset;

public interface Viewer {
    int turn();

    int getColor(int letter, int number);
    
    Status getStatus();
}
