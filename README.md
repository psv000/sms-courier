# 🤖 SMS Forwarder (Telegram Bot)

## 📝 Project Description

This is a native AI GENERATED Android application, developed using Kotlin, designed to automatically intercept all incoming SMS messages and forward them to a specified Telegram chat using the Bot API.

The application operates in the background with a minimal user interface. It is ideal for forwarding One-Time Passwords (OTPs), verification codes, and general notifications from a dedicated "source" phone.

## ✨ Key Features

* **SMS Interception:** Uses an Android `BroadcastReceiver` to react instantly to new incoming messages.
* **Secure Forwarding:** Sends data via HTTPS to the Telegram Bot API.
* **Secure Configuration:** Bot token and chat ID are stored securely in `local.properties` and are kept out of version control.

## 🛠️ Requirements and Preparation

To build and run the application, you will need:

1.  **Android Studio** and the necessary SDKs.
2.  An Android phone running Android 5.0 (Lollipop) or higher.
3.  **Telegram Bot Token:** Obtained from @BotFather (e.g., `123456:ABC-DEF1234...`).
4.  **Telegram Chat ID:** Your personal user ID where the bot will deliver messages (e.g., `123456789`).

## ⚙️ Secret Key Configuration

The bot token and chat ID must be set in the project's **`local.properties`** file. This is the secure method to ensure your secrets are not committed to Git.

### 1. Add Keys

Open the **`local.properties`** file in the project root directory and add the following lines at the end, replacing the placeholders with your actual values:

```properties
# --- Telegram Secrets ---
telegram.bot.token=YOUR_BOT_TOKEN_FROM_BOTFATHER
telegram.chat.id=YOUR_CHAT_ID_NUMBERS