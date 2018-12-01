Parameters
	Puntaje(j) Puntaje del punto j
 	CV(j) Vida promedio por ciudad
	/j1 83289, j2 79215, j3 16874, j4 55785/;
table CT (j,k) Costos transporte
		j1	j2	j3	j4
	j1	17735	80283	37545	37428
	j2	91046	40287	65259	38367
	j3	55632	16582	38304	41702
	j4	86178	32764	87208	47221
;
table S (j,l) Puntaje de ciudad i por punto de interes
		l1	l2
	j1	40718	11572
	j2	42942	28574
	j3	31778	6383
	j4	64425	28561
;
table R (j,l) Cantidad de reviews de ciudad i por punto de interes
		l1	l2
	j1	22680	18859
	j2	10305	83797
	j3	64148	9748
	j4	35042	91177
;
LOOP((j),Puntaje(j)=sum(l,S(j,l)*(R(j,l))**0.5/5););