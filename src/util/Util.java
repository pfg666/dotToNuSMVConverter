package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Util {
	public static String repeatString(String s, int times) {
		String result = "";
		for (int i = 0; i < times; i++) {
			result += s;
		}
		return result;
	}
	
	public <S,T,U> Map<S,Pair<T,U>> toTupleMap(Map<S,Triple<T,U,?>> tripleMap) {
		Map<S,Pair<T,U>> result = new HashMap<>();
		for (Entry<S,Triple<T,U,?>> entry : tripleMap.entrySet()) {
			result.put(entry.getKey(), new Pair<T,U>(entry.getValue().el1, entry.getValue().el2));
		}
		return result;
	}
}
