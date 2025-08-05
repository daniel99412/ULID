# 🔥 ULID for Java 🚀

## ✨ Overview
This library provides an implementation of **ULID (Universally Unique Lexicographically Sortable Identifier)** in Java. It offers:

- ⚡ Fast ULID generation
- 📏 Lexicographically sortable identifiers
- 🔐 Secure random entropy
- 🔄 Conversion between ULID and string representation

## 📦 Installation
Add the dependency to your `pom.xml` if using Maven:

```xml
<dependency>
    <groupId>com.dpardo.ulid</groupId>
    <artifactId>ulid</artifactId>
    <version>1.0.0</version>
</dependency>
```

For Gradle:

```gradle
dependencies {
    implementation 'com.dpardo.ulid:ulid:1.0.0'
}
```

## 🚀 Usage
### 🔧 Generating a ULID
```java
ULID ulid = ULID.getUlid();
System.out.println("Generated ULID: " + ulid);
```

### 📜 Parsing a ULID from String
```java
ULID parsedUlid = ULID.fromString("01ARZ3NDEKTSV4RRFFQ69G5FAV");
```

### 🔤 Converting to Lowercase
```java
String lowerUlid = ulid.toLowerCase();
```

### 🔄 Converting to Bytes
```java
byte[] ulidBytes = ulid.toBytes();
```

## 📜 License
This project is licensed under the **GNU General Public License v3 (GPL-3.0)**.

---
🔥 Enjoy using ULIDs in your Java applications! 🚀
