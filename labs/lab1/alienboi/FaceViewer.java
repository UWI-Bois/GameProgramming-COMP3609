
/**
 * Write a description of class FaceViewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class FaceViewer
{
    public static void main(String[] args) {
        EmptyFrame faceFrame = new EmptyFrame();
        FaceComponent face = new FaceComponent();
        faceFrame.setVisible(true);
        faceFrame.add(face);

        EmptyFrame rectFrame = new EmptyFrame();
        RectangleComponent rec = new RectangleComponent();
        rectFrame.setVisible(true);
        rectFrame.add(rec);
    }
}
