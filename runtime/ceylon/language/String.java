package ceylon.language;

import com.redhat.ceylon.compiler.java.language.AbstractCallable;
import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Class;
import com.redhat.ceylon.compiler.java.metadata.Defaulted;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.SatisfiedTypes;
import com.redhat.ceylon.compiler.java.metadata.Sequenced;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;

@Ceylon(major = 1)
@Class(extendsType="ceylon.language.Object")
@SatisfiedTypes({"ceylon.language.List<ceylon.language.Character>",
                 "ceylon.language.Comparable<ceylon.language.String>",
                 "ceylon.language.Ranged<ceylon.language.Integer,ceylon.language.String>",
                 "ceylon.language.FixedSized<ceylon.language.Character>",
                 "ceylon.language.Summable<ceylon.language.String>",
                 "ceylon.language.Castable<ceylon.language.String>",
                 "ceylon.language.Cloneable<ceylon.language.String>"})
public abstract class String
    implements Comparable<String>, List<Character>,
               Summable<String>, Castable<String>,
               FixedSized<Character> {

    public final java.lang.String value;

    String(java.lang.String s) {
        value = s;
    }

    public java.lang.String toString() {
        return value;
    }

    @Ignore
    public static ceylon.language.String instance(java.lang.String s) {
        if (s==null) return null;
        return s.isEmpty() ? StringOfNone.instance : new StringOfSome(s);
    }

    @Ignore
    public static ceylon.language.String instance(java.lang.String... strings) {
        StringBuffer buf = new StringBuffer();
        for (java.lang.String s: strings)
            buf.append(s);
        return instance(buf.toString());
    }

    @Ignore
    public static ceylon.language.String instance(String... strings) {
        StringBuffer buf = new StringBuffer();
        for (String s: strings)
            buf.append(s.value);
        return instance(buf.toString());
    }

    public java.lang.String getUppercased() {
        return value.toUpperCase();
    }

    public java.lang.String getLowercased() {
        return value.toLowerCase();
    }

    @Override
    public boolean equals(@Name("that") java.lang.Object that) {
        if (that instanceof String) {
            String s = (String)that;
            return value.equals(s.value);
        } 
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public Comparison compare(@Name("other") String other) {
        int c = value.compareTo(other.value);
        return (c < 0) ? smaller.getSmaller() :
            ((c == 0) ? equal.getEqual() : larger.getLarger());
    }

    /*@Override
    public boolean largerThan(@Name("other") String other) {
        return value.compareTo(other.value) > 0;
    }

    @Override
    public boolean smallerThan(@Name("other") String other) {
        return value.compareTo(other.value) < 0;
    }

    @Override
    public boolean asLargeAs(@Name("other") String other) {
        return value.compareTo(other.value) >= 0;
    }

    @Override
    public boolean asSmallAs(@Name("other") String other) {
        return value.compareTo(other.value) <= 0;
    }*/

    @Override
    public <CastValue extends String> CastValue castTo() {
        return (CastValue) this;
    }

    @Override
    public String plus(@Name("other") String string) {
        return instance(value + string.value);
    }

    @Override
    @TypeInfo("ceylon.language.Integer")
    public long getSize() {
        //TODO: should we cache this value in an instvar?
        return value.codePointCount(0, value.length());
    }

    @TypeInfo("ceylon.language.Nothing|ceylon.language.Integer")
    public Integer getLastIndex() {
        long length = getSize();
        return (length == 0) ? null : Integer.instance(length - 1);
    }

    @Override
    public boolean getEmpty() {
        return value.isEmpty();
    }

    @Override
    public Character item(@Name("index") Integer key) {
        int index = (int)key.longValue();
        int length = value.length();
        if (index < 0 || index >= length) {
            return null;
        }
        int offset = value.offsetByCodePoints(0, index);
        int codePoint = value.codePointAt(offset);
        return new Character(codePoint);
    }

    @Override
    public boolean defines(@Name("index") Integer key) {
        long index = key.longValue();
        return index >= 0 && index < getSize();
    }

    @Override
    @Ignore
    public Category getKeys() {
        return Correspondence$impl._getKeys(this);
    }

    @Override
    @Ignore
    public boolean definesEvery(@Name("keys") 
    @TypeInfo("ceylon.language.Empty|ceylon.language.Sequence<ceylon.language.Integer>")
    Iterable<? extends Integer> keys) {
        //TODO: inefficient ... better to cache the result
        //      of getSize()
        return Correspondence$impl._definesEvery(this, keys);
    }
    @Override
    @Ignore
    public boolean definesEvery() {
        return Correspondence$impl._definesEvery(this, (Iterable)$empty.getEmpty());
    }
    @Override
    @Ignore
    public Iterable<? extends Integer> definesEvery$keys() {
        return (Iterable)$empty.getEmpty();
    }

    @Override
    @Ignore
    public boolean definesAny(@Name("keys") 
    @TypeInfo("ceylon.language.Empty|ceylon.language.Sequence<ceylon.language.Integer>")
    Iterable<? extends Integer> keys) {
        //TODO: inefficient ... better to cache the result
        //      of getSize()
        return Correspondence$impl._definesAny(this, keys);
    }
    @Override
    @Ignore
    public boolean definesAny() {
        return Correspondence$impl._definesAny(this, (Iterable)$empty.getEmpty());
    }
    @Override
    @Ignore
    public Iterable<? extends Integer> definesAny$keys() {
        return (Iterable)$empty.getEmpty();
    }

    @Override
    @Ignore
    public List<? extends Character> items(@Name("keys") 
    @TypeInfo("ceylon.language.Empty|ceylon.language.Sequence<ceylon.language.Integer>")
    Iterable<? extends Integer> keys) {
        return Correspondence$impl._items(this, keys);
    }
    @Override
    @Ignore
    public List<? extends Character> items() {
        return Correspondence$impl._items(this, (Iterable)$empty.getEmpty());
    }
    @Override
    @Ignore
    public Iterable<? extends Integer> items$keys() {
        return (Iterable)$empty.getEmpty();
    }

    @Override
    @TypeInfo("ceylon.language.Iterator<ceylon.language.Character>")
    public Iterator<Character> getIterator() {
        class StringIterator implements Iterator<Character> {
            private int offset = 0;

            @Override
            public java.lang.Object next() {
                java.lang.Object result;
                if (offset < value.length()) {
                    int codePoint = value.codePointAt(offset);
                    result = Character.instance(codePoint);
                    offset += java.lang.Character.charCount(codePoint);
                } else {
                    result = exhausted.getExhausted();
                }
                return result;
            }
        }
        
        return new StringIterator();
    }

    @TypeInfo("ceylon.language.Empty|ceylon.language.Sequence<ceylon.language.Character>")
    public Iterable<? extends Character> getCharacters() {
        int length = value.length();
        if (length == 0) {
            return (Iterable)$empty.getEmpty();
        }
        Character[] chars = new Character[(int)getSize()];
        for (int offset = 0, i = 0; offset < length; i++) {
            int codePoint = value.codePointAt(offset);
            chars[i] = new Character(codePoint);
            offset += java.lang.Character.charCount(codePoint);
        }
        return new ArraySequence<Character>(chars);
    }
    
    @TypeInfo("ceylon.language.Nothing|ceylon.language.Integer")
    public Integer firstOccurrence(@Name("substring") java.lang.String substring) {
        int index = value.indexOf(substring);
        return (index >= 0) ? Integer.instance(value.codePointCount(0, index)) : null;
    }
    
    @TypeInfo("ceylon.language.Nothing|ceylon.language.Integer")
    public Integer lastOccurrence(@Name("substring") java.lang.String substring) {
        int index = value.lastIndexOf(substring);
        return (index >= 0) ? Integer.instance(value.codePointCount(0, index)) : null;
    }
    
    @TypeInfo("ceylon.language.Nothing|ceylon.language.Integer")
    public Integer firstCharacterOccurrence(@Name("character") 
    @TypeInfo("ceylon.language.Character") int character) {
        int index = value.indexOf(character);
        return (index >= 0) ? Integer.instance(value.codePointCount(0, index)) : null;
    }
    
    @TypeInfo("ceylon.language.Nothing|ceylon.language.Integer")
    public Integer lastCharacterOccurrence(@Name("character") 
    @TypeInfo("ceylon.language.Character") int character) {
        int index = value.lastIndexOf(character);
        return (index >= 0) ? Integer.instance(value.codePointCount(0, index)) : null;
    }
    
    @Override
    public boolean contains(@Name("element") java.lang.Object element) {
        if (element instanceof String) {
            return value.indexOf(((String)element).value) >= 0;
        }
        else if (element instanceof Character) {
            return value.indexOf(((Character)element).intValue()) >= 0;
        }
        else {
            return false;
        }
    }
    
    @Override
    public long count(@Name("element") java.lang.Object element) {
        throw new Exception(null, null); //TODO!!!!!
    }
    
    public boolean startsWith(@Name("substring") java.lang.String substring) {
        return value.startsWith(substring);
    }
    
    public boolean endsWith(@Name("substring") java.lang.String substring) {
        return value.endsWith(substring);
    }
    
    @Override
    @Ignore
    public boolean containsAny(@Sequenced @Name("elements") 
    @TypeInfo("ceylon.language.Iterable<ceylon.language.Object>")
    Iterable<?> elements) {
        return Category$impl._containsAny(this, elements);
    }
    @Override
    @Ignore
    public boolean containsAny() {
        return Category$impl._containsAny(this, $empty.getEmpty());
    }
    @Override
    @Ignore
    public Iterable<?> containsAny$elements() {
        return $empty.getEmpty();
    }

    @Override
    @Ignore
    public boolean containsEvery(@Sequenced @Name("elements") 
    @TypeInfo("ceylon.language.Iterable<ceylon.language.Object>")
    Iterable<?> elements) {
        return Category$impl._containsEvery(this, elements);
    }
    @Override
    @Ignore
    public boolean containsEvery() {
        return Category$impl._containsEvery(this, $empty.getEmpty());
    }
    @Override
    @Ignore
    public Iterable<?> containsEvery$elements() {
        return $empty.getEmpty();
    }
    
    public boolean longerThan(@TypeInfo("ceylon.language.Integer") 
    @Name("length") long length) {
        try {
            value.offsetByCodePoints(0, (int)length+1);
            return true;
        }
        catch (IndexOutOfBoundsException iobe) {
            return false;
        }
    }

    public boolean shorterThan(@TypeInfo("ceylon.language.Integer") 
    @Name("length") long length) {
        try {
            value.offsetByCodePoints(0, (int)length);
            return false;
        }
        catch (IndexOutOfBoundsException iobe) {
            return true;
        }
    }
    
    public java.lang.String getTrimmed() {
        return value.trim();
    }
    
    public java.lang.String getNormalized() {
        java.lang.StringBuilder result = new java.lang.StringBuilder();
        boolean previousWasWhitespace=false;
        for (int i=0;i<value.length();) {
            int c = java.lang.Character.codePointAt(value, i);
            boolean isWhitespace = java.lang.Character.isWhitespace(c);
            if (!isWhitespace) {
                result.appendCodePoint(c);
            }
            else if (!previousWasWhitespace) {
                result.append(" ");
            }
            previousWasWhitespace = isWhitespace;
            i+=java.lang.Character.charCount(c);
        }
        return result.toString();
    }
    
    @TypeInfo("ceylon.language.String")
    public java.lang.String initial(@TypeInfo("ceylon.language.Integer") 
    @Name("length") long length) {
    	if (length <= 0) {
    	    return "";
    	} else if (length >= getSize()) {
            return value; 
        } else {
            int offset = value.offsetByCodePoints(0, (int)length);
            return value.substring(0, (int)offset);
        }
    }

    @TypeInfo("ceylon.language.String")
    public java.lang.String terminal(@TypeInfo("ceylon.language.Integer") 
    @Name("length") long length) {
    	if (length <= 0) {
    	    return "";
    	} else if (length >= getSize()) {
    	    return value; 
    	} else {
            int offset = value.offsetByCodePoints(0, value.length()-(int)length);
    	    return value.substring(offset, value.length());
    	}
    }
    
    public java.lang.String join(@Name("strings") @Sequenced
    @TypeInfo("ceylon.language.Iterable<ceylon.language.String>")
    Iterable<? extends String> strings) {
        java.lang.StringBuilder result = new java.lang.StringBuilder();
        Iterator<? extends String> it = strings.getIterator();
        java.lang.Object elem = it.next();
        if (elem != exhausted.getExhausted()) {
            result.append(elem);
            for (;!((elem = it.next()) instanceof Finished);) {
                result.append(value).append(elem);
            }
        }
        return result.toString();
    }
    @Ignore
    public java.lang.String join() {
        return join((Iterable)$empty.getEmpty());
    }
    
    @Override
    @TypeInfo("ceylon.language.String")
    public String segment(@Name("from") final Integer from, 
            @Name("length") final long length) {
        long fromIndex = from.longValue();
        long resultLength = length;
        long len = getSize();
        if (fromIndex >= len || resultLength <= 0) {
            return instance("");
        }
        if ((fromIndex + resultLength) > len) {
            resultLength = len - fromIndex;
        }
        int start = value.offsetByCodePoints(0, (int)fromIndex);
        int end = value.offsetByCodePoints(start, (int)resultLength);
        return instance(value.substring(start, end));
    }
    
    @Override
    @TypeInfo("ceylon.language.String")
    public String span(@Name("from") final Integer from, 
            @Name("to") @TypeInfo("ceylon.language.Nothing|ceylon.language.Integer") 
            final Integer to) {
        long len = getSize();
        if (len == 0) {
            return instance("");
        }
        long fromIndex = from.longValue();
        long toIndex = (to == null) ? len - 1 : to.longValue();
        if (fromIndex >= len || toIndex < fromIndex) {
            return instance("");
        }
        if (toIndex >= len) {
            toIndex = len - 1;
        }
        int start = value.offsetByCodePoints(0, (int)fromIndex);
        int end = value.offsetByCodePoints(start, (int)(toIndex - fromIndex + 1));
        return instance(value.substring(start, end));
    }
    
    public java.lang.String getReversed() {
        long len = getSize();
        if (len < 2) {
            return value;
        }
        java.lang.StringBuilder builder = new java.lang.StringBuilder();
        int offset = value.length();
        while (offset > 0) {
            int c = value.codePointBefore(offset);
            builder.appendCodePoint(c);
            offset -= java.lang.Character.charCount(c);
        }
        return builder.toString();
    }
    
    public java.lang.String repeat(
            @TypeInfo("ceylon.language.Integer") 
            @Name("times") long times) {
        int len = value.length();
        if (times<=0 || len==0) return "";
        if (times==1) return value;
        java.lang.StringBuilder builder = new java.lang.StringBuilder(len*(int)times);
        for (int i=0; i<times; i++) {
            builder.append(value);
        }
        return builder.toString();
    }
    
    public java.lang.String replace( 
            @Name("substring") java.lang.String substring,
            @Name("replacement") java.lang.String replacement) {
        return value.replace(substring, replacement);
    }
    
    @TypeInfo("ceylon.language.Iterable<ceylon.language.String>")
    public Iterable<? extends String> split(
            @TypeInfo("ceylon.language.Callable<ceylon.language.Boolean,ceylon.language.Character>")
            @Defaulted
            @Name("separator") Callable<? extends Boolean> separator,
            @Defaulted
            @Name("discardSeparators") boolean discardSeparators,
            @Defaulted
            @Name("groupSeparators") boolean groupSeparators) {
        if (value.isEmpty()) {
            return new Singleton<String>(this);
        }
        return new Tokens(value, separator, !discardSeparators, groupSeparators);
    }
    
    @Ignore
    public Iterable<? extends String> split(
            Callable<? extends Boolean> separator,
            boolean discardSeparators) {
        return split(separator, discardSeparators, split$groupSeparators(separator, discardSeparators));
    }

    @Ignore
    public Iterable<? extends String> split(
            Callable<? extends Boolean> separator) {
        return split(separator, split$discardSeparators(separator));
    }
    
    @Ignore
    public Iterable<? extends String> split() {
        return split(split$separator());
    }
    
    @TypeInfo("ceylon.language.Iterable<ceylon.language.String>")
    public Iterable<? extends String> getLines() {
        return split(new AbstractCallable<Boolean>("whitespace") {
            @Override
            public Boolean $call(java.lang.Object ch) {
                return Boolean.instance(((Character) ch).toString().equals("\n"));
            }
        }, true);
    }
    @TypeInfo("ceylon.language.Iterable<ceylon.language.Integer>")
    public Iterable<? extends Integer> occurrences(
            @Name("substring") java.lang.String substring) {
        return new Occurs(value, substring);
    }

    private static final class Tokens implements Iterable<String> {
        private final java.lang.String str;
        private final ceylon.language.Callable<? extends Boolean> separator;
        private final boolean keepSeparators;
        private final boolean groupSeparators;
        
        public Tokens(java.lang.String str, ceylon.language.Callable<? extends Boolean> separator, 
                boolean keepSeparators, boolean groupSeparators) {
            this.str = str;
            this.separator = separator;
            this.keepSeparators = keepSeparators;
            this.groupSeparators = groupSeparators;
        }

        @Override
        public Iterator<? extends String> getIterator() {
            class TokenIterator implements Iterator<String> {
                private final char[] chars = str.toCharArray();
                private int index = 0;
                private boolean first = true;
                private boolean lastTokenWasSeparator = false;

                @Override
                public java.lang.Object next() {
                    if (!eof()) {
                        int start = index;
                        // if we start with a separator, or if we returned a separator the last time
                        // and we are still looking at a separator: return an empty token once
                        if(((first && start == 0)
                                || lastTokenWasSeparator)
                                && peekSeparator()){
                            first = false;
                            lastTokenWasSeparator = false;
                            return String.instance("");
                        }
                        // are we looking at a separator
                        if(eatSeparator()){
                            if(groupSeparators){
                                // eat them all in one go if we group them
                                do{}while(eatSeparator());
                            }
                            // do we return them?
                            if(keepSeparators){
                                lastTokenWasSeparator = true;
                                return String.instance(new java.lang.String(chars, start, index-start));
                            }
                            // keep going and eat the next word
                            start = index;
                        }
                        // eat until the next separator
                        while(!eof() && !peekSeparator()){
                            eatChar();
                        }
                        lastTokenWasSeparator = false;
                        return String.instance(new java.lang.String(chars, start, index-start));
                    } else if (lastTokenWasSeparator){
                        // we're missing a last empty token before the EOF because the string ended
                        // with a returned separator
                        lastTokenWasSeparator = false;
                        return String.instance("");
                    } else {
                        return exhausted.getExhausted();
                    }
                }
                
                private boolean eof(){
                    return index >= chars.length;
                }

                private boolean eatSeparator() {
                    boolean ret = peekSeparator();
                    if(ret)
                        eatChar();
                    return ret;
                }
                
                private void eatChar() {
                    if(java.lang.Character.isHighSurrogate(chars[index]))
                        index += 2;
                    else
                        index++;
                }

                private boolean peekSeparator() {
                    if(eof())
                        return false;
                    int charCodePoint = java.lang.Character.codePointAt(chars, index);
                    return separator.$call(Character.instance(charCodePoint)).booleanValue();
                }
            }
            
            return new TokenIterator();
        }

        @Override
        public boolean getEmpty() {
            return getIterator().next() == exhausted.getExhausted();
        }

        /*@Override
        public String getFirst() {
            java.lang.Object result = getIterator().next();
            return (String) ((result != exhausted.getExhausted()) ? result : null);
        }*/
        @Override 
        @Ignore
        public Iterable<? extends String> getSequence() { 
            return Iterable$impl._getSequence(this); 
        }
        @Override 
        @Ignore
        public String find(Callable<? extends Boolean> f) { 
            return Iterable$impl._find(this, f); 
        }
        @Override 
        @Ignore
        public Iterable<? extends String> sorted(Callable<? extends Comparison> f) { 
            return Iterable$impl._sorted(this, f); 
        }
        @Override 
        @Ignore
        public <Result> Iterable<Result> map(Callable<? extends Result> f) { 
            return new MapIterable<String, Result>(this, f); 
        }
        @Override 
        @Ignore
        public Iterable<? extends String> filter(Callable<? extends Boolean> f) { 
            return new FilterIterable<String>(this, f); 
        }
        @Override
        @Ignore
        public <Result> Result fold(Result ini, Callable<? extends Result> f) { 
            return Iterable$impl._fold(this, ini, f); 
        }
        @Override @Ignore
        public boolean any(Callable<? extends Boolean> f) {
            return Iterable$impl._any(this, f);
        }
        @Override @Ignore
        public boolean every(Callable<? extends Boolean> f) {
            return Iterable$impl._every(this, f);
        }
    }

    private static final class Occurs implements Iterable<Integer> {
        private final java.lang.String str;
        private final java.lang.String oc;
        
        public Occurs(java.lang.String str, java.lang.String oc) {
            this.str = str;
            this.oc = oc;
        }

        @Override
        public Iterator<? extends Integer> getIterator() {
            class OccurrenceIterator implements Iterator<Integer> {
                private int pos=0;

                @Override
                public java.lang.Object next() {
                    int idx = str.indexOf(oc, pos);
                    if (idx >= pos) {
                        pos = idx+oc.length();
                        return Integer.instance(idx);
                    }
                    return exhausted.getExhausted();
                }
            }
            
            return new OccurrenceIterator();
        }

        @Override
        public boolean getEmpty() {
            return getIterator().next() == exhausted.getExhausted();
        }

        /*@Override
        public String getFirst() {
            java.lang.Object result = getIterator().next();
            return (String) ((result != exhausted.getExhausted()) ? result : null);
        }*/
        @Override 
        @Ignore
        public Iterable<? extends Integer> getSequence() { 
            return Iterable$impl._getSequence(this); 
        }
        @Override 
        @Ignore
        public Integer find(Callable<? extends Boolean> f) { 
            return Iterable$impl._find(this, f); 
        }
        @Override 
        @Ignore
        public Iterable<? extends Integer> sorted(Callable<? extends Comparison> f) { 
            return Iterable$impl._sorted(this, f); 
        }
        @Override 
        @Ignore
        public <Result> Iterable<Result> map(Callable<? extends Result> f) { 
            return new MapIterable<Integer, Result>(this, f); 
        }
        @Override 
        @Ignore
        public Iterable<? extends Integer> filter(Callable<? extends Boolean> f) { 
            return new FilterIterable<Integer>(this, f); 
        }
        @Override 
        @Ignore
        public <Result> Result fold(Result ini, Callable<? extends Result> f) { 
            return Iterable$impl._fold(this, ini, f); 
        }
        @Override @Ignore
        public boolean any(Callable<? extends Boolean> f) {
            return Iterable$impl._any(this, f);
        }
        @Override @Ignore
        public boolean every(Callable<? extends Boolean> f) {
            return Iterable$impl._every(this, f);
        }
    }

    @Override
    public String getClone() {
        return this;
    }

    @Ignore
    public Callable<? extends Boolean> split$separator(){
        return new AbstractCallable<Boolean>("whitespace") {
            @Override
            public Boolean $call(java.lang.Object ch) {
                return Boolean.instance(((Character) ch).getWhitespace());
            }
        };
    }
    @Ignore
    public boolean split$discardSeparators(Callable<? extends Boolean> separator){
        return true;
    }
    @Ignore
    public boolean split$groupSeparators(Callable<? extends Boolean> separator, boolean discardSeparators){
        return true;
    }
}
