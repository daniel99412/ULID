# ULID - Universally Unique Lexicographically Sortable Identifier

This project provides a Java implementation of **ULID** (Universally Unique Lexicographically Sortable Identifier). ULID is a unique identifier that is lexicographically sortable and can be used as an alternative to traditional UUIDs.

## 🚀 Features

- Generation of 128-bit ULIDs with a timestamp and random part.
- Lexicographic ordering based on the timestamp.
- Conversion between ULID strings and their binary representation.
- Implementation based on Crockford's Base32 for better readability.
- Methods for comparison and conversion.

## 📦 Installation

You can clone this repository and include it in your project:

```sh
git clone https://github.com/your-username/ulid-java.git
cd ulid-java
```

If you use **Maven**, add the dependency to your `pom.xml` (when publishing the package to a Maven repository):

```xml
<dependency>
    <groupId>com.dpardo</groupId>
    <artifactId>ulid</artifactId>
    <version>1.0.0</version>
</dependency>
```

If you use **Gradle**, add it to `build.gradle`:

```gradle
dependencies {
    implementation 'com.dpardo:ulid:1.0.0'
}
```

## 🛠 Usage

### Generating a ULID

```java
import com.dpardo.ulid.ULID;

public class Main {
    public static void main(String[] args) {
        ULID ulid = ULID.getUlid();
        System.out.println("New ULID: " + ulid);
    }
}
```

### Convert ULID to String

```java
ULID ulid = ULID.getUlid();
String ulidString = ulid.toString();
System.out.println("ULID in string format: " + ulidString);
```

### Parsing a ULID from String

```java
ULID parsedUlid = ULID.fromString("01H7B7A2XFP01T4H8Z7G7Q3Y6J");
System.out.println("Parsed ULID: " + parsedUlid);
```

### Comparing ULIDs

```java
ULID ulid1 = ULID.getUlid();
ULID ulid2 = ULID.getUlid();

if (ulid1.compareTo(ulid2) < 0) {
    System.out.println("ulid1 is smaller than ulid2");
} else {
    System.out.println("ulid1 is greater than or equal to ulid2");
}
```

## 📜 License

This project is licensed under the **GNU General Public License v3.0 (GPLv3)**. You are free to use, modify, and distribute it under the terms of this license. For more details, see the [LICENSE](LICENSE) file.

