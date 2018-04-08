#!/bin/bash
#Compiling classes
javac Chain.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc3/build
javac Tokenizer.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc3/build
javac Symbol.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc3/build
javac SymbolTable.java -d  /home/ESCOM/Documents/Repositorios/Compilers/Hoc3/build
javac Term.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc3/build
javac Fraction.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc3/build
javac Polynomial.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc3/build
jflex Analizadorlex.l
byaccj -J Analizadorsin.y
javac Parser.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc3/build
javac Yylex.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc3/build
java -cp /home/ESCOM/Documents/Repositorios/Compilers/Hoc3/build Parser
