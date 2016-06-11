package battleship;

import java.util.ArrayList;
import java.util.HashMap;

public class BattleShipRunner {
    private GameHelper helper = new GameHelper();
    private ArrayList<BattleShip> battleShipList = new ArrayList<BattleShip>();
    private int numOfGuesses = 0;

    private void setUpGame() {
        BattleShip one = new BattleShip();
        one.setName("Pets.com");
        BattleShip two = new BattleShip();
        two.setName("eToys.com");
        BattleShip three = new BattleShip();
        three.setName("Go2.com");
        battleShipList.add(one);
        battleShipList.add(two);
        battleShipList.add(three);
        for (BattleShip battleShip : battleShipList) {
            ArrayList<String> newLocation = helper.placeDotCom(3);
            battleShip.setLocationCells(newLocation);
        }
    }

    public int startPlaying() throws Exception {
        ArrayList<String> historyHits = new ArrayList<>();
        String result = null;
        while (!battleShipList.isEmpty()) {
            String compGuess = helper.getCompInput(result);
            while (historyHits.contains(compGuess)) {
                compGuess = helper.getCompInput(result);
            }
            historyHits.add(compGuess);
            result = checkUserGuess(compGuess);
            System.out.print(compGuess + ":" + result + ",");
        }
        int count = finishGame();
        return count;
    }

    public ArrayList<String> fireHistory = new ArrayList<>();

    public String checkUserGuess(String userGuess) throws Exception {
        numOfGuesses++;
        String result = "miss";
        if (fireHistory.contains(userGuess)) {
            throw new Exception("duplicate fire," + userGuess);
        }
        for (int x = 0; x < battleShipList.size(); x++) {
            result = battleShipList.get(x).checkYourself(userGuess);
            if (result.equals("hit")) {
                break;
            }
            if (result.equals("kill")) {
                battleShipList.remove(x);
            }
        }
        fireHistory.add(userGuess);
        return result;
    }

    private int finishGame() {
        return numOfGuesses;
    }


    public static void main(String[] args) throws Exception {
        HashMap<Integer, Integer> scores = new HashMap<Integer, Integer>();
        int total = 1000;
        for (int i = 0; i < total; i++) {
            System.out.println();
            System.out.println("Round " + i);
            BattleShipRunner game = new BattleShipRunner();
            game.setUpGame();
            int count = game.startPlaying();
            scores.put(count, (scores.containsKey(count)) ? (scores.get(count) + 1) : 1);
            System.out.println();
        }
        int cumulative = 0;
        for (int i = 0; i < 50; i++) {
            if (cumulative >= total / 2) {
                System.out.println("cumulative score is " + i);
                break;
            } else if (scores.get(i) != null) {
                cumulative += scores.get(i);
            }
        }
        System.out.println(scores);
    }
}