using System.Collections.Generic;
using Newtonsoft.Json;


namespace LAViD.Structures{

    public class Trie
    {
        public Node root { get; set; }
    }

    public class Node
    {
        public IDictionary<string, Node> children { get; set; }
        public bool end { get; set; }
    }
    
    public class TrieParser
    {
        public static Trie ParseJSON(string json)
        {
            Trie trie = JsonConvert.DeserializeObject<Trie>(json);
            return trie;
        }

        public static List<string> StartsWith(Trie trie, string word)
        {
            PlayerLogger.Log("T", "SW", "Searching for '" + word + "'.");

            List<string> list = new List<string>();
            if(trie == null)
                return list;
            Node root = trie.root;
            if (root == null) return list;
            var children = root.children;
            if(children.Keys.Count == 0)
            {
                return list;               
            }
            Node node = root;
                     
            foreach (char c in word)
            {
                    if (children.TryGetValue(c.ToString(), out Node value))
                    {
                        node = value;
                        children = node.children;
                    }
                    else
                    {
                    return list;
                    }
                                       
            }

            if ( node != null) feed(list, word, node);
            return list;

        }

        private static bool isLastNode(Node node)
        {
            return node.children.Keys.Count == 0;
            
        }

        private static void feed(List<string> list, string word, Node node)
        {
            if (node.end) {
                list.Add(word);
            }

            if (isLastNode(node))
                return;

            var children = node.children;
            var keys = children.Keys;
            
            foreach( string key in keys){
                feed(list, word + key, children[key]);
            }
        }
    }


}

