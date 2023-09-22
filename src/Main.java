import java.io.File;
import java.util.*;

public class Main {

    private static File biggestFile = null;

    private static ArrayList<Listener> listeners = new ArrayList<>();

    public static void main(String[] args) {
        File file = new File("C:/");
        for (File file1: file.listFiles()) {
            System.out.println(file1);
        }

        Scanner input = new Scanner(System.in);
        while (!input.equals("stop")) {
            System.out.println("\n" + "Какой файл смотрим?");
            String a = input.nextLine();
            startAnimationThread();

            File mainFile = new File(a);

            ArrayList<FileObject> files = new ArrayList<>();

            if (mainFile.length() == 0) {
                notifyListeners();
                System.out.println("\nДАБАЕЕЕЕЕЕЕБ, ФАЙЛА НЕТ");
                continue;
            }

            for (File currentFile : mainFile.listFiles()) {
                if (currentFile.isFile()) {
                    FileObject fileObject = new FileObject(currentFile.length(), currentFile);
                    files.add(fileObject);
                    checkIfTheBiggest(currentFile);
                }else if (currentFile.isDirectory()) {
                    FileObject fileObject = new FileObject(getDirectorySize(currentFile), currentFile);
                    files.add(fileObject);
                }
            }

            bubbleSort(files);

            notifyListeners();

            for (FileObject file2 : files) {
                if (file2.getFile().isFile()) {
                    System.out.println(file2.getFile().getPath() + " - файл " + getSize(file2.getSize()));
                } else if (file2.getFile().isDirectory()) {
                    System.out.println(file2.getFile().getPath() + " - папка " + getSize(file2.getSize()));
                }
            }
            if (biggestFile == null) {
                System.out.println("Нет файлов в папке");
                continue;
            }
            FileObject theBiggestFile = new FileObject(biggestFile.length(), biggestFile);
            System.out.println("Наибольший сука член на этом сервере: " + theBiggestFile.getFile().getPath() + " имеет размер " + getSize(theBiggestFile.getSize()));
        }
    }

    public static long getDirectorySize(File file) {
        long directorySize = 0;
        try {
            for (File file1 : file.listFiles()) {
                if (file1.isFile()) {
                    directorySize += file1.length();
                    checkIfTheBiggest(file1);
                } else if (file1.isDirectory()) {
                    directorySize += getDirectorySize(file1);
                }
            }
        } catch (Exception exception) {
            }
        return directorySize;
    }

    private static void checkIfTheBiggest(File file) {
        long size = file.length();
        if (biggestFile == null) {
            biggestFile = file;
            return;
        }
        if (file != null && biggestFile.length() < file.length() && !file.isDirectory()) {
            biggestFile = file;
        }
    }


    public static String getSize(long fileSizeInBytes) {
        double fileSizeInKBytes = (double) fileSizeInBytes / 1000;
        double fileSizeInMBytes = fileSizeInKBytes / 1000;
        double fileSizeInGBytes = fileSizeInMBytes / 1000;
        double value = 0;
        if (fileSizeInBytes < 1000) {
            value = Math.round(fileSizeInBytes);    
            return (value + " байт");
        } else if (fileSizeInKBytes < 1000) {
            value = Math.round(fileSizeInKBytes);
            return (value + " Кб");
        } else if (fileSizeInMBytes < 1000) {
            value = Math.round(fileSizeInMBytes);
            return (value + " Мб");
        } else {
            value = Math.round(fileSizeInGBytes);
            return (value + " Гб");
        }
    }
    private static void bubbleSort(ArrayList<FileObject> files) {
        for (int i = 0; i < files.size(); i++){
            for (int j = 1; j < files.size() - i; j++) {
                FileObject previous = files.get(j - 1);
                FileObject actual = files.get(j);
                if (actual.getSize() < previous.getSize()) {
                    files.set(j - 1, actual);
                    files.set(j, previous);
                }
            }
        }
    }

    private static void startAnimationThread() {
        AnimationThread animationThread = new AnimationThread();
        listeners.add(animationThread);
        animationThread.start();
    }

    private static void notifyListeners() {
        for (Listener listener : listeners) {
            listener.onStop();
        }
    }

}



