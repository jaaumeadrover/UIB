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
const int W_WIDTH = 500; // Tamaño incial de la ventana
const int W_HEIGHT = 500;
GLfloat fAngulo; // Variable que indica el ángulo de rotaci�n de los ejes.
GLfloat size;
float angle1 = 0.0f;
float angle2 = 0.0f;

float cameraTheta = 0.2; // ángulo de la cámara alrededor del objeto
float cameraPhi = 0.5;   // ángulo de la cámara respecto al eje vertical
float cameraRadius = 5; // distancia de la cámara al objeto

GLfloat cameraX = cameraRadius * sin(cameraTheta) * sin(cameraPhi);
GLfloat cameraY = cameraRadius * cos(cameraPhi);
GLfloat cameraZ = cameraRadius * cos(cameraTheta) * sin(cameraPhi);

GLfloat centerX = 0.0f;
GLfloat centerY = 0.0f;
GLfloat centerZ = 0.0f;

//Etapa 5

//Posición de la luz
GLfloat light_position0[] = { 1.0, 1.0, 1.0, 1.0 };
GLfloat light_position1[] = { 1.0, 1.0, 1.0, 1.0 };

bool tipoDeLlum = true;

// Función que visualiza la escena OpenGL
void Display(void)
{ 
    glEnable(GL_DEPTH_TEST);
	//Añadido etapa5
	glEnable (GL_LIGHTING);
	glEnable (GL_COLOR_MATERIAL);
	glColorMaterial (GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE);
	glEnable(GL_LIGHT0); // Primera fuente de luz
    glEnable(GL_LIGHT1); // Segunda fuente de luz
	if(tipoDeLlum){
		glShadeModel (GL_FLAT);
	}else{
		glShadeModel (GL_SMOOTH);
	}
	// Borramos la escena
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	glMatrixMode (GL_PROJECTION);
   	glLoadIdentity();
	//glOrtho(-0.1, 0.1, -0.1, 0.1, 0.1, 5.0);
   	//glFrustum(-0.1, 0.1, -0.1, 0.1, 0.3, 5.0);
	//gluPerspective(45.0, 1.0, 1.0, 100.0);
	gluPerspective(40.0f, W_HEIGHT / W_WIDTH, 1.0f, 100.0f);

   	glMatrixMode(GL_MODELVIEW);
	cameraX = cameraRadius * sin(cameraTheta) * sin(cameraPhi);
 	cameraY = cameraRadius * cos(cameraPhi);
	cameraZ = cameraRadius * cos(cameraTheta) * sin(cameraPhi);
    glLoadIdentity();
    gluLookAt(cameraX, cameraY, cameraZ,  // eye position
              centerX, centerY, centerZ,  // look at position
              0.0, 1.0, 0.0); // up vector
	
	//Etapa 5

	//Configuramos las fuentes de luz

	//Primera fuente de luz
	GLfloat ambient0[] = { 0.2, 0.2, 0.2, 1.0 };
    GLfloat diffuse0[] = { 1.0, 1.0, 1.0, 1.0 };
    GLfloat specular0[] = { 1.0, 1.0, 1.0, 1.0 };
    glLightfv(GL_LIGHT0, GL_POSITION, light_position0);
    glLightfv(GL_LIGHT0, GL_AMBIENT, ambient0);
    glLightfv(GL_LIGHT0, GL_DIFFUSE, diffuse0);
    glLightfv(GL_LIGHT0, GL_SPECULAR, specular0);

	//Segunda fuente de luz
	GLfloat ambient1[] = { 0.0f, 0.0f, 0.0f, 1.0f };
    GLfloat diffuse1[] = { 0.0f, 1.0f, 0.0f, 1.0f };
    GLfloat specular1[] = { 1.0f, -1.0f, 1.0f, 0.0f };
    glLightfv(GL_LIGHT1, GL_POSITION, light_position1);
    glLightfv(GL_LIGHT1, GL_AMBIENT, ambient1);
    glLightfv(GL_LIGHT1, GL_DIFFUSE, diffuse1);
    glLightfv(GL_LIGHT1, GL_SPECULAR, specular1);
	
	glPushMatrix();
	//glRotatef(angle2, 0.0, 1.0, 0.0);

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
		//glRotatef(angle1, 0.0, 1.0, 0.0);
        glutSolidTeapot(0.2);
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
	// Incrementamos el ángulo
	angle1 += 0.5f;
	angle2 +=0.3f;
	
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
            cameraPhi += 0.01f;
            //if(cameraPhi > 1.5f) cameraPhi = 1.5f; // asegurarse de que la cámara no gire demasiado hacia arriba
            break;
        case GLUT_KEY_DOWN:  // rotar la cámara hacia abajo
            cameraPhi -= 0.01f;
            //if(cameraPhi < -1.5f) cameraPhi = -1.5f; // asegurarse de que la cámara no gire demasiado hacia abajo
            break;
    }
}

void keys(unsigned char key, int x, int y) {
	switch(key){
		case 107: // mover la cámara hacia adelante k
            cameraRadius -= 0.4f;
            if(cameraRadius < 0.1f) cameraRadius = 0.1f; // asegurarse de que la cámara no se acerque demasiado
            break;
        case 108: // mover la cámara hacia atrás l
            cameraRadius += 0.4f;
            break;
		case 'a':
        case 'A':
            light_position0[0] += 0.3;
            break;
        case 'x':
        case 'X':
            light_position0[1] -= 0.3;
            break;
        case 'z':
        case 'Z':
            light_position0[0] -= 0.3;
            break;
        case 's':
        case 'S':
            light_position0[1] += 0.3;
            break;
        case 'd':
        case 'D':
            light_position0[2] -= 0.3;
            break;
        case 'c':
        case 'C':
            light_position0[2] += 0.3;
            break;
		case 'p':
		case 'P':
			if(tipoDeLlum == true){
				tipoDeLlum = false;
			}else{
				tipoDeLlum = true;
			}
			break;
	}

	//std::cout << cameraRadius << std::endl;
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
	glutCreateWindow("Etapa 5");
	glutReshapeFunc(reshape);

	// Indicamos cuales son las funciones de redibujado e idle
	glutDisplayFunc(Display);

	glutIdleFunc(Idle);

	glutSpecialFunc(specialKeys);

	glutKeyboardFunc(keys);

	// El color de fondo ser� el negro (RGBA, RGB + Alpha channel)
	glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	//glOrtho(-1.0, 1.0f, -1.0, 1.0f, -1.0, 1.0f);

	// Comienza la ejecuci�n del core de GLUT
	glutMainLoop();
	return 0;
}
