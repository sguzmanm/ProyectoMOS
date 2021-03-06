clc, clear all, close all

[iter, f1, f2] = textread('results.dat', '%s %f %f', 20);

figure (1);
scatter(f1,f2);
plot(f1,f2,'-o');
title('Pareto Front GAMS');
xlabel('f1');
ylabel('f2');
axis([0 1 0 1]);


[iter, f1, f2] = textread('resultsJava.dat', '%s %f %f', 20);

figure (2);
scatter(f1,f2);
plot(f1,f2,'-o');
title('Pareto Front SPEA2');
xlabel('f1');
ylabel('f2');
axis([0 1 0 1]);

