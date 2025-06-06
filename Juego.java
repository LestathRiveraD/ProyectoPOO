public class Juego
{
    public int board[][];

    public Juego()
    {
        board = new int[3][3];
        clearBoard();
    }

    /*
     * Return value of -1 means game has not finished
     * Return value of 0 means game ended in a draw
     * Return value of 1 means player 1 won
     * Return value of 2 mean player 2 won
    */ 
    public int getGameState() {
        for (int row[] : board) // Check board rows for winner
        {
            if (row[0] == 1 && row[1] == 1 && row[2] == 1)
            {
                return 1;
            }
            if (row[0] == 2 && row[1] == 2 && row[2] == 2)
            {
                return 2;
            }
        }

        for (int i = 0; i < 3; i++) // Check columns rows for winner
        {
            if (board[0][i] == 1 && board[1][i] == 1 && board[2][i] == 1)
            {
                return 1;
            }
            if (board[0][i] == 2 && board[1][i] == 2 && board[2][i] == 2)
            {
                return 2;
            }
        }

        for (int i = 0; i < 2; i++) // Check diagonals for winner
        {
            if (board[0][0] == 1 && board[1][1] == 1 && board[2][2] == 1) {
                return 1;
            }
            if (board[2][0] == 1 && board[1][1] == 1 && board[0][2] == 1) {
                return 1;
            }
            if (board[0][0] == 2 && board[1][1] == 2 && board[2][2] == 2) {
                return 2;
            }
            if (board[2][0] == 2 && board[1][1] == 2 && board[0][2] == 2) {
                return 2;
            }
        }

        for (int i = 0; i < 3; i++) // If no winner and there are empty tiles, game has not finished
        {
            for (int j = 0; j < 3; j++)
            {
                if (board[i][j] == 0) 
                {
                    return -1;
                }
            }
        }

        return 0; // If game is finished and there is no winner, then draw
    }

    public void updateBoard(int col, int row, int player)
    {
        board[col][row] = player;
    }

    public void clearBoard()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                board[i][j] = 0;
            }
        }
    }

    public void printBoard()
    {
        for (int vec[] : board)
        {
            for (int i : vec)
            {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}
