#!/bin/sh
rm main.ali main.o main def_maitre.ali def_maitre.o
gnatmake main.adb
./main