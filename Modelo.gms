
$offlisting
$include C:\Users\Dell\Documents\UNIANDES\MOS\ProyectoMOS\paramsBase1.inc
$onlisting

$offlisting
$include C:\Users\Dell\Documents\UNIANDES\MOS\ProyectoMOS\tablesBase1.inc
$onlisting

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
option minlp = BONMIN                 ;
Solve Modelo using minlp minimizing obj ;
Display Puntaje;
Display obj.l;
Display x.l;

