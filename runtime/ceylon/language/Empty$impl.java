package ceylon.language;

import com.redhat.ceylon.compiler.java.metadata.Ignore;

@Ignore
public final class Empty$impl {
    private final Empty $this;
    public Empty$impl(Empty $this) {
        this.$this = $this;
    }
    
    public long getSize() {
        return _getSize($this);
    }
    static long _getSize(Empty $this){
        return 0;
    }
    
    public Iterator getIterator() {
        return _getIterator($this);
    }
    static Iterator _getIterator(Empty $this){
        return emptyIterator.getEmptyIterator();
    }
    
    public java.lang.Object item(java.lang.Object key){
        return _item($this, key);
    }
    static java.lang.Object _item(Empty $this, java.lang.Object key){
        return null;
    }
    
    public Empty segment(Integer from, long length) {
        return _segment($this, from, length);
    }
    static Empty _segment(Empty $this, Integer from, long length) {
        return $this;
    }
    
    public Empty span(Integer from, Integer to) {
        return _span($this, from, to);
    }
    static Empty _span(Empty $this, Integer from, Integer to) {
        return $this;
    }
    
    public long count(java.lang.Object element) {
        return _count($this, element);
    }
    static long _count(Empty $this, java.lang.Object element) {
        return 0;
    }
    
    public boolean contains(java.lang.Object element) {
        return _contains($this, element);
    }
    static boolean _contains(Empty $this, java.lang.Object element) {
        return false;
    }
    
    public boolean defines(Integer key) {
        return _defines($this, key);
    }
    static boolean _defines(Empty $this, Integer key) {
        return false;
    }
    
    public Empty getClone() {
        return _getClone($this);
    }
    static Empty _getClone(Empty $this) {
        return $this;
    }
    
    public java.lang.String toString() {
        return _toString($this);
    }
    static java.lang.String _toString(Empty $this) {
        return "{}";
    }
    
    public Integer getLastIndex(){
        return _getLastIndex($this);
    }
    static Integer _getLastIndex(Empty $this){
        return null;
    }
}