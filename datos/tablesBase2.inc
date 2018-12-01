Parameters
	Puntaje(j) Puntaje del punto j
 	CV(j) Vida promedio por ciudad
	/j1 13157, j2 98657/;
table CT (j,k) Costos transporte
		j1	j2
	j1	62268	73580
	j2	61092	95479
;
table S (j,l) Puntaje de ciudad i por punto de interes
		l1	l2	l3	l4	l5	l6	l7	l8	l9	l10
	j1	50	0	0	0	0	0	0	0	0	0
	j2	5	0	0	0	0	0	0	0	0	0
;
table R (j,l) Cantidad de reviews de ciudad i por punto de interes
		l1	l2	l3	l4	l5	l6	l7	l8	l9	l10
	j1	1	1	1	1	1	1	1	1	1	1
	j2	1	1	1	1	1	1	1	1	1	1
;
LOOP((j),Puntaje(j)=sum(l,S(j,l)*(R(j,l))**0.5/5););