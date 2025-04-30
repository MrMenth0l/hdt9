
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class Compressor {
    public static void compress(String inputPath, String huffPath, String treePath) throws IOException {
        String text = Files.readString(new File(inputPath).toPath());
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        HuffmanTree tree = new HuffmanTree();
        tree.build(freqMap);
        Map<Character, String> codes = tree.getCodes();

        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray()) {
            encoded.append(codes.get(c));
        }

        try (FileOutputStream fos = new FileOutputStream(huffPath)) {
            BitSet bitSet = new BitSet(encoded.length());
            for (int i = 0; i < encoded.length(); i++) {
                if (encoded.charAt(i) == '1') bitSet.set(i);
            }
            byte[] bytes = bitSet.toByteArray();
            fos.write(bytes);
        }

        Files.writeString(new File(treePath).toPath(), tree.serialize());
    }
}
