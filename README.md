# CreoLib
 
```
repositories {
    maven {
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")
        content {
            includeGroup("maven.modrinth")
        }
    }
}
dependencies {
    modImplementation "maven.modrinth:creo-lib:VERSION"
}
```
