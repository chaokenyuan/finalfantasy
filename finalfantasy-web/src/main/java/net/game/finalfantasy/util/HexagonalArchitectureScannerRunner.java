package net.game.finalfantasy.util;

/**
 * 六角形架構掃描工具執行器 (Hexagonal Architecture Scanner Runner)
 * 
 * 這個類用於執行 HexagonalArchitectureScanner 掃描工具並生成開發提示。
 * 它提供了一個簡單的入口點，用於掃描專案目錄結構，分析六角形架構的各個層次，
 * 並生成開發提示，幫助開發人員理解專案架構並遵循架構規範進行開發。
 * 
 * 使用方法:
 * 1. 直接執行此類的 main 方法:
 *    java -cp <classpath> net.game.finalfantasy.util.HexagonalArchitectureScannerRunner
 * 
 * 2. 使用 Maven 執行:
 *    mvn compile exec:java -Dexec.mainClass="net.game.finalfantasy.util.HexagonalArchitectureScannerRunner"
 * 
 * 輸出:
 * 程式會在控制台輸出掃描結果，包括專案架構概述、各層次的文件列表、配置文件列表、
 * 開發新模組的步驟指南和命名約定等。
 */
public class HexagonalArchitectureScannerRunner {

    public static void main(String[] args) {
        System.out.println("開始掃描專案目錄結構...");

        HexagonalArchitectureScanner scanner = new HexagonalArchitectureScanner();
        scanner.scan();

        String prompt = scanner.generatePrompt();
        System.out.println("\n=== 生成的開發提示 ===\n");
        System.out.println(prompt);

        System.out.println("\n掃描完成。您可以使用上述提示作為開發新模組的參考。");
    }
}
