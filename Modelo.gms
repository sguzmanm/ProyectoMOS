$Set n 4
$Set d 4
$Set maxd2 2

$macro f(a) 1$(a>0)


Sets
         i       Num ciudades    /i1*i%n%/
         k       Num dias mas uno /k0*k%d%/
         l       Num puntos de interes de 1 a 2maxd
                             /l1*l%maxd2%/;

Alias (i,z);
Alias (i,j);
Alias(k,m);

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
         Puntaje(i)      Puntaje del punto i
         CV(i)    Vida promedio por ciudad
         /i1 1, i2 1, i3 1, i4 1/
       ;
table CT(i,j)       Costos transporte
                 i1      i2      i3      i4
         i1      1       1       1       1
         i2      1       1       1       1
         i3      1       1       1       1
         i4      1       1       1       1     ;
table S(i,l) Puntaje de ciudad i por punto de inter�s l
                 l1      l2
         i1      1       1
         i2      1       1
         i3      1       1
         i4      1       1         ;
table R(i,l) Cantidad de reviews de ciudad i por punto de interes l
                 l1      l2
         i1      1       1
         i2      1       1
         i3      1       1
         i4      1       1          ;

LOOP((i),Puntaje(i)=sum(l,S(i,l)*(R(i,l))**0.5/5););

Variables
         x(i,j,k)        Decidir que dias quedarse y en donde
         obj       F objetivo;
Positive variable x;

Equations
         fObj
         rest_mind (i,j) Cota minima y maxima de dias
         rest_maxd (i,j) Cota minima y maxima de dias
         noDosciudades (i,j,k)   La persona no puede viajar a dos ciudades el mismo dia
         primerDia       Primer dia de viaje
         regreso(i,k)         No es posible viajar a la misma ciudad de donde se sali�
         continuidad (i,j,k,m)     Hay continuidad en el trayecto planteado;

fObj     ..      obj=e=sum((i,j,k),p1*f(x(i,j,k))*Puntaje(j)+p2/((CT(i,j)+CV(j))*f(x(i,j,k)) ));
rest_mind (i,j)$(sum(k,x(i,j,k)) ne 0)    ..      mind=l=sum(k,x(i,j,k));
rest_maxd (i,j)$(sum(k,x(i,j,k)) ne 0)    ..      maxd=g=sum(k,x(i,j,k));
noDosCiudades (i,j,k)$(x(i,j,k) ne 0)    ..     sum((z),f(x(i,z,k)) )=e=1;
primerDia        ..      x('i1','i1','k0')=g=0+0.00000001;
regreso (i,k)$(ord(k)>0)    ..   x(i,i,k)=e=0;
continuidad(i,j,k,m)$(x(i,j,k)>0 and ((ord(k)+x(i,j,k))=ord(m))
                 and ord(m)<=d)  ..  sum(z,f(x(j,z,m)))=e=1;
Model Modelo /all/;
option nlp = BARON                 ;
Solve Modelo using nlp maximizing obj ;
Display obj.l;
Display x.l;

