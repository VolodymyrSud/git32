import Keys.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Menu {

    private static final int EXIT_NUMBER = 0;
    private static final int DECODE_FILE = 1;
    private static final int ENCODE_FILE = 2;

    private static final String MENU_INFO =
            """
                    Select an action:
                    1. Encode file
                    2. Decode file
                    0. Exit
                    """;

    private static final String OPTIONS_FOR_ENCODE =
            """
                    1. Move letter on 3 points
                    2. Move letter on 4 points
                    3. Move letter on 5 points
                    """;

    private static final String OPTIONS_FOR_DECODE =
            """
                    1. Move letter on 3 points
                    2. Move letter on 4 points
                    3. Move letter on 5 points
                    4. Decode with brute force
                    """;

    private static boolean isRunning = true;

    final CeasarCypher ceasarCypher = new CeasarCypher();

    final Scanner scanner = new Scanner(System.in);



    public void run() {

        while (isRunning) {
            System.out.println(MENU_INFO);
            int menuItem = scanner.nextInt();

            switch (menuItem) {
                case ENCODE_FILE -> itemToEncodeFile();
                case DECODE_FILE -> itemToDecodeFile();
                case EXIT_NUMBER -> itemToExit();
            }
        }
    }

    private void itemToEncodeFile() {
        System.out.println(OPTIONS_FOR_ENCODE);
        var encodingScanner = new Scanner(System.in);
        int input = encodingScanner.nextInt();

        Key key = switch (input) {
            case 1 -> new EncodingWithShiftBy3Letters();
            case 2 -> new EncodingWithShiftBy4Letters();
            case 3 -> new EncodingWithShiftBy5Letters();
            default -> null;
        };

        ceasarCypher.encode(validateFilePath(), key);
    }
    private void itemToDecodeFile() {
        System.out.println(OPTIONS_FOR_DECODE);
        var decodingScanner = new Scanner(System.in);
        int input = decodingScanner.nextInt();

        Key key = switch (input) {
            case 1 -> new DecodingWithShiftBy3Letters();
            case 2 -> new DecodingWithShiftBy4Letters();
            case 3 -> new DecodingWithShiftBy5Letters();
            case 4 -> new BruteForce();
            default -> null;
        };
        ceasarCypher.decode(validateFilePath(), key);
    }

    private void itemToExit() {
        isRunning = false;
        System.out.println("Bye bye!");
    }

    private String validateFilePath() {
        Scanner scannerForFileName = new Scanner(System.in);
        System.out.println("Provide path to file: ");
        String filePath = scannerForFileName.nextLine();
        if (!Files.exists(Path.of(filePath))) {
            throw new FilePathNotValid("Invalid file Path!");
        }
        return filePath;
    }
}
