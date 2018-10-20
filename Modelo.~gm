$Set n 4
$Set d 4
$Set maxd2 2


Sets
         i       Días    /i0*i%d%/
         j       Num ciudades /j1*j%d%/
         l       Num puntos de interes de 1 a 2maxd
                             /l1*l%maxd2%/;
Alias(j,k)

Scalars
         n       Num ciudades
                 /4/
         d Num dias
                 /4/
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
         /j1 1, j2 1, j3 1, j4 1/
       ;
table CT(j,k)       Costos transporte
                 j1      j2      j3      j4
         j1      1       1       1       1
         j2      1       1       1       1
         j3      1       1       1       1
         j4      1       1       1       1     ;
table S(j,l) Puntaje de ciudad i por punto de interés l
                 l1      l2
         j1      1       1
         j2      1       1
         j3      1       1
         j4      1       1         ;
table R(j,l) Cantidad de reviews de ciudad i por punto de interes l
                 l1      l2
         j1      1       1
         j2      1       1
         j3      1       1
         j4      1       1          ;

LOOP((j),Puntaje(j)=sum(l,S(j,l)*(R(j,l))**0.5/5););

Variables
         x(i,j)       En el dia i me quedo en la ciudad j
         f1              Obj 1
         f2              Obj 2.1
         f3              Obj 2.2
         obj       F objetivo;
Binary variable x;

Equations
         r1      Num dias total
         r2      Costo de transporte
         r3      Costo de vida promedio
         fObj    Función objetivo
         rest_mind (j) Cota minima y maxima de dias
         rest_maxd (j) Cota minima y maxima de dias
         noDosciudades (i)   La persona no puede viajar a dos ciudades el mismo dia
         primerDia       Primer dia de viaje
         ;
r1       ..      f1=e=sum((i,j),(x(i,j)*Puntaje(j)+1));
r2       ..      f2=e=sum((i,j,k)$(ord(i)<d and ord(k)<>ord(j)),x(i,j)*x(i+1,k)*CT(j,k)) ;
r3       ..      f3=e=sum((i,j),x(i,j)*CV(j))      ;
fObj     ..      obj=e=p1/f1+p2*(f2+f3);
rest_mind (j)     ..      mind=l=sum(i,x(i,j));
rest_maxd (j)        ..      maxd=g=sum(i,x(i,j));
noDosCiudades (i)    ..     sum((j),x(i,j) )=e=1;
primerDia        ..      x('i0','j1')=g=0+0.00000001;

Model Modelo /all/;
option minlp = BARON                 ;
Solve Modelo using minlp minimizing obj ;
Display Puntaje;
Display f1.l;
Display obj.l;
Display x.l;

