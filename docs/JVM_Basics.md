# JVM Basics

## What is JDK, JRE, and JVM?

These three terms are often confused, but they describe three different layers of the Java platform.

**JVM — Java Virtual Machine** is the core engine that actually runs Java programs. It reads a special file format called *bytecode* and executes the instructions on whatever operating system it is running on. The JVM is the reason Java programs can run on Windows, macOS, and Linux without changing the source code — each platform has its own JVM, but they all understand the same bytecode.

**JRE — Java Runtime Environment** is the JVM plus the standard libraries that Java programs commonly use (things like `ArrayList`, `Scanner`, `String`, etc.). If you only want to *run* Java applications, the JRE is all you need.

**JDK — Java Development Kit** is the JRE plus the tools needed to *write and compile* Java programs. The most important of these tools is `javac`, the Java compiler. When you are a developer building a Java project, you install the JDK. End users who just run the finished application only need the JRE.

A simple way to picture the relationship:

```
JDK
 └── JRE
      └── JVM
```

---

## What is Bytecode?

When you write a Java program and compile it with `javac`, the compiler does not produce machine code that the CPU can directly understand (the way C or C++ compilers do). Instead, it produces **bytecode** — a compact, platform-neutral instruction set stored in `.class` files.

Bytecode is like an intermediate language. It is lower-level than Java source code (`.java` files), but higher-level than the raw binary instructions a specific CPU understands. The JVM's job is to take that bytecode and translate it into the real instructions the current machine can run.

---

## Write Once, Run Anywhere

One of Java's most well-known promises is *write once, run anywhere* (WORA). Because all Java source code compiles down to the same bytecode format, and because every major operating system has a JVM that understands that format, a single compiled `.class` file can run on any platform without recompilation.

For example, you can write and compile a Java program on a Windows laptop, copy the `.class` files onto a Linux server or a macOS machine, and run them there without any changes. The JVM on each platform handles the translation from bytecode to that platform's native instructions — your code never has to care about those differences.

This is fundamentally different from languages like C, where you typically need to recompile the source code for each target operating system.
