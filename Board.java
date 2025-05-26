public interface Board
{
    void fillSquare(int arr[]);
    int[] getChange();
    void setMy_turn(boolean turn);
    boolean getMy_turn();
    void setChange(int[] change);
    void gameover(String message);
    String getStatus();
    void reinicio(Boolean p1);
    boolean isConnected();
}
