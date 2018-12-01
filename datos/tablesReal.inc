Parameters
	Puntaje(j) Puntaje del punto j
 	CV(j) Vida promedio por ciudad
	/j1 648.61
, j2 754.47
, j3 640.90
, j4 671.74
, j5 603.43
, j6 610
, j7 620
, j8 600.4
, j9 709.5
, j10 689
, j11 755.40
, j12 670
, j13 650
, j14 806.37
, j15 715.40
, j16 760
/;
table CT (j,k) Costos transporte
		j1	j2	j3	j4	j5	j6	j7	j8	j9	j10	j11	j12	j13	j14	j15	j16
	j1	0	29	55	68	75	47.5	67	45	42	53.5	32.25	25.5	38	60.5	47	64.5
	j2	29	0	72.5	67	72.5	70	86	68.5	60	71.5	51	32	37.5	52.5	59.5	57.5
	j3	55	72.5	0	65	34	32	24.5	27.5	18	40	42	42.5	32.5	70.5	75.5	60
	j4	40	57.5	33	0	24.5	28	40	28.5	42.5	71.5	40	75.5	85	85	66.5	62.5
	j5	47	62.5	24.5	20	0	28.5	27.5	29	51.5	52.5	42	105	70	78	56	80
	j6	47.5	65	27.5	28.5	28.5	0	18.5	36	23	46.5	23.5	92.5	52	52.5	40	68
	j7	84.5	89.5	18	39.5	27.5	26.5	0	45	48.5	47.5	41	91	75.5	79.5	61.5	89
	j8	45	72.5	35	28	47.5	32.5	45	0	68.5	75	48.5	82	80	89	56.5	90.5
	j9	18	57	51	43.5	64	35	58	43	0	32	12	56.5	49	51	30	53.5
	j10	55.5	78	45	50	52.5	46	51.5	73	33.5	0	21.5	38	73	61.5	19.5	40
	j11	35	54.5	34.5	44	41.5	25.5	40	35.5	15	24.5	0	37.5	51.5	42	10.5	44.5
	j12	25.5	20	42.5	75.5	105	92.5	91	82	56.5	38	37.5	0	25	36	36	40
	j13	38	37.5	75.5	85	70	52	75.5	80	49	73	51.5	25	0	19	50.5	32.5
	j14	60.5	52.5	60	85	78	52.5	79.5	89	51	61.5	42	36	19	0	44	23
	j15	47	59.5	47	66.5	56	40	61.5	56.5	30	19.5	10.5	36	50.5	44	0	51.5
	j16	64.5	57.5	65	62.5	80	68	89	90.5	53.5	40	44.5	40	32.5	23	51.5	0
;
table S (j,l) Puntaje de ciudad i por punto de interes
		l1	l2	l3	l4	l5	l6	l7	l8	l9	l10
	j1	4.6	4.5	4.5	4.5	4.5	4.6	4.5	4.6	4.5	4.4
	j2	4.7	4.4	4.5	4.6	4.4	4.5	4.6	4.6	4.5	4.7
	j3	4.7	4.7	4.8	4.7	4.3	4.7	4.3	4.3	4.6	4.2
	j4	4.7	4.7	4.4	4.4	4.8	4.6	4.4	4.6	4.6	4.2
	j5	4.6	4.2	4.5	4.5	4.4	4.3	4.6	4.5	4.5	3.9
	j6	4.7	4.6	4.5	4.5	4.4	4.4	4.3	4.1	4.4	4.5
	j7	4.5	4.4	4.4	4.5	4.4	4.5	4.5	4.3	4.5	4.7
	j8	4.5	4.4	4.2	4.4	4.5	4.1	4.4	4.5	4.5	4.4
	j9	4.5	4.6	4.5	4.6	4.6	4.5	4.2	4.3	4.4	4.4
	j10	4.7	4.6	4.5	4.4	4.4	4.6	4.3	4.6	4.4	4.8
	j11	4.6	4.7	4.7	4.5	4.5	4.5	4.4	4.6	4.3	4.6
	j12	4.7	4.5	4.6	4.7	4.4	4.6	4.6	4.4	4.2	4.6
	j13	4.4	4.5	4.2	4.4	4.6	4.5	4.4	4.4	4.6	4.3
	j14	4.5	4.5	4.1	4.4	4.2	4.4	4.4	4.5	4.4	4.3
	j15	4.7	4.8	4.5	4.5	4.5	4.4	4.3	4.6	4.5	4.3
	j16	4.5	4.6	4.2	4.6	4.1	4.3	4.3	4.4	3.9	4.7
;
table R (j,l) Cantidad de reviews de ciudad i por punto de interes
		l1	l2	l3	l4	l5	l6	l7	l8	l9	l10
	j1	36284	16750	2928	11635	1886	19406	10306	8778	8992	10036
	j2	25567	53779	24514	32843	55756	40953	56553	26799	5312	11469
	j3	16441	4350	36177	5671	6992	3119	17316	5662	4459	6599
	j4	32437	2220	2075	1731	290	732	443	591	221	1327
	j5	6990	6543	1579	2289	6905	1794	1223	1724	328	769
	j6	6521	4075	8833	3095	1192	1618	5032	2109	793	430
	j7	4208	2256	1575	1923	1642	508	216	899	1076	334
	j8	3398	1868	472	546	830	427	1522	46	173	160
	j9	8731	2107	1061	2258	2019	1777	1515	353	398	294
	j10	18791	1339	453	1682	2922	1001	1016	346	97	227
	j11	15168	32517	50370	44352	63650	13610	9192	10810	38282	11067
	j12	2371	6588	471	7756	867	1466	4514	2021	548	4754
	j13	7149	3932	3669	344	646	1576	1388	291	217	1334
	j14	21480	2732	1406	5107	409	6168	5240	288	1731	2831
	j15	11435	25393	6371	1236	2005	678	662	83	145	275
	j16	6703	3390	2782	155	792	1422	2193	1091	1600	135
;
LOOP((j),Puntaje(j)=sum(l,S(j,l)*(R(j,l))**0.5/5););