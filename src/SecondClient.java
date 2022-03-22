import com.sun.jdi.IntegerValue;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingException;
import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class SecondClient {
    public static void main(String[] args) throws NamingException, IOException, NotBoundException, InterruptedException {

        int playerNum;
        int x, y;

        Game g = (Game) Naming.lookup("rmi://localhost/game");
        playerNum = g.getPlayerNum();
        System.out.println("You're player № " + playerNum);

        while(true) {
            Scanner reader = new Scanner(System.in);
            System.out.println("Wait for your turn");
            while (g.getCurrentPlayer() != playerNum)
                TimeUnit.SECONDS.sleep(5);
            Field clientField = new Field(g.getField());
            clientField.printField();
            if (g.endGame())
                break;
            System.out.println("Your turn");
            System.out.println("Pick position");
            System.out.println("x:");
            x = Integer.valueOf(reader.nextLine());
            System.out.println("y:");
            y = Integer.valueOf(reader.nextLine());
            if(g.pick(x, y)){
                g.endOfTurn();
            } else while (true){
                Scanner reader2 = new Scanner(System.in);
                System.out.println("Invalid coordinates");
                System.out.println("Pick position");
                System.out.println("x:");
                x = Integer.valueOf(reader2.nextLine());
                System.out.println("y:");
                y = Integer.valueOf(reader2.nextLine());
                if (g.pick(x, y)){
                    g.endOfTurn();
                    break;
                }
            }
        }
        System.out.println("Your score: " + g.getScore()[playerNum - 1]);
        if (g.getScore()[playerNum - 1] >= 5)
            System.out.println("You win!");
        else
            System.out.println("You lose!");
        g.endOfTurn();
    }
}
