$Set n 2
$Set d 1
$Set maxd2 2


Sets
         i       D�as    /i0*i%d%/
         j       Num ciudades /j1*j%n%/
         l       Num puntos de interes de 1 a 2maxd
                             /l1*l%maxd2%/;
Alias(j,k)

Scalars
         inicio Ciudad de inicio
                 /1/
         n       Num ciudades
                 /2/
         d Num dias
                 /1/
         maxd Max num dias
                 /1/

         mind    Min num dias
                 /1/
         p1      Prioridad dias
                 /1/
         p2      Prioridad presupuesto
                 /0/;
Parameters
         Puntaje(j)      Puntaje del punto j
         CV(j)    Vida promedio por ciudad
         /j1 1, j2 1/
       ;
table CT(j,k)       Costos transporte
                 j1      j2
         j1      1       1
         j2      1       1
          ;
table S(j,l) Puntaje de ciudad i por punto de inter�s l
                 l1      l2
         j1      1       1
         j2      1       1
                 ;
table R(j,l) Cantidad de reviews de ciudad i por punto de interes l
                 l1      l2
         j1      1       1
         j2      1       1
                   ;

LOOP((j),Puntaje(j)=sum(l,S(j,l)*(R(j,l))**0.5/5););

Variables
         x(i,j)       En el dia i me quedo en la ciudad j
         f2           Costo de transporte
         obj       F objetivo;
Binary variable x;

Equations
         trans      Costo de transporte
         fObj    Funci�n objetivo
         rest_mind (j) Cota minima y maxima de dias
         rest_maxd (j) Cota minima y maxima de dias
         noDosciudades (i)   La persona no puede viajar a dos ciudades el mismo dia
         primerDia (j)      Primer dia de viaje
         ;

trans       ..      f2=e=sum((i,j,k)$(ord(i)<d and ord(k)<>ord(j)),x(i,j)*x(i+1,k)*CT(j,k)) ;
fObj     ..      obj=e=p1/(sum( (i,j),(x(i,j)+1) ))+p2*(f2+sum((i,j),x(i,j)*CV(j)) );
rest_mind (j)     ..      mind=l=sum(i,x(i,j));
rest_maxd (j)        ..      maxd=g=sum(i,x(i,j));
noDosCiudades (i)    ..     sum((j),x(i,j) )=e=1;
primerDia (j)$(ord(j)=inicio)       ..      x('i0',j)=g=1;

Model Modelo /all/;
option minlp = BARON                 ;
Solve Modelo using minlp minimizing obj ;
Display Puntaje;
Display obj.l;
Display x.l;

