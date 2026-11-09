# Diplay for Lynk&Co 09 PHEV

**針對領克 09 PHEV 使用情境優化的 CarPlay 分支。**

[完整說明（簡體中文）](README.md) · [本專案發布頁](https://github.com/chenkaijia113/Diplay-for-Lynk-Co-09-PHEV/releases) · [回報問題](https://github.com/chenkaijia113/Diplay-for-Lynk-Co-09-PHEV/issues)

## 來源與本分支的改動

本專案由 [chenkaijia113](https://github.com/chenkaijia113) 基於 [shihabal3amri/DiPlay v0.2.22](https://github.com/shihabal3amri/DiPlay/tree/v0.2.22) 調整。底層接收實作來自 [shilapi/xcertplay](https://github.com/shilapi/xcertplay)，部分介面與網站沿用 [DiAuto](https://github.com/shihabal3amri/DiAuto)。CarPlay、USB / 無線連線、音視訊與診斷等主要能力屬於上游及社群成果。

相較上游，本分支做了以下調整：

- 預設 OEM 標籤改為 `Lynk & Co`，空標籤或舊的 `BYD` 標籤會回到領克標籤，其他自訂標籤繼續保留。
- 導航與提示音的清晰模式改為請求 **24 kHz 以上 PCM**，並排除這些輸出流中的 Opus。尚未儲存此偏好的安裝預設啟用；既有開關值會保留。修改此選項會重新連線，若導航無聲可關閉後重試。
- 導航音量範圍由 **−20～0 dB** 擴充至 **−30～+6 dB**，PCM 放大時限制在 16 位元取樣範圍內；不改動音樂與通話的獨立音量設定。較響的音訊仍可能因限幅失真。
- 移除 CarPlay 媒體會話自動綁定 / 解除綁定吉利私有方向盤服務的兩處呼叫，保留通用媒體鍵處理；不宣稱新增領克 09 全套方向盤按鍵支援。
- 替換返回車機圖示，讓預設預覽使用實際車機圖示。
- 預設應用程式名稱改為 `Apple CarPlay`，分支版本為 `0.2.22-lynkco-stable.2`，version code 為 `44`。
- 清理確認未使用的函式、匯入、文案、圖片與產物，保留必要的建置、測試及授權檔案。

## 適配與驗證範圍

**本分支的維護目標是領克 09 PHEV。** 倉庫保留的 BYD / DiLink 程式碼與文件屬於上游相容性實作與歷史資料，不代表本分支以比亞迪為適配目標。

上游吉利儀表路徑針對特定 `geely_fx11` / `CS11_A4` 車機，領克 03 的驗證記錄不能用來證明 09 PHEV 的儀表、HUD、車速或電池資料已適配。不同年式、韌體和 iOS 版本仍需分別驗證。

`stable` 是版本名稱，不是驗證認證。本次已核對原始碼差異；整理環境缺少 Java Runtime，完整單元測試、Lint 與 APK 建置尚未完成。

## 建置與回報

應用程式安裝在 Android 車機上。主應用程式模組是 `:mobile`，建置要求與外部認證素材配置請見[建置文件](docs/BUILD.md)。目前本倉庫未隨初次上傳發布 APK；上游安裝包不等同於本分支的建置。

請在[本倉庫 Issues](https://github.com/chenkaijia113/Diplay-for-Lynk-Co-09-PHEV/issues)回報問題，附上車輛年式、車機韌體、iPhone / iOS、連線方式、應用程式版本及重現步驟。

## 致謝與授權

感謝 shihabal3amri、shilapi、DiAuto 作者及所有上游貢獻者。本分支保留 [GPL-3.0](LICENSE)、相關 [AGPL-3.0 聲明](docs/licenses/DiAuto-AGPL-3.0.txt)與[第三方署名和授權](docs/THIRD_PARTY_NOTICES.md)。本專案與領克 / 吉利或 Apple 無官方隸屬或背書關係，未經 Apple CarPlay / MFi 官方認證。
