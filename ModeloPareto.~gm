
$offlisting
$include C:\Users\Dell\Documents\UNIANDES\MOS\ProyectoMOS\paramsMedium.inc
$onlisting

$offlisting
$include C:\Users\Dell\Documents\UNIANDES\MOS\ProyectoMOS\tablesMedium.inc
$onlisting


Sets
         iter /it1*it11/;

Parameters
         p1_vec(iter) /it1 0, it2 0.1, it3 0.2, it4 0.3, it5 0.4, it6 0.5, it7 0.6, it8 0.7, it9 0.8, it10 0.9, it11 1/
         p2_vec(iter)
         obj_res(iter)
         f1_res(iter)
         f2_res(iter)
         x_res(i,j,iter);
Variables
         x(i,j)       En el dia i me quedo en la ciudad j
         f1           Maximizar dias
         f2           Costos
         obj       F objetivo;
Binary variable x;

Equations
         dias    Costo de dias
         trans      Costo de transporte
         fObj    Función objetivo
         noDosciudades (i)   La persona no puede viajar a dos ciudades el mismo dia
         primerDia (j)      Primer dia de viaje
         rest_mind (j) Cota minima y maxima de dias
         rest_maxd (j) Cota minima y maxima de dias;


trans       ..      f2=e=sum((i,j,k)$(ord(i)<d and ord(k)<>ord(j)),x(i,j)*x(i+1,k)*CT(j,k)) + sum((i,j),x(i,j)*CV(j)) ;
dias     ..      f1=e=sum( (i,j),(x(i,j)+1) );
fObj     ..      obj=e=p1/(sum( (i,j),(x(i,j)+1) ))+p2*(f2 );
rest_mind (j)     ..      sum(i,x(i,j))*( sum(i,x(i,j))-mind )=g=0;
rest_maxd (j)        ..    maxd=g=sum(i,x(i,j));
noDosCiudades (i)    ..     sum((j),x(i,j) )=e=1;
primerDia (j)$(ord(j)=inicio)       ..      x('i0',j)=e=1;

Model Modelo /all/;
LOOP(iter,
p1=p1_vec(iter);
p2=1-p1;
p2_vec(iter)=p2;

option minlp = BONMIN                 ;
Solve Modelo using minlp minimizing obj ;

obj_res(iter)=obj.l;
f1_res(iter)=f1.l;
f2_res(iter)=f2.l;
x_res(i,j,iter)=x.l(i,j);
);

Display obj_res;
Display f1_res;
Display f2_res;
Display x_res;

File ModeloPareto /C:\Users\Dell\Documents\UNIANDES\MOS\ProyectoMOS\results.dat/;
Put ModeloPareto
LOOP(iter,put /iter.tl,@5,f1_res(iter),@18,f2_res(iter) ;);

