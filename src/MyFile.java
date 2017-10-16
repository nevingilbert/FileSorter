import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.File;
import java.io.IOException;

public class MyFile {
    public File file;
    public Filetype filetype;
    public MyDate date;
    boolean hasData;

    private Metadata metadata;
    private Directory directory;


    public MyFile(File file) {
        this.file = file;
        if (file.isDirectory()) {
            filetype = Filetype.DIRECTORY;
            date = null;
            metadata = null;
            directory = null;
            hasData = false;
        } else if ((this.isImage() || this.isRaw() && filetype == null)) {
            this.metadata = this.getMetadata();
            if (filetype == null) {

                if (this.isImage()) {
                    this.filetype = Filetype.IMAGE_FILE;
                } else if (this.isRaw()) {
                    this.filetype = Filetype.RAW_FILE;
                }

                this.directory = this.metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
                if (this.directory != null) {
                    if (this.directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL) != null) {
                        this.hasData = true;
                        this.date = new MyDate(directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL).toString());
                    } else {
                        this.hasData = false;
                        this.date = null;
                    }
                } else if (this.directory == null) {
                    this.hasData = false;
                    this.date = null;
                }

            }
        } else if (this.isVideo()) {
            filetype = Filetype.VIDEO;
            date = null;
            metadata = null;
            directory = null;
            hasData = false;
        } else {
            filetype = Filetype.OTHER;
            date = null;
            metadata = null;
            directory = null;
            hasData = false;
        }
    }

    public static String getFileExtension(File file) {
        return file.getName().substring(file.getName().lastIndexOf(".") + 1);
    }

    public void move() {
        this.printDetails();
        Process p = null;
        switch (filetype) {
            case IMAGE_FILE:
                if (hasData) {

                    try {
                        String command = "cmd /c start robocopy /xc /xn /xo \"" + file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('\\')) +
                                "\" \"E:\\" + date.getYear() + "\\iPhone\\" + date.getMonth() + "\" " + "\"" + file.getName() + "\"" + " /mov";
                        System.out.println(command);
                        p = Runtime.getRuntime().exec(command);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        p.waitFor();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        String command = "cmd /c start robocopy /xc /xn /xo \"" + file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('\\')) +
                                "\" \"E:\\No Date\\iPhone\" " + "\"" + file.getName() + "\"" + " /mov";
                        System.out.println(command);
                        p = Runtime.getRuntime().exec(command);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        p.waitFor();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case RAW_FILE:
                if (hasData) {
                    try {
                        String command = "cmd /c start robocopy /xc /xn /xo \"" + file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('\\')) +
                                "\" \"E:\\" + date.getYear() + "\\Unsorted Raw\" " + "\"" + file.getName() + "\"" + " /mov";
                        System.out.println(command);
                        p = Runtime.getRuntime().exec(command);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        p.waitFor();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        String command = "cmd /c start robocopy /xc /xn /xo \"" + file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('\\')) +
                                "\" \"E:\\No Date\\Unsorted Raw\" " + "\"" + file.getName() + "\"" + " /mov";
                        System.out.println(command);
                        p = Runtime.getRuntime().exec(command);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        p.waitFor();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case VIDEO:
                try {
                    String command = "cmd /c start robocopy /xc /xn /xo \"" + file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('\\')) +
                            "\" \"E:\\Video\" " + "\"" + file.getName() + "\"" + " /mov";
                    System.out.println(command);
                    p = Runtime.getRuntime().exec(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    p.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
            case OTHER:
                System.out.println("CANNOT MOVE NON-MEDIA File");
                break;
            case DIRECTORY:
                System.out.println("CANNOT MOVE DIRECTORY");
                break;
        }

    }

    public void rename(int extension) {
        System.out.println("New Name: " + this.file.getAbsolutePath().substring(0, this.file.getAbsolutePath().lastIndexOf('.')) +
                extension + this.file.getName().substring(this.file.getName().lastIndexOf(".")));

        File temp = new File(this.file.getAbsolutePath().substring(0, this.file.getAbsolutePath().lastIndexOf('.')) +
                extension + this.file.getName().substring(this.file.getName().lastIndexOf(".")));
        this.file.renameTo(temp);
    }

    private Metadata getMetadata() {
        System.out.println("Getting Data for: " + file.getAbsolutePath());
        Metadata metadata = null;
        try {
            metadata = ImageMetadataReader.readMetadata(this.file);
        } catch (ImageProcessingException e) {
            System.out.println(e.getMessage());
            filetype = Filetype.OTHER;
            date = null;
            this.metadata = null;
            directory = null;
            hasData = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return metadata;
    }

    public boolean isImage() {
        return getFileExtension(this.file).equalsIgnoreCase("jpg") || getFileExtension(this.file).equalsIgnoreCase("png") ||
                getFileExtension(this.file).equalsIgnoreCase("jpeg");
    }

    public boolean isRaw() {
        return getFileExtension(this.file).equalsIgnoreCase("NEF");
    }

    public boolean isVideo() {
        return getFileExtension(this.file).equalsIgnoreCase("MOV") || getFileExtension(this.file).equalsIgnoreCase("AVI")
                || getFileExtension(this.file).equalsIgnoreCase("FLV") || getFileExtension(this.file).equalsIgnoreCase("WMV") ||
                getFileExtension(this.file).equalsIgnoreCase("MP4");
    }

    public void printDetails() {
        System.out.println("-------------------------------------");
        System.out.println(this.filetype);
        System.out.println("Path: " + this.file.getAbsolutePath());
        if (hasData) {
            System.out.println("Month: " + this.date.getMonth());
            System.out.println("Date: " + this.date.getDate());
            System.out.println("Year: " + this.date.getYear());
            System.out.println();
        } else {
            System.out.println("NO METADATA");
            System.out.println();
        }


    }

}
