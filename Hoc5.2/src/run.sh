#!/bin/bash
#Compiling Support Classes
javac InputText.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
javac Auxiliar.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
javac Fraction.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
javac Term.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
javac Polynomial.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
javac Symbol.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
javac SymbolTable.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
javac Sum.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
javac Sub.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
javac Mul.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
javac Div.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
javac Pow.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
javac Binomial.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
javac Geometric.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
javac Integral.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
javac Derivative.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
javac Functions.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
javac VerifyType.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
javac Machine.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build

#Generating Yylex and Parser classes
jflex -jlex LexicalAnalizer.l
byaccj -J -v SintAnalizer.y

#Compiling Parser and Yylex classes
javac Parser.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
javac Yylex.java -d /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build


#Cleaning Yylex.class file from code source
cp Yylex.class /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
rm Yylex.class
cp Parser.class  /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build
rm Parser.class

#Running project :D
java -cp /home/ESCOM/Documents/Repositorios/Compilers/Hoc5.2/build Parser
