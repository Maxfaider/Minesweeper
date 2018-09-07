# Minesweeper

## How use

### Direct
```console
java -jar dist/Minesweeper-1.0.0.jar
```

### Maven
```console
mvn package
java -jar target/Minesweeper-1.0.0.jar
```

### compiled JDK 10

```maven
<properties>
	<maven.compiler.source>10</maven.compiler.source>
	<maven.compiler.target>10</maven.compiler.target>
	<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```

## stage Main
> language Default: es

```console
>>Minesweeper<<
Seleccione Nivel
[1] Principiante <filas: 9> <columnas: 9> <minas: 10>
[2] Intemedio <filas: 16> <columnas: 16> <minas: 40>
[3] Experto <filas: 16> <columnas: 30> <minas: 99>
[4] Personalizar
[5] Salir
```

## stage play
```console
?4
Personalizar: <filas> <columnas> <mines>
?10 10 30
true
>>Minesweeper<<
Minas: 30     [0] Exit
        01 02 03 04 05 06 07 08 09 10

1       .  .  .  .  .  .  .  .  .  .
2       .  .  .  .  .  .  .  .  .  .
3       .  .  .  .  .  .  .  .  .  .
4       .  .  .  .  .  .  .  .  .  .
5       .  .  .  .  .  .  .  .  .  .
6       .  .  .  .  .  .  .  .  .  .
7       .  .  .  .  .  .  .  .  .  .
8       .  .  .  .  .  .  .  .  .  .
9       .  .  .  .  .  .  .  .  .  .
10      .  .  .  .  .  .  .  .  .  .
Jugada: <fila> <columna> <opción: U | M | D>
```

### controls
```console
n n U -> uncover
n n M -> marker
n n D -> unmarker
0 -> exit
```

