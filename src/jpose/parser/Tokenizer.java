package jpose.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Tokenizer {
	public List<String> tokenize(String s) {
		Objects.requireNonNull(s);
		
		var l = listOfString(s);
		var t = tokenizerHelper(Chartype.WHITE, List.of(), l);
		var retVal = t.stream().map(this::stringOfList).collect(Collectors.toList());
		return retVal;
	}
	
	private List<List<Character>> tokenizerHelper(Chartype cls, List<Character> acc, List<Character> xs) {
		final List<List<Character>> tk = acc.isEmpty() ? List.of() : List.of((List<Character>) new ArrayList<Character>(acc.reversed()));
		if (xs.isEmpty()) {
			return new ArrayList<>(tk);
		} else {
			var x = xs.getFirst();
			var xsNext = new ArrayList<Character>(xs.subList(1, xs.size()));
			var xCls = Chartype.classifyChar(x);
			if (Character.valueOf('(').equals(x)) {
				var tkOther = tokenizerHelper(Chartype.OTHER, List.of(), xsNext);
				tkOther.add(0, List.of(Character.valueOf('(')));
				var retVal = new ArrayList<>(tk);
				retVal.addAll(tkOther);
				return retVal;
			} else if (Character.valueOf(')').equals(x)) {
				var tkOther = tokenizerHelper(Chartype.OTHER, List.of(), xsNext);
				tkOther.add(0, List.of(Character.valueOf(')')));
				var retVal = new ArrayList<>(tk);
				retVal.addAll(tkOther);
				return retVal;
			} else if (Chartype.WHITE.equals(xCls)) {
				var tkOther = tokenizerHelper(Chartype.WHITE, List.of(), xsNext);
				var retVal = new ArrayList<>(tk);
				retVal.addAll(tkOther);
				return retVal;
			} else if ((Chartype.ALPHA.equals(cls) || Chartype.DIGIT.equals(cls)) && 
			           (Chartype.ALPHA.equals(xCls) || Chartype.DIGIT.equals(xCls))) {
				var accNext = new ArrayList<>(acc);
				accNext.addFirst(x);
				return tokenizerHelper(xCls, accNext, xsNext);
			} else if (Chartype.OTHER.equals(cls) && Chartype.OTHER.equals(xCls)) {
				var accNext = new ArrayList<>(acc);
				accNext.addFirst(x);
				return tokenizerHelper(xCls, accNext, xsNext);
			} else {
				var tkOther = tokenizerHelper(xCls, List.of(x), xsNext);
				var retVal = new ArrayList<>(tk);
				retVal.addAll(tkOther);
				return retVal;
			}
		}
	}
	
	private List<Character> listOfString(String s) {
		final ArrayList<Character> retVal = new ArrayList<>();
		for (char c : s.toCharArray()) {
			retVal.add(c);
		}
		return retVal;
	}
	
	private String stringOfList(List<Character> l) {
		final StringBuilder retVal = new StringBuilder();
		for (char c : l) {
			retVal.append(c);
		}
		return retVal.toString();
	}
}
