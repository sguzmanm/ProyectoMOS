
$offlisting
$include C:\Users\Dell\Documents\UNIANDES\MOS\ProyectoMOS\datos\paramsBase4.inc
$onlisting

$offlisting
$include C:\Users\Dell\Documents\UNIANDES\MOS\ProyectoMOS\datos\tablesBase4.inc
$onlisting

Variables
         num_f1
         num_f2
         den_f1
         den_f2
         x(i,j)       En el dia i me quedo en la ciudad j
         f1           Costo dias
         f2           Costo de transporte
         f3           Costo vida promedio
         obj       F objetivo;
Binary variable x;

Equations
         r1
         r2
         r3
         r4
         dias    Costo de dias
         vidaProm        Costo de vida promedio
         trans      Costo de transporte
         fObj    Función objetivo
         noDosciudades (i)   La persona no puede viajar a dos ciudades el mismo dia
         primerDia (j)      Primer dia de viaje
         rest_mind (j) Cota minima y maxima de dias
         rest_maxd (j) Cota minima y maxima de dias;

r1       ..    (smin(j,Puntaje(j))*d+1)=e=num_f1;
r2       ..    (sum( (i,j),(x(i,j)*Puntaje(j)) )+1)=e=den_f1;
r3       ..    ( sum((i,j,k)$(ord(i)<d and ord(k)<>ord(j)),x(i,j)*x(i+1,k)*CT(j,k)) + sum((i,j),x(i,j)*CV(j)) )=e=num_f2;
r4       ..    (smax(j,CV(j))*d+smax((j,k),CT(j,k))*d)=e=den_f2;
trans       ..      f2=e=sum((i,j,k)$(ord(i)<d and ord(k)<>ord(j)),x(i,j)*x(i+1,k)*CT(j,k)) ;
dias     ..      f1=e=(smin(j,Puntaje(j))*d+1)/(sum( (i,j),(x(i,j)*Puntaje(j)) )+1);
vidaProm ..      f3=e=sum((i,j),x(i,j)*CV(j));
fObj     ..      obj=e=p1*f1+p2*(f2+f3 )/(smax(j,CV(j))*d+smax((j,k),CT(j,k))*d);
rest_mind (j)     ..      sum(i,x(i,j))*( sum(i,x(i,j))-mind )=g=0;
rest_maxd (j)        ..    maxd=g=sum(i,x(i,j));
noDosCiudades (i)    ..     sum((j),x(i,j) )=e=1;
primerDia (j)$(ord(j)=inicio)       ..      x('i0',j)=e=1;

Model Modelo /all/;
option minlp = COUENNE                 ;
Solve Modelo using minlp minimizing obj ;
Display CV;
Display Puntaje;
Display R;
Display S;
Display inicio;
Display mind;
Display maxd;
Display obj.l;
Display f1.l;
Display f2.l;
Display f3.l;
Display num_f1.l;
Display num_f2.l;
Display den_f1.l;
Display den_f2.l;
Display x.l;

