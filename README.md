# Meta
nothing more than a meta language


Status: Proof of Concept

# Why
- Generate Simple and Clear Code (without abstract, extend but using Design patterns ) for common languages (Java, Python, C++) for  any platform (pc, arduino, raspberry pi)
- Mainly for Algorithms (ggt, shandon, aoc, consensus)
- Also generate Infrastructure (Dockerfile, Cloudformation) for distributed algorithms
- Visual representation, suitable for discussions
- Automated Documentation Generation and Runtime Analysis 
- Simulation of Algorithm for different Size of Input's (Latenz, Throuput, CPU, Memory )
- Simulation of Protocols (cohydra, docker compose) 
- Eligible for certification
-- Improving Code Consistency
-- Automated Unit-Tests Generation from Input classes

# Example (Advent Of Code - Day 1)

Beispiel für das Redundante ausführen von Add

```

```


# Tools
- Visual Editor or Plugin
- Code Generators
- SimpleJava2Meta-Converter

# Get started

## build
```
mvn clean compile assembly:single
```

## run
```
java -jar target/meta-0.0.1-SNAPSHOT-jar-with-dependencies.jar src/test/resources/ChronalCalibration.yml 
```
## SimpleJava2Meta
```
java -cp target/meta-0.0.1-SNAPSHOT-jar-with-dependencies.jar de.frittenburger.meta.impl.SimpleJavaToMetaConverterImpl src/main/java/de/frittenburger/aoc2020/day01/ReportRepa
ir.java out.yml
```
# Other Code Generators
