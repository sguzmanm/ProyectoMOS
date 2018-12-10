# ProyectoMOS
Proyecto creado para subir el código y los archivos relacionados con el proyecto de MOS. 
##Estudiantes: 
Julián Manrique: 201615449
Sergio Guzmán: 2016148
## Estructura
Las herramientas utilizadas fueron: 
Flask para generar los archivos de escenarios para GAMS y Java.
Matlab para la visualiación de los frentes con los archivos results.dat y resultsJava.dat. (pareto.m)
Java para la metaheurística con el algoritmo SPEA2, el cual genera el archivo resultsJava.dat. (Carpeta SPA, clase SPEA2.java)
GAMS para el modelo con el frente de pareto, usando una variación de pesos ponderados, el cual genera el archivo results.dat. (ModeloPareto.gms)
## Inspiraciòn princip59al
Algoritmo SPEA 2: https://pdfs.semanticscholar.org/6672/8d01f9ebd0446ab346a855a44d2b138fd82d.pdf
## Implementación SPEA 2
Esta implementación se realizó en Java, en la carpeta SPEA y en el main de la clase SPEA2.java se tiene la siguiente línea de codigo: 
SPEA2 spea=scenario(30,6,5000,1,3);
La cual consiste de los siguientes parámetros: 
    N=30
    Np=6
    T=5000
    kp=1
    Id del escenario=3
N hace referencia al tamaño de la población.
Np hace referencia al tamaño del pool de soluciones óptimas a guardar
T Número de generaciones
kp Oarámetro apra partición del mating pool
El id del escenario es un núemro que varía entre 1 y 6, donde se tienen los 4 escenarios base (de 1 a 4), el escenario intermedio (5) y el real (6). 
