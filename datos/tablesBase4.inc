Parameters
	Puntaje(j) Puntaje del punto j
 	CV(j) Vida promedio por ciudad
	/j1 1, j2 1, j3 1, j4 1/;
table CT (j,k) Costos transporte
		j1	j2	j3	j4
	j1	999999	10	100000	999999
	j2	10	999999	2	999999
	j3	9999	12	999999	1
	j4	999999	999999	999999	999999
;
table S (j,l) Puntaje de ciudad i por punto de interes
		l1	l2
	j1	46898	53827
	j2	55970	82657
	j3	50815	2210
	j4	60601	19379
;
table R (j,l) Cantidad de reviews de ciudad i por punto de interes
		l1	l2
	j1	54846	27744
	j2	92988	41897
	j3	74556	44467
	j4	28217	93515
;
LOOP((j),Puntaje(j)=sum(l,S(j,l)*(R(j,l))**0.5/5););