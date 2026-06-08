// Etapa4.cpp
// Fichero principal
////////////////////////////////////////////////////

// Includes para linux
//#include <GL/glut.h>
//#include <GL/gl.h>
//#include <GL/glu.h>

// Includes para Mac
#include <OpenGL/gl.h>
#include <OpenGL/glu.h>
#include <GLUT/glut.h>

#include <math.h>
#include <iostream>

//CONSTANTES
const int W_WIDTH = 500; // Tamañoo incial de la ventana
const int W_HEIGHT = 500;
GLfloat fAngulo; // Variable que indica el ángulo de rotación de los ejes.
GLfloat size;

float cameraTheta = 0.2; // ángulo de la cámara alrededor del objeto
float cameraPhi = 0.5;   // ángulo de la cámara respecto al eje vertical
float cameraRadius = 5; // distancia de la cámara al objeto

GLfloat cameraX = cameraRadius * sin(cameraTheta) * sin(cameraPhi);
GLfloat cameraY = cameraRadius * cos(cameraPhi);
GLfloat cameraZ = cameraRadius * cos(cameraTheta) * sin(cameraPhi);

GLfloat centerX = 0.0f;
GLfloat centerY = 0.0f;
GLfloat centerZ = 0.0f;

bool mode = false; //false = modo vista libre 
				   //true = modo vista donde se pueden cambira los parametros manualmente


// Función que visualiza la escena OpenGL
void Display(void)
{ 
    glEnable(GL_DEPTH_TEST);

	// Borramos la escena
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	glMatrixMode (GL_PROJECTION);
   	glLoadIdentity();
	//glOrtho(-0.1, 0.1, -0.1, 0.1, 0.1, 5.0);
   	//glFrustum(-0.1, 0.1, -0.1, 0.1, 0.3, 5.0);
	//gluPerspective(45.0, 1.0, 1.0, 100.0);
	gluPerspective(40.0f, W_HEIGHT / W_WIDTH, 1.0f, 100.0f);

   	glMatrixMode(GL_MODELVIEW);
	if(!mode){
		cameraX = cameraRadius * sin(cameraTheta) * sin(cameraPhi);
 		cameraY = cameraRadius * cos(cameraPhi);
		cameraZ = cameraRadius * cos(cameraTheta) * sin(cameraPhi);
	}
    glLoadIdentity();
    gluLookAt(cameraX, cameraY, cameraZ,  // eye position
              centerX, centerY, centerZ,  // look at position
              0.0, 1.0, 0.0); // up vector
	
	glPushMatrix();

    //Axis X RED
	glPushMatrix();
		glLineWidth(2.0);
		glBegin(GL_LINES);
            glColor3f(1.0f, 0.0f, 0.0f);
            glVertex3f(-1.0f, 0.0f, 0.0f);
            glColor3f(1.0f, 0.0f, 0.0f);
            glVertex3f(1.0f, 0.0f, 0.0f);
        glEnd();

        //Axis Y GREEN
        glBegin(GL_LINES);
            glColor3f(0.0f, 1.0f, 0.0f);
            glVertex3f(0.0f, -1.0f, 0.0f);
            glColor3f(0.0f, 1.0f, 0.0f);
            glVertex3f(0.0f, 1.0f, 0.0f);
        glEnd();

        //Axis Z BLUE
        glBegin(GL_LINES);
            glColor3f(0.0f, 0.0f, 1.0f);
            glVertex3f(0.0f, 0.0f, -1.0f);
            glColor3f(0.0f, 0.0f, 1.0f);
            glVertex3f(0.0f, 0.0f, 1.0f);
        glEnd();
	glPopMatrix();

	//FIGURAS DE LA ESCENA
	//(-x, y, -z)
    glPushMatrix();
		//glTranslatef(0.0, 0.0, 0.0);
        glColor3f(0.5f, 0.4f, 0.7f);
        glutWireTeapot(0.2);
    glPopMatrix();

	glPopMatrix();

	for(float i = -100; i < 100; i += 1.1){
		glColor3f(0.0, 0.0, 0.0);
		glBegin(GL_LINE_STRIP);
		glVertex3f(-100, 0, i);
		glVertex3f(100, 0, i);
		glEnd();
	}

	for(float i = -100; i < 100; i += 1.1){
		glColor3f(0.0, 0.0, 0.0);
		glBegin(GL_LINE_STRIP);
		glVertex3f(i, 0, -100);
		glVertex3f(i, 0, 100);
		glEnd();
	}

	glFlush();
	glutSwapBuffers();
}

void reshape(int width, int height)
{

	if (width > height)
	{
		glViewport((width - height) / 2, 0, height, height);
	}
	else
	{
		glViewport(0, (height - width) / 2, width, width);
	}

	// Volver a la matriz de modelo-vista
	glMatrixMode(GL_MODELVIEW);
}

// Funci�n que se ejecuta cuando el sistema no esta ocupado
void Idle(void)
{
	
	glutPostRedisplay();
}

// Función para manejar las teclas del teclado
void specialKeys(int key, int x, int y) {
    switch(key) {
		case GLUT_KEY_LEFT:  // rotar la cámara hacia la izquierda
            cameraTheta += 0.1f;
            break;
        case GLUT_KEY_RIGHT: // rotar la cámara hacia la derecha
            cameraTheta -= 0.1f;
            break;
        case GLUT_KEY_UP:    // rotar la cámara hacia arriba
            cameraPhi += 0.1f;
            break;
        case GLUT_KEY_DOWN:  // rotar la cámara hacia abajo
            cameraPhi -= 0.1f;
            break;
    }
}

void keys(unsigned char key, int x, int y) {
	switch(key){
		//Modificación de parametros del GlLookAt()
		//CAMERA
		case 109:// con la m cambiamos de modo
			if(mode == true){
				mode = false;
			}else{
				mode = true;
			}
			break;
		case 113: //Codigo ASCII de la q
			cameraX += 0.2f;
			break;
		case 119: //Codigo ASCII de la w
			cameraX -= 0.2f;
			break;
		case 97: //Codigo ASCII de la a
			cameraY += 0.2f;
			break;
		case 115: //Codigo ASCII de la s
			cameraY -= 0.2f;
			break;
		case 122: //Codigo ASCII de la z
			cameraZ += 0.2f;
			break;
		case 120: //Codigo ASCII de la x
			cameraZ -= 0.2f;
			break;
		//CENTER
		case 101: //Codigo ASCII de la e
			centerX += 0.2f;
			break;
		case 114: //Codigo ASCII de la r
			centerX -= 0.2f;
			break;
		case 100: //Codigo ASCII de la d
			centerY += 0.2f;
			break;
		case 102: //Codigo ASCII de la f
			centerY -= 0.2f;
			break;
		case 99: //Codigo ASCII de la c
			centerZ += 0.2f;
			break;
		case 118: //Codigo ASCII de la v
			centerZ -= 0.2f;
			break;
		case 49: //Perfil //Tecla 1
			cameraX = 3.0f;
			cameraY = 0.0f;
			cameraZ = 1.5f;
			centerX = 0.0f;
			centerY = 0.0f;
			centerZ = 0.0f;
			break;
		case 50: //Alçada //Tecla 2
			cameraX = 0.1f;
			cameraY = 3.0f;
			cameraZ = 0.0f;
			centerX = 0.0f;
			centerY = 0.0f;
			centerZ = 0.0f;
			break;
		case 51: //Planta //Tecla 3
			cameraX = 3.0f;
			cameraY = 3.0f;
			cameraZ = 1.5f;
			centerX = 0.0f;
			centerY = 0.0f;
			centerZ = 0.0f;
			break;
		case 107: // mover la cámara hacia adelante k
            cameraRadius -= 0.4f;
            if(cameraRadius < 0.1f) cameraRadius = 0.1f; // asegurarse de que la cámara no se acerque demasiado
            break;
        case 108: // mover la cámara hacia atrás l
            cameraRadius += 0.4f;
            break;
	}
}

// Funci�n principal
int main(int argc, char **argv)
{
	// Inicializamos la librer�a GLUT
	glutInit(&argc, argv);

	// Indicamos como ha de ser la nueva ventana
	glutInitWindowPosition(100, 100);
	glutInitWindowSize(W_WIDTH, W_HEIGHT);
	glutInitDisplayMode(GLUT_RGBA | GLUT_DOUBLE);
    

	// Creamos la nueva ventana
	glutCreateWindow("Mi primera Ventana");
	glutReshapeFunc(reshape);

	// Indicamos cuales son las funciones de redibujado e idle
	glutDisplayFunc(Display);

	glutIdleFunc(Idle);

	glutSpecialFunc(specialKeys);

	glutKeyboardFunc(keys);

	// El color de fondo ser� el negro (RGBA, RGB + Alpha channel)
	glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	//glOrtho(-1.0, 1.0f, -1.0, 1.0f, -1.0, 1.0f);

	// Comienza la ejecución del core de GLUT
	glutMainLoop();
	return 0;
}
