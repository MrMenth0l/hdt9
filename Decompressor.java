
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.BitSet;

public class Decompressor {
    public static void decompress(String huffPath, String treePath, String outputPath) throws IOException {
        byte[] bytes = Files.readAllBytes(new File(huffPath).toPath());
        BitSet bitSet = BitSet.valueOf(bytes);
        StringBuilder bitString = new StringBuilder();
        for (int i = 0; i < bytes.length * 8; i++) {
            bitString.append(bitSet.get(i) ? '1' : '0');
        }

        String treeData = Files.readString(new File(treePath).toPath());
        HuffmanTree tree = new HuffmanTree();
        tree.deserialize(treeData);

        StringBuilder decoded = new StringBuilder();
        HuffmanNode node = tree.root;
        for (int i = 0; i < bitString.length(); i++) {
            node = bitString.charAt(i) == '0' ? node.left : node.right;
            if (node.isLeaf()) {
                decoded.append(node.character);
                node = tree.root;
            }
        }

        Files.writeString(new File(outputPath).toPath(), decoded.toString());
    }
}
