# kordmand

[![kordmand](https://img.shields.io/badge/kordmand-0.0.7-blue.svg)]()
<br>
[![Java](https://img.shields.io/badge/Java-21-FF7700.svg?logo=java)]()
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-186FCC.svg?logo=kotlin)]()
<br><br>
Application command library for [Kord](https://github.com/kordlib/kord)


## Sample Code

```kotlin

suspend fun main() {
    val kord = Kord("TOKEN")
    
    TestCommand(kord).let {
        kord.registerGlobalCommand(it)
        kord.registerEvent()
    }
}

class TestCommand(bot: Kord): Command(bot) {
    override val command = "test"
    override val description = "Test command description"
    override val options = listOf(
        IntegerOption("option1", "First option") {
            this.minValue = 0
            this.maxValue = 20
            this.required = true
        },
        StringOption("option2", "Second option") {
            this.required = true
            this.autoComplete = true
        },
        BooleanOption("option3", "Third option")
    )
    
    override fun GuildChatInputCommandInteractionCreateEvent.onRun() {
        interaction.respondPublic {
            embed {
                this.title = "Hello, world!"
            }
        }
    }

    override fun AutoCompleteInteractionCreateEvent.onAutoComplete() {
        interaction.suggestString {
            this.choice("asdf", "asdf")
            this.choice("fdsa", "fdsa")
            this.choice("qwer", "qwer")
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
