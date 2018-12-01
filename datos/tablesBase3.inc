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
	j1	72115	49611	26925	82901	80499	46383	81886	21580	29533	49168
	j2	7570	7622	86730	34447	97876	49161	78125	3534	73984	59887
;
table R (j,l) Cantidad de reviews de ciudad i por punto de interes
		l1	l2	l3	l4	l5	l6	l7	l8	l9	l10
	j1	74724	2651	64483	77528	44978	77317	29101	90897	84349	87301
	j2	48872	82608	99201	64905	40917	12913	32211	99679	15655	57246
;
LOOP((j),Puntaje(j)=sum(l,S(j,l)*(R(j,l))**0.5/5););