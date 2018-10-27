Parameters
        Puntaje(j) Puntaje del punto j
        CV(j) Vida promedio por ciudad
        /j1 1, j2 1, j3 1/;
table CT (j,k) Costos transporte
                j1      j2      j3
        j1      999999  10      100000
        j2      10      999999  2
        j3      9999    12      999999
;
table S (j,l) Puntaje de ciudad i por punto de interes
                l1      l2
        j1      3565    79658
        j2      34332   40916
        j3      78776   67441
;
table R (j,l) Cantidad de reviews de ciudad i por punto de interes
                l1      l2
        j1      64641   72354
        j2      84652   46839
        j3      26237   20862
;
LOOP((j),Puntaje(j)=sum(l,S(j,l)*(R(j,l))**0.5/5););
