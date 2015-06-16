

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class WordChain {

	public static class Head {
		
		private final String value;
		
		private int level = 0;
		
		private Set<Head> childs = new HashSet<Head>();
		
		public Head(String value) {
			this.value = value;
		}
		
		public int add(Head child) {
			
			int childLevel = child.level;
			if (level < childLevel + 1) {
				childs.clear();
				childs.add(child);
				level = childLevel + 1;
			}
			else if (level == childLevel + 1) {
				childs.add(child);
			}
			
			return level;
		}

		private void print(String prefix, StringBuilder out) {
	    String line;
	    if (prefix.length() > 0) {
	    	line = prefix + " => " + value;
	    }
	    else {
	    	line = value;
	    }
			
			if (childs.isEmpty()) {
				out.append(line).append("\n");
			}
			else {
				for (Head c : childs) {
					c.print(line, out);
				}
			}
		}
		
		@Override
		public String toString() {
			StringBuilder out = new StringBuilder();
			print("", out);
			return out.toString();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Head other = (Head) obj;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}
		
		
		
	}
	
	public static class Dict {
		
		private final static Map<String, Head> map = new HashMap<String, Head>();
		
		public Head get(String word) {
			return map.get(word);
		}
		
		public void put(String word) {
			map.put(word, new Head(word));
		}
		
		public Set<Map.Entry<String, Head>> entrySet() {
			return map.entrySet();
		}
		
	}

	public static class Holder {

		public static final TreeMap<Integer, Dict> letters = new TreeMap<Integer, Dict>();

		public Dict getOrCreateDict(Integer len) {
			Dict d = letters.get(len);
			if (d == null) {
				d = new Dict();
				letters.put(len, d);
			}
			return d;
		}
		
		public Dict getDict(Integer len) {
			return letters.get(len);
		}
		
		public int getMin() {
			return letters.keySet().iterator().next();
		}
		
	}

	public static final Holder holder = new Holder();

	public static void main(String[] args) throws Exception {
		
		if (args.length < 1) {
			System.out.println("Usage: java -jar WordChain path_to_dict");
			return;
		}
		
		String file = args[0];
		
		BufferedReader b = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

		try {
			loadDict(b);
		}
		finally {
			b.close();
		}
		
		algorithm();
		
	}

	private static void loadDict(BufferedReader b) throws IOException {
		while(true) {
			
			String line = b.readLine();
			if (line == null) {
				break;
			}
			
			String word = line.trim();
			int len = word.length();
			if (len > 0) {
				holder.getOrCreateDict(len).put(word);
			}
			
		}
	}
	
	public static void algorithm() {

		int maxLevel = -1;
		Set<Head> maxHeads = new HashSet<Head>();
		
		int i = holder.getMin();
		
		while(true) {
			
			Dict first = holder.getDict(i);
			Dict second = holder.getDict(i+1);
			if (second == null) {
				break;
			}
			
			for (Map.Entry<String, Head> e : second.entrySet()) {
				
				ShortterIterator iter = new ShortterIterator(e.getKey());
				
				while(iter.hasNext()) {
					
					String possibleWord = iter.next();
					
					Head head = first.get(possibleWord);
					if (head != null) {
						
						int level = e.getValue().add(head);
						
						if (maxLevel < level) {
							maxLevel = level;
							maxHeads.clear();
							maxHeads.add(e.getValue());
						}
						else if (maxLevel == level) {
							maxHeads.add(e.getValue());
						}
						
					}
					
				}
				
				
			}
			
			
			++i;
			
		}
		
		for (Head h : maxHeads) {
			System.out.print(h);
		}
		
	}
	
	
	public static final class ShortterIterator implements Iterator<String> {

		private final String source;
		private final int len;
		private int index = 0;
		
		public ShortterIterator(String source) {
			this.source = source;
			this.len = source.length();
		}
		
		@Override
		public boolean hasNext() {
			return index < len;
		}
		
		@Override
		public String next() {
			String val = toString();
			index++;
			return val;
		}

		@Override
		public String toString() {
			if (index == 0) {
				return source.substring(1);
			}
			else if (index + 1 == len) {
				return source.substring(0, index);
			}
			else {
				return source.substring(0, index) + source.substring(index + 1, source.length());
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
}
