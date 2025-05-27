import java.util.Arrays;
import java.util.Random;

public class DiceStatistics {
    public static void main(String... args) {
        int targetTrials = args.length > 0 ? Integer.parseInt(args[0]) : 1000;
        int[] trialCounts = {1, 10, 100, 1000, 10000, 100000};

        System.out.println("🎲 サイコロ統計分析");
        System.out.println("=".repeat(60));

        System.out.println("\n📊 変動係数の変化（試行回数別）:");
        displayCoefficientOfVariationGraph(trialCounts);

        System.out.println("\n🔍 詳細分析 - " + targetTrials + "回:");
        runDetailedAnalysis(targetTrials);
    }

    public static void displayCoefficientOfVariationGraph(int[] trialCounts) {
        double[] coefficients = new double[trialCounts.length];

        for (int i = 0; i < trialCounts.length; i++) {
            int trials = trialCounts[i];
            int[] diceResults = new int[6];
            Random random = new Random();

            for (int j = 0; j < trials; j++) {
                int roll = random.nextInt(6) + 1;
                diceResults[roll - 1]++;
            }

            double variance = calculateVariance(diceResults);
            double standardDeviation = Math.sqrt(variance);
            double expectedPerRoll = (double) trials / 6;
            coefficients[i] = (standardDeviation / expectedPerRoll) * 100;
        }

        double maxCoefficient = Arrays.stream(coefficients).max().orElse(100.0);

        for (int i = 0; i < trialCounts.length; i++) {
            String trialLabel = String.format("%6d回", trialCounts[i]);
            double normalizedValue = coefficients[i] / maxCoefficient;
            int barLength = (int) (normalizedValue * 50); // 最大50文字のバー
            String bar = "█".repeat(Math.max(0, barLength));

            System.out.printf("%s: %6.2f%% %s\n", 
                trialLabel, coefficients[i], bar);
        }

        System.out.println("\n💡 変動係数が小さいほど、出目が均等に分布していることを示します");
        System.out.println("   試行回数が増えるほど、大数の法則により変動係数は小さくなります");
    }

    public static void runDetailedAnalysis(int trials) {
        int[] diceResults = new int[6];
        Random random = new Random();

        System.out.println("🎲 サイコロを" + trials + "回振ります...");

        for (int i = 0; i < trials; i++) {
            int roll = random.nextInt(6) + 1;
            diceResults[roll - 1]++;
        }

        displayResults(diceResults, trials);
        analyzeWithArrays(diceResults);

        double variance = calculateVariance(diceResults);
        double standardDeviation = Math.sqrt(variance);
        System.out.println("\n📈 対数スケール表示:");
        displayResultsWithLogScale(diceResults, trials, standardDeviation);
    }

    public static double calculateVariance(int[] results) {
        double mean = Arrays.stream(results).average().orElse(0.0);
        double variance = 0.0;

        for (int result : results) {
            variance += Math.pow(result - mean, 2);
        }

        return variance / results.length;
    }

    public static void displayResultsWithLogScale(int[] results, int trials, double stdDev) {
        double expectedPerRoll = (double) trials / 6;

        System.out.printf("期待値: %.2f, 標準偏差: %.2f\n", expectedPerRoll, stdDev);

        for (int i = 0; i < results.length; i++) {
            double percentage = (double) results[i] / trials * 100;
            double logValue = results[i] > 0 ? Math.log10(results[i]) : 0;

            System.out.printf("出目%d: %4d回 (%.1f%%) log₁₀=%.2f %s\n", 
                i + 1, results[i], percentage, logValue, getLogBar(logValue));
        }

        double coefficientOfVariation = (stdDev / expectedPerRoll) * 100;
        System.out.printf("変動係数: %.2f%%\n", coefficientOfVariation);
    }

    public static void displayResults(int[] results, int... additionalInfo) {
        int total = additionalInfo.length > 0 ? additionalInfo[0] : Arrays.stream(results).sum();

        System.out.println("\n📊 詳細結果:");
        for (int i = 0; i < results.length; i++) {
            double percentage = (double) results[i] / total * 100;
            System.out.printf("出目%d: %d回 (%.1f%%) %s\n", 
                i + 1, results[i], percentage, getBar(percentage));
        }
    }

    public static void analyzeWithArrays(int[] results) {
        System.out.println("\n🔍 Arraysクラスを使った分析:");

        int[] sorted = Arrays.copyOf(results, results.length);
        Arrays.sort(sorted);

        System.out.println("最少出現回数: " + sorted[0]);
        System.out.println("最多出現回数: " + sorted[5]);
        System.out.println("ソート済み配列: " + Arrays.toString(sorted));

        boolean allEqual = Arrays.stream(results).allMatch(x -> x == results[0]);
        System.out.println("完全に均等?: " + (allEqual ? "はい" : "いいえ"));

        double expectedPerRoll = (double) Arrays.stream(results).sum() / 6;
        System.out.printf("期待値(1つの出目あたり): %.1f回\n", expectedPerRoll);

        double variance = calculateVariance(results);
        double standardDeviation = Math.sqrt(variance);
        System.out.printf("分散: %.2f\n", variance);
        System.out.printf("標準偏差: %.2f\n", standardDeviation);

        findMostBiased(results, expectedPerRoll);
    }
    
    public static void findMostBiased(int[] results, double expected) {
        double maxDeviation = 0;
        int mostBiasedRoll = 1;
        
        for (int i = 0; i < results.length; i++) {
            double deviation = Math.abs(results[i] - expected);
            if (deviation > maxDeviation) {
                maxDeviation = deviation;
                mostBiasedRoll = i + 1;
            }
        }
        
        System.out.printf("最も偏った出目: %d (期待値から%.1f回の差)\n", 
            mostBiasedRoll, maxDeviation);
    }
    
    private static String getBar(double percentage) {
        int bars = (int) (percentage / 2);
        return "█".repeat(Math.max(0, bars));
    }
    
    private static String getLogBar(double logValue) {
        int bars = (int) (logValue * 5);
        return "▓".repeat(Math.max(0, bars));
    }
} 