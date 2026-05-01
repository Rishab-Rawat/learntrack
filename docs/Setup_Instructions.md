# Setup Instructions

## JDK Version Used

This project was developed and tested with ** 25.0.3 2026-04-21 LTS **.

```
$ java -version
openjdk version "25.0.3 2026-04-21 LTS
OpenJDK Runtime Environment (build 25.0.3)
OpenJDK 64-Bit Server VM (build 25.0.3, mixed mode)
```



---

## Step 1: Install JDK

```
```

**Windows:**
Download from https://www.oracle.com/in/java/technologies/downloads/ run the installer.

---

## Step 2: Verify Installation

```bash
java -version
javac -version
```

Both commands should print version 25.x.x (or whichever version is installed).

---

## Step 3: Hello World — Confirming Java Works

Create a file called `Hello.java`:

```java
public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

Compile and run:

```bash
javac Hello.java
java Hello
```

 output:
```
Hello, World!
```



---

## Step 4: Compile LearnTrack

From the project root directory:

```bash
javac -d out $(find src -name "*.java")
```

On Windows (Command Prompt):
```
for /r src %f in (*.java) do javac -d out "%f"
```

---

## Step 5: Run LearnTrack

```bash
java -cp out com.airtribe.learntrack.ui.Main
```



---

## IDE Setup (IntelliJ IDEA — Recommended)

1. Open IntelliJ → **File → New → Project from Existing Sources**
2. Select the `learntrack` folder
3. Mark `src` as the **Sources Root** (right-click → Mark Directory As)
4. Set SDK to JDK 25
5. Run `Main.java` directly from the IDE
