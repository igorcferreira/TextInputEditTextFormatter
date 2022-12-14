# TextInputEditTextFormatter [![Build Status](https://app.bitrise.io/app/21913789073d341c/status.svg?token=kYOCprddVCv8w7u9DI4pIw&branch=main)](https://app.bitrise.io/app/21913789073d341c) [![Release](https://jitpack.io/v/igorcferreira/TextInputEditTextFormatter.svg)](https://jitpack.io/#igorcferreira/TextInputEditTextFormatter)

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
    }
}
```

**Step 2.** Add the TextInputEditTextFormatter library into the dependencies
```kotlin
dependencies {
    implementation("com.github.igorcferreira.TextInputEditTextFormatter:textinputedittextformatter:[1.0,1.1[")
    //If you are using Fragment/Activities, add
    implementation("com.github.igorcferreira.TextInputEditTextFormatter:textinputedittextformatter-android:[1.0,1.1[")
    //Or if you are using Combine, add
    implementation("com.github.igorcferreira.TextInputEditTextFormatter:textinputedittextformatter-combine:[1.0, 1.1[")
}
```

**Step 3.1.** TextInputEditText: Include the text formatter into your `TextInputEditText`
```kotlin
val formatter = CurrencyFormatter("USD", eraseSingleSymbol = true)
//...
binding.textInputEditText.apply {
    addTextChangedListener(TextInputEditTextMask(this, formatter))
}
```

**Step 3.2.** Combine: Include the text formatter into your `TextInputEditText`
```kotlin
val formatter = CurrencyFormatter("USD", eraseSingleSymbol = true)
//...
OutlinedTextField(
    value = composeInput,
    onValueChange = { composeInput = it },
    visualTransformation = InputFormatVisualTransformation(formatter),
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
)
```

**Extracting values:**
Some formatters, like the `CurrencyFormatter` may provide a way to cleanup the input to an non-formatted style.
This may help with propagating the result of the user input into other layers of the app or navigation flow.
```kotlin
val formatter = CurrencyFormatter("USD", eraseSingleSymbol = true)
//...
binding.textInputEditText.apply {
    addTextChangedListener(TextInputEditTextMask(this, formatter))
}
//...
val number = formatter.extractDouble(binding.textInputEditText.text.toString())
```
On Combine, this `extractDouble` don't need to be used once the formatter is applied as a mask,
not as a mutation of the actual value in the UI object.

### Documentation

- [textinputedittextformatter Javadoc](https://javadoc.jitpack.io/com/github/igorcferreira/TextInputEditTextFormatter/textinputedittextformatter/latest/javadoc/)
- [textinputedittextformatter-android Javadoc](https://javadoc.jitpack.io/com/github/igorcferreira/TextInputEditTextFormatter/textinputedittextformatter-android/latest/javadoc/)
- [textinputedittextformatter-combine Javadoc](https://javadoc.jitpack.io/com/github/igorcferreira/TextInputEditTextFormatter/textinputedittextformatter-combine/latest/javadoc/)

### Demonstration

![Demonstration of currency inputs being formatted. Example, the input "1234" becomes "$1,234"](docs/demo.gif)

### License

[Apache 2.0](LICENSE)