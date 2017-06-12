import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Test {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String path;
//        System.out.print("Directory to sort: ");
//        path = scanner.nextLine();
//        System.out.println("Your Path: " + path);

        path = "D:\\Testable Data\\Pictures\\2014\\CynPhone\\IMG_1584.JPG";
        MyFile file = new MyFile(new File(path));
        System.out.println(file.filetype);

//        File[] javaFileList = new File(path).listFiles();
//        MyFile[] fileList = new MyFile[javaFileList.length];
//        for (int i = 0; i < javaFileList.length; i++) {
//            fileList[i] = new MyFile(javaFileList[i]);
//        }
//
//        for (MyFile test : fileList){
//            System.out.println(test.file);
//        }

    }
}
