#!/bin/bas
jflex reglas.l
byaccj -J complejos.y
javac Parser.java 
javac Yylex.java
javac Symbol.java
javac Cadena.java
javac Functions.java
java Parser
