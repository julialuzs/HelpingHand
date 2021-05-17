using System.Collections.Generic;
using System;
using System.Text;
using UnityEngine;

/**	Árvore para dicionário de sinais.
 *
 *	Versão 1.1
 *		- Inclusão de "[", "]" e "_" nos possíveis caracteres da key.
 */
public class TrieST<Value>
{
	private static char[] chars = {
		' ',  '-', '(', ')', ',', '%', '[', ']', '_',
		'A', 'Á', 'Ã', 'Â', 'À',
		'B', 'C', 'Ç', 'D', 'E', 'É', 'Ê',
		'F', 'G', 'H', 'I', 'Í', 'J',
		'K', 'L', 'M', 'N', 'O', 'Ó', 'Ô', 'Õ',
		'P', 'Q', 'R', 'S', 'T',
		'U', 'Ú', 'V', 'W', 'X', 'Y', 'Z',
		'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
	};

	private static int R = chars.Length;

	private static int[] indexes = new int[256];

	private Node root;
	private int N;

	private class Node
	{
		public String val;
		public Node[] next = new Node[R];
	}

	public TrieST()
	{
		for (int i = 0; i < R; i++)
			indexes[chars[i]] = i;
	}

	public String get(String key)
	{
		Node x = get(root, key, 0);
		if (x == null) return null;
		return x.val;
	}

	private Node get(Node x, String key, int d)
	{
		if (x == null) return null;
		if (d == key.Length) return x;
		int c = indexes[key[d]];
		return get(x.next[c], key, d + 1);
	}

	public void put(String key, String val)
	{
		if ( ! String.IsNullOrEmpty(key) && ! String.IsNullOrEmpty(val))
			root = put(root, key, val, 0);
	}

	private Node put(Node x, String key, String val, int d)
	{
		if (x == null) x = new Node();
		if (d == key.Length)
		{
			if (x.val == null) N++;
			x.val = val;
			return x;
		}

		try {
			int c = indexes[key[d]];
			x.next[c] = put(x.next[c], key, val, d + 1);
		}
		catch (IndexOutOfRangeException) {
			Debug.Log("Error at TrieST.put: { key: " + key + "; index: " + d + "; value: " + ((int) key[d]) + " }" + key);
		}

		return x;
	}

	public Queue<String> keys()
	{ return keysWithPrefix(""); }

	public Queue<String> keysWithPrefix(String prefix)
	{
		Queue<String> results = new Queue<String>();
		Node x = get(root, prefix, 0); //ref para o primeiro nó que contem a palavra prefix
		collect(x, new StringBuilder(prefix), results);
		return results;
	}

	private void collect(Node x, StringBuilder prefix, Queue<String> results)
	{
		if (x == null) return;
		if (x.val != null) results.Enqueue(prefix.ToString());

		for (char c = (char)0; c < R; c++)
		{
			prefix.Append(chars[c]);

			collect(x.next[c], prefix, results);
			prefix.Remove(prefix.Length - 1, 1);
		}

	}

}