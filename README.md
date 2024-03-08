# kordmand

[![kordmand](https://img.shields.io/badge/kordmand-0.0.3-blue.svg)]()
<br><br>
[![Java](https://img.shields.io/badge/Java-21-FF7700.svg?logo=java)]()
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.23-186FCC.svg?logo=kotlin)]()


## Sample Code

```kotlin

suspend fun main() {
    val kord = Kord("TOKEN")
    
    TestCommand(kord).let {
        kord.registerGlobalCommand(it)
    }
}

class TestCommand(kord: Kord): Command {
    override val command = "test"
    override val description = "Test command description"
    override val options = listOf(
        IntegerOption("option1", "First option").apply {
            this.minValue = 0
            this.maxValue = 20
            this.required = true
        },
        StringOption("option2", "Second option").apply {
            this.required = true
            this.autoComplete = true
        },
        BooleanOption("option3", "Third option")
    )
    
    init {
        onRun(kord) {
            interaction.respondPublic {
                embed {
                    this.title = "Hello, world!"
                }
            }
        }

        onAutoComplete(kord) {
            interaction.suggestString {
                this.choice("asdf", "asdf")
                this.choice("fdsa", "fdsa")
                this.choice("qwer", "qwer")
            }
        }
    }
}
```

<br>

### Use API

## Maven
```xml
<repositories>
    <repository>
        <id>kr.blugon</id>
        <url>https://repo.blugon.kr/repository/maven-public/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>kr.blugon</groupId>
        <artifactId>kordmand</artifactId>
        <version>VERSION</version>
    </dependency>
</dependencies>
```


## Gradle
```groovy
repositories {
    maven { 'https://repo.blugon.kr/repository/maven-public/' }
}

dependencies {
    implementation 'kr.blugon:kordmand:VERSION'
}
```

## Kotlin DSL
```kotlin
repositories {
    maven("https://repo.blugon.kr/repository/maven-public/")
}

dependencies {
    implementation("kr.blugon:kordmand:VERSION")
}
```
