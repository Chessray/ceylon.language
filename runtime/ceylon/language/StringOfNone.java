package ceylon.language;

import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Ignore;

@Ignore
@Ceylon(major = 1)
class StringOfNone extends String implements None<Character> {
    
    static StringOfNone instance = new StringOfNone();
    
    private StringOfNone() { 
        super(""); 
    }
    
    @Override
    public long getSize() {
        return 0;
    }
    
    @Override
    public boolean getEmpty() {
        return true;
    }
    
    @Override
    public Character getFirst() {
        return null;
    }

    @Override 
    @Ignore
    public Iterable<? extends Character> getSequence() { 
        return Iterable$impl._getSequence(this); 
    }
    @Override 
    @Ignore
    public Character find(Callable<? extends Boolean> f) { 
        return Iterable$impl._find(this, f); 
    }
    @Override 
    @Ignore
    public Iterable<? extends Character> sorted(Callable<? extends Comparison> f) { 
        return this; 
    }
    @Override 
    @Ignore
    public <Result> Iterable<Result> map(Callable<? extends Result> f) { 
        return new MapIterable<Character, Result>(this, f); 
    }
    @Override 
    @Ignore
    public Iterable<? extends Character> filter(Callable<? extends Boolean> f) { 
        return this; 
    }
    @Override 
    @Ignore
    public <Result> Result fold(Result ini, Callable<? extends Result> f) { 
        return ini;
    }
    @Override @Ignore
    public boolean any(Callable<? extends Boolean> f) {
        return false;
    }
    @Override @Ignore
    public boolean every(Callable<? extends Boolean> f) {
        return false;
    }
}
