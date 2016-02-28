/*
 * PROGRAMA QUE MODELA UNA FIGURA 3D FORMADA POR CINCO CUBOS
 *
 *Karen Abarca Garcia A01323627
 *Fernando Arey Duran A00397411
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/*Clase Point3D que define un tipo de valor formado por 3 valores double
  Cada valor representa una coordenada en x,y,z
*/
class Point3D {
   public double x, y, z;
   public Point3D( double X, double Y, double Z ) {
      x = X;  y = Y;  z = Z;
   }
}//Fin de la clase Point3D


/*Clase Edge que define un tipo de valor formado por 2 enteros
  Cada valor representa un punto que sera unido por la arista
*/
class Edge {
   public int a, b;
   public Edge( int A, int B ) {
      a = A;  b = B;
   }
}//Fin de la clase Edge



/*Clase Edge que define un tipo de valor formado por 2 enteros
  Cada valor representa un punto que sera unido por la arista
*/
class Face {
   public int a, b, c, d;
   public Face( int A, int B, int C, int D ) {
      a = A;  b = B; c = C; d = D;
   }
}//Fin de la clase Edge



/**CLASE PRINCIPAL**/
public class WireframeJApplet extends JApplet implements KeyListener, FocusListener, MouseListener {

   int NUM_CARAS = 26;
   int NUM_VERTICES = 24;
   int NUM_ARISTAS = 44;
                      
   int width, height;                //Ancho y alto del canvas

   int azimuth = 35, elevation = 30; //Elevacion y rotacion de la figura

   Point3D[] vertices;               //Arreglo de puntos
   Edge[] edges;                     //Arreglo de aristas
   Face[] faces;                     //Arreglo de caras

   boolean focussed = false;         //True when this applet has input focus.
   
   DisplayPanel canvas;  


   /*METODO QUE INICIALIZA LOS RECURSOS*/
   public void init() {

      /**VERTICES**/
      vertices = new Point3D[ NUM_VERTICES];
      
      //Cubo central
      vertices[0] = new Point3D( -1,  1,  1 );
      vertices[1] = new Point3D( -1,  1, -1 );
      vertices[2] = new Point3D( -1, -1,  1 );
      vertices[3] = new Point3D( -1, -1, -1 );
      vertices[4] = new Point3D(  1,  1,  1 );
      vertices[5] = new Point3D(  1,  1, -1 );
      vertices[6] = new Point3D(  1, -1,  1 );
      vertices[7] = new Point3D(  1, -1, -1 );

      //Segundo cubo
      vertices[8] = new Point3D(  -3,  1,  1 );
      vertices[9] = new Point3D(  -3,  1, -1 );
      vertices[10] = new Point3D( -3, -1,  1 );
      vertices[11] = new Point3D( -3, -1, -1 );

      //Tercer cubo
      vertices[12] = new Point3D( -1,  1,  3 );
      vertices[13] = new Point3D( -1, -1,  3 );
      vertices[14] = new Point3D(  1,  1,  3 );
      vertices[15] = new Point3D(  1, -1,  3 );

      //Cuarto cubo
      vertices[16] = new Point3D( -1, 3,  3 );
      vertices[17] = new Point3D( -1, 3,  1 );
      vertices[18] = new Point3D(  1, 3,  3 );
      vertices[19] = new Point3D(  1, 3,  1 );

      //Quinto cubo
      vertices[20] = new Point3D( -3,  -3,  1 );
      vertices[21] = new Point3D( -3,  -3, -1 );
      vertices[22] = new Point3D( -1,  -3,  1 );
      vertices[23] = new Point3D( -1,  -3, -1 );



      /**ARISTAS**/
      edges = new Edge[ NUM_ARISTAS ];

      //Cubo central
      edges[ 0] = new Edge( 0, 1 );
      edges[ 1] = new Edge( 0, 2 );
      edges[ 2] = new Edge( 0, 4 );
      edges[ 3] = new Edge( 1, 3 );
      edges[ 4] = new Edge( 1, 5 );
      edges[ 5] = new Edge( 2, 3 );
      edges[ 6] = new Edge( 2, 6 );
      edges[ 7] = new Edge( 3, 7 );
      edges[ 8] = new Edge( 4, 5 );
      edges[ 9] = new Edge( 4, 6 );
      edges[10] = new Edge( 5, 7 );
      edges[11] = new Edge( 6, 7 );

      //Segundo cubo
      edges[12] = new Edge( 8, 0 );
      edges[13] = new Edge( 8, 9 );
      edges[14] = new Edge( 8, 10 );
      edges[15] = new Edge( 9, 1 );
      edges[16] = new Edge( 9, 11 );
      edges[17] = new Edge( 10, 2 );
      edges[18] = new Edge( 10, 11 );
      edges[19] = new Edge( 11, 3 );

      //Tercer cubo
      edges[20] = new Edge( 12, 0 );
      edges[21] = new Edge( 12, 13 );
      edges[22] = new Edge( 12, 14 );
      edges[23] = new Edge( 13, 2 );
      edges[24] = new Edge( 13, 15 );
      edges[25] = new Edge( 14, 4 );
      edges[26] = new Edge( 14, 15 );
      edges[27] = new Edge( 15, 6);

      //Cuarto cubo
      edges[28] = new Edge( 16, 13 );
      edges[29] = new Edge( 16, 17 );
      edges[30] = new Edge( 16, 18 );
      edges[31] = new Edge( 17, 2 );
      edges[32] = new Edge( 17, 19);
      edges[33] = new Edge( 18, 19);
      edges[34] = new Edge( 18, 15);
      edges[35] = new Edge( 19, 6 );

      //Quinto cubo
      edges[36] = new Edge( 20, 8 );
      edges[37] = new Edge( 20, 21 );
      edges[38] = new Edge( 20, 22 );
      edges[39] = new Edge( 21, 9 );
      edges[40] = new Edge( 21, 23 );
      edges[41] = new Edge( 22, 0 );
      edges[42] = new Edge( 22, 23 );
      edges[43] = new Edge( 23, 1 );


      /**CARAS**/
      faces = new Face[NUM_CARAS];

      //Primer cubo
      faces[0] = new Face(1, 5, 7, 3);
      faces[1] = new Face(1, 0, 2, 3);
      faces[2] = new Face(1, 0, 4, 5);
      faces[3] = new Face(5, 4, 6, 7);
      faces[4] = new Face(2, 6, 7, 3);
      faces[5] = new Face(0, 4, 6, 2);

      //Segundo cubo
      faces[ 6] = new Face(9,  1,  3, 11);
      faces[ 7] = new Face(9,  8, 10, 11);
      faces[ 8] = new Face(9,  8,  0,  1);
      faces[ 9] = new Face(10, 2,  3, 11);
      faces[10] = new Face(8,  0,  2, 10);

      //Tercer cubo
      faces[11] = new Face(0,  12, 13,  2);
      faces[12] = new Face(0,  12, 14,  4);
      faces[13] = new Face(4,  14, 15,  6);
      faces[14] = new Face(2,  13, 15,  6);
      faces[15] = new Face(12, 14, 15,  13);

      //Cuarto cubo
      faces[16] = new Face(17, 19,  4,   0);
      faces[17] = new Face(17, 16, 12,   0);
      faces[18] = new Face(17, 16, 18,  19);
      faces[19] = new Face(19, 18, 14,   4);
      faces[20] = new Face(16, 18, 14,  12);

      //Quinto cubo
      faces[21] = new Face(11,  3, 23,  21);
      faces[22] = new Face(11, 10, 20,  21);
      faces[23] = new Face( 3,  2, 22,  23);
      faces[24] = new Face(20, 22, 23,  21);
      faces[25] = new Face(10,  2, 22,  20);

      // Create drawing surface and install it as the applet's content pane.
      canvas = new DisplayPanel();  
      setContentPane(canvas);       
   
      //Set up the applet to listen for events from the canvas.
      canvas.addFocusListener(this);   
      canvas.addKeyListener(this);                         
      canvas.addMouseListener(this);
      
   } //End init();
   

   /*CLASE QUE CREA EL FRAME PARA REALIZAR LOS TRAZOS DE LA FIGURA*/
   class DisplayPanel extends JPanel {

      /*METODO QUE TRAZA LO NECESARIO PARA DESPLEGAR LA FIGURA*/
      public void paintComponent(Graphics g) {
         
         super.paintComponent(g);  //Constructor

         //Verificando enfoque
         if (focussed) 
            g.setColor(Color.cyan);
         else
            g.setColor(Color.lightGray);


         int width = getSize().width;   // Width of the applet.
         int height = getSize().height; // Height of the applet.

         //Dibujando borde
         g.drawRect(0,0,width-1,height-1);
         g.drawRect(1,1,width-3,height-3);
         g.drawRect(2,2,width-5,height-5);

         
         //Acciones si el frame no esta activo
         if (!focussed) {
            g.setColor(Color.magenta);
            g.drawString("Click to activate",100,120);
            g.drawString("Use arrow keys to change azimuth and elevation",100,150);

         }

         //Acciones cuando el frame se activa
         else {

            //Calculando orientacion y perspectiva
            double theta = Math.PI * azimuth / 180.0;
            double phi = Math.PI * elevation / 180.0;
            float cosT = (float)Math.cos( theta ), sinT = (float)Math.sin( theta );
            float cosP = (float)Math.cos( phi ), sinP = (float)Math.sin( phi );
            float cosTcosP = cosT*cosP, cosTsinP = cosT*sinP,
                  sinTcosP = sinT*cosP, sinTsinP = sinT*sinP;

            //Project vertices onto the 2D viewport
            Point[] points = new Point[ vertices.length ];
            int scaleFactor = width/5;
            float near = 3;          // distance from eye to near plane
            float nearToObj = 10f;  // distance from near plane to 

            //Obteniendo el z,y promedio de todas las caras
            double[] promedioZ = new double[NUM_CARAS];
            double[] promedioY = new double[NUM_CARAS];
            double[] promedioX = new double[NUM_CARAS];
            int[] ordenCaras = new int[NUM_CARAS];
            for (int i = 0; i < NUM_CARAS; i++) {

               double x0, y0, z0;

               for (int j = 0 ; j < 4 ; j++) {

                  if (j == 0) {
                     x0 = vertices[faces[i].a].x;
                     y0 = vertices[faces[i].a].y;
                     z0 = vertices[faces[i].a].z;   
                  }

                  else if (j == 1) {
                     x0 = vertices[faces[i].b].x;
                     y0 = vertices[faces[i].b].y;
                     z0 = vertices[faces[i].b].z;   
                  }

                  else if (j == 2) {
                     x0 = vertices[faces[i].c].x;
                     y0 = vertices[faces[i].c].y;
                     z0 = vertices[faces[i].c].z;   
                  }

                  else {
                     x0 = vertices[faces[i].d].x;
                     y0 = vertices[faces[i].d].y;
                     z0 = vertices[faces[i].d].z;   
                  }

                  //Compute an orthographic projection
                  double x1 = cosT*x0 + sinT*z0;
                  double y1 = -sinTsinP*x0 + cosP*y0 + cosTsinP*z0;
                  
                  //Now adjust things to get a perspective projection
                  double z1 = cosTcosP*z0 - sinTcosP*x0 - sinP*y0;

                  promedioZ[i] += z1;
                  promedioY[i] += y1;
                  promedioX[i] += x1;
                  
               }

               ordenCaras[i] = i; //Orden temporal de las caras
               promedioZ[i] /= 4.0;
               promedioY[i] /= 4.0;
               promedioX[i] /= 4.0;
               //System.out.println("Z cara " + i + " = " + promedioZ[i] + "\n");
            }

            //Ordenando z de las caras
            boolean swapped = true;
            int k = 0;
            double temporal;
            int temporal2;
            while (swapped) {
               swapped = false;
               k++;

               for (int i = 0; i < NUM_CARAS - k; i++) {                                       
                  if (promedioZ[i] < promedioZ[i + 1]) {                          
                     temporal = promedioZ[i];
                     temporal2 = ordenCaras[i];
                     promedioZ[i] = promedioZ[i + 1];
                     ordenCaras[i] = ordenCaras[i + 1];
                     promedioZ[i + 1] = temporal;
                     ordenCaras[i + 1] = temporal2;
                     swapped = true;
                  }
               }                
            }

            //Creando puntos 2D
            for (int j = 0; j < vertices.length; ++j ) {
               
               //Obteniendo coordenadas y almacenandolas en un arreglo
               double x0 = vertices[j].x;
               double y0 = vertices[j].y;
               double z0 = vertices[j].z;
               //double[] arregloZ = new double [NUM_VERTICES] ;

               //Compute an orthographic projection
               double x1 = cosT*x0 + sinT*z0;
               double y1 = -sinTsinP*x0 + cosP*y0 + cosTsinP*z0;

               //Now adjust things to get a perspective projection
               double z1 = cosTcosP*z0 - sinTcosP*x0 - sinP*y0;
               x1 = x1*near/(z1+near+nearToObj);
               y1 = y1*near/(z1+near+nearToObj);

               //The 0.5 is to round off when converting to int
               points[j] = new Point(
                  (int)(width/2 + scaleFactor*x1 + 0.5),
                  (int)(height/2 - scaleFactor*y1 + 0.5)
               );

            }//Fin del for


            //Fondo
            g.setColor(Color.white);
            g.fillRect( 0, 0, width, height );

            //Dibujando caras 1 cubo
            for (int j = 0; j < NUM_CARAS ; j++) {

               //Creando arreglos
               int[] puntosX = new int[4];
               int[] puntosY = new int[4];

               puntosX[0] = points[faces[ordenCaras[j]].a].x;
               puntosX[1] = points[faces[ordenCaras[j]].b].x;
               puntosX[2] = points[faces[ordenCaras[j]].c].x;
               puntosX[3] = points[faces[ordenCaras[j]].d].x;

               puntosY[0] = points[faces[ordenCaras[j]].a].y;
               puntosY[1] = points[faces[ordenCaras[j]].b].y;
               puntosY[2] = points[faces[ordenCaras[j]].c].y;
               puntosY[3] = points[faces[ordenCaras[j]].d].y;


               if(ordenCaras[j] == 0 || ordenCaras[j] == 14 || ordenCaras[j] == 20 || ordenCaras[j] == 22)
                  g.setColor(Color.red);

               else if(ordenCaras[j] == 7 || ordenCaras[j] == 15 || ordenCaras[j] == 16 || ordenCaras[j] == 23)
                  g.setColor(Color.green);

               else if(ordenCaras[j] == 3 || ordenCaras[j] == 10 || ordenCaras[j] == 18 || ordenCaras[j] == 21)
                  g.setColor(Color.blue);

               else if(ordenCaras[j] == 2 || ordenCaras[j] == 11 || ordenCaras[j] == 20 || ordenCaras[j] == 24)
                  g.setColor(Color.cyan);

               else if(ordenCaras[j] == 6 || ordenCaras[j] == 19 || ordenCaras[j] == 25)
                  g.setColor(Color.magenta);
               
               else if(ordenCaras[j] == 4 || ordenCaras[j] == 8 || ordenCaras[j] == 13 || ordenCaras[j] == 17)
                  g.setColor(Color.yellow);
               else
                  g.setColor(Color.black);

               g.fillPolygon(puntosX, puntosY, 4);
            }


            //Trazando aristas
            for (int j = 0; j < edges.length ; ++j ) {
               g.setColor(Color.darkGray);
               g.drawLine(
                  points[ edges[j].a ].x, points[ edges[j].a ].y,
                  points[ edges[j].b ].x, points[ edges[j].b ].y
               );

            }//Fin del for que traza aristas

         }//Fin del else 

      } //End method paintComponent()    

    } //End nested class DisplayPanel 


   /**EVENT HANDLING METHODS**/

   //Metodos para saber si el frame esta activo o no
   public void focusGained(FocusEvent evt) {
      focussed = true;
      canvas.repaint();
   }
   public void focusLost(FocusEvent evt) {
      focussed = false;
      canvas.repaint(); 
   }
      
      
   public void keyPressed(KeyEvent evt) { 
       
      int key = evt.getKeyCode();  // keyboard code for the key that was pressed
      
      if (key == KeyEvent.VK_LEFT) {
         azimuth += 5;
         canvas.repaint();         
      }
      else if (key == KeyEvent.VK_RIGHT) {
         azimuth -= 5;
         canvas.repaint();         
      }
      else if (key == KeyEvent.VK_UP) {
         elevation -= 5;
         canvas.repaint();         
      }
      else if (key == KeyEvent.VK_DOWN) {
         elevation += 5;         
         canvas.repaint();
      }

   }  // end keyPressed()



   //Metodos requeridos por la interfaz KeyListener
   public void keyTyped(KeyEvent evt) {  
   }  

   public void keyReleased(KeyEvent evt) { 
   }
   



   
   /*METODOS REQUERIDOS POR LA INTERFAZ MOUSELISTENER*/

   /*Metodo que detecta cuando el mouse es presionado y activa el foco en el canvas*/
   public void mousePressed(MouseEvent evt) {      
      canvas.requestFocus();
   }      

   public void mouseEntered(MouseEvent evt) { }  
   public void mouseExited(MouseEvent evt) { }   
   public void mouseReleased(MouseEvent evt) { } 
   public void mouseClicked(MouseEvent evt) { }
   public void mouseDragged( MouseEvent e ) { }


} //Fin de la clase WireframeJApplet
