[![AetherJFX](https://img.shields.io/badge/Only%20for:-AetherJFX-blue)](https://github.com/0xD3ADCODE/AetherJFX)
![Java](https://img.shields.io/badge/Java-17-b07219)

# AetherJFX AVIF Image Decoder Plugin

> [!WARNING]  
> This plugin is designed to be used ONLY with [JavaFX](https://github.com/openjdk/jfx) fork [AetherJFX](https://github.com/0xD3ADCODE/AetherJFX). Attempting to use it with standard JavaFX will lead to an exception!

[AVIF](https://github.com/AOMediaCodec/libavif) image decoding plugin for [AetherJFX](https://github.com/0xD3ADCODE/AetherJFX)

Based on [vavi-image-avif](https://github.com/umjammer/vavi-image-avif) and integrates into JavaFX's `ImageIO` (`IIO`) instead of default one that depends on `AWT`

## Dependency

> [!WARNING]  
> Prebuild `Windows x86-64` library is included into jar. Refer to [libavif](https://github.com/AOMediaCodec/libavif) repository for installation guide for other systems

Define custom Gradle ivy repository in `repositories` block:
```groovy
repositories {
    //...your repositories
    def github = ivy {
        url 'https://github.com/'
        patternLayout {
            artifact '/[organisation]/[module]/releases/download/[revision]/[artifact].[ext]'
        }
        metadataSources { artifact() }
    }
    exclusiveContent {
        forRepositories(github)
        filter { includeGroup("0xD3ADCODE") }
    }
}
```

Add dependency into `dependencies` block:
```groovy
dependencies {
    //...your dependencies
    implementation("0xD3ADCODE:AetherJFX-ImageDecoder-AVIF:{version}") {
        artifact {
            name = 'AetherJFX-ImageDecoder-AVIF-{version}'
            type = 'jar'
        }
    }

    // JNA is required for native library
    implementation 'net.java.dev.jna:jna:5.14.0'
}
```

Replace `{version}` with latest [Release](https://github.com/0xD3ADCODE/AetherJFX-ImageDecoder-AVIF/releases) tag (eg, `v1.0`)

## Usage
Register plugin as soon as possible (before JavaFX Toolkit start) with just one line of code:
```java
AVIFLoader.register();
```

After that all AVIF images will be decoded using newly installed decoder directly into JavaFX's `Image` without `AWT`

## Development

To properly build `jar` by yourself, clone repository and create `gradle.properties` file with:
```text
AETHER_JFX_SDK_PATH = <path to unpacked AetherJFX SDK folder>
```

## Credits
[AOMediaCodec](https://github.com/AOMediaCodec) for [AVIF](https://github.com/AOMediaCodec/libavif) decoder  
[umjammer](https://github.com/umjammer/) for [vavi-image-avif](https://github.com/umjammer/vavi-image-avif) AVIF decoder/encoder implementation for Java