<!-- Banner -->
<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?logo=java&logoColor=white" alt="Java 21">
  <img src="https://img.shields.io/badge/Maven-3.9-blue?logo=apachemaven&logoColor=white" alt="Maven">
  <img src="https://img.shields.io/badge/License-MIT-green.svg" alt="License MIT">
  <img src="https://img.shields.io/badge/Security-AES--256--GCM-red" alt="AES-256-GCM">
  <img src="https://img.shields.io/badge/Platform-Cross--Platform-lightgrey" alt="Cross Platform">
</p>

<h1 align="center">🔐 FortiFile</h1>
<p align="center"><b>A simple, secure, and user-friendly Java desktop application for encrypting and decrypting any type of file using AES-256.</b></p>

<p align="center">
  <a href="#-key-features">Features</a> •
  <a href="#-installation--setup">Install</a> •
  <a href="#-how-to-use">Usage</a> •
  <a href="#-security-notes">Security</a> •
  <a href="#-contributing">Contribute</a>
</p>

---

## 🚀 One-Line Pitch

**FortiFile** lets you lock any file—photos, PDFs, videos, text—with a password, using the same encryption trusted by governments and banks (AES-256). No installation hassles. No cloud. Just your file and your password.

---

## ✨ Key Features

| Feature | Description |
|---------|-------------|
| 🔒 **AES-256-GCM** | Military-grade authenticated encryption (NIST recommended). |
| 🔑 **PBKDF2 Key Derivation** | 65,536 iterations with random salt to stop brute-force attacks. |
| 📁 **Any File Type** | Encrypt text, images, PDFs, videos, archives—anything. |
| 🌊 **Streaming I/O** | Chunked 64 KB buffers. Encrypt a 10 GB video without freezing your PC. |
| 🖥️ **Two Interfaces** | Clean console mode + simple Swing GUI. |
| 📊 **Live Progress** | Watch encryption/decryption percentage in real time. |
| 🛡️ **Tamper Detection** | GCM authentication tag detects any file modification. |
| ☕ **Zero Dependencies** | Uses only the Java Standard Library. |

---

## 📸 Screenshots

> Place your screenshots inside the `screenshots/` folder and update these paths.

### Console Mode
![Console Menu](screenshots/console-menu.png)

### Encryption Progress
![Encrypt Progress](screenshots/encrypt-progress.png)

### Swing GUI
![Swing GUI](screenshots/swing-gui.png)

---

## 📋 Prerequisites

Before you start, make sure you have **one** of the following:

| Option | What You Need |
|--------|---------------|
| ☕ **Java Runtime (JRE) 21+** | To run the pre-built `.jar` file. [Download here](https://adoptium.net/) |
| 🛠️ **JDK 21 + Maven 3.9+** | To build from source. [Download JDK](https://adoptium.net/) • [Download Maven](https://maven.apache.org/download.cgi) |

**How to check Java:**
```bash
java -version
```
You should see something like `openjdk version "21"`.

---

## ⚡ Installation & Setup

### Option A: Download Pre-built JAR (Easiest 🏆)

1. Go to the [Releases](https://github.com/yourusername/FortiFile/releases) page.
2. Download `FortiFile-1.0.0.jar`.
3. Save it anywhere on your computer (e.g., your Desktop).

That's it. No installation wizard. No registry changes.

### Option B: Build from Source

```bash
# 1. Clone this repository
git clone https://github.com/yourusername/FortiFile.git

# 2. Enter the project folder
cd FortiFile

# 3. Build with Maven
mvn clean package

# 4. Your JAR is now in:
ls target/FortiFile-1.0.0.jar
```

---

## 🎮 How to Use

### Method 1: Console Mode (Terminal)

```bash
# Run the JAR
java -jar FortiFile-1.0.0.jar
```

You will see a menu:
