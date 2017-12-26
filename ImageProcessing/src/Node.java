public class Node implements Comparable<Node> {
		private int integer = 0;//��������ֵ��Ϣ
		private int frequence = 0;//�����ڵ����
		private Node parent;//���ڵ�
		private Node leftNode;//���ӽڵ�
		private Node rightNode;//���ӽڵ�

		@Override
		public int compareTo(Node n) {
			return frequence - n.frequence;
		}

		public boolean isLeaf() {
			return leftNode == null && rightNode == null;
		}

		public boolean isRoot() {
			return parent == null;
		}

		public boolean isLeftChild() {
			return parent != null && this == parent.leftNode;
		}

		public int getFrequence() {
			return frequence;
		}

		public void setFrequence(int frequence) {
			this.frequence = frequence;
		}

		public int getInteger() {
			return integer;
		}

		public void setInteger(int num) {
			this.integer = num;
		}

		public Node getParent() {
			return parent;
		}

		public void setParent(Node parent) {
			this.parent = parent;
		}

		public Node getLeftNode() {
			return leftNode;
		}

		public void setLeftNode(Node leftNode) {
			this.leftNode = leftNode;
		}

		public Node getRightNode() {
			return rightNode;
		}

		public void setRightNode(Node rightNode) {
			this.rightNode = rightNode;
		}
	}