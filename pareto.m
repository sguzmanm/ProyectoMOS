clc, clear all, close all

[iter, f1, f2] = textread('results.dat', '%s %f %f', 20);

figure
plot(f1,f2,'-o')
title('Pareto Front');
xlabel('f1');
ylabel('f2');