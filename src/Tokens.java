
public class Tokens {
	public String vp;
	public String cp;
	public int lineNo;
	public Tokens(String vp , String cp , int l){
		this.vp = vp;
		this.cp = cp;
		this.lineNo = l;
	}
	public String toString(){
		return vp+"~"+cp+"~"+lineNo;
	}
	public void println(){
		System.out.println("( " +vp+" , "+cp+" ,"+lineNo+" )");
	}
	public void print(){
		System.out.print("( " +vp+" , "+cp+" ,"+lineNo+" )");
	}
	
	
}
		
