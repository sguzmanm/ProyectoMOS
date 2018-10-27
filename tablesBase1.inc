Parameters
        Puntaje(j) Puntaje del punto j
        CV(j) Vida promedio por ciudad
        /j1 1, j2 1, j3 1, j4 1/;
table CT (j,k) Costos transporte
                j1      j2      j3      j4
        j1      1       1       1       1
        j2      1       1       1       1
        j3      1       1       1       1
        j4      1       1       1       1
;
table S (j,l) Puntaje de ciudad i por punto de interes
                l1      l2
        j1      1       1
        j2      1       1
        j3      1       1
        j4      1       1
;
table R (j,l) Cantidad de reviews de ciudad i por punto de interes
                l1      l2
        j1      1       1
        j2      1       1
        j3      1       1
        j4      1       1
;
LOOP((j),Puntaje(j)=sum(l,S(j,l)*(R(j,l))**0.5/5););
