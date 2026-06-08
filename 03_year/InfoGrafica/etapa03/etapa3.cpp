// Etapa3.cpp
// Fichero principal
////////////////////////////////////////////////////

// Includes para linux
//#include <GL/glut.h>
//#include <GL/gl.h>
//#include <GL/glu.h>

// Includes para mac
#include <OpenGL/gl.h>
#include <OpenGL/glu.h>
#include <GLUT/glut.h>
const int W_WIDTH = 500; // Tamaño incial de la ventana
const int W_HEIGHT = 500;
GLfloat fAngulo; // Variable que indica el ángulo de rotación de los ejes.
GLfloat size;
float angle1 = 0.0f;
float angle2 = 0.0f;

// Función que visualiza la escena OpenGL
void Display(void)
{ 
    glEnable(GL_DEPTH_TEST);

	// Borramos la escena
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	glMatrixMode (GL_PROJECTION);
   	glLoadIdentity();
	//glOrtho(-0.1, 0.1, -0.1, 0.1, 0.1, 5.0);
   	glFrustum(-0.1, 0.1, -0.1, 0.1, 0.3, 5.0);

   	glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    gluLookAt(3.0, 1.0, 1.5,  // eye position
              0.0, 0.0, 0.0,  // look at position
              0.0, 2.0, 0.0); // up vector
	
	glPushMatrix();
	glRotatef(angle2, 0.0, 1.0, 0.0);

    //Ejes
	glPushMatrix();
		glLineWidth(2.0);
		//Axis X
		glBegin(GL_LINES);
            glColor3f(1.0f, 0.0f, 0.0f);
            glVertex3f(-1.0f, 0.0f, 0.0f);
            glColor3f(1.0f, 0.0f, 0.0f);
            glVertex3f(1.0f, 0.0f, 0.0f);
        glEnd();

        //Axis Y
        glBegin(GL_LINES);
            glColor3f(0.0f, 1.0f, 0.0f);
            glVertex3f(0.0f, -1.0f, 0.0f);
            glColor3f(0.0f, 1.0f, 0.0f);
            glVertex3f(0.0f, 1.0f, 0.0f);
        glEnd();

        //Axis Z
        glBegin(GL_LINES);
            glColor3f(0.0f, 0.0f, 1.0f);
            glVertex3f(0.0f, 0.0f, -1.0f);
            glColor3f(0.0f, 0.0f, 1.0f);
            glVertex3f(0.0f, 0.0f, 1.0f);
        glEnd();
	glPopMatrix();


	//FIGURAS DE LA ESCENA

	//(x, y, z)
    glPushMatrix();
		glTranslatef(0.5, 0.5, 0.5);
        glColor3f(0.3f, 0.5f, 0.8f);
		glRotatef(angle1, 0.0, 1.0, 0.0);
		glutWireCube(0.2);
    glPopMatrix();

	//(x, y, -z)
    glPushMatrix();
		glTranslatef(0.5, 0.5, -0.5);
        glColor3f(0.1f, 0.2f, 0.3f);
		glRotatef(angle1, 0.0, 1.0, 0.0);
		glutWireCube(0.2);
    glPopMatrix();
	
	//(-x, y, z)
    glPushMatrix();
		glTranslatef(-0.5, 0.5, 0.5);
        glColor3f(0.7f, 0.5f, 0.3f);
		glRotatef(angle1, 0.0, 1.0, 0.0);
		glutWireTorus(0.2, 0.2, 4, 4);
    glPopMatrix();

	//(-x, y, -z)
    glPushMatrix();
		glTranslatef(-0.5, 0.5, -0.5);
        glColor3f(0.5f, 0.4f, 0.7f);
		glRotatef(angle1, 0.0, 1.0, 0.0);
        glutWireTeapot(0.2);
    glPopMatrix();

    //(x, -y, z)
    glPushMatrix();
		glTranslatef(0.5, -0.5, 0.5);
        glColor3f(0.3f, 0.5f, 0.8f);
		glRotatef(angle1, 0.0, 1.0, 0.0);
		glutWireCube(0.2);
    glPopMatrix();

	//(x, -y, -z)
    glPushMatrix();
		glTranslatef(0.5, -0.5, -0.5);
        glColor3f(0.1f, 0.2f, 0.3f);
		glRotatef(angle1, 0.0, 1.0, 0.0);
		glutWireCube(0.2);
    glPopMatrix();
	
	//(-x, -y, z)
    glPushMatrix();
		glTranslatef(-0.5, -0.5, 0.5);
        glColor3f(0.7f, 0.5f, 0.3f);
		glRotatef(angle1, 0.0, 1.0, 0.0);
		glutWireCone(0.2, 0.75, 5, 35);
    glPopMatrix();

	//(-x, -y, -z)
    glPushMatrix();
		glTranslatef(-0.5, -0.5, -0.5);
        glColor3f(0.5f, 0.4f, 0.7f);
		glRotatef(angle1, 0.0, 1.0, 0.0);
        glutWireTeapot(0.2);
    glPopMatrix();

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
	// Incrementamos el ángulo
	angle1 += 0.5f;
	angle2 +=0.3f;
	
	glutPostRedisplay();
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

	// El color de fondo ser� el negro (RGBA, RGB + Alpha channel)
	glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	//glOrtho(-1.0, 1.0f, -1.0, 1.0f, -1.0, 1.0f);

	// Comienza la ejecuci�n del core de GLUT
	glutMainLoop();
	return 0;
}
