package domain.compression.shannonfano.codegenerator;

import domain.compression.counter.CharacterEntry;

import java.util.*;

/**
 * Created by ensar on 14/02/17.
 */
public class HuffmanCodeGenerator implements CodeGenerator {

    public Map<Character, String> generateCodes(List<CharacterEntry> characterEntryList, Map<Character, String> codeMap) {
        HuffmanNode root = buildTree(characterEntryList);
        return generateCodes(root);
    }

    private HuffmanNode buildTree(List<CharacterEntry> entries) {
        final Queue<HuffmanNode> nodeQueue = createNodeQueue(entries);

        while (nodeQueue.size() > 1) {
            final HuffmanNode node1 = nodeQueue.remove();
            final HuffmanNode node2 = nodeQueue.remove();
            HuffmanNode node = new HuffmanNode('\0', node1.frequency + node2.frequency, node1, node2);
            nodeQueue.add(node);
        }

        // remove it to prevent object leak.
        return nodeQueue.remove();
    }

    private Queue<HuffmanNode> createNodeQueue(List<CharacterEntry> entries) {
        final Queue<HuffmanNode> pq = new PriorityQueue<HuffmanNode>(11, new HuffManComparator());
        for (CharacterEntry entry : entries) {
            pq.add(new HuffmanNode(entry.getCharacter(), entry.getFrequency(), null, null));
        }
        return pq;
    }

    private Map<Character, String> generateCodes(HuffmanNode node) {
        final Map<Character, String> map = new HashMap<Character, String>();
        doGenerateCode(node, map, "");
        return map;
    }


    private void doGenerateCode(HuffmanNode node, Map<Character, String> map, String s) {
        if (node.left == null && node.right == null) {
            map.put(node.ch, s);
            return;
        }
        doGenerateCode(node.left, map, s + '0');
        doGenerateCode(node.right, map, s + '1' );
    }

    private static class HuffmanNode {
        char ch;
        long frequency;
        HuffmanNode left;
        HuffmanNode right;

        HuffmanNode(char ch, long frequency,  HuffmanNode left,  HuffmanNode right) {
            this.ch = ch;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }
    }

    private static class HuffManComparator implements Comparator<HuffmanNode> {
        @Override
        public int compare(HuffmanNode node1, HuffmanNode node2) {
            return (int)(node1.frequency - node2.frequency);
        }
    }
}
