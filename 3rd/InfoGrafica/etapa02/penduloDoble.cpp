// Includes para linux
//#include <GL/glut.h>
//#include <GL/gl.h>
//#include <GL/glu.h>
#include <OpenGL/gl.h>
#include <OpenGL/glu.h>
#include <GLUT/glut.h>
#include <cmath>

float angle1 = 0.0; // Ángulo de rotación del primer hilo
float radius1 = 0.5; // Radio del primer péndulo
float x_fixed1 = 0.0; // Coordenada X del punto fijo del primer péndulo
float y_fixed1 = 0.8; // Coordenada Y del punto fijo del primer péndulo
bool forward1 = true; // Indica la dirección del primer péndulo

float angle2 = 0.0; // Ángulo de rotación del segundo hilo
float radius2 = 0.3; // Radio del segundo péndulo
float x_fixed2 = 0.0; // Coordenada X del punto fijo del segundo péndulo
float y_fixed2 = 0.3; // Coordenada Y del punto fijo del segundo péndulo
bool forward2 = true; // Indica la dirección del segundo péndulo

float newX, newY;

void display() {
    glClear(GL_COLOR_BUFFER_BIT);

    newX = x_fixed1 + radius1 * sin(angle1);
    newY = y_fixed1 - radius1 * cos(angle1);
    // Dibuja el primer hilo
    glLineWidth(2.0);
    glColor3f(0.0, 0.0, 0.0);
    glBegin(GL_LINES);
        glVertex2f(x_fixed1, y_fixed1);
        glVertex2f(newX, newY);
    glEnd();

    // Dibuja el segundo hilo
    glLineWidth(2.0);
    glColor3f(0.0, 0.0, 0.0);
    glBegin(GL_LINES);
        glVertex2f(x_fixed1 + radius1 * sin(angle1), y_fixed1 - radius1 * cos(angle1));
        glVertex2f(x_fixed2 + radius2 * sin(angle2), y_fixed2 - radius2 * cos(angle2));
    glEnd();

    // Dibuja la bola del primer péndulo
    glColor3f(1.0, 0.0, 0.0);
    glPushMatrix();
        glTranslatef(x_fixed1 + radius1 * sin(angle1), y_fixed1 - radius1 * cos(angle1), 0.0);
        glutSolidSphere(0.05, 20, 20);
    glPopMatrix();

    // Dibuja la bola del segundo péndulo
    glColor3f(0.0, 0.0, 1.0);
    glPushMatrix();
        glTranslatef(x_fixed2 + radius2 * sin(angle2), y_fixed2 - radius2 * cos(angle2), 0.0);
        glutSolidSphere(0.05, 20, 20);
    glPopMatrix();

    glutSwapBuffers();
}


void idle() {
    if(forward1){
        angle1 += 0.03; // Incrementa el ángulo de rotación del primer pendulo
    }else{
        angle1 -= 0.03;
    }

    if(forward2){
        angle2 += 0.03; // Incrementa el ángulo de rotación del segundo pendulo
    }else{
        angle2 -= 0.03;
    }

    // Cambia la dirección del primer péndulo al alcanzar el ángulo máximo
    if ((angle1 >= M_PI/3) && (forward1)){
        forward1 = false;
    }else if ((angle1 <= -(M_PI/3)) && (!forward1)){
        forward1 = true;
    }

    // Cambia la dirección del segundo péndulo al alcanzar el ángulo máximo
    if ((angle2 >= M_PI/3) && (forward2)){
        forward2 = false;
    }else if ((angle2 <= -(M_PI/3)) && (!forward2)){
        forward2 = true;
    }
    glutPostRedisplay();
}


int main(int argc, char** argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);
    glutInitWindowSize(640, 480);
    glutCreateWindow("Péndulo Doble");

    glClearColor(1.0, 1.0, 1.0, 1.0);

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluOrtho2D(-1.0, 1.0, -1.0, 1.0);

    glutDisplayFunc(display);
    glutIdleFunc(idle);

    glutMainLoop();
    return 0;
}
