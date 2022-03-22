import javax.naming.NamingException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) throws NamingException, RemoteException {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);

            GameImpl g = new GameImpl();
            Naming.rebind("rmi://localhost/game", g);
            System.out.println("Server is ready.");
            g.getF().printField();
        }catch (Exception e) {
            System.out.println("Server exeption: " + e);
        }

    }
}

interface Game extends Remote {
    int[] getScore() throws RemoteException;
    char[][] getField() throws RemoteException;
    boolean endGame() throws RemoteException;
    boolean pick(int x, int y) throws RemoteException;
    int getPlayerNum() throws RemoteException;
    void printField() throws RemoteException;
    int getCurrentPlayer() throws RemoteException;
    void endOfTurn() throws RemoteException;
}

class GameImpl extends UnicastRemoteObject implements Game {
    private Field field;
    private int score1;
    private int score2;
    private int currentPlayer;

    public GameImpl() throws RemoteException {
        field = new Field();

        currentPlayer = 1;
        score1 = 0;
        score2 = 0;
    }

    public Field getF() {
        return field;
    }


    @Override
    public int[] getScore() {
        int[] score;
        score = new int[2];
        score[0] = score1;
        score[1] = score2;
        return score;
    }

    @Override
    public /*Field*/char[][] getField() {
        return field.getValues();
    }

    @Override
    public boolean endGame() throws RemoteException {
        if (score1 < 5 && score2 < 5)
            return false;
        else if (score1 >= 5)
            return true;
        else if (score2 >= 5)
            return true;
        return false;
    }

    @Override
    public boolean pick(int x, int y) {
        boolean flag = false;
        if(field.pickPosition(x, y)){
            flag = true;
            if (field.checkByPosition(x - 1, y)) {
                if (currentPlayer == 1) {
                    score1++;
                    field.setValue(x - 1, y, '1');
                }
                else {
                    score2++;
                    field.setValue(x - 1, y, '2');
                }
                //flag = true;
            }
            if (field.checkByPosition(x + 1, y)) {
                if (currentPlayer == 1) {
                    score1++;
                    field.setValue(x + 1, y, '1');
                }
                else {
                    score2++;
                    field.setValue(x + 1, y, '2');
                }
                //flag = true;
            }
            if (field.checkByPosition(x, y - 1)) {
                if (currentPlayer == 1) {
                    score1++;
                    field.setValue(x, y - 1, '1');
                }
                else {
                    score2++;
                    field.setValue(x, y - 1, '2');
                }
                //flag = true;
            }
            if (field.checkByPosition(x, y + 1)) {
                if (currentPlayer == 1) {
                    score1++;
                    field.setValue(x, y + 1, '1');
                }
                else {
                    score2++;
                    field.setValue(x, y + 1, '2');
                }
                //flag = true;
            }
        }
        return flag;
    }

    @Override
    public int getPlayerNum() throws RemoteException {
        int res = currentPlayer;
        if(currentPlayer == 1) {
            currentPlayer++;
        } else {
            currentPlayer--;
        }
        return res;
    }

    @Override
    public void printField() {
        field.printField();
    }

    @Override
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public void endOfTurn() {
        if(currentPlayer == 1) {
            currentPlayer++;
        } else {
            currentPlayer--;
        }
    }
}

