
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class HuffmanTree {
    public HuffmanNode root;
    private Map<Character, String> codes = new HashMap<>();

    public void build(Map<Character, Integer> freqMap) {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            pq.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }
        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode parent = new HuffmanNode('*', left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            pq.add(parent);
        }
        root = pq.poll();
        buildCodes(root, "");
    }

    private void buildCodes(HuffmanNode node, String code) {
        if (node.isLeaf()) {
            codes.put(node.character, code);
        } else {
            buildCodes(node.left, code + "0");
            buildCodes(node.right, code + "1");
        }
    }

    public Map<Character, String> getCodes() {
        return codes;
    }

    public String serialize() {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }

    private void serializeHelper(HuffmanNode node, StringBuilder sb) {
        if (node.isLeaf()) {
            sb.append('L').append(node.character);
        } else {
            sb.append('I');
            serializeHelper(node.left, sb);
            serializeHelper(node.right, sb);
        }
    }

    public void deserialize(String data) {
        Stack<HuffmanNode> stack = new Stack<>();
        for (int i = 0; i < data.length(); i++) {
            char type = data.charAt(i);
            if (type == 'L') {
                char c = data.charAt(++i);
                stack.push(new HuffmanNode(c, 0));
            } else {
                HuffmanNode right = stack.pop();
                HuffmanNode left = stack.pop();
                HuffmanNode parent = new HuffmanNode('*', 0);
                parent.left = left;
                parent.right = right;
                stack.push(parent);
            }
        }
        root = stack.pop();
        codes.clear();
        buildCodes(root, "");
    }
}
