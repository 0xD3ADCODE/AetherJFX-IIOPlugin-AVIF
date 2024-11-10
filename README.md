# AetherJFX AVIF IIOPlugin

> [!WARNING]  
> This plugin is designed to be used ONLY with [JavaFX](https://github.com/openjdk/jfx) fork [AetherJFX](https://github.com/0xD3ADCODE/AetherJFX). Attempting to use it with standard JavaFX will lead to an exception!

[AVIF](https://github.com/AOMediaCodec/libavif) image decoding plugin for [AetherJFX](https://github.com/0xD3ADCODE/AetherJFX)

Based on [vavi-image-avif](https://github.com/umjammer/vavi-image-avif) and integrates into JavaFX's `ImageIO` (`IIO`) instead of default one that depends on `AWT`

## Dependency

> [!WARNING]  
> Prebuild `Windows x86-64` library is included into jar. Refer to [libavif](https://github.com/AOMediaCodec/libavif) repository for installation guide for other systems

## Usage
Register plugin as soon as possible (before JavaFX Toolkit start) with just one line of code:
```java
AVIFLoader.register();
```

After that all AVIF images will be decoded using newly installed decoder directly into JavaFX's `Image` without `AWT`

## Credits
[AOMediaCodec](https://github.com/AOMediaCodec) for [AVIF](https://github.com/AOMediaCodec/libavif) decoder  
[umjammer](https://github.com/umjammer/) for [vavi-image-avif](https://github.com/umjammer/vavi-image-avif) AVIF decoder/encoder implementation for Java