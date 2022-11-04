# TextInputEditTextFormatter [![Build Status](https://app.bitrise.io/app/21913789073d341c/status.svg?token=kYOCprddVCv8w7u9DI4pIw&branch=main)](https://app.bitrise.io/app/21913789073d341c) [![Release](https://jitpack.io/v/igorcferreira/TextInputEditTextFormatter.svg)](https://jitpack.io/igorcferreira/TextInputEditTextFormatter)

The `TextInputEditTextFormatter` is an implementation of `TextWatcher` that helps formatting text
input by the user. For example, formatting the number `5000` into `$5,000` based on the currency
code "USD".

### Usage

**Step 1.** Add the JitPack maven repository
```kotlin
repositories {
    mavenCentral()
    maven {
        url = "https://jitpack.io"
        content { includeGroup("com.github.igorcferreira") }
    }
}
```

**Step 2.** Add the TextInputEditTextFormatter library into the dependencies
```kotlin
dependencies {
    //...
    implementation("com.github.igorcferreira:TextInputEditTextFormatter:1.0.0")
}
```

**Step 3.** Include the text formatter into your `TextInputEditText`
```kotlin
val formatter = CurrencyFormatter("USD", eraseSingleSymbol = true)
binding.textInputEditText.apply {
    addTextChangedListener(TextInputEditTextMask(this, formatter))
}
```

### Documentation

- [Javadoc](https://javadoc.jitpack.io/com/github/igorcferreira/TextInputEditTextFormatter/latest/javadoc/)

### Demonstration

![Demonstration of currency inputs being formatted. Example, the input "1234" becomes "$1,234"](docs/demo.gif)

### License

[Apache 2.0](LICENSE)