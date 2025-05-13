import java.util.Scanner;
import java.util.Random;

/**
 * 簡単な冒険ゲームのデモプログラム
 * クラス変数とメソッドの使用例を示しています
 */
public class AdventureGame {
    // クラス変数 - すべてのインスタンスで共有される
    private static final Random random = new Random();
    private static int totalGamesPlayed = 0;
    private static int totalTreasuresFound = 0;

    // インスタンス変数
    private String playerName;
    private int health;
    private int gold;
    private int level;
    private boolean hasKey;
    private Scanner scanner;
    private static final int GOAL_GOLD = 100; // クリア条件のゴールド量

    // コンストラクタ
    public AdventureGame(String name) {
        this.playerName = name;
        this.health = 100;
        this.gold = 0;
        this.level = 1;
        this.hasKey = false;
        this.scanner = new Scanner(System.in);
        totalGamesPlayed++; // クラス変数をインクリメント
    }

    // メインメソッド - プログラムのエントリーポイント
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== 簡単な冒険ゲーム ===");
        System.out.print("あなたの名前を入力してください: ");
        String playerName = scanner.nextLine();

        AdventureGame game = new AdventureGame(playerName);
        game.start();

        System.out.println("\n=== ゲーム統計 ===");
        System.out.println("プレイされたゲーム数: " + totalGamesPlayed);
        System.out.println("見つかった宝の総数: " + totalTreasuresFound);

        scanner.close();
    }

    // ゲームを開始するメソッド
    public void start() {
        System.out.println("\nようこそ、" + playerName + "！冒険を始めましょう。");
        System.out.println(GOAL_GOLD + "ゴールド集めるとゲームクリアです！");
        printStatus();

        boolean playing = true;
        while (playing && health > 0) {
            if (gold >= GOAL_GOLD) {
                System.out.println("\n祝福！" + playerName + "は" + GOAL_GOLD + "ゴールド以上を集め、冒険を成功させました！");
                System.out.println("ゲームクリアおめでとうございます！");
                break;
            }

            System.out.println("\n何をしますか？");
            System.out.println("1. 探索する");
            System.out.println("2. 休息する");
            System.out.println("3. ステータスを確認する");
            System.out.println("4. 終了する");

            int choice = getIntInput("選択してください (1-4): ", 1, 4);

            switch (choice) {
                case 1:
                    explore();
                    break;
                case 2:
                    rest();
                    break;
                case 3:
                    printStatus();
                    break;
                case 4:
                    playing = false;
                    System.out.println("冒険を終了します。さようなら、" + playerName + "！");
                    break;
            }

            if (health <= 0) {
                System.out.println("あなたは倒れてしまいました！ゲームオーバー...");
            }
        }
    }

    // 探索するメソッド - ランダムイベントが発生
    private void explore() {
        System.out.println("\n" + playerName + "は新しい場所を探索しています...");

        // 体力を少し減らす
        health -= 10;
        System.out.println("探索で疲れました。体力が10減少しました。");

        // ランダムイベントを生成
        int event = (int)(Math.random() * 5);

        switch (event) {
            case 0:
                findTreasure();
                break;
            case 1:
                fightMonster();
                break;
            case 2:
                findKey();
                break;
            case 3:
                findHealingPotion();
                break;
            case 4:
                findNothing();
                break;
        }
    }

    // 休息するメソッド - 体力回復
    private void rest() {
        System.out.println("\n" + playerName + "は休憩をとっています...");
        int recoveredHealth = (int)(Math.random() * 16) + 10; // 10〜25の範囲で回復
        int oldHealth = health;
        health += recoveredHealth;
        if (health > 100) {
            recoveredHealth = 100 - oldHealth; // 実際に回復した量を計算
            health = 100; // 体力の上限は100
        }

        System.out.println("休憩で体力が" + recoveredHealth + "回復しました！");
    }

    // 宝を見つけるメソッド
    private void findTreasure() {
        int treasureValue = (int)(Math.random() * 11) + 5; // 5〜15の範囲
        gold += treasureValue;
        totalTreasuresFound++; // クラス変数をインクリメント

        System.out.println("宝箱を見つけました！" + treasureValue + "ゴールドを獲得しました。");
        System.out.println("目標まであと" + Math.max(0, GOAL_GOLD - gold) + "ゴールド");

        if (hasKey) {
            System.out.println("あなたは鍵を持っています。宝箱を開けますか？ (y/n)");
            String answer = scanner.nextLine().toLowerCase();

            if (answer.equals("y")) {
                int bonusGold = (int)(Math.random() * 21) + 10; // 10〜30の範囲
                gold += bonusGold;
                hasKey = false; // 鍵を使用

                System.out.println("宝箱を開けました！追加で" + bonusGold + "ゴールドを獲得しました。");
            }
        }
    }

    // モンスターと戦うメソッド
    private void fightMonster() {
        System.out.println("モンスターに遭遇しました！");

        int monsterPower = (int)(Math.random() * 21) + 5 * level; // レベルに応じた強さ
        System.out.println("モンスターの強さ: " + monsterPower);

        System.out.println("1. 戦う");
        System.out.println("2. 逃げる");

        int choice = getIntInput("選択してください (1-2): ", 1, 2);

        if (choice == 1) {
            // 戦う
            int playerPower = (int)(Math.random() * 31) + 10 * level;
            System.out.println("あなたの強さ: " + playerPower);

            if (playerPower >= monsterPower) {
                // 勝利
                int rewardGold = (int)(Math.random() * 16) + 5 * level;
                gold += rewardGold;
                level++;

                System.out.println("モンスターに勝利しました！");
                System.out.println(rewardGold + "ゴールドを獲得し、レベルが" + level + "に上がりました！");
            } else {
                // 敗北
                int damage = (int)(Math.random() * 31) + 10;
                health -= damage;

                System.out.println("モンスターに敗北しました...");
                System.out.println("体力が" + damage + "減少しました。");
            }
        } else {
            // 逃げる
            int escapeDamage = (int)(Math.random() * 16) + 5;
            health -= escapeDamage;
            
            System.out.println("モンスターから逃げました！");
            System.out.println("逃げる際に" + escapeDamage + "のダメージを受けました。");
        }
    }

    // 鍵を見つけるメソッド
    private void findKey() {
        if (!hasKey) {
            hasKey = true;
            System.out.println("宝箱の鍵を見つけました！これで宝箱を開けられます。");
        } else {
            System.out.println("また鍵を見つけましたが、すでに持っています。");
            findNothing();
        }
    }

    // 回復ポーションを見つけるメソッド
    private void findHealingPotion() {
        int healAmount = (int)(Math.random() * 21) + 10;
        health += healAmount;
        health = Math.min(health, 100);

        System.out.println("回復ポーションを見つけました！体力が" + healAmount + "回復しました。");
    }

    // 何も見つからないメソッド
    private void findNothing() {
        System.out.println("この場所には特に何もありませんでした...");
    }

    // ステータスを表示するメソッド
    private void printStatus() {
        System.out.println("\n=== " + playerName + "のステータス ===");
        System.out.println("体力: " + health);
        System.out.println("ゴールド: " + gold + " / " + GOAL_GOLD);
        System.out.println("レベル: " + level);
        System.out.println("鍵: " + (hasKey ? "あり" : "なし"));
    }
    
    // 整数入力を取得するためのヘルパーメソッド
    private int getIntInput(String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("入力は" + min + "から" + max + "の範囲で入力してください。");
                }
            } catch (NumberFormatException e) {
                System.out.println("有効な数字を入力してください。");
            }
        }
    }
} 