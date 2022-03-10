package DrawsinessDetection;

import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.FrameRecorder;
import com.googlecode.javacv.VideoInputFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import com.googlecode.javacv.cpp.opencv_objdetect;
import static com.googlecode.javacv.cpp.opencv_objdetect.*;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.opencv.core.Core;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;

public class Detection extends javax.swing.JFrame implements SerialPortEventListener {

    static long abc = 0;
    boolean imgChange = false;
    public int CX = 320;
    public int CY = 240;
    CommPortIdentifier portId;
    OutputStream outputStream;
    InputStream inputStream;
    SerialPort serialPort;
    boolean flag = true;
    public boolean dataReady, receivedPin;
    public int inData = 0, outData = 0, outData1 = 0;
    boolean isInitilizeOnce = false;
    DefaultComboBoxModel dc;
    DefaultComboBoxModel dc1;
    long startTimeForHeartBit = 0;
    long currentTimeForBit = 0;
    int MAX_EYE_BLINK_COUNT = 10;
    int currentAVGDurationTime = 0;
    int tiltCnt = 0;
    int channelNumber = 0;

    public Detection() {
        initComponents();
        setLocationRelativeTo(null);
        isInitilizeOnce = false;

        dc = new DefaultComboBoxModel();

        jProgressAlchol.setMaximum(255);
        jProgressIR.setMaximum(255);

        fillCombo();
        fill_Sec();
        fillComboData();

    }

    void fillCombo() {

        for (int i = 1; i <= 25; i++) {
            dc.addElement("COM" + i);
        }

        jComboBox1.setModel(dc);
        jComboBox1.setSelectedIndex(2);
    }

    void fill_Sec() {
        dc1 = new DefaultComboBoxModel();
        for (int i = 0; i <= 60; i++) {
            dc1.addElement(i + " sec");
        }

        jComboBox3.setModel(dc1);
        jComboBox3.setSelectedIndex(30);
    }

    void fillComboData() {
        DefaultComboBoxModel dcTemp = new DefaultComboBoxModel();
        for (int i = 1; i <= 100; i++) {
            dcTemp.addElement(i);
        }
        // jComboBox2.setModel(dcTemp);
        // jComboBox2.setSelectedIndex(80);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jProgressAlchol = new javax.swing.JProgressBar();
        jProgressIR = new javax.swing.JProgressBar();
        jProgressBarHB = new javax.swing.JProgressBar();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaFinalOut = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(153, 153, 153));
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel3.setFont(new java.awt.Font("Yu Gothic", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("DROWSINESS STATUS DUE TO HEAD MOVMENTS");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        jLabel4.setFont(new java.awt.Font("Yu Gothic Light", 0, 14)); // NOI18N
        jLabel4.setText(" BLINK RATE PER MINUTE COUNT");

        jLabel1.setFont(new java.awt.Font("Calibri Light", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Drowsiness Detection");

        jButton1.setFont(new java.awt.Font("Lucida Bright", 2, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 102, 0));
        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Heart Bit Sensor"));

        jLabel5.setText("COM:");

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane4.setViewportView(jTextArea3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                    .addComponent(jProgressAlchol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressIR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBarHB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(jProgressAlchol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressIR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBarHB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(320, Short.MAX_VALUE))
        );

        jTextArea5.setColumns(20);
        jTextArea5.setRows(5);
        jScrollPane6.setViewportView(jTextArea5);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));

        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jTextAreaFinalOut.setColumns(20);
        jTextAreaFinalOut.setRows(5);
        jScrollPane1.setViewportView(jTextAreaFinalOut);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane7.setViewportView(jTextArea2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane6)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                            .addComponent(jScrollPane7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 3, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // running = true;
        try {
            portId = CommPortIdentifier.getPortIdentifier(jComboBox1.getSelectedItem().toString());
            serialPort = (SerialPort) portId.open("INTERNET OF THINGS", 2000);
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
            // Thread.sleep(1000);
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

        } catch (Exception e) {
            System.out.println("Some Exception: " + e);
            flag = false;
            JOptionPane.showMessageDialog(this, "Port Is Not Initlized!!");

            return;

        }

        if (flag) {
            System.out.println("Serial Port Initialized!");
        }

        GrabberShowGUI gs = new GrabberShowGUI(this);
        Thread th = new Thread(gs);
        th.start();

        dataReady = false;
        isInitilizeOnce = true;
        ExtractHBTask eHBT = new ExtractHBTask(this);
        Timer t = new Timer();
        t.schedule(eHBT, 100, 100);
        startTimeForHeartBit = System.currentTimeMillis();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        jTextArea1.setText("");

    }//GEN-LAST:event_jButton2ActionPerformed

    class ExtractHBTask extends TimerTask {

        Detection parent;

        ExtractHBTask(Detection parent) {
            this.parent = parent;
            channelNumber = 0;
        }

        @Override
        public void run() {
            //   System.gc();
            parent.writeData(73); // adc
            parent.writeData(channelNumber);
            if (parent.dataReady) {
                if (channelNumber == 0) {
                    int HB = parent.inData;
                    //  System.out.println("IR: "+alchol+"  "+channelNumber );
                    parent.jProgressAlchol.setValue(HB);
                    parent.jProgressAlchol.setString("HB: " + HB);

                    parent.jProgressAlchol.setStringPainted(true);

                    if (HB > 240) {
                        parent.writeData(34);
                        parent.writeData(1);

                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }

                        parent.writeData(34);
                        parent.writeData(0);
                    }
                    parent.dataReady = false;
                } else if (channelNumber == 1) {
                    int alchol = parent.inData;
                    //  System.out.println("Alchol: "+alchol+"  "+channelNumber );
                    parent.jProgressIR.setString("Alchol: " + alchol);
                    parent.jProgressIR.setStringPainted(true);
                    parent.jProgressIR.setValue(alchol);
                    if (alchol > 110) {
                        parent.writeData(34);
                        parent.writeData(4);

                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }

                        parent.writeData(34);
                        parent.writeData(0);
                    }
                    parent.dataReady = false;
                } else if (channelNumber == 2) {
                    parent.jProgressBarHB.setMaximum(255);

                    int IR = parent.inData;
                    parent.jProgressBarHB.setString("IR " + IR);
                    parent.jProgressBarHB.setStringPainted(true);
                    parent.jProgressBarHB.setValue(IR);
                    if (IR > 80 && IR < 230) {
                        if (IR > 130) {
                            parent.writeData(34);
                            parent.writeData(1);

                            try {
                                Thread.sleep(1000);
                            } catch (Exception e) {
                            }

                            parent.writeData(34);
                            parent.writeData(0);
                        }

                    }
                    parent.dataReady = false;
                }



            }
            channelNumber++;
            if (channelNumber == 3) {
                channelNumber = 0;
            }
        }
    }

    void setlable(String str) {
        System.err.println("Video Recording " + str);

    }

    /**
     * @param args the command line arguments
     */
    public void writeData(int data) {
        try {
            outputStream.write(data);
        } catch (Exception e) {
            System.out.println("Error writing data : " + e);
            e.printStackTrace();
        }
    }

    public void serialEvent(SerialPortEvent event) {

        switch (event.getEventType()) {
            case SerialPortEvent.DATA_AVAILABLE:
                try {
                    while (inputStream.available() > 0) {
                        inData = inputStream.read();
                        dataReady = true;
                    }
                } catch (IOException e) {
                    System.out.println("Serial Port Read Error: " + e);
                }
                break;
        }
    }

    public static void main(String args[]) {
 
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Detection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Detection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Detection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Detection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Detection().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    public javax.swing.JComboBox jComboBox3;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JProgressBar jProgressAlchol;
    private javax.swing.JProgressBar jProgressBarHB;
    public javax.swing.JProgressBar jProgressIR;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    public javax.swing.JTable jTable1;
    public javax.swing.JTextArea jTextArea1;
    public javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    public javax.swing.JTextArea jTextArea5;
    public javax.swing.JTextArea jTextAreaFinalOut;
    // End of variables declaration//GEN-END:variables
}

class GrabberShowGUI extends Detection implements Runnable {

    opencv_core.IplImage image;
    static FrameRecorder recorder;
    static opencv_core.CvScalar rgba_min = cvScalar(0, 0, 130, 0);// RED wide dabur birko
    static opencv_core.CvScalar rgba_max = cvScalar(80, 80, 255, 0);
    int ii = 0;
    // JPanel jp = new JPanel();
    static Rect rectCrop = null;
    static CanvasFrame canvas = null;
    private static final String HAAR_DIR = "E:\\opencv\\opencv\\sources\\data\\haarcascades\\";
    public static final String XML_FILE = HAAR_DIR + "haarcascade_frontalface_default.xml";
    String face_cascade_name = HAAR_DIR + "haarcascade_frontalface_alt.xml";
    String eyes_cascade_name = HAAR_DIR + "haarcascade_eye_tree_eyeglasses.xml";
    CascadeClassifier face_cascade = null;
    CascadeClassifier eyes_cascade = null;
    // private static final String FACE_DIR = "faces\\";     // where the test input images are stored
    private static final String FACE = "haarcascade_frontalface_alt.xml";
    private static final String LEFT_EYE = "haarcascade_eye_tree_eyeglasses.xml";
    private static final String RIGHT_EYE = "haarcascade_eye_tree_eyeglasses.xml";
    private static final String RIGHT_EYE_PU = "haarcascade_mcs_lefteye.xml";
    private static final String LEFT_EYE_PU = "haarcascade_mcs_righteye.xml";
    // JavaCV variables
    private static opencv_core.CvMemStorage storage;
    private static opencv_core.IplImage drawImg;
    static long currtime = 0;
    public static Detection parent;
    static int blinkCntPerSec = 0;
    static int blinkCntPerMin = 0;
    int minCnt = 0;
    DefaultTableModel tm;
    Object[] colHeader = new Object[]{"#", "Per Minute"};
    int indexCnt = 0;
    int totalCnt = 0;

    public GrabberShowGUI(Detection parent) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.parent = parent;
        canvas = new CanvasFrame("Web Cam");
        try {

            currtime = System.currentTimeMillis();

        } catch (Exception ex) {
        }
        face_cascade = new CascadeClassifier();
        eyes_cascade = new CascadeClassifier();
        if (!face_cascade.load(face_cascade_name)) {
            System.out.println("Error loading face cascade");
        } else {
            System.out.println("Success loading face cascade");
        }

        //load the eyes xml cascade
        if (!eyes_cascade.load(eyes_cascade_name)) {
            System.out.println("Error loading eyes cascade");
        } else {
            System.out.println("Success loading eyes cascade");
        }
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
     
    }

    @Override
    public void run() {
        FrameGrabber grabber = new VideoInputFrameGrabber(0); // 1 for next camera
        // int i = 0;
        try {
            grabber.start();
        } catch (Exception e) {
            System.out.println("Error:  " + e);
        }
        try {
            // System.gc();

            opencv_core.IplImage img;
            long startTime = System.currentTimeMillis();
            int startCnt = 0;
            minCnt = 0;
            tm = new DefaultTableModel(colHeader, 0);
            parent.jTable1.setModel(tm);
            while (true) {
                //  System.gc();
                img = grabber.grab();
                int width = img.width();
                int height = img.height();
                // System.out.println("Image  W:" + width + "  H:" + height);

                if (img != null) {
                    cvFlip(img, img, 1);// l-r = 90_degrees_steps_anti_clockwise
                    Loader.load(opencv_objdetect.class);
                    storage = opencv_core.CvMemStorage.create(); // create storage used during object detection
                    // find the face first
                    drawImg = img.clone();    // original color image
                    // convert to grayscale
                    opencv_core.IplImage grayImage = opencv_core.IplImage.create(img.width(), img.height(), IPL_DEPTH_8U, 1);
                    cvCvtColor(img, grayImage, CV_BGR2GRAY);
                    opencv_core.CvRect faceRect = detectFeature(grayImage, "face", FACE,
                            0, 0, null, opencv_core.CvScalar.RED);
                    if (faceRect != null) {
                        //  System.out.println("No face detected");
                        detectFacialFeatures(grayImage, faceRect);
                    }

                    // draw the input image and any feature boxes
                    canvas.showImage(drawImg);
                    long currentTime = System.currentTimeMillis();
                    long diff = currentTime - startTime;
                    long diffValue = (parent.jComboBox3.getSelectedIndex() - 1) * 1000;

                    startCnt++;
                    if (diff >= 1000) {
                        startTime = currentTime;
                        if (blinkCntPerSec > 0) {
                            blinkCntPerMin++;
                        }
                        blinkCntPerSec = 0;
                        startCnt = 0;
                        minCnt++;
                    }
                    jLabel2.setText(""+minCnt);
                    if (minCnt >= 30) {
                        blinkCntPerMin = blinkCntPerMin / 2;
                        Object row[] = new Object[2];
                        row[0] = ++indexCnt;
                        row[1] = (blinkCntPerMin);
                        tm.addRow(row);
                        parent.jTable1.setModel(tm);
                        String out = "";
                        double HB_PER = 0;
                        double CLOSE_EYE_DURATION_PER = 0;
                        double BLINK_PER = 0;
                        if (blinkCntPerMin > 10) {
                            parent.jTextArea2.setText("Drawsiness Detected Due To MAX Blink Rate: " + blinkCntPerMin);
                            parent.writeData(34);
                            parent.writeData(1);

                            try {
                                Thread.sleep(1000);
                            } catch (Exception e) {


                                parent.writeData(34);
                                parent.writeData(0);
                            }
                            parent.dataReady = false;

                        }
                        blinkCntPerMin = 0;
                        minCnt = 0;
                    }
                }
            }

        } catch (Exception e) {
        }
    }

    private static opencv_core.IplImage loadGrayImage(opencv_core.IplImage fnm) {
        opencv_core.IplImage img = fnm;
        if (img == null) {
            System.exit(1);
        } else {
        }
        drawImg = img.clone();    // original color image
        // convert to grayscale
        opencv_core.IplImage grayImage = opencv_core.IplImage.create(img.width(), img.height(), IPL_DEPTH_8U, 1);
        cvCvtColor(img, grayImage, CV_BGR2GRAY);
        // equalize the grayscale
        cvEqualizeHist(grayImage, grayImage);

        return grayImage;
    }  // end of loadGrayImage()

    static double calDist(int X1, int Y1, int X2, int Y2) {
        return Math.sqrt((X1 - X2) * (X1 - X2) + (Y1 - Y2) * (Y1 - Y2));
    }

    private static opencv_core.CvRect detectFeature(opencv_core.IplImage im, String featureName,
            String haarFnm, int xF, int yF, opencv_core.CvRect selectRect, opencv_core.CvScalar color) /*
     *
     */ {

        System.gc();
        // instantiate a classifier cascade for the feature detection
        opencv_objdetect.CvHaarClassifierCascade classifier = new opencv_objdetect.CvHaarClassifierCascade(cvLoad(HAAR_DIR + haarFnm));
        if (classifier.isNull()) {
            System.out.println("Could not load the classifier: " + haarFnm + " for " + featureName);
            return null;
        }

        // use selection rectangle to apply a ROI to the image
        int xSelect = 0;
        int ySelect = 0;
        if (selectRect != null) {
            cvSetImageROI(im, selectRect);
            xSelect = selectRect.x();
            ySelect = selectRect.y();
        }
        // System.out.println(featureName + " y: " + ySelect);

        opencv_core.CvSeq featureSeq = cvHaarDetectObjects(im, classifier, storage, 1.1, 1,
                CV_HAAR_DO_ROUGH_SEARCH | CV_HAAR_FIND_BIGGEST_OBJECT);
        // speed things up by searching for only a single, largest feature subimage

        cvClearMemStorage(storage);
        cvResetImageROI(im);

        int total = featureSeq.total();
        // System.out.println("total:" + total);
        if (total == 1) {
            opencv_core.CvRect fRect = new opencv_core.CvRect(cvGetSeqElem(featureSeq, 0));
            // draw feature rectangle on input image
            int xDraw = xF + xSelect + fRect.x();    // convert fRect back to input image coords
            int yDraw = yF + ySelect + fRect.y();
            int currCX = (xDraw + fRect.width() / 2);
            int currCY = (yDraw + fRect.height() / 2);

            double dist = calDist(currCX, currCY, parent.CX, parent.CY);
            //  System.out.println("DIST:  "+dist);
            if (dist > 150) {
                //System.out.println("Drawsiness Detected:  "+dist);
                int tempDist = (int) dist * 100;
                double newDist = tempDist * 1.0 / 100;

                parent.tiltCnt++;

                //System.out.println("CNT: "+parent.tiltCnt);
                // Time Thresold Cnt

                if ((parent.tiltCnt / 2) > 2) {

                    parent.jTextArea1.setText("Drawsiness Detected Due to Head Tilt:" +
                                              parent.tiltCnt + "  " + newDist + " "   + 
                                              getCurrentDateTime() + "\n" + parent.jTextArea1.getText());
                    parent.tiltCnt = 0;

                    parent.writeData(34);
                    parent.writeData(4);

                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }

                    parent.writeData(34);
                    parent.writeData(0);
                    java.awt.Toolkit.getDefaultToolkit().beep();
                    // Toolkit.getDefaultToolkit().beep();
                }
                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    System.out.println("Error: " + e);
                }
            } else {
            }

            if (fRect != null) {
                cvRectangle(drawImg, cvPoint(xDraw, yDraw),
                        cvPoint(xDraw + fRect.width(), yDraw + fRect.height()),
                        color, 1, CV_AA, 0);    // thicker line
            }
            return fRect;

        } else {

            return null;
        }


    }  // end of detectFeature()

    public static String getCurrentDateTime() {

        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        return df.format(dateobj);
      
    }
    // features detected: left eye, right eye, nose, mouth, all relative to the face
    private static void detectFacialFeatures(opencv_core.IplImage grayImage, opencv_core.CvRect faceRect) 
    {
        int xF = faceRect.x();     // these (x,y) coords are relative to the gray image 
        int yF = faceRect.y();
        int wF = faceRect.width();
        int hF = faceRect.height();
        opencv_core.IplImage faceImage = opencv_core.IplImage.create(faceRect.width(),
                faceRect.height(), IPL_DEPTH_8U, 1);
        cvSetImageROI(grayImage, faceRect);
        cvCopy(grayImage, faceImage);
        // detect eyes (assume in left and right halves of image)
        opencv_core.CvRect selectRect = new opencv_core.CvRect(0, 0, wF / 2, hF);     // left eye 
        opencv_core.CvRect leRect = detectFeature(faceImage, "left eye", LEFT_EYE,
                xF, yF, selectRect, opencv_core.CvScalar.MAGENTA);

        //parent.jTextArea1.setText(" "+"Left Eye:     H:" + leRect.height() + "  W:" + leRect.width());
        opencv_core.CvRect selectRect1 = new opencv_core.CvRect(wF / 2, 0, wF / 2, hF);    // right eye
        opencv_core.CvRect reRect = detectFeature(faceImage, "right eye", RIGHT_EYE,
                xF, yF, selectRect1, opencv_core.CvScalar.MAGENTA);

        // Increment Count For Blink    OR Condition
        if (leRect == null || reRect == null) {
            blinkCntPerSec++;
        }
        // these (x,y) coords are relative to the gray image 
        int leftxF = selectRect.x();     
        int leftyF = selectRect.y();
        int leftwF = selectRect.width();
        int lefthF = selectRect.height();

        // nose detection (below top of eyes)
        int yEye = hF / 2;  // guess eye top
        if ((leRect != null) && (reRect != null)) {
            yEye = (leRect.y() < reRect.y()) ? leRect.y() : reRect.y();    // choose higher
        }
    }  // end of detectFacialFeatures()
}
