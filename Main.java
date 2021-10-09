import model.ExtractData;
import model.HideData;
import view.Window;

public class Main
 {
    public static void main(String[] args)
    {
        HideData hd = new HideData();
        ExtractData ed = new ExtractData();

        Window w = new Window(hd, ed);
        w.createGUI();
    }
}
