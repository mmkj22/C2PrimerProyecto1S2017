SET JAVA_HOME="C:\Program Files\Java\jdk1.8.0_112\bin"
SET PATH=%JAVA_HOME%;%PATH%
SET CLASSPATH=%JAVA_HOME%;
cd C:\Users\Kristhal\Documents\NetBeansProjects\[Compi2]Proyecto1_201314655\src\Analisis
java -classpath C:\Cup\ java_cup.Main   -parser sintacticoHaskell -symbols symH parserHaskell.cup
pause


