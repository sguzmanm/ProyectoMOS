Parameters
	Puntaje(j) Puntaje del punto j
 	CV(j) Vida promedio por ciudad
	/j1 1, j2 10/;
table CT (j,k) Costos transporte
		j1	j2
	j1	1	1
	j2	1	1
;
table S (j,l) Puntaje de ciudad i por punto de interes
		l1	l2	l3	l4	l5	l6	l7	l8	l9	l10
	j1	36031	83132	95377	95014	79563	49380	77955	57171	53755	13063
	j2	26556	92787	21920	68838	55825	98165	70798	94861	44033	81638
;
table R (j,l) Cantidad de reviews de ciudad i por punto de interes
		l1	l2	l3	l4	l5	l6	l7	l8	l9	l10
	j1	6612	73261	44233	75854	89453	53850	64035	36580	94346	87078
	j2	46563	81339	4851	45121	71421	70694	74668	94335	47491	78439
;
LOOP((j),Puntaje(j)=sum(l,S(j,l)*(R(j,l))**0.5/5););