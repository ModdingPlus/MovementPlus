# CoyoteLib

## About

CoyoteLib is a library mod providing things like a double jump and coyote time API that my future mods will tap into. It also provides a customizable debug overlay (Work in Progress) and custom gamerules.

At the time of writing the only custom gamerule is coyotelib:preventCreeperGriefing which prevents creeper griefing even when mob griefing is enabled.

There is a maven repository avaiable at https://maven.necro.dev providing source, api and deobfJars for development.

## Development

Add the maven repository:
```groovy
repositories {
    [...]
    maven { url = 'https://maven.necro.dev/' }
}
```
Add CoyoteLib as a dependency:
```groovy
dependencies {
    minecraft 'net.minecraftforge:forge:1.15.2-31.x.x'

    [...]
    compile fg.deobf('dev.necro.coyotelib:CoyoteLib:1.1.0:dev')
}
```