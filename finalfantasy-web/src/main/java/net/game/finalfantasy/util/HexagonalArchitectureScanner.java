package net.game.finalfantasy.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 六角形架構掃描工具 (Hexagonal Architecture Scanner)
 * 
 * 這個工具用於掃描 Final Fantasy 遊戲系統的專案目錄結構，分析六角形架構的各個層次，
 * 並生成開發提示，幫助開發人員理解專案架構並遵循架構規範進行開發。
 * 
 * 功能特點:
 * - 掃描專案目錄結構，識別 Java 文件
 * - 根據文件路徑和命名將文件分類到不同的架構層次（領域層、應用層、基礎設施層）
 * - 生成詳細的開發提示，包括：
 *   - 專案架構概述
 *   - 各層次的文件列表
 *   - 配置文件列表
 *   - 開發新模組的步驟指南
 *   - 命名約定
 * 
 * 使用方法:
 * 1. 直接執行此類的 main 方法
 * 2. 或者使用 HexagonalArchitectureScannerRunner 類執行
 * 3. 或者在程式中使用:
 *    HexagonalArchitectureScanner scanner = new HexagonalArchitectureScanner();
 *    scanner.scan();
 *    String prompt = scanner.generatePrompt();
 * 
 * 注意事項:
 * - 掃描器基於文件路徑和命名進行分類，因此專案應遵循一致的命名約定
 * - 掃描器會忽略測試文件（路徑中包含 "test" 的文件）
 * - 配置文件（路徑中包含 "config" 或 "Config" 的文件）會被單獨分類
 */
public class HexagonalArchitectureScanner {

    private static final String PROJECT_ROOT = System.getProperty("user.dir");
    private static final String[] DOMAIN_KEYWORDS = {"model", "entity", "domain", "service"};
    private static final String[] APPLICATION_KEYWORDS = {"usecase", "application", "service", "port"};
    private static final String[] INFRASTRUCTURE_KEYWORDS = {"adapter", "repository", "persistence", "web", "controller", "config"};

    private final Map<String, List<String>> domainFiles = new HashMap<>();
    private final Map<String, List<String>> applicationFiles = new HashMap<>();
    private final Map<String, List<String>> infrastructureFiles = new HashMap<>();
    private final List<String> configFiles = new ArrayList<>();

    public static void main(String[] args) {
        HexagonalArchitectureScanner scanner = new HexagonalArchitectureScanner();
        scanner.scan();
        System.out.println(scanner.generatePrompt());
    }

    public void scan() {
        try {
            // 掃描專案根目錄
            scanDirectory(Paths.get(PROJECT_ROOT));
        } catch (IOException e) {
            System.err.println("掃描專案目錄時發生錯誤: " + e.getMessage());
        }
    }

    private void scanDirectory(Path directory) throws IOException {
        try (Stream<Path> paths = Files.walk(directory)) {
            paths.filter(Files::isRegularFile)
                 .filter(path -> path.toString().endsWith(".java"))
                 .forEach(this::categorizeFile);
        }
    }

    private void categorizeFile(Path file) {
        String relativePath = Paths.get(PROJECT_ROOT).relativize(file).toString();
        String packagePath = relativePath.replace(File.separatorChar, '.');

        // 忽略測試文件
        if (packagePath.contains("test")) {
            return;
        }

        // 配置文件
        if (packagePath.contains("config") || packagePath.contains("Config")) {
            configFiles.add(relativePath);
            return;
        }

        // 根據關鍵字分類文件
        if (containsAnyKeyword(packagePath, DOMAIN_KEYWORDS)) {
            String category = getCategoryFromPath(packagePath, DOMAIN_KEYWORDS);
            domainFiles.computeIfAbsent(category, k -> new ArrayList<>()).add(relativePath);
        } else if (containsAnyKeyword(packagePath, APPLICATION_KEYWORDS)) {
            String category = getCategoryFromPath(packagePath, APPLICATION_KEYWORDS);
            applicationFiles.computeIfAbsent(category, k -> new ArrayList<>()).add(relativePath);
        } else if (containsAnyKeyword(packagePath, INFRASTRUCTURE_KEYWORDS)) {
            String category = getCategoryFromPath(packagePath, INFRASTRUCTURE_KEYWORDS);
            infrastructureFiles.computeIfAbsent(category, k -> new ArrayList<>()).add(relativePath);
        }
    }

    private boolean containsAnyKeyword(String path, String[] keywords) {
        for (String keyword : keywords) {
            if (path.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private String getCategoryFromPath(String path, String[] keywords) {
        for (String keyword : keywords) {
            if (path.contains(keyword)) {
                return keyword;
            }
        }
        return "other";
    }

    public String generatePrompt() {
        StringBuilder prompt = new StringBuilder();
        prompt.append("# Final Fantasy 遊戲系統 - 六角形架構開發指南\n\n");

        prompt.append("## 專案架構概述\n");
        prompt.append("本專案採用六角形架構（Hexagonal Architecture，又稱埠和適配器架構），將應用程式分為三個主要層次：\n\n");
        prompt.append("1. **領域層（Domain Layer）**：包含業務邏輯和領域模型\n");
        prompt.append("2. **應用層（Application Layer）**：包含應用服務和用例\n");
        prompt.append("3. **基礎設施層（Infrastructure Layer）**：包含外部適配器（Web控制器、儲存庫等）\n\n");

        prompt.append("## 領域層（Domain Layer）\n");
        prompt.append("領域層是核心業務邏輯所在，包含實體、值對象和領域服務。\n\n");
        appendFilesSection(prompt, domainFiles, "領域");

        prompt.append("\n## 應用層（Application Layer）\n");
        prompt.append("應用層協調領域對象以執行特定用例，定義埠（接口）供外部適配器使用。\n\n");
        appendFilesSection(prompt, applicationFiles, "應用");

        prompt.append("\n## 基礎設施層（Infrastructure Layer）\n");
        prompt.append("基礎設施層包含與外部系統交互的適配器，如Web控制器、資料庫儲存庫等。\n\n");
        appendFilesSection(prompt, infrastructureFiles, "基礎設施");

        prompt.append("\n## 配置文件\n");
        prompt.append("以下是專案中的主要配置文件：\n\n");
        for (String file : configFiles) {
            prompt.append("- ").append(file).append("\n");
        }

        prompt.append("\n## 開發新模組指南\n");
        prompt.append("開發新模組時，請遵循以下步驟：\n\n");
        prompt.append("1. **定義領域模型**：在領域層創建實體和值對象\n");
        prompt.append("2. **實現領域服務**：在領域層實現核心業務邏輯\n");
        prompt.append("3. **定義應用埠**：在應用層定義輸入和輸出埠（接口）\n");
        prompt.append("4. **實現應用服務**：在應用層實現用例，協調領域對象\n");
        prompt.append("5. **實現適配器**：在基礎設施層實現與外部系統的交互\n\n");

        prompt.append("## 命名約定\n");
        prompt.append("- 領域實體：使用業務術語，如 `Hero`, `Equipment`\n");
        prompt.append("- 應用埠：使用 `UseCase` 後綴，如 `HeroManagementUseCase`\n");
        prompt.append("- 應用服務：使用 `Service` 後綴，如 `HeroManagementService`\n");
        prompt.append("- 輸入適配器：使用 `Controller` 或具體技術名稱，如 `HeroController`\n");
        prompt.append("- 輸出適配器：使用 `Repository` 或具體技術名稱，如 `HeroRepository`\n");

        return prompt.toString();
    }

    private void appendFilesSection(StringBuilder prompt, Map<String, List<String>> files, String layerName) {
        prompt.append("### ").append(layerName).append("層文件\n");

        if (files.isEmpty()) {
            prompt.append("目前沒有").append(layerName).append("層文件。\n");
            return;
        }

        for (Map.Entry<String, List<String>> entry : files.entrySet()) {
            String category = entry.getKey();
            List<String> fileList = entry.getValue();

            prompt.append("#### ").append(formatCategoryName(category)).append("\n");
            for (String file : fileList) {
                prompt.append("- ").append(file).append("\n");
            }
            prompt.append("\n");
        }
    }

    private String formatCategoryName(String category) {
        switch (category) {
            case "model":
            case "entity":
                return "領域模型";
            case "domain":
                return "領域服務";
            case "service":
                return "服務";
            case "usecase":
                return "用例";
            case "application":
                return "應用服務";
            case "port":
                return "埠（接口）";
            case "adapter":
                return "適配器";
            case "repository":
            case "persistence":
                return "儲存庫";
            case "web":
            case "controller":
                return "Web控制器";
            case "config":
                return "配置";
            default:
                return "其他";
        }
    }
}
