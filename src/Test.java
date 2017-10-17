import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        MyFile file = new MyFile(new File("D:\\Full Testable Data - Copy\\IMG_3544.mov"));
        Process p;

        String command = "cmd /c start /wait robocopy /xc /xn /xo \"" + file.file.getAbsolutePath().substring(0, file.file.getAbsolutePath().lastIndexOf('\\')) +
                "\" \"E:\\Video\" " + "\"" + file.file.getName() + "\"" + " /mov";
        System.out.println(command);
        p = Runtime.getRuntime().exec(command);
        p.waitFor();
        System.out.print("Waited!");




    }
}
