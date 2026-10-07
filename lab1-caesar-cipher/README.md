# Laboratory Work 1 - Caesar Cipher

This laboratory contains a Java implementation of the Caesar cipher for the Romanian alphabet.

## Tasks

- Task 1.1 - Caesar Cipher
- Task 1.2 - Caesar Cipher with a keyword-based alphabet permutation

## Features

- Romanian alphabet with 31 letters
- Encryption and decryption
- Numeric key validation from 1 to 30
- Uppercase and lowercase input support
- Automatic space removal
- Romanian diacritic support
- Normalization of Ş/Ţ to Ș/Ț
- Invalid character validation
- Keyword validation for Task 1.2
- Construction and display of the permuted alphabet

## Compile

From the `lab1-caesar-cipher` directory:

```bash
javac -encoding UTF-8 src/CaesarCipherLab.java
```

## Run

```bash
java -cp src CaesarCipherLab
```

## UTF-8 on Windows PowerShell

Before running the program, use:

```powershell
chcp 65001
[Console]::InputEncoding = [System.Text.UTF8Encoding]::new()
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new()
```

## Source

The Java source file is located in:

`src/CaesarCipherLab.java`
