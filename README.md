# Spending tracker (Android)

The Spending tracker web app in an Android shell, with a home-screen widget.

- Works offline. Accounts and entries are stored on the phone.
- Backup: Backup tab, Save backup file, then choose Drive in the picker.
- Widget: long-press the home screen, Widgets, Spending tracker. It shows this
  month's total and a shortcut to add spendings.

## Build the APK without installing anything

1. Create a new GitHub repository and upload all files from this folder to it.
2. Open the Actions tab and run **Build APK** (it also runs on every push).
3. When it finishes, open the run and download the `spending-tracker-apk` artifact.
4. Unzip it, copy `app-debug.apk` to your phone and open it.
   Allow "install unknown apps" for your file manager or browser when asked.

## Build locally

Install Android Studio, open this folder, and press Run. Or with the SDK and
Gradle 8.7 installed: `gradle assembleDebug`.
