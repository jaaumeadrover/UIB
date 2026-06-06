gcc -Wno-deprecated-declarations -o $1 $1.cpp -framework GLUT -framework OpenGL -framework Carbon -lstdc++
./$1