public class Main {
    public static void main(String[] args) {
        String inputPath = "input/example2.txt";
        String compressedPath = "output/example2.huff";
        String treePath = "output/example2.hufftree";
        String decompressedPath = "output/example2_decoded.txt";

        try {
            System.out.println("Comprimiendo archivo...");
            Compressor.compress(inputPath, compressedPath,treePath); 
            System.out.println("Archivo comprimido en: " + compressedPath);

            System.out.println("Descomprimiendo archivo...");
            Decompressor.decompress(compressedPath, treePath, decompressedPath);
            System.out.println("Archivo descomprimido en: " + decompressedPath);
        } catch (Exception e) {
            System.err.println("Error durante compresión/descompresión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}