// Etapa2.cpp
// Fichero principal
////////////////////////////////////////////////////
// Includes para linux
//#include <GL/glut.h>
//#include <GL/gl.h>
//#include <GL/glu.h>

// mac
#include <OpenGL/gl.h>
#include <OpenGL/glu.h>
#include <GLUT/glut.h>

//CONSTANTES
const int W_WIDTH = 500; // Tamaño incial de la ventana
const int W_HEIGHT = 500;
GLfloat fAngulo; // Variable que indica el Ángulo de rotaci�n de los ejes.
GLfloat size;
float angle = 0.0f;

// Función que visualiza la escena OpenGL
void Display(void)
{
	// Borramos la escena
	glClear(GL_COLOR_BUFFER_BIT);

	glPushMatrix();
	//Ejes de coordenadas

	//Eje X
	glBegin(GL_LINES);
		glColor3f(1.0f, 0.0f, 0.0f);
		glVertex3f(-1.0f, 0.0f, 0.0f);
		glColor3f(1.0f, 0.0f, 0.0f);
		glVertex3f(1.0f, 0.0f, 0.0f);
	glEnd();

	//Eje Y
	glBegin(GL_LINES);
		glColor3f(0.0f, 1.0f, 0.0f);
		glVertex3f(0.0f, -1.0f, 0.0f);
		glColor3f(0.0f, 1.0f, 0.0f);
		glVertex3f(0.0f, 1.0f, 0.0f);
	glEnd();

	glPopMatrix();

	glPushMatrix();
		glRotatef(angle, 0.0f, 0.0f, 1.0f);
		angle += 1.0f;
		glBegin(GL_POLYGON);
			glColor3f(1.0f, 0.0f, 1.0f);
			glVertex3f(-0.25f, 0.25f, 0.0f);
			glColor3f(0.0f, 1.0f, 0.0f);
			glVertex3f(-0.4f, 0.75, 0.0f);
			glColor3f(0.0f, 1.0f, 0.0f);
			glVertex3f(-0.4f, 0.25f, 0.0f);
		glEnd();
	glPopMatrix();

	glPushMatrix();
		glBegin(GL_POLYGON);
			glColor3f(1.0, 0.0, 0.0);
			glVertex3f(0.75f, 0.75f, 0.0f);
			glVertex3f(0.25f, 0.75f, 0.0f);
			glVertex3f(0.25f, 0.25f, 0.0f);
			glVertex3f(0.75f, 0.25f, 0.0f);
		glEnd();
	glPopMatrix();

	glPushMatrix();
		glBegin(GL_POLYGON);
			glColor3f(0.0f, 1.0f, 1.0f);
			glVertex3f(-0.75f, -0.75f, 0.0f);
			glVertex3f(-0.25f, -0.75f, 0.0f);
			glVertex3f(-0.25f, -0.25f, 0.0f);
			glVertex3f(-0.75f, -0.25f, 0.0f);
		glEnd();
	glPopMatrix();

	glPushMatrix();
		glBegin(GL_POLYGON);
			glColor3f(1.0f, 0.0f, 1.0f);
			glVertex3f(0.75f, -0.75f, 0.0f);
			glVertex3f(0.25f, -0.75f, 0.0f);
			glVertex3f(0.25f, -0.25f, 0.0f);
			glVertex3f(0.75f, -0.25f, 0.0f);
		glEnd();
	glPopMatrix();

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
	// Incrementamos el �ngulo
	size += 0.001f;
	// Si es mayor que dos pi la decrementamos
	if (size > 2)
		size -= 2;
	// Indicamos que es necesario repintar la pantalla
	glutPostRedisplay();
}

// Funci�n principal
int main(int argc, char **argv)
{
	// Inicializamos la librería GLUT
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

	// El color de fondo ser� el negro (RGBA, RGB + Alpha channel)
	glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	glOrtho(-1.0, 1.0f, -1.0, 1.0f, -1.0, 1.0f);

	// Comienza la ejecución del core de GLUT
	glutMainLoop();
	return 0;
}
