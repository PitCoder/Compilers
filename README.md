# Compilers
> This repository contains the construction of a basic compiler step by step, along with one interpreter that has been implemented on a robot's control. All topics viewed during the compilers course 2018-2 ESCOM IPN are covered.

### Content
- Introduction
- Hoc Compiler Construction
- GramBot (On Site Programable Vehicle)
- Team
- License

### Introduction

#### What is a Compiler?
Is a program that translates and executable program in one language into an executable program in another language, we expect the program produced by the compiler to be better, in some way, to the original.

<p align="center">
  <img src="https://codon.com/images/compilers-for-free/compilation.gif" alt="Compilers"/>
</p>

#### What is an Interpreter?
Is a program that reads an executable program and produces the results of running that program. Usually, this involves executing the source program in somefashion.

<p align="center">
  <img src="https://codon.com/images/compilers-for-free/interpretation.gif" alt="Interpretation"/>
</p>

<p align="center">
  <img src="https://codon.com/images/compilers-for-free/venn-diagram.gif" alt="Difference"/>
</p>

#### Why study a compiler construction?
Compiler construction is a microcosm of computer science where things like artificial intelligence, algorithms, computational theory, sistems and computer architectures all come together. Even if compilers are a well study subject, they are constantly changing by adding new features to pose new problems, changing costs and re-engineering of old solutions.

#### Hoc Compiler Construction:
The construction of a Hoc compiler involves:
  - Lexical Analizer
  - Grammar Parsing
  - Semantic Analysis 
  - Translation and simplification
  
All these topics are implemented in the Hoc folders, where an implementation of a Hoc Compiler applied to polynomials has been made. The compiler allows:
  - All basic arithmetic operations (e.g addition, substraction, etc...)
  - Some statistic operations (e.g mean, middle, mode, etc...)
  - Integration and Differentiation over polynomials.
  - All basic components for programming (e.g decisions, cycles, conditions, memory and so more)
  
  
### GramBot (On Site Programable Vehicle)
A robot vehicle which can be remotely programmable on site from a desktop application, the robot uses its own interpreter allowing it to be customizable by modifying robot’s command set in an easy way. Hardware components of the robot were programed using C++ and Arduino, middleware communication using Python 3 and the interpreter using Java along with Lex and Yacc.

<p align="center">
  <img src="https://raw.githubusercontent.com/PitCoder/Compilers/master/Img/IMG_4836.JPG" alt="Grambot"/>
</p>

#### Codebase
Grambot's codebase is within the folder "Grambot/codebase/", the folder contains the frontend and backend of the application: interface, interpreter and server comunication in Java, middleware communication in Python and finally hardware implementation in Arduino files.  

#### Demonstration
<p align="center">
  <img src="https://raw.githubusercontent.com/PitCoder/Compilers/master/Img/demo.gif" alt="Demostration"/>
</p>

### Team

> This is the team that made Grambot possible:

| <a href="https://github.com/ccruz182" target="_blank">**César Cruz Arredondo**</a> | <a href="https://github.com/jonathanoleaz" target="_blank">**Jonathan Olea Zuñiga**</a> | <a href="https://github.com/PitCoder" target="_blank">**Eric Alejandro López Ayala**</a> | <a href="https://github.com/luisfig" target="_blank">**Luis Figeroa Romero**</a> |
| :---: |:---:| :---:| :---:|
| [![César Cruz Arredondo](https://avatars1.githubusercontent.com/u/28882910?s=200&v=2)](https://github.com/ccruz182)    | [![Jonathan Olea Zuñiga](https://avatars3.githubusercontent.com/u/21295348?s=200&v=2)](https://github.com/jonathanoleaz) | [![Eric Alejandro López Ayala](https://avatars3.githubusercontent.com/u/22123865?s=200&v=2)](https://github.com/PitCoder)  | [![Luis Figeroa Romero](https://avatars2.githubusercontent.com/u/31828147?s=200&v=2)](https://github.com/luisfig) |
| <p>Python, Java and Arduino Developer</p> | <p>Java and Python Developer</p> | <p>Java, Python and Arduino Developer</p> | <p>Java and Python Developer</p> |

### License

[![License](http://img.shields.io/:license-mit-blue.svg?style=flat-square)](https://github.com/PitCoder/Compilers/blob/master/LICENSE)

- **[MIT license](https://github.com/PitCoder/Compilers/blob/master/LICENSE)**
- Copyright 2018 © <a href="https://github.com/PitCoder" target="_blank">Eric Alejandro López Ayala</a>
<a href="https://github.com/ccruz182" target="_blank">César Cruz Arredondo</a>
<a href="https://github.com/jonathanoleaz" target="_blank">Jonathan Olea Zuñiga</a>
<a href="https://github.com/luisfig" target="_blank">Luis Figeroa Romero</a>.


