import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileSorter {

    private int counter;

    public FileSorter() {
        counter = 0;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        FileSorter fileSorter = new FileSorter();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path for the directory you want to sort: ");
        String path = scanner.nextLine();

        fileSorter.sortDirectory(path);
        fileSorter.renameFilesInDirectory(path);
        fileSorter.sortDirectory(path);

    }

    public void sortDirectory(String path) throws IOException, InterruptedException {
        System.out.println("***********SORTING DIRECTORY***********");
        File[] javaFileList = new File(path).listFiles();
        MyFile[] fileList = new MyFile[javaFileList.length];
        for (int i = 0; i < javaFileList.length; i++) {
            fileList[i] = new MyFile(javaFileList[i]);
        }

        for (MyFile file : fileList) {

            if (file.filetype == Filetype.DIRECTORY) {
                sortDirectory(file.file.getPath());
            } else {
                file.move();
            }

        }
        System.out.println();
    }

    public void renameFilesInDirectory(String path) {
        System.out.println("***********Renaming Remaining Files***********");
        File[] javaFileList = new File(path).listFiles();
        MyFile[] fileList = new MyFile[javaFileList.length];
        for (int i = 0; i < javaFileList.length; i++) {
            fileList[i] = new MyFile(javaFileList[i]);
        }


        for (MyFile file : fileList) {

            if (file.filetype == Filetype.DIRECTORY) {
                renameFilesInDirectory(file.file.getPath());
            } else {
                file.rename(this.counter);
                this.counter++;
            }

        }
        System.out.println();
    }
}