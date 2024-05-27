package jpose.smt;

import java.util.HashMap;
import java.util.List;

import jpose.syntax.SyValue;

public class TrieClause {
	private class Node {
		private HashMap<SyValue, Node> children = new HashMap<>();
	}
	
	private Node root;

	public TrieClause() {
		this.root = new Node();
	}

	public void insert(List<SyValue> word) {
		Node current = this.root;
		for (SyValue c : word) {
			current = current.children.computeIfAbsent(c, k -> new Node());
		}
	}

	public boolean containsPrefix(List<SyValue> prefix) {
		Node current = this.root;
		for (SyValue c : prefix) {
			current = current.children.get(c);
			if (current == null) {
				return false;
			}
		}
		return true;
	}
}
