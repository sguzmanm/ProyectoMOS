
$offlisting
$include C:\Users\Dell\Documents\UNIANDES\MOS\ProyectoMOS\paramsMedium.inc
$onlisting

$offlisting
$include C:\Users\Dell\Documents\UNIANDES\MOS\ProyectoMOS\tablesMedium.inc
$onlisting

Variables
         x(i,j)       En el dia i me quedo en la ciudad j
         f1           Costo dias
         f2           Costo de transporte
         f3           Costo vida promedio
         obj       F objetivo;
Binary variable x;

Equations
         dias    Costo de dias
         vidaProm        Costo de vida promedio
         trans      Costo de transporte
         fObj    Función objetivo
         noDosciudades (i)   La persona no puede viajar a dos ciudades el mismo dia
         primerDia (j)      Primer dia de viaje
         rest_mind (j) Cota minima y maxima de dias
         rest_maxd (j) Cota minima y maxima de dias;


trans       ..      f2=e=sum((i,j,k)$(ord(i)<d and ord(k)<>ord(j)),x(i,j)*x(i+1,k)*CT(j,k)) ;
dias     ..      f1=e=sum( (i,j),(x(i,j)+1) );
vidaProm ..      f3=e=sum((i,j),x(i,j)*CV(j));
fObj     ..      obj=e=p1/(sum( (i,j),(x(i,j)+1) ))+p2*(f2+f3 );
rest_mind (j)     ..      mind=l=sum(i,x(i,j));
rest_maxd (j)        ..    maxd=g=sum(i,x(i,j));
noDosCiudades (i)    ..     sum((j),x(i,j) )=e=1;
primerDia (j)$(ord(j)=inicio)       ..      x('i0',j)=e=1;

Model Modelo /all/;
option minlp = BONMIN                 ;
Solve Modelo using minlp minimizing obj ;
Display CV;
Display Puntaje;
Display obj.l;
Display f1.l;
Display f2.l;
Display f3.l;
Display x.l;

