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

1. Go to the [Releases](https://github.com/ahmedibu777/FortiFile/releases) page.
2. Download `FortiFile-1.0.0.jar`.
3. Save it anywhere on your computer (e.g., your Desktop).

That's it. No installation wizard. No registry changes.

### Option B: Build from Source

```bash
# 1. Clone this repository
git clone https://github.com/ahmedibu777/FortiFile.git

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
java -jar target/fortifile-1.0.0.jar
```

You will see a menu:

```
=================================================
     FortiFile - Secure File Encryption System   
=================================================
Algorithm: AES-256-GCM  |  KDF: PBKDF2-HMAC-SHA256

Main Menu:
  [1] Encrypt a File
  [2] Decrypt a File
  [3] Exit
Enter choice: 
```

### Method 2: GUI Mode

```bash
java -jar target/fortifile-1.0.0.jar --gui
```

**Note for IntelliJ users:** To run directly from the IDE, open `Main.java`, click the green ▶ gutter icon. For GUI mode, go to `Run → Edit Configurations → Program Arguments` and type `--gui`.

---

## 📖 Step-by-Step Guide for Absolute Beginners

### Path A: Run the Pre-built JAR (No Programming Knowledge Needed)

#### Step 1: Install Java

1. Open your web browser and go to https://adoptium.net/
2. Download Eclipse Temurin JDK 21 (LTS) for your operating system.
3. Run the installer and follow the prompts.
4. Open a terminal or command prompt and type:
   ```bash
   java -version
   ```
5. If you see `openjdk version "21"`, you are ready!

#### Step 2: Download FortiFile

1. Visit the [Releases](https://github.com/ahmedibu777/FortiFile/releases) page.
2. Click on the latest release.
3. Download the file named `FortiFile-1.0.0.jar`.

#### Step 3: Run FortiFile

**On Windows:**
1. Open Command Prompt or PowerShell.
2. Type:
   ```
   cd Desktop
   java -jar FortiFile-1.0.0.jar
   ```

**On macOS or Linux:**
1. Open Terminal.
2. Type:
   ```bash
   cd ~/Downloads
   java -jar FortiFile-1.0.0.jar
   ```

#### Step 4: Encrypt Your First File

1. When the menu appears, type `1` and press Enter.
2. Type the path to a file you want to lock. Example:
   ```
   C:\Users\YourName\Pictures\photo.jpg
   ```
3. Choose where to save the locked file. Example:
   ```
   C:\Users\YourName\Pictures\photo.jpg.enc
   ```
4. Enter a strong password and press Enter.
5. Wait for 100%. Your file is now encrypted.

#### Step 5: Decrypt Your File

1. Restart FortiFile and type `2`.
2. Enter the path to your `.enc` file.
3. Choose an output path for the restored file.
4. Enter the same password you used before.
5. If the password is correct, your original file is restored. If wrong, you will see an error.

---

## 🧪 Testing Your Installation

Create a test file and verify encryption/decryption works:

```bash
# Create a test file
echo "This is my secret document." > test.txt

# Run FortiFile and choose [1] Encrypt
# Input:  test.txt
# Output: test.txt.enc
# Password: anything you like

# Then choose [2] Decrypt
# Input:  test.txt.enc
# Output: test_recovered.txt
# Password: same as above

# Verify they match
diff test.txt test_recovered.txt
# No output = files are identical ✅
```

---

## 🔐 Security Notes

- **AES-256-GCM:** Military-grade encryption standard used by governments and financial institutions.
- **PBKDF2-HMAC-SHA256:** Your password is never stored directly. It's transformed into an encryption key using 65,536 iterations of PBKDF2 with a random salt.
- **Local Processing:** All encryption happens on your machine. Your files and passwords never leave your computer.
- **Tamper Detection:** If someone modifies an encrypted file, decryption will fail immediately due to GCM authentication.

---

## 🤝 Contributing

We welcome contributions! Here's how:

1. Fork the repository.
2. Create a feature branch: `git checkout -b feature/my-feature`
3. Commit your changes: `git commit -m "Add my feature"`
4. Push to the branch: `git push origin feature/my-feature`
5. Open a pull request.

---

## 📄 License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

---

## ❓ FAQs

**Q: Can I use this on macOS or Linux?**  
A: Yes! FortiFile runs on any operating system with Java 21 installed.

**Q: What if I forget my password?**  
A: Unfortunately, there's no "forgot password" option. The encryption is so strong that even we can't decrypt your file without the correct password. Choose a password you'll remember!

**Q: How long does encryption take?**  
A: For most files (up to 1 GB), a few seconds. For larger files, it depends on your disk speed. The progress bar keeps you updated.

**Q: Is my data sent to the cloud?**  
A: No. Everything happens locally on your computer. Your files never leave your machine.

---

## 📞 Support

Found a bug? Have a question? [Open an issue](https://github.com/ahmedibu777/FortiFile/issues) on GitHub!

---

**Made with ❤️ by [Ahmad](https://github.com/ahmedibu777)**
