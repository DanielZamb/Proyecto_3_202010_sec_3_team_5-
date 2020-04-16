package model.data_structures;

public class RedBlackBST<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private NodeRB root;

    class NodeRB {
        Key key;
        Value value;
        NodeRB left;
        NodeRB right;
        int N;
        boolean color;

        NodeRB(Key key, Value value, int N, boolean color) {
            this.key = key;
            this.value = value;
            this.N = N;
            this.color = color;
        }
    }

    private boolean isRed(NodeRB x) {
        if (x == null) return false;
        return x.color == RED;
    }

    private boolean is2Leaf(NodeRB x) {
        if (x.left == null && x.right == null) return true;
        else return false;
    }

    private boolean is3Leaf(NodeRB x) {
        if (x.left.left == null && x.left.right == null && x.right == null) return true;
        else return false;
    }

    private NodeRB rotateLeft(NodeRB x) {
        NodeRB h = x.right;
        x.right = h.left;
        h.left = x;
        h.color = x.color;
        h.N = x.N;
        x.N = size(x);
        return h;
    }

    private NodeRB rotateRight(NodeRB x) {
        NodeRB h = x.left;
        x.left = h.right;
        h.right = x;
        h.color = x.color;
        h.N = x.N;
        x.N = size(x);
        return h;
    }

    private void flipColors(NodeRB x) { //when x (4-node) is ready to split.
        x.left.color = BLACK;
        x.right.color = BLACK;
        x.color = RED;
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
        root.color = BLACK;
    }

    private NodeRB put(NodeRB x, Key key, Value value) { // x is the root of the Red-Black subtree
        if (x == null) // search miss , the root of the subtree is null
            return new NodeRB(key, value, 1, RED); // add the node to the symbol table
        int c = key.compareTo(x.key);
        if (c < 0) x.left = put(x.left, key, value);
        else if (c > 0) x.right = put(x.right, key, value);
        else x.value = value;
        //tree balancing
        if (isRed(x.right) && !isRed(x.left))
            x = rotateLeft(x); // check if right-leaning red link in subtree, if so, rotate root to the left.
        if (isRed(x.left) && isRed(x.left.left))
            x = rotateRight(x); // check if the prior statement caused a double left-leaning (4-node), if so, rotate root to the right.
        if (isRed(x.right) && isRed(x.left))
            flipColors(x);// check if a 4-node caused by the prior statement is ready to split into a 2-node, if so, split.
        //update size of the root argument.
        x.N = size(x);
        //returns 4-node (ready to split) || 4-node(2 left-leaning links in a row)|| 3-node || 3-node(rigth-leaning link) if x null || 2-node;
        return x;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(NodeRB x, Key key) {
        if (x == null)
            return null;
        int c = key.compareTo(x.key);
        if (c < 0)
            return get(x.left, key);
        if (c > 0)
            return get(x.right, key);
        else {
            return x.value;
        }
    }

    private int size() {
        return size(root);
    }

    private int size(NodeRB x) {
        if (x == null) return 0;
        else
            return size(x.left) + 1 + size(x.right);
    }

    public boolean isEmpty() {
        int N = size();
        return (N == 0);
    }

    public int getHeight(Key key) {
        return getHeight(root, key);
    }

    private int getHeight(NodeRB x, Key key) {
        if (x == null) return -1;
        int c = key.compareTo(x.key);
        if (c > 0)
            return 1 + getHeight(x.right, key);
        if (c < 0)
            return 1 + getHeight(x.left, key);
        else
            return 1;
    }

    public boolean contains(Key key) {
        return (get(root, key) != null);
    }

    public int height() {
        return height(root);
    }

    private int height(NodeRB x) {
        if (x == null) return 0;
        else
            return 1 + Math.max(height(x.left), height(x.right));
    }
    private int blackHeigth(){
        return blackHeight(root);
    }
    private int blackHeight(NodeRB x){
        if (x == null) return 0;
        else if (isRed(x.left))
            return Math.max(blackHeight(x.left), 1+ blackHeight(x.right));
        else
            return 1 + Math.max(blackHeight(x.left),blackHeight(x.right));
    }

    public Key min() {
        return min(root);
    }

    private Key min(NodeRB x) {
        if (x == null) return null;
        else if (x.left == null)
            return x.key;
        else
            return min(x.left);
    }

    public Key max() {
        return max(root);
    }

    private Key max(NodeRB x) {
        if (x == null) return null;
        else if (x.right == null)
            return x.key;
        else
            return max(x.right);
    }

    public boolean check() {
        return check(root);
    }

    private boolean check(NodeRB x) {
        boolean RRL = checkRRl(x); //Red Right-leaning Links
        boolean RLR = checkRLR(x); //Red Left-leaning links in a Row
        boolean Order = checkOrder(x);
        boolean blackBalanced = checkBlackBalance(x);
        boolean balancedRB = Order && !RRL && !RLR && blackBalanced;
        return balancedRB;
    }

    private boolean checkOrder(NodeRB x) { // if the put worked then, the only cases of leafs are 3-nodes and 2-nodes (black balanced)
        if (x == null) return true;
        else if (is2Leaf(x))
            return checkOrder(x.left) && checkRRl(x.right); //NullPointer managed , i think.
        else if (is3Leaf(x))
            return checkOrder(x.left) && checkRRl(x.right);
        int c1 = x.key.compareTo(x.left.key); // possible nullPointer if not managed
        int c2 = x.key.compareTo(x.right.key); // same as prior.
        if (c1 > 0 && c2 < 0)
            return checkOrder(x.left) && checkOrder(x.right);
        else
            return false;
    }

    private boolean checkRRl(NodeRB x) {
        if (x == null) return false;
        else if (is2Leaf(x))
            return checkRRl(x.left) && checkRRl(x.right); // if this is not here, then it would output true in a presence of a 2-node that is a leaf
        else if (isRed(x.left) && !isRed(x.right))
            return checkRRl(x.left) && checkRRl(x.right);
        else
            return true;
    }

    private boolean checkRLR(NodeRB x) {
        if (x == null) return false;
        else if (is2Leaf(x))
            return checkRLR(x.left) && checkRLR(x.right);
        else if (isRed(x.left) && !isRed(x.left.left))
            return checkRLR(x.left) && checkRRl(x.right);
        else
            return true;
    }
    private boolean checkBlackBalance(NodeRB x){ //if we check every black height in every subtree in the RedBlackBST we end up cheking the height of every branch, thus we can calculate if the tree is black balanced;
        if (x == null) return true;
        boolean h;
        if (isRed(x.left)) {
            h = blackHeight(x.left) == (1 + blackHeight(x.right));
            if (h)
                return checkBlackBalance(x.left) && checkBlackBalance(x.right);
            else
                return false;
        }
        else{
            h = blackHeight(x.left)==blackHeight(x.right);
            if (h)
                return checkBlackBalance(x.left) && checkBlackBalance(x.right);
            else
                return false;
        }
    }
    //implementar keys, keys in range, values in range


}

